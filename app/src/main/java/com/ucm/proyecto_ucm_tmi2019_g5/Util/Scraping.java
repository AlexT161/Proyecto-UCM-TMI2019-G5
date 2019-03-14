package com.ucm.proyecto_ucm_tmi2019_g5.Util;


import java.io.IOException;
import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Scraping {

    public static final int maxPages = 2;
    public static final String GOOGLE_SEARCH_URL = "https://www.google.com/search";

    public static String doScraping() throws IOException {
        String searchTerm = "carta el tenedor tako daruma madrid";

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

        System.out.println(url);
        Connection response = Jsoup.connect(url).timeout(60000).ignoreContentType(true).ignoreHttpErrors(true);
        response.userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.64 Safari/537.11");
        response.header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        response.header("Accept-Charset", "ISO-8859-1,utf-8;q=0.7,*;q=0.3");
        response.header("Accept-Encoding", "none");
        response.header("Accept-Language", "en-US,en;q=0.8");
        response.header("Connection", "keep-alive");

        Document doc1 = null;
        try {
            doc1 = response.get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //System.out.println("---------Comida---------");
        Elements comida = doc1.select("div.restaurantTabContent-section");
        //System.out.println(comida.text().replace("€", "€\n"));
        //System.out.println("---------Bebida---------");
        Elements bebidas = doc1.select("section.restaurantTabContent-section");
        //System.out.println(bebidas.text().replace("€", "€\n"));
        return "---------Comida---------\n\n" +
                comida.text().replace("€", "€\n") +
                "\n" +
                "---------Bebida---------\n\n" +
                bebidas.text().replace("€", "€\n") +
                "\n";
    }
}