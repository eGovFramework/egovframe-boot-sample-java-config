package egovframework.example.sample.service.impl;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

import org.egovframe.rte.fdl.cmmn.exception.BaseRuntimeException;
import org.egovframe.rte.fdl.cmmn.exception.FdlException;
import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import egovframework.example.sample.service.SampleVO;
import lombok.extern.slf4j.Slf4j;

/**
 * [게시판][SampleMapper.selectSampleListTotCnt] DAO 단위 테스트
 *
 * @author 이백행
 * @since 2024.09.19
 */
@SpringBootTest
@Slf4j
class SampleMapperTestSelectSampleListTotCntTest {

	@Autowired
	private SampleMapper sampleMapper;

	@Autowired
	private EgovIdGnrService egovIdGnrService;

	@Test
	void test() {
		// given
		final SampleVO sampleVO = new SampleVO();
		try {
			sampleVO.setId(egovIdGnrService.getNextStringId());
		} catch (FdlException e) {
			throw new BaseRuntimeException(e);
		}

		final String now = LocalDateTime.now().toString();

		sampleVO.setName("test 이백행 카테고리명 " + now);
		sampleVO.setDescription("test 이백행 설명 " + now);
		sampleVO.setUseYn("Y");
		sampleVO.setRegUser("eGov");

		sampleMapper.insertSample(sampleVO);

		// when
		final SampleVO searchVO = new SampleVO();
		searchVO.setSearchCondition("1");
		searchVO.setSearchKeyword(sampleVO.getName());

		final int totCnt = sampleMapper.selectSampleListTotCnt(searchVO);

		if (log.isDebugEnabled()) {
			log.debug("searchVO={}", searchVO);
			log.debug("totCnt={}", totCnt);
		}

		// then
		assertTrue(totCnt > 0, "전체 건수가 1 이상임");
	}

}
