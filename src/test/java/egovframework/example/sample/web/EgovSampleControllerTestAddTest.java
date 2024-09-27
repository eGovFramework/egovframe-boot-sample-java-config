package egovframework.example.sample.web;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.time.LocalDateTime;
import java.util.List;

import org.egovframe.rte.psl.dataaccess.util.EgovMap;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import egovframework.example.sample.service.EgovSampleService;
import egovframework.example.sample.service.SampleVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * [게시판][EgovSampleController.add] Controller 단위 테스트
 * 
 * @author 이백행
 * @since 2024-09-19
 *
 */

@SpringBootTest
@AutoConfigureMockMvc

@RequiredArgsConstructor
@Slf4j
class EgovSampleControllerTestAddTest {

	@Autowired
	private MockMvc mockMvc;

	/**
	 * 
	 */
	@Autowired
	private EgovSampleService egovSampleService;

	@Test
	void test() throws Exception {
		// given
		final SampleVO sampleVO = new SampleVO();

		final String now = LocalDateTime.now().toString();
//		final String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("uuuu-MM-dd'T'HH:mm:ss.SSSSSSSSS"));

		// 카테고리명
		sampleVO.setName("test 이백행 카테고리명 " + now);

		// 설명
		sampleVO.setDescription("test 이백행 설명 " + now);

		// 사용여부
		sampleVO.setUseYn("Y");

		// 등록자
		sampleVO.setRegUser("eGov");

		// when
		mockMvc.perform(

				post("/sample/add")

						.param("name", sampleVO.getName())

						.param("description", sampleVO.getDescription())

						.param("useYn", sampleVO.getUseYn())

						.param("regUser", sampleVO.getRegUser())

		).andDo(print());

		// then
		sampleVO.setRecordCountPerPage(10);
		sampleVO.setFirstIndex(0);
		sampleVO.setSearchCondition("1");
		sampleVO.setSearchKeyword(sampleVO.getName());
		final List<?> resultList = egovSampleService.selectSampleList(sampleVO);
		EgovMap result = (EgovMap) resultList.get(0);
		final SampleVO resultSampleVO = new SampleVO();
		resultSampleVO.setName((String) result.get("name"));
		resultSampleVO.setDescription((String) result.get("description"));
		resultSampleVO.setUseYn((String) result.get("useYn"));
		resultSampleVO.setRegUser((String) result.get("regUser"));

		if (log.isDebugEnabled()) {
			log.debug("sampleVO={}", sampleVO);
			log.debug("resultList={}", resultList);

			log.debug("getId={}, {}", sampleVO.getId(), resultSampleVO.getId());
			log.debug("getName={}, {}", sampleVO.getName(), resultSampleVO.getName());
			log.debug("getDescription={}, {}", sampleVO.getDescription(), resultSampleVO.getDescription());
			log.debug("getUseYn={}, {}", sampleVO.getUseYn(), resultSampleVO.getUseYn());
			log.debug("getRegUser={}, {}", sampleVO.getRegUser(), resultSampleVO.getRegUser());
		}

		asserts(sampleVO, resultSampleVO);
	}

	private void asserts(final SampleVO sampleVO, final SampleVO resultSampleVO) {
//		assertEquals(sampleVO.getId(), resultSampleVO.getId(), "글을 등록한다. getId");
		assertEquals(sampleVO.getName(), resultSampleVO.getName(), "글을 등록한다. 카테고리명");
		assertEquals(sampleVO.getDescription(), resultSampleVO.getDescription(), "글을 등록한다. 설명");
		assertEquals(sampleVO.getUseYn(), resultSampleVO.getUseYn(), "글을 등록한다. 카테사용여부고리명");
		assertEquals(sampleVO.getRegUser(), resultSampleVO.getRegUser(), "글을 등록한다. 등록자");
	}

}
