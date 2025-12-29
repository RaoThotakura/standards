from langchain_community.document_loaders import PyPDFLoader
from langchain_text_splitters import RecursiveCharacterTextSplitter
from langchain_ollama import OllamaEmbeddings
from langchain_core.vectorstores import InMemoryVectorStore

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
vector_1 = embeddings.embed_query(all_splits[0].page_content)
vector_2 = embeddings.embed_query(all_splits[1].page_content)

assert len(vector_1) == len(vector_2)
print(f"Generated vectors of length {len(vector_1)}\n")
print(vector_1[:10])

# VECTORSTORE

# Create a vector store with a sample text
vectorstore = InMemoryVectorStore(embeddings)
ids = vectorstore.add_documents(documents=all_splits)

# Retrieve the most similar text - synchronous
results = vectorstore.similarity_search(queries['1'])
print(f"Results from similar text - synchronous {results[0]}")

# Retrieve the most similar text - asynchronous
async def similarity(search_str):
    results = await vectorstore.asimilarity_search(search_str)
    print(f"Results from similar text - asynchronous {results[0]}")

similarity(queries['2'])

# Note that providers implement different scores; the score here
# is a distance metric that varies inversely with similarity.
# Return scores:
results = vectorstore.similarity_search_with_score(queries['3'])
doc, score = results[0]
print(f"Score: {score}\n")
print(f"Results from similar search with score: {doc}")

# Return documents based on similarity to an embedded query:
embedding = embeddings.embed_query(queries['4'])
results = vectorstore.similarity_search_by_vector(embedding)
print(f"Results from similarity to an embedded query: {results[0]}")