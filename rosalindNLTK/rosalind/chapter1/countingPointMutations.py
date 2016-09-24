f = open('chapter1/countingPointMutations.txt','r')

string1 = f.readline()
string2 = f.readline()
#string1 = "AAAAAB"
#string2 = "AAAAAA"
hammingDistance = 0

for x in range(0, len(string1)):
	if (string1[x] != string2[x]):
		hammingDistance += 1

print hammingDistance
