package sample.thymeleaf.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/form")
public class FormController {
    
    @GetMapping
    public String init(Model model) {
        model.addAttribute(new MyForm());
        return "form";
    }
    
    @PostMapping
    public String submit(@Validated MyForm form, BindingResult result) {
        System.out.println("********************************************************");
        System.out.println("form = " + form);
        System.out.println("result = " + result);
        System.out.println("********************************************************");
        return "form";
    }
}
