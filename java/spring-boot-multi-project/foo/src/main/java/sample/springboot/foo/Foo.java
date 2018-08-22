package sample.springboot.foo;

import sample.springboot.bar.Bar;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Foo {

    @GetMapping("/hello")
    public String hello() {
        new Bar().method();
        return "hello";
    }
}