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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * [게시판][EgovSampleController.selectList] Controller 단위 테스트
 *
 * @author 표준프레임워크센터
 * @since 2025.01.01
 */
@SpringBootTest
@AutoConfigureMockMvc
@RequiredArgsConstructor
@Slf4j
class EgovSampleControllerTestSelectListTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void test_목록조회_기본() throws Exception {
		mockMvc.perform(get("/egovSampleList.do"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(view().name("sample/egovSampleList"))
				.andExpect(model().attributeExists("resultList"))
				.andExpect(model().attributeExists("paginationInfo"));
	}

	@Test
	void test_목록조회_검색조건_이름() throws Exception {
		mockMvc.perform(
				get("/egovSampleList.do")
						.param("searchCondition", "1")
						.param("searchKeyword", "테스트")
						.param("pageIndex", "1")
		)
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(view().name("sample/egovSampleList"))
				.andExpect(model().attributeExists("resultList"))
				.andExpect(model().attributeExists("paginationInfo"));
	}

	@Test
	void test_인덱스_리다이렉트() throws Exception {
		mockMvc.perform(get("/"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(view().name("sample/egovSampleList"));
	}

}
