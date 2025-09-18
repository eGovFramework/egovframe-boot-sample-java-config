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

## Java Config 설정 (Globals 자동화 방식)

<application.yml>

```yaml
# 전자정부 프레임워크 글로벌 설정 (심플백엔드 호환)
# 주의: Globals.* 프로퍼티는 자동으로 PascalCase로 변환됩니다
# 예시: Globals.page-unit → Globals.PageUnit, Globals.db-type → Globals.DbType
# 활용: propertiesService.getString("Globals.PageUnit")
#
# eGovFrame Global Configuration (Simple-backend compatible)
# Note: Globals.* properties are automatically converted to PascalCase
# Example: Globals.page-unit → Globals.PageUnit, Globals.db-type → Globals.DbType
# Usage: propertiesService.getString("Globals.PageUnit")
Globals:
  # 페이징 설정
  # Pagination settings
  page-unit: 10
  page-size: 10
  
  # 파일 업로드 설정
  # File upload settings
  posbl-atch-file-size: 5242880
  file-store-path: "./files"
  added-options: false
  
  # 데이터베이스 설정
  # Database settings
  db-type: "mysql"  # hsql, mysql, oracle
  
  # MySQL 설정
  # MySQL configuration
  mysql:
    driver-class-name: "com.mysql.cj.jdbc.Driver"
    url: "jdbc:mysql://127.0.0.1:3306/egovframe"
    user-name: "egovframe"
    password: "egovframe"
    
  # Oracle 설정
  # Oracle configuration
  oracle:
    driver-class-name: "oracle.jdbc.driver.OracleDriver"
    url: "jdbc:oracle:thin:@127.0.0.1:1521:XE"
    user-name: "egovframe"
    password: "egovframe"
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
        
        // 모든 프로퍼티 소스에서 Globals.* 프로퍼티를 찾아서 자동 변환
        // Find Globals.* properties from all property sources and convert automatically
        MutablePropertySources propertySources = ((AbstractEnvironment) environment).getPropertySources();
        
        for (PropertySource<?> propertySource : propertySources) {
            if (propertySource instanceof EnumerablePropertySource) {
                String[] propertyNames = ((EnumerablePropertySource<?>) propertySource).getPropertyNames();
                
                for (String propertyName : propertyNames) {
                    if (propertyName.startsWith("Globals.")) {
                        String value = environment.getProperty(propertyName);
                        String key = convertGlobalsProperty(propertyName);
                        properties.put(key, value);
                    }
                }
            }
        }

        egovPropertyServiceImpl.setProperties(properties);
        return egovPropertyServiceImpl;
    }
    
    // Globals.page-unit → Globals.PageUnit 변환
    // Globals.mysql.driver-class-name → Globals.Mysql.DriverClassName 변환
    private String convertGlobalsProperty(String propertyName) {
        // CaseUtils.toCamelCase 활용한 변환 로직
    }
}
```

## 자동 변환 예시

| YAML 설정 (kebab-case) | Java 프로퍼티 (PascalCase) | 설명 |
|------------------------|---------------------------|------|
| `Globals.page-unit` | `Globals.PageUnit` | 페이지당 항목 수 |
| `Globals.page-size` | `Globals.PageSize` | 페이지 크기 |
| `Globals.db-type` | `Globals.DbType` | 데이터베이스 타입 |
| `Globals.mysql.driver-class-name` | `Globals.Mysql.DriverClassName` | MySQL 드라이버 |
| `Globals.mysql.url` | `Globals.Mysql.Url` | MySQL 접속 URL |
| `Globals.oracle.user-name` | `Globals.Oracle.UserName` | Oracle 사용자명 |

## 다중 데이터베이스 지원

### HSQL (내장 DB)
```yaml
Globals:
  db-type: "hsql"
```

### MySQL
```yaml
Globals:
  db-type: "mysql"
  mysql:
    driver-class-name: "com.mysql.cj.jdbc.Driver"
    url: "jdbc:mysql://localhost:3306/egovframe"
    user-name: "egovframe"
    password: "egovframe"
```

### Oracle
```yaml
Globals:
  db-type: "oracle"
  oracle:
    driver-class-name: "oracle.jdbc.driver.OracleDriver"
    url: "jdbc:oracle:thin:@localhost:1521:XE"
    user-name: "egovframe"
    password: "egovframe"
```

## 개선 효과

1. **심플백엔드 호환성**: Globals.* 구조로 기존 전자정부 프로젝트와 일관성 확보
2. **완전 자동화**: YAML에 프로퍼티만 추가하면 Java 코드 수정 없이 자동 등록
3. **다중 DB 지원**: DB 타입에 따른 동적 데이터소스 생성
4. **네이밍 컨벤션**: YAML은 kebab-case, Java는 PascalCase 자동 변환
5. **중앙 집중 관리**: 모든 설정값이 application.yml에서 관리
6. **환경별 설정**: 개발/운영 환경별로 다른 설정 파일 사용 가능
7. **국제화**: 한글-영문 병행 주석으로 글로벌 개발자 지원
8. **Apache Commons Text 활용**: 검증된 라이브러리로 안정적인 변환
