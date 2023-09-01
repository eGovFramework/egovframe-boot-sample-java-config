package egovframework.example.config;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

@Configuration
public class EgovConfigDatasource {

	@Bean(name="dataSource")
	public DataSource dataSource() {
	    EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
	    return builder.setType(EmbeddedDatabaseType.HSQL).addScript("classpath:/db/sampledb.sql").build();
	}
	
	/**
	 * mySql 사용 시
	 * */
//	@Bean(name="dataSource")
//	@ConfigurationProperties(prefix = "spring.datasource.hikari")
//	public DataSource dataSource() {
//		return DataSourceBuilder.create().build();
//	}

}
