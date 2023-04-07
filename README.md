# 표준프레임워크 부트 기반 심플 게시판

![java](https://img.shields.io/badge/java-007396?style=for-the-badge&logo=JAVA&logoColor=white)
![javascript](https://img.shields.io/badge/javascript-F7DF1E?style=for-the-badge&logo=javascript&logoColor=black)
![Spring_boot](https://img.shields.io/badge/Spring_Boot-F2F4F9?style=for-the-badge&logo=spring-boot)
![maven](https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white)  
![workflow](https://github.com/eGovFramework/egovframe-boot-sample-java-config/actions/workflows/maven.yml/badge.svg)

※ 본 프로젝트는 Spring boot 기반의 CRUD 기본 게시판 예제 소스 코드입니다.  

## 환경 설정

프로젝트에서 사용된 환경 프로그램 정보는 다음과 같다.
| 프로그램 명 | 버전 명 |
| :--------- | :------ |
| java | 1.8 이상 |
| maven | 3.8.4 |

## 프로젝트 실행

### CLI 구동 방법

```bash
mvn spring-boot:run
```

### IDE 구동 방법

__프로젝트 우클릭 > Run As > Spring Boot App__ 을 통해 구동한다.

### 구동 후 확인

구동 후, 브라우저에서 `http://localhost:포트번호/` 로 확인이 가능하다.  
초기 포트번호는 8080이며 `src/main/resources/application.yml` 파일의 port 항목에서 변경 가능하다.

## 참조 화면

### 목록 화면

![4th_new_web4](https://user-images.githubusercontent.com/3771788/229034000-be8e116b-01ec-4a13-ab17-4c8e85956f8a.jpg)

### 게시글 등록 화면

![4th_new_web5](https://user-images.githubusercontent.com/3771788/229034017-68edf6c3-aeee-441c-bff8-9405626a277c.jpg)

## 변경 사항

### 1. [Java Config 변환](./Docs/java-config-convert.md)

#### 1) Web.xml -> WebApplicationInitializer 구현체로 변환

#### 2) context-\*.xml -> @Configuration 변환

#### 3) properties 변환(예정) boot 지원
