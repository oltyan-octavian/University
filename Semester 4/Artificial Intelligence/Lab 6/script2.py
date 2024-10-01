import warnings; warnings.simplefilter('ignore')
import csv
import matplotlib.pyplot as plt
import numpy as np
import os
from SGD import MySGDRegression
from sklearn.linear_model import SGDRegressor
from sklearn.preprocessing import StandardScaler
from sklearn.pipeline import make_pipeline


def loadData(fileName, inputVariabName1,inputVariabName2, outputVariabName):
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
    selectedVariable1 = dataNames.index(inputVariabName1)
    inputs1 = [float(data[i][selectedVariable1]) for i in range(len(data))]
    selectedVariable2 = dataNames.index(inputVariabName2)
    inputs2 = [float(data[i][selectedVariable2]) for i in range(len(data))]
    selectedOutput = dataNames.index(outputVariabName)
    outputs = [float(data[i][selectedOutput]) for i in range(len(data))]

    return inputs1,inputs2, outputs

crtDir=os.getcwd()
fileP = os.path.join(crtDir, 'world.csv')

def gradientDescentBatchMultiple():
    inputs1, inputs2, outputs = loadData(fileP, 'Economy..GDP.per.Capita.', 'Freedom', 'Happiness.Score')

    np.random.seed(5)
    indexes = [i for i in range(len(inputs1))]
    trainSample = np.random.choice(indexes, int(0.8 * len(inputs1)), replace=False)
    testSample = [i for i in indexes if not i in trainSample]

    trainInputs1 = [inputs1[i] for i in trainSample]
    trainInputs2 = [inputs2[i] for i in trainSample]
    trainOutputs = [outputs[i] for i in trainSample]

    testInputs1 = [inputs1[i] for i in testSample]
    testInputs2 = [inputs2[i] for i in testSample]
    testOutputs = [outputs[i] for i in testSample]

    trainInputs = np.column_stack((trainInputs1, trainInputs2))
    testInputs = np.column_stack((testInputs1, testInputs2))

    model = make_pipeline(StandardScaler(), SGDRegressor(max_iter=1000, tol=1e-3, learning_rate='constant', eta0=0.01, warm_start=True))
    model.fit(trainInputs, trainOutputs)

    w0, w1, w2 = model.named_steps['sgdregressor'].intercept_[0], model.named_steps['sgdregressor'].coef_[0], model.named_steps['sgdregressor'].coef_[1]
    print("Modelul de regresie liniară multiplă: f(x1, x2) = ", w0, "+", w1, "* x1 +", w2, "* x2")

    predictions = model.predict(testInputs)

    mse = np.mean((predictions - testOutputs) ** 2)
    print("Mean Squared Error:", mse)


gradientDescentBatchMultiple()