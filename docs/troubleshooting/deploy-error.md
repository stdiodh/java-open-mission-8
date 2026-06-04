# Deploy Error Troubleshooting

본 문서는 GitHub Actions, Dockerfile, docker-compose, application 설정에서 확인한 배포 실패 후보를 정리합니다.

## 배포 workflow 요약

| 단계 | 확인 파일 | 내용 |
| :--- | :--- | :--- |
| Build | `.github/workflows/deploy.yml` | `./gradlew clean build -x test` |
| Image push | `.github/workflows/deploy.yml` | DockerHub login, build, tag, push |
| Compose copy | `.github/workflows/deploy.yml` | EC2의 `/home/${AWS_USER}/app/java-open-mission-8`로 compose 복사 |
| Deploy | `.github/workflows/deploy.yml` | EC2에서 image pull, `docker-compose up -d`, image prune |
| Runtime | `docker-compose.yml` | app + mongo service, `.env` 사용 |

## 실패 후보와 확인 방법

| 증상 | 확인할 항목 |
| :--- | :--- |
| GitHub Actions에서 Docker login 실패 | DockerHub 계정, 비밀번호 또는 토큰, repository 이름 secret 존재 여부 |
| EC2 접속 실패 | AWS host, user, SSH private key secret과 EC2 SSH 접근 가능 여부 |
| app container 기동 실패 | EC2의 `/home/ec2-user/app/java-open-mission-8/.env` 존재 여부 |
| Mongo 연결 실패 | `.env`의 `MONGO_URI`가 `mongodb://` 또는 `mongodb+srv://`로 시작하는지 확인 |
| API 접근 포트 불일치 | host port `8082`, container port `8080` 매핑 확인 |
| 배포는 되었지만 테스트가 깨짐 | deploy workflow가 `-x test`로 테스트를 제외하고 빌드하는 점 확인 |

## 현재 테스트 실패와 연결되는 지점

로컬 `./gradlew clean test`는 `2026-06-04` 기준 102개 테스트가 통과했습니다. 배포 환경에서는 test resource가 아니라 main `application.yaml`을 사용하므로, EC2 `.env`에 유효한 `MONGO_URI`가 없거나 인증 정보가 맞지 않으면 MongoDB 설정 또는 인증 오류가 발생할 수 있습니다.

## 확인되지 않은 항목

- EC2 내부 Nginx 설정
- Certbot 인증서 설정
- 실제 DockerHub repository 공개 여부
- 최근 GitHub Actions 실행 성공 여부

위 항목은 repository 파일만으로 확인되지 않아 `[확인 필요]` 상태입니다.
