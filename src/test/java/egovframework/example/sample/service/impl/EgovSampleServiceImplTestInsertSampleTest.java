package egovframework.example.sample.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import egovframework.example.sample.service.EgovSampleService;
import egovframework.example.sample.service.SampleVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * [게시판][EgovSampleServiceImpl.insertSample] ServiceImpl 단위 테스트
 * 
 * @author 이백행
 * @since 2024-09-19
 *
 */

@SpringBootTest

@RequiredArgsConstructor
@Slf4j
class EgovSampleServiceImplTestInsertSampleTest {

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
		String id = egovSampleService.insertSample(sampleVO);

		// then
		final SampleVO resultSampleVO = egovSampleService.selectSample(sampleVO);

		if (log.isDebugEnabled()) {
			log.debug("sampleVO={}", sampleVO);
			log.debug("id={}", id);
			log.debug("resultSampleVO={}", resultSampleVO);

			log.debug("getId={}, {}", sampleVO.getId(), resultSampleVO.getId());
			log.debug("getName={}, {}", sampleVO.getName(), resultSampleVO.getName());
			log.debug("getDescription={}, {}", sampleVO.getDescription(), resultSampleVO.getDescription());
			log.debug("getUseYn={}, {}", sampleVO.getUseYn(), resultSampleVO.getUseYn());
			log.debug("getRegUser={}, {}", sampleVO.getRegUser(), resultSampleVO.getRegUser());
		}

		asserts(sampleVO, resultSampleVO);
	}

	private void asserts(final SampleVO sampleVO, final SampleVO resultSampleVO) {
		assertEquals(sampleVO.getId(), resultSampleVO.getId(), "글을 등록한다. getId");
		assertEquals(sampleVO.getName(), resultSampleVO.getName(), "글을 등록한다. 카테고리명");
		assertEquals(sampleVO.getDescription(), resultSampleVO.getDescription(), "글을 등록한다. 설명");
		assertEquals(sampleVO.getUseYn(), resultSampleVO.getUseYn(), "글을 등록한다. 카테사용여부고리명");
		assertEquals(sampleVO.getRegUser(), resultSampleVO.getRegUser(), "글을 등록한다. 등록자");
	}

}
