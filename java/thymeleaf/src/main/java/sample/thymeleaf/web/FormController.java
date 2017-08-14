package sample.thymeleaf.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/form")
public class FormController {
    
    @GetMapping
    public String init(Model model) {
        model.addAttribute(new MyForm());
        return "form";
    }
    
    @PostMapping(params="appendRow")
    public String appendRow(MyForm form) {
        form.appendRow();
        this.printRows(form);
        return "form";
    }
    
    @PostMapping(params="removeIndex")
    public String submit(MyForm form, @RequestParam int removeIndex) {
        form.removeRow(removeIndex);
        this.printRows(form);
        return "form";
    }
    
    private void printRows(MyForm form) {
        List<MyForm.Row> rows = form.getRows();
        for (int i = 0; i < rows.size(); i++) {
            MyForm.Row row = rows.get(i);
            System.out.println("i=" + i + ", row.value=" + row.getValue());
        }
    }
}
