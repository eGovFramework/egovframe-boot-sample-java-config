package egovframework.example.config;

import javax.sql.DataSource;

import org.egovframe.rte.fdl.idgnr.impl.EgovTableIdGnrServiceImpl;
import org.egovframe.rte.fdl.idgnr.impl.strategy.EgovIdGnrStrategyImpl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.NoArgsConstructor;

/**
 * IdGeneration 구성
 */
@Configuration
@NoArgsConstructor
public class EgovConfigIdGeneration {

	/**
	 * Id Generation 정책(Strategy) 를 위한 기본 구현 클래스
	 */
	@Bean
	public EgovIdGnrStrategyImpl mixPrefixSample() {
		final EgovIdGnrStrategyImpl egovIdGnrStrategyImpl = new EgovIdGnrStrategyImpl();
		egovIdGnrStrategyImpl.setPrefix("SAMPLE-");
		egovIdGnrStrategyImpl.setCipers(5);
		egovIdGnrStrategyImpl.setFillChar('0');
		return egovIdGnrStrategyImpl;
	}

	/**
	 * ID Generation 서비스를 위한 Table 구현 클래스
	 * 
	 * @param dataSource
	 * @return
	 */
	@Bean(destroyMethod = "destroy")
	public EgovTableIdGnrServiceImpl egovIdGnrService(final @Qualifier("dataSource") DataSource dataSource) {
		final EgovTableIdGnrServiceImpl egovTableIdGnrServiceImpl = new EgovTableIdGnrServiceImpl();
		egovTableIdGnrServiceImpl.setDataSource(dataSource);
		egovTableIdGnrServiceImpl.setStrategy(mixPrefixSample());
		egovTableIdGnrServiceImpl.setBlockSize(10);
		egovTableIdGnrServiceImpl.setTable("IDS");
		egovTableIdGnrServiceImpl.setTableName("SAMPLE");
		return egovTableIdGnrServiceImpl;
	}

}
