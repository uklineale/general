package com.seniordesigndbgt.dashboard.analytics;

import com.seniordesigndbgt.dashboard.dao.PressDAO;
import com.seniordesigndbgt.dashboard.dao.TrendDAO;
import com.seniordesigndbgt.dashboard.dao.TwitterDAO;
import com.seniordesigndbgt.dashboard.model.Press;
import com.seniordesigndbgt.dashboard.model.Trend;
import com.seniordesigndbgt.dashboard.model.Twitter;
import org.springframework.beans.factory.annotation.Autowired;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;

import java.io.*;
import java.util.*;

public class TrendAnalyzer {

    public String algorithm = "x mentions in y time, if x > threshold";
    private static Map<String, Integer> frequencyMap;
    private List<Map.Entry<String,Integer>> trends;
    private static final int THRESHOLD = 2;
    //The number of keywords to get
    public static final int NUM_OF_KEYWORDS_TO_DISPLAY = 4;
    public static final int NUM_OF_KEYWORDS_TO_STORE = NUM_OF_KEYWORDS_TO_DISPLAY + 2;
    private static ArrayList<String> stopList;
    @Autowired
    private PressDAO _pressDao;
    @Autowired
    private TwitterDAO _twitterDao;
    @Autowired
    private TrendDAO _trendDao;



    public TrendAnalyzer() {
        this.frequencyMap = new LinkedHashMap<String, Integer>();
    }

    public void findNewTrends() {
        //System.out.println("\nStart");
    //Get press keywords
        List<Press> pressList = _pressDao.getAll();
        String allKeywords = "";
        for (Press article : pressList) {
            if (article.getKeywords() != null) {
                String[] articleKeywordSplit = article.getKeywords().split(",");
                allKeywords += article.getKeywords() + " ";
                for (String keyword : articleKeywordSplit) {
                    allKeywords += keyword + " ";
                }
            }
        }
        allKeywords = allKeywords.replace(",", " ");



        String keyString = findKeywords(allKeywords);
        System.out.println("all keywords: " + keyString);
        String[] keywordSplit = keyString.split(",");
        List<Trend> trends = new ArrayList<Trend>();
        for (String keyword : keywordSplit) {
            String mentions = "";
            for (Press article : pressList) {
                if (article.getKeywords() != null && article.getKeywords().contains(keyword)) {
                    mentions += article.getId() + ", ";
                }
            }
            trends.add(new Trend(keyword, mentions));
        }
        for (Trend trend : trends) {
            _trendDao.save(trend);
        }
        for (Trend databaseTrend : _trendDao.getAll()) {
            //System.out.println(databaseTrend.getMentions());
        }

    }
    /**
    * Finds the top constant number of keywords, returned as a comma separated string*/
    public String findKeywords(String text){
        String textClean = sanitizeInput(text);
        List<Map.Entry<String,Integer>> allWords = sortTrends(updateFrequencyMap(textClean,
                new LinkedHashMap<String, Integer>()));
        String[] allWordsArray = new String[allWords.size()];
        for (int i = 0; i < allWords.size(); i++){
            allWordsArray[i] = allWords.get(i).getKey();
        }

        InputStream modelIn;
        POSModel model = null;
        try {
                modelIn = new FileInputStream("en-pos-maxent.bin");
                model = new POSModel(modelIn);
            } catch (IOException e) {
                e.printStackTrace();
            }
        POSTaggerME tagger = new POSTaggerME(model);
        String[] tagged = tagger.tag(allWordsArray);
        List<Map.Entry<String,Integer>> nounsAndCounts = new LinkedList<Map.Entry<String, Integer>>();
        //Check if the words are nouns
        for (int i = 0; i < tagged.length; i++){
            if (tagged[i].equals("NN") || tagged[i].equals("NNS") ||
                        tagged[i].equals("NNP") || tagged[i].equals("NNPS")){
                nounsAndCounts.add(allWords.get(i));
            }
        }
        List<String> keyWords = new LinkedList<String>();
        for (int i = 0; i < NUM_OF_KEYWORDS_TO_STORE; i++){
            if ( i < nounsAndCounts.size())
                keyWords.add(nounsAndCounts.get(i).getKey());
        }
        String keyWordsString = "";
        for (String word : keyWords) {
            keyWordsString += word;
            keyWordsString += ",";
        }
        keyWordsString = keyWordsString.substring(0,keyWordsString.length()-1);
        return keyWordsString;
    }

    /* Put data into a frequency map,*/
    public Map<String, Integer> updateFrequencyMap(String text, Map<String,Integer> map){
        //Sanitize input
        text = sanitizeInput(text);
        //Basic tokenization, only separates on space
        String[] splitArray = text.split(" ");
        //If the word is in the map, increment its count
        //If not, add it to the map and give it a count of 1
        for (String currWord : splitArray) {
            if (map.containsKey(currWord)) {
                int currCount = map.get(currWord);
                map.put(currWord, currCount + 1);
            } else {
                map.put(currWord, 1);
            }
        }
        return map;

    }
    /**
     * Gets rid of punctuation and articles
     * List of articles is incomplete*/
    public String sanitizeInput(String text){
        text = text.toLowerCase();
        populateStopList();
        for (int i = 0; i < stopList.size(); i++){
            //Replace all stop words with a space to maintain separation
            text = text.replaceAll("[^a-zA-Z]"+stopList.get(i)+"[^a-zA-Z]", " ");//Remove all stop words
            text = text.replaceAll(" {2,}", " ");//Replace all instances of multiple spaces to single space
        }
        return text;
    }

    /*Takes data from stoplist.txt and puts it in stoplist
    * A stoplist is a list of words to remove from text before processing it
    * As in, removing common words like "a" or "the"
    * Stoplist is usually null, since we're starting the application repeatedly*/
    public void populateStopList(){
        try {
            Scanner scanner = new Scanner(new File("stoplist.txt"));
            if (stopList == null) {
                stopList = new ArrayList<String>();
                while (scanner.hasNextLine()) {
                    stopList.add(scanner.nextLine());
                }
            } else {
                //Update the stop list if word in txt file isn't found in list
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    if (!stopList.contains(line)){
                        stopList.add(line);
                    }
                }
            }
            scanner.close();
        }  catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /*
    * Takes map data, sorts it into List, returns List
    * */
    private static <K, V extends Comparable<? super V>> List<Map.Entry<K, V>> sortTrends(Map<K,V> map) {
        List<Map.Entry<K, V>> list = new LinkedList<Map.Entry<K, V>>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<K, V>>() {
            public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });
        return list;
    }
}
