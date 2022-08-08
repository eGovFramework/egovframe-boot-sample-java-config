package egovframework.example.config;

import java.io.IOException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springmodules.validation.commons.DefaultBeanValidator;
import org.springmodules.validation.commons.DefaultValidatorFactory;

@Configuration
public class EgovConfigValidator {

	@Bean
	public DefaultBeanValidator beanValidator() throws IOException {
		DefaultBeanValidator defaultBeanValidator = new DefaultBeanValidator();
		defaultBeanValidator.setValidatorFactory(validatorFactory());
		return defaultBeanValidator;
	}

	@Bean
	public DefaultValidatorFactory validatorFactory() throws IOException {
		PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
	    Resource[] resource = resolver.getResources("classpath:/egovframework/validator/*.xml");
	    DefaultValidatorFactory defaultValidatorFactory = new DefaultValidatorFactory();
	    defaultValidatorFactory.setValidationConfigLocations(resource);
		return defaultValidatorFactory;
	}

}
