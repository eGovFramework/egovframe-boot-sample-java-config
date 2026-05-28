package egovframework.example.exception;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * EgovSampleExcepHndlr 단위 테스트
 *
 * @author 이백행
 * @since 2025-05-28
 */
class EgovSampleExcepHndlrTest {

	private EgovSampleExcepHndlr handler;

	@BeforeEach
	void setUp() {
		handler = new EgovSampleExcepHndlr();
	}

	@Test
	@DisplayName("occur 메서드 - RuntimeException 발생 시 예외 없이 처리")
	void testOccurWithRuntimeException() {
		RuntimeException ex = new RuntimeException("테스트 예외");
		assertDoesNotThrow(() -> handler.occur(ex, "egovframework.example"));
	}

	@Test
	@DisplayName("occur 메서드 - 일반 Exception 발생 시 예외 없이 처리")
	void testOccurWithException() {
		Exception ex = new Exception("일반 예외");
		assertDoesNotThrow(() -> handler.occur(ex, "egovframework.example.sample"));
	}

	@Test
	@DisplayName("occur 메서드 - 빈 패키지명으로 호출 시 예외 없이 처리")
	void testOccurWithEmptyPackageName() {
		Exception ex = new IllegalArgumentException("잘못된 인수");
		assertDoesNotThrow(() -> handler.occur(ex, ""));
	}

}
