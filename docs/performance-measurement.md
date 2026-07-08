# Performance Measurement

본 프로젝트에는 현재 before/after 성능 측정값이 없습니다. 응답 속도 개선률, 처리량 개선률, p95 latency 같은 수치는 실제 측정 로그나 리포트가 생기기 전까지 이력서에 쓰지 않습니다.

## 현재 상태

| 항목 | 상태 |
| :--- | :--- |
| latency 측정값 | 없음 |
| throughput 측정값 | 없음 |
| before/after 비교 | 없음 |
| 성능 개선률 | 작성 금지 |
| 부하 테스트 스크립트 | repository에서 확인되지 않음 |

## 측정 대상 API 후보

| API | 측정 이유 |
| :--- | :--- |
| `POST /api/calculator/add` | 문자열 길이와 구분자 처리에 따른 parsing 비용 확인 |
| `POST /api/racingcar/play` | 자동차 수와 시도 횟수 증가에 따른 라운드 계산 비용 확인 |
| `POST /api/lottos` | 구매 금액 증가에 따른 로또 생성과 MongoDB 저장 비용 확인 |
| `POST /api/lottos/{purchaseId}/results` | 구매 로또 수에 따른 등수 계산과 MongoDB 조회 비용 확인 |

## 측정 환경 템플릿

| 항목 | 값 |
| :--- | :--- |
| 실행 일시 | `[확인 필요]` |
| Branch/commit | `[확인 필요]` |
| Java version | `[확인 필요]` |
| Spring profile | `[확인 필요]` |
| MongoDB 환경 | `[확인 필요]` |
| 서버 사양 | `[확인 필요]` |
| 측정 도구 | `[확인 필요]` |

## before/after 표 템플릿

| API | 조건 | before | after | 차이 | 근거 |
| :--- | :--- | :--- | :--- | :--- | :--- |
| `POST /api/calculator/add` | `[확인 필요]` | `[미측정]` | `[미측정]` | `[미측정]` | `[확인 필요]` |
| `POST /api/racingcar/play` | `[확인 필요]` | `[미측정]` | `[미측정]` | `[미측정]` | `[확인 필요]` |
| `POST /api/lottos` | `[확인 필요]` | `[미측정]` | `[미측정]` | `[미측정]` | `[확인 필요]` |
| `POST /api/lottos/{purchaseId}/results` | `[확인 필요]` | `[미측정]` | `[미측정]` | `[미측정]` | `[확인 필요]` |

## 이력서 사용 기준

- 현재는 성능 수치를 이력서에 쓰면 안 됩니다.
- 성능 문장을 쓰려면 측정 환경, 입력 조건, 원본 로그, 재현 명령이 함께 있어야 합니다.
- 개선률은 같은 환경에서 같은 조건으로 측정한 before/after가 있을 때만 계산합니다.
