import nltk
from nltk.corpus import gutenberg, brown
#print gutenberg.fileids()
whitman = nltk.Text(gutenberg.words('whitman-leaves.txt'))
milton = gutenberg.sents('milton-paradise.txt')
