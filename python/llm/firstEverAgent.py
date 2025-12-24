import os
from langchain.agents import create_agent
from langchain_openai import ChatOpenAI
from dataclasses import dataclass
from langchain.tools import tool, ToolRuntime
from langchain.chat_models import init_chat_model
from dotenv import load_dotenv
from langgraph.checkpoint.memory import InMemorySaver

SYSTEM_PROMPT = """You are an expert weather forecaster, who speaks in puns.

You have access to two tools:

- get_weather_for_location: use this to get the weather for a specific location
- get_user_location: use this to get the user's location

If a user asks you for the weather, make sure you know the location. If you can tell from the question that they mean wherever they are, use the get_user_location tool to find their location."""

load_dotenv()
openai_api_key = os.getenv("OPENAI_API_KEY")
openai_api_base_url = os.getenv("OPENAI_API_BASE")

print(f'OpenAI API Key: {openai_api_key}')
print(f'OpenAI API Base URL: {openai_api_base_url}')

# instantiate with a legacy model

# model = init_chat_model(
#     model="gpt-4.1", #"gpt-4o",  # The model name
#     model_provider="openai",  # Explicitly state the provider
#     openai_api_base=os.getenv("OPENAI_API_BASE"),  # Custom API base URL if needed
#     openai_api_key=os.getenv("OPENAI_API_KEY"),  # Though env var is recommended
#     temperature=0.5,
#     timeout=10,
#     max_tokens=1000
# )

# instantiate with a latest model using ChatOpenAI class

model = ChatOpenAI(
    model="gpt-5-nano",
    stream_usage=True,
    temperature=0.5,
    timeout=10,
    max_tokens=1000,
    reasoning_effort="low",
    max_retries=2,
    api_key=os.getenv("OPENAI_API_KEY"),  # If you prefer to pass api key in directly
    base_url=os.getenv("OPENAI_API_BASE"),
    # organization="...",
    # other params...
)

@dataclass
class Context:
    """Custom runtime context schema."""
    user_id: str

@tool
def get_weather_for_location(city: str) -> str:
    """Get weather for a given city."""
    return f"It's always sunny in {city}!"

@tool
def get_user_location(runtime: ToolRuntime[Context]) -> str:
    """Retrieve user information based on user ID."""
    user_id = runtime.context.user_id
    return "Florida" if user_id == "1" else "SF"

# We use a dataclass here, but Pydantic models are also supported.
@dataclass
class ResponseFormat:
    """Response schema for the agent."""
    # A punny response (always required)
    punny_response: str
    # Any interesting information about the weather if available
    weather_conditions: str | None = None

checkpointer = InMemorySaver()

from langchain.agents.structured_output import ToolStrategy

agent = create_agent(
    model=model,
    system_prompt=SYSTEM_PROMPT,
    tools=[get_user_location, get_weather_for_location],
    context_schema=Context,
    response_format=ToolStrategy(ResponseFormat),
    checkpointer=checkpointer
)

# `thread_id` is a unique identifier for a given conversation.
config = {"configurable": {"thread_id": "1"}}

# agent.invoke gives a error response in HTML format with a openai.PermissionDeniedError
response = agent.invoke(
    {"messages": [{"role": "user", "content": "what is the weather outside?"}]},
    config=config,
    context=Context(user_id="1")
)

print(response['structured_response'])
