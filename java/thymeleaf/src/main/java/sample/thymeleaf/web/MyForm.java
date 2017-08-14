package sample.thymeleaf.web;

import java.util.LinkedHashMap;
import java.util.Map;

public class MyForm {
    private String selectedValue = "piyo";
    
    public Map<String, String> radioButtons() {
        Map<String, String> radioButtons = new LinkedHashMap<>();
        radioButtons.put("hoge", "HOGE");
        radioButtons.put("fuga", "FUGA");
        radioButtons.put("piyo", "PIYO");
        
        return radioButtons;
    }

    public String getSelectedValue() {
        return selectedValue;
    }

    public void setSelectedValue(String selectedValue) {
        this.selectedValue = selectedValue;
    }
}
