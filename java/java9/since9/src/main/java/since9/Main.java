package since9;

import org.jsoup.Jsoup;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.TimeUnit;

public class Main {
    private static final String URL_BASE = "http://download.java.net/java/jdk9/docs/api";
    private boolean shouldWait;

    public static void main(String[] args) {
        new Main().execute();
    }
    
    private void execute() {
        String allClassesHtml = this.get(URL_BASE + "/allclasses-noframe.html");
        Jsoup.parse(allClassesHtml).select("body main ul li a").forEach(link -> {
            String href = link.attr("href");
            String classPage = this.get(URL_BASE + "/" + href);
            Jsoup.parse(classPage).select("")
        });
    }
    
    private String get(String url) {
        if (this.shouldWait) {
            try {
                TimeUnit.SECONDS.sleep(2L);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
        
        try {
            URLConnection con = new URL(url).openConnection();
            try (InputStream in = con.getInputStream();
                 ByteArrayOutputStream out = new ByteArrayOutputStream();) {
                
                byte[] buf = new byte[1024];
                int length;
                while ((length = in.read(buf)) != -1) {
                    out.write(buf, 0, length);
                }

                return out.toString("UTF-8");
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        } finally {
            this.shouldWait = true;
        }
    }
}
