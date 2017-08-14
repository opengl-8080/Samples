package sample.thymeleaf.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    public String submit(MyForm form) {
        System.out.println("form.checked=" + form.isChecked());
        return "form";
    }
}
