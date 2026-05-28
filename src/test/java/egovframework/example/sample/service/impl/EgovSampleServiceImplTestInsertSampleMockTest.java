package egovframework.example.sample.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import egovframework.example.sample.service.SampleVO;

/**
 * [게시판][EgovSampleServiceImpl.insertSample] ServiceImpl Mock 단위 테스트
 *
 * SampleMapper와 EgovIdGnrService를 Mock으로 교체하여
 * 스프링 컨텍스트 없이 insertSample 로직을 검증한다.
 *
 * @author 표준프레임워크센터
 * @since 2026-05-29
 */
@ExtendWith(MockitoExtension.class)
class EgovSampleServiceImplTestInsertSampleMockTest {

	@Mock
	private SampleMapper sampleMapper;

	@Mock
	private EgovIdGnrService egovIdGnrService;

	@InjectMocks
	private EgovSampleServiceImpl sut;

	private SampleVO sampleVO;

	@BeforeEach
	void setUp() {
		sampleVO = new SampleVO();
		sampleVO.setName("테스트 카테고리");
		sampleVO.setDescription("테스트 설명");
		sampleVO.setUseYn("Y");
		sampleVO.setRegUser("eGov");
	}

	@Test
	@DisplayName("글 등록 - ID 생성 서비스에서 ID를 발급받아 VO에 설정하고 매퍼를 호출한다")
	void insertSample_idGeneratedAndMapperCalled() throws Exception {
		// given
		String generatedId = "SAMPLE-001";
		when(egovIdGnrService.getNextStringId()).thenReturn(generatedId);
		doNothing().when(sampleMapper).insertSample(any(SampleVO.class));

		// when
		sut.insertSample(sampleVO);

		// then
		assertEquals(generatedId, sampleVO.getId(), "글을 등록한다. 생성된 ID가 VO에 설정됨");
		verify(egovIdGnrService).getNextStringId();
		verify(sampleMapper).insertSample(sampleVO);
	}

	@Test
	@DisplayName("글 등록 - 등록 전 VO의 ID는 null이다")
	void insertSample_idIsNullBeforeInsert() throws Exception {
		// given
		when(egovIdGnrService.getNextStringId()).thenReturn("SAMPLE-002");
		doNothing().when(sampleMapper).insertSample(any(SampleVO.class));

		// when
		sut.insertSample(sampleVO);

		// then
		assertEquals("SAMPLE-002", sampleVO.getId(), "글을 등록한다. ID 생성 서비스가 반환한 값이 설정됨");
	}

}
