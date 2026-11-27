package egovframework.example.sample.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * [게시판] URL 별 HTTP 메서드 제한 테스트. "/" 만 GET 을 허용하고 나머지 URL 은 POST 전용이다.
 * 허용되지 않은 메서드로 요청하면 공통 에러 화면(500)이 아니라 405 와 Allow 헤더로 응답해야 한다.
 */
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class EgovSampleControllerTestHttpMethodTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	@DisplayName("\"/\" 는 GET 으로 목록 화면을 반환한다")
	void index_GET_허용() throws Exception {
		mockMvc.perform(get("/")).andExpect(status().isOk()).andExpect(view().name("sample/egovSampleList"));
	}

	@Test
	@DisplayName("\"/\" 는 POST 를 허용하지 않는다")
	void index_POST_거부() throws Exception {
		mockMvc.perform(post("/")).andExpect(status().isMethodNotAllowed()).andExpect(header().string("Allow", "GET"));
	}

	@ParameterizedTest
	@ValueSource(strings = { "/egovSampleList.do", "/addSampleView.do", "/addSample.do", "/updateSampleView.do",
			"/updateSample.do", "/deleteSample.do" })
	@DisplayName("\"/\" 외 URL 은 GET 을 허용하지 않는다")
	void 나머지URL_GET_거부(String path) throws Exception {
		mockMvc.perform(get(path)).andExpect(status().isMethodNotAllowed()).andExpect(header().string("Allow", "POST"));
	}

	@Test
	@DisplayName("목록 조회는 POST 로 동작한다")
	void 목록_POST_허용() throws Exception {
		mockMvc.perform(post("/egovSampleList.do").param("pageIndex", "1")).andExpect(status().isOk())
				.andExpect(view().name("sample/egovSampleList"));
	}

}
