package egovframework.example.sample.service.impl;

import java.util.List;

import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
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
	 * @exception Exception
	 */
	@Override
	public String insertSample(final SampleVO sampleVO) throws Exception {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug(sampleVO.toString());
		}

		/** ID Generation Service */
		final String id = egovIdGnrService.getNextStringId();
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
	 * @exception Exception
	 */
	@Override
	public void updateSample(final SampleVO sampleVO) throws Exception {
		sampleDAO.updateSample(sampleVO);
	}

	/**
	 * 글을 삭제한다.
	 * 
	 * @param sampleVO - 삭제할 정보가 담긴 SampleVO
	 * @return void형
	 * @exception Exception
	 */
	@Override
	public void deleteSample(final SampleVO sampleVO) throws Exception {
		sampleDAO.deleteSample(sampleVO);
	}

	/**
	 * 글을 조회한다.
	 * 
	 * @param sampleVO - 조회할 정보가 담긴 SampleVO
	 * @return 조회한 글
	 * @exception Exception
	 */
	@Override
	public SampleVO selectSample(final SampleVO sampleVO) throws Exception {
		final SampleVO resultVO = sampleDAO.selectSample(sampleVO);
		if (resultVO == null)
			throw processException("info.nodata.msg");
		return resultVO;
	}

	/**
	 * 글 목록을 조회한다.
	 * 
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @return 글 목록
	 * @exception Exception
	 */
	@Override
	public List<?> selectSampleList(final SampleVO searchVO) throws Exception {
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
