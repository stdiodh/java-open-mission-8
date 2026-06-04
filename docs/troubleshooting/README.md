# Troubleshooting

본 프로젝트의 문제 해결 문서는 repository에서 확인한 설정과 현재 테스트 실행 결과를 기준으로 작성합니다.

| 문서 | 설명 |
| :--- | :--- |
| [CORS](./cors.md) | 프론트엔드 origin 허용과 Swagger server URL 설정 |
| [Deploy Error](./deploy-error.md) | Docker, EC2, MongoDB 환경 변수, GitHub Actions 배포 실패 후보 |

## 현재 확인된 주요 위험

- main runtime의 `MONGO_URI`가 없거나 유효하지 않으면 애플리케이션 기동 또는 MongoDB 연결이 실패할 수 있습니다.
- 테스트 리소스는 `src/test/resources/application.yml`에 있으며, test scope에서는 MongoDB 자동 인덱스 생성을 끕니다.
- `2026-06-04` 로컬 기준 `./gradlew clean test`는 102개 테스트가 통과합니다.
- deploy workflow는 테스트를 제외하고 빌드하므로, 배포 전 테스트 성공을 별도로 확인해야 합니다.
