import asyncio
from langchain_core.vectorstores import InMemoryVectorStore
from langchain_community.document_loaders import PyPDFLoader
from langchain_text_splitters import RecursiveCharacterTextSplitter

from langchain_core.embeddings import DeterministicFakeEmbedding

# Facebook AI Similarity Search (FAISS) Usage
import faiss
from langchain_community.docstore.in_memory import InMemoryDocstore
from langchain_community.vectorstores import FAISS

# Embedding Model
embeddings = DeterministicFakeEmbedding(size=4096)

# Usage BedrockEmbeddings from AWS
# Installation steps
# pip install -qU langchain-aws
# Requires AWS cloud account credentials
# from langchain_aws import BedrockEmbeddings
# embeddings = BedrockEmbeddings(model_id="amazon.titan-embed-text-v2:0",  region_name="us-east-1")

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

# Resolution for error message: exc=AttributeError("'Document' object has no attribute 'encode'"), type(exc)=<class 'AttributeError'>
# Changes needed after utilizing DeterministicFakeEmbedding
all_splits_strings = [doc.page_content for doc in all_splits]

print(len(all_splits_strings))

# VECTORSTORE

# Create a vector store with the embeddings reference by add_documents approach
# This approach will store entire chunks that were created by text splitter
try:
    index = faiss.IndexFlatL2(len(embeddings.embed_query(queries['1'])))

    vector_store = FAISS(
        embedding_function=embeddings,
        index=index,
        docstore=InMemoryDocstore(),
        index_to_docstore_id={},
    )

    custom_vector_store= InMemoryVectorStore(embeddings)
    results = embeddings.embed_documents(all_splits_strings) # gives  exc=AttributeError("'Document' object has no attribute 'encode'"), type(exc)=<class 'AttributeError'>
except Exception as exc:
    print(f"RuntimeError embedding documents:\n\n {exc=}, {type(exc)=}")

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