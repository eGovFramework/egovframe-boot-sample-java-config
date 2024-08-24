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
		final EgovIdGnrStrategyImpl impl = new EgovIdGnrStrategyImpl();
		impl.setPrefix("SAMPLE-");
		impl.setCipers(5);
		impl.setFillChar('0');
		return impl;
	}

	/**
	 * ID Generation 서비스를 위한 Table 구현 클래스
	 * 
	 * @param dataSource
	 * @return
	 */
	@Bean(destroyMethod = "destroy")
	public EgovTableIdGnrServiceImpl egovIdGnrService(final @Qualifier("dataSource") DataSource dataSource) {
		final EgovTableIdGnrServiceImpl impl = new EgovTableIdGnrServiceImpl();
		impl.setDataSource(dataSource);
		impl.setStrategy(mixPrefixSample());
		impl.setBlockSize(10);
		impl.setTable("IDS");
		impl.setTableName("SAMPLE");
		return impl;
	}

}
