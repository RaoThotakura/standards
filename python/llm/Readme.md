
How many of the embedding models are supported locally?

Below study gives an idea of whether a Embedding Model requires API key or not. 
If a API key is required, then it is available as a remote resource.

CohereEmbeddings - requires API Key

TogetherEmbeddings - requires API Key

FireworksEmbeddings - requires API Key

MistralAIEmbeddings - requires API Key

NomicEmbeddings - requires API Key

WatsonxEmbeddings - requires API Key

NVIDIAEmbeddings - requires API Key

AimlapiEmbeddings - requires API Key

GoogleGenerativeAIEmbeddings - requires API Key

AzureOpenAIEmbeddings - requires API Key

OpenAIEmbeddings - requires API Key

DatabricksEmbeddings - requires API Key

OllamaEmbeddings - does not require API Key

FakeEmbeddings - does not require API Key


RESPONSES

firstEventAgent.py

agent.invoke gives a error response in HTML format with a openai.PermissionDeniedError
in both approaches using init_chat_model and ChatOpenAI class based model creation
OpenAI has no free access.

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

huggingFaceAgent.py

HuggingFaceChatModel is locally accessible by means of a API
No pricing or cost involved. But the free access is limited on a monthly basis


after a series of runs, the endpoint gives this error which means the monthly quota has reached for local access
Reference: https://discuss.huggingface.co/t/hugging-face-payment-error-402-youve-exceeded-monthly-quota/144968
requests.exceptions.HTTPError: 402 Client Error: Payment Required for url: https://router.huggingface.co/novita/v3/openai/chat/completion

model = HuggingFaceEndpoint(
    repo_id="deepseek-ai/DeepSeek-R1-0528",
    task="text-generation",
    max_new_tokens=512,
    do_sample=False,
    repetition_penalty=1.03,
    provider="auto",  # let Hugging Face choose the best provider for you
)

chat_model_microsoft = init_chat_model(
    "microsoft/Phi-3-mini-4k-instruct",
    model_provider="huggingface",
    temperature=0.7,
    max_tokens=1024,
)

ollamaOssAgent.py

This model works locally without any API Key.
the model is installed into ~/ollama/models using a CLI

chat_model = (ChatOllama(
    model="gpt-oss:20b",
    validate_model_on_init=True,
    temperature=0,
))

/Users/raothotakura/Documents/Samples/autoDCD/python/.venv/lib/python3.14/site-packages/langchain_core/_api/deprecation.py:26: UserWarning: Core Pydantic V1 functionality isn't compatible with Python 3.14 or greater.
  from pydantic.v1.fields import FieldInfo as FieldInfoV1

The “unstoppable force vs. immovable object” paradox

| **Question** | **Answer** |
|--------------|------------|
| **What happens when an unstoppable force meets an immovable object?** | The paradox is that the two concepts cannot coexist in a single, consistent physical world. Either the force is not truly unstoppable, or the object is not truly immovable. In a universe that obeys the laws of physics as we understand them, the scenario is impossible. |

---

1. Why the paradox is a paradox

| **Concept** | **Definition** | **Physical implication** |
|-------------|----------------|--------------------------|
| **Unstoppable force** | A force that can move *any* object, no matter how massive or rigid. | Requires infinite energy or a mechanism that can overcome any resistance. |
| **Immovable object** | An object that cannot be moved by *any* force, no matter how large. | Requires infinite mass or an infinite resistance to deformation. |

If both existed simultaneously, the following contradictions arise:

1. **Conservation of Energy / Momentum** – An unstoppable force would have to transfer infinite energy to an immovable object, violating conservation laws.
2. **Relativity of Motion** – In relativity, “force” is an interaction between masses. A force that can move *any* mass would have to act on the immovable object, but by definition that object cannot change its state of motion.
3. **Material limits** – Real materials have finite strength. An “unstoppable” force would break or deform any material; an “immovable” object would have infinite strength, which is physically impossible.

Thus, the two definitions are mutually exclusive. The paradox is a logical contradiction rather than a physical scenario.

---

2. How physics resolves the paradox

