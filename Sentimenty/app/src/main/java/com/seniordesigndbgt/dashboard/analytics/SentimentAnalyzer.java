package com.seniordesigndbgt.dashboard.analytics;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.seniordesigndbgt.dashboard.model.Press;
import java.awt.*;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class SentimentAnalyzer {

    protected SentimentAnalyzer(List que) {
        //sentimentQue = que;
    }
    private final String apiKey = "329f2c9dac59bb494f29ef219b42d84b6896be5c";
    private final String apiBase = "http://gateway-a.watsonplatform.net/calls";

    //List sentimentQue

    public String getSentiment(String text) {
        if(text.trim().isEmpty() )
            return null;
        try {
            text = URLEncoder.encode(text, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        HttpResponse<JsonNode> response = null;
        try {
            String url = apiBase + "/text/TextGetTextSentiment?apikey=" + apiKey + "&text=" + text + "&outputMode=json";
            response = Unirest.get(url)
                    .asJson();
            String jsonString = response.getBody().toString();

            //Walk through query to get to quote data
            JsonElement jElement = new JsonParser().parse(jsonString);
            JsonObject jObject = jElement.getAsJsonObject();
            jObject = jObject.getAsJsonObject("docSentiment");

            String out = jObject.toString();
            return out;
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        return "fail";
    }

    public String getSentiment(Press article) {
        // String url = pressArticle.getUrl();
        //TODO
        HttpResponse<JsonNode> response = null;

        try {
            String url = apiBase + "/url/URLGetTextSentiment?url=" + article.getUrl() + "&apikey=" + apiKey + "&outputMode=json&sourceText=cleaned";
            response = Unirest.get(url)
                    .asJson();
            String jsonString = response.getBody().toString();

            //Walk through query to get to quote data
            JsonElement jElement = new JsonParser().parse(jsonString);
            JsonObject jObject = jElement.getAsJsonObject();
            jObject = jObject.getAsJsonObject("docSentiment");

            String out = jObject.toString();
            return out;
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        return "fail";
    }
}
