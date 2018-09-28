package sample.httpclient;

import java.io.FileNotFoundException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Paths;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) throws InterruptedException, FileNotFoundException {
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest httpRequest = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/test")).POST(
                HttpRequest.BodyPublishers.ofFile(Paths.get("./build/image.jpg"))).build();
        CompletableFuture<HttpResponse<String>> future = httpClient.sendAsync(httpRequest, HttpResponse.BodyHandlers.ofString());
        future.thenAcceptAsync(stringHttpResponse -> {
            System.out.println("body=" + stringHttpResponse.body());
        });

        System.out.println("waiting...");

        TimeUnit.SECONDS.sleep(3);

        System.out.println("terminate.");
    }
}
