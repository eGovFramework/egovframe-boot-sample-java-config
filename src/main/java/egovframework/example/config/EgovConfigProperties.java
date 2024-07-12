package egovframework.example.config;

import org.egovframe.rte.fdl.property.impl.EgovPropertyServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class EgovConfigProperties {

	@Bean(destroyMethod="destroy")
	public EgovPropertyServiceImpl propertiesService() {
		Map<String, String> properties = new HashMap<>();
		properties.put("pageUnit", "10");
		properties.put("pageSize", "10");

		EgovPropertyServiceImpl egovPropertyServiceImpl = new EgovPropertyServiceImpl();
		egovPropertyServiceImpl.setProperties(properties);
		return egovPropertyServiceImpl;
	}

}
