import warnings; warnings.simplefilter('ignore')
import csv
import matplotlib.pyplot as plt
import numpy as np
import os
from SGD import MySGDRegression
from sklearn.linear_model import SGDRegressor
from sklearn.preprocessing import StandardScaler
from sklearn.pipeline import make_pipeline


def loadData(fileName, inputVariabName, outputVariabName):
    data = []
    dataNames = []
    with open(fileName) as csv_file:
        csv_reader = csv.reader(csv_file, delimiter=',')
        line_count = 0
        for row in csv_reader:
            if line_count == 0:
                dataNames = row
            else:
                data.append(row)
            line_count += 1
    selectedVariable = dataNames.index(inputVariabName)
    inputs = [float(data[i][selectedVariable]) for i in range(len(data))]
    selectedOutput = dataNames.index(outputVariabName)
    outputs = [float(data[i][selectedOutput]) for i in range(len(data))]

    return inputs, outputs

crtDir=os.getcwd()
fileP = os.path.join(crtDir, 'world_happiness.csv')

def gradientDescentBatch():
    inputs,outputs=loadData(fileP, 'Economy..GDP.per.Capita.', 'Happiness.Score')

    np.random.seed(5)
    indexes=[i for i in range(len(inputs))]
    trainSample=np.random.choice(indexes,int(0.8*len(inputs)),replace=False)
    testSample=[i for i in indexes if not i  in trainSample]

    trainInputs=[inputs[i] for i in trainSample]
    trainOutputs=[outputs[i] for i in trainSample]

    testInputs=[inputs[i] for i in testSample]
    testOutputs=[outputs[i] for i in testSample]

    xx=[[el] for el in trainInputs]

    model = make_pipeline(StandardScaler(),
                          SGDRegressor(max_iter=1000, tol=1e-3, eta0=0.01, learning_rate='constant', warm_start=True))

    batchSize = 32
    noOfBatches = len(trainInputs) // batchSize

    for epoch in range (100):
        for batch in range (noOfBatches):
            batchInputs = np.array(trainInputs[batch * batchSize: (batch + 1) * batchSize])
            batchOutputs = np.array(trainOutputs[batch * batchSize: (batch + 1) * batchSize])

            model.fit(batchInputs.reshape(-1, 1), batchOutputs)

    w0,w1=model.named_steps['sgdregressor'].intercept_[0],model.named_steps['sgdregressor'].coef_[0]
    print('the model is: f(x) = ',w0,' + ',w1,'*x')



    noOfPoints=1000
    xref=[]
    val=min(trainInputs)
    step=(max(trainInputs)-min(trainInputs))/noOfPoints
    for i in range(1,noOfPoints):
        xref.append(val)
        val+=step

    yref=[w0+w1*x for x in xref]


    computedTestOutputs=model.predict([[x] for x in testInputs])

    error=0.0
    for t1,t2 in zip(computedTestOutputs,testOutputs):
        error+=(t1-t2)**2
    error/=len(testOutputs)
    print('The mean square error(Tool) is: ',error)


def myGradientDescentBatch():
    inputs, outputs = loadData(fileP, 'Economy..GDP.per.Capita.', 'Happiness.Score')

    np.random.seed(5)
    indexes = [i for i in range(len(inputs))]
    trainSample = np.random.choice(indexes, int(0.8 * len(inputs)), replace=False)
    testSample = [i for i in indexes if not i in trainSample]

    trainInputs = [inputs[i] for i in trainSample]
    trainOutputs = [outputs[i] for i in trainSample]

    testInputs = [inputs[i] for i in testSample]
    testOutputs = [outputs[i] for i in testSample]

    model = MySGDRegression()

    batchSize = 32
    noOfBatches = len(trainInputs) // batchSize

    for epoch in range(100):
        for batch in range(noOfBatches):
            batchInputs = np.array(trainInputs[batch * batchSize: (batch + 1) * batchSize])
            batchOutputs = np.array(trainOutputs[batch * batchSize: (batch + 1) * batchSize])

            model.fit(batchInputs.reshape(-1, 1), batchOutputs)

    w0, w1 = model.bias, model.weights[0]
    print('the model is: f(x) = ', w0, ' + ', w1, '* x')

    noOfPoints = 1000
    xref = np.linspace(min(trainInputs), max(trainInputs), noOfPoints)
    yref = model.predict(xref.reshape(-1, 1))

    computedTestOutputs = model.predict(np.array(testInputs).reshape(-1, 1))

    error = np.mean((computedTestOutputs - testOutputs) ** 2)
    print('The mean square error(manual) is: ', error)



myGradientDescentBatch()



# gradientDescentBatch()