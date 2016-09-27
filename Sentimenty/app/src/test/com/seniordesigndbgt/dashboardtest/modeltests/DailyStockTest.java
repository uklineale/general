package com.seniordesigndbgt.dashboardtest.modeltests;

import org.junit.Test;
import org.junit.*;
import static org.junit.Assert.*;
import com.seniordesigndbgt.dashboard.model.DailyStock;

import java.time.*;

import java.time.format.DateTimeFormatter;
import java.util.List;
/**
 * Created by kamehardy on 3/27/16.
 */
public class DailyStockTest {


    @Test
    public void testGetSymbol() throws Exception {
        String str = "XYN";
        DailyStock stock = new DailyStock();
        assertNull( stock.getSymbol());
        stock.setSymbol(str);
        assertEquals(str, stock.getSymbol());
        stock = new DailyStock("potato", LocalDateTime.of(LocalDate.now(), LocalTime.now()), 1);
    }

    @Test
    public void testGetTime() throws Exception {
        DailyStock stock = new DailyStock();
        assertNull(stock.getTime());
        LocalDateTime today = LocalDateTime.of(LocalDate.now(), LocalTime.now());
        stock.setTime(today);
        assertEquals(today, stock.getTime());

    }

    @Test
    public void testGetValue() throws Exception {
        DailyStock stock = new DailyStock();
        assertEquals(0.0, stock.getValue(), 0.00);
        double val = 18.40;
        stock.setValue(val);
        assertEquals(val, stock.getValue(), 0.0);
    }

    @Test
    public void testConvertTime() throws Exception {
        DailyStock stock = new DailyStock();
        assertNull(stock.getTime());
        LocalDateTime today = LocalDateTime.of(LocalDate.now(), LocalTime.now());
        stock.setTime(today);
        assertEquals(today, stock.getTime());

        assertEquals(today.format(DateTimeFormatter.ofPattern("MM/dd/yyyy")), stock.convertTimetoHistoricalDate());
    }

}