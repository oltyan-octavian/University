import random


def markovChain(text, stateSize):
    allWords = text.split(' ')
    dict = {}

    for i in range(stateSize, len(allWords)):
        key = tuple(allWords[i-stateSize:i])
        if key not in dict:
            dict[key] = [allWords[i]]
        else:
            dict[key].append(allWords[i])

    return dict


def generateSentence(chain, stateSize, count=20):
    key = random.choice(list(chain.keys()))
    generatedSentence = ' '.join(key).capitalize()

    for i in range(count-stateSize):
        word = random.choice(chain[key])
        key = tuple((list(key)[1:] + [word])[-stateSize:])
        generatedSentence += ' ' + word

    generatedSentence += '.'
    return generatedSentence


with open('data/proverbe.txt', 'r', encoding='utf-8') as file:
    text = file.read().replace('\n', ' ')
    stateSize = 2
    chainFromText = markovChain(text, stateSize)
    generatedSentence = generateSentence(chainFromText, stateSize)
    print(generatedSentence)