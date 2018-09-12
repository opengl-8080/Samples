package sample.apt.tool;

import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic.Kind;

public class MyAnnotationProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        annotations.forEach(a -> {
            Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(a);
            elements.forEach(element -> {
                print(element.toString());
                element.getEnclosedElements().stream()
                .filter(e -> e.getKind() == ElementKind.FIELD)
                .forEach(field -> {
                    TypeMirror type = field.asType();
                    Element typeElement = this.processingEnv.getTypeUtils().asElement(type);
                    print("  " + field + ", type=" + type + ", annotationMirrors=" + typeElement.getAnnotationMirrors());
                });
            });
        });
        return true;
    }
    
    private void print(String message) {
        this.processingEnv.getMessager().printMessage(Kind.NOTE, message);
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
