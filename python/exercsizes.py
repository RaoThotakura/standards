class Exercises:
    def fib(self):
        output = []
        print("invoking fibonacci..\n")
        a, b = 0, 1
        while a < 10:
            # print(a, end=";")
            output.append(a)
            a, b = b, a+b
        return output

    def loopingAndPrintKeyValue(self):  
        output = []
        print("invoking loopingAndPrint1..\n")
        knights = {'gallahad': 'the pure', 'robin': 'the brave'}
        for k, v in knights.items():
            output.append(f'{k}:{v}')
        return output
    
    def renderDict(self):  
        knights = {'gallahad': 'the pure', 'robin': 'the brave'}
        return knights

    def loopingAndPrintMatrix(self):
        print("invoking loopingAndPrint2..\n")
        output = []
        questions = ['name', 'quest', 'favorite color']
        answers = ['lancelot', 'the holy grail', 'blue']
        for q, a in zip(questions, answers):
            print('What is your {0}?  It is {1}.'.format(q, a))
            print(f'Question:- What is your {q}?'.format(q))
            print(f'Answer:- It is {a}.'.format(a))
            output.append('What is your {0}?  It is {1}.'.format(q, a))
        return output

    def tablePrint(self):
        print("invoking tablePrint..\n")
        output = []
        for x in range(1, 11):
            print('{0:2d} {1:3d} {2:4d}'.format(x, x*x, x*x*x))
            output.append('{0:2d} {1:3d} {2:4d}'.format(x, x*x, x*x*x))
        return output

e = Exercises()
e.fib()
print("\n")
e.loopingAndPrintKeyValue()
print("\n")
e.loopingAndPrintMatrix()
print("\n")
e.tablePrint()
