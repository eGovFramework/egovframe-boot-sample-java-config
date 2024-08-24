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
import lombok.NoArgsConstructor;

/**
 * Aspect 구성
 */
@Configuration
@EnableAspectJAutoProxy
@NoArgsConstructor
public class EgovConfigAspect {

	/**
	 * Egov 샘플 ExcepHndlr
	 * 
	 * @return
	 */
	@Bean
	public EgovSampleExcepHndlr egovHandler() {
		return new EgovSampleExcepHndlr();
	}

	/**
	 * Egov 샘플 OthersExcepHndlr
	 * 
	 * @return
	 */
	@Bean
	public EgovSampleOthersExcepHndlr otherHandler() {
		return new EgovSampleOthersExcepHndlr();
	}

	/**
	 * 기본 예외 처리 관리자
	 * 
	 * @param antPathMatcher
	 * @param egovHandler
	 * @return
	 */
	@Bean
	public DefaultExceptionHandleManager defaultExceptionHandleManager(final AntPathMatcher antPathMatcher,
			final EgovSampleExcepHndlr egovHandler) {
		final DefaultExceptionHandleManager manager = new DefaultExceptionHandleManager();
		manager.setReqExpMatcher(antPathMatcher);
		manager.setPatterns(new String[] { "**service.impl.*" });
		manager.setHandlers(new ExceptionHandler[] { egovHandler });
		return manager;
	}

	/**
	 * 기타 예외 처리 관리자
	 * 
	 * @param antPathMatcher
	 * @param othersExcepHndlr
	 * @return
	 */
	@Bean
	public DefaultExceptionHandleManager otherExceptionHandleManager(final AntPathMatcher antPathMatcher,
			final EgovSampleOthersExcepHndlr othersExcepHndlr) {
		final DefaultExceptionHandleManager handleManager = new DefaultExceptionHandleManager();
		handleManager.setReqExpMatcher(antPathMatcher);
		handleManager.setPatterns(new String[] { "**service.impl.*" });
		handleManager.setHandlers(new ExceptionHandler[] { othersExcepHndlr });
		return handleManager;
	}

	/**
	 * 예외 이전
	 * 
	 * @param defaultException
	 * @param otherException
	 * @return
	 */
	@Bean
	public ExceptionTransfer exceptionTransfer(
			final @Qualifier("defaultExceptionHandleManager") DefaultExceptionHandleManager defaultException,
			final @Qualifier("otherExceptionHandleManager") DefaultExceptionHandleManager otherException) {
		final ExceptionTransfer exceptionTransfer = new ExceptionTransfer();
		exceptionTransfer.setExceptionHandlerService(
				new ExceptionHandlerService[] { defaultException, otherException });
		return exceptionTransfer;
	}

	/**
	 * Egov Aop 예외 전송
	 * 
	 * @param exceptionTransfer
	 * @return
	 */
	@Bean
	public EgovAopExceptionTransfer aopExceptionTransfer(final ExceptionTransfer exceptionTransfer) {
		final EgovAopExceptionTransfer transfer = new EgovAopExceptionTransfer();
		transfer.setExceptionTransfer(exceptionTransfer);
		return transfer;
	}

}
