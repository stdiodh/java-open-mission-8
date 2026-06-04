# Test

본 프로젝트의 테스트와 coverage 결과는 `2026-06-04` 로컬에서 실제 실행한 Gradle 명령 기준으로 기록합니다. README는 수정하지 않았고, commit/push도 수행하지 않았습니다.

## 작업 지시서 확인

| 항목 | 결과 |
| :--- | :--- |
| 세부 작업 파일 | `10_COVERAGE-QUERY-TUNING_TASKS.md` |
| 확인 결과 | repository와 `/Users/dh/Downloads`에서 파일을 찾지 못함 |
| 처리 | goal prompt의 명시 조건을 기준으로 진행 |

## 실행 환경

| 항목 | 값 |
| :--- | :--- |
| 요청 테스트 명령 | `./gradlew clean test` |
| coverage 확인 명령 | `./gradlew jacocoTestReport` |
| CI env 재현 명령 | `MONGO_URI=mongodb://dummy:dummy@localhost:27017/dummy-db SERVER_URL=https://dummy-server.com ./gradlew clean test` |
| Gradle launcher JVM | OpenJDK `23.0.1` |
| Project Java toolchain | `build.gradle.kts` 기준 Java `21` |
| Gradle | Wrapper 기준 Gradle `8.14.3` |
| test resource | `src/test/resources/application.yml` |

## 테스트 실행 결과

요청 명령을 그대로 실행한 결과입니다.

```text
BUILD SUCCESSFUL in 5s
```

| 항목 | 결과 |
| :--- | :--- |
| 전체 테스트 수 | 102 |
| 실패 | 0 |
| skipped | 0 |
| 테스트 suite 누적 시간 | 1.275s |
| Gradle 명령 전체 시간 | 5s |
| 테스트 리포트 | `build/reports/tests/test/index.html` |

CI workflow에 설정된 dummy env를 재현한 결과입니다.

```text
BUILD SUCCESSFUL in 5s
```

| 항목 | 결과 |
| :--- | :--- |
| 실행 명령 | `MONGO_URI=mongodb://dummy:dummy@localhost:27017/dummy-db SERVER_URL=https://dummy-server.com ./gradlew clean test` |
| 결과 | 성공 |
| 이유 | `src/test/resources/application.yml`에서 test scope의 `auto-index-creation`을 `false`로 설정해 context load가 MongoDB 인증에 의존하지 않음 |

## 추가한 테스트

coverage를 올리기 위한 빈 테스트는 추가하지 않았습니다. 기존 API 오류 정책과 controller 응답 계약을 검증하는 테스트만 추가했습니다.

| 테스트 파일 | 검증 내용 |
| :--- | :--- |
| `src/test/java/.../calculator/controller/CalculatorControllerTest.java` | 문자열 덧셈 성공 응답, domain 예외의 표준 오류 응답 |
| `src/test/java/.../racingcar/controller/RacingCarControllerTest.java` | 자동차 경주 성공 응답, DTO validation 실패 응답 |
| `src/test/java/.../lotto/controller/LottoControllerTest.java` | 로또 구매 성공 응답, 결과 조회 성공 응답, DTO validation 실패 응답 |

테스트 추가 과정에서 WebFlux의 DTO validation 실패가 기존 handler에서 500으로 처리되는 실제 문제를 확인했습니다. `GlobalExceptionHandler`에 `WebExchangeBindException` handler를 추가해 `COMMON_INVALID_INPUT` 응답으로 처리하도록 수정했습니다.

## 테스트 설정 정리

기존 테스트용 `application.yml`은 `src/test/java/.../resources` 아래에 있어 테스트 리소스로 로드되지 않았습니다. 이번 단계에서 `src/test/resources/application.yml`로 옮기고, 테스트 context에서는 MongoDB 자동 인덱스 생성을 끄도록 설정했습니다.

| 항목 | 변경 |
| :--- | :--- |
| 기존 위치 | `src/test/java/woowacourse_precoruse/java_open_mission_8/resources/application.yml` |
| 새 위치 | `src/test/resources/application.yml` |
| 테스트 Mongo URI | `mongodb://dummy:dummy@localhost:27017/dummy-db` |
| 테스트 auto index | `false` |

## Coverage 설정

이번 단계에서 `jacoco` plugin을 추가하고 `jacocoTestReport`의 XML, HTML, CSV 리포트 생성을 설정했습니다. coverage threshold나 verification gate는 추가하지 않았습니다.

| 항목 | 값 |
| :--- | :--- |
| HTML report | `build/reports/jacoco/test/html/index.html` |
| XML report | `build/reports/jacoco/test/jacocoTestReport.xml` |
| CSV report | `build/reports/jacoco/test/jacocoTestReport.csv` |
| coverage gate | 없음 |
| exclude 추가 | 없음 |

## Coverage Before/After

JaCoCo task가 없던 초기 상태에서는 coverage 수치를 확인할 수 없었습니다. 따라서 before 수치는 JaCoCo 리포트 생성을 설정한 직후, 테스트를 추가하기 전 `95`개 테스트 기준 리포트입니다.

| 지표 | 테스트 추가 전 | 테스트 추가 후 |
| :--- | :--- | :--- |
| Test count | 95 | 102 |
| Instruction coverage | 88.64% | 96.21% |
| Branch coverage | 93.90% | 96.34% |
| Line coverage | 87.06% | 95.04% |
| Method coverage | 85.07% | 94.85% |
| Complexity coverage | 86.29% | 94.35% |

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

## 남은 개선 항목

- coverage gate는 현재 추가하지 않았습니다. 안정적인 기준을 정하려면 CI에서 여러 번 리포트를 확인한 뒤 최소 기준을 정해야 합니다.
- MongoDB 인덱스 적용 후 쓰기 latency와 index storage size는 아직 측정하지 않았습니다.
- controller 통합 테스트는 slice test 기준입니다. 실제 MongoDB까지 포함한 end-to-end API 테스트는 별도 단계에서 추가할 수 있습니다.
