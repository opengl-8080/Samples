package sample.bar;

import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic.Kind;

public class MyProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Elements elements = this.processingEnv.getElementUtils();
        
        if (!annotations.isEmpty()) {
            this.printAnnotations(elements.getTypeElement("sample.foo.Fizz"));
            this.printAnnotations(elements.getTypeElement("sample.foo.Buzz"));
        }
        
        return true;
    }
    
    private void printAnnotations(TypeElement element) {
        element.getAnnotationMirrors().forEach(annotation -> {
            this.processingEnv.getMessager().printMessage(Kind.NOTE, element.getSimpleName() + " : " + annotation);
        });
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.RELEASE_10;
    }
    
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Set.of(MySourceRetention.class.getCanonicalName());
    }
}
