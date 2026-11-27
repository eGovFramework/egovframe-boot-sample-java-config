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
package egovframework.example.sample.service;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.egovframe.rte.ptl.reactive.validation.EgovNullCheck;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

/**
 * 게시판 VO (검색 · 페이징 조건 + 게시글 정보)
 *
 * @Modification Information
 *  수정일      수정자  수정내용
 *  ----------  ------  ----------------------------------------
 *  2009.03.16          최초생성
 *  2026.10.27          SampleDefaultVO 통합
 *
 * @author 개발프레임워크 실행환경 개발팀
 * @since 2009.03.16
 * @version 1.0
 */
@Getter
@Setter
public class SampleVO implements Serializable {

	private static final long serialVersionUID = -858838578081269359L;

	/** 검색조건 */
	private String searchCondition = "";

	/** 검색어 */
	private String searchKeyword = "";

	/** 검색 사용여부 */
	private String searchUseYn = "";

	/** 현재 페이지 */
	private int pageIndex = 1;

	/** 페이지당 표시 건수 */
	private Integer pageUnit;

	/** 페이지 번호 표시 개수 */
	private Integer pageSize;

	/** 조회 시작 행 (OFFSET) */
	private int firstIndex = 1;

	/** 조회 끝 행 */
	private int lastIndex = 1;

	/** 페이지당 조회 행 수 */
	private int recordCountPerPage = 10;

	/** 아이디 */
	private String id;

	/** 이름 */
	@EgovNullCheck(message="{confirm.required.name}")
	@Size(max = 50, message = "{confirm.maxlength.name}")
	private String name;

	/** 내용 */
	@EgovNullCheck(message="{confirm.required.description}")
	@Size(max = 200, message = "{confirm.maxlength.description}")
	private String description;

	/** 사용여부 */
	@Pattern(regexp = "[YN]", message = "{confirm.invalid.useYn}")
	private String useYn;

	/** 등록자 */
	@EgovNullCheck(message="{confirm.required.user}")
	@Size(max = 10, message = "{confirm.maxlength.user}")
	private String regUser;

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
