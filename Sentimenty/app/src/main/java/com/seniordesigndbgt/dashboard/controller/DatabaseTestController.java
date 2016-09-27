package com.seniordesigndbgt.dashboard.controller;

import com.seniordesigndbgt.dashboard.dao.*;
import com.seniordesigndbgt.dashboard.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalTime;
import java.util.List;

@Controller
public class DatabaseTestController {

    @Autowired
    private UserDAO _userDao;
    @Autowired
    private ViewDAO _viewDao;
    @Autowired
    private PressDAO _pressDAO;
    @Autowired
    private DailyStockDAO _dailyStockDao;
    @Autowired
    private TwitterDAO _twitterDao;
    @Autowired
    private StockHistoryDAO _stockHistoryDao;

    @RequestMapping("/newUser/{username}")
    @ResponseBody
    public String save(@PathVariable String username) {
        try {
            User user = new User(username);
            _userDao.save(user);
        } catch (Exception e) {
            return e.getMessage();
        }
        return "User saved successfully";
    }

    @RequestMapping("/show/{username}")
    @ResponseBody
    public String show(@PathVariable String username) {
        User user;
        List views = null;
        try {
            user = _userDao.getByUsername(username);
        } catch (Exception e) {
            return "User not found";
        }
        try {
            views = _viewDao.getByUser(user);
        } catch (Exception e) {

        }

        String size = "";

        if(views != null){
            size +=  "" + views.size();
        }

        return "username " + user.getUsername() + " DefaultView " + user.getDefaultView() + " views " + size;

    }

    @RequestMapping("/saveStock")
    @ResponseBody
    public String saveStock(){
        DailyStock test1 = new DailyStock("test 1", new Timestamp(System.currentTimeMillis()).toLocalDateTime(), 100.23);
        DailyStock test2 = new DailyStock("test 2", new Timestamp(System.currentTimeMillis()).toLocalDateTime(), 101.01);
        DailyStock test3 = new DailyStock("test 3", new Timestamp(System.currentTimeMillis()).toLocalDateTime(), 102.01);

        _dailyStockDao.save(test1);
        _dailyStockDao.save(test2);
        _dailyStockDao.save(test3);

        return "Stocks: " + test1.getValue() + " " + test2.getValue() + " " + test3.getValue();

    }

    @RequestMapping("/showStocks")
    @ResponseBody
    public String showStock(){
        List<DailyStock> allStocks = _dailyStockDao.getAll();
        List<StockHistory> allOldStocks = _stockHistoryDao.getAll();
        String r = "";
        for(DailyStock stock : allStocks) {
            r += "DAILY: "+stock.getSymbol() + " " + stock.getValue() + " " + stock.getTime() + "\n\n";
        }
        for (StockHistory oldStock : allOldStocks){
            r += "HISTORICAL: "+oldStock.getDateStock()+" "+oldStock.getClosePrice()+"\n\n";
        }
        return r;
    }

    @RequestMapping("/showTweets")
    @ResponseBody
    public String showTweets(){
        List<Twitter> allTweets = _twitterDao.getAll();
        String r = "";
        for(Twitter tweet : allTweets){
            r += tweet.getText();
        }
        return r;
    }

    @RequestMapping("/showPress")
    @ResponseBody
    public List showPress(){
        List<Press> presses = _pressDAO.getAll();
        return presses;
    }
}
