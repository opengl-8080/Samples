package sample.spring.shell;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class HogeConverter implements Converter<String, Hoge> {
    
    @Override
    public Hoge convert(String source) {
        return new Hoge(source);
    }
}
