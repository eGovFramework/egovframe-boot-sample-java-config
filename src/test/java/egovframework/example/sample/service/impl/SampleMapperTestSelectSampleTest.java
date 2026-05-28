package egovframework.example.sample.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;

import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import egovframework.example.sample.service.SampleVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * [게시판][SampleMapper.selectSample] DAO 단위 테스트
 *
 * @author 표준프레임워크센터
 * @since 2026-05-29
 */
@SpringBootTest
@RequiredArgsConstructor
@Slf4j
class SampleMapperTestSelectSampleTest {

	@Autowired
	private SampleMapper sampleMapper;

	@Autowired
	private EgovIdGnrService egovIdGnrService;

	@Test
	void test() throws Exception {
		// given
		final SampleVO sampleVO = new SampleVO();
		sampleVO.setId(egovIdGnrService.getNextStringId());

		final String now = LocalDateTime.now().toString();

		sampleVO.setName("test 단건조회 카테고리명 " + now);
		sampleVO.setDescription("test 단건조회 설명 " + now);
		sampleVO.setUseYn("Y");
		sampleVO.setRegUser("eGov");

		sampleMapper.insertSample(sampleVO);

		// when
		final SampleVO resultSampleVO = sampleMapper.selectSample(sampleVO);

		if (log.isDebugEnabled()) {
			log.debug("sampleVO={}", sampleVO);
			log.debug("resultSampleVO={}", resultSampleVO);
		}

		// then
		assertNotNull(resultSampleVO, "단건 조회 결과가 null이 아님");
		assertEquals(sampleVO.getId(), resultSampleVO.getId(), "글을 조회한다. getId");
		assertEquals(sampleVO.getName(), resultSampleVO.getName(), "글을 조회한다. 카테고리명");
		assertEquals(sampleVO.getDescription(), resultSampleVO.getDescription(), "글을 조회한다. 설명");
		assertEquals(sampleVO.getUseYn(), resultSampleVO.getUseYn(), "글을 조회한다. 사용여부");
		assertEquals(sampleVO.getRegUser(), resultSampleVO.getRegUser(), "글을 조회한다. 등록자");
	}

}
