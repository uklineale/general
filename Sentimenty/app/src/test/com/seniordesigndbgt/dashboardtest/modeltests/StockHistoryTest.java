package com.seniordesigndbgt.dashboardtest.modeltests;


import org.junit.Test;

import static org.junit.Assert.*;
import com.seniordesigndbgt.dashboard.model.StockHistory;

/**
 * Created by kamehardy on 3/27/16.
 */
public class StockHistoryTest {

    @Test
    public void testGetDateStock() throws Exception {
        String str = "2016-03-27";
        StockHistory history = new StockHistory();
        StockHistory preset = new StockHistory("today", 1.00);
        history.setDateStock(str);
        assertEquals(str, history.getDateStock());
        assertEquals("today", preset.getDateStock());
    }

    @Test
    public void testGetClosePrice() throws Exception {
        double val = 18.30;
        StockHistory his = new StockHistory();
        StockHistory preset = new StockHistory("today", 1.00);
        his.setClosePrice(val);
        assertEquals(val, his.getClosePrice(), 0.0);
        assertEquals(1.00, preset.getClosePrice(), 0.00);
    }

}