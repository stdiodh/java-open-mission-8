# Docs

본 프로젝트는 우아한테크코스 8기 프리코스 1~3주차 콘솔 미션을 Spring Boot API 서버로 확장한 기록을 문서화합니다. 이번 작업에서는 루트 `README.md`를 수정하지 않고, 구현 근거와 API 흐름을 이 docs 구조에 분리합니다.

## 문서 목차

| 문서 | 설명 |
| :--- | :--- |
| [Architecture](./architecture.md) | 콘솔 미션을 API 서버로 확장한 구조와 요청 처리 흐름 |
| [API Error Policy](./api-error-policy.md) | DTO validation, custom exception, `@RestControllerAdvice`, error code 기반 응답 정책 |
| [API Flow](./api-flows/README.md) | 문자열 덧셈 계산기, 자동차 경주, 로또 API 흐름 |
| [Test](./test.md) | 실제 테스트 실행 결과, CI workflow, coverage 확인 결과 |
| [Deployment](./deployment.md) | Docker, docker-compose, GitHub Actions 배포 근거 |
| [Troubleshooting](./troubleshooting/README.md) | CORS와 배포 실패 가능 지점 정리 |
| [Performance Measurement](./performance-measurement.md) | 성능 측정값 현황과 측정 후보 템플릿 |
| [Query Tuning](./query-tuning.md) | MongoDB `purchaseId` 조회 인덱스 측정과 적용 근거 |
| [Resume Evidence](./resume-evidence.md) | 이력서 문장과 코드/테스트/문서 근거 연결 |
| [Demo Capture](./demo-capture.md) | 데모 파일 생성 상태와 수동 촬영 기준 |

## API 흐름 문서

| 기능 | 문서 |
| :--- | :--- |
| 문자열 덧셈 계산기 | [calculator-add.md](./api-flows/calculator-add.md) |
| 자동차 경주 | [racingcar-play.md](./api-flows/racingcar-play.md) |
| 로또 구매/결과 | [lotto-purchase-result.md](./api-flows/lotto-purchase-result.md) |

## 확인 기준

- `README.md`는 이번 docs 정리 작업에서 수정하지 않습니다.
- README에 나중에 붙일 수 있는 링크 문구는 [readme-link-snippets.md](./readme-link-snippets.md)에만 둡니다.
- 테스트 결과는 `2026-06-04` 로컬 실행 결과만 기록했습니다.
- `./gradlew clean test`와 `./gradlew jacocoTestReport`가 통과했고, 전체 테스트 수는 102개입니다.
- JaCoCo 리포트 기준 line coverage 95.04%, branch coverage 96.34%를 확인했습니다.
- MongoDB `purchaseId` 조회는 로컬 합성 데이터 기준 executionStats before/after를 기록했습니다.
- API latency, p95, throughput 수치는 아직 작성하지 않았습니다.
- API 데모 이미지는 현재 repository에 존재하는 Swagger UI screenshot을 사용합니다.
