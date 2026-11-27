# egovframe-boot-sample — 컨테이너/쿠버네티스 운영 안내

## 사전 요구 사항

| 도구 | 최소 버전 | 용도 |
|------|-----------|------|
| Docker | 24 이상 | 이미지 빌드 및 Compose 실행 |
| kubectl | 1.27 이상 | 쿠버네티스 배포 |
| (선택) minikube / Docker Desktop / kind | — | 로컬 클러스터 |

| 항목 | 값 |
|------|----|
| 리소스명(Deployment/Service/이미지) | `egovframe-boot-sample` |
| 컨텍스트 경로 | `/` |
| 컨테이너 포트 | `8080` |
| NodePort | `30080` |

---

## 1. 이미지 빌드

```bash
# 프로젝트 루트에서 실행
docker build -t egovframe-boot-sample:5.1.0 .
```

멀티 스테이지 빌드로 Maven JAR 빌드 → 실행 이미지(JRE 17 Alpine)를 순서대로 생성합니다.  
빌드 캐시(`/root/.m2`)를 활용하려면 BuildKit을 활성화(`DOCKER_BUILDKIT=1`)하세요.

---

## 2. Docker Compose로 로컬 실행

```bash
# 실행
docker compose up -d --build

# 로그 확인
docker compose logs -f

# 중지 및 컨테이너 삭제
docker compose down
```

접속: http://localhost:8080/

| 환경 변수 | 기본값 | 설명 |
|-----------|--------|------|
| `APP_VERSION` | `5.1.0` | 빌드/실행할 이미지 태그 |
| `JAVA_OPTS` | `-Xms256m -Xmx512m -XX:+ExitOnOutOfMemoryError` | JVM 옵션 |
| `SESSION_COOKIE_SECURE` | `false` | 세션 쿠키 Secure 속성. TLS를 프록시에서 종료하는 운영 환경은 `true` |

예: `SESSION_COOKIE_SECURE=true docker compose up -d --build`

헬스 확인:
```bash
curl -s http://localhost:8080/actuator/health
```

---

## 3. 쿠버네티스 배포

### 3-1. 이미지 준비

`k8s/deployment.yml`의 `image` 값을 실제 레지스트리 경로로 변경합니다.

```yaml
# 예시
image: ghcr.io/<org>/egovframe-boot-sample:5.1.0
```

로컬 클러스터는 환경에 따라 이미지를 적재합니다.

| 환경 | 이미지 적재 |
|------|-------------|
| minikube | `minikube image load egovframe-boot-sample:5.1.0` |
| kind | `kind load docker-image egovframe-boot-sample:5.1.0` |
| Docker Desktop | 별도 적재 불필요 (로컬 Docker 이미지 그대로 사용) |

> **참고**
> - 샘플은 내장 HSQLDB(인메모리)를 사용하므로 Pod 재시작 시 데이터가 초기화되고, Pod 간 데이터가 공유되지 않습니다. 외부 DB 연동 전까지 `replicas: 1`을 유지하세요.
> - 컨테이너는 root 가 아닌 계정(UID/GID 1000)으로 실행됩니다 (Dockerfile `USER`, deployment.yml `securityContext`).
> - TLS를 Ingress/로드밸런서에서 종료하는 경우 `deployment.yml`의 `SESSION_COOKIE_SECURE` 값을 `"true"`로 변경합니다.

### 3-2. 매니페스트 적용

```bash
kubectl apply -f k8s/service.yml
kubectl apply -f k8s/deployment.yml
```

### 3-3. 배포 상태 확인

```bash
# Pod 상태
kubectl get pods -l app.kubernetes.io/name=egovframe-boot-sample

# Deployment 롤아웃 완료 대기
kubectl rollout status deployment/egovframe-boot-sample

# 전체 확인
kubectl get all -l app.kubernetes.io/name=egovframe-boot-sample
```

---

## 4. 접속

### NodePort (service.yml 기준)

service.yml은 `type: NodePort`, `nodePort: 30080`로 설정되어 있습니다.  
클러스터 내부에서는 `egovframe-boot-sample:8080`으로 접근할 수 있습니다.

| 환경 | 접속 URL |
|------|----------|
| minikube | `http://<minikube INTERNAL-IP>:30080/` (`kubectl get nodes -o wide` 로 INTERNAL-IP 확인) |
| Docker Desktop / kind | `http://localhost:30080/` |

> Apple Silicon Mac 에서 minikube + docker driver 를 사용하면 NodePort 로 연결되지 않을 수 있습니다.
> 이 경우 `minikube service egovframe-boot-sample` 으로 로컬 터널을 구성하거나 아래 포트 포워딩을 사용합니다.

### 포트 포워딩 (개발/테스트 용)

```bash
kubectl port-forward service/egovframe-boot-sample 8080:8080
```

접속: http://localhost:8080/

---

## 5. 헬스체크 확인

```bash
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
kubectl delete -f k8s/deployment.yml
kubectl delete -f k8s/service.yml
```

> 이전 버전 매니페스트(Service `type: ClusterIP`)로 배포한 경우 `kubectl apply -f k8s/service.yml` 로 NodePort 로 갱신됩니다.
