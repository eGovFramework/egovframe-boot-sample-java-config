package egovframework.example.sample.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * SampleDefaultVO 단위 테스트
 *
 * 검색 조건, 페이지 정보 등 기본 VO 필드의 기본값과
 * setter/getter 동작을 검증한다.
 */
class SampleDefaultVOTest {

	private SampleDefaultVO vo;

	@BeforeEach
	void setUp() {
		vo = new SampleDefaultVO();
	}

	@Test
	@DisplayName("기본값 확인 - 검색조건 및 키워드는 빈 문자열")
	void defaultValues_searchFields() {
		assertThat(vo.getSearchCondition()).isEmpty();
		assertThat(vo.getSearchKeyword()).isEmpty();
		assertThat(vo.getSearchUseYn()).isEmpty();
	}

	@Test
	@DisplayName("기본값 확인 - 페이지 관련 필드는 1 또는 10")
	void defaultValues_pageFields() {
		assertThat(vo.getPageIndex()).isEqualTo(1);
		assertThat(vo.getPageUnit()).isEqualTo(10);
		assertThat(vo.getPageSize()).isEqualTo(10);
		assertThat(vo.getFirstIndex()).isEqualTo(1);
		assertThat(vo.getLastIndex()).isEqualTo(1);
		assertThat(vo.getRecordCountPerPage()).isEqualTo(10);
	}

	@Test
	@DisplayName("검색조건 setter/getter 정상 동작")
	void setterGetter_searchCondition() {
		vo.setSearchCondition("SEARCH_NM");
		vo.setSearchKeyword("테스트");
		vo.setSearchUseYn("Y");

		assertThat(vo.getSearchCondition()).isEqualTo("SEARCH_NM");
		assertThat(vo.getSearchKeyword()).isEqualTo("테스트");
		assertThat(vo.getSearchUseYn()).isEqualTo("Y");
	}

	@Test
	@DisplayName("페이지 관련 setter/getter 정상 동작")
	void setterGetter_pageFields() {
		vo.setPageIndex(3);
		vo.setPageUnit(20);
		vo.setPageSize(5);
		vo.setFirstIndex(41);
		vo.setLastIndex(60);
		vo.setRecordCountPerPage(20);

		assertThat(vo.getPageIndex()).isEqualTo(3);
		assertThat(vo.getPageUnit()).isEqualTo(20);
		assertThat(vo.getPageSize()).isEqualTo(5);
		assertThat(vo.getFirstIndex()).isEqualTo(41);
		assertThat(vo.getLastIndex()).isEqualTo(60);
		assertThat(vo.getRecordCountPerPage()).isEqualTo(20);
	}

	@Test
	@DisplayName("toString 은 null 을 반환하지 않는다")
	void toString_notNull() {
		assertThat(vo.toString()).isNotNull();
	}

}
