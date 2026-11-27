# syntax=docker/dockerfile:1.7
#########################################################
# 목적 : egovframe-boot-sample JAR 를 구동하는 도커 이미지 생성 (멀티 스테이지 빌드)
#   1단계(build)   : Maven 으로 JAR 빌드 (사전에 mvn package 불필요)
#   2단계(runtime) : Temurin JRE 17(Alpine) 에서 실행 JAR 구동, root 가 아닌 계정(UID/GID 1000)으로 구동
#   컨텍스트 경로 : / (내장 Tomcat)
# 실행방법 : 프로젝트 루트디렉토리에서(Dockerfile 경로) 아래 명령어 실행
# (명령어) docker build -t egovframe-boot-sample:5.1.0 .
# 접속 URL : http://localhost:8080/
#########################################################

# ---------- Build ----------
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /workspace

COPY pom.xml ./
RUN --mount=type=cache,target=/root/.m2 \
    mvn -B -ntp dependency:go-offline

COPY src ./src
# 내장 HSQL DB 초기화 스크립트 (pom.xml 에서 classpath:/db/hsql 로 패키징)
COPY DATABASE/hsql ./DATABASE/hsql
RUN --mount=type=cache,target=/root/.m2 \
    mvn -B -ntp -DskipTests -Dmaven.javadoc.skip=true -Dpmd.skip=true package && \
    cp target/*.jar /workspace/app.jar

# ---------- Runtime ----------
# 베이스 이미지 : eclipse-temurin 17 JRE (Alpine)
FROM eclipse-temurin:17-jre-alpine AS runtime

LABEL org.opencontainers.image.title="egovframe-boot-sample" \
      org.opencontainers.image.version="5.1.0" \
      org.opencontainers.image.url="https://www.egovframe.go.kr" \
      org.opencontainers.image.licenses="Apache-2.0"

# root 가 아닌 전용 계정(UID/GID 1000)으로 구동 (컨테이너 탈취 시 피해 범위 축소, k8s runAsUser 와 동일)
RUN addgroup -S -g 1000 app && adduser -S -u 1000 -G app app

WORKDIR /app
COPY --from=build --chown=1000:1000 /workspace/app.jar /app/app.jar
USER 1000:1000

ENV JAVA_OPTS="-XX:MaxRAMPercentage=75 -XX:+ExitOnOutOfMemoryError"

# 웹서버 포트
EXPOSE 8080

# 헬스체크
HEALTHCHECK --interval=30s --timeout=5s --start-period=60s --retries=3 \
  CMD wget -qO- http://127.0.0.1:8080/actuator/health > /dev/null || exit 1

# 컨테이너 시작 시 실행 JAR 구동
ENTRYPOINT ["sh","-c","exec java $JAVA_OPTS -jar /app/app.jar"]
