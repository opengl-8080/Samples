package sample.httpclient;

import spark.Request;

import java.util.Arrays;

import static spark.Spark.*;

public class HttpServer {
    public static void main(String[] args) {
        post("/test", (request, response) -> printRequest(request));
        get("/test", (request, response) -> printRequest(request));
    }
    
    private static String printRequest(Request request) {
        System.out.println("[" + request.requestMethod() + "]");
        System.out.println("<headers>");
        request.headers().stream().sorted().map(h -> "  " + h + ": " + request.headers(h)).forEach(System.out::println);
        System.out.println("<query params>");
        request.queryMap().toMap().entrySet().stream().map(e -> "  " + e.getKey() + " : " + Arrays.toString(e.getValue()))
                .sorted().forEach(System.out::println);
        System.out.println();
        return "";
    }
}
