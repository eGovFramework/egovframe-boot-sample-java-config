package egovframework.example.config;

import java.io.IOException;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import lombok.NoArgsConstructor;

/**
 * Mapper 구성
 */
@Configuration
@MapperScan(basePackages = "egovframework.example.sample.service.impl")
@NoArgsConstructor
public class EgovConfigMapper {

	/**
	 * SQL 세션 팩토리
	 * 
	 * @param dataSource
	 * @return
	 * @throws IOException
	 */
	@Bean
	public SqlSessionFactoryBean sqlSessionFactory(final @Qualifier("dataSource") DataSource dataSource)
			throws IOException {
		final PathMatchingResourcePatternResolver pmrpr = new PathMatchingResourcePatternResolver();
		final SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(dataSource);
		sqlSessionFactoryBean
				.setConfigLocation(pmrpr.getResource("classpath:/egovframework/sqlmap/example/sql-mapper-config.xml"));
		sqlSessionFactoryBean
				.setMapperLocations(pmrpr.getResources("classpath:/egovframework/sqlmap/example/mappers/*.xml"));
		return sqlSessionFactoryBean;
	}

	/**
	 * SQL 세션
	 * 
	 * @param sqlSessionFactory
	 * @return
	 */
	@Bean
	public SqlSessionTemplate sqlSession(final SqlSessionFactory sqlSessionFactory) {
		return new SqlSessionTemplate(sqlSessionFactory);
	}

}
