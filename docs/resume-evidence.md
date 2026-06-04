# Resume Evidence

> 메인 README로 돌아가기: [README](../README.md)

## 이력서 연결 요약

| 이력서 문장 | README 위치 | 상세 근거 | 검증 상태 |
| :--- | :--- | :--- | :--- |
| 콘솔 기반 프리코스 미션을 REST API 요청/응답 구조로 확장했습니다. | README 직접 수정 없음, [README Link Snippets](./readme-link-snippets.md) 후보 | [Architecture](./architecture.md), [API Flow](./api-flows/README.md) | 코드 구조와 controller 확인 |
| DTO validation, custom exception, `@RestControllerAdvice`를 사용해 API 오류 응답을 표준화했습니다. | README 직접 수정 없음, [README Link Snippets](./readme-link-snippets.md) 후보 | [API Error Policy](./api-error-policy.md) | `GlobalExceptionHandler`, `ErrorCode`, DTO annotation 확인 |
| GitHub Actions 기반 테스트 파이프라인을 구성하고, 테스트 실행 결과를 문서화했습니다. | README 직접 수정 없음, [README Link Snippets](./readme-link-snippets.md) 후보 | [Test](./test.md), `.github/workflows/test.yml` | workflow 확인, `./gradlew clean test` 102개 통과 |
| API 오류 응답 테스트와 JaCoCo 리포트로 검증 범위를 보강했습니다. | README 직접 수정 없음, [README Link Snippets](./readme-link-snippets.md) 후보 | [Test](./test.md) | 102개 테스트 통과, line coverage 95.04%, branch coverage 96.34% |
| 로또 결과 조회의 `purchaseId` 조건에 MongoDB 인덱스를 적용했습니다. | README 직접 수정 없음, [README Link Snippets](./readme-link-snippets.md) 후보 | [Query Tuning](./query-tuning.md) | 합성 10만 건 기준 COLLSCAN -> IXSCAN 확인 |

## 바로 사용할 수 있는 문장

- 콘솔 기반 프리코스 미션을 Spring Boot REST API 요청/응답 구조로 확장하고, 문자열 덧셈 계산기·자동차 경주·로또 기능을 기능별 endpoint로 분리했습니다.
- DTO validation, custom exception, `@RestControllerAdvice`, error code를 사용해 API 오류 응답을 `status/error/code/message` 구조로 표준화했습니다.
- 자동차 경주 API에서 라운드별 진행 결과와 최종 우승자를 JSON으로 반환하도록 설계해 프론트엔드 시각화에 필요한 응답 구조를 만들었습니다.
- 로또 API를 구매와 결과 확인 단계로 분리하고, MongoDB에 저장한 구매 내역을 `purchaseId`로 조회해 당첨 통계를 계산하도록 구현했습니다.
- GitHub Actions에서 JDK 21과 Gradle 기반 테스트 workflow를 구성하고, 로컬에서 필수 환경변수를 명시해 102개 테스트 통과를 확인했습니다.
- JaCoCo 리포트 생성 환경을 추가하고 controller/advice 테스트로 line coverage 87.06%에서 95.04%, branch coverage 93.90%에서 96.34%로 개선된 것을 확인했습니다.
- 로또 결과 조회의 `purchaseId` 조건에 non-unique index를 적용하고, 합성 10만 건 기준 MongoDB executionStats에서 COLLSCAN에서 IXSCAN으로 변경된 것을 확인했습니다.

## 아직 사용할 수 없는 문장

- 운영 환경에서 MongoDB 연결 없이도 애플리케이션이 정상 동작합니다.
  - 제한: main runtime은 여전히 유효한 `MONGO_URI`가 필요합니다. 테스트 통과는 test resource 기준입니다.
- API 응답 속도를 개선했습니다.
  - 제한: 이번 단계에서는 API latency, p95, throughput을 측정하지 않았습니다.

## 검증된 수치

- [x] line coverage 87.06% -> 95.04%
- [x] branch coverage 93.90% -> 96.34%
- [x] 필수 환경변수 명시 후 테스트 102개 전체 통과
- [x] `./gradlew clean test` 실행 시 102개 테스트 전체 통과
- [x] MongoDB `purchaseId` 조회 synthetic benchmark: docs examined 100,000 -> 1,000

## 아직 쓰면 안 되는 표현

- 검증되지 않은 커버리지 달성 표현
- 검증되지 않은 응답 속도 개선 표현
- 검증되지 않은 장애 대응 시간 단축 표현
- 검증되지 않은 처리량 개선 표현
- 환경변수 설정 없이 테스트가 통과함

## 근거 링크

| 구분 | 위치 | 설명 |
| :--- | :--- | :--- |
| 코드 | `src/main/java/.../calculator/controller/CalculatorController.java` | `POST /api/calculator/add` |
| 코드 | `src/main/java/.../racingcar/controller/RacingCarController.java` | `POST /api/racingcar/play` |
| 코드 | `src/main/java/.../lotto/controller/LottoController.java` | `POST /api/lottos`, `POST /api/lottos/{purchaseId}/results` |
| 코드 | `src/main/java/.../common/exception/GlobalExceptionHandler.java` | 전역 예외 처리 |
| 코드 | `src/main/java/.../common/exception/ErrorCode.java` | domain별 error code |
| 테스트 | `src/test/java/...` | domain/service 중심 테스트 |
| 테스트 | `src/test/java/.../controller/*ControllerTest.java` | controller 성공 응답과 validation/error response 테스트 |
| CI | `.github/workflows/test.yml` | JDK 21 기반 `./gradlew test` workflow |
| 배포 | `.github/workflows/deploy.yml` | DockerHub push와 EC2 docker-compose 배포 workflow |
| 문서 | [Test](./test.md) | 실제 테스트 실행 결과와 coverage 미확인 사유 |
| 문서 | [API Error Policy](./api-error-policy.md) | 오류 응답 정책 |
| 문서 | [Query Tuning](./query-tuning.md) | `purchaseId` 인덱스 측정과 적용 근거 |
