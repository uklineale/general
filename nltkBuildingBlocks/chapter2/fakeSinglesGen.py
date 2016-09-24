import nltk
from nltk.corpus import webtext
#Remember, this is for funz, but later make a wine recommender - either pick new wine or learn what you like ( I like these wines -> you have this taste preference
#generate a mix of presidential addresses and genesis - corpus.state_union && corpus.genesis
srcLen = webtext.raw('singles.txt')

print 'Length of sezzy shingles waiting for you: {0}'.format(srcLen) 

