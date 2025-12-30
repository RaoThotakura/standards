import asyncio

from langchain_community.document_loaders import PyPDFLoader
from langchain_text_splitters import RecursiveCharacterTextSplitter
from langchain_ollama import OllamaEmbeddings
from langchain_postgres import PGVector

# Utilize Postgres DB Container based vector store called PGVector
# This program needs a docker instance running in order to host the Postgres vector (PGVector) Container

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

# See docker command above to launch a postgres instance with pgvector enabled.
connection = "postgresql+psycopg://langchain:langchain@localhost:6024/langchain"  # Uses psycopg3!
collection_name = "my_docs"

vector_store = PGVector(
    embeddings=embeddings,
    collection_name=collection_name,
    connection=connection,
    use_jsonb=True,
)

ids = vector_store.add_documents(documents=all_splits)

results = vector_store.similarity_search(queries['1'])
print(f"Results from similar text - synchronous {results[0]}")

# Note that providers implement different scores; the score here
# is a distance metric that varies inversely with similarity.
# Return scores:
results = vector_store.similarity_search_with_score(queries['3'])
doc, score = results[0]
print(f"Score: {score}\n")
print(f"Results from similar search with score: {doc}")

# Return documents based on similarity to an embedded query:
embedding = embeddings.embed_query(queries['4'])
results = vector_store.similarity_search_by_vector(embedding)
print(f"Results from similarity to an embedded query: {results[0]}")

# Retrieve the most similar text - asynchronous
# async def similarity(search_str):
#     results = await vector_store.asimilarity_search(search_str)
#     print(f"Results from similar text - asynchronous {results[0]}")
#
# asyncio.run(similarity(queries['2']))

async def similar_search(search_str):
    docs = await vector_store.asimilarity_search(search_str)
    for doc in docs:
        print(repr(doc))
    # print(f"Results from similar text - asynchronous {results[0]}")

asyncio.run(similar_search(queries['2']))

