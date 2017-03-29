import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.NodeVisitor;

import java.util.stream.Collectors;

public class MyVisitor implements NodeVisitor {
    private StringBuilder sb = new StringBuilder();
    private int ulLevel;

    @Override
    public void head(Node node, int depth) {
        if (!(node instanceof Element)) {
            return;
        }
        Element element = (Element) node;
        String tagName = element.tagName();
        
        if (tagName.matches("^h\\d$")) {
            this.sb.append(tagName + "\t" + element.text() + "\n");
        } else if ("table".equals(tagName)) {
            this.sb.append("table\t");

            int rowSize = element.select("tr").size();
            int colSize = element.select("th").size();
            
            this.sb.append(rowSize + "\t" + colSize);
            String cellValues = element.select("th, td").stream().map(Element::text).collect(Collectors.joining("\t"));
            this.sb.append("\t" + cellValues + "\n");
        } else if ("pre".equals(tagName)) {
            this.sb.append("pre\t" + element.text().replaceAll("\n", "\\\\n") + "\n");
        } else if ("ul".equals(tagName)) {
            this.ulLevel++;
        } else if ("li".equals(tagName)) {
            this.sb.append("li\t" + this.ulLevel + "\t" + element.ownText() + "\n");
        } else if ("p".equals(tagName)) {
            this.sb.append("p\t" + element.html().replaceAll("<br>", "\\\\n") + "\n");
        }
    }

    @Override
    public void tail(Node node, int depth) {
        if (!(node instanceof Element)) {
            return;
        }
        Element element = (Element) node;
        String tagName = element.tagName();
        
        if ("ul".equals(tagName)) {
            this.ulLevel--;
        }
    }

    public String getText() {
        return this.sb.toString();
    }
}
