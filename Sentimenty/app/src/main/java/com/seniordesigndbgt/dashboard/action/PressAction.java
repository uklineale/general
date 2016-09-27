package com.seniordesigndbgt.dashboard.action;

import com.seniordesigndbgt.dashboard.analytics.AnalyzerFactory;
import com.seniordesigndbgt.dashboard.model.Press;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class PressAction {

    public PressAction() {}

    public static String getBodyContent(Press article) throws IOException {
        if(article.getUrl() == null)
                return null;
        String url = article.getUrl();
        String source = article.getSource();
        Document doc = Jsoup.connect(url).get();
        String text = "";
        Elements body = null;
        switch (source.charAt(0)) {
            case 'R':
                body = doc.select("#articleText");
                text = body.text();
                if(text.isEmpty()){
                    body = doc.select("#postcontent");
                    text = body.text();
                }
                break;
            case 'B':
                body = doc.select("div.article-body__content");
                text = body.text();
                break;
        }
        return text;
    }
}
