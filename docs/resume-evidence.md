# Resume Evidence

이 문서는 이력서에 쓸 수 있는 문장과 아직 쓰면 안 되는 문장을 구분합니다. 모든 수치는 조건, source, 날짜 또는 commit이 함께 있을 때만 사용합니다.

## Resume-safe Bullets

- GitHub Actions에서 JDK 21, Gradle 기반 테스트 workflow를 구성하고, 최근 성공 10회 기준 workflow 실행 시간을 평균 `85.4s`, 중앙값 `85.5s`로 측정했습니다.
- GitHub Actions 배포 workflow에서 Gradle build, Docker image build/push, EC2 compose 배포 단계를 구성하고, 확인 가능한 성공 4회 기준 job 실행 시간을 평균 `117.2s`, 중앙값 `117.5s`로 측정했습니다.
- 로컬 Gradle 기준 102개 테스트 통과와 JaCoCo line coverage 95.04%, branch coverage 96.34%를 문서화했습니다.
- 콘솔 기반 프리코스 미션을 Spring Boot REST API 요청/응답 구조로 확장하고, 문자열 덧셈 계산기, 자동차 경주, 로또 기능을 endpoint 단위로 분리했습니다.
- DTO validation, custom exception, `@RestControllerAdvice`, error code를 사용해 API 오류 응답을 `status/error/code/message` 구조로 표준화했습니다.

## Evidence Table

| Claim | Metric value | Measurement condition | Source | Date / commit | Resume-safe |
| :--- | :--- | :--- | :--- | :--- | :--- |
| Test workflow duration measured | 평균 `85.4s`, 중앙값 `85.5s` | `Test CICD` 최근 성공 10회 | `gh run list`, `gh run view` | latest `5e84f0d`, 조회일 2026-07-08 | 가능 |
| Deploy job duration measured | 평균 `117.2s`, 중앙값 `117.5s` | `Deploy CICD` 확인 가능한 성공 4회 | `gh run list`, `gh run view` | latest `5e84f0d`, 조회일 2026-07-08 | 가능 |
| Local test pass checked | 102 tests, Gradle command `5s`, suite `0.961s` | local `./gradlew clean test` | `docs/test.md`, `docs/PORTFOLIO.md` | 2026-07-08 local verification, latest `5e84f0d` | 로컬 테스트 조건 명시 시 가능 |
| API performance improved | `[확인 필요]` | before/after 성능 측정 없음 | `docs/performance-measurement.md` | 2026-07-08 정리 | 불가 |
| Coverage checked | line 95.04%, branch 96.34% | local `./gradlew jacocoTestReport` | `build/reports/jacoco/test/jacocoTestReport.csv`, `docs/PORTFOLIO.md` | 2026-06-04, latest `5e84f0d` | 가능 |

## Do Not Use Yet

- API 응답 속도를 개선했습니다.
- p95 latency를 N ms로 낮췄습니다.
- throughput을 N% 개선했습니다.
- Docker layer cache로 build 시간을 단축했습니다.
- Gradle cache로 CI 시간을 N% 단축했습니다.
- 운영 환경 장애 대응 시간을 N분 단축했습니다.

## [확인 필요]

| 항목 | 다음 작업 |
| :--- | :--- |
| Docker build-only / push-only 평균 | workflow step 분리 후 성공 run 수집 |
| Gradle warm cache 효과 | cache hit/cold run 분리 집계 |
| Docker layer cache 효과 | layer cache 설정 도입 후 같은 workflow before/after 비교 |
| coverage gate | threshold 기준이 없으므로 gate 통과 수치로 표현하지 않음 |
| CI/CD 개선률 | 같은 workflow, 같은 조건의 변경 전후 run 확보 |
