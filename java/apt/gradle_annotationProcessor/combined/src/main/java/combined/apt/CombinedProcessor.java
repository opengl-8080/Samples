package combined.apt;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.util.Set;

public class CombinedProcessor extends AbstractProcessor {
    
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        this.processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "CombinedProcessor!!");
        return true;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Set.of("combined.core.Combined");
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.RELEASE_11;
    }
}
