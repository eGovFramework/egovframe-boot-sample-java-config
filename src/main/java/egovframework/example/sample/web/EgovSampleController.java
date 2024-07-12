package egovframework.example.sample.web;

import egovframework.example.sample.service.EgovSampleService;
import egovframework.example.sample.service.SampleVO;
import org.egovframe.rte.fdl.property.EgovPropertyService;
import org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.validation.Valid;

@Controller
public class EgovSampleController {

	/** EgovSampleService */
	@Resource(name = "sampleService")
	private EgovSampleService sampleService;

	/** EgovPropertyService */
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;

	@GetMapping("/")
	public String search(@ModelAttribute SampleVO sampleVO, Model model) throws Exception {
		return this.list(sampleVO, model);
	}

	@PostMapping("/sample/list")
	public String list(@ModelAttribute SampleVO sampleVO, Model model) throws Exception {
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

	@PostMapping("/sample/detail")
	public String detail(@ModelAttribute SampleVO sampleVO, @RequestParam String id, Model model) throws Exception {
		sampleVO.setId(id);
		SampleVO detail = this.sampleService.selectSample(sampleVO);
		model.addAttribute("sampleVO", detail);
		return "egovSampleRegister";
	}

	@GetMapping("/sample/add")
	public String form(@ModelAttribute SampleVO sampleVO) {
		return "egovSampleRegister";
	}

	@PostMapping("/sample/add")
	public String add(@Valid @ModelAttribute SampleVO sampleVO, BindingResult bindingResult) throws Exception {
		if (bindingResult.hasErrors()) {
			return "egovSampleRegister";
		}
		this.sampleService.insertSample(sampleVO);
		return "redirect:/";
	}

	@PostMapping("/sample/update")
	public String update(@Valid @ModelAttribute SampleVO sampleVO, BindingResult bindingResult) throws Exception {
		if (bindingResult.hasErrors()) {
			return "egovSampleRegister";
		}
		this.sampleService.updateSample(sampleVO);
		return "redirect:/";
	}

	@PostMapping("/sample/delete")
	public String delete(@ModelAttribute SampleVO sampleVO) throws Exception {
		this.sampleService.deleteSample(sampleVO);
		return "redirect:/";
	}

}
