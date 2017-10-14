package since9.html;

import java.util.stream.Stream;

public class AllClassesHtml extends ApiHtml {
    public AllClassesHtml() {
        super("allclasses-noframe.html");
    }
    
    public Stream<ClassHtml> classes() {
        return this.doc.select("body main ul li a")
                .stream()
                .map(a -> a.attr("href"))
                .map(ClassHtml::new);
    }

    public Stream<ClassHtml> classesWithParallel() {
        return this.doc.select("body main ul li a")
                .parallelStream()
                .map(a -> a.attr("href"))
                .map(ClassHtml::new);
    }
}
