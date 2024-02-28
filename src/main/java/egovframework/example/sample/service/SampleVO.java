package egovframework.example.sample.service;

import egovframework.example.validation.EgovNotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SampleVO extends SampleDefaultVO {

	private static final long serialVersionUID = 1L;

	/** 아이디 */
	private String id;

	/** 이름 */
	@EgovNotNull(message="{confirm.required.name}")
	private String name;

	/** 내용 */
	@EgovNotNull(message="{confirm.required.description}")
	private String description;

	/** 사용여부 */
	private String useYn;

	/** 등록자 */
	@EgovNotNull(message="{confirm.required.user}")
	private String regUser;

}
