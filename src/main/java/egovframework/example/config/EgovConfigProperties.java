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
	 * YAML 설정 파일에서 egov.* 프로퍼티를 자동으로 읽어와 camelCase로 변환하여 등록
	 * -
	 * Create eGovFrame property service bean
	 * Automatically read egov.* properties from YAML configuration and convert to camelCase
	 * 
	 * @return EgovPropertyServiceImpl 프로퍼티 서비스 구현체 / Property service implementation
	 */
	@Bean(destroyMethod="destroy")
	public EgovPropertyServiceImpl propertiesService() {
		EgovPropertyServiceImpl egovPropertyServiceImpl = new EgovPropertyServiceImpl();
		Map<String, String> properties = new HashMap<>();
		
		// 모든 프로퍼티 소스에서 egov.* 프로퍼티를 찾아서 자동 변환
		// Find egov.* properties from all property sources and convert automatically
		MutablePropertySources propertySources = ((AbstractEnvironment) environment).getPropertySources();
		
		for (PropertySource<?> propertySource : propertySources) {
			if (propertySource instanceof EnumerablePropertySource) {
				String[] propertyNames = ((EnumerablePropertySource<?>) propertySource).getPropertyNames();
				
				for (String propertyName : propertyNames) {
					if (propertyName.startsWith("egov.")) {
						String value = environment.getProperty(propertyName);
						
						// egov. 접두사 제거 후 kebab-case를 camelCase로 변환
						// Remove egov. prefix and convert kebab-case to camelCase
						String key = propertyName.substring(5); // "egov." 제거 / Remove "egov."
						String camelCaseKey = CaseUtils.toCamelCase(key, false, '-');
						
						properties.put(camelCaseKey, value);
					}
				}
			}
		}

		egovPropertyServiceImpl.setProperties(properties);
		return egovPropertyServiceImpl;
	}

}