| **Approach** | **What it tells us** |
|--------------|----------------------|
| **Classical mechanics** | Forces are vectors that act on masses. There is no “infinite” force or “infinite” mass in Newtonian physics. |
| **Special relativity** | As an object’s speed approaches the speed of light, its relativistic mass increases without bound. No finite force can accelerate an object to or beyond light speed. |
| **General relativity** | Mass-energy curves spacetime. A “force” that could move any mass would have to curve spacetime infinitely, which would create a singularity (black hole). An “immovable” mass would be a singularity that cannot be perturbed. |
| **Quantum mechanics** | Even at the smallest scales, forces are mediated by particles (e.g., photons, gluons). There is no mechanism for an “infinite” force or an “infinite” resistance. |

In short, the laws of physics preclude the simultaneous existence of an unstoppable force and an immovable object.

---

3. Philosophical and literary interpretations

| **Interpretation** | **Key idea** |
|--------------------|--------------|
| **Metaphor for inevitability** | The unstoppable force represents an inevitable change (e.g., time, evolution). The immovable object represents an unchanging truth or principle. The paradox invites reflection on how change confronts constancy. |
| **Narrative device** | In stories, the clash can symbolize conflict between two powerful forces (e.g., good vs. evil). The resolution often involves one side being compromised or the other being redefined. |
| **Thought experiment** | Used to illustrate limits of language and logic. It shows that we must be careful when we use absolute terms like “unstoppable” or “immovable.” |

---

4. A playful “what if” scenario

If we imagine a universe where:

- **Unstoppable force** = a *massless* particle traveling at the speed of light (e.g., a photon) that can’t be stopped by any medium.
- **Immovable object** = a *black hole* with an event horizon that cannot be crossed by any particle.

Then the “meeting” would be a photon approaching a black hole. The photon can’t be stopped, but it can be captured by the black hole’s gravity. The black hole is “immovable” in the sense that nothing can escape from inside its horizon. The paradox dissolves because the photon is not “moving the black hole”; it is simply following the curved spacetime geometry.

---

5. Bottom line

- **In physics**: The scenario is impossible. The definitions contradict each other and violate conservation laws.
- **In philosophy/literature**: The paradox is a useful tool for exploring themes of inevitability, resistance, and the limits of human understanding.

So, when an unstoppable force meets an immovable object, the universe simply refuses to let the meeting happen—because the two concepts cannot coexist in a consistent, physical reality.
Tool: get_weather
Args: {'location': 'Boston'}
Response:  content='' additional_kwargs={} response_metadata={'model': 'gpt-oss:20b', 'created_at': '2025-12-27T01:08:24.987542Z', 'done': True, 'done_reason': 'stop', 'total_duration': 25206477291, 'load_duration': 294284458, 'prompt_eval_count': 128, 'prompt_eval_duration': 5631991209, 'eval_count': 32, 'eval_duration': 19122552249, 'logprobs': None, 'model_name': 'gpt-oss:20b', 'model_provider': 'ollama'} id='lc_run--019b5d59-1c96-7643-9fda-0f8ce4abbd4a-0' tool_calls=[{'name': 'get_weather', 'args': {'location': 'Boston'}, 'id': '27730b99-4c5e-429a-8d74-341c46185bc1', 'type': 'tool_call'}] usage_metadata={'input_tokens': 128, 'output_tokens': 32, 'total_tokens': 160}
response_tool_calls:  [{'name': 'get_population', 'args': {'location': 'Los Angeles'}, 'id': '82dd2ab1-cb09-4664-b388-9ecaad9a8e09', 'type': 'tool_call'}]
AI Message with tool calls:  **Which city is bigger?**
Philadelphia, PA is far larger than Chadds Ford, PA.
- **Philadelphia**: ~1.6 million residents (2020 Census)
- **Chadds Ford**: ~2,000 residents (2020 Census)

**Which city is hotter today?**
I don’t have real‑time weather data in this chat, so I can’t tell you which one is warmer right now.
To find out the current temperature, check a reliable weather source (e.g., the National Weather Service, Weather.com, or a weather app) for both locations. Once you have the numbers, the higher temperature will be the hotter city.

semantic_search_pre_retrieval.py

/Users/raothotakura/Documents/Samples/autoDCD/python/.venv/lib/python3.14/site-packages/langchain_core/_api/deprecation.py:26: UserWarning: Core Pydantic V1 functionality isn't compatible with Python 3.14 or greater.
  from pydantic.v1.fields import FieldInfo as FieldInfoV1
516
Generated vectors of length 4096

