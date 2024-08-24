package egovframework.example.exception;

import org.egovframe.rte.fdl.cmmn.exception.handler.ExceptionHandler;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Egov 샘플 기타 ExcepHndlr
 */
@Slf4j
@NoArgsConstructor
public class EgovSampleOthersExcepHndlr implements ExceptionHandler {

	@Override
	public void occur(Exception exception, String packageName) {
		log.debug("##### EgovSampleOthersExcepHndlr Run...");
	}

}
