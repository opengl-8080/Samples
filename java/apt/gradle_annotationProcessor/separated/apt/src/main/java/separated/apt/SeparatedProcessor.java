package separated.apt;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.util.Set;

public class SeparatedProcessor extends AbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        this.processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "SeparatedProcessor!!");
        return true;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Set.of("separated.core.Separated");
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.RELEASE_11;
    }
}
