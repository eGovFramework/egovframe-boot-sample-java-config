package egovframework.example.config;

import java.util.List;
import java.util.Properties;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;

import egovframework.example.pagination.EgovPaginationDialect;
import lombok.NoArgsConstructor;

/**
 * Web 구성
 */
@Configuration
@Import({ EgovConfigAspect.class, EgovConfigCommon.class, EgovConfigDatasource.class, EgovConfigIdGeneration.class,
		EgovConfigMapper.class, EgovConfigProperties.class, EgovConfigTransaction.class, EgovConfigValidation.class })
@NoArgsConstructor
public class EgovConfigWeb implements WebMvcConfigurer, ApplicationContextAware {

	/**
	 * 애플리케이션 컨텍스트
	 */
	private ApplicationContext context;

	/**
	 * 
	 */
	private static final String EGOV_SAMPLE_ERROR = "egovSampleError";

	@Override
	public void setApplicationContext(final ApplicationContext context) {
		this.context = context;
	}

	/**
	 * 템플릿 해석기
	 * 
	 * @return
	 */
	@Bean
	public SpringResourceTemplateResolver templateResolver() {
		final SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
		templateResolver.setApplicationContext(this.context);
		templateResolver.setPrefix("classpath:/templates/thymeleaf/");
		templateResolver.setSuffix(".html");
		templateResolver.setTemplateMode(TemplateMode.HTML);
		templateResolver.setCacheable(true);
		return templateResolver;
	}

	/**
	 * 템플릿 엔진
	 * 
	 * @return
	 */
	@Bean
	public SpringTemplateEngine templateEngine() {
		final SpringTemplateEngine templateEngine = new SpringTemplateEngine();
		templateEngine.setTemplateResolver(templateResolver());
		templateEngine.setEnableSpringELCompiler(true);
		// add custom tag
		templateEngine.addDialect(new EgovPaginationDialect());
		return templateEngine;
	}

	/**
	 * thymeleaf 뷰 리졸버
	 * 
	 * @return
	 */
	@Bean
	public ThymeleafViewResolver thymeleafViewResolver() {
		final ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
		viewResolver.setCharacterEncoding("UTF-8");
		viewResolver.setTemplateEngine(templateEngine());
		return viewResolver;
	}

	/**
	 * 리소스 핸들러 추가
	 */
	@Override
	public void addResourceHandlers(final ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/css/**").addResourceLocations("classpath:/static/css/");
		registry.addResourceHandler("/images/**").addResourceLocations("classpath:/static/images/");
		registry.addResourceHandler("/js/**").addResourceLocations("classpath:/static/js/");
	}

	/**
	 * 로케일 확인자
	 * 
	 * @return
	 */
	@Bean
	public SessionLocaleResolver localeResolver() {
		return new SessionLocaleResolver();
	}

	/**
	 * 로케일 변경 인터셉터
	 * 
	 * @return
	 */
	@Bean
	public LocaleChangeInterceptor localeChangeInterceptor() {
		final LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
		interceptor.setParamName("language");
		return interceptor;
	}

	/**
	 * 인터셉터 추가
	 */
	@Override
	public void addInterceptors(final InterceptorRegistry registry) {
		registry.addInterceptor(localeChangeInterceptor());
	}

	/**
	 * 처리기 예외 확인자 구성
	 */
	@Override
	public void configureHandlerExceptionResolvers(final List<HandlerExceptionResolver> resolvers) {
		final Properties prop = new Properties();
		prop.setProperty("org.springframework.dao.DataAccessException", EGOV_SAMPLE_ERROR);
		prop.setProperty("org.springframework.transaction.TransactionException", EGOV_SAMPLE_ERROR);
		prop.setProperty("org.egovframe.rte.fdl.cmmn.exception.EgovBizException", EGOV_SAMPLE_ERROR);
		prop.setProperty("org.springframework.security.AccessDeniedException", EGOV_SAMPLE_ERROR);
		prop.setProperty("java.lang.Throwable", EGOV_SAMPLE_ERROR);

		final Properties statusCode = new Properties();
		statusCode.setProperty(EGOV_SAMPLE_ERROR, "400");
		statusCode.setProperty(EGOV_SAMPLE_ERROR, "500");

		final SimpleMappingExceptionResolver smer = new SimpleMappingExceptionResolver();
		smer.setDefaultErrorView(EGOV_SAMPLE_ERROR);
		smer.setExceptionMappings(prop);
		smer.setStatusCodes(statusCode);
		resolvers.add(smer);
	}

}
