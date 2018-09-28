package sample.httpclient;

import spark.Request;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;

import static spark.Spark.*;

public class HttpServer {
    public static void main(String[] args) {
        post("/test", (request, response) -> printRequest(request));
        get("/test", (request, response) -> printRequest(request));
    }
    
    private static String printRequest(Request request) {
        System.out.println("[" + request.requestMethod() + "]");
        System.out.println("<HEADERS>");
        request.headers().stream().sorted().map(h -> "  " + h + ": " + request.headers(h)).forEach(System.out::println);
        System.out.println("<QUERY PARAMS>");
        request.queryMap().toMap().entrySet().stream().map(e -> "  " + e.getKey() + " : " + Arrays.toString(e.getValue()))
                .sorted().forEach(System.out::println);
        try(InputStream in = new ByteArrayInputStream(request.bodyAsBytes())) {
            Files.copy(in, Paths.get("./build/body"), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println();
        return "";
    }
}
