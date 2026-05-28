package egovframework.example.sample.service.impl;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;
import java.util.List;

import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import egovframework.example.sample.service.SampleVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * [게시판][SampleMapper.selectSampleList] DAO 단위 테스트
 *
 * @author 이백행
 * @since 2024-09-19
 *
 */
@SpringBootTest
@RequiredArgsConstructor
@Slf4j
class SampleMapperTestSelectSampleListTest {

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
		final SampleVO searchVO = new SampleVO();
		searchVO.setRecordCountPerPage(10);
		searchVO.setFirstIndex(0);
		searchVO.setSearchCondition("1");
		searchVO.setSearchKeyword(sampleVO.getName());

		final List<?> resultList = sampleMapper.selectSampleList(searchVO);

		if (log.isDebugEnabled()) {
			log.debug("searchVO={}", searchVO);
			log.debug("resultList={}", resultList);
		}

		// then
		assertNotNull(resultList, "목록 조회 결과가 null이 아님");
		assertFalse(resultList.isEmpty(), "목록 조회 결과가 비어 있지 않음");
	}

}
