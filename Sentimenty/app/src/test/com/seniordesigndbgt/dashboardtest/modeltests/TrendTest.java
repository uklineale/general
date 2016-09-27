package com.seniordesigndbgt.dashboardtest.modeltests;


import org.junit.Test;
import com.seniordesigndbgt.dashboard.model.Trend;
import static org.junit.Assert.*;

/**
 * Created by kamehardy on 3/27/16.
 */
public class TrendTest {


    @Test
    public void testGetId() throws Exception {
        Trend trend = new Trend();
        Trend preset = new Trend("potato", "hot,fire");
        trend.setId(100);
        assertEquals(100, trend.getId());
        assertEquals(0, preset.getId());
    }


    @Test
    public void testGetTrendTitle() throws Exception {
        Trend trend = new Trend();
        Trend preset = new Trend("potato", "hot,fire");
        trend.setTrendTitle("Positive trend");
        assertEquals("Positive trend", trend.getTrendTitle());
        assertEquals("potato", preset.getTrendTitle());
    }


    @Test
    public void testGetMentions() throws Exception {
        Trend trend = new Trend();
        Trend preset = new Trend("potato", "hot,fire");
        trend.setMentions("Kel liked this");
        assertEquals("Kel liked this", trend.getMentions());
        assertEquals("hot,fire", preset.getMentions());
    }

}