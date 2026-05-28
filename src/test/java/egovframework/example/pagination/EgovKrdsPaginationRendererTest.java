package egovframework.example.pagination;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Locale;

import org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.MessageSource;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * EgovKrdsPaginationRenderer 단위 테스트
 *
 * @author 이백행
 * @since 2026-05-28
 */
class EgovKrdsPaginationRendererTest {

	private EgovKrdsPaginationRenderer renderer;
	private MessageSource messageSource;

	@BeforeEach
	void setUp() {
		messageSource = mock(MessageSource.class);
		when(messageSource.getMessage(eq("page.prev"), isNull(), eq("이전"), any(Locale.class))).thenReturn("이전");
		when(messageSource.getMessage(eq("page.next"), isNull(), eq("다음"), any(Locale.class))).thenReturn("다음");

		renderer = new EgovKrdsPaginationRenderer();
		ReflectionTestUtils.setField(renderer, "messageSource", messageSource);
		renderer.initMessageLabels();
	}

	@Test
	@DisplayName("initVariables 호출 후 previousPageLabel이 null이 아니다")
	void testInitVariablesPreviousPageLabel() {
		renderer.initVariables();
		String label = (String) ReflectionTestUtils.getField(renderer, "previousPageLabel");
		assertThat(label).isNotNull().contains("page-navi prev");
	}

	@Test
	@DisplayName("initVariables 호출 후 nextPageLabel이 null이 아니다")
	void testInitVariablesNextPageLabel() {
		renderer.initVariables();
		String label = (String) ReflectionTestUtils.getField(renderer, "nextPageLabel");
		assertThat(label).isNotNull().contains("page-navi next");
	}

	@Test
	@DisplayName("initVariables 호출 후 currentPageLabel이 active 클래스를 포함한다")
	void testInitVariablesCurrentPageLabel() {
		renderer.initVariables();
		String label = (String) ReflectionTestUtils.getField(renderer, "currentPageLabel");
		assertThat(label).isNotNull().contains("active");
	}

	@Test
	@DisplayName("initVariables 호출 후 dotPageLabel이 ... 을 포함한다")
	void testInitVariablesDotPageLabel() {
		renderer.initVariables();
		String label = (String) ReflectionTestUtils.getField(renderer, "dotPageLabel");
		assertThat(label).isNotNull().contains("...");
	}

	@Test
	@DisplayName("initMessageLabels 이후 previousPageLabel에 메시지소스 값이 반영된다")
	void testInitMessageLabelsPreviousLabel() {
		String label = (String) ReflectionTestUtils.getField(renderer, "previousPageLabel");
		assertThat(label).contains("이전");
	}

	@Test
	@DisplayName("initMessageLabels 이후 nextPageLabel에 메시지소스 값이 반영된다")
	void testInitMessageLabelsNextLabel() {
		String label = (String) ReflectionTestUtils.getField(renderer, "nextPageLabel");
		assertThat(label).contains("다음");
	}

	@Test
	@DisplayName("initMessageLabels 이후 previousPageDisabledLabel에 disabled 클래스가 포함된다")
	void testInitMessageLabelsPreviousDisabledLabel() {
		String label = (String) ReflectionTestUtils.getField(renderer, "previousPageDisabledLabel");
		assertThat(label).contains("disabled").contains("이전");
	}

	@Test
	@DisplayName("initMessageLabels 이후 nextPageDisabledLabel에 disabled 클래스가 포함된다")
	void testInitMessageLabelsNextDisabledLabel() {
		String label = (String) ReflectionTestUtils.getField(renderer, "nextPageDisabledLabel");
		assertThat(label).contains("disabled").contains("다음");
	}

	@Test
	@DisplayName("renderPagination은 null이 아닌 HTML 문자열을 반환한다")
	void testRenderPaginationReturnsHtml() {
		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(1);
		paginationInfo.setRecordCountPerPage(10);
		paginationInfo.setPageSize(5);
		paginationInfo.setTotalRecordCount(50);

		String result = renderer.renderPagination(paginationInfo, "fn_egov_link_page");
		assertThat(result).isNotNull().isNotEmpty();
	}

	@Test
	@DisplayName("renderPagination 결과에 페이지 링크 HTML이 포함된다")
	void testRenderPaginationContainsPageLink() {
		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(3);
		paginationInfo.setRecordCountPerPage(10);
		paginationInfo.setPageSize(5);
		paginationInfo.setTotalRecordCount(100);

		String result = renderer.renderPagination(paginationInfo, "fn_egov_link_page");
		assertThat(result).contains("fn_egov_link_page");
	}

}
