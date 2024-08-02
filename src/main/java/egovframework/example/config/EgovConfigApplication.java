package egovframework.example.config;

import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;

@Configuration
public class EgovConfigApplication {
  @Bean
  public WebServerFactoryCustomizer<ConfigurableWebServerFactory> serverPortCustomizer() {
    return factory -> factory.setPort(8080);
  }
  @Bean
  public JpaProperties jpaProperties() {
    JpaProperties properties = new JpaProperties();
    properties.setOpenInView(false);
    return properties;
  }
}