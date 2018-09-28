package sample.httpclient;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest httpRequest = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/test")).POST(HttpRequest.BodyPublishers.
                ofString("ほげ")).build();
        CompletableFuture<HttpResponse<String>> future = httpClient.sendAsync(httpRequest, HttpResponse.BodyHandlers.ofString());
        future.thenAcceptAsync(stringHttpResponse -> {
            System.out.println("body=" + stringHttpResponse.body());
        });

        System.out.println("waiting...");

        TimeUnit.SECONDS.sleep(3);

        System.out.println("terminate.");
    }
}
