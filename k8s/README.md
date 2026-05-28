# egovframe-boot-sample-java-config — 컨테이너/쿠버네티스 운영 안내

## 사전 요구 사항

| 도구 | 최소 버전 | 용도 |
|------|-----------|------|
| Docker | 24 이상 | 이미지 빌드 및 Compose 실행 |
| kubectl | 1.27 이상 | 쿠버네티스 배포 |
| (선택) minikube / kind | — | 로컬 클러스터 |

---

## 1. 이미지 빌드

```bash
# 프로젝트 루트에서 실행
docker build -t egovframe-boot-sample-java-config:5.0.0 .
```

멀티 스테이지 빌드로 Maven 컴파일 → 실행 이미지(JRE 17 Alpine)를 순서대로 생성합니다.  
빌드 캐시(`/root/.m2`)를 활용하려면 BuildKit을 활성화(`DOCKER_BUILDKIT=1`)하세요.

---

## 2. Docker Compose로 로컬 실행

```bash
# 실행
docker compose up -d

# 로그 확인
docker compose logs -f

# 중지 및 볼륨 삭제
docker compose down -v
```

접속: http://localhost:8080

헬스 확인:
```bash
curl -s http://localhost:8080/actuator/health
# {"status":"UP"}
```

---

## 3. 쿠버네티스 배포

### 3-1. 이미지 레지스트리 설정

`k8s/deployment.yaml`의 `image` 값을 실제 레지스트리 경로로 변경합니다.

```yaml
# 예시
image: ghcr.io/<org>/egovframe-boot-sample-java-config:5.0.0
```

로컬 클러스터(minikube)를 사용하는 경우 이미지를 직접 로드합니다.

```bash
minikube image load egovframe-boot-sample-java-config:5.0.0
```

### 3-2. 매니페스트 적용

```bash
kubectl apply -f k8s/service.yaml
kubectl apply -f k8s/deployment.yaml
```

### 3-3. 배포 상태 확인

```bash
# Pod 상태
kubectl get pods -l app.kubernetes.io/name=egovframe-boot-sample-java-config

# Deployment 롤아웃 완료 대기
kubectl rollout status deployment/egovframe-boot-sample-java-config
```

---

## 4. 접속

### NodePort (service.yaml 기준)

```bash
# minikube 사용 시
minikube service egovframe-boot-sample-java-config --url
```

일반 클러스터에서는 NodePort 값(기본 30080)으로 접속합니다.

```
http://<노드 IP>:30080
```

### 포트 포워딩 (개발/테스트 용)

```bash
kubectl port-forward service/egovframe-boot-sample-java-config 8080:8080
```

접속: http://localhost:8080

---

## 5. 헬스체크 확인

애플리케이션이 `/actuator/health` 엔드포인트를 제공합니다.

```bash
# 전체 상태
curl -s http://localhost:8080/actuator/health

# readiness
curl -s http://localhost:8080/actuator/health/readiness

# liveness
curl -s http://localhost:8080/actuator/health/liveness
```

정상 응답 예시:
```json
{"status":"UP"}
```

k8s Probe 설정 요약:

| Probe | 경로 | initialDelaySeconds | periodSeconds |
|-------|------|---------------------|---------------|
| readiness | /actuator/health/readiness | 20 | 10 |
| liveness | /actuator/health/liveness | 60 | 20 |

Pod가 `Running` 상태이고 `READY` 열이 `1/1`이면 정상입니다.

---

## 6. 삭제

```bash
kubectl delete -f k8s/deployment.yaml
kubectl delete -f k8s/service.yaml
```
