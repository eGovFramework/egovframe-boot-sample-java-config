package egovframework.example.boot.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import org.egovframe.rte.ptl.mvc.tags.ui.pagination.DefaultPaginationManager;
import org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationRenderer;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.filter.OrderedCharacterEncodingFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;
import egovframework.example.cmmn.web.EgovBindingInitializer;
import egovframework.example.cmmn.web.EgovImgPaginationRenderer;

@Configuration
public class EgovWebMvcConfiguration extends WebMvcConfigurationSupport {

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/cmmn/validator.do").setViewName("cmmn/validator");
		registry.addViewController("/").setViewName("forward:/index.jsp");
		registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
	}

	@Bean
	public RequestMappingHandlerAdapter requestMappingHandlerAdapter() {
		RequestMappingHandlerAdapter rmha = super.createRequestMappingHandlerAdapter();
		rmha.setWebBindingInitializer(new EgovBindingInitializer());
		return rmha;
	}

	@Override
	public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
		Properties prop = new Properties();
		prop.setProperty("org.springframework.dao.DataAccessException", "cmmn/dataAccessFailure");
		prop.setProperty("org.springframework.transaction.TransactionException", "cmmn/transactionFailure");
		prop.setProperty("org.egovframe.rte.fdl.cmmn.exception.EgovBizException", "cmmn/egovError");
		prop.setProperty("org.springframework.security.AccessDeniedException", "cmmn/egovError");
		prop.setProperty("java.lang.Throwable", "mmn/egovError");

		Properties statusCode = new Properties();
		statusCode.setProperty("cmmn/egovError", "400");
		statusCode.setProperty("cmmn/egovError", "500");

		SimpleMappingExceptionResolver smer = new SimpleMappingExceptionResolver();
		smer.setDefaultErrorView("cmmn/egovError");
		smer.setExceptionMappings(prop);
		smer.setStatusCodes(statusCode);
		exceptionResolvers.add(smer);
	}

	@Bean
	public UrlBasedViewResolver urlBasedViewResolver() {
		UrlBasedViewResolver urlBasedViewResolver = new UrlBasedViewResolver();
		urlBasedViewResolver.setOrder(1);
		urlBasedViewResolver.setViewClass(JstlView.class);
		urlBasedViewResolver.setPrefix("/WEB-INF/jsp/egovframework/example/");
		urlBasedViewResolver.setSuffix(".jsp");
		return urlBasedViewResolver;
	}

	@Bean
	public EgovImgPaginationRenderer imageRenderer() {
		return new EgovImgPaginationRenderer();
	}

	@Bean
	public DefaultPaginationManager paginationManager(EgovImgPaginationRenderer imageRenderer) {
		Map<String, PaginationRenderer> rendererType = new HashMap<>();
		rendererType.put("image", imageRenderer);
		DefaultPaginationManager defaultPaginationManager = new DefaultPaginationManager();
		defaultPaginationManager.setRendererType(rendererType);
		return defaultPaginationManager;	
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
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
    	registry.addResourceHandler("/**").addResourceLocations("/");
    }

	@Bean
	public FilterRegistrationBean encodingFilterBean() {
		FilterRegistrationBean registrationBean = new FilterRegistrationBean();
		CharacterEncodingFilter filter = new CharacterEncodingFilter();
		filter.setForceEncoding(true);
		filter.setEncoding("UTF-8");
		registrationBean.setFilter(filter);
		registrationBean.addUrlPatterns("*.do");
		return registrationBean;
	}

}
