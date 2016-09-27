package com.seniordesigndbgt.dashboard.scheduler;

import com.google.gson.*;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.seniordesigndbgt.dashboard.dao.DailyStockDAO;
import com.seniordesigndbgt.dashboard.dao.StockHistoryDAO;
import com.seniordesigndbgt.dashboard.model.DailyStock;
import com.seniordesigndbgt.dashboard.model.StockHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Timestamp;

@Component
public class StockSchedule {

    @Autowired
    private DailyStockDAO _dailyStockDao;
    @Autowired
    private StockHistoryDAO _stockHistoryDao;

    //@Scheduled(cron = "0/5 9-16 * * MON-FRI")
    @Scheduled(fixedDelay = 120000)
    public void getCurrentPrice() {
        DailyStock result = null;
        try {
            HttpResponse<String> response = Unirest.get("http://dev.markitondemand.com/MODApis/Api/v2/Quote/jsonp?symbol=DB&callback=cb").asString();
            String r = response.getBody();
            r = r.substring(3, r.length()-1);

            String jsonString = r;
            //Walk through query to get to quote data
            JsonElement jElement = new JsonParser().parse(jsonString);
            JsonObject jObject = jElement.getAsJsonObject();
            //Get selected quote data
            JsonPrimitive priceOnly = jObject.getAsJsonPrimitive("LastPrice");
            double price = priceOnly.getAsDouble();
            String symbol = "DB";

            Timestamp time = new Timestamp(System.currentTimeMillis());
            result = new DailyStock(symbol, time.toLocalDateTime(), price);

            _dailyStockDao.save(result);

        } catch (UnirestException e) {
            e.printStackTrace();
        }
    }
    /*
     * Populate historical stock data
     */
    @PostConstruct
    public void backfillStocks(){
        if (_stockHistoryDao.getAll().size() == 0) {
            String filename = "dbOldStock.csv";
            BufferedReader br = null;
            String line = "";
            String splitBy = ",";
            try {
                br = new BufferedReader(new FileReader(filename));
                while ((line = br.readLine()) != null) {
                    String[] stock = line.split(splitBy);
                    //System.out.println( stock[0] + stock[4]);
                    StockHistory s = new StockHistory(stock[0], Double.parseDouble(stock[4]));
                    _stockHistoryDao.save(s);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException f) {
                f.printStackTrace();
            }
        }
    }
    /*
    * Get the last trade price of the day and add it to the historical data table
    * */
    //@Scheduled(cron = "0 1 17 * * MON-FRI")
//    @Scheduled(fixedDelay = 5000)
    public void updateHistoricalDatabase() {
        DailyStock ds = _dailyStockDao.getLatest();
//        System.out.println(ds.convertTimetoHistoricalDate());
        _stockHistoryDao.save(new StockHistory(ds.convertTimetoHistoricalDate(), ds.getValue()));
//        System.out.println("Saved todays close price as historical data");

    }
    /*
     * Clears daily stock table so it can be repopulated with new data
     */
    //@Scheduled(cron = "0 59 8 * * MON-FRI")
    public void clearDailyDatabase(){
        _dailyStockDao.clearDaily();
    }
}
