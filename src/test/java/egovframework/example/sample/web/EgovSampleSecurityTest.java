package egovframework.example.sample.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import egovframework.example.sample.service.SampleVO;

/**
 * [게시판] 보안 점검 항목 테스트 : 보안 헤더, 서버 입력값 검증, 표준 HTTP 상태코드(400/404)
 */
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class EgovSampleSecurityTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	@DisplayName("모든 응답에 보안 헤더가 추가된다")
	void 보안헤더() throws Exception {
		mockMvc.perform(get("/")).andExpect(status().isOk())
				.andExpect(header().string("X-Content-Type-Options", "nosniff"))
				.andExpect(header().string("X-Frame-Options", "DENY"))
				.andExpect(header().string("Referrer-Policy", "strict-origin-when-cross-origin"))
				.andExpect(header().string("Content-Security-Policy", containsString("frame-ancestors 'none'")))
				.andExpect(header().doesNotExist("Strict-Transport-Security"));
		mockMvc.perform(get("/").secure(true))
				.andExpect(header().string("Strict-Transport-Security", containsString("max-age=")));
	}

	@Test
	@DisplayName("없는 URL 은 500 이 아니라 404 로 응답한다")
	void 없는URL_404() throws Exception {
		mockMvc.perform(get("/nonexist")).andExpect(status().isNotFound());
		mockMvc.perform(get("/actuator/env")).andExpect(status().isNotFound());
	}

	@Test
	@DisplayName("숫자 파라미터에 문자를 넣으면 500 이 아니라 400 으로 응답한다")
	void 타입오류_400() throws Exception {
		mockMvc.perform(post("/egovSampleList.do").param("pageIndex", "abc")).andExpect(status().isBadRequest());
	}

	@ParameterizedTest
	@CsvSource({ "0, 1", "-5, 1", "2147483647, LAST" })
	@DisplayName("페이지 번호는 1 ~ 마지막 페이지로 보정된다")
	void 페이지번호_보정(String pageIndex, String expected) throws Exception {
		var result = mockMvc.perform(post("/egovSampleList.do").param("pageIndex", pageIndex))
				.andExpect(status().isOk()).andReturn();
		SampleVO criteria = (SampleVO) result.getModelAndView().getModel().get("sampleVO");
		if ("LAST".equals(expected)) {
			var paginationInfo = (org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationInfo) result.getModelAndView()
					.getModel().get("paginationInfo");
			assertThat(criteria.getPageIndex()).isEqualTo(paginationInfo.getTotalPageCount());
		} else {
			assertThat(criteria.getPageIndex()).isEqualTo(Integer.parseInt(expected));
		}
	}

	@ParameterizedTest
	@ValueSource(strings = { "abc", "2", "" })
	@DisplayName("검색조건이 0/1 이 아니면 검색하지 않고 목록을 보여준다")
	void 검색조건_오류값(String searchCondition) throws Exception {
		mockMvc.perform(post("/egovSampleList.do").param("searchCondition", searchCondition).param("searchKeyword", "x"))
				.andExpect(status().isOk()).andExpect(view().name("sample/egovSampleList"));
	}

	@Test
	@DisplayName("잘못된 language 값은 무시한다")
	void 잘못된로케일_무시() throws Exception {
		mockMvc.perform(get("/").param("language", "../../")).andExpect(status().isOk());
	}

	@ParameterizedTest
	@CsvSource({ "name, 51", "description, 201", "regUser, 11" })
	@DisplayName("DB 컬럼 길이를 넘는 입력은 등록 화면에서 검증 오류로 처리한다")
	void 길이초과_검증(String field, int length) throws Exception {
		var request = post("/addSample.do").param("name", "n").param("description", "d").param("regUser", "u")
				.param("useYn", "Y").param(field, "x".repeat(length));
		mockMvc.perform(request).andExpect(status().isOk()).andExpect(view().name("sample/egovSampleRegister"))
				.andExpect(model().attributeHasFieldErrors("sampleVO", field))
				.andExpect(content().string(containsString("form-error-list")));
	}

	@Test
	@DisplayName("사용여부는 Y/N 만 허용한다")
	void 사용여부_검증() throws Exception {
		mockMvc.perform(post("/addSample.do").param("name", "n").param("description", "d").param("regUser", "u")
				.param("useYn", "ZZ")).andExpect(status().isOk()).andExpect(view().name("sample/egovSampleRegister"))
				.andExpect(model().attributeHasFieldErrors("sampleVO", "useYn"));
	}

	@ParameterizedTest
	@ValueSource(strings = { "/updateSample.do", "/deleteSample.do" })
	@DisplayName("없는 글을 수정/삭제하면 성공으로 처리하지 않는다")
	void 없는글_수정삭제(String path) throws Exception {
		mockMvc.perform(post(path).param("id", "NOPE").param("name", "n").param("description", "d")
				.param("regUser", "u").param("useYn", "Y")).andExpect(view().name("sample/egovSampleError"));
	}

}
