package egovframework.example.config;

import org.egovframe.rte.fdl.cmmn.trace.LeaveaTrace;
import org.egovframe.rte.fdl.cmmn.trace.handler.DefaultTraceHandler;
import org.egovframe.rte.fdl.cmmn.trace.handler.TraceHandler;
import org.egovframe.rte.fdl.cmmn.trace.manager.DefaultTraceHandleManager;
import org.egovframe.rte.fdl.cmmn.trace.manager.TraceHandlerService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.util.AntPathMatcher;

import lombok.NoArgsConstructor;

/**
 * Common 구성
 */
@Configuration
@NoArgsConstructor
public class EgovConfigCommon {

	/**
	 * 개미 경로 일치자
	 * 
	 * @return
	 */
	@Bean
	public AntPathMatcher antPathMatcher() {
		return new AntPathMatcher();
	}

	/**
	 * 기본 추적 핸들러
	 * 
	 * @return
	 */
	@Bean
	public DefaultTraceHandler defaultTraceHandler() {
		return new DefaultTraceHandler();
	}

	/**
	 * 메시지 소스
	 * 
	 * @return
	 */
	@Bean
	public ReloadableResourceBundleMessageSource messageSource() {
		final ReloadableResourceBundleMessageSource reloadableResourceBundleMessageSource = new ReloadableResourceBundleMessageSource();
		reloadableResourceBundleMessageSource.setBasenames("classpath:/egovframework/message/message-common",
				"classpath:/org/egovframe/rte/fdl/idgnr/messages/idgnr",
				"classpath:/org/egovframe/rte/fdl/property/messages/properties");
		reloadableResourceBundleMessageSource.setDefaultEncoding("UTF-8");
		reloadableResourceBundleMessageSource.setCacheSeconds(60);
		return reloadableResourceBundleMessageSource;
	}

	/**
	 * 메시지 소스 접근자
	 * 
	 * @return
	 */
	@Bean
	public MessageSourceAccessor messageSourceAccessor() {
		return new MessageSourceAccessor(this.messageSource());
	}

	/**
	 * 추적 핸들러 서비스
	 * 
	 * @return
	 */
	@Bean
	public DefaultTraceHandleManager traceHandlerService() {
		final DefaultTraceHandleManager defaultTraceHandleManager = new DefaultTraceHandleManager();
		defaultTraceHandleManager.setReqExpMatcher(antPathMatcher());
		defaultTraceHandleManager.setPatterns(new String[] { "*" });
		defaultTraceHandleManager.setHandlers(new TraceHandler[] { defaultTraceHandler() });
		return defaultTraceHandleManager;
	}

	/**
	 * 흔적을 남기다
	 * 
	 * @return
	 */
	@Bean
	public LeaveaTrace leaveaTrace() {
		final LeaveaTrace leaveaTrace = new LeaveaTrace();
		leaveaTrace.setTraceHandlerServices(new TraceHandlerService[] { traceHandlerService() });
		return leaveaTrace;
	}

}
