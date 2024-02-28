package egovframework.example.exception;

import lombok.extern.slf4j.Slf4j;
import org.egovframe.rte.fdl.cmmn.exception.handler.ExceptionHandler;

@Slf4j
public class EgovSampleExcepHndlr implements ExceptionHandler {

	@Override
	public void occur(Exception ex, String packageName) {
		log.debug("##### EgovServiceExceptionHandler Run...");
	}

}
