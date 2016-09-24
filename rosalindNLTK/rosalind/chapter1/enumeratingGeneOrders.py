import itertools
#f = open('enumeratingGeneOrders.txt','w')
length = 6
perms = itertools.permutations(range(1,length+1))
listOfPerms =[]
for perm in perms:
	listOfPerms.append(perm)

#f.write(len(listOfPerms))
print len(listOfPerms)
for perm in listOfPerms:
#	f.write(perm)
	print perm
