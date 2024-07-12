package egovframework.example.validation;

import org.springframework.util.ObjectUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EgovValidation implements ConstraintValidator<EgovNotNull, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return !ObjectUtils.isEmpty(value);
    }

}
