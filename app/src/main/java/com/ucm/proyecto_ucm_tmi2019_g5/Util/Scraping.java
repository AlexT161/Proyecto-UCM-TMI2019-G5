package com.ucm.proyecto_ucm_tmi2019_g5.Util;


import android.os.AsyncTask;

import com.ucm.proyecto_ucm_tmi2019_g5.Controller.PhotoController;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class Scraping extends AsyncTask<String, String, String> {

    public static final int maxPages = 2;
    public static final String GOOGLE_SEARCH_URL = "https://www.google.com/search";
    String menu = null;

    @Override
    protected String doInBackground(String... params) {
        try{
            System.out.println("params: " + params[0]);
            menu = doScraping(params[0]);
        } catch(Exception e){
            e.printStackTrace();
        }

        return menu;
    }

    public static String doScraping(String restaurante) throws IOException {
        //restaurante = "Daruma Tako";
        String searchTerm = "carta el tenedor " + restaurante + " madrid";
        String searchURL = GOOGLE_SEARCH_URL + "?q=" + searchTerm.replace(' ', '-') + "&num=" + 1;
        Document doc = null;
        try {
            doc = Jsoup.connect(searchURL).userAgent("Mozilla/5.0").get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String url = "";
        Elements results = doc.select("h3.r > a");
        for (Element result : results) {
            String linkHref = result.attr("href");
            url = linkHref.substring(7, linkHref.indexOf("&"));
        }

        //System.out.println(url);
        Connection response = Jsoup.connect(url).timeout(60000).ignoreContentType(true).ignoreHttpErrors(true);
        response.userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.64 Safari/537.11");
        response.header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        response.header("Accept-Charset", "ISO-8859-1,utf-8;q=0.7,*;q=0.3");
        response.header("Accept-Encoding", "none");
        response.header("Accept-Language", "en-US,en;q=0.8");
        response.header("Connection", "keep-alive");

        Document elTenedor = response.get();

        //System.out.println("---------------------Comida---------------------");
        Elements food = elTenedor.select("li.cardCategory");
        Elements foodText = food.select("div.cardCategory-itemTitle");
        Elements foodPrice = food.select("div.cardCategory-itemPrice");
        String foodString = "";
        for (int i = 0; i < foodText.size(); i++) {
            foodString += foodPrice.get(i).text() + " - " +foodText.get(i).text() + "\n";
        }
        return foodString;
    }
}