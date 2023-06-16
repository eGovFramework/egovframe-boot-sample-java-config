package egovframework.example.cmmn;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import org.egovframe.rte.fdl.cmmn.aspect.ExceptionTransfer;

/**
 * @Class Name : AopExceptionTransfer.java
 * @Description : AopExceptionTransfer Class
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ---------   ---------   -------------------------------
 * @ 2023.06.16           최초생성
 *
 * @author 개발프레임웍크 실행환경 개발팀
 * @since 2009. 03.16
 * @version 1.0
 * @see
 *
 *  Copyright (C) by MOPAS All right reserved.
 */
@Aspect
public class AopExceptionTransfer {

	private ExceptionTransfer exceptionTransfer;

	public void setExceptionTransfer(ExceptionTransfer exceptionTransfer) {
		this.exceptionTransfer = exceptionTransfer;
	}

	@Pointcut("execution(* egovframework.example..impl.*Impl.*(..))")
	private void exceptionTransferService() {}

	@AfterThrowing(pointcut= "exceptionTransferService()", throwing="ex")
	public void doAfterThrowingExceptionTransferService(JoinPoint thisJoinPoint, Exception ex) throws Exception {
		exceptionTransfer.transfer(thisJoinPoint, ex);
	}

}
