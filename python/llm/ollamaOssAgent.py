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


# RESPONSES
# /Users/raothotakura/Documents/Samples/autoDCD/python/.venv/lib/python3.14/site-packages/langchain_core/_api/deprecation.py:26: UserWarning: Core Pydantic V1 functionality isn't compatible with Python 3.14 or greater.
#   from pydantic.v1.fields import FieldInfo as FieldInfoV1
# ### The “unstoppable force vs. immovable object” paradox
#
# | **Question** | **Answer** |
# |--------------|------------|
# | **What happens when an unstoppable force meets an immovable object?** | The paradox is that the two concepts cannot coexist in a single, consistent physical world. Either the force is not truly unstoppable, or the object is not truly immovable. In a universe that obeys the laws of physics as we understand them, the scenario is impossible. |
#
# ---
#
# ## 1. Why the paradox is a paradox
#
# | **Concept** | **Definition** | **Physical implication** |
# |-------------|----------------|--------------------------|
# | **Unstoppable force** | A force that can move *any* object, no matter how massive or rigid. | Requires infinite energy or a mechanism that can overcome any resistance. |
# | **Immovable object** | An object that cannot be moved by *any* force, no matter how large. | Requires infinite mass or an infinite resistance to deformation. |
#
# If both existed simultaneously, the following contradictions arise:
#
# 1. **Conservation of Energy / Momentum** – An unstoppable force would have to transfer infinite energy to an immovable object, violating conservation laws.
# 2. **Relativity of Motion** – In relativity, “force” is an interaction between masses. A force that can move *any* mass would have to act on the immovable object, but by definition that object cannot change its state of motion.
# 3. **Material limits** – Real materials have finite strength. An “unstoppable” force would break or deform any material; an “immovable” object would have infinite strength, which is physically impossible.
#
# Thus, the two definitions are mutually exclusive. The paradox is a logical contradiction rather than a physical scenario.
#
# ---
#
# ## 2. How physics resolves the paradox
#
# | **Approach** | **What it tells us** |
# |--------------|----------------------|
# | **Classical mechanics** | Forces are vectors that act on masses. There is no “infinite” force or “infinite” mass in Newtonian physics. |
# | **Special relativity** | As an object’s speed approaches the speed of light, its relativistic mass increases without bound. No finite force can accelerate an object to or beyond light speed. |
# | **General relativity** | Mass-energy curves spacetime. A “force” that could move any mass would have to curve spacetime infinitely, which would create a singularity (black hole). An “immovable” mass would be a singularity that cannot be perturbed. |
# | **Quantum mechanics** | Even at the smallest scales, forces are mediated by particles (e.g., photons, gluons). There is no mechanism for an “infinite” force or an “infinite” resistance. |
#
# In short, the laws of physics preclude the simultaneous existence of an unstoppable force and an immovable object.
#
# ---
#
# ## 3. Philosophical and literary interpretations
#
# | **Interpretation** | **Key idea** |
# |--------------------|--------------|
# | **Metaphor for inevitability** | The unstoppable force represents an inevitable change (e.g., time, evolution). The immovable object represents an unchanging truth or principle. The paradox invites reflection on how change confronts constancy. |
# | **Narrative device** | In stories, the clash can symbolize conflict between two powerful forces (e.g., good vs. evil). The resolution often involves one side being compromised or the other being redefined. |
# | **Thought experiment** | Used to illustrate limits of language and logic. It shows that we must be careful when we use absolute terms like “unstoppable” or “immovable.” |
#
# ---
#
# ## 4. A playful “what if” scenario
#
# If we imagine a universe where:
#
# - **Unstoppable force** = a *massless* particle traveling at the speed of light (e.g., a photon) that can’t be stopped by any medium.
# - **Immovable object** = a *black hole* with an event horizon that cannot be crossed by any particle.
#
# Then the “meeting” would be a photon approaching a black hole. The photon can’t be stopped, but it can be captured by the black hole’s gravity. The black hole is “immovable” in the sense that nothing can escape from inside its horizon. The paradox dissolves because the photon is not “moving the black hole”; it is simply following the curved spacetime geometry.
#
# ---
#
# ## 5. Bottom line
#
# - **In physics**: The scenario is impossible. The definitions contradict each other and violate conservation laws.
# - **In philosophy/literature**: The paradox is a useful tool for exploring themes of inevitability, resistance, and the limits of human understanding.
#
# So, when an unstoppable force meets an immovable object, the universe simply refuses to let the meeting happen—because the two concepts cannot coexist in a consistent, physical reality.
# Tool: get_weather
# Args: {'location': 'Boston'}
# Response:  content='' additional_kwargs={} response_metadata={'model': 'gpt-oss:20b', 'created_at': '2025-12-27T01:08:24.987542Z', 'done': True, 'done_reason': 'stop', 'total_duration': 25206477291, 'load_duration': 294284458, 'prompt_eval_count': 128, 'prompt_eval_duration': 5631991209, 'eval_count': 32, 'eval_duration': 19122552249, 'logprobs': None, 'model_name': 'gpt-oss:20b', 'model_provider': 'ollama'} id='lc_run--019b5d59-1c96-7643-9fda-0f8ce4abbd4a-0' tool_calls=[{'name': 'get_weather', 'args': {'location': 'Boston'}, 'id': '27730b99-4c5e-429a-8d74-341c46185bc1', 'type': 'tool_call'}] usage_metadata={'input_tokens': 128, 'output_tokens': 32, 'total_tokens': 160}
# response_tool_calls:  [{'name': 'get_population', 'args': {'location': 'Los Angeles'}, 'id': '82dd2ab1-cb09-4664-b388-9ecaad9a8e09', 'type': 'tool_call'}]
# AI Message with tool calls:  **Which city is bigger?**
# Philadelphia, PA is far larger than Chadds Ford, PA.
# - **Philadelphia**: ~1.6 million residents (2020 Census)
# - **Chadds Ford**: ~2,000 residents (2020 Census)
#
# **Which city is hotter today?**
# I don’t have real‑time weather data in this chat, so I can’t tell you which one is warmer right now.
# To find out the current temperature, check a reliable weather source (e.g., the National Weather Service, Weather.com, or a weather app) for both locations. Once you have the numbers, the higher temperature will be the hotter city.
