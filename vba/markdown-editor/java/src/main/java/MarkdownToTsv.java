import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.pegdown.Extensions;
import org.pegdown.PegDownProcessor;

public class MarkdownToTsv {
    String toTsv(String markdown) {
        PegDownProcessor processor = new PegDownProcessor(Extensions.ALL);
        String html = processor.markdownToHtml(markdown);

        Document document = Jsoup.parse(html);
        MyVisitor visitor = new MyVisitor();
        document.traverse(visitor);
        return visitor.getText();
    }
}
