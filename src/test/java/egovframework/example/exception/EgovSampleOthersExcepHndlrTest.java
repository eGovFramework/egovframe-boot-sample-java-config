package egovframework.example.exception;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * EgovSampleOthersExcepHndlr 단위 테스트
 *
 * @author 이백행
 * @since 2025-05-28
 */
class EgovSampleOthersExcepHndlrTest {

	private EgovSampleOthersExcepHndlr handler;

	@BeforeEach
	void setUp() {
		handler = new EgovSampleOthersExcepHndlr();
	}

	@Test
	@DisplayName("occur 메서드 - RuntimeException 발생 시 예외 없이 처리")
	void testOccurWithRuntimeException() {
		RuntimeException ex = new RuntimeException("런타임 예외");
		assertDoesNotThrow(() -> handler.occur(ex, "egovframework.example"));
	}

	@Test
	@DisplayName("occur 메서드 - 일반 Exception 발생 시 예외 없이 처리")
	void testOccurWithException() {
		Exception ex = new Exception("일반 예외");
		assertDoesNotThrow(() -> handler.occur(ex, "egovframework.example.sample"));
	}

	@Test
	@DisplayName("occur 메서드 - NullPointerException 발생 시 예외 없이 처리")
	void testOccurWithNullPointerException() {
		NullPointerException ex = new NullPointerException("널 포인터 예외");
		assertDoesNotThrow(() -> handler.occur(ex, "egovframework.example.service"));
	}

}
