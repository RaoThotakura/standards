import asyncio, os, uuid
from langchain_core.documents import Document
from langchain_ollama import OllamaEmbeddings
from langchain_postgres import PGEngine, PGVectorStore

from sqlalchemy.ext.asyncio import create_async_engine
from langchain_postgres import Column
from langchain_cohere import CohereEmbeddings
from dotenv import load_dotenv
load_dotenv()
cohere_api_key = os.getenv("COHERE_API_KEY")

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

# embeddings = OllamaEmbeddings(model="llama3")
embeddings = CohereEmbeddings(model="embed-english-v3.0")

docs = [
    Document(
        id=str(uuid.uuid4()),
        page_content="Red Apple",
        metadata={"description": "red", "content": "1", "category": "fruit"},
    ),
    Document(
        id=str(uuid.uuid4()),
        page_content="Banana Cavendish",
        metadata={"description": "yellow", "content": "2", "category": "fruit"},
    ),
    Document(
        id=str(uuid.uuid4()),
        page_content="Orange Navel",
        metadata={"description": "orange", "content": "3", "category": "fruit"},
    ),
]

# asynchronous
async def setup_pgvector_store(docs=None):
    # Await the function call to get the result and assign it to a variable
    # asynchronous
    await pg_engine.ainit_vectorstore_table(
        table_name=TABLE_NAME,
        vector_size=VECTOR_SIZE,
        schema_name=SCHEMA_NAME,    # Default: "public"
        id_column=Column(name="langchain_id", data_type="VARCHAR"),
        overwrite_existing=True
    )
    try:
        custom_vector_store = await PGVectorStore.create(
            engine=pg_engine,
            table_name=TABLE_NAME,
            schema_name=SCHEMA_NAME,
            embedding_service=embeddings
        )
    except Exception as rte:
        print(f"RuntimeError while creating vector store {rte=}, {type(rte)=}")
    print(f"Received: {custom_vector_store}")
    try:
        ids = [str(uuid.uuid4()) for _ in docs]
        # await custom_vector_store.all_splits(all_splits,  ids=ids) # metadatas=metadatas,
        ids = await custom_vector_store.aadd_documents(docs,ids=ids)
        # OllamaEmbeddings gives RuntimeError while adding documents into vector store exc=DBAPIError("(sqlalchemy.dialects.postgresql.asyncpg.Error) <class 'asyncpg.exceptions.DataError'>: expected 1024 dimensions, not 4096"), type(exc)=<class 'sqlalchemy.exc.DBAPIError'>
        # CohereEmbeddings gives RuntimeError while adding documents into vector store exc=TooManyRequestsError(), type(exc)=<class 'cohere.errors.too_many_requests_error.TooManyRequestsError'>
    except Exception as exc:
        print(f"RuntimeError while adding documents into vector store {exc=}, {type(exc)=}")
# Run the entry point coroutine in the event loop
if __name__ == "__main__":
    asyncio.run(setup_pgvector_store(docs))