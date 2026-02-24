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

@Configuration
@MapperScan(basePackages="egovframework.example.sample.service.impl")
public class EgovConfigMapper {

	@Bean(name = "sqlSession") // DAO 방식을 활용할 경우 EgovAbstractMapper 가 찾는 Bean Name
	public SqlSessionFactoryBean sqlSessionFactory(@Qualifier("dataSource") DataSource dataSource) throws IOException {
		PathMatchingResourcePatternResolver pmrpr = new PathMatchingResourcePatternResolver();
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(dataSource);
		sqlSessionFactoryBean.setConfigLocation(pmrpr.getResource("classpath:/egovframework/sqlmap/example/sql-mapper-config.xml"));
		sqlSessionFactoryBean.setMapperLocations(pmrpr.getResources("classpath:/egovframework/sqlmap/example/mappers/*.xml"));
		return sqlSessionFactoryBean;
	}

	@Bean(name = "sqlSessionTemplate") // 혼동 방지를 위한 명시적 Bean Name 을 표기
	public SqlSessionTemplate sqlSession(SqlSessionFactory sqlSessionFactory) {
		return new SqlSessionTemplate(sqlSessionFactory);
	}

}
