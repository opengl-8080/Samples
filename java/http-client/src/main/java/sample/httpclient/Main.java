package sample.httpclient;

import java.io.FileNotFoundException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Paths;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) throws InterruptedException, FileNotFoundException {
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest httpRequest = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/test")).POST(
                HttpRequest.BodyPublishers.ofFile(Paths.get("./build/image.jpg"))).build();

        ExecutorService executor = Executors.newSingleThreadExecutor(runnable -> new Thread(runnable, "my executor"));

        httpClient.sendAsync(httpRequest, HttpResponse.BodyHandlers.ofString())
                .thenApply(body -> {
                    if (body == null) {
                        throw new RuntimeException("test");
                    }
                    return "hello";
                })
                .handle((r, e) -> "r=" + r + ", e=" + e)
                .thenAccept(System.out::println);

        TimeUnit.SECONDS.sleep(3);
        System.out.println("done");
    }
    
    private static void printThread(String tag) {
        System.out.println("[" + tag + "] " + Thread.currentThread().getName());
    }
}
