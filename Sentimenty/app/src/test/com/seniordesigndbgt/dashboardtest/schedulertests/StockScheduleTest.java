package com.seniordesigndbgt.dashboardtest.schedulertests;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.seniordesigndbgt.dashboard.model.DailyStock;
import org.junit.Test;

import java.sql.Timestamp;

import static org.junit.Assert.*;

public class StockScheduleTest {

    @Test
    public void testGetCurrentPrice() throws Exception {
            HttpResponse<String> response = Unirest.get("http://dev.markitondemand.com/MODApis/Api/v2/Quote/jsonp?symbol=DB&callback=cb").asString();
            String r = response.getBody();
            r = r.substring(3, r.length()-1);

            String jsonString = r;
            System.out.println(r);
            //Walk through query to get to quote data
            JsonElement jElement = new JsonParser().parse(jsonString);
            JsonObject jObject = jElement.getAsJsonObject();
            //Get selected quote data
            JsonPrimitive priceOnly = jObject.getAsJsonPrimitive("LastPrice");
            double price = priceOnly.getAsDouble();
            String symbol = "DB";

            System.out.println(price);
            System.out.println(symbol);

            Timestamp time = new Timestamp(System.currentTimeMillis());
            DailyStock result = new DailyStock(symbol, time.toLocalDateTime(), price);
    }
}