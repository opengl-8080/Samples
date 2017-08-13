package sample.thymeleaf.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LayoutController {
    
    @GetMapping("/layout")
    public String method() {
        return "layout/content";
    }
}
