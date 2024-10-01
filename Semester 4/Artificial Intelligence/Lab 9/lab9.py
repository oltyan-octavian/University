import csv
import os


# load some data
crtDir =  os.getcwd()
fileName = os.path.join(crtDir, 'data', 'reviews_mixed.csv')

data = []
with open(fileName) as csv_file:
    csv_reader = csv.reader(csv_file, delimiter=',')
    line_count = 0
    for row in csv_reader:
        if line_count == 0:
            dataNames = row
        else:
            data.append(row)
        line_count += 1

inputs = [data[i][0] for i in range(len(data))][:100]
outputs = [data[i][1] for i in range(len(data))][:100]
labelNames = list(set(outputs))

#print(inputs[:2])
#print(labelNames[:2])

# prepare data for training and testing

import numpy as np

np.random.seed(5)
# noSamples = inputs.shape[0]
noSamples = len(inputs)
indexes = [i for i in range(noSamples)]
trainSample = np.random.choice(indexes, int(0.8 * noSamples), replace = False)
testSample = [i for i in indexes  if not i in trainSample]

trainInputs = [inputs[i] for i in trainSample]
trainOutputs = [outputs[i] for i in trainSample]
testInputs = [inputs[i] for i in testSample]
testOutputs = [outputs[i] for i in testSample]

#print(trainInputs[:3])

# extract some features from the raw text

# # representation 1: Bag of Words
from sklearn.feature_extraction.text import CountVectorizer
vectorizer = CountVectorizer(max_features=50)

trainFeatures = vectorizer.fit_transform(trainInputs)
testFeatures = vectorizer.transform(testInputs)

# vocabulary size
print("BAG OF WORDS")
print("vocab size: ", len(vectorizer.vocabulary_),  " words")
# no of emails (Samples)
print("traindata size: ", len(trainInputs), " emails")
# shape of feature matrix
print("trainFeatures shape: ", trainFeatures.shape)

# vocabbulary from the train data 
print('some words of the vocab: ', vectorizer.get_feature_names_out()[-20:])
# extracted features
print('some features: ', trainFeatures.toarray()[:3])



# unsupervised classification ( = clustering) of data

from sklearn.cluster import KMeans

unsupervisedClassifier = KMeans(n_clusters=2, random_state=0)
unsupervisedClassifier.fit(trainFeatures)

computedTestIndexes = unsupervisedClassifier.predict(testFeatures)
computedTestOutputs = [labelNames[value] for value in computedTestIndexes]
for i in range(0, len(testInputs)):
    print(testInputs[i], " -> ", computedTestOutputs[i])


from sklearn.metrics import accuracy_score

# just supposing that we have the true labels
print("acc: ", accuracy_score(testOutputs, computedTestOutputs))



# [START analyze_sentiment]
import os
from azure.core.credentials import AzureKeyCredential
from azure.ai.textanalytics import TextAnalyticsClient

endpoint = "https://ai-oltyan-octavian-language.cognitiveservices.azure.com/"
key = "8736cc2bdcad40d4be76375fdaa97a51"

client = TextAnalyticsClient(endpoint=endpoint, credential=AzureKeyCredential(key))

text = ["By choosing a bike over a car, I’m reducing my environmental footprint. Cycling promotes eco-friendly transportation, and I’m proud to be part of that movement."]

result = client.analyze_sentiment(text, show_opinion_mining=True)
docs = [doc for doc in result if not doc.is_error]

print("Let's visualize the sentiment of each of these reviews")
for idx, doc in enumerate(docs):
    print(f"Document text: {text[idx]}")
    print(f"Overall sentiment: {doc.sentiment}")