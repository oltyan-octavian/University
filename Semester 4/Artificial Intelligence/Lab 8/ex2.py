import markovify
import nltk
import random
import re
from nltk.corpus import wordnet
from nltk.sentiment import SentimentIntensityAnalyzer
from nltk.translate.bleu_score import sentence_bleu, SmoothingFunction
#nltk.download('wordnet')
#nltk.download('vader_lexicon')
#nltk.download('punkt')

#Citim text
with open('data/sonnets.txt', 'r') as file:
    text = file.read()

textModel = markovify.Text(text)
print('Text generat: ')
generatedText = ''
for i in range(4):
    generatedSentence = textModel.make_sentence()
    if generatedSentence is None:
        continue
    generatedText = generatedText + generatedSentence + ' '
    print(generatedSentence)


generatedSentences = re.split(r'(?<=[.?]) +', generatedText)

#Inlocuim cu sinonime
newSentencesUsingSynonyms = []
for generatedSentence in generatedSentences:
    generatedSentenceIntoWords = generatedSentence.split()
    for i in range(len(generatedSentenceIntoWords)):
        word = generatedSentenceIntoWords[i]
        synonyms = []
        for syn in wordnet.synsets(word):
            for lemma in syn.lemmas():
                synonyms.append(lemma.name())
        if synonyms:
            numberOfSynonyms = min(5, len(synonyms))
            selectedSynonyms = random.sample(synonyms, numberOfSynonyms)
            generatedSentenceIntoWords[i] = selectedSynonyms[0] 
    new_sentence = ' '.join(generatedSentenceIntoWords)
    newSentencesUsingSynonyms.append(new_sentence)

#Text cu sinonime
generatedTextWithSynonyms = '\n'.join(newSentencesUsingSynonyms)

print(f'\nText cu sinonime:\n{generatedTextWithSynonyms}')

#Sentiment
sentimentIntensityAnalyzer = SentimentIntensityAnalyzer()
sentiment = sentimentIntensityAnalyzer.polarity_scores(generatedTextWithSynonyms)
print(f'\nSentiment: {sentiment}')

#BLEU Score
def calculateBLEU(originalText, generatedText):
    originalSentences = nltk.sent_tokenize(originalText)
    generatedSentences = nltk.sent_tokenize(generatedText)
    BLEUScores = []
    smoothingFunction = SmoothingFunction()
    for sentence in generatedSentences:
        BLEUScore = max(sentence_bleu([originalSentence.split()], sentence.split(), smoothing_function=smoothingFunction.method1) for originalSentence in originalSentences)
        BLEUScores.append(BLEUScore)
    averageBLEU = sum(BLEUScores) / len(BLEUScores)
    return averageBLEU

bleu_score = calculateBLEU(text, generatedTextWithSynonyms)
print(f'BLEU Score: {bleu_score}')