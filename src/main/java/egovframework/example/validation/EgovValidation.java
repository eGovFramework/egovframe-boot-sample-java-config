package egovframework.example.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.util.ObjectUtils;

public class EgovValidation implements ConstraintValidator<EgovNotNull, String> {

	@Override
	public boolean isValid(final String value, final ConstraintValidatorContext context) {
		return !ObjectUtils.isEmpty(value);
	}

}
