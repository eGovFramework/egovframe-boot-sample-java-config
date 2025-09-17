# context-properties.xml 설정 변환

> 프로퍼티 정보 설정

프로젝트 내에서 사용할 EgovPropertyService에 값을 등록하는 예제이다.

## XML 설정 (기존)

<context-properties.xml>

```xml
<bean name="propertiesService" class="egovframework.rte.fdl.property.impl.EgovPropertyServiceImpl" destroy-method="destroy">
    <property name="properties">
        <map>
            <entry key="pageUnit" value="10"/>
            <entry key="pageSize" value="10"/>
            <entry key="posblAtchFileSize" value="5242880"/>
            <entry key="Globals.fileStorePath" value="/user/file/sht/"/>
            <entry key="Globals.addedOptions" value="false"/>
        </map>
    </property>
</bean>
```

## Java Config 설정 (자동화된 YAML 연동 방식)

<application.yml>

```yaml
# 전자정부 프레임워크 프로퍼티 설정
# eGovFrame Properties Configuration
# 
# 주의: egov.* 프로퍼티는 자동으로 camelCase로 변환됩니다
# Note: egov.* properties are automatically converted to camelCase
# 예시: egov.page-unit → pageUnit, egov.file-upload-size → fileUploadSize
# Example: egov.page-unit → pageUnit, egov.file-upload-size → fileUploadSize
egov:
  # 페이징 설정
  # Pagination settings
  page-unit: 10
  page-size: 10
  
  # 파일 업로드 설정 (예시)
  # File upload settings (example)
  posbl-atch-file-size: 5242880
  globals-file-store-path: "/user/file/sht/"
  globals-added-options: false
```

<EgovConfigProperties.class>

```java
@Configuration
public class EgovConfigProperties {

    @Autowired
    private Environment environment;

    @Bean(destroyMethod = "destroy")
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
```

## 자동 변환 예시

| YAML 설정 (kebab-case) | Java 프로퍼티 (camelCase) | 설명 |
|------------------------|---------------------------|------|
| `egov.page-unit` | `pageUnit` | 페이지당 항목 수 |
| `egov.page-size` | `pageSize` | 페이지 크기 |
| `egov.posbl-atch-file-size` | `posblAtchFileSize` | 첨부파일 최대 크기 |
| `egov.globals-file-store-path` | `globalsFileStorePath` | 파일 저장 경로 |
| `egov.new-awesome-feature` | `newAwesomeFeature` | 새 기능 (자동 추가) |

## 개선 효과

1. **완전 자동화**: YAML에 프로퍼티만 추가하면 Java 코드 수정 없이 자동 등록
2. **네이밍 컨벤션**: YAML은 kebab-case, Java는 camelCase 자동 변환
3. **확장성**: 새로운 프로퍼티 추가 시 개발자 개입 불필요
4. **중앙 집중 관리**: 모든 설정값이 application.yml에서 관리
5. **환경별 설정**: 개발/운영 환경별로 다른 설정 파일 사용 가능
6. **국제화**: 한글-영문 병행 주석으로 글로벌 개발자 지원
7. **Apache Commons Text 활용**: 검증된 라이브러리로 안정적인 변환
