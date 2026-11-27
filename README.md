# 표준프레임워크 부트 기반 심플 게시판

[![eGovFrame](https://img.shields.io/badge/eGovFrame-5.1.0-134F8C?labelColor=white&logo=data:image/svg%2Bxml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHZpZXdCb3g9IjAgMCAxNzMuMjgyIDE3My4yODIiPjxwYXRoIGZpbGw9IiNmZmYiIGQ9Ik0xNzMuMjgyIDg2LjY1YzAgNDcuODQ0LTM4Ljc5NCA4Ni42MzItODYuNjQ2IDg2LjYzMkMzOC43OTkgMTczLjI4MiAwIDEzNC40OTQgMCA4Ni42NSAwIDM4Ljc4OCAzOC43OTkgMCA4Ni42MzYgMGM0Ny44NTIgMCA4Ni42NDYgMzguNzg4IDg2LjY0NiA4Ni42NSIvPjxwYXRoIGZpbGw9IiMwMDM3NjQiIGQ9Ik0xMjcuMzg5IDgwLjU5OGMtMTMuNzkxLTkuMzY1LTMxLjQzOS01LjU0Mi00MC43MTcgOC41MzMtNy43MiAxMS43NjctMTkuNDA1IDEzLjIzNS0yMy45MDYgMTMuMjM1LTE0Ljc1MSAwLTI0LjgyNC0xMC4zNjctMjcuODE4LTIxLjA5NWgtLjAxYy0uMDM5LS4xMDgtLjA1OS0uMi0uMDktLjMwNy0uMDI1LS4xMi0uMDU2LS4yMzMtLjA4OS0uMzY2LTEuMTc3LTQuNDY3LTEuNDY3LTYuNjA5LTEuNDY3LTExLjM2OCAwLTI1LjY1IDI2LjMyMS01NC4yMTMgNjQuMjE1LTU0LjIxMyAzOC44MjkgMCA2MS4wNSAyOS41NDUgNjYuNzggNDUuOTc5LS4xMDctLjI5NS0uMjA5LS41ODEtLjI5MS0uODc3LTExLjAxNS0zMi4xMjUtNDEuNDc0LTU1LjIxNi03Ny4zNTctNTUuMjE2LTQ1LjEzIDAtODEuNzI5IDM2LjU5LTgxLjcyOSA4MS43MzkgMCA0MC4zNTEgMjkuMTA4IDc0Ljg5MSA2OS40NzkgNzQuODkxIDMyLjE5NyAwIDUzLjg0Mi0xOC4wNTIgNjMuNzU3LTQyLjkzIDUuNDUtMTMuNjE0IDEuNTk1LTI5LjYwNS0xMC43NTctMzguMDA1Ii8%2BPHBhdGggZmlsbD0iI2U0MDMyZSIgZD0iTTE2NC43ODggNjIuNTg5Yy00Ljc3Ny0xNi4wMjYtMjcuMTUzLTQ3LjU3MS02Ny4yODItNDcuNTcxLTM3Ljg5NCAwLTY0LjIxNCAyOC41NjMtNjQuMjE0IDU0LjIxMiAwIDQuNzU5LjI5IDYuOTAxIDEuNDY2IDExLjM2OC0uNDg5LTEuOTUxLS43NC0zLjkwOC0uNzQtNS44MjMgMC0yNi43MjEgMjYuNzQxLTQ1LjIyNyA1NC4yNDgtNDUuMjI3IDM3LjIxOCAwIDY3LjM4OCAzMC4xNzQgNjcuMzg4IDY3LjM5IDAgMjkuMTczLTE2Ljc4NSA1NC40MzctNDEuMTc5IDY2LjU2NXYuMDIzYzMxLjQ1NS0xMS4zOTEgNTMuOTA4LTQxLjUxMiA1My45MDgtNzYuODg0IDAtOC4zNzgtMS4xMjctMTUuNzU5LTMuNTk1LTI0LjA1MyIvPjwvc3ZnPg%3D%3D)](https://www.egovframe.go.kr)
[![Java](https://img.shields.io/badge/Java-17-007396?logo=openjdk&logoColor=white)](https://openjdk.org/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.16-F2F4F9?logo=spring-boot)](https://spring.io/projects/spring-boot)
[![Spring Framework](https://img.shields.io/badge/Spring%20Framework-6.2.19-6DB33F?logo=spring&logoColor=white)](https://spring.io/projects/spring-framework)
[![KRDS](https://img.shields.io/badge/KRDS-HTML%20Component%20Kit-256EF4)](https://github.com/KRDS-uiux/krds-uiux)
[![jQuery](https://img.shields.io/badge/jQuery-3.7.1-0769AD?logo=jquery&logoColor=white)](https://jquery.com/)
[![maven](https://img.shields.io/badge/Maven-C71A36?logo=apache-maven&logoColor=white)](https://maven.apache.org/)
![workflow](https://github.com/eGovFramework/egovframe-boot-sample-java-config/actions/workflows/maven.yml/badge.svg)

※ 본 프로젝트는 Spring Boot 기반의 기본 게시판 예제 소스 코드이며, 화면은 [KRDS(대한민국 디지털 정부 디자인 시스템)](https://www.krds.go.kr/html/site/index.html) 기준으로 구성되어 있습니다.

## 환경 설정

| 항목 | 버전 |
| :--- | :--- |
| JDK | 17 |
| Jakarta EE | 10 |
| Servlet | 6.0 (내장 Tomcat 10.1) |
| WAS | 내장 Tomcat (Spring Boot) |
| Spring Framework | 6.2.19-egov.2 |
| Spring Boot | 3.5.16 |
| eGovFrame Parent | egovframe-boot-starter-parent 5.1.0 |
| View | Thymeleaf |
| UI | KRDS HTML Component Kit / jQuery 3.7.1 |
| DB | HSQLDB 2.7.4 (내장 메모리 DB, 기동 시 `DATABASE/hsql` 의 DDL · 데이터 실행) |
| 로깅 | Log4j2 (`src/main/resources/log4j2.xml`) |

## 서비스 정보

| 항목 | 값 |
| :--- | :--- |
| 서비스명 (Maven artifactId) | `egovframe-boot-sample` |
| 패키징 | JAR (`target/egovframe-boot-sample-5.1.0.jar`) |
| 컨텍스트 경로 | `/` |
| Docker 이미지 | `egovframe-boot-sample:5.1.0` |
| Docker Compose 서비스 / 컨테이너 | `app` / `egovframe-boot-sample` |
| Kubernetes Deployment / Service | `egovframe-boot-sample` (NodePort `30080`) |

## 프로젝트 구조

```
src/main/java/egovframework/example
 ├─ EgovBootApplication.java              Spring Boot 기동 클래스
 ├─ config         EgovConfig*(Java Config), EgovSecurityHeaderFilter (보안 헤더 필터)
 ├─ exception      EgovSampleExcepHndlr, EgovSampleOthersExcepHndlr, EgovAopExceptionTransfer (AOP 예외 후처리)
 ├─ pagination     EgovKrdsPaginationRenderer, EgovPaginationDialect (KRDS 페이지네이션)
 └─ sample         게시판 Controller / Service / Mapper / VO
src/main/resources
 ├─ application.properties                  서비스 설정
 ├─ egovframework/sqlmap/example           MyBatis 설정 · Mapper
 ├─ egovframework/message                  다국어 메시지 (ko, en)
 ├─ templates/thymeleaf/sample             목록 · 등록/수정 · 에러 화면
 └─ static                                 css(KRDS · egovframe.css) · js · error 페이지
DATABASE/<dbms>                            DBMS 별 DDL · 데이터 스크립트 (hsql 은 classpath:/db/hsql 로 패키징되어 내장 DB 초기화)
```

## 프로젝트 실행

### IDE (eGovFrame 개발환경)

__프로젝트 우클릭 > Run As > Spring Boot App__ 을 선택하여 구동한다.

### CLI (Maven)

```bash
mvn spring-boot:run
```

또는 실행 JAR 를 빌드하여 구동한다.

```bash
mvn clean package
java -jar target/egovframe-boot-sample-5.1.0.jar
```

### 구동 후 확인

브라우저에서 http://localhost:8080/ 에 접속한다.  
포트는 `src/main/resources/application.properties` 의 `server.port` 항목에서 변경할 수 있다.

헬스체크는 Spring Boot Actuator 엔드포인트로 확인한다.

```bash
curl -s http://localhost:8080/actuator/health
# {"status":"UP"}
```

## 빌드 및 테스트

```bash
mvn clean package
```

- 빌드 결과물 : `target/egovframe-boot-sample-5.1.0.jar`
- 단위 테스트(VO · 서비스 · 매퍼 · MockMvc · 보안 · 페이지네이션)가 패키징 시 함께 실행됩니다. 생략하려면 `-DskipTests` 를 사용합니다.

## 주요 설정

| 파일 · 항목 | 기본값 | 설명 |
| :--- | :--- | :--- |
| `spring.application.name` | egovframe-boot-sample | 서비스명 |
| `server.port` | 8080 | HTTP 포트 |
| `management.endpoints.web.exposure.include` | health | Actuator 노출 엔드포인트 (`/actuator/health`, readiness/liveness 프로브 포함) |
| `server.servlet.session.cookie.secure` | `${SESSION_COOKIE_SECURE:false}` | TLS를 프록시에서 종료하는 운영 환경은 환경 변수 `SESSION_COOKIE_SECURE=true` 로 기동 |
| `logging.level.egovframework` | INFO | 개발 시 `--logging.level.egovframework=DEBUG` 로 기동 |
| `config/EgovConfig*.java` | — | 공통 · 데이터소스 · MyBatis · 트랜잭션 · 검증 · Web MVC 설정 (Java Config) |

## URL 정책

| URL | Method | 설명 |
| :--- | :--- | :--- |
| `/` | GET | 목록 첫 화면 (유일한 GET URL, 쿼리스트링은 바인딩하지 않음) |
| `/egovSampleList.do` | POST | 목록 조회 (검색 · 페이지 이동) |
| `/addSampleView.do` | POST | 등록 화면 |
| `/addSample.do` | POST | 등록 → `redirect:/` |
| `/updateSampleView.do` | POST | 수정 화면 |
| `/updateSample.do` | POST | 수정 → `redirect:/` |
| `/deleteSample.do` | POST | 삭제 → `redirect:/` |

- 등록 · 수정 · 삭제 후에는 쿼리스트링 없이 `/` 로 리다이렉트(PRG)하고, 목록 조회조건은 Flash Attribute 로 전달합니다.
- 그 밖의 메서드 요청은 `405`, 없는 URL 은 `404` 로 공통 에러 화면을 응답합니다.

## 보안 구성

- **보안 헤더** (`EgovSecurityHeaderFilter` (`EgovConfigWeb` 에 등록), 정적 리소스 · 에러 화면 포함 전체 응답) :
  `X-Frame-Options: DENY`, `Content-Security-Policy`, `X-Content-Type-Options: nosniff`,
  `Referrer-Policy`, `Permissions-Policy`, HTTPS 요청 시 `Strict-Transport-Security`
- **세션 쿠키** : `HttpOnly`, `SameSite=Lax`, URL 에 `;jsessionid` 미노출 (`tracking-modes=cookie`), `Secure` 는 `SESSION_COOKIE_SECURE` 로 지정 (`application.properties`)
- **입력값 검증** : 서버 Bean Validation (필수 · 길이 · 사용여부 Y/N), 화면 maxlength 를 DB 컬럼폭과 일치,
  페이지 번호 · 페이징 단위 범위 보정, 잘못된 로케일(`?language=`) 무시
- **에러 화면** : 예외 메시지 · WAS 버전을 노출하지 않는 공통 에러 화면
- **XSS** : Thymeleaf `th:text` 자동 이스케이프

## DB 스크립트

샘플은 내장 HSQLDB(`DATABASE/hsql`, `pom.xml` 에서 `classpath:/db/hsql` 로 패키징)로 구동되며, 외부 DBMS 적용 시 `DATABASE/` 디렉터리의 DBMS별 스크립트를 사용한다.

| 파일 | 내용 |
| :--- | :--- |
| `DATABASE/<dbms>/all_sample_ddl_<dbms>.sql` | 기존 테이블 삭제 · 테이블 생성 · 테이블/컬럼 주석 |
| `DATABASE/<dbms>/all_sample_data_<dbms>.sql` | ID 채번(IDS) · 샘플 게시글 초기 데이터 |

- 지원 DBMS : altibase, cubrid, goldilocks, hsql, maria, mysql, oracle, postgres, tibero
- 실행 순서 : `all_sample_ddl_<dbms>.sql` → `all_sample_data_<dbms>.sql`

## 컨테이너 / 쿠버네티스 실행

```bash
# Docker 이미지 빌드
docker build -t egovframe-boot-sample:5.1.0 .

# Docker Compose 실행 (사전 mvn package 불필요)
docker compose up -d --build

# 쿠버네티스 배포
kubectl apply -f k8s/
```

- 접속 URL (Docker) : http://localhost:8080/
- 접속 URL (Kubernetes) : `http://<노드 IP>:30080/` (Docker Desktop / kind 는 `http://localhost:30080/`)
- 자세한 내용은 [k8s/README.md](k8s/README.md) 를 참고한다.

| 파일 | 설명 |
| :--- | :--- |
| `Dockerfile` | Maven 빌드 → Temurin JRE 17 Alpine 실행 이미지 (멀티 스테이지, UID/GID 1000 non-root, HEALTHCHECK `/actuator/health`) |
| `docker-compose.yml` | 로컬 단일 컨테이너 실행 (서비스 `app`, 컨테이너 `egovframe-boot-sample`, 8080 포트) |
| `k8s/deployment.yml` | Deployment (`egovframe-boot-sample`, readiness/liveness 프로브, non-root) |
| `k8s/service.yml` | NodePort Service (`egovframe-boot-sample:8080`, nodePort `30080`) |

## 참조 화면

### 목록 화면

![list](https://github.com/user-attachments/assets/1139a877-acf0-4acd-9c7c-35ba524089e5)

### 게시글 등록 화면

![regist](https://github.com/user-attachments/assets/9a3a4540-58db-4a4f-a6e2-7a40f65ad797)

## 변경 사항

### Java Config 변환

1. web.xml → `@SpringBootApplication` + 내장 Tomcat 으로 변환
2. dispatcher-servlet.xml → `EgovConfigWeb`(WebMvcConfigurer) 변환
3. context-\*.xml → `@Configuration` 변환

### [KRDS (Korea Design System)](https://www.krds.go.kr/html/site/index.html) 적용

### 반응형 페이지네이션

`EgovKrdsPaginationRenderer` 가 화면 폭별 표시 항목을 class 로 구분하고, `egovframe.css` 가 화면 폭에 맞춰 표시합니다.

| 화면 폭 | 형식 |
| :--- | :--- |
| PC (1024px 이상) | `이전 1 … 5 6 7 8 9 … 12 다음` (최대 9칸) |
| 태블릿 (768~1023px) | `이전 1 … 6 7 8 … 12 다음` (처음 · 현재 ±1 · 끝) |
| 모바일 (767px 이하) | `이전  7 / 12  다음` (한 줄) |

- KRDS 기본 규칙(1023px 이하에서 6~8번째 링크 숨김)은 현재 · 마지막 페이지를 가릴 수 있어 해제했습니다.
- 라벨(이전 · 다음 · 현재 페이지 · 전체 페이지)은 요청마다 지역 변수로 만들어 다국어 동시 요청에도 섞이지 않습니다.
