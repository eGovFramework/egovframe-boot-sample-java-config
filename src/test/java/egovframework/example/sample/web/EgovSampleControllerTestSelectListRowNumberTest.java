package egovframework.example.sample.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.egovframe.rte.fdl.cmmn.exception.BaseRuntimeException;
import org.egovframe.rte.fdl.property.impl.EgovPropertyServiceImpl;
import org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

/**
 * [게시판][EgovSampleController.selectList] 목록 행 번호 단위 테스트
 *
 * 페이지당 건수(pageUnit)와 페이지 링크 묶음 크기(pageSize)를 다른 값으로 두고
 * 행 번호가 페이지당 건수를 따라가는지 확인한다.
 *
 * @author wantaek
 * @since 2026.09.09
 */
@SpringBootTest
@AutoConfigureMockMvc
class EgovSampleControllerTestSelectListRowNumberTest {

	private static final int PAGE_INDEX = 2;

	/** 구조화 목록(표형, PC) 의 No 칸 */
	private static final Pattern TABLE_ROW_NUMBER = Pattern.compile("<td class=\"list-no\">(\\d+)</td>");

	/** 구조화 목록(기본형, 모바일) 항목의 No 영역 */
	private static final Pattern CARD_ROW_NUMBER = Pattern
			.compile("<span class=\"list-no\"><span class=\"sr-only\">No</span><span>(\\d+)</span></span>");

	@Autowired
	private MockMvc mockMvc;

	/**
	 * 두 값이 같으면 행 번호 식이 어느 쪽을 쓰든 결과가 같아 검증이 되지 않으므로
	 * pageUnit 과 pageSize 를 다르게 준다.
	 */
	@TestConfiguration
	static class EgovConfigPropertiesTestConfig {

		@Bean(destroyMethod = "destroy")
		EgovPropertyServiceImpl propertiesService() {
			final Map<String, String> properties = new HashMap<>();
			properties.put("pageUnit", "10");
			properties.put("pageSize", "5");

			final EgovPropertyServiceImpl egovPropertyServiceImpl = new EgovPropertyServiceImpl();
			egovPropertyServiceImpl.setProperties(properties);
			return egovPropertyServiceImpl;
		}

	}

	@Test
	@DisplayName("행 번호는 페이지 링크 묶음 크기가 아니라 페이지당 건수만큼 페이지마다 건너뛴다")
	void test_행번호_페이지당건수() throws BaseRuntimeException, Exception {
		// given
		final MvcResult mvcResult = mockMvc
				.perform(post("/egovSampleList.do").param("pageIndex", String.valueOf(PAGE_INDEX)))
				.andExpect(status().isOk())
				.andReturn();

		final PaginationInfo paginationInfo = (PaginationInfo) mvcResult.getModelAndView().getModel()
				.get("paginationInfo");

		assertThat(paginationInfo.getRecordCountPerPage()).isNotEqualTo(paginationInfo.getPageSize());
		assertThat(paginationInfo.getTotalRecordCount()).isGreaterThan(paginationInfo.getRecordCountPerPage());

		// when
		final String content = mvcResult.getResponse().getContentAsString();

		// then
		// 목록은 내림차순이므로 2페이지 첫 행 = 총건수 + 1 - ((2 - 1) * 페이지당건수 + 1)
		final int expected = paginationInfo.getTotalRecordCount() + 1
				- ((PAGE_INDEX - 1) * paginationInfo.getRecordCountPerPage() + 1);

		// PC(표형) · 모바일(기본형) 목록 모두 같은 번호로 시작한다.
		for (final Pattern rowNumber : new Pattern[] { TABLE_ROW_NUMBER, CARD_ROW_NUMBER }) {
			final Matcher matcher = rowNumber.matcher(content);
			assertThat(matcher.find()).isTrue();
			assertThat(Integer.parseInt(matcher.group(1))).isEqualTo(expected);
		}
	}

}
