package egovframework.example.config;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.egovframe.rte.fdl.property.impl.EgovPropertyServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.NoArgsConstructor;

/**
 * Properties 구성
 */
@Configuration
@NoArgsConstructor
public class EgovConfigProperties {

	/**
	 * 속성 서비스
	 * 
	 * @return
	 */
	@Bean(destroyMethod = "destroy")
	public EgovPropertyServiceImpl propertiesService() {
		final Map<String, String> properties = new ConcurrentHashMap<>();
		properties.put("pageUnit", "10");
		properties.put("pageSize", "10");

		final EgovPropertyServiceImpl impl = new EgovPropertyServiceImpl();
		impl.setProperties(properties);
		return impl;
	}

}
