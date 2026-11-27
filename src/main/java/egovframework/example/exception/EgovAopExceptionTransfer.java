package egovframework.example.exception;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.egovframe.rte.fdl.cmmn.aspect.ExceptionTransfer;
import org.egovframe.rte.fdl.cmmn.exception.BaseRuntimeException;

/**
 * 서비스 예외를 ExceptionTransfer 로 전달하는 Aspect
 */
@Aspect
public class EgovAopExceptionTransfer {

	private ExceptionTransfer exceptionTransfer;

	public void setExceptionTransfer(ExceptionTransfer exceptionTransfer) {
		this.exceptionTransfer = exceptionTransfer;
	}

	@Pointcut("execution(* egovframework.example..impl.*Impl.*(..))")
	private void exceptionTransferService() {}

	@AfterThrowing(pointcut="exceptionTransferService()", throwing="ex")
	public void doAfterThrowingExceptionTransferService(JoinPoint thisJoinPoint, Exception ex) throws BaseRuntimeException, Exception {
		exceptionTransfer.transfer(thisJoinPoint, ex);
	}

}
