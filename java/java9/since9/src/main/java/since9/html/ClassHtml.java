package since9.html;

import org.jsoup.nodes.Element;

public class ClassHtml extends ApiHtml {

    public ClassHtml(String relativePath) {
        super(relativePath);
    }

    public boolean isSince9Type() {
        return this.doc.select("body main .contentContainer .description dl")
                .stream()
                .anyMatch(dl -> dl.text().equals("Since: 9"));
    }
    
    public String moduleName() {
        return this.doc.select("main .header .subTitle")
                .stream()
                .filter(subTitle -> subTitle.text().startsWith("Module"))
                .map(Element::text)
                .map(text -> text.replaceAll("^Module *", ""))
                .findFirst()
                .orElse("unknown module name");
    }
    
    public String packageName() {
        return this.doc.select("main .header .subTitle")
                .stream()
                .filter(subTitle -> subTitle.text().startsWith("Package"))
                .map(Element::text)
                .map(text -> text.replaceAll("^Package *", ""))
                .findFirst()
                .orElse("unknown package name");
    }

    public String name() {
        return this.doc.select("main .header .title")
                .text().replaceAll("^[^ ]+ *", "");
    }

    @Override
    public String toString() {
        return "ClassHtml(module=" + this.moduleName() + ", package=" + this.packageName() + ", name=" + this.name() + ")";
    }
}
