package com.seniordesigndbgt.dashboard.scheduler;

import com.seniordesigndbgt.dashboard.action.PressAction;
import com.seniordesigndbgt.dashboard.analytics.AnalyzerFactory;
import com.seniordesigndbgt.dashboard.analytics.TrendAnalyzer;
import com.seniordesigndbgt.dashboard.dao.PressDAO;
import com.seniordesigndbgt.dashboard.model.Press;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.seniordesigndbgt.dashboard.analytics.SentimentAnalyzer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

@Component
public class PressSchedule {

    @Autowired
    private PressDAO _pressDao;

    private SentimentAnalyzer analyzer;

    TrendAnalyzer ta = new TrendAnalyzer();

    private static final int RATE = 1000000;

    @Scheduled(fixedRate = RATE)
    public void checkBloomberg() throws IOException {
        Document doc = Jsoup.connect("http://www.bloomberg.com/search?query=deutsche+bank").get();
        Elements newsHeadlines = doc.select(".search-result-story");
        analyzer = AnalyzerFactory.getSentimentAnalyzer();
        for(Element e : newsHeadlines) {
            String timestamp = e.child(0).select(".metadata-timestamp").first().child(0).attr("datetime");
            //System.out.println(timestamp); 2016-03-31T11:00:48+00:00
            String a[] = timestamp.split("T");
            String a2[] = a[1].split("\\+");
            timestamp = a[0] + " " + a2[0];
            String thumbnail;
            //2016-04-02 21:52:49
            //System.out.println(timestamp);
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd k:m:s", Locale.ENGLISH);
            Date thistime = new Date();
            try {
                thistime = format.parse(timestamp);
            } catch (ParseException e1) {
                e1.printStackTrace();
            }
            //System.out.println(thistime);
            String link = e.select(".search-result-story__headline").first().child(0).attr("href");
            try {
                thumbnail = e.select(".search-result-story__thumbnail__image").first().attr("src");
            } catch ( NullPointerException ex) {
                thumbnail = "http://assets.bwbx.io/business/public/images/logos/BB-Logo-H1-c318357b96.svg";
            }
            if(!link.contains("www")) {
                link = "http://www.bloomberg.com/" + link;
            }
            String title = e.select(".search-result-story__headline").first().text();
            try {
                Timestamp time = new Timestamp(System.currentTimeMillis());
                Press article = new Press("Bloomberg", link, title, thistime, thumbnail);
                _pressDao.save(article);
                String sent = analyzer.getSentiment(article);
                article.setSentiment(sent);
                String bodyContent = PressAction.getBodyContent(article);
                article.setBody(bodyContent.substring(0, 150));
                article.setKeywords(ta.findKeywords(bodyContent).toString());
                _pressDao.update(article);
            }catch(Exception error){
                System.out.println(error.toString());
            }
        }
    }

//    @Scheduled(fixedRate = RATE)
//    public void checkForbes() throws IOException {
//        Document doc = Jsoup.connect("http://www.forbes.com/search/?q=deutsche+bank").get();
//        Elements newsHeadlines = doc.select(".article article h2");
//        analyzer = SentimentAnalyzer.getInstance();
//        for(Element e : newsHeadlines) {
//            String link = e.child(0).attr("href");
//            String title = e.child(0).text();
//            System.out.println(link + "/////"  + title);
//            try {
//                Press article = new Press("Forbes", link, title);
//                _pressDao.save(article);
//                String sent = analyzer.getSentiment(article);
//                article.setSentiment(sent);
//                _pressDao.update(article);
//            }catch(Exception error){
//
//            }
//        }
//    }

//
//    @Scheduled(fixedRate = RATE)
//    public void checkNyt() throws IOException {
//        Document doc = Jsoup.connect("http://query.nytimes.com/search/sitesearch/?pgtype=Homepage#/deutsche bank").get();
//        Elements newsHeadlines = doc.select(".story");
//        analyzer = SentimentAnalyzer.getInstance();
//        for(Element e : newsHeadlines) {
//            System.out.println(e);
//            String link = e.child(0).attr("href");
//            link = "http://www.nytimes.com/" + link;
//            String title = e.text();
//            System.out.println(link + "     " + title);
//            try {
//                Press article = new Press("NY Times", link, title);
//                _pressDao.save(article);
//                article.setSentiment(analyzer.getSentiment(article));
//                _pressDao.update(article);
//            }catch(Exception error){
//
//            }
//        }
//    }
//
    @Scheduled(fixedRate = RATE)
    public void checkReuters() throws IOException {
        Document doc = Jsoup.connect("http://www.reuters.com/search/news?blob=deutsche+bank").get();
        Elements newsHeadlines = doc.select(".search-result-content");
        analyzer = AnalyzerFactory.getSentimentAnalyzer();
        for(Element e : newsHeadlines) {
            //System.out.println(e);
            Element t = e.getElementsByClass("search-result-timestamp").remove(0);
            String timestamp = t.text();
            //time April 08, 2016 02:56pm EDT
            DateFormat format = new SimpleDateFormat("MMMM dd, yyyy h:mma z", Locale.ENGLISH);
            Date thisTime = new Date();
            try {
                thisTime = format.parse(timestamp);
            } catch (ParseException e1) {
                e1.printStackTrace();
            }
            e = e.child(0);
            String link = e.child(0).attr("href");
            String title = e.text();
            //String timestamp = "March 22, 2016 04:57am EDT";
            //String thumbnail = "http://www.thewrap.com/wp-content/uploads/2013/10/Reuters-Logo.jpg";
            String thumbnail = "http://fontslogo.com/wp-content/uploads/2013/02/Reuters-Logo-Font.jpg";
            try {
                Timestamp time = new Timestamp(System.currentTimeMillis());
                Press article = new Press("Reuters", link, title, thisTime, thumbnail);
                _pressDao.save(article);
                article.setSentiment(analyzer.getSentiment(article));
                String bodyContent = PressAction.getBodyContent(article);
                article.setBody(bodyContent.substring(0, 150));
                article.setKeywords(ta.findKeywords(bodyContent).toString());
                _pressDao.update(article);
            }catch(Exception error){
                System.out.println(error);
            }
        }
    }

}
