package com.seniordesigndbgt.dashboard.analytics;

import java.awt.List;

/**
 * Created by hammer on 2/24/2016.
 */
public class AnalyzerFactory {
    static List sentimentQue;

    public static SentimentAnalyzer getSentimentAnalyzer() {
        return new SentimentAnalyzer(sentimentQue);
    }
}
