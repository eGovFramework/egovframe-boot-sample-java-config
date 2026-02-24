# 표준프레임워크 부트 기반 심플 게시판

![java](https://img.shields.io/badge/java-007396?style=for-the-badge&logo=JAVA&logoColor=white)
![javascript](https://img.shields.io/badge/javascript-F7DF1E?style=for-the-badge&logo=javascript&logoColor=black)
![Spring_boot](https://img.shields.io/badge/Spring_Boot-F2F4F9?style=for-the-badge&logo=spring-boot)
![maven](https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white)  
![workflow](https://github.com/eGovFramework/egovframe-boot-sample-java-config/actions/workflows/maven.yml/badge.svg)

※ 본 프로젝트는 Spring Boot 기반의 기본 게시판 예제 소스 코드입니다.  

## 환경 설정

| 항목 | 버전 |
| :--- | :--- |
| JDK | 17 |
| Jakarta EE | 10 |
| Servlet | 6.0 |
| Spring Framework | 6.2.11 |
| Spring Boot | 3.5.6 |

## 프로젝트 실행

### CLI 구동 방법

```bash
mvn spring-boot:run
```

### IDE 구동 방법

__프로젝트 우클릭 > Run As > Spring Boot App__ 을 통해 구동한다.

### 구동 후 확인

구동 후, 브라우저에서 `http://localhost:포트번호/` 로 확인이 가능하다.  
초기 포트번호는 8080이며 `src/main/resources/application.properties` 파일의 `server.port` 항목에서 변경 가능하다.

## 참조 화면

### 목록 화면

![list](https://github.com/user-attachments/assets/1139a877-acf0-4acd-9c7c-35ba524089e5)

### 게시글 등록 화면

![regist](https://github.com/user-attachments/assets/9a3a4540-58db-4a4f-a6e2-7a40f65ad797)

## 변경 사항

### 1. [Java Config 변환](./Docs/java-config-convert.md)

#### 1) Web.xml -> WebApplicationInitializer 구현체로 변환

#### 2) context-\*.xml -> @Configuration 변환

### 2. [KRDS (Korea Design System)](https://www.krds.go.kr/html/site/index.html) 적용
