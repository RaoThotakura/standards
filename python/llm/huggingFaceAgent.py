from langchain_huggingface import ChatHuggingFace, HuggingFaceEndpoint
from langchain.messages import (
    HumanMessage,
    SystemMessage,
)
import os

os.getenv("HUGGINGFACEHUB_API_KEY")

# from huggingface_hub import login
# login()  # You will be prompted for your HF key, which will then be saved locally

llm = HuggingFaceEndpoint(
    repo_id="deepseek-ai/DeepSeek-R1-0528",
    task="text-generation",
    max_new_tokens=512,
    do_sample=False,
    repetition_penalty=1.03,
    provider="auto",  # let Hugging Face choose the best provider for you
)

chat_model = ChatHuggingFace(llm=llm)



messages = [
    SystemMessage(content="You're a helpful assistant"),
    HumanMessage(
        content="What happens when an unstoppable force meets an immovable object?"
    ),
]

ai_msg = chat_model.invoke(messages)
print(ai_msg.content)