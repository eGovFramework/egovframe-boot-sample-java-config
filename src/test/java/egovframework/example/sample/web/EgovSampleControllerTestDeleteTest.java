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
package egovframework.example.sample.web;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
 * [게시판][EgovSampleController.delete] Controller 단위 테스트
 *
 * @author 표준프레임워크센터
 * @since 2025.01.01
 */
@SpringBootTest
@AutoConfigureMockMvc
@RequiredArgsConstructor
@Slf4j
class EgovSampleControllerTestDeleteTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private EgovSampleService egovSampleService;

	@Test
	void test() throws Exception {
		// given - 등록
		final SampleVO insertVO = new SampleVO();
		final String now = LocalDateTime.now().toString();
		insertVO.setName("test 삭제대상 카테고리명 " + now);
		insertVO.setDescription("test 삭제대상 설명 " + now);
		insertVO.setUseYn("Y");
		insertVO.setRegUser("eGov");
		egovSampleService.insertSample(insertVO);

		insertVO.setRecordCountPerPage(10);
		insertVO.setFirstIndex(0);
		insertVO.setSearchCondition("1");
		insertVO.setSearchKeyword(insertVO.getName());
		final List<?> insertedList = egovSampleService.selectSampleList(insertVO);
		final EgovMap insertedRow = (EgovMap) insertedList.get(0);
		final String insertedId = (String) insertedRow.get("id");

		// when
		mockMvc.perform(
				post("/deleteSample.do")
						.param("id", insertedId)
		)
				.andDo(print())
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrlPattern("/egovSampleList.do*"));

		// then - 삭제 후 조회 시 예외 발생
		if (log.isDebugEnabled()) {
			log.debug("insertedId={}", insertedId);
		}

		final SampleVO queryVO = new SampleVO();
		queryVO.setId(insertedId);
		assertThrows(Exception.class, () -> egovSampleService.selectSample(queryVO), "글을 삭제한다. 삭제 후 조회 불가");
	}

}
