# HuggingFaceChatModel is locally accessible by means of a API
# No pricing or cost involved
# after a series of runs, the endpoint gives this error which means the monthly quota has reached for local access
# Reference: https://discuss.huggingface.co/t/hugging-face-payment-error-402-youve-exceeded-monthly-quota/144968
#requests.exceptions.HTTPError: 402 Client Error: Payment Required for url: https://router.huggingface.co/novita/v3/openai/chat/completion
#
from langchain_huggingface import ChatHuggingFace, HuggingFaceEndpoint
from langchain.messages import (
    HumanMessage,
    SystemMessage,
)
from langchain.tools import tool

import os
from pydantic import BaseModel, Field

os.getenv("HUGGINGFACEHUB_API_KEY")

# from huggingface_hub import login
# login()  # You will be prompted for your HF key, which will then be saved locally

model = HuggingFaceEndpoint(
    repo_id="deepseek-ai/DeepSeek-R1-0528",
    task="text-generation",
    max_new_tokens=512,
    do_sample=False,
    repetition_penalty=1.03,
    provider="auto",  # let Hugging Face choose the best provider for you
)

chat_model = ChatHuggingFace(llm=model)

# OpenAI Message prompts format
#
messages = [
    SystemMessage(content="You're a helpful assistant"),
    HumanMessage(
        content="What happens when an unstoppable force meets an immovable object?"
    ),
]
ai_msg = chat_model.invoke(messages)
print(ai_msg.content)

# OpenAI Completions / Dictionary format
conversation = [
    {"role": "system", "content": "You are a helpful assistant that translates English to French."},
    {"role": "user", "content": "Translate: I love programming."},
    {"role": "assistant", "content": "J'adore la programmation."},
    {"role": "user", "content": "Translate: I love building applications."}
]
response = chat_model.invoke(conversation)
print(f'AI Message: ', response)  # AIMessage("J'adore créer des applications.")
print(f'Type of Message: ', type(response))

#Tool calls

@tool
def get_weather(location: str) -> str:
    """Get the weather at a location."""
    return f"It's sunny in {location}."

model_with_tools = chat_model.bind_tool([get_weather])

response = model_with_tools.invoke("What's the weather like in Boston?")
for tool_call in response.tool_calls:
    # View tool calls made by the model
    print(f"Tool: {tool_call['name']}")
    print(f"Args: {tool_call['args']}")
print(f"Response: ",response)

class GetPopulation(BaseModel):
    '''Get the current population in a given location'''
    location: str = Field(..., description="The city and state, e.g. San Francisco, CA")

chat_with_tools = chat_model.bind_tools([GetPopulation])
ai_msg = chat_with_tools.invoke("Which city is hotter today and which is bigger: LA or NY?")
response_tool_calls= ai_msg.tool_calls
print(f'response_tool_calls: ', response_tool_calls)
chat_model_with_tools = chat_model.bind_tools([get_weather, GetPopulation])
ai_msg_with_tools = chat_model_with_tools.invoke("Which city is hotter today and which is bigger: Chadds Ford, PA or Pittsburgh, PA?")
# ai_msg.tool_calls
print(f'AI Message with tool calls: ',ai_msg_with_tools.content)

for tool_call in ai_msg_with_tools.tool_calls:
    print(f"Tool: {tool_call['name']}")
    print(f"Args: {tool_call['args']}")
    print(f"ID: {tool_call['id']}")

# Bind (potentially multiple) tools to the model
model_with_tools = chat_model.bind_tools([get_weather])


