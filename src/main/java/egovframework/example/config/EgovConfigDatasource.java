package egovframework.example.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

/**
 * 데이터소스 설정 (내장 HSQLDB, DATABASE/hsql 의 DDL · 데이터 로드)
 */
@Configuration
public class EgovConfigDatasource {

	@Bean(name="dataSource")
	DataSource dataSource() {
	    EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
	    return builder.setType(EmbeddedDatabaseType.HSQL).setScriptEncoding("UTF-8")
				.addScripts("classpath:/db/hsql/all_sample_ddl_hsql.sql", "classpath:/db/hsql/all_sample_data_hsql.sql").build();
	}

}
