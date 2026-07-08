# CI/CD Metrics

GitHub Actions 실행 시간은 `2026-07-08`에 `gh` CLI로 조회했습니다. 로컬 checkout은 `d6fd898`이지만, 원격 `main`의 최신 확인 commit은 `5e84f0d475a6630f71dd92b08952f5640b4508de`입니다.

## Workflows

| Scope | Workflow | Trigger | 주요 단계 | Source |
| :--- | :--- | :--- | :--- | :--- |
| CI / test | `Test CICD` | `main` push, pull request | checkout, JDK 21, Gradle setup, `./gradlew test` | `.github/workflows/test.yml` |
| build / docker / deploy | `java-open-mission-8 Deploy CICD` | `main` push | Gradle build, Docker build/push, SCP, EC2 SSH deploy | `.github/workflows/deploy.yml` |
| performance | 없음 | 없음 | 없음 | `.github/workflows` 확인 |

## Recent Run Metrics

| Metric | Value | Measurement condition | Source command | Date / commit | Resume-safe |
| :--- | :--- | :--- | :--- | :--- | :--- |
| `Test CICD` workflow duration | 평균 `85.4s`, 중앙값 `85.5s` | 최근 성공 10회, run `createdAt`~`updatedAt` | `gh run list --workflow 'Test CICD' --limit 100`; `gh run view <run-id> --json jobs` | 2025-11-21~2026-06-04, latest `5e84f0d` | 가능 |
| `Test CICD` job duration | 평균 `81.0s`, 중앙값 `81.5s` | 최근 성공 10회, job `startedAt`~`completedAt` | `gh run view <run-id> --json jobs` | 2025-11-21~2026-06-04, latest `5e84f0d` | 가능 |
| `Run test` step duration | 평균 `71.5s`, 중앙값 `72.0s` | step timestamp가 제공되는 2026-06-04 성공 6회 | `gh run view <run-id> --json jobs` | 2026-06-04, latest `5e84f0d` | 조건 명시 시 가능 |
| `Deploy CICD` workflow duration | 평균 `122.0s`, 중앙값 `121.5s` | 확인 가능한 성공 4회, run `createdAt`~`updatedAt` | `gh run list --workflow 'java-open-mission-8 Deploy CICD' --limit 100` | 2025-11-16~2026-06-04, latest `5e84f0d` | `n=4` 명시 시 가능 |
| `Deploy CICD` job duration | 평균 `117.2s`, 중앙값 `117.5s` | 확인 가능한 성공 4회, job `startedAt`~`completedAt` | `gh run view <run-id> --json jobs` | 2025-11-16~2026-06-04, latest `5e84f0d` | `n=4` 명시 시 가능 |
| latest deploy Gradle build step | `56s` step timestamp, log `BUILD SUCCESSFUL in 55s` | latest deploy run 1회, `./gradlew clean build -x test` | `gh run view 26951120303 --json jobs`; `gh run view 26951120303 --job 79516336969 --log` | 2026-06-04, `5e84f0d` | 단독 이력서 수치로는 보류 |
| latest Docker build/push step | `14s` | latest deploy run 1회, `docker login/build/tag/push`가 한 step에 묶임 | `gh run view 26951120303 --json jobs` | 2026-06-04, `5e84f0d` | 보류 |
| latest EC2 copy step | `7s` | latest deploy run 1회, `appleboy/scp-action@v0.1.3` | `gh run view 26951120303 --json jobs` | 2026-06-04, `5e84f0d` | 보류 |
| latest EC2 deploy step | `16s` | latest deploy run 1회, `appleboy/ssh-action@master` | `gh run view 26951120303 --json jobs` | 2026-06-04, `5e84f0d` | 보류 |

## Cache Evidence

| 항목 | 확인 결과 | Source | Resume-safe |
| :--- | :--- | :--- | :--- |
| Gradle cache 설정 | `gradle/actions/setup-gradle@v3` 사용, cache disabled 아님 | `.github/workflows/test.yml`, `.github/workflows/deploy.yml` | 설정 사실만 가능 |
| Gradle cache restore | 확인한 2026-06-04 로그 7개 모두 `Gradle User Home cache not found` 또는 restore 실패 | `gh run view <run-id> --job <job-id> --log` | 효과 수치 사용 불가 |
| Docker layer cache | workflow에 `docker/build-push-action` 또는 layer cache 설정 없음 | `.github/workflows/deploy.yml` | 효과 수치 사용 불가 |

## Same-scope Comparisons

- 로컬 테스트 시간과 GitHub Actions 실행 시간은 측정 범위와 환경이 다르므로 before/after로 비교하지 않습니다.
- `Test CICD`와 `Deploy CICD`는 서로 다른 workflow이므로 before/after로 비교하지 않습니다.
- Docker build와 push는 현재 한 step에 묶여 있어 build-only, push-only 평균을 분리하지 않습니다.
- Gradle cold/warm cache 비교는 cache hit 여부가 분리되는 run 집합이 생긴 뒤에만 계산합니다.

## [확인 필요] 작업 계획

| 항목 | 확인 방법 | 필요한 변경 | 완료 기준 |
| :--- | :--- | :--- | :--- |
| Docker build-only / push-only 평균 | deploy workflow의 Docker step을 `docker login`, `docker build`, `docker tag`, `docker push`로 분리한 뒤 성공 run 5~10회 수집 | `.github/workflows/deploy.yml` 별도 작업 | 같은 workflow에서 분리된 step timestamp 확보 |
| Gradle warm cache 효과 | `gh run view <run-id> --job <job-id> --log`에서 cache restored/cache not found를 분류 | workflow 변경 없음 | cold/warm run이 각각 충분히 쌓인 뒤 평균/중앙값 계산 |
| Docker layer cache 효과 | layer cache 설정 전후를 같은 deploy workflow에서 비교 | `docker/build-push-action`과 cache 설정 도입 필요 | 같은 조건의 before/after run 확보 |
| CI/CD before/after 개선률 | 같은 workflow, 같은 step, 같은 branch 조건의 변경 전후 run 비교 | 비교 대상 변경사항을 commit/PR로 분리 | 다른 workflow끼리 비교하지 않는 개선률 산출 |

## Commands

```bash
gh run list --workflow 'Test CICD' --limit 100
gh run list --workflow 'java-open-mission-8 Deploy CICD' --limit 100
gh run view <run-id> --json jobs
gh run view <run-id> --job <job-id> --log
```
