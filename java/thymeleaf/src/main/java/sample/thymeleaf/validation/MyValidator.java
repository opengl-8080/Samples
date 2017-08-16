package sample.thymeleaf.validation;


import sample.thymeleaf.web.MyForm;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MyValidator implements ConstraintValidator<MyValidation, MyForm> {
    @Override
    public void initialize(MyValidation constraintAnnotation) {
    }

    @Override
    public boolean isValid(MyForm value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
//
//        Integer number = value.getNumber();
//        if (number == null) {
//            return true;
//        }
//        
//        String text = value.getText();
//        
//        if (number == 500) {
//            return "500".equals(text);
//        }
        
        return true;
    }
}
