package sample.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import sample.spring.security.mvc.MyMvcController;

@EnableWebMvc
public class MyMvcConfig extends WebMvcConfigurerAdapter {
    
    @Bean
    public MyMvcController myMvcController() {
        return new MyMvcController();
    }
}
