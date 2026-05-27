package egovframework.example.pagination;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.thymeleaf.expression.IExpressionObjectFactory;

/**
 * EgovPaginationDialect 단위 테스트
 *
 * @author 이백행
 * @since 2026-05-28
 */
class EgovPaginationDialectTest {

	private EgovKrdsPaginationRenderer renderer;
	private EgovPaginationDialect dialect;

	@BeforeEach
	void setUp() {
		renderer = mock(EgovKrdsPaginationRenderer.class);
		dialect = new EgovPaginationDialect(renderer);
	}

	@Test
	@DisplayName("Dialect 이름이 EgovPaginationDialect로 등록된다")
	void testDialectName() {
		assertEquals("EgovPaginationDialect", dialect.getName());
	}

	@Test
	@DisplayName("ExpressionObjectFactory가 null이 아니다")
	void testGetExpressionObjectFactoryNotNull() {
		IExpressionObjectFactory factory = dialect.getExpressionObjectFactory();
		assertNotNull(factory);
	}

	@Test
	@DisplayName("ExpressionObject 이름 목록에 egovKrdsPaginationRenderer가 포함된다")
	void testExpressionObjectNames() {
		IExpressionObjectFactory factory = dialect.getExpressionObjectFactory();
		Set<String> names = factory.getAllExpressionObjectNames();
		assertTrue(names.contains("egovKrdsPaginationRenderer"));
	}

	@Test
	@DisplayName("buildObject는 주입된 renderer 인스턴스를 반환한다")
	void testBuildObjectReturnsRenderer() {
		IExpressionObjectFactory factory = dialect.getExpressionObjectFactory();
		Object result = factory.buildObject(null, "egovKrdsPaginationRenderer");
		assertEquals(renderer, result);
	}

	@Test
	@DisplayName("isCacheable은 egovKrdsPaginationRenderer에 대해 true를 반환한다")
	void testIsCacheable() {
		IExpressionObjectFactory factory = dialect.getExpressionObjectFactory();
		assertTrue(factory.isCacheable("egovKrdsPaginationRenderer"));
	}

}