[-0.004283323, -0.029123468, -0.013847927, 0.004041289, -0.005853984, -0.025666876, -0.0069365455, -0.00687183, -0.034450404, -0.0008472501]

Results from similar text - synchronous 

page_content='Table of Contents
INTERNATIONAL MARKETS
For fiscal 2023, non-U.S. NIKE Brand and Converse sales accounted for approximately 57% of total revenues, compared to 60% and 61% for fiscal 2022 and fiscal 2021,
respectively. We sell our products to retail accounts through our own NIKE Direct operations and through a mix of independent distributors, licensees and sales
representatives around the world. We sell to thousands of retail accounts and ship products from 67 distribution centers outside of the United States. Refer to Item 2.
Properties for further information on distribution facilities outside of the United States. During fiscal 2023, NIKE's three largest customers outside of the United States
accounted for approximately 14% of total non-U.S. sales.
In addition to NIKE-owned and Converse-owned digital commerce platforms in over 40 countries, our NIKE Direct and Converse direct to consumer businesses operate
the following number of retail stores outside the United States:' metadata={'producer': 'EDGRpdf Service w/ EO.Pdf 22.0.40.0', 'creator': 'EDGAR Filing HTML Converter', 'creationdate': '2023-07-20T16:22:00-04:00', 'title': '0000320187-23-000039', 'author': 'EDGAR Online, a division of Donnelley Financial Solutions', 'subject': 'Form 10-K filed on 2023-07-20 for the period ending 2023-05-31', 'keywords': '0000320187-23-000039; ; 10-K', 'moddate': '2023-07-20T16:22:08-04:00', 'source': '../example_data/nke-10k-2023.pdf', 'total_pages': 107, 'page': 5, 'page_label': '6', 'start_index': 0}

Results from similar text - asynchronous 

page_content='Table of Contents
INTERNATIONAL MARKETS
For fiscal 2023, non-U.S. NIKE Brand and Converse sales accounted for approximately 57% of total revenues, compared to 60% and 61% for fiscal 2022 and fiscal 2021,
respectively. We sell our products to retail accounts through our own NIKE Direct operations and through a mix of independent distributors, licensees and sales
representatives around the world. We sell to thousands of retail accounts and ship products from 67 distribution centers outside of the United States. Refer to Item 2.
Properties for further information on distribution facilities outside of the United States. During fiscal 2023, NIKE's three largest customers outside of the United States
accounted for approximately 14% of total non-U.S. sales.
In addition to NIKE-owned and Converse-owned digital commerce platforms in over 40 countries, our NIKE Direct and Converse direct to consumer businesses operate
the following number of retail stores outside the United States:' metadata={'producer': 'EDGRpdf Service w/ EO.Pdf 22.0.40.0', 'creator': 'EDGAR Filing HTML Converter', 'creationdate': '2023-07-20T16:22:00-04:00', 'title': '0000320187-23-000039', 'author': 'EDGAR Online, a division of Donnelley Financial Solutions', 'subject': 'Form 10-K filed on 2023-07-20 for the period ending 2023-05-31', 'keywords': '0000320187-23-000039; ; 10-K', 'moddate': '2023-07-20T16:22:08-04:00', 'source': '../example_data/nke-10k-2023.pdf', 'total_pages': 107, 'page': 5, 'page_label': '6', 'start_index': 0}

Results from similar text - asynchronous 

page_content='Table of Contents
INTERNATIONAL MARKETS
For fiscal 2023, non-U.S. NIKE Brand and Converse sales accounted for approximately 57% of total revenues, compared to 60% and 61% for fiscal 2022 and fiscal 2021,
respectively. We sell our products to retail accounts through our own NIKE Direct operations and through a mix of independent distributors, licensees and sales
representatives around the world. We sell to thousands of retail accounts and ship products from 67 distribution centers outside of the United States. Refer to Item 2.
Properties for further information on distribution facilities outside of the United States. During fiscal 2023, NIKE's three largest customers outside of the United States
accounted for approximately 14% of total non-U.S. sales.
In addition to NIKE-owned and Converse-owned digital commerce platforms in over 40 countries, our NIKE Direct and Converse direct to consumer businesses operate
the following number of retail stores outside the United States:' metadata={'producer': 'EDGRpdf Service w/ EO.Pdf 22.0.40.0', 'creator': 'EDGAR Filing HTML Converter', 'creationdate': '2023-07-20T16:22:00-04:00', 'title': '0000320187-23-000039', 'author': 'EDGAR Online, a division of Donnelley Financial Solutions', 'subject': 'Form 10-K filed on 2023-07-20 for the period ending 2023-05-31', 'keywords': '0000320187-23-000039; ; 10-K', 'moddate': '2023-07-20T16:22:08-04:00', 'source': '../example_data/nke-10k-2023.pdf', 'total_pages': 107, 'page': 5, 'page_label': '6', 'start_index': 0}
Score: 0.6461532714115137

Results from similar search with score: 

page_content='Table of Contents
INTERNATIONAL MARKETS
For fiscal 2023, non-U.S. NIKE Brand and Converse sales accounted for approximately 57% of total revenues, compared to 60% and 61% for fiscal 2022 and fiscal 2021,
respectively. We sell our products to retail accounts through our own NIKE Direct operations and through a mix of independent distributors, licensees and sales
representatives around the world. We sell to thousands of retail accounts and ship products from 67 distribution centers outside of the United States. Refer to Item 2.
Properties for further information on distribution facilities outside of the United States. During fiscal 2023, NIKE's three largest customers outside of the United States
accounted for approximately 14% of total non-U.S. sales.
In addition to NIKE-owned and Converse-owned digital commerce platforms in over 40 countries, our NIKE Direct and Converse direct to consumer businesses operate
the following number of retail stores outside the United States:' metadata={'producer': 'EDGRpdf Service w/ EO.Pdf 22.0.40.0', 'creator': 'EDGAR Filing HTML Converter', 'creationdate': '2023-07-20T16:22:00-04:00', 'title': '0000320187-23-000039', 'author': 'EDGAR Online, a division of Donnelley Financial Solutions', 'subject': 'Form 10-K filed on 2023-07-20 for the period ending 2023-05-31', 'keywords': '0000320187-23-000039; ; 10-K', 'moddate': '2023-07-20T16:22:08-04:00', 'source': '../example_data/nke-10k-2023.pdf', 'total_pages': 107, 'page': 5, 'page_label': '6', 'start_index': 0}
Results from similarity to an embedded query: page_content='In recent years, uncertain global and regional economic and political conditions have affected international trade and increased protectionist actions around the
world. These trends are affecting many global manufacturing and service sectors, and the footwear and apparel industries, as a whole, are not immune. Companies in our
industry are facing trade protectionism in many different regions, and, in nearly all cases, we are working together with industry groups to address trade issues and reduce
the impact to the industry, while observing applicable competition laws. Notwithstanding our efforts, protectionist measures have resulted in increases in the cost of our
products, and additional measures, if implemented, could adversely affect sales and/or profitability for NIKE, as well as the imported footwear and apparel industry as a
whole.' metadata={'producer': 'EDGRpdf Service w/ EO.Pdf 22.0.40.0', 'creator': 'EDGAR Filing HTML Converter', 'creationdate': '2023-07-20T16:22:00-04:00', 'title': '0000320187-23-000039', 'author': 'EDGAR Online, a division of Donnelley Financial Solutions', 'subject': 'Form 10-K filed on 2023-07-20 for the period ending 2023-05-31', 'keywords': '0000320187-23-000039; ; 10-K', 'moddate': '2023-07-20T16:22:08-04:00', 'source': '../example_data/nke-10k-2023.pdf', 'total_pages': 107, 'page': 6, 'page_label': '7', 'start_index': 2900}

semantic_search_inline_docs_fake_embedding_similarity.py

/Users/raothotakura/Documents/Samples/autoDCD/python/.venv/lib/python3.14/site-packages/langchain_core/_api/deprecation.py:26: UserWarning: Core Pydantic V1 functionality isn't compatible with Python 3.14 or greater.
  from pydantic.v1.fields import FieldInfo as FieldInfoV1
516

Results from embeddings.embed_documents
 1352

Results from custom_vector_store.aadd_documents:
 659665ab-fc75-4236-aa32-73a6b536f5b1

Results from similar text - synchronous:
 page_content='Table of Contents
HUMAN CAPITAL RESOURCES
At NIKE, we consider the strength and effective management of our workforce to be essential to the ongoing success of our business. We believe that it is important to
attract, develop and retain a diverse and engaged workforce at all levels of our business and that such a workforce fosters creativity and accelerates innovation. We are
focused on building an increasingly diverse talent pipeline that reflects our consumers, athletes and the communities we serve.
CULTURE
Each employee shapes NIKE's culture through behaviors and practices. This starts with our Maxims, which represent our core values and, along with our Code of
Conduct, feature the fundamental behaviors that help anchor, inform and guide us and apply to all employees. Our mission is to bring inspiration and innovation to every
athlete in the world, which includes the belief that if you have a body, you are an athlete. We aim to do this by creating groundbreaking sport innovations, making our
products more sustainably, building a creative and diverse global team, supporting the well-being of our employees and making a positive impact in communities where
we live and work. Our mission is aligned with our deep commitment to maintaining an environment where all NIKE employees have the opportunity to reach their full
potential, to connect to our brands and to shape our workplace culture. We believe providing for growth and retention of our employees is essential in fostering such a
culture and are dedicated to giving access to training programs and career development opportunities, including trainings on NIKE's values, history and business,
trainings on developing leadership skills at all levels, tools and resources for managers and qualified tuition reimbursement opportunities.
As part of our commitment to empowering our employees to help shape our culture, we source employee feedback through our Engagement Survey program, including
several corporate pulse surveys. The program provides every employee throughout the globe an opportunity to provide confidential feedback on key areas known to drive
employee engagement, including their satisfaction with their managers, their work and the Company generally. The program also measures our employees’ emotional
commitment to NIKE as well as NIKE's culture of diversity, equity and inclusion. NIKE also provides multiple points of contact for employees to speak up if they experience
something that does not align with our values or otherwise violates our workplace policies, even if they are uncertain what they observed or heard is a violation of
company policy.
As part of our commitment to make a positive impact on our communities, we maintain a goal of investing 2% of our prior fiscal year's pre-tax income into global
communities. The focus of this investment continues to be inspiring kids to be active through play and sport as well as uniting and inspiring communities to create a better
and more equitable future for all. Our community investments are an important part of our culture in that we also support employees in giving back to community
organizations through donations and volunteering, which are matched by the NIKE Foundation where eligible.
EMPLOYEE BASE
As of May 31, 2023, we had approximately 83,700 employees worldwide, including retail and part-time employees. We also utilize independent contractors and temporary
personnel to supplement our workforce.
None of our employees are represented by a union, except certain employees in the EMEA and APLA geographies are members of and/or represented by trade unions,
as allowed or required by local law and/or collective bargaining agreements. Also, in some countries outside of the United States, local laws require employee
representation by works councils (which may be entitled to information and consultation on certain subsidiary decisions) or by organizations similar to a union. In certain
European countries, we are required by local law to enter into, and/or comply with, industry-wide or national collective bargaining agreements. NIKE has never
experienced a material interruption of operations due to labor disagreements.
DIVERSITY, EQUITY AND INCLUSION
Diversity, equity and inclusion ("DE&I") is a strategic priority for NIKE and we are committed to having an increasingly diverse team and culture. We aim to foster an
inclusive and accessible workplace through recruitment, development and retention of diverse talent with the goal of expanding representation across all dimensions of
diversity over the long term. We remain committed to the targets announced in fiscal 2021 for the Company to work toward by fiscal 2025, including increasing
representation of women in our global corporate workforce and leadership positions, as well as increasing representation of U.S. racial and ethnic minorities in our U.S.
corporate workforce and at the Director level and above.
We continue to enhance our efforts to recruit diverse talent through our traditional channels and through initiatives, such as partnerships with athletes and sports-related
organizations to create apprenticeship programs and new partnerships with organizations, colleges and universities that serve diverse populations. Additionally, we are
prioritizing DE&I education so that all NIKE employees and leaders have the cultural awareness and understanding to lead inclusively and build diverse and inclusive
teams. We also have Employee Networks, collectively known as NikeUNITED, representing various employee groups.
2023 FORM 10-K 6' metadata={'producer': 'EDGRpdf Service w/ EO.Pdf 22.0.40.0', 'creator': 'EDGAR Filing HTML Converter', 'creationdate': '2023-07-20T16:22:00-04:00', 'title': '0000320187-23-000039', 'author': 'EDGAR Online, a division of Donnelley Financial Solutions', 'subject': 'Form 10-K filed on 2023-07-20 for the period ending 2023-05-31', 'keywords': '0000320187-23-000039; ; 10-K', 'moddate': '2023-07-20T16:22:08-04:00', 'source': '../example_data/nke-10k-2023.pdf', 'total_pages': 107, 'page': 8, 'page_label': '9'}
Score: 0.06247198010772326

Results from similar search with score: 
page_content='Table of Contents
OTHER (INCOME) EXPENSE, NET
(Dollars in millions) FISCAL 2023 FISCAL 2022 FISCAL 2021
Other (income) expense, net $ (280) $ (181) $ 14 
Other (income) expense, net comprises foreign currency conversion gains and losses from the remeasurement of monetary assets and liabilities denominated in non-
functional currencies and the impact of certain foreign currency derivative instruments, as well as unusual or non-operating transactions that are outside the normal
course of business.
FISCAL 2023 COMPARED TO FISCAL 2022
Other (income) expense, net increased from $181 million of other income, net in fiscal 2022 to $280 million in the current fiscal year, primarily due to a net favorable
change in foreign currency conversion gains and losses, including hedges, and the one-time charge related to the deconsolidation of our Russian operations recognized
in the prior year. This increase was partially offset by net unfavorable activity related to our strategic distributor partnership transition within APLA, including the loss
recognized upon the completion of the sale of our entities in Argentina and Uruguay to a third-party distributor in the second quarter of fiscal 2023.
For more information related to our distributor partnership transition within APLA, see Note 18 — Acquisitions and Divestitures within the accompanying Notes to the
Consolidated Financial Statements.
We estimate the combination of the translation of foreign currency-denominated profits from our international businesses, and the year-over-year change in foreign
currency-related gains and losses included in Other (income) expense, net had an unfavorable impact on our Income before income taxes of $1,023 million for fiscal
2023.
INCOME TAXES
FISCAL 2023 FISCAL 2022 % CHANGE FISCAL 2021 % CHANGE
Effective tax rate 18.2 % 9.1 % 910 bps 14.0 % (490) bps
FISCAL 2023 COMPARED TO FISCAL 2022
Our effective tax rate was 18.2% for fiscal 2023, compared to 9.1% for fiscal 2022, primarily due to decreased benefits from stock-based compensation and a non-cash,
one-time benefit in the prior year related to the onshoring of certain non-U.S. intangible property ownership rights.
On August 16, 2022, the U.S. government enacted the Inflation Reduction Act of 2022 that includes, among other provisions, changes to the U.S. corporate income tax
system, including a fifteen percent minimum tax based on "adjusted financial statement income," which is effective for NIKE beginning June 1, 2023. Based on our current
analysis of the provisions, we do not expect these tax law changes to have a material impact on our financial statements; however, we will continue to evaluate their
impact as further information becomes available.
2023 FORM 10-K 35' metadata={'producer': 'EDGRpdf Service w/ EO.Pdf 22.0.40.0', 'creator': 'EDGAR Filing HTML Converter', 'creationdate': '2023-07-20T16:22:00-04:00', 'title': '0000320187-23-000039', 'author': 'EDGAR Online, a division of Donnelley Financial Solutions', 'subject': 'Form 10-K filed on 2023-07-20 for the period ending 2023-05-31', 'keywords': '0000320187-23-000039; ; 10-K', 'moddate': '2023-07-20T16:22:08-04:00', 'source': '../example_data/nke-10k-2023.pdf', 'total_pages': 107, 'page': 37, 'page_label': '38'}
Results from similarity to an embedded query:
 page_content='Table of Contents
