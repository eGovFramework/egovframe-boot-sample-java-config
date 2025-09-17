package egovframework.example.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.text.CaseUtils;
import org.egovframe.rte.fdl.property.EgovPropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

/**
 * 전자정부 프레임워크 데이터소스 설정
 * eGovFrame DataSource Configuration
 * 
 * @author eGovFrame Development Team
 * @since 2025.09.17
 * @version 4.3.1
 */
@Configuration
public class EgovConfigDatasource {

	@Autowired
	private EgovPropertyService propertiesService;

	private String className;
	private String url;
	private String username;
	private String password;

	/**
	 * 데이터소스 빈 생성
	 * 프로퍼티 서비스에서 설정값을 읽어와 DB 타입에 따라 적절한 데이터소스 구성
	 * -
	 * Create DataSource bean
	 * Configure appropriate datasource based on database type from property service
	 * 
	 * @return DataSource 데이터소스 / DataSource instance
	 */
	@Bean(name="dataSource")
	public DataSource dataSource() {
		// 프로퍼티 서비스에서 DB 설정값 읽기
		// Read database configuration from property service
		String dbType = propertiesService.getString("Globals.DbType", "mysql").toLowerCase();
		String dbTypeCapitalized = CaseUtils.toCamelCase(dbType, true);
		this.className = propertiesService.getString("Globals." + dbTypeCapitalized + ".DriverClassName", "com.mysql.cj.jdbc.Driver");
		this.url = propertiesService.getString("Globals." + dbTypeCapitalized + ".Url", "jdbc:mysql://localhost:3306/egovframe");
		this.username = propertiesService.getString("Globals." + dbTypeCapitalized + ".UserName", "sa");
		this.password = propertiesService.getString("Globals." + dbTypeCapitalized + ".Password", "egovframe");

		// DB 타입에 따른 데이터소스 생성
		// Create datasource based on database type
		switch (dbType) {
			case "mysql":
			case "oracle":
				return basicDataSource(dbType);
			case "hsql":
			default:
				return embeddedDataSource();
		}
	}

	/**
	 * 내장 데이터베이스 생성 (HSQL)
	 * Create embedded database (HSQL)
	 * 
	 * @return DataSource 내장 데이터소스 / Embedded DataSource
	 */
	private DataSource embeddedDataSource() {
		String scriptLocation = propertiesService.getString("Globals.Hsql.ScriptLocation", "classpath:/db/sampledb.sql");
		EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
		return builder
			.setType(EmbeddedDatabaseType.HSQL)
			.addScript(scriptLocation)
			.build();
	}

	/**
	 * 외부 데이터베이스 생성 (MySQL, Oracle)
	 * Create external database (MySQL, Oracle)
	 * 
	 * @param dbType 데이터베이스 타입 / Database type
	 * @return DataSource 외부 데이터소스 / External DataSource
	 */
	private DataSource basicDataSource(String dbType) {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName(this.className);
		dataSource.setUrl(this.url);
		dataSource.setUsername(this.username);
		dataSource.setPassword(this.password);
		
		// 커넥션 풀 설정
		// Connection pool settings
		dataSource.setInitialSize(5);
		dataSource.setMaxTotal(20);
		dataSource.setMaxIdle(10);
		dataSource.setMinIdle(5);
		return dataSource;
	}
}
