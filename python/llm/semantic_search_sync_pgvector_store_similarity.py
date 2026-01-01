import asyncio
import os
import uuid

from langchain_community.document_loaders import PyPDFLoader
from langchain_text_splitters import RecursiveCharacterTextSplitter
from langchain_ollama import OllamaEmbeddings
from langchain_postgres import PGVector, PGEngine, PGVectorStore, Column
from sqlalchemy.exc import ProgrammingError

from langchain_cohere import CohereEmbeddings
from dotenv import load_dotenv
load_dotenv()
cohere_api_key = os.getenv("COHERE_API_KEY")

# Utilize Postgres DB Container based vector store called PGVector
# This program needs a docker instance running in order to host the Postgres vector (PGVector) Container
# PGVector is deprecated. Instead PGVectorStore is recommended

# SEARCH STRINGS
queries =dict([
        ('1', "How many distribution centers does Nike have in the US?"),
        ('2', "When was Nike incorporated?"),
        ('3', "What was Nike's revenue in 2023?"),
        ('4', "How were Nike's margins impacted in 2023?"),
      ])

# DOC LOADER

file_path = "../example_data/nke-10k-2023.pdf"
loader = PyPDFLoader(file_path)
docs = loader.load()

# TEXT SPLITTER

text_splitter = RecursiveCharacterTextSplitter(
    chunk_size=1000, chunk_overlap=200, add_start_index=True
)
all_splits = text_splitter.split_documents(docs)

print(len(all_splits))

# EMBEDDING

# embeddings = OllamaEmbeddings(model="llama3")
#
embeddings = CohereEmbeddings(model="embed-english-v3.0")

# Direct embed a text(s) as a query
vector_1 = embeddings.embed_query(all_splits[0].page_content)
vector_2 = embeddings.embed_query(all_splits[1].page_content)

assert len(vector_1) == len(vector_2)
print(f"Generated vectors of length {len(vector_1)}\n")
print(vector_1[:10])

# Expects a list of Document object
# embedded_documents = embeddings.embed_documents(all_splits)
# print(embedded_documents)


# See docker command to launch a postgres instance with pgvector enabled. This is for synchronous connection
# connection = "postgresql+psycopg://langchain:langchain@localhost:6024/langchain"  # Uses psycopg3!
# docker CLI: docker run --name pgvector-container -e POSTGRES_USER=langchain -e POSTGRES_PASSWORD=langchain -e POSTGRES_DB=langchain -p 6024:5432 -d pgvector/pgvector:pg16
# requires docker desktop running on the MacOS

collection_name = "my_docs"
# @title Set your values or use the defaults to connect to Docker { display-mode: "form" }
POSTGRES_USER = "langchain"  # @param {type: "string"}
POSTGRES_PASSWORD = "langchain"  # @param {type: "string"}
POSTGRES_HOST = "localhost"  # @param {type: "string"}
POSTGRES_PORT = "6024"  # @param {type: "string"}
POSTGRES_DB = "langchain"  # @param {type: "string"}
TABLE_NAME = "custom_test_vectorstore"  # @param {type: "string"}
VECTOR_SIZE = 1024  # @param {type: "int"}
# synchronous
CONNECTION_STRING = (
    f"postgresql+psycopg://{POSTGRES_USER}:{POSTGRES_PASSWORD}@{POSTGRES_HOST}"
    f":{POSTGRES_PORT}/{POSTGRES_DB}"
)

# To use psycopg3 driver, set your connection string to `postgresql+psycopg://`
# synchronous
pg_engine = PGEngine.from_connection_string(
    url=CONNECTION_STRING
)

vector_store = PGVectorStore.create_sync(
    engine=pg_engine,
    table_name=TABLE_NAME,
    embedding_service=embeddings
)
SCHEMA_NAME="public"

# synchronous
def setup_pgvector_store():
    # Await the function call to get the result and assign it to a variable
    # asynchronous
    try:
        pg_engine.init_vectorstore_table(
            table_name=TABLE_NAME,
            vector_size=VECTOR_SIZE,
            schema_name=SCHEMA_NAME,    # Default: "public"
            id_column=Column(name="langchain_id", data_type="VARCHAR"),
            overwrite_existing=True
        )
    except ProgrammingError as e:
        print("Table already exists. Skipping creation.")


    # synchronous
    custom_vector_store = PGVectorStore.create(
        engine=pg_engine,
        table_name=TABLE_NAME,
        schema_name=SCHEMA_NAME,
        embedding_service=embeddings
        # metadata_columns=["len"],
    )

    print(f"Received: {custom_vector_store}")
    try:
        ids = [str(uuid.uuid4()) for _ in all_splits]
        records = (custom_vector_store.add_documents(documents=all_splits,ids=ids))
    except Exception as exc:
        print(f"RuntimeError while adding documents into vector store {exc=}, {type(exc)=}")

    # RuntimeError while adding documents into vector store exc=TooManyRequestsError(),
    # type(exc)= < class 'cohere.errors.too_many_requests_error.TooManyRequestsError' >
    # Same error is reported in asynchronous mode.
    # For now cohere embeddings issue with how many records that PGVectorStore extension can consume without back pressure

    # print(f"Records added: {records}")

    results = custom_vector_store.similarity_search(queries['1'])
    print(f"Results from similar text - synchronous {results[0]}")

    # Note that providers implement different scores; the score here
    # is a distance metric that varies inversely with similarity.
    # Return scores:
    results = custom_vector_store.similarity_search_with_score(queries['3'])
    doc, score = results[0]
    print(f"Score: {score}\n")
    print(f"Results from similar search with score: {doc}")

    # Return documents based on similarity to an embedded query:
    embedding = embeddings.embed_query(queries['4'])
    results = custom_vector_store.similarity_search_by_vector(embedding)
    print(f"Results from similarity to an embedded query: {results[0]}")

    # Retrieve the most similar text - asynchronous
    async def similarity(search_str):
        results = await custom_vector_store.asimilarity_search(search_str)
        print(f"Results from similar text - asynchronous {results[0]}")
    asyncio.run(similarity(queries['2']))


# Run the entry point coroutine in the event loop
if __name__ == "__main__":
    setup_pgvector_store()
