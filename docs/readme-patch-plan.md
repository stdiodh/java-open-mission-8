# README Patch Plan

README는 전체 재작성하지 않습니다. 기존 `### 📊 테스트 실행 결과 (Test Execution Result)` 섹션 아래에 CI/CD 근거 표만 추가합니다.

## 삽입 위치

- `README.md`의 테스트 실행 결과 스크린샷 아래
- 기존 `-----` 구분선 위

## 추가한 내용

```md
### 🔁 CI/CD 실행 근거 (GitHub Actions)

| 항목 | 측정 결과 | 조건 | 근거 | 날짜/commit | 이력서 사용 |
| :--- | :--- | :--- | :--- | :--- | :--- |
| `Test CICD` workflow | 평균 `85.4s`, 중앙값 `85.5s` | 최근 성공 10회 | `gh run list`, `gh run view` | 2025-11-21~2026-06-04, latest `5e84f0d` | 가능 |
| `Deploy CICD` job | 평균 `117.2s`, 중앙값 `117.5s` | 확인 가능한 성공 4회 | `gh run list`, `gh run view` | 2025-11-16~2026-06-04, latest `5e84f0d` | `n=4` 명시 시 가능 |

> 로컬 테스트 시간과 GitHub Actions 실행 시간은 측정 범위가 달라 before/after 개선률로 비교하지 않습니다.
> 상세 근거와 `[확인 필요]` 항목은 [CI/CD Metrics](./docs/ci-cd-metrics.md)에 정리했습니다.
```

## 제외한 내용

- API latency, throughput, p95
- Docker layer cache 개선률
- Gradle cache 개선률
- coverage gate 통과 표현
- 테스트/coverage 개선률 표현

위 항목은 현재 repository에서 직접 검증되지 않으므로 README에 추가하지 않습니다.
