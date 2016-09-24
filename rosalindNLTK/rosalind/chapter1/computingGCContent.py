import nltk as nltk


def computeGCContent(dna):
	percentG = 100 * dna.count('G') / float(len(dna))
	percentC = 100 * dna.count('C') / float(len(dna))
	return percentG+percentC

print "Start!"
fileO = open('rosalind_gc.txt', 'r')
lines = fileO.readlines()
strings = {}
dnaString = ''
idString =''
for line in lines:
	if line[0] == '>':
		print "Loop!"
		if idString != '':
			print dnaString
			strings[idString] = computeGCContent(dnaString)
		dnaString = ''
		idString = line[1:]
		strings[idString]=0
	else:
		dnaString += line
else:
	print strings
