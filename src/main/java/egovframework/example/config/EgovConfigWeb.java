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
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring6.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;

import egovframework.example.pagination.EgovKrdsPaginationRenderer;
import egovframework.example.pagination.EgovPaginationDialect;

@Configuration
@Import({
		EgovConfigAspect.class,
		EgovConfigCommon.class,
		EgovConfigDatasource.class,
		EgovConfigIdGeneration.class,
		EgovConfigMapper.class,
		EgovConfigProperties.class,
		EgovConfigTransaction.class,
		EgovConfigValidation.class
})
public class EgovConfigWeb implements WebMvcConfigurer, ApplicationContextAware {

	private ApplicationContext applicationContext;

	public void setApplicationContext(final ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	@Bean
	public SpringResourceTemplateResolver templateResolver() {
		SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
		templateResolver.setApplicationContext(this.applicationContext);
		templateResolver.setPrefix("classpath:/templates/thymeleaf/");
		templateResolver.setSuffix(".html");
		templateResolver.setTemplateMode(TemplateMode.HTML);
		templateResolver.setCacheable(true);
		return templateResolver;
	}

	@Bean
	public SpringTemplateEngine templateEngine(EgovKrdsPaginationRenderer egovKrdsPaginationRenderer) {
		SpringTemplateEngine templateEngine = new SpringTemplateEngine();
		templateEngine.setTemplateResolver(templateResolver());
		templateEngine.setEnableSpringELCompiler(true);
		// add custom tag
		templateEngine.addDialect(new EgovPaginationDialect(egovKrdsPaginationRenderer));
		return templateEngine;
	}

	@Bean
	public ThymeleafViewResolver thymeleafViewResolver(EgovKrdsPaginationRenderer egovKrdsPaginationRenderer) {
		ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
		viewResolver.setCharacterEncoding("UTF-8");
		viewResolver.setTemplateEngine(templateEngine(egovKrdsPaginationRenderer));
		return viewResolver;
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/css/**").addResourceLocations("classpath:/static/css/");
        registry.addResourceHandler("/images/**").addResourceLocations("classpath:/static/images/");
        registry.addResourceHandler("/img/**").addResourceLocations("classpath:/static/img/");
        registry.addResourceHandler("/js/**").addResourceLocations("classpath:/static/js/");

        // favicon.ico 처리를 위한 빈 핸들러 (404 오류 방지)
        registry.addResourceHandler("/favicon.ico")
        		.addResourceLocations("classpath:/static/")
        		.setCachePeriod(3600);

        // .well-known 경로도 처리 (Chrome DevTools 자동 요청)
        registry.addResourceHandler("/.well-known/**")
        		.addResourceLocations("classpath:/static/.well-known/");
	}

	@Bean
	public SessionLocaleResolver localeResolver() {
		return new SessionLocaleResolver();
	}

	@Bean
	public LocaleChangeInterceptor localeChangeInterceptor() {
		LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
		interceptor.setParamName("language");
		return interceptor;
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(localeChangeInterceptor());
	}

	@Override
	public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> resolvers) {
		Properties prop = new Properties();
		prop.setProperty("org.springframework.dao.DataAccessException", "sample/egovSampleError");
		prop.setProperty("org.springframework.transaction.TransactionException", "sample/egovSampleError");
		prop.setProperty("org.egovframe.rte.fdl.cmmn.exception.EgovBizException", "sample/egovSampleError");
		prop.setProperty("org.springframework.security.AccessDeniedException", "sample/egovSampleError");
		prop.setProperty("java.lang.Throwable", "sample/egovSampleError");

		Properties statusCode = new Properties();
		statusCode.setProperty("sample/egovSampleError", "400");
		statusCode.setProperty("sample/egovSampleError", "500");

		SimpleMappingExceptionResolver smer = new SimpleMappingExceptionResolver();
		smer.setDefaultErrorView("sample/egovSampleError");
		smer.setExceptionMappings(prop);
		smer.setStatusCodes(statusCode);
		resolvers.add(smer);
	}

}
