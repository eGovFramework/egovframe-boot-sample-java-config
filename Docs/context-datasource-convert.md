# context-datasource.xml 설정 변환

> 데이터 소스관련 설정 사항들 다루고 있음

## XML 설정 (기존)

### 내장 DB 사용시

<context-datasource.xml>

```xml
<jdbc:embedded-database id="dataSource-hsql" type="HSQL">
    <jdbc:script location= "classpath:/db/sampledb.sql"/>
</jdbc:embedded-database>
```

### 다른 DB 사용시

<context-datasource.xml>

```xml
<!-- mysql -->
<bean id="dataSource-mysql" class="org.apache.commons.dbcp2.BasicDataSource" destroy-method="close">
    <property name="driverClassName" value="${Globals.DriverClassName}"/>
    <property name="url" value="${Globals.Url}" />
    <property name="username" value="${Globals.UserName}"/>
    <property name="password" value="${Globals.Password}"/>
</bean>

<!-- Oracle -->
<bean id="dataSource-oracle" class="org.apache.commons.dbcp2.BasicDataSource" destroy-method="close">
    <property name="driverClassName" value="${Globals.DriverClassName}"/>
    <property name="url" value="${Globals.Url}" />
    <property name="username" value="${Globals.UserName}"/>
    <property name="password" value="${Globals.Password}"/>
</bean>
```

## Java Config 설정 (Globals 자동화 방식)

<application.yml>

```yaml
# 전자정부 프레임워크 글로벌 설정 (심플백엔드 호환)
# 주의: Globals.* 프로퍼티는 자동으로 PascalCase로 변환됩니다
# 예시: Globals.db-type → Globals.DbType, Globals.mysql.driver-class-name → Globals.Mysql.DriverClassName
# 활용: propertiesService.getString("Globals.DbType")
#
# eGovFrame Global Configuration (Simple-backend compatible)
# Note: Globals.* properties are automatically converted to PascalCase
# Example: Globals.db-type → Globals.DbType, Globals.mysql.driver-class-name → Globals.Mysql.DriverClassName
# Usage: propertiesService.getString("Globals.DbType")
Globals:
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

<EgovConfigDatasource.class>

```java
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
     * @return DataSource 데이터소스 / DataSource instance
     */
    @Bean(name="dataSource")
    public DataSource dataSource() {
        // 프로퍼티 서비스에서 DB 설정값 읽기
        // Read database configuration from property service
        String dbType = propertiesService.getString("Globals.DbType", "hsql").toLowerCase();
        String dbTypeCapitalized = CaseUtils.toCamelCase(dbType, true);
        this.className = propertiesService.getString("Globals." + dbTypeCapitalized + ".DriverClassName", "com.mysql.cj.jdbc.Driver");
        this.url = propertiesService.getString("Globals." + dbTypeCapitalized + ".Url", "jdbc:mysql://localhost:3306/egovframe");
        this.username = propertiesService.getString("Globals." + dbTypeCapitalized + ".UserName", "egovframe");
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
```

## 동적 프로퍼티 매핑

### DB 타입별 자동 프로퍼티 선택

| DB 타입 | YAML 설정 | Java 프로퍼티 키 |
|---------|-----------|------------------|
| mysql | `Globals.mysql.driver-class-name` | `Globals.Mysql.DriverClassName` |
| mysql | `Globals.mysql.url` | `Globals.Mysql.Url` |
| oracle | `Globals.oracle.driver-class-name` | `Globals.Oracle.DriverClassName` |
| oracle | `Globals.oracle.url` | `Globals.Oracle.Url` |

### 동작 방식

1. **DB 타입 확인**: `Globals.DbType` 값 읽기
2. **동적 키 생성**: `CaseUtils.toCamelCase(dbType, true)` 활용
3. **프로퍼티 조회**: `Globals.{DbType}.{Property}` 형태로 자동 매핑

## 사용 예시

### HSQL (내장 DB)
```yaml
Globals:
  db-type: "hsql"
```
→ 내장 HSQL 데이터베이스 사용

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
→ MySQL 외부 데이터베이스 사용

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
→ Oracle 외부 데이터베이스 사용

## 개선 효과

1. **심플백엔드 호환성**: Globals.* 구조로 기존 전자정부 프로젝트와 일관성 확보
2. **동적 DB 지원**: DB 타입 변경만으로 자동 데이터소스 전환
3. **프로퍼티 자동화**: YAML 설정만으로 DB 연결 정보 관리
4. **커넥션 풀**: 외부 DB 사용 시 자동 커넥션 풀 설정
5. **확장성**: 새로운 DB 타입 추가 시 YAML 설정만 추가
6. **중앙 집중 관리**: 모든 DB 설정이 application.yml에서 관리
7. **환경별 설정**: 개발/운영 환경별로 다른 DB 설정 가능
8. **Docker 지원**: 컨테이너 환경에서의 DB 연동 최적화
