package since9;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class Main {
    private static final String URL_BASE = "http://download.java.net/java/jdk9/docs/api";

    public static void main(String[] args) throws IOException {
        URL url = new URL(URL_BASE + "/allclasses-noframe.html");
        Document document = Jsoup.parse(url, (int) TimeUnit.SECONDS.toMillis(30L));
        document.select("body main ul li a").forEach(link -> {
            System.out.println(link);
        });
    }
}
