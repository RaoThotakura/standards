from starlette.applications import Starlette
from starlette.routing import Route
from starlette.templating import Jinja2Templates
from starlette.staticfiles import StaticFiles

from exercsizes import Exercises
e = Exercises()

templates = Jinja2Templates(directory='templates')

async def homepage(request):
    knights = e.renderDict()
    context = {"request": request, "data":knights}
    return templates.TemplateResponse('index.html',context)

routes = [
    Route('/index', endpoint=homepage)
]

app = Starlette(debug=True, routes=routes)