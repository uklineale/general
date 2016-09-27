package com.seniordesigndbgt.dashboardtest.analyticstests;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.seniordesigndbgt.dashboard.model.Press;
import org.aspectj.runtime.reflect.Factory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.omg.CORBA.DoubleHolder;
import com.seniordesigndbgt.dashboard.analytics.SentimentAnalyzer;
import com.seniordesigndbgt.dashboard.analytics.AnalyzerFactory;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Calendar;

import static org.junit.Assert.*;

public class SentimentAnalyzerTest {
    private SentimentAnalyzer analyzer;
    @Before
    public void setUp() throws Exception {
        analyzer = AnalyzerFactory.getSentimentAnalyzer();
    }

    @After
    public void tearDown() throws Exception {

    }

    //testing for strings
    @Test
    public void testGetSentimentText() throws Exception {
        String testNeg = "I hate potatoes. They are the worst. Not even ketchup will save them";//This will be used to test for negative responses
        String testPos = "This is so awesome! I love tater tots. They are so amazing with honey mustard";//this will be used to test for positive responses
        String negRes = analyzer.getSentiment(testNeg);
        String posRes = analyzer.getSentiment(testPos);
        JsonElement jElementNeg = new JsonParser().parse(negRes);
        JsonElement jElementPos = new JsonParser().parse(posRes);
        JsonObject jObject = jElementNeg.getAsJsonObject();
        JsonPrimitive jPrimitive = jObject.getAsJsonPrimitive("score");
        Double n = jPrimitive.getAsDouble();
        assertTrue("n between -1 and 0", n < 0.00 && n > -1.00);
        jObject = jElementPos.getAsJsonObject();
        jPrimitive = jObject.getAsJsonPrimitive("score");
        n = jPrimitive.getAsDouble();
        assertTrue("n between -1 and 1", n > 0.00 && n < 1.00);

        //Test for empyt string and string with only spaces returning null
        //This should be caused to reduce use of the api calls
        assertNull(analyzer.getSentiment(" "));
        assertNull(analyzer.getSentiment(""));
    }

    //testing for articles
    @Test
    public void testGetSentimentArticle() throws Exception {
        Timestamp time = new Timestamp(System.currentTimeMillis());
        Press badArticle = new Press("badArticle", "http://www.bloomberg.com/news/articles/2016-02-25/ex-deutsche-bank-trader-zhou-admitted-to-mismarking-cmbs-trades", "The Big Bad Wolf",  Calendar.getInstance().getTime(),"");
        Press goodArticle = new Press("goodArticle", "http://www.bloomberg.com/news/audio/2016-02-23/silverstein-balance-sheets-are-best-drivers-of-performance-now", "Fluffy Bunnies",  Calendar.getInstance().getTime(), "");
        String negRes = analyzer.getSentiment(badArticle);
        String posRes = analyzer.getSentiment(goodArticle);
        JsonElement jElementNeg = new JsonParser().parse(negRes);
        JsonElement jElementPos = new JsonParser().parse(posRes);
        JsonObject jObject = jElementNeg.getAsJsonObject();
        JsonPrimitive jPrimitive = jObject.getAsJsonPrimitive("score");
        Double n = jPrimitive.getAsDouble();
        assertTrue("n between -1 and 0", n < 0.00 && n > -1.00);
        jObject = jElementPos.getAsJsonObject();
        jPrimitive = jObject.getAsJsonPrimitive("score");
        n = jPrimitive.getAsDouble();
        assertTrue("n between -1 and 1", n > 0.00 && n < 1.00);
    }
}
