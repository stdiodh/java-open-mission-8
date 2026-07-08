# Docs

이 디렉터리는 이력서에 사용할 수 있는 수치와 아직 쓰면 안 되는 수치를 구분해 정리합니다. 루트 `README.md`는 전체 재작성하지 않고, 필요한 근거 링크와 짧은 표만 추가합니다.

| 문서 | 설명 |
| :--- | :--- |
| [ci-cd-metrics.md](./ci-cd-metrics.md) | GitHub Actions workflow, run/job/step duration, cache 확인 결과 |
| [resume-evidence.md](./resume-evidence.md) | 이력서 사용 가능 문장과 사용 금지 문장 |
| [test.md](./test.md) | 로컬 테스트 실행 결과와 CI 테스트 workflow 조건 |
| [performance-measurement.md](./performance-measurement.md) | API 성능 수치 미측정 항목과 측정 기준 |
| [demo-capture.md](./demo-capture.md) | Swagger/curl 기반 API 동작 증거 기록 |
| [readme-patch-plan.md](./readme-patch-plan.md) | README 최소 패치 위치와 내용 |

## 정리 기준

- 검증 가능한 수치만 이력서 후보로 분류합니다.
- 같은 scope가 아닌 수치를 before/after로 비교하지 않습니다.
- `[확인 필요]` 항목은 확인 방법과 필요한 변경을 함께 기록합니다.
- 검증되지 않은 성능 개선률, 운영 지표는 이력서 문장으로 사용하지 않습니다.
- Coverage 수치는 local JaCoCo report 조건을 함께 명시할 때만 사용합니다.
