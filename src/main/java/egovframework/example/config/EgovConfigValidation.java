package egovframework.example.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import lombok.NoArgsConstructor;

/**
 * Validation 구성
 */
@Configuration
@NoArgsConstructor
public class EgovConfigValidation {

	/**
	 * 유효성 검사기 받기
	 * 
	 * @param messageSource
	 * @return
	 */
	@Bean
	public Validator getValidator(final @Qualifier("messageSource") MessageSource messageSource) {
		LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
		localValidatorFactoryBean.setValidationMessageSource(messageSource);
		return localValidatorFactoryBean;
	}

}
