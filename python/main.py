from exercsizes import Exercises
from fastapi import FastAPI
app = FastAPI()

e = Exercises()

@app.get("/")
def read_root ():
    return {"Hello" : "World"}

@app.get("/fibonacci")
def read_fibonacci ():
    return e.fib()

@app.get("/loopNprintKv")
def read_loopingAndPrintKeyValue ():
    return e.loopingAndPrintKeyValue()

@app.get("/loopNprintMatrix")
def read_loopingAndPrintMatrix ():
    return e.loopingAndPrintMatrix()

@app.get("/tablePrint")
def read_tablePrint ():
    return e.tablePrint()

@app.get("/render")
def read_render ():
    e.fib()
    e.loopingAndPrintKeyValue()
    e.loopingAndPrintMatrix()
    e.tablePrint()