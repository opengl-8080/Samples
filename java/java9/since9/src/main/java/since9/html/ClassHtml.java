package since9.html;

import org.jsoup.nodes.Element;

import java.util.stream.Stream;

public class ClassHtml extends ApiHtml {
    
    private final String relativePath;

    public ClassHtml(String relativePath) {
        super(relativePath);
        this.relativePath = relativePath;
    }

    public boolean isSince9Type() {
        return this.doc.select("body main .contentContainer .description dl")
                .stream()
                .anyMatch(dl -> dl.text().contains("Since: 9"));
    }
    
    public String getRelativePath() {
        return this.relativePath;
    }
    
    public String description() {
        String text = this.doc.select("main .contentContainer .description div.block").text();
        int firstDotIndex = text.indexOf(".");
        if (firstDotIndex == -1) {
            return text;
        } else {
            return text.substring(0, firstDotIndex + 1);
        }
    }
    
    public Stream<SummaryBlock> summaries() {
        return this.doc.select("main .contentContainer .summary table.memberSummary tr:nth-child(n+2)")
                .stream().map(SummaryBlock::new);
    }
    
    public Stream<MemberBlock> members() {
        return this.doc.select("main .contentContainer .details section[role='region'] ul ul")
                .stream().map(element -> new MemberBlock(this, element));
    }
    
    public String moduleName() {
        return this.doc.select("main .header .subTitle")
                .stream()
                .filter(subTitle -> subTitle.text().startsWith("Module"))
                .map(Element::text)
                .map(text -> text.replaceAll("^Module[  ]*", ""))
                .findFirst()
                .orElse("unknown module name");
    }
    
    public String packageName() {
        return this.doc.select("main .header .subTitle")
                .stream()
                .filter(subTitle -> subTitle.text().startsWith("Package"))
                .map(Element::text)
                .map(text -> text.replaceAll("^Package[  ]*", ""))
                .findFirst()
                .orElse("unknown package name");
    }

    public String name() {
        return this.doc.select("main .header .title")
                .text().replaceAll("^[^ ]+ *", "");
    }

    @Override
    public String toString() {
        return "ClassHtml(module=" + this.moduleName() + ", package=" + this.packageName() + ", name=" + this.name() + ", description=" + this.description() + ")";
    }

    public SummaryBlock findSummaryBlock(String id) {
        return this.summaries().filter(summary -> summary.href().equals(id)).findFirst().orElseThrow(() -> new IllegalStateException("id not found. id=" + id));
    }
}
