/*
 * Copyright 2008-2009 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package egovframework.example.sample.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import egovframework.example.sample.service.SampleVO;

/**
 * EgovSampleServiceImpl 단위 테스트
 *
 * SampleMapper와 EgovIdGnrService를 Mock으로 교체하여
 * 스프링 컨텍스트 없이 서비스 레이어 로직만 검증한다.
 *
 * @author 표준프레임워크센터
 * @since 2024.01.01
 * @version 1.0
 */
@ExtendWith(MockitoExtension.class)
class EgovSampleServiceImplTest {

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
		sampleVO.setId("SAMPLE-001");
		sampleVO.setName("테스트 카테고리");
		sampleVO.setDescription("테스트 설명");
		sampleVO.setUseYn("Y");
		sampleVO.setRegUser("eGov");
	}

	@Test
	@DisplayName("글 수정 - 정상적으로 updateSample을 호출한다")
	void updateSample_정상() throws Exception {
		// given
		doNothing().when(sampleMapper).updateSample(sampleVO);

		// when
		sut.updateSample(sampleVO);

		// then
		verify(sampleMapper).updateSample(sampleVO);
	}

	@Test
	@DisplayName("글 삭제 - 정상적으로 deleteSample을 호출한다")
	void deleteSample_정상() throws Exception {
		// given
		doNothing().when(sampleMapper).deleteSample(sampleVO);

		// when
		sut.deleteSample(sampleVO);

		// then
		verify(sampleMapper).deleteSample(sampleVO);
	}

	@Test
	@DisplayName("글 단건 조회 - 존재하는 글을 정상적으로 반환한다")
	void selectSample_정상() throws Exception {
		// given
		SampleVO expected = new SampleVO();
		expected.setId("SAMPLE-001");
		expected.setName("테스트 카테고리");
		when(sampleMapper.selectSample(sampleVO)).thenReturn(expected);

		// when
		SampleVO result = sut.selectSample(sampleVO);

		// then
		assertNotNull(result);
		assertEquals("SAMPLE-001", result.getId());
		assertEquals("테스트 카테고리", result.getName());
	}

	@Test
	@DisplayName("글 단건 조회 - 데이터가 없으면 예외가 발생한다")
	void selectSample_데이터없음_예외() throws Exception {
		// given
		when(sampleMapper.selectSample(sampleVO)).thenReturn(null);

		// when & then
		assertThrows(Exception.class, () -> sut.selectSample(sampleVO));
	}

	@Test
	@DisplayName("글 목록 조회 - 목록을 정상적으로 반환한다")
	@SuppressWarnings("unchecked")
	void selectSampleList_정상() throws Exception {
		// given
		SampleVO item1 = new SampleVO();
		item1.setId("SAMPLE-001");
		SampleVO item2 = new SampleVO();
		item2.setId("SAMPLE-002");
		List<SampleVO> expected = Arrays.asList(item1, item2);
		when((List<SampleVO>) sampleMapper.selectSampleList(sampleVO)).thenReturn(expected);

		// when
		List<?> result = sut.selectSampleList(sampleVO);

		// then
		assertNotNull(result);
		assertEquals(2, result.size());
	}

	@Test
	@DisplayName("글 총 개수 조회 - 전체 건수를 정상적으로 반환한다")
	void selectSampleListTotCnt_정상() {
		// given
		when(sampleMapper.selectSampleListTotCnt(sampleVO)).thenReturn(5);

		// when
		int count = sut.selectSampleListTotCnt(sampleVO);

		// then
		assertEquals(5, count);
	}

}
