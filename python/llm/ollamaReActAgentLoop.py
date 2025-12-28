from dataclasses import dataclass
from typing import List, Union
from langchain.agents.structured_output import ToolStrategy
from langchain.messages import AIMessage
from langchain.tools import tool
from langchain_ollama import ChatOllama
from langchain.agents import create_agent
from langchain.agents.middleware import wrap_tool_call
from langchain.messages import ToolMessage
from pydantic import BaseModel
# Evaluating ReAct (Reasoning and Acting) pattern of Tool execution in Agents

SYSTEM_PROMPT = """You are an expert weather forecaster and population census reporter, who speaks in puns.

You have access to two tools:

- weather: use this to get the weather for a specific location
- population: use this to get the population of the user's location

If a user asks you for the weather, make sure you know the location. If you can tell from the question that they mean wherever they are, use the get_user_location tool to find their location."""

@dataclass
class Context:
    """Custom runtime context schema."""
    user_id: str

@dataclass
class ResponseFormat:
    """Response schema for the agent."""
    # A punny response (always required)
    punny_response: str
    # Any interesting information about the weather if available
    weather_conditions: str | None = None
    population: int | None = None

@dataclass
class DemographicInfo(BaseModel):
    city: str
    state: str
    population: str
    weather: str

@tool("validate_user", description="Validate User")
def user(user_id: int, addresses: List[str]) -> bool:
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

@wrap_tool_call
def handle_tool_errors(request, handler):
    """Handle tool execution errors with custom messages."""
    try:
        return handler(request)
    except Exception as e:
        # Return a custom error message to the model
        return ToolMessage(
            content=f"Tool error: Please check your input and try again. ({str(e)})",
            tool_call_id=request.tool_call["id"]
        )

# This model works locally without any API Key.
# the model is installed into ~/ollama/models using a CLI

chat_model = ChatOllama(
    model="gpt-oss:20b",
    validate_model_on_init=True,
    temperature=0.8,
    num_predict=256
)

config = {"configurable": {"thread_id": "1"}}
context = Context(user_id="1")

@wrap_tool_call
def handle_tool_errors(request, handler):
    """Handle tool execution errors with custom messages."""
    try:
        return handler(request)
    except Exception as e:
        # Return a custom error message to the model
        return ToolMessage(
            content=f"Tool error: Please check your input and try again. ({str(e)})",
            tool_call_id=request.tool_call["id"]
        )

agent = create_agent(
    model=chat_model,
    system_prompt=SYSTEM_PROMPT,
    tools=[user, weather, population],
    context_schema=Context,
    response_format=ToolStrategy(ResponseFormat),
    middleware=[handle_tool_errors]
)

messages=dict([('role', 'user'), ('content', 'Could you validate user 123? They previously lived at 123 Fake St in Boston MA and 234 Pretend Boulevard in Houston TX."')])

# agent.invoke expects a dict object.
# that's why messages key is not used to allow the dictionary directly.
# inputs = {"messages":message},

result = agent.invoke(
    messages,
    config=config,
    context=context
)

if isinstance(result, AIMessage) and result.tool_calls:
    print(result.tool_calls)
print(f'AI Message with tool calls: ',result)

# RESPONSE
# AI Message with tool calls:  {'messages': [AIMessage(
#     content="Hello! I'm ready to forecast the sky and count the folks—just ask away, and I'll keep it pun-tastic!",
#     additional_kwargs={},
#     response_metadata={'model': 'gpt-oss:20b', 'created_at': '2025-12-27T22:19:45.151336Z', 'done': True, 'done_reason': 'stop', 'total_duration': 98444302125, 'load_duration': 10430438292, 'prompt_eval_count': 290, 'prompt_eval_duration': 54156739625, 'eval_count': 66, 'eval_duration': 33768406290, 'logprobs': None, 'model_name': 'gpt-oss:20b', 'model_provider': 'ollama'}, id='lc_run--019b61e3-efef-73f2-b4f6-6f48a99626f0-0',
#     usage_metadata={'input_tokens': 290, 'output_tokens': 66, 'total_tokens': 356})]}

for message in result["messages"]:
    message.pretty_print()

# RESPONSE
#================================== Ai Message ==================================

#Sure thing! I’m ready to bring the weather forecast and census data—pun‑tastic style—whenever you need it. Just let me know what location you’re curious about, and I’ll fetch the latest weather and population numbers for you!

# messages = [
#     SystemMessage(content="You're a helpful assistant"),
#     HumanMessage(
#         content="What happens when an unstoppable force meets an immovable object?"
#     ),
# ]

demographicInfoAgent = create_agent(
    model=chat_model,
    system_prompt=SYSTEM_PROMPT,
    tools=[weather, population],
    context_schema=Context,
    response_format=ToolStrategy(DemographicInfo),
    middleware=[handle_tool_errors]
)

demographicResult = demographicInfoAgent.invoke({
    "messages": [{"role": "user", "content": "Which city is bigger: Chadds Ford, PA or Philadelphia, PA?"}]
})

for message in demographicResult["messages"]:
    message.pretty_print()

# RESPONSE
# Extract demographic and weather info for: Chadds Ford, PA
# ================================== Ai Message ==================================
# Tool Calls:
#   get_population (fc69cfdd-70f8-44e3-8f97-87c189f1be46)
#  Call ID: fc69cfdd-70f8-44e3-8f97-87c189f1be46
#   Args:
#     location: Chadds Ford, PA
# ================================= Tool Message =================================
# Name: get_population
#
# Currently people live in city and state: 'Chadds Ford, PA'
# ================================== Ai Message ==================================
# Tool Calls:
#   get_weather (fb30f7aa-ebc8-4e8a-8851-83c76c5ddd22)
#  Call ID: fb30f7aa-ebc8-4e8a-8851-83c76c5ddd22
#   Args:
#     location: Chadds Ford, PA
# ================================= Tool Message =================================
# Name: get_weather
#
# It's sunny in Chadds Ford, PA.
# ================================== Ai Message ==================================
# Tool Calls:
#   DemographicInfo (57044ff1-c514-4007-87b9-5be66a5da996)
#  Call ID: 57044ff1-c514-4007-87b9-5be66a5da996
#   Args:
#     city: Chadds Ford
#     population: Currently people live in city and state: 'Chadds Ford, PA'
#     state: PA
#     weather: It's sunny in Chadds Ford, PA.
# ================================= Tool Message =================================
# Name: DemographicInfo

demographicResultPhl = demographicInfoAgent.invoke({
    "messages": [{"role": "user", "content": "What is the population of cities: Chadds Ford, PA and Philadelphia, PA?"}]
})
# structured_response appears not supported for this specific model provider
# for message in demographicResultPhl["structured_response"]:
#     message.pretty_print()

# RESPONSE
# Returning structured response: city='Chadds Ford' state='PA' population="Currently people live in city and state: 'Chadds Ford, PA'" weather="It's sunny in Chadds Ford, PA."
# Traceback (most recent call last):
#   File "/Users/raothotakura/Documents/Samples/autoDCD/python/llm/ollamaToolExecAgentLoop.py", line 167, in <module>
#     for message in demographicResultPhl["structured_response"]:
#                    ~~~~~~~~~~~~~~~~~~~~^^^^^^^^^^^^^^^^^^^^^^^
for message in demographicResultPhl["messages"]:
    message.pretty_print()