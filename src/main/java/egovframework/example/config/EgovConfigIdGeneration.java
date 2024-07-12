package egovframework.example.config;

import org.egovframe.rte.fdl.idgnr.impl.EgovTableIdGnrServiceImpl;
import org.egovframe.rte.fdl.idgnr.impl.strategy.EgovIdGnrStrategyImpl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class EgovConfigIdGeneration {

	@Bean
	public EgovIdGnrStrategyImpl mixPrefixSample() {
		EgovIdGnrStrategyImpl egovIdGnrStrategyImpl = new EgovIdGnrStrategyImpl();
		egovIdGnrStrategyImpl.setPrefix("SAMPLE-");
		egovIdGnrStrategyImpl.setCipers(5);
		egovIdGnrStrategyImpl.setFillChar('0');
		return egovIdGnrStrategyImpl;
	}

	@Bean(destroyMethod="destroy")
	public EgovTableIdGnrServiceImpl egovIdGnrService(@Qualifier("dataSource") DataSource dataSource) {
		EgovTableIdGnrServiceImpl egovTableIdGnrServiceImpl = new EgovTableIdGnrServiceImpl();
		egovTableIdGnrServiceImpl.setDataSource(dataSource);
		egovTableIdGnrServiceImpl.setStrategy(mixPrefixSample());
		egovTableIdGnrServiceImpl.setBlockSize(10);
		egovTableIdGnrServiceImpl.setTable("IDS");
		egovTableIdGnrServiceImpl.setTableName("SAMPLE");
		return egovTableIdGnrServiceImpl;	
	}

}
