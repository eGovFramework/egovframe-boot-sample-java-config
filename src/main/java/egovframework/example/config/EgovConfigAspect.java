package egovframework.example.config;

import org.egovframe.rte.fdl.cmmn.aspect.ExceptionTransfer;
import org.egovframe.rte.fdl.cmmn.exception.handler.ExceptionHandler;
import org.egovframe.rte.fdl.cmmn.exception.manager.DefaultExceptionHandleManager;
import org.egovframe.rte.fdl.cmmn.exception.manager.ExceptionHandlerService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.util.AntPathMatcher;

import egovframework.example.exception.EgovAopExceptionTransfer;
import egovframework.example.exception.EgovSampleExcepHndlr;
import egovframework.example.exception.EgovSampleOthersExcepHndlr;

@Configuration
@EnableAspectJAutoProxy
public class EgovConfigAspect {

	@Bean
	public EgovSampleExcepHndlr egovHandler() {
		return new EgovSampleExcepHndlr();
	}

	@Bean
	public EgovSampleOthersExcepHndlr otherHandler() {
		return new EgovSampleOthersExcepHndlr();
	}

	@Bean
	public DefaultExceptionHandleManager defaultExceptionHandleManager(final AntPathMatcher antPathMatcher,
			final EgovSampleExcepHndlr egovHandler) {
		DefaultExceptionHandleManager defaultExceptionHandleManager = new DefaultExceptionHandleManager();
		defaultExceptionHandleManager.setReqExpMatcher(antPathMatcher);
		defaultExceptionHandleManager.setPatterns(new String[] { "**service.impl.*" });
		defaultExceptionHandleManager.setHandlers(new ExceptionHandler[] { egovHandler });
		return defaultExceptionHandleManager;
	}

	@Bean
	public DefaultExceptionHandleManager otherExceptionHandleManager(final AntPathMatcher antPathMatcher,
			final EgovSampleOthersExcepHndlr othersExcepHndlr) {
		DefaultExceptionHandleManager defaultExceptionHandleManager = new DefaultExceptionHandleManager();
		defaultExceptionHandleManager.setReqExpMatcher(antPathMatcher);
		defaultExceptionHandleManager.setPatterns(new String[] { "**service.impl.*" });
		defaultExceptionHandleManager.setHandlers(new ExceptionHandler[] { othersExcepHndlr });
		return defaultExceptionHandleManager;
	}

	@Bean
	public ExceptionTransfer exceptionTransfer(
			final @Qualifier("defaultExceptionHandleManager") DefaultExceptionHandleManager defaultExceptionHandleManager,
			final @Qualifier("otherExceptionHandleManager") DefaultExceptionHandleManager otherExceptionHandleManager) {
		ExceptionTransfer exceptionTransfer = new ExceptionTransfer();
		exceptionTransfer.setExceptionHandlerService(
				new ExceptionHandlerService[] { defaultExceptionHandleManager, otherExceptionHandleManager });
		return exceptionTransfer;
	}

	@Bean
	public EgovAopExceptionTransfer aopExceptionTransfer(final ExceptionTransfer exceptionTransfer) {
		EgovAopExceptionTransfer egovAopExceptionTransfer = new EgovAopExceptionTransfer();
		egovAopExceptionTransfer.setExceptionTransfer(exceptionTransfer);
		return egovAopExceptionTransfer;
	}

}
