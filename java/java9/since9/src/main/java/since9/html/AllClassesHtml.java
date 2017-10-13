package since9.html;

import java.util.stream.Stream;

public class AllClassesHtml extends ApiHtml {
    public AllClassesHtml() {
        super("allclasses-noframe.html");
    }
    
    public Stream<ClassHtml> stream() {
        return this.doc.select("body main ul li a")
                .stream()
                .map(a -> a.attr("href"))
                .map(ClassHtml::new);
    }
}
