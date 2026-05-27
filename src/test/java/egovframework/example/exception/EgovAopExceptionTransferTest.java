package egovframework.example.exception;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.aspectj.lang.JoinPoint;
import org.egovframe.rte.fdl.cmmn.aspect.ExceptionTransfer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * EgovAopExceptionTransfer 단위 테스트
 *
 * @author 이백행
 * @since 2025-05-28
 */
class EgovAopExceptionTransferTest {

	private EgovAopExceptionTransfer exceptionTransfer;
	private ExceptionTransfer mockExceptionTransfer;

	@BeforeEach
	void setUp() {
		exceptionTransfer = new EgovAopExceptionTransfer();
		mockExceptionTransfer = mock(ExceptionTransfer.class);
		exceptionTransfer.setExceptionTransfer(mockExceptionTransfer);
	}

	@Test
	@DisplayName("doAfterThrowingExceptionTransferService - ExceptionTransfer.transfer 위임 확인")
	void testDoAfterThrowingDelegatesToExceptionTransfer() throws Exception {
		JoinPoint joinPoint = mock(JoinPoint.class);
		Exception ex = new RuntimeException("서비스 예외");

		exceptionTransfer.doAfterThrowingExceptionTransferService(joinPoint, ex);

		verify(mockExceptionTransfer).transfer(eq(joinPoint), eq(ex));
	}

	@Test
	@DisplayName("setExceptionTransfer - 의존성 주입 후 transfer 호출 가능")
	void testSetExceptionTransfer() throws Exception {
		ExceptionTransfer another = mock(ExceptionTransfer.class);
		exceptionTransfer.setExceptionTransfer(another);

		JoinPoint joinPoint = mock(JoinPoint.class);
		Exception ex = new IllegalStateException("상태 예외");

		exceptionTransfer.doAfterThrowingExceptionTransferService(joinPoint, ex);

		verify(another).transfer(any(JoinPoint.class), eq(ex));
	}

}
