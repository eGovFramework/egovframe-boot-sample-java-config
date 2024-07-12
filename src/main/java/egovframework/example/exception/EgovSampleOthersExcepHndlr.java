package egovframework.example.exception;

import lombok.extern.slf4j.Slf4j;
import org.egovframe.rte.fdl.cmmn.exception.handler.ExceptionHandler;

@Slf4j
public class EgovSampleOthersExcepHndlr implements ExceptionHandler {

	@Override
	public void occur(Exception exception, String packageName) {
		log.debug("##### EgovSampleOthersExcepHndlr Run...");
	}

}
