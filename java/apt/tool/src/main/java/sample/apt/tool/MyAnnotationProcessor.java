package sample.apt.tool;

import java.io.IOException;
import java.io.Writer;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;

public class MyAnnotationProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        annotations.forEach(a -> {
            try {
                JavaFileObject javaFileObject = this.processingEnv.getFiler().createSourceFile("foo.Foo");
                
                try (Writer writer = javaFileObject.openWriter()) {
                    TypeSpec typeSpec = TypeSpec.classBuilder("Foo").build();
                    JavaFile javaFile = JavaFile.builder("foo", typeSpec).build();
                    javaFile.writeTo(writer);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
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
