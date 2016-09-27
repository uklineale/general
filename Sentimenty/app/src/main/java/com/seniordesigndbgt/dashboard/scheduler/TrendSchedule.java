package com.seniordesigndbgt.dashboard.scheduler;


import com.seniordesigndbgt.dashboard.analytics.AnalyzerFactory;
import com.seniordesigndbgt.dashboard.analytics.TrendAnalyzer;
import com.seniordesigndbgt.dashboard.dao.PressDAO;
import com.seniordesigndbgt.dashboard.dao.TrendDAO;
import com.seniordesigndbgt.dashboard.dao.TwitterDAO;
import com.seniordesigndbgt.dashboard.model.Press;
import com.seniordesigndbgt.dashboard.model.Trend;
import com.seniordesigndbgt.dashboard.model.Twitter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class TrendSchedule {

    @Autowired
    private PressDAO _pressDao;
    @Autowired
    private TwitterDAO _twitterDao;
    @Autowired
    private TrendDAO _trendDao;
    TrendAnalyzer ta = new TrendAnalyzer();
    private static final int RATE = 10000;

    @Scheduled(fixedDelay = RATE)
    public void getTrends() {
        //Get all keywords from pressDao
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

        //Get the keywords from all keywords
        allKeywords = allKeywords.replace(",", " ");
        String keyString = ta.findKeywords(allKeywords);
        String[] keywordSplit = keyString.split(",");
        List<Trend> trends = new ArrayList<Trend>();
        for (String keyword : keywordSplit) {
            if (keyword != null && !keyword.isEmpty()) {
                String mentions = "";
                for (Press article : pressList) {
                    if (article.getKeywords() != null && article.getKeywords().contains(keyword)) {
                        mentions += article.getId() + ", ";
                    }
                }
                _trendDao.save(new Trend(keyword, mentions));
            }
        }
    }
}
