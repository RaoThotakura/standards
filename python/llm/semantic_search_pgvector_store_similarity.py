import asyncio, os
import uuid

from langchain_community.document_loaders import PyPDFLoader
from langchain_text_splitters import RecursiveCharacterTextSplitter
from langchain_ollama import OllamaEmbeddings
from langchain_postgres import PGVector, PGEngine, PGVectorStore
from sqlalchemy.exc import ProgrammingError

from sqlalchemy.ext.asyncio import create_async_engine
from langchain_postgres import Column
from langchain_cohere import CohereEmbeddings
from dotenv import load_dotenv
load_dotenv()
cohere_api_key = os.getenv("COHERE_API_KEY")

# Utilize Postgres DB Container based vector store called PGVector
# This program needs a docker instance running in order to host the Postgres vector (PGVector) Container
# PGVector is deprecated. Instead PGVectorStore is recommended
# Reference: https://docs.langchain.com/oss/python/integrations/vectorstores/pgvectorstore

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

embeddings = OllamaEmbeddings(model="llama3")

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

POSTGRES_USER = "langchain"  # @param {type: "string"}
POSTGRES_PASSWORD = "langchain"  # @param {type: "string"}
POSTGRES_HOST = "localhost"  # @param {type: "string"}
POSTGRES_PORT = "6024"  # @param {type: "string"}
POSTGRES_DB = "langchain"  # @param {type: "string"}
TABLE_NAME = "custom_test_vectorstore"  # @param {type: "string"}
VECTOR_SIZE = 1024  # @param {type: "int"}

# See docker command  to launch a Postgres instance with pgvector enabled. This is for asynchronous
# docker CLI:
CONNECTION_STRING = (
    f"postgresql+asyncpg://{POSTGRES_USER}:{POSTGRES_PASSWORD}@{POSTGRES_HOST}"
    f":{POSTGRES_PORT}/{POSTGRES_DB}"
)

# Create an SQLAlchemy based Asynchronous Engine
engine = create_async_engine(
    CONNECTION_STRING
)
pg_engine = PGEngine.from_engine(engine=engine)
SCHEMA_NAME="public"

# asynchronous
async def setup_pgvector_store():
    # Await the function call to get the result and assign it to a variable
    # asynchronous
    # try:
    await pg_engine.ainit_vectorstore_table(
        table_name=TABLE_NAME,
        vector_size=VECTOR_SIZE,
        schema_name=SCHEMA_NAME,    # Default: "public"
        id_column=Column(name="langchain_id", data_type="VARCHAR"),
        overwrite_existing=True,
        # metadata_columns=[Column("len", "INTEGER")],
    )
    # overwrite_existing=True option resolved the Duplicate Table runtime error.
    # metadate RuntimeError received while adding documents. So removed the metadata_columns as no meta data exists in all_splits
    # id_column was mapped as INTEGER and it gives DBAPIError : 'str' object cannot be interpreted as an integer.
    # Changing id_column to VARCHAR resolved the above error

    # except ProgrammingError as pe:
    #     await pg_engine.adrop_table(TABLE_NAME)
    #     print(f"Table already exists. Skipping creation. {pe=}, {type(pe)=}")

    embedding = CohereEmbeddings(model="embed-english-v3.0")

    # asynchronous
    try:
        custom_vector_store = await PGVectorStore.create(
            engine=pg_engine,
            table_name=TABLE_NAME,
            schema_name=SCHEMA_NAME,
            embedding_service=embedding,
            # metadata_columns=["len"]
            # metadatas=[{"len": len(t)} for t in all_splits]
        )
    except Exception as rte:
        print(f"RuntimeError while creating vector store {rte=}, {type(rte)=}")

    print(f"Received: {custom_vector_store}")
    try:
        # ids = await custom_vector_store.aadd_documents(documents=all_splits)
        # metadatas = [{"len": len(t)} for t in all_splits]
        ids = [str(uuid.uuid4()) for _ in all_splits]
        # await custom_vector_store.all_splits(all_splits,  ids=ids) # metadatas=metadatas,
        ids = await custom_vector_store.aadd_documents(all_splits,ids=ids)
    except Exception as exc:
        print(f"RuntimeError while adding documents into vector store {exc=}, {type(exc)=}")

    # aadd_documents gave DBAPIError - expected 1024 dimensions, not 4096. Found the
    # PGVectorStore's underlying database table is configured to accept (1024 dimensions).
    # embedding "OllamaEmbeddings" is configured to accept 4096. Changing to CohereEmbeddings resolved the error

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
    asyncio.run(setup_pgvector_store())


