import asyncio
from langchain_community.embeddings import FakeEmbeddings
from langchain_core.vectorstores import InMemoryVectorStore
from langchain_community.document_loaders import PyPDFLoader
from langchain_text_splitters import RecursiveCharacterTextSplitter

embeddings = FakeEmbeddings(size=1352)

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

# VECTORSTORE

# Create a vector store with the embeddings reference by add_documents approach
# This approach will store entire chunks that were created by text splitter
# Using InMemoryVectorStore which is RAM intensive and very slow

custom_vector_store= InMemoryVectorStore(embeddings)
results = embeddings.embed_documents(docs)

print(f"Results from embeddings.embed_documents\n {len(results[0])}")

# asynchronous
async def setup_pgvector_store(docs=None):

    results = await custom_vector_store.aadd_documents(docs)
    print(f"Results from custom_vector_store.aadd_documents:\n {results[0]}")

    results = custom_vector_store.similarity_search(queries['1'])
    print(f"Results from similar text - synchronous:\n {results[0]}")

    # Note that providers implement different scores; the score here
    # is a distance metric that varies inversely with similarity.
    # Return scores:
    results = custom_vector_store.similarity_search_with_score(queries['3'])
    doc, score = results[0]
    print(f"Score: {score}\n")
    print(f"Results from similar search with score:\n {doc}")

    # Return documents based on similarity to an embedded query:
    embedding = embeddings.embed_query(queries['4'])
    results = custom_vector_store.similarity_search_by_vector(embedding)
    print(f"Results from similarity to an embedded query:\n {results[0]}")

# Retrieve the most similar text - asynchronous
async def similarity(search_str):
    try:
        results = await custom_vector_store.asimilarity_search(search_str)
        print(f"Results from similar text - asynchronous:\n {results[0]}")
    except Exception as exc:
        print(f"RuntimeError while asimilarity_search:\n {exc=}, {type(exc)=}")

# Run the entry point coroutine in the event loop
if __name__ == "__main__":
    asyncio.run(setup_pgvector_store(docs))
    asyncio.run(similarity(queries['2']))