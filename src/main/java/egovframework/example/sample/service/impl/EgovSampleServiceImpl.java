package egovframework.example.sample.service.impl;

import java.util.List;

import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.egovframe.rte.fdl.cmmn.exception.FdlException;
import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import egovframework.example.sample.service.EgovSampleService;
import egovframework.example.sample.service.SampleDefaultVO;
import egovframework.example.sample.service.SampleVO;
import lombok.RequiredArgsConstructor;

/**
 * 샘플 ServiceImpl
 */
@Service("sampleService")
@RequiredArgsConstructor
public class EgovSampleServiceImpl extends EgovAbstractServiceImpl implements EgovSampleService {

	/**
	 * 로그
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(EgovSampleServiceImpl.class);

	/** SampleDAO */
//	@Resource(name = "sampleMapper")
//	private SampleMapper sampleDAO;
	private final SampleMapper sampleDAO;

	/** ID Generation */
//	@Resource(name = "egovIdGnrService")
//	private EgovIdGnrService egovIdGnrService;
	private final EgovIdGnrService egovIdGnrService;

	/**
	 * 글을 등록한다.
	 * 
	 * @param sampleVO - 등록할 정보가 담긴 SampleVO
	 * @return 등록 결과
	 * @throws Exception
	 */
	@Override
	public String insertSample(final SampleVO sampleVO) throws Exception {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug(sampleVO.toString());
		}

		/** ID Generation Service */
		String id;
		try {
			id = egovIdGnrService.getNextStringId();
		} catch (FdlException e) {
			throw processException("fail.common.msg", e);
		}
		sampleVO.setId(id);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug(sampleVO.toString());
		}

		sampleDAO.insertSample(sampleVO);
		return id;
	}

	/**
	 * 글을 수정한다.
	 * 
	 * @param sampleVO - 수정할 정보가 담긴 SampleVO
	 * @return void형
	 */
	@Override
	public void updateSample(final SampleVO sampleVO) {
		sampleDAO.updateSample(sampleVO);
	}

	/**
	 * 글을 삭제한다.
	 * 
	 * @param sampleVO - 삭제할 정보가 담긴 SampleVO
	 * @return void형
	 */
	@Override
	public void deleteSample(final SampleVO sampleVO) {
		sampleDAO.deleteSample(sampleVO);
	}

	/**
	 * 글을 조회한다.
	 * 
	 * @param sampleVO - 조회할 정보가 담긴 SampleVO
	 * @return 조회한 글
	 * @throws Exception
	 */
	@Override
	public SampleVO selectSample(final SampleVO sampleVO) throws Exception {
		final SampleVO resultVO = sampleDAO.selectSample(sampleVO);
		if (resultVO == null) {
			throw processException("info.nodata.msg");
		}
		return resultVO;
	}

	/**
	 * 글 목록을 조회한다.
	 * 
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @return 글 목록
	 */
	@Override
	public List<?> selectSampleList(final SampleVO searchVO) {
		return sampleDAO.selectSampleList(searchVO);
	}

	/**
	 * 글 총 갯수를 조회한다.
	 * 
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @return 글 총 갯수
	 * @exception
	 */
	@Override
	public int selectSampleListTotCnt(final SampleDefaultVO searchVO) {
		return sampleDAO.selectSampleListTotCnt(searchVO);
	}

}
