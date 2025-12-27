from typing import List
from langchain.messages import AIMessage
from langchain.tools import tool
from langchain_ollama import ChatOllama
from langchain.messages import (
    HumanMessage,
    SystemMessage,
)

@tool
def validate_user(user_id: int, addresses: List[str]) -> bool:
    """Validate user using historical addresses.

    Args:
        user_id (int): the user ID.
        addresses (List[str]): Previous addresses as a list of strings.
    """
    return True

@tool("get_weather", description="Get weather")
def weather(location: str) -> str:
    """Get the weather at a location."""
    return f"It's sunny in {location}."


@tool("get_population", description="Get population")
def population(location: str) -> str:
    """Get the population of a specific location.
    Args:  location (str): The location to get the population from."""
    return f"Currently people live in city and state: '{location}'"

# This model works locally without any API Key.
# the model is installed into ~/ollama/models using a CLI

chat_model = (ChatOllama(
    model="gpt-oss:20b",
    validate_model_on_init=True,
    temperature=0,
))
chat_model.bind_tools([validate_user])

result = chat_model.invoke(
    "Could you validate user 123? They previously lived at "
    "123 Fake St in Boston MA and 234 Pretend Boulevard in "
    "Houston TX."
)

if isinstance(result, AIMessage) and result.tool_calls:
    print(result.tool_calls)

messages = [
    SystemMessage(content="You're a helpful assistant"),
    HumanMessage(
        content="What happens when an unstoppable force meets an immovable object?"
    ),
]
ai_msg = chat_model.invoke(messages)
print(ai_msg.content)

model_with_tools = chat_model.bind_tools([weather])

response = model_with_tools.invoke("What's the weather like in Boston?")
for tool_call in response.tool_calls:
    # View tool calls made by the model
    print(f"Tool: {tool_call['name']}")
    print(f"Args: {tool_call['args']}")
print(f"Response: ",response)

# Tool execution loop approach

chat_with_tools = chat_model.bind_tools([population])
ai_msg = chat_with_tools.invoke("Which city is hotter today and which is bigger: LA or NY?")
response_tool_calls= ai_msg.tool_calls
print(f'response_tool_calls: ', response_tool_calls)

# Parallel tool call approach

chat_model_with_tools = chat_model.bind_tools([population])
ai_msg_with_tools = chat_model_with_tools.invoke("Which city is hotter today and which is bigger: Chadds Ford, PA or Philadelphia, PA?")
print(f'AI Message with tool calls: ',ai_msg_with_tools.content)

