package since9.html;

import org.jsoup.nodes.Element;

public class SummaryBlock {
    private final Element tr;

    public SummaryBlock(Element tr) {
        this.tr = tr;
    }
    
    public String name() {
        return this.tr.select(".memberNameLink").first().parent().text();
    }
    
    public String href() {
        return this.tr.select(".memberNameLink a").first().attr("href").replaceAll(".*#", "");
    }
    
    public String description() {
        return this.tr.select("td.colLast").text();
    }

    @Override
    public String toString() {
        return "SummaryBlock(name=" + this.name() + ", href=" + this.href() + ")";
    }
}
