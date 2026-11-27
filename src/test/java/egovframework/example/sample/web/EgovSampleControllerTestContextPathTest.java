package egovframework.example.sample.web;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.util.StringUtils;

/**
 * [게시판] 컨텍스트 패스 하위에 배포했을 때 화면이 만드는 폼 액션 URL 검증
 *
 * @since 2026.09.09
 */

@SpringBootTest
@AutoConfigureMockMvc
class EgovSampleControllerTestContextPathTest {

	private static final String CONTEXT_PATH = "/sample";

	@Autowired
	private MockMvc mockMvc;

	@Test
	void test_목록화면_폼액션에_컨텍스트패스가_붙는다() throws Exception {
		final String html = render(post(CONTEXT_PATH + "/egovSampleList.do").contextPath(CONTEXT_PATH));

		assertContextPath(html, "/egovSampleList.do");
		assertContextPath(html, "/addSampleView.do");
		assertContextPath(html, "/updateSampleView.do");
	}

	@Test
	void test_등록화면_폼액션에_컨텍스트패스가_붙는다() throws Exception {
		final String html = render(post(CONTEXT_PATH + "/addSampleView.do").contextPath(CONTEXT_PATH));

		assertContextPath(html, "/egovSampleList.do");
		assertContextPath(html, "/addSample.do");
		assertContextPath(html, "/updateSample.do");
		assertContextPath(html, "/deleteSample.do");
	}

	/**
	 * 응답 본문을 읽는다. 자바스크립트 인라인 출력은 슬래시를 이스케이프하므로 되돌린다.
	 */
	private String render(final RequestBuilder requestBuilder) throws Exception {
		return mockMvc.perform(requestBuilder).andExpect(status().isOk()).andReturn().getResponse()
				.getContentAsString().replace("\\/", "/");
	}

	/**
	 * 화면에 나타난 해당 경로가 모두 컨텍스트 패스로 시작하는지 확인한다.
	 */
	private void assertContextPath(final String html, final String path) {
		assertEquals(StringUtils.countOccurrencesOf(html, path),
				StringUtils.countOccurrencesOf(html, CONTEXT_PATH + path),
				path + " 폼 액션에 컨텍스트 패스가 빠졌다");
	}

}
