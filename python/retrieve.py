from sqlmodel import Field, Session, SQLModel, create_engine, select

class Hero(SQLModel, table=True):
    id: int | None = Field(default=None, primary_key=True)
    name: str
    secret_name: str
    age: int | None = None

    def getHero(self):
        engine = create_engine("sqlite:///hero.db", echo=True)

        with Session(engine) as session:
            statement = select(Hero).where(Hero.name == "Spider-Boy")
            hero = session.exec(statement).first()
        return hero

    def getHeroes(self):
        engine = create_engine("sqlite:///hero.db", echo=True)

        with Session(engine) as session:
            statement = select(Hero)
            results = session.exec(statement)
            heroes = results.all()
        return heroes