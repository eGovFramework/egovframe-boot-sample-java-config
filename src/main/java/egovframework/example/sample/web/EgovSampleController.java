/*
 * Copyright (c) 2009-2026 MOIS (MINISTRY OF THE INTERIOR AND SAFETY).
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

import java.util.List;

import org.egovframe.rte.fdl.property.EgovPropertyService;
import org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import egovframework.example.sample.service.EgovSampleService;
import egovframework.example.sample.service.SampleVO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @Class Name : EgovSampleController.java
 * @Description : EgovSample Controller Class
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ---------   ---------   -------------------------------
 * @ 2009.03.16                최초생성
 * @ 2026.07.01  이백행          [2026년 컨트리뷰션] 불필요한 import 및 ModelAttribute name 속성 제거
 *   2026.07.16  이백행          [2026년 컨트리뷰션] 불필요한 예외 제거
 *
 * @author 개발프레임웍크 실행환경 개발팀
 * @since 2009. 03.16
 * @version 1.0
 * @see
 */
@Controller
@RequiredArgsConstructor
@Slf4j
public class EgovSampleController {

	private static final int MAX_PAGE_UNIT = 100;
	private static final int MAX_PAGE_SIZE = 100;

	/** EgovSampleService */
	private final EgovSampleService sampleService;

	/** EgovPropertyService */
	private final EgovPropertyService propertiesService;

	@GetMapping("/")
	public String index(@ModelAttribute SampleVO sampleVO, Model model) {
		return this.selectSampleList(sampleVO, model);
	}

	/**
	 * 글 목록을 조회한다. (pageing)
	 * @param sampleVO - 조회할 정보가 담긴 SampleDefaultVO
	 * @param model
	 * @return "egovSampleList"
	 */
	@GetMapping("/egovSampleList.do")
	public String selectSampleList(@ModelAttribute SampleVO sampleVO, Model model) {

		/** EgovPropertyService.sample */
		sampleVO.setPageUnit(resolvePageValue(sampleVO.getPageUnit(), "pageUnit", MAX_PAGE_UNIT));
		sampleVO.setPageSize(resolvePageValue(sampleVO.getPageSize(), "pageSize", MAX_PAGE_SIZE));

		/** pageing setting */
		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(sampleVO.getPageIndex());
		paginationInfo.setRecordCountPerPage(sampleVO.getPageUnit());
		paginationInfo.setPageSize(sampleVO.getPageSize());

		sampleVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		sampleVO.setLastIndex(paginationInfo.getLastRecordIndex());
		sampleVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

		/** List */
		List<SampleVO> sampleList = sampleService.selectSampleList(sampleVO);
		model.addAttribute("resultList", sampleList);

		/** Count */
		int totCnt = sampleService.selectSampleListTotCnt(sampleVO);
		paginationInfo.setTotalRecordCount(totCnt);

		/** Pagination */
		model.addAttribute("paginationInfo", paginationInfo);

		return "sample/egovSampleList";
	}

	/**
	 * 글 등록 화면을 조회한다.
	 * @param sampleVO - 목록 조회조건 정보가 담긴 VO
	 * @param model
	 * @return "egovSampleRegister"
	 */
	@PostMapping("/addSampleView.do")
	public String addSampleView( @ModelAttribute SampleVO sampleVO, Model model) {

		model.addAttribute("sampleVO", sampleVO);

		return "sample/egovSampleRegister";
	}

	/**
	 * 글을 등록한다.
	 * @param sampleVO - 등록할 정보가 담긴 VO
	 * @param status
	 * @return "forward:/egovSampleList.do"
	 */
	@PostMapping("/addSample.do")
	public String addSample(@Valid @ModelAttribute SampleVO sampleVO, BindingResult bindingResult, Model model,
			RedirectAttributes redirectAttributes, SessionStatus status) {

		if (bindingResult.hasErrors()) {
			model.addAttribute("sampleVO", sampleVO);
			return "sample/egovSampleRegister";
		}

		int result = sampleService.insertSample(sampleVO);
		log.debug("result={}", result);
		status.setComplete();
		addPaginationAttributes(sampleVO, redirectAttributes);

		return "redirect:/egovSampleList.do";
	}

	/**
	 * 글 수정화면을 조회한다.
	 * @param sampleVO - 수정할 글 정보가 담긴 VO
	 * @param model
	 * @return "egovSampleRegister"
	 */
	@PostMapping("/updateSampleView.do")
	public String updateSampleView(@ModelAttribute SampleVO sampleVO, Model model) {

		SampleVO detail = sampleService.selectSample(sampleVO);
		detail.setSearchCondition(sampleVO.getSearchCondition());
		detail.setSearchKeyword(sampleVO.getSearchKeyword());
		detail.setPageIndex(sampleVO.getPageIndex());
		detail.setPageUnit(sampleVO.getPageUnit());
		detail.setPageSize(sampleVO.getPageSize());

		model.addAttribute("sampleVO", detail);

		return "sample/egovSampleRegister";
	}

	/**
	 * 글을 수정한다.
	 * @param sampleVO - 수정할 정보가 담긴 VO
	 * @param status
	 * @return "forward:/egovSampleList.do"
	 */
	@PostMapping("/updateSample.do")
	public String updateSample(@Valid @ModelAttribute SampleVO sampleVO, BindingResult bindingResult,
			Model model, RedirectAttributes redirectAttributes, SessionStatus status) {

		if (bindingResult.hasErrors()) {
			model.addAttribute("sampleVO", sampleVO);
			return "sample/egovSampleRegister";
		}

		int result = sampleService.updateSample(sampleVO);
		log.debug("result={}", result);
		status.setComplete();

		redirectAttributes.addAttribute("searchCondition", sampleVO.getSearchCondition());
		redirectAttributes.addAttribute("searchKeyword", sampleVO.getSearchKeyword());
		redirectAttributes.addAttribute("pageIndex", sampleVO.getPageIndex());
		addPaginationAttributes(sampleVO, redirectAttributes);

		return "redirect:/egovSampleList.do";
	}

	/**
	 * 글을 삭제한다.
	 * @param sampleVO - 삭제할 정보가 담긴 VO
	 * @param status
	 * @return "forward:/egovSampleList.do"
	 */
	@PostMapping("/deleteSample.do")
	public String deleteSample(@ModelAttribute SampleVO sampleVO, RedirectAttributes redirectAttributes, SessionStatus status) {

		int result = sampleService.deleteSample(sampleVO);
		log.debug("result={}", result);
		status.setComplete();

		redirectAttributes.addAttribute("searchCondition", sampleVO.getSearchCondition());
		redirectAttributes.addAttribute("searchKeyword", sampleVO.getSearchKeyword());
		redirectAttributes.addAttribute("pageIndex", sampleVO.getPageIndex());
		addPaginationAttributes(sampleVO, redirectAttributes);

		return "redirect:/egovSampleList.do";
	}

	private int resolvePageValue(Integer value, String property, int maximum) {
		if (value != null && value > 0 && value <= maximum) {
			return value;
		}
		// 요청값뿐 아니라 서버 기본값도 안전한 범위로 제한한다.
		return Math.max(1, Math.min(propertiesService.getInt(property), maximum));
	}

	private void addPaginationAttributes(SampleVO sampleVO, RedirectAttributes redirectAttributes) {
		if (sampleVO.getPageUnit() != null) {
			redirectAttributes.addAttribute("pageUnit", sampleVO.getPageUnit());
		}
		if (sampleVO.getPageSize() != null) {
			redirectAttributes.addAttribute("pageSize", sampleVO.getPageSize());
		}
	}

}
