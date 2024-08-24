package egovframework.example.sample.web;

import javax.validation.Valid;

import org.egovframe.rte.fdl.property.EgovPropertyService;
import org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import egovframework.example.sample.service.EgovSampleService;
import egovframework.example.sample.service.SampleVO;
import lombok.RequiredArgsConstructor;

/**
 * 샘플 Controller
 */
@Controller
@RequiredArgsConstructor
public class EgovSampleController {

	/** EgovSampleService */
//	@Resource(name = "sampleService")
//	private EgovSampleService sampleService;
	private final EgovSampleService sampleService;

	/** EgovPropertyService */
//	@Resource(name = "propertiesService")
//	protected EgovPropertyService propertiesService;
	protected final EgovPropertyService propertiesService;

	/**
	 * 검색
	 * 
	 * @param sampleVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/")
	public String search(final @ModelAttribute SampleVO sampleVO, final Model model) throws Exception {
		return this.list(sampleVO, model);
	}

	/**
	 * 샘플 목록화면
	 * 
	 * @param sampleVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/sample/list")
	public String list(final @ModelAttribute SampleVO sampleVO, final Model model) throws Exception {
		sampleVO.setPageUnit(propertiesService.getInt("pageUnit"));
		sampleVO.setPageSize(propertiesService.getInt("pageSize"));

		// pagination setting
		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(sampleVO.getPageIndex());
		paginationInfo.setRecordCountPerPage(sampleVO.getPageUnit());
		paginationInfo.setPageSize(sampleVO.getPageSize());

		sampleVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		sampleVO.setLastIndex(paginationInfo.getLastRecordIndex());
		sampleVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

		// List
		model.addAttribute("resultList", sampleService.selectSampleList(sampleVO));
		// Count
		paginationInfo.setTotalRecordCount(sampleService.selectSampleListTotCnt(sampleVO));
		// Pagination
		model.addAttribute("paginationInfo", paginationInfo);

		return "egovSampleList";
	}

	/**
	 * 샘플 상세화면
	 * 
	 * @param sampleVO
	 * @param id
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/sample/detail")
	public String detail(final @ModelAttribute SampleVO sampleVO, final @RequestParam String id, final Model model)
			throws Exception {
		sampleVO.setId(id);
		SampleVO detail = this.sampleService.selectSample(sampleVO);
		model.addAttribute("sampleVO", detail);
		return "egovSampleRegister";
	}

	/**
	 * 샘플 등록화면
	 * 
	 * @param sampleVO
	 * @return
	 */
	@GetMapping("/sample/add")
	public String form(final @ModelAttribute SampleVO sampleVO) {
		return "egovSampleRegister";
	}

	/**
	 * 샘플 등록처리
	 * 
	 * @param sampleVO
	 * @param bindingResult
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/sample/add")
	public String add(final @Valid @ModelAttribute SampleVO sampleVO, final BindingResult bindingResult)
			throws Exception {
		if (bindingResult.hasErrors()) {
			return "egovSampleRegister";
		}
		this.sampleService.insertSample(sampleVO);
		return "redirect:/";
	}

	/**
	 * 샘플 수정처리
	 * 
	 * @param sampleVO
	 * @param bindingResult
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/sample/update")
	public String update(final @Valid @ModelAttribute SampleVO sampleVO, final BindingResult bindingResult)
			throws Exception {
		if (bindingResult.hasErrors()) {
			return "egovSampleRegister";
		}
		this.sampleService.updateSample(sampleVO);
		return "redirect:/";
	}

	/**
	 * 샘플 삭제처리
	 * 
	 * @param sampleVO
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/sample/delete")
	public String delete(final @ModelAttribute SampleVO sampleVO) throws Exception {
		this.sampleService.deleteSample(sampleVO);
		return "redirect:/";
	}

}
