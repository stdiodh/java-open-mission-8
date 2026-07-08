# Test

본 프로젝트의 테스트와 coverage 결과는 `2026-06-04` 로컬에서 Gradle 명령을 실행해 기록한 값과, 현재 repository의 `build.gradle.kts` 설정을 기준으로 정리합니다.

## 실행 환경

| 항목 | 값 |
| :--- | :--- |
| 테스트 명령 | `./gradlew clean test` |
| coverage 명령 | `./gradlew jacocoTestReport` |
| Project Java toolchain | `build.gradle.kts` 기준 Java `21` |
| Gradle | Wrapper 기준 Gradle `8.14.3` |
| 테스트 설정 파일 | `src/test/resources/application.yml` |
| Test `MONGO_URI` | `mongodb://dummy:dummy@localhost:27017/dummy-db` |
| Test `SERVER_URL` | `http://localhost:8080` |

## 테스트 실행 결과

| Metric | Value | Measurement condition | Source | Date / commit | Resume-safe |
| :--- | :--- | :--- | :--- | :--- | :--- |
| Test count | 102 passed, 0 failures, 0 errors, 0 skipped | local `./gradlew clean test` | `docs/PORTFOLIO.md`, `build/reports/tests/test/index.html` | 2026-06-04, latest `5e84f0d` | 가능 |
| Gradle test duration | `BUILD SUCCESSFUL in 5s` | local `./gradlew clean test` | `docs/PORTFOLIO.md` | 2026-06-04, latest `5e84f0d` | 로컬 조건 명시 시 가능 |
| Test suite duration | `0.961s` | local Gradle test HTML report after `./gradlew clean test` | `build/reports/tests/test/index.html` | 2026-07-08 local verification | 참고용 |

## Coverage 확인 결과

| Metric | Value | Measurement condition | Source | Date / commit | Resume-safe |
| :--- | :--- | :--- | :--- | :--- | :--- |
| Line coverage | 95.04% | local `./gradlew jacocoTestReport` | `build/reports/jacoco/test/jacocoTestReport.csv`, `docs/PORTFOLIO.md` | 2026-06-04, latest `5e84f0d` | 가능 |
| Branch coverage | 96.34% | local `./gradlew jacocoTestReport` | `build/reports/jacoco/test/jacocoTestReport.csv`, `docs/PORTFOLIO.md` | 2026-06-04, latest `5e84f0d` | 가능 |
| Coverage gate | threshold rule 없음 | `jacocoTestCoverageVerification` 설정 없음 | `build.gradle.kts` | latest `5e84f0d` | 제한 사항 명시 필요 |

## CI Workflow

`.github/workflows/test.yml` 기준 테스트 workflow는 다음 조건과 단계로 구성되어 있습니다.

| 항목 | 내용 |
| :--- | :--- |
| Trigger | `main` branch push, pull request |
| Runner | `ubuntu-latest` |
| JDK | Temurin 21 |
| Gradle | `gradle/actions/setup-gradle@v3` |
| Test command | `./gradlew test` |
| Test env | `SERVER_URL`와 `MONGO_URI`를 placeholder 값으로 주입 |

## 검증 범위

테스트 파일은 calculator, racingcar, lotto의 domain/service/controller 중심으로 구성되어 있습니다. API latency, throughput, p95는 이 문서에서 측정하지 않았습니다.
