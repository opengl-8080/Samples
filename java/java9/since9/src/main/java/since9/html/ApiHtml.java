package since9.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class ApiHtml {
    private static final Path apiDir;
    static {
        try {
            apiDir = Paths.get(new String(Files.readAllBytes(Paths.get("./apidir")), "UTF-8"));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    protected final Path path;
    protected final Document doc;

    protected ApiHtml(String relativePath) {
        this.path = apiDir.resolve(relativePath);
        this.doc = this.parse();
    }

    private Document parse() {
        try (InputStream in = Files.newInputStream(this.path, StandardOpenOption.READ)) {
            return Jsoup.parse(in, "UTF-8", "");
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
