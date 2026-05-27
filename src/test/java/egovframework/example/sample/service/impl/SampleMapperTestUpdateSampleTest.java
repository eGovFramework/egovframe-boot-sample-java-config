package egovframework.example.sample.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import egovframework.example.sample.service.SampleVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * [게시판][SampleMapper.updateSample] DAO 단위 테스트
 *
 * @author 이백행
 * @since 2024-09-19
 *
 */
@SpringBootTest
@RequiredArgsConstructor
@Slf4j
class SampleMapperTestUpdateSampleTest {

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
		final String updatedNow = LocalDateTime.now().toString();
		sampleVO.setName("test 이백행 수정 카테고리명 " + updatedNow);
		sampleVO.setDescription("test 이백행 수정 설명 " + updatedNow);
		sampleVO.setUseYn("N");

		sampleMapper.updateSample(sampleVO);

		// then
		final SampleVO resultSampleVO = sampleMapper.selectSample(sampleVO);

		if (log.isDebugEnabled()) {
			log.debug("sampleVO={}", sampleVO);
			log.debug("resultSampleVO={}", resultSampleVO);

			log.debug("getId={}, {}", sampleVO.getId(), resultSampleVO.getId());
			log.debug("getName={}, {}", sampleVO.getName(), resultSampleVO.getName());
			log.debug("getDescription={}, {}", sampleVO.getDescription(), resultSampleVO.getDescription());
			log.debug("getUseYn={}, {}", sampleVO.getUseYn(), resultSampleVO.getUseYn());
		}

		asserts(sampleVO, resultSampleVO);
	}

	private void asserts(final SampleVO sampleVO, final SampleVO resultSampleVO) {
		assertEquals(sampleVO.getId(), resultSampleVO.getId(), "글을 수정한다. getId");
		assertEquals(sampleVO.getName(), resultSampleVO.getName(), "글을 수정한다. 카테고리명");
		assertEquals(sampleVO.getDescription(), resultSampleVO.getDescription(), "글을 수정한다. 설명");
		assertEquals(sampleVO.getUseYn(), resultSampleVO.getUseYn(), "글을 수정한다. 사용여부");
	}

}
