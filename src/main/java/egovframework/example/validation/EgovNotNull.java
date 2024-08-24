package egovframework.example.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EgovValidation.class)
@Documented
public @interface EgovNotNull {

	/**
	 * 유효성 검사 false시 반환할 기본 메시지
	 * 
	 * @return
	 */
	String message() default "";

	/**
	 * 어노테이션을 적용할 특정 상황(예를 들어 특정 Class 시 어노테이션 동작)
	 * 
	 * @return
	 */
	Class<?>[] groups() default {};

	/**
	 * 심각한 정도 등 메타 데이터를 정의해 넣을 수 있음
	 * 
	 * @return
	 */
	Class<? extends Payload>[] payload() default {};

}
