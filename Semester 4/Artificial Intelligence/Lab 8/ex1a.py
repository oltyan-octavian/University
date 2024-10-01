import random


def markovChain(text):
    allWords = text.split(' ')
    dict = {}

    for i in range(1, len(allWords)):
        if allWords[i-1] not in dict:
            dict[allWords[i-1]] = [allWords[i]]
        else:
            dict[allWords[i-1]].append(allWords[i])

    return dict


def generate(currentChain, count=20):
    firstWord = random.choice(list(currentChain.keys()))
    generatedSentence = firstWord.capitalize()

    for i in range(count-1):
        secondWord = random.choice(currentChain[firstWord])
        firstWord = secondWord
        generatedSentence += ' ' + secondWord

    generatedSentence += '.'
    return generatedSentence


with open('data/proverbe.txt', 'r', encoding='utf-8') as textFile:
    text = textFile.read().replace('\n', ' ')
    chainFromText = markovChain(text)
    generatedSentence = generate(chainFromText)
    print(generatedSentence)