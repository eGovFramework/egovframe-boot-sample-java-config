package egovframework.example.sample.web;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.egovframe.rte.fdl.cmmn.exception.BaseRuntimeException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * [게시판][EgovSampleController.addSampleView, updateSampleView] Controller 단위 테스트
 *
 * 등록/수정 화면의 maxlength 가 SAMPLE 테이블 컬럼 폭(NAME 50, DESCRIPTION 200, REG_USER 10)을
 * 넘지 않는지 확인한다. 넘으면 입력값이 INSERT/UPDATE 단계에서 잘림 예외로 떨어진다.
 *
 * @author wantaek
 * @since 2026.09.09
 */

@SpringBootTest
@AutoConfigureMockMvc

@RequiredArgsConstructor
@Slf4j
class EgovSampleControllerTestRegisterViewTest {

	/** SAMPLE.NAME 컬럼 폭 */
	private static final int NAME_COLUMN_SIZE = 50;

	/** SAMPLE.DESCRIPTION 컬럼 폭 */
	private static final int DESCRIPTION_COLUMN_SIZE = 200;

	/** SAMPLE.REG_USER 컬럼 폭 */
	private static final int REG_USER_COLUMN_SIZE = 10;

	@Autowired
	private MockMvc mockMvc;

	@Test
	void testAddSampleView() throws BaseRuntimeException, Exception {
		// given, when
		final String html = mockMvc.perform(

				post("/addSampleView.do")

		).andExpect(status().isOk())

				.andExpect(view().name("sample/egovSampleRegister"))

				.andReturn().getResponse().getContentAsString();

		if (log.isDebugEnabled()) {
			log.debug("html={}", html);
		}

		// then
		assertEquals(NAME_COLUMN_SIZE, maxlength(html, "name"), "등록 화면 카테고리명 maxlength");
		assertEquals(DESCRIPTION_COLUMN_SIZE, maxlength(html, "description"), "등록 화면 설명 maxlength");
		assertEquals(REG_USER_COLUMN_SIZE, maxlength(html, "regUser"), "등록 화면 등록자 maxlength");
	}

	@Test
	void testUpdateSampleView() throws BaseRuntimeException, Exception {
		// given, when
		final String html = mockMvc.perform(

				post("/updateSampleView.do")

						.param("id", "SAMPLE-00001")

		).andExpect(status().isOk())

				.andExpect(view().name("sample/egovSampleRegister"))

				.andReturn().getResponse().getContentAsString();

		if (log.isDebugEnabled()) {
			log.debug("html={}", html);
		}

		// then
		assertEquals(NAME_COLUMN_SIZE, maxlength(html, "name"), "수정 화면 카테고리명 maxlength");
		assertEquals(DESCRIPTION_COLUMN_SIZE, maxlength(html, "description"), "수정 화면 설명 maxlength");
		assertEquals(REG_USER_COLUMN_SIZE, maxlength(html, "regUser"), "수정 화면 등록자 maxlength");
	}

	private int maxlength(final String html, final String id) {
		final Matcher matcher = Pattern.compile("id=\"" + id + "\"[^>]*maxlength=\"(\\d+)\"").matcher(html);
		assertTrue(matcher.find(), id + " 입력 항목의 maxlength");
		return Integer.parseInt(matcher.group(1));
	}

}
