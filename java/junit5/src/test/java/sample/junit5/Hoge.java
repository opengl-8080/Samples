package sample.junit5;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.junit.gen5.api.Tag;

@Tag("hoge")
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Hoge {
}
