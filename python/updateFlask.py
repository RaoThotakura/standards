from flask import Flask, render_template, request, jsonify

from update import Hero
hs = Hero()
app = Flask(__name__)

@app.route('/specifichero/<name>')
def specifichero(name):
    hero = hs.getSpecificHero(name)
    return render_template('update.html', hero=dict(hero))

@app.route('/update', methods=['POST'])
def update():
    # formData = {
    #     'name' : request.form["name"],
    #     'secret_name' : request.form["secret_name"],
    #     'age' : request.form["age"]
    # }
    # name = request.form["name"]
    # secret_name = request.form["secret_name"]
    # age = request.form["age"]

    formData = request.get_json()

    hero = hs.updateHero(formData)
    print(f'update :: hero: {dict(hero)}')
    return {"status": "success", "response": dict(hero)}
    #
    # return jsonify([hero.to_json() for hero in hero])
    # return render_template('update.html', hero=hero)

@app.errorhandler(500)
def internal_error(error):
    return "500 error"

@app.errorhandler(404)
def not_found(error):
    return "404 error",404