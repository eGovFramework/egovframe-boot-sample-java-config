package egovframework.example.sample.service.impl;

import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalDateTime;

import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import egovframework.example.sample.service.SampleVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * [게시판][SampleMapper.deleteSample] DAO 단위 테스트
 *
 * @author 이백행
 * @since 2024-09-19
 *
 */
@SpringBootTest
@RequiredArgsConstructor
@Slf4j
class SampleMapperTestDeleteSampleTest {

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

		sampleVO.setName("test 이백행 카테고리명 " + now);
		sampleVO.setDescription("test 이백행 설명 " + now);
		sampleVO.setUseYn("Y");
		sampleVO.setRegUser("eGov");

		sampleMapper.insertSample(sampleVO);

		// when
		sampleMapper.deleteSample(sampleVO);

		// then
		final SampleVO resultSampleVO = sampleMapper.selectSample(sampleVO);

		if (log.isDebugEnabled()) {
			log.debug("sampleVO={}", sampleVO);
			log.debug("resultSampleVO={}", resultSampleVO);
		}

		assertNull(resultSampleVO, "글을 삭제한다. 삭제 후 조회 결과 없음");
	}

}
