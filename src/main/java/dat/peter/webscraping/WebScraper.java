package dat.peter.webscraping;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class WebScraper {

    private static final String BASE_URL = "https://steamdb.info/";
    private static final String APP_CHART_URL = BASE_URL + "app/#/charts/";

    public static void main(String[] args) {
        System.out.println(getData());
    }

    public static String getData() {
        Document document = getDocument(BASE_URL);
        System.out.println(document);

        return "Hello world!";
    }

    private static Document getDocument(String url) {
        try {
            Document document = Jsoup.connect(url).get();
            Thread.sleep(2000);
            return document;
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}