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
	EgovSampleExcepHndlr egovHandler() {
		return new EgovSampleExcepHndlr();
	}

	@Bean
	EgovSampleOthersExcepHndlr otherHandler() {
		return new EgovSampleOthersExcepHndlr();
	}

	@Bean
	DefaultExceptionHandleManager defaultExceptionHandleManager(AntPathMatcher antPathMatcher, EgovSampleExcepHndlr egovHandler) {
		DefaultExceptionHandleManager defaultExceptionHandleManager = new DefaultExceptionHandleManager();
		defaultExceptionHandleManager.setReqExpMatcher(antPathMatcher);
		defaultExceptionHandleManager.setPatterns(new String[]{"**service.impl.*"});
		defaultExceptionHandleManager.setHandlers(new ExceptionHandler[]{egovHandler});
		return defaultExceptionHandleManager;
	}

	@Bean
	DefaultExceptionHandleManager otherExceptionHandleManager(AntPathMatcher antPathMatcher, EgovSampleOthersExcepHndlr othersExcepHndlr) {
		DefaultExceptionHandleManager defaultExceptionHandleManager = new DefaultExceptionHandleManager();
		defaultExceptionHandleManager.setReqExpMatcher(antPathMatcher);
		defaultExceptionHandleManager.setPatterns(new String[]{"**service.impl.*"});
		defaultExceptionHandleManager.setHandlers(new ExceptionHandler[]{othersExcepHndlr});
		return defaultExceptionHandleManager;
	}

	@Bean
	ExceptionTransfer exceptionTransfer(
		@Qualifier("defaultExceptionHandleManager") DefaultExceptionHandleManager defaultExceptionHandleManager,
		@Qualifier("otherExceptionHandleManager") DefaultExceptionHandleManager otherExceptionHandleManager) {
		ExceptionTransfer exceptionTransfer = new ExceptionTransfer();
		exceptionTransfer.setExceptionHandlerService(new ExceptionHandlerService[] {
			defaultExceptionHandleManager, otherExceptionHandleManager
		});
		return exceptionTransfer;
	}

	@Bean
	EgovAopExceptionTransfer aopExceptionTransfer(ExceptionTransfer exceptionTransfer) {
		EgovAopExceptionTransfer egovAopExceptionTransfer = new EgovAopExceptionTransfer();
		egovAopExceptionTransfer.setExceptionTransfer(exceptionTransfer);
		return egovAopExceptionTransfer;
	}

}
