package egovframework.example.sample.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * SampleVO 단위 테스트
 *
 * 게시글 VO 필드의 setter/getter 동작과
 * 부모 클래스(SampleDefaultVO) 상속 관계를 검증한다.
 */
class SampleVOTest {

	private SampleVO vo;

	@BeforeEach
	void setUp() {
		vo = new SampleVO();
	}

	@Test
	@DisplayName("새로 생성된 SampleVO 의 필드는 null 이다")
	void newInstance_fieldsAreNull() {
		assertThat(vo.getId()).isNull();
		assertThat(vo.getName()).isNull();
		assertThat(vo.getDescription()).isNull();
		assertThat(vo.getUseYn()).isNull();
		assertThat(vo.getRegUser()).isNull();
	}

	@Test
	@DisplayName("id setter/getter 정상 동작")
	void setterGetter_id() {
		vo.setId("SAMPLE-001");
		assertThat(vo.getId()).isEqualTo("SAMPLE-001");
	}

	@Test
	@DisplayName("name setter/getter 정상 동작")
	void setterGetter_name() {
		vo.setName("테스트 카테고리");
		assertThat(vo.getName()).isEqualTo("테스트 카테고리");
	}

	@Test
	@DisplayName("description setter/getter 정상 동작")
	void setterGetter_description() {
		vo.setDescription("테스트 설명입니다.");
		assertThat(vo.getDescription()).isEqualTo("테스트 설명입니다.");
	}

	@Test
	@DisplayName("useYn setter/getter 정상 동작")
	void setterGetter_useYn() {
		vo.setUseYn("Y");
		assertThat(vo.getUseYn()).isEqualTo("Y");

		vo.setUseYn("N");
		assertThat(vo.getUseYn()).isEqualTo("N");
	}

	@Test
	@DisplayName("regUser setter/getter 정상 동작")
	void setterGetter_regUser() {
		vo.setRegUser("eGov");
		assertThat(vo.getRegUser()).isEqualTo("eGov");
	}

	@Test
	@DisplayName("SampleVO 는 SampleDefaultVO 를 상속한다")
	void inheritsSampleDefaultVO() {
		assertThat(vo).isInstanceOf(SampleDefaultVO.class);
	}

	@Test
	@DisplayName("부모 페이지 필드도 정상 동작한다")
	void parentPageFields_accessible() {
		vo.setPageIndex(2);
		vo.setPageUnit(5);

		assertThat(vo.getPageIndex()).isEqualTo(2);
		assertThat(vo.getPageUnit()).isEqualTo(5);
	}

}
