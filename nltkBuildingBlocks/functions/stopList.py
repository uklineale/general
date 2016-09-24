from nltk.corpus import stopwords

tokens = get_tokens()
filtered = [w for w in tokens if not w in stopwords.words('english')]
count = Counter(filtered)
print count.most_common(100)
