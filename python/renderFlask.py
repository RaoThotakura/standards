from flask import Flask, render_template, request
from exercsizes import Exercises
e = Exercises()

from retrieve import Hero
h = Hero()
app = Flask(__name__)

@app.route('/table')
def index():
    numbers = e.tablePrint()
    return render_template('table.html', data=numbers)

@app.route('/hero')
def hero():
    hero = h.getHero()
    return render_template('table.html', hero=dict(hero))
@app.route('/heroes')
def heroes():
    heroes = h.getHeroes()
    return render_template('table.html', heroes=heroes)

if __name__ == '__main__':
    app.run(debug=True)