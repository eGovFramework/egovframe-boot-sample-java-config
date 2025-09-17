package egovframework.example.config;

import org.apache.commons.text.CaseUtils;
import org.egovframe.rte.fdl.property.impl.EgovPropertyServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;

import java.util.HashMap;
import java.util.Map;

/**
 * 전자정부 프레임워크 프로퍼티 서비스 설정
 * eGovFrame Property Service Configuration
 * 
 * @author eGovFrame Development Team
 * @since 2025.09.17
 * @version 4.3.1
 */
@Configuration
public class EgovConfigProperties {

	@Autowired
	private Environment environment;

	/**
	 * 전자정부 프레임워크 프로퍼티 서비스 빈 생성
	 * YAML 설정 파일에서 Globals.* 프로퍼티를 자동으로 읽어와 PascalCase로 변환하여 등록
	 * -
	 * Create eGovFrame property service bean
	 * Automatically read Globals.* properties from YAML configuration and convert to PascalCase
	 * 
	 * @return EgovPropertyServiceImpl 프로퍼티 서비스 구현체 / Property service implementation
	 */
	@Bean(destroyMethod="destroy")
	public EgovPropertyServiceImpl propertiesService() {
		EgovPropertyServiceImpl egovPropertyServiceImpl = new EgovPropertyServiceImpl();
		Map<String, String> properties = new HashMap<>();
		
		// 모든 프로퍼티 소스에서 Globals.* 프로퍼티를 찾아서 자동 변환
		// Find Globals.* properties from all property sources and convert automatically
		MutablePropertySources propertySources = ((AbstractEnvironment) environment).getPropertySources();
		
		for (PropertySource<?> propertySource : propertySources) {
			if (propertySource instanceof EnumerablePropertySource) {
				String[] propertyNames = ((EnumerablePropertySource<?>) propertySource).getPropertyNames();
				
				for (String propertyName : propertyNames) {
					if (propertyName.startsWith("Globals.")) {
						String value = environment.getProperty(propertyName);
						
						// Globals. 접두사 유지하고 나머지 부분을 kebab-case → PascalCase로 변환
						// Keep Globals. prefix and convert remaining part from kebab-case to PascalCase
						String key = convertGlobalsProperty(propertyName);
						
						properties.put(key, value);
					}
				}
			}
		}

		egovPropertyServiceImpl.setProperties(properties);
		return egovPropertyServiceImpl;
	}

	/**
	 * Globals 프로퍼티 키를 PascalCase로 변환
	 * Convert Globals property key to PascalCase
	 * Example : Globals.page-unit → Globals.PageUnit
	 * Example : Globals.mysql.driver-class-name → Globals.Mysql.DriverClassName
	 * 
	 * @param propertyName 원본 프로퍼티 이름 / Original property name
	 * @return String 변환된 프로퍼티 이름 / Converted property name
	 */
	private String convertGlobalsProperty(String propertyName) {
		// Globals. 접두사 분리
		// Separate Globals. prefix
		String withoutGlobals = propertyName.substring(8); // "Globals." 제거
		
		// 점(.)으로 분할하여 각 부분을 PascalCase로 변환
		// Split by dot and convert each part to PascalCase
		String[] parts = withoutGlobals.split("\\.");
		StringBuilder result = new StringBuilder("Globals.");
		
		for (int i = 0; i < parts.length; i++) {
			String part = parts[i];
			// kebab-case를 PascalCase로 변환 (첫글자 대문자)
			// Convert kebab-case to PascalCase (first letter uppercase)
			String pascalCase = CaseUtils.toCamelCase(part, true, '-');
			result.append(pascalCase);
			
			if (i < parts.length - 1) {
				result.append(".");
			}
		}
		
		return result.toString();
	}

}
