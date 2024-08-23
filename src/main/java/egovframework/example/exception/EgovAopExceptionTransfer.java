package egovframework.example.exception;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.egovframe.rte.fdl.cmmn.aspect.ExceptionTransfer;

@Aspect
public class EgovAopExceptionTransfer {

	private ExceptionTransfer exceptionTransfer;

	public void setExceptionTransfer(ExceptionTransfer exceptionTransfer) {
		this.exceptionTransfer = exceptionTransfer;
	}

	@Pointcut("execution(* egovframework.example..impl.*Impl.*(..))")
	public void exceptionTransferService() {
		// 포인트컷 빈메소드임
	}

	@AfterThrowing(pointcut = "exceptionTransferService()", throwing = "ex")
	public void doAfterThrowingExceptionTransferService(JoinPoint thisJoinPoint, Exception ex) throws Exception {
		exceptionTransfer.transfer(thisJoinPoint, ex);
	}

}
