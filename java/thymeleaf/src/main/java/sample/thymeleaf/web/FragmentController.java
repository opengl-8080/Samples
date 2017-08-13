package sample.thymeleaf.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FragmentController {
    
    
    @GetMapping("/fragment")
    public String fragment() {
        return "fragment/fragment";
    }
}
