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
 * 게시판 Controller (목록 · 등록 · 수정 · 삭제)
 *
 * @Modification Information
 *  수정일      수정자  수정내용
 *  ----------  ------  ----------------------------------------
 *  2009.03.16          최초생성
 *  2026.07.01  이백행  [2026년 컨트리뷰션] 불필요한 import 및 ModelAttribute name 속성 제거
 *  2026.07.16  이백행  [2026년 컨트리뷰션] 불필요한 예외 제거
 *
 * @author 개발프레임워크 실행환경 개발팀
 * @since 2009.03.16
 * @version 1.0
 */
@Controller
@RequiredArgsConstructor
@Slf4j
public class EgovSampleController {

	private static final int MAX_PAGE_UNIT = 100;
	private static final int MAX_PAGE_SIZE = 100;

	private final EgovSampleService sampleService;

	private final EgovPropertyService propertiesService;

	/**
	 * 목록 화면 진입점(유일한 GET URL).
	 * 쿼리스트링은 바인딩하지 않고, 등록/수정/삭제 후 Flash Attribute 로 전달된 조회조건만 사용한다.
	 * @return "egovSampleList"
	 */
	@GetMapping("/")
	public String index(Model model) {
		SampleVO sampleVO = model.getAttribute("sampleVO") instanceof SampleVO flashVO ? flashVO : new SampleVO();
		model.addAttribute("sampleVO", sampleVO);
		return this.selectSampleList(sampleVO, model);
	}

	/**
	 * 글 목록을 조회한다. (페이징)
	 * @param sampleVO 조회할 정보가 담긴 SampleVO
	 * @return "egovSampleList"
	 */
	@PostMapping("/egovSampleList.do")
	public String selectSampleList(@ModelAttribute SampleVO sampleVO, Model model) {

		// 페이징 단위 · 페이지 번호 개수 (기본값 : EgovPropertyService)
		sampleVO.setPageUnit(resolvePageValue(sampleVO.getPageUnit(), "pageUnit", MAX_PAGE_UNIT));
		sampleVO.setPageSize(resolvePageValue(sampleVO.getPageSize(), "pageSize", MAX_PAGE_SIZE));

		// 전체 건수 → 현재 페이지 보정
		int totCnt = sampleService.selectSampleListTotCnt(sampleVO);
		sampleVO.setPageIndex(resolvePageIndex(sampleVO.getPageIndex(), totCnt, sampleVO.getPageUnit()));

		// 페이징 정보
		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(sampleVO.getPageIndex());
		paginationInfo.setRecordCountPerPage(sampleVO.getPageUnit());
		paginationInfo.setPageSize(sampleVO.getPageSize());

		sampleVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		sampleVO.setLastIndex(paginationInfo.getLastRecordIndex());
		sampleVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

		// 목록
		List<SampleVO> sampleList = sampleService.selectSampleList(sampleVO);
		model.addAttribute("resultList", sampleList);

		paginationInfo.setTotalRecordCount(totCnt);
		model.addAttribute("paginationInfo", paginationInfo);

		return "sample/egovSampleList";
	}

	/**
	 * 글 등록 화면을 조회한다.
	 * @param sampleVO 목록 조회조건 정보가 담긴 VO
	 * @return "egovSampleRegister"
	 */
	@PostMapping("/addSampleView.do")
	public String addSampleView( @ModelAttribute SampleVO sampleVO, Model model) {

		model.addAttribute("sampleVO", sampleVO);

		return "sample/egovSampleRegister";
	}

	/**
	 * 글을 등록한다.
	 * @param sampleVO 등록할 정보가 담긴 VO
	 * @return "redirect:/" (GET 은 "/" 만 허용하므로 "/" 로 리다이렉트, 조회조건은 Flash Attribute 로 전달)
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
		addListCondition(sampleVO, false, redirectAttributes);

		return "redirect:/";
	}

	/**
	 * 글 수정 화면을 조회한다.
	 * @param sampleVO 수정할 글 정보가 담긴 VO
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
	 * @param sampleVO 수정할 정보가 담긴 VO
	 * @return "redirect:/" (GET 은 "/" 만 허용하므로 "/" 로 리다이렉트, 조회조건은 Flash Attribute 로 전달)
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
		addListCondition(sampleVO, true, redirectAttributes);

		return "redirect:/";
	}

	/**
	 * 글을 삭제한다.
	 * @param sampleVO 삭제할 정보가 담긴 VO
	 * @return "redirect:/" (GET 은 "/" 만 허용하므로 "/" 로 리다이렉트, 조회조건은 Flash Attribute 로 전달)
	 */
	@PostMapping("/deleteSample.do")
	public String deleteSample(@ModelAttribute SampleVO sampleVO, RedirectAttributes redirectAttributes, SessionStatus status) {

		int result = sampleService.deleteSample(sampleVO);
		log.debug("result={}", result);
		status.setComplete();
		addListCondition(sampleVO, true, redirectAttributes);

		return "redirect:/";
	}

	private int resolvePageValue(Integer value, String property, int maximum) {
		if (value != null && value > 0 && value <= maximum) {
			return value;
		}
		// 요청값뿐 아니라 서버 기본값도 안전한 범위로 제한한다.
		return Math.max(1, Math.min(propertiesService.getInt(property), maximum));
	}

	private int resolvePageIndex(int pageIndex, int totalCount, int pageUnit) {
		// 0, 음수, 마지막 페이지를 넘는 값은 DB 오류(음수 OFFSET, int overflow)가 나지 않도록 1 ~ 마지막 페이지로 보정한다.
		int lastPage = Math.max(1, (totalCount + pageUnit - 1) / pageUnit);
		return Math.max(1, Math.min(pageIndex, lastPage));
	}

	/**
	 * 목록 조회조건을 Flash Attribute 로 전달한다.
	 * GET 은 "/" 만 허용하므로 조회조건을 쿼리스트링(/?pageUnit=...)으로 넘기지 않고 세션 Flash 로 넘긴다.
	 * @param sampleVO 요청에 담긴 조회조건
	 * @param keepSearch 검색조건과 현재 페이지까지 유지할지 여부 (false 면 페이징 단위만 유지)
	 */
	private void addListCondition(SampleVO sampleVO, boolean keepSearch, RedirectAttributes redirectAttributes) {
		SampleVO listVO = new SampleVO();
		if (keepSearch) {
			listVO.setSearchCondition(sampleVO.getSearchCondition());
			listVO.setSearchKeyword(sampleVO.getSearchKeyword());
			listVO.setPageIndex(sampleVO.getPageIndex());
		}
		listVO.setPageUnit(sampleVO.getPageUnit());
		listVO.setPageSize(sampleVO.getPageSize());
		redirectAttributes.addFlashAttribute("sampleVO", listVO);
	}

}
