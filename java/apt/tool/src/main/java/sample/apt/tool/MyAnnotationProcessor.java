package sample.apt.tool;

import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic.Kind;

public class MyAnnotationProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Messager messager = this.processingEnv.getMessager();
        messager.printMessage(Kind.NOTE, "Hello Annotation Processing!!!!!!!???????");
        return true;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latest();
    }
    
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Set.of("sample.apt.tool.Hoge");
    }
}
