package com.seniordesigndbgt.dashboardtest.analyticstests;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.seniordesigndbgt.dashboard.action.PressAction;
import com.seniordesigndbgt.dashboard.analytics.TrendAnalyzer;
import com.seniordesigndbgt.dashboard.model.Press;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.omg.CORBA.DoubleHolder;
import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;

public class TrendAnalyzerTest {

    TrendAnalyzer ta = new TrendAnalyzer();

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testFindKeywords() throws Exception {
//        Press article = new Press("Reuters", "http://www.reuters.com/article/us-deutsche-bank-bafin-idUSKCN0VY2O4", "German regulator ends Deutsche Bank probes over fixing scandals");
//        String body = PressAction.getBodyContent(article);
//        String keywords = ta.findKeywords(body);
//        System.out.println(keywords);
//        assertTrue("size", keywords.size() >= 0);
    }
}
