package egovframework.example.exception;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.egovframe.rte.fdl.cmmn.aspect.ExceptionTransfer;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Slf4j
public class EgovAopExceptionTransfer {

	private ExceptionTransfer exceptionTransfer;

	public void setExceptionTransfer(ExceptionTransfer exceptionTransfer) {
		this.exceptionTransfer = exceptionTransfer;
	}

	@Pointcut("execution(* egovframework.example..impl.*Impl.*(..))")
	public void exceptionTransferService() {
		if (log.isDebugEnabled()) {
			log.debug("exceptionTransferService");
		}
	}

	@AfterThrowing(pointcut = "exceptionTransferService()", throwing = "ex")
	public void doAfterThrowingExceptionTransferService(JoinPoint thisJoinPoint, Exception ex) throws Exception {
		exceptionTransfer.transfer(thisJoinPoint, ex);
	}

}
