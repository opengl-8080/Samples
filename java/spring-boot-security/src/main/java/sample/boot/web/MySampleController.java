package sample.boot.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MySampleController {
    
    @GetMapping("/js/*")
    public String js() {
        return "js!!";
    }
    
    @GetMapping("/css/*")
    public String css() {
        return "css!!";
    }
    
    @GetMapping("/images/*")
    public String images() {
        return "images!!";
    }
    
    @GetMapping("/webjars/*")
    public String webjars() {
        return "webjars!!";
    }
    
    @GetMapping("/favicon.ico")
    public String favicon() {
        return "favicon!!";
    }
}
