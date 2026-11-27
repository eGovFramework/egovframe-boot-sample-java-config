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
package egovframework.example.sample.service.impl;

import java.util.List;

import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.egovframe.rte.fdl.cmmn.exception.BaseRuntimeException;
import org.egovframe.rte.fdl.cmmn.exception.FdlException;
import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;
import org.springframework.stereotype.Service;

import egovframework.example.sample.service.EgovSampleService;
import egovframework.example.sample.service.SampleVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 게시판 Service 구현 (ID 생성, 대상 글이 없으면 예외)
 *
 * @Modification Information
 *  수정일      수정자  수정내용
 *  ----------  ------  ----------------------------------------
 *  2009.03.16          최초생성
 *  2026.06.19          [2026년 컨트리뷰션] 문자열 기반 설정 제거
 *  2026.06.25          [2026년 컨트리뷰션] 생성자 주입으로 변경
 *  2026.07.04  정찬영  [2026년 컨트리뷰션] 미사용 import 제거 및 @Slf4j로 로거 선언 통일
 *  2026.07.16  이백행  [2026년 컨트리뷰션] 불필요한 예외 제거
 *
 * @author 개발프레임워크 실행환경 개발팀
 * @since 2009.03.16
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class EgovSampleServiceImpl extends EgovAbstractServiceImpl implements EgovSampleService {

	private final SampleMapper sampleMapper;

	private final EgovIdGnrService egovIdGnrService;

	@Override
	public int insertSample(SampleVO sampleVO) {
		log.debug("id={}", sampleVO.getId());

		// ID 생성 (IDS 테이블)
		try {
			sampleVO.setId(egovIdGnrService.getNextStringId());
		} catch (FdlException e) {
			throw new BaseRuntimeException(e);
		}
		log.debug("id={}", sampleVO.getId());

		return sampleMapper.insertSample(sampleVO);
	}

	@Override
	public int updateSample(SampleVO sampleVO) {
		int result = sampleMapper.updateSample(sampleVO);
		if (result == 0) {
			throw new BaseRuntimeException(processException("info.nodata.msg"));
		}
		return result;
	}

	@Override
	public int deleteSample(SampleVO sampleVO) {
		int result = sampleMapper.deleteSample(sampleVO);
		if (result == 0) {
			throw new BaseRuntimeException(processException("info.nodata.msg"));
		}
		return result;
	}

	@Override
	public SampleVO selectSample(SampleVO sampleVO) {
		SampleVO resultVO = sampleMapper.selectSample(sampleVO);
		if (resultVO == null) {
			throw new BaseRuntimeException(processException("info.nodata.msg"));
		}
		return resultVO;
	}

	@Override
	public List<SampleVO> selectSampleList(SampleVO sampleVO) {
		return sampleMapper.selectSampleList(sampleVO);
	}

	@Override
	public int selectSampleListTotCnt(SampleVO sampleVO) {
		return sampleMapper.selectSampleListTotCnt(sampleVO);
	}

}
