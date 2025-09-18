# 표준프레임워크 부트 기반 심플 게시판

![java](https://img.shields.io/badge/java-007396?style=for-the-badge&logo=JAVA&logoColor=white)
![javascript](https://img.shields.io/badge/javascript-F7DF1E?style=for-the-badge&logo=javascript&logoColor=black)
![Spring_boot](https://img.shields.io/badge/Spring_Boot-F2F4F9?style=for-the-badge&logo=spring-boot)
![maven](https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white)  
![workflow](https://github.com/eGovFramework/egovframe-boot-sample-java-config/actions/workflows/maven.yml/badge.svg)

※ 본 프로젝트는 Spring Boot 기반의 기본 게시판 예제 소스 코드입니다.  

## 환경 설정

프로젝트에서 사용된 환경 프로그램 정보는 다음과 같다.
| 프로그램 명 | 버전 명 |
| :--------- | :------ |
| java | 1.8 이상 |
| maven | 3.8.4 ~ 3.8.8 |

## 프로젝트 실행

### CLI 구동 방법

```bash
mvn spring-boot:run
```

### IDE 구동 방법

__프로젝트 우클릭 > Run As > Spring Boot App__ 을 통해 구동한다.

### 구동 후 확인

구동 후, 브라우저에서 `http://localhost:포트번호/` 로 확인이 가능하다.  
초기 포트번호는 8080이며 `src/main/resources/application.yml` 파일의 `server.port` 항목에서 변경 가능하다.

## 참조 화면

### 목록 화면

![4th_new_web4](https://github.com/user-attachments/assets/199c6964-1aa1-42bc-a3d2-0234d037057a)

### 게시글 등록 화면

![4th_new_web5](https://github.com/user-attachments/assets/91c1b668-cb59-45ea-8b13-0e3f34ea9078)

## 변경 사항

### 1. [Java Config 변환](./Docs/java-config-convert.md)

#### 1) Web.xml -> WebApplicationInitializer 구현체로 변환

#### 2) context-\*.xml -> @Configuration 변환

#### 3) properties 삭제 -> yaml 파일로 변환

---

# eGovFrame Boot-based Simple Board

![java](https://img.shields.io/badge/java-007396?style=for-the-badge&logo=JAVA&logoColor=white)
![javascript](https://img.shields.io/badge/javascript-F7DF1E?style=for-the-badge&logo=javascript&logoColor=black)
![Spring_boot](https://img.shields.io/badge/Spring_Boot-F2F4F9?style=for-the-badge&logo=spring-boot)
![maven](https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white)  
![workflow](https://github.com/eGovFramework/egovframe-boot-sample-java-config/actions/workflows/maven.yml/badge.svg)

※ This project is a basic board example source code based on Spring Boot.

## Environment Setup

The environment program information used in the project is as follows.
| Program Name | Version |
| :--------- | :------ |
| java | 1.8 or higher |
| maven | 3.8.4 ~ 3.8.8 |

## Project Execution

### CLI Execution Method

```bash
mvn spring-boot:run
```

### IDE Execution Method

Execute through __Project Right Click > Run As > Spring Boot App__.

### Verification After Execution

After execution, you can check at `http://localhost:port number/` in the browser.  
The initial port number is 8080, and it can be changed in the `server.port` item of the `src/main/resources/application.yml` file.

## Reference Screens

### List Screen

![4th_new_web4](https://github.com/user-attachments/assets/199c6964-1aa1-42bc-a3d2-0234d037057a)

### Post Registration Screen

![4th_new_web5](https://github.com/user-attachments/assets/91c1b668-cb59-45ea-8b13-0e3f34ea9078)

## Changes

### 1. [Java Config Conversion](./Docs/java-config-convert.md)

#### 1) Web.xml -> WebApplicationInitializer Implementation Conversion

#### 2) context-\*.xml -> @Configuration Conversion

#### 3) properties Deletion -> yaml File Conversion
