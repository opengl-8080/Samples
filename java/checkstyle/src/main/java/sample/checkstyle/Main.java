package sample.checkstyle;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

import static java.util.stream.Collectors.*;

public class Main {

    public static void main(String[] args) throws Exception {
        try (InputStream in = new FileInputStream(new File("./google_checks.xml"))) {
            Document document = Jsoup.parse(in, "UTF-8", ".", Parser.xmlParser());

            document.select("module").forEach(module -> {
                String moduleName = module.attr("name");


                List<String> list =
                module.select("> property")
                        .stream()
                        .map(p -> moduleName + "\t" + p.attr("name") + "\t" + p.attr("value"))
                        .collect(toList());

                if (list.isEmpty()) {
                    System.out.println(moduleName);
                } else {
                    list.forEach(System.out::println);
                }
            });
        }


    }
}