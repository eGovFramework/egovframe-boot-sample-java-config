package egovframework.example.sample.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.egovframe.rte.fdl.cmmn.exception.BaseRuntimeException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import egovframework.example.sample.service.EgovSampleService;
import egovframework.example.sample.service.SampleVO;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class EgovSamplePaginationTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private EgovSampleService sampleService;

	@ParameterizedTest
	@ValueSource(strings = { "0", "-1", "101", "2147483647" })
	void invalidPageSizesUseDefaults(String value) throws BaseRuntimeException, Exception {
		for (String path : new String[] { "/", "/egovSampleList.do" }) {
			var result = mockMvc.perform(get(path).param("pageUnit", value).param("pageSize", value))
					.andExpect(status().isOk()).andReturn();
			SampleVO criteria = (SampleVO) result.getModelAndView().getModel().get("sampleVO");
			assertThat(criteria.getPageUnit()).isEqualTo(10);
			assertThat(criteria.getPageSize()).isEqualTo(10);
		}
	}

	@Test
	void customPageSizesAreRenderedAndUsedForNextPage() throws BaseRuntimeException, Exception {
		var result = mockMvc
				.perform(
						get("/egovSampleList.do").param("pageIndex", "2").param("pageUnit", "5").param("pageSize", "3"))
				.andExpect(status().isOk()).andExpect(content().string(containsString("value=\"5\" selected=\"selected\"")))
				.andExpect(content().string(containsString("name=\"pageSize\" value=\"3\""))).andReturn();
		SampleVO criteria = (SampleVO) result.getModelAndView().getModel().get("sampleVO");
		assertThat(criteria.getFirstIndex()).isEqualTo(5);
		assertThat(criteria.getRecordCountPerPage()).isEqualTo(5);
	}

	@Test
	void detailAndCrudRedirectsKeepPageSizes() throws BaseRuntimeException, Exception {
		SampleVO sample = new SampleVO();
		sample.setName("pagination test");
		sample.setDescription("pagination test");
		sample.setUseYn("Y");
		sample.setRegUser("tester");
		sampleService.insertSample(sample);

		for (String path : new String[] { "/addSampleView.do", "/updateSampleView.do" }) {
			mockMvc.perform(post(path).param("id", sample.getId()).param("pageUnit", "5").param("pageSize", "3"))
					.andExpect(status().isOk())
					.andExpect(content().string(containsString("name=\"pageUnit\" value=\"5\"")))
					.andExpect(content().string(containsString("name=\"pageSize\" value=\"3\"")));
		}
		for (String path : new String[] { "/updateSample.do", "/deleteSample.do", "/addSample.do" }) {
			var result = mockMvc
					.perform(post(path).param("id", sample.getId()).param("name", sample.getName())
							.param("description", sample.getDescription()).param("useYn", "Y")
							.param("regUser", "tester").param("pageUnit", "5").param("pageSize", "3"))
					.andExpect(status().is3xxRedirection()).andReturn();
			assertThat(result.getResponse().getRedirectedUrl()).contains("pageUnit=5", "pageSize=3");
		}
	}
}
