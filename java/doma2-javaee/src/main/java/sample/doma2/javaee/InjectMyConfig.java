package sample.doma2.javaee;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.seasar.doma.AnnotateWith;
import org.seasar.doma.Annotation;
import org.seasar.doma.AnnotationTarget;

@AnnotateWith(annotations={
    @Annotation(target=AnnotationTarget.CONSTRUCTOR, type=Inject.class),
    @Annotation(target=AnnotationTarget.CLASS, type=ApplicationScoped.class)
})
public @interface InjectMyConfig {
}