The following table summarizes the reclassifications from Accumulated other comprehensive income (loss) to the Consolidated Statements of Income:
AMOUNT OF GAIN (LOSS)RECLASSIFIED FROM ACCUMULATEDOTHER COMPREHENSIVE INCOME(LOSS) INTO INCOME LOCATION OF GAIN (LOSS)RECLASSIFIED FROM ACCUMULATEDOTHER COMPREHENSIVE INCOME(LOSS) INTO INCOME
YEAR ENDED MAY 31,
(Dollars in millions) 2023 2022
Gains (losses) on foreign currency translation adjustment $ (374)$ — Other (income) expense, net
Total before tax (374) — 
Tax (expense) benefit 16 — 
Gain (loss) net of tax (358) — 
Gains (losses) on cash flow hedges:
Foreign exchange forwards and options 26 (82) Revenues
Foreign exchange forwards and options 581 (23) Cost of sales
Foreign exchange forwards and options (5) 1 Demand creation expense
Foreign exchange forwards and options 338 130 Other (income) expense, net
Interest rate swaps (8) (7) Interest expense (income), net
Total before tax 932 19 
Tax (expense) benefit (97) (11)
Gain (loss) net of tax 835 8 
Gains (losses) on other (19) 31 Other (income) expense, net
Total before tax (19) 31 
Tax (expense) benefit 5 (9)
Gain (loss) net of tax (14) 22 
Total net gain (loss) reclassified for the period $ 463 $ 30 
2023 FORM 10-K 82' metadata={'producer': 'EDGRpdf Service w/ EO.Pdf 22.0.40.0', 'creator': 'EDGAR Filing HTML Converter', 'creationdate': '2023-07-20T16:22:00-04:00', 'title': '0000320187-23-000039', 'author': 'EDGAR Online, a division of Donnelley Financial Solutions', 'subject': 'Form 10-K filed on 2023-07-20 for the period ending 2023-05-31', 'keywords': '0000320187-23-000039; ; 10-K', 'moddate': '2023-07-20T16:22:08-04:00', 'source': '../example_data/nke-10k-2023.pdf', 'total_pages': 107, 'page': 84, 'page_label': '85'}

Results from similar text - asynchronous:
 page_content='Table of Contents
ITEM 9. CHANGES IN AND DISAGREEMENTS WITHACCOUNTANTS ON ACCOUNTING AND FINANCIALDISCLOSURE
There has been no change of accountants nor any disagreements with accountants on any matter of accounting principles or practices or financial statement disclosure
required to be reported under this Item.
ITEM 9A. CONTROLS AND PROCEDURES
We maintain disclosure controls and procedures that are designed to provide reasonable assurance that information required to be disclosed in our Securities Exchange
Act of 1934, as amended (the "Exchange Act"), reports is recorded, processed, summarized and reported within the time periods specified in the Securities and Exchange
Commission's rules and forms and that such information is accumulated and communicated to our management, including our Chief Executive Officer and Chief Financial
Officer, as appropriate, to allow for timely decisions regarding required disclosure. In designing and evaluating the disclosure controls and procedures, management
recognizes that any controls and procedures, no matter how well designed and operated, can provide only reasonable assurance of achieving the desired control
objectives, and management is required to apply its judgment in evaluating the cost-benefit relationship of possible controls and procedures.
We carry out a variety of ongoing procedures, under the supervision and with the participation of our management, including our Chief Executive Officer and Chief
Financial Officer, to evaluate the effectiveness of the design and operation of our disclosure controls and procedures. Based on the foregoing, our Chief Executive Officer
and Chief Financial Officer concluded that our disclosure controls and procedures were effective at the reasonable assurance level as of May 31, 2023.
"Management's Annual Report on Internal Control Over Financial Reporting" is included in Item 8 of this Annual Report.
We are continuing several transformation initiatives to centralize and simplify our business processes and systems. These are long-term initiatives, which we believe will
enhance our internal control over financial reporting due to increased automation and further integration of related processes. We will continue to monitor our internal
control over financial reporting for effectiveness throughout these transformation initiatives.
There have not been any changes in our internal control over financial reporting during our most recent fiscal quarter that have materially affected, or are reasonably likely
to materially affect, our internal control over financial reporting.
ITEM 9B. OTHER INFORMATION
No disclosure is required under this item.
ITEM 9C. DISCLOSURE REGARDING FOREIGNJURISDICTIONS THAT PREVENT INSPECTIONS
Not applicable.
2023 FORM 10-K 91' metadata={'producer': 'EDGRpdf Service w/ EO.Pdf 22.0.40.0', 'creator': 'EDGAR Filing HTML Converter', 'creationdate': '2023-07-20T16:22:00-04:00', 'title': '0000320187-23-000039', 'author': 'EDGAR Online, a division of Donnelley Financial Solutions', 'subject': 'Form 10-K filed on 2023-07-20 for the period ending 2023-05-31', 'keywords': '0000320187-23-000039; ; 10-K', 'moddate': '2023-07-20T16:22:08-04:00', 'source': '../example_data/nke-10k-2023.pdf', 'total_pages': 107, 'page': 93, 'page_label': '94'}