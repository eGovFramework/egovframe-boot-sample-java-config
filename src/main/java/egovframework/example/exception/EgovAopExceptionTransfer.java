package egovframework.example.exception;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.egovframe.rte.fdl.cmmn.aspect.ExceptionTransfer;

import lombok.NoArgsConstructor;

/**
 * Egov Aop 예외 전송
 */
@Aspect
@NoArgsConstructor
public class EgovAopExceptionTransfer {

	/**
	 * 예외 이전
	 */
	private ExceptionTransfer exceptionTransfer;

	public void setExceptionTransfer(final ExceptionTransfer exceptionTransfer) {
		this.exceptionTransfer = exceptionTransfer;
	}

	@Pointcut("execution(* egovframework.example..impl.*Impl.*(..))")
	private void exceptionTransferService() {
	}

	/**
	 * 예외 전송 서비스 발생 후 수행
	 * 
	 * @param thisJoinPoint
	 * @param exception
	 * @throws Exception
	 */
	@AfterThrowing(pointcut = "exceptionTransferService()", throwing = "exception")
	public void doAfterThrowingExceptionTransferService(final JoinPoint thisJoinPoint, final Exception exception)
			throws Exception {
		exceptionTransfer.transfer(thisJoinPoint, exception);
	}

}
