from sqlmodel import Field, Session, SQLModel, create_engine, select

class Hero(SQLModel, table=True):
    id: int | None = Field(default=None, primary_key=True)
    name: str
    secret_name: str
    age: int | None = None

    def getSpecificHero(self,name):
        engine = create_engine("sqlite:///hero.db", echo=True)

        with Session(engine) as session:
            statement = select(Hero).where(Hero.name == name)
            hero = session.exec(statement).first()
        return hero

    def updateHero(self,formData):
        engine = create_engine("sqlite:///hero.db", echo=True)

        print(f'updateHero :: formData: {formData.get('name')}')

        with Session(engine) as session:
            statement = select(Hero).where(Hero.id == formData.get('id'))
            results = session.exec(statement)
            hero = results.one()
            hero.name = formData.get('name')
            hero.secret_name = formData.get('secret_name')
            hero.age = formData.get('age')
            session.add(hero)
            session.commit()
            session.refresh(hero)
        print(f'updateHero :: hero: {hero}')

        return hero