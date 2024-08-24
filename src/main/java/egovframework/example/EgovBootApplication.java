package egovframework.example;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import lombok.extern.slf4j.Slf4j;

/**
 * 스프링 부트 애플리케이션
 */
@SpringBootApplication
@Slf4j
public class EgovBootApplication {

	/**
	 * 메인
	 * 
	 * @param args
	 */
	public static void main(final String[] args) {
		SpringApplication.run(EgovBootApplication.class, args);
	}

	/**
	 * 애플리케이션 러너
	 * 
	 * @return
	 */
	@Bean
	public ApplicationRunner applicationRunner() {
		return args -> {
			if (log.isDebugEnabled()) {
				log.debug("args={}", args);
			}
		};
	}

}
