package com.seniordesigndbgt.dashboardtest.actiontests;

import com.seniordesigndbgt.dashboard.action.PressAction;
import com.seniordesigndbgt.dashboard.analytics.TrendAnalyzer;
import com.seniordesigndbgt.dashboard.model.Press;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by thomaspatterson on 2/28/16.
 */
public class PressActionTest {

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testGetBodyContent() throws Exception {
        Timestamp time = new Timestamp(System.currentTimeMillis());
        Press article = new Press();
        assertNull(PressAction.getBodyContent(article));
        article = new Press("Reuters", "http://www.reuters.com/article/us-deutsche-bank-bafin-idUSKCN0VY2O4", "German regulator ends Deutsche Bank probes over fixing scandals",  Calendar.getInstance().getTime(),"");
        String body = PressAction.getBodyContent(article);
        assertTrue("body is there", body.length() > 100);
        article = new Press("Reuters", "http://www.reuters.com/article/us-deutsche-bank-bafin-idUSKCN0VY2O4", "German regulator ends Deutsche Bank probes over fixing scandals",  Calendar.getInstance().getTime(),"");
    }

}