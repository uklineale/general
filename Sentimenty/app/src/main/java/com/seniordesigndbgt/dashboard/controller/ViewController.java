package com.seniordesigndbgt.dashboard.controller;

import com.seniordesigndbgt.dashboard.dao.UserDAO;
import com.seniordesigndbgt.dashboard.dao.ViewDAO;
import com.seniordesigndbgt.dashboard.model.Module;
import org.apache.tomcat.util.digester.ArrayStack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import com.seniordesigndbgt.dashboard.model.User;
import com.seniordesigndbgt.dashboard.model.View;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ViewController {

    @Autowired
    private UserDAO _userDAO;
    @Autowired
    private ViewDAO _viewDAO;

    @RequestMapping("/{username}")
    public String loadHome(ModelMap modelMap) {
        String username = "TODO";
        System.out.println("username " + username);
        User current = new User(username, "employee");
        ArrayList<Module> columnOne = new ArrayList<Module>();
        ArrayList<Module> columnTwo = new ArrayList<Module>();
        ArrayList<Module> columnThree = new ArrayList<Module>();
        Module stockModule = new Module("Stocks from module", "stock", "fragments/stock");
        Module trendModule = new Module("Trends from module", "trends", "fragments/stock");
        Module geoModule = new Module("Geo from module", "geo", "fragments/stock");
        Module sentiment = new Module("Sentiment from moudule", "sentiment", "fragments/stock");
        Module press = new Module ("Press from module", "press", "fragments/stock");
        Module earnings = new Module ("Earnings from module", "earnings", "fragments/stock");
        columnOne.add(stockModule);
        columnOne.add(sentiment);
        columnOne.add(press);
        columnTwo.add(trendModule);
        columnThree.add(geoModule);
        columnThree.add(earnings);
        View preferred = new View(current, current.getDefaultView(), columnOne, columnTwo, columnThree);
        modelMap.put("view", preferred);
        return "index";
    }

    @RequestMapping("/")
    public String loadHome(@CookieValue(value = "layout", required = false) Object layout, ModelMap modelMap) {
        System.out.println(layout);
        String username = "tcpatter";
        User current = new User(username, "employee");
        //_userDAO.save(current);
        ArrayList<Module> columnOne = new ArrayList<Module>();
        ArrayList<Module> columnTwo = new ArrayList<Module>();
        ArrayList<Module> columnThree = new ArrayList<Module>();
        Module stockModule = new Module("DB Stock Information", "stock");
        Module totalMentionModule = new Module("Mentions Today", "mentions");
        Module trendModule = new Module("Trending Topics", "trends");
        Module gaugeModule = new Module("Current Sentiment", "gauge");
        Module percentSentimentModule = new Module("Percent Makeup of Sentiment", "percent");
        Module twitterModule = new Module("Twitter Feed", "twitter");
        columnOne.add(stockModule);
        columnTwo.add(trendModule);
        //columnTwo.add(totalMentionModule);
        columnThree.add(gaugeModule);
        columnOne.add(percentSentimentModule);
        columnThree.add(twitterModule);
        View preferred = new View(current, current.getDefaultView(), columnOne, columnTwo, columnThree);
        //_viewDAO.save(preferred);
        modelMap.put("view", preferred);
        return "index";
    }

    @RequestMapping(value = "/layout")
    public @ResponseBody
    View search(@RequestParam("query") String user) {
        User current = _userDAO.getByUsername(user);
        String userView = current.getDefaultView();
        View thisView = _viewDAO.getByViewName(userView);
        return thisView;
    }

}



