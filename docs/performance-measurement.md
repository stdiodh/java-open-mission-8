# Performance Measurement

본 프로젝트에는 아직 API latency, throughput, p95 같은 end-to-end 성능 측정값이 없습니다. 이번 단계에서는 실제로 측정 가능한 범위인 MongoDB `purchaseId` 조회의 query executionStats만 확인했습니다.

## 현재 상태

| 항목 | 상태 |
| :--- | :--- |
| API latency 측정값 | 없음 |
| API throughput 측정값 | 없음 |
| p95 latency | 없음 |
| 쿼리 executionStats | 있음 |
| 성능 개선률 표현 | 운영 환경 측정이 아니므로 작성 금지 |

## 측정한 쿼리

| 항목 | 값 |
| :--- | :--- |
| 대상 코드 | `LottoRepository.findAllByPurchaseId(String purchaseId)` |
| 호출 흐름 | `POST /api/lottos/{purchaseId}/results` -> `LottoServiceImpl.checkWinningResult()` |
| 측정 DB | `java-open-mission-8-query-benchmark` |
| 측정 collection | `lottos` |
| 문서 수 | 100,000 |
| 대상 `purchaseId` 결과 수 | 1,000 |
| 측정 도구 | `mongosh` `explain("executionStats")` |

## Query Before/After

상세 조건과 인덱스 적용 판단은 [Query Tuning](./query-tuning.md)에 기록합니다.

| 상태 | winning stage | docs examined | keys examined | nReturned | executionTimeMillis |
| :--- | :--- | ---: | ---: | ---: | ---: |
| before index | `COLLSCAN` | 100,000 | 0 | 1,000 | 30 |
| after index | `FETCH` + `IXSCAN` | 1,000 | 1,000 | 1,000 | 2 |

## 적용한 튜닝

- `Lotto.purchaseId`에 non-unique index `purchaseId_1`을 추가했습니다.
- `spring.data.mongodb.auto-index-creation: true`를 설정해 Spring Data MongoDB가 index metadata를 생성할 수 있게 했습니다.
- 로컬 demo DB에서 `db.lottos.getIndexes()`로 `_id_`, `purchaseId_1` 인덱스 존재를 확인했습니다.

## 아직 측정하지 않은 항목

| API | 미측정 사유 | 다음 측정 절차 |
| :--- | :--- | :--- |
| `POST /api/calculator/add` | API 서버 부하 테스트 미수행 | 입력 길이별 요청을 고정하고 `wrk` 또는 `k6`로 p95 측정 |
| `POST /api/racingcar/play` | API 서버 부하 테스트 미수행 | 자동차 수와 시도 횟수를 고정하고 p95/throughput 측정 |
| `POST /api/lottos` | MongoDB 쓰기 부하 테스트 미수행 | 인덱스 적용 전후 insert latency와 index storage 증가량 측정 |
| `POST /api/lottos/{purchaseId}/results` | API 계층 포함 end-to-end 측정 미수행 | purchaseId별 로또 수를 고정하고 API latency와 MongoDB executionStats를 함께 수집 |

## 이력서 사용 기준

- 현재 바로 쓸 수 있는 것은 “합성 데이터 기준 MongoDB executionStats로 `purchaseId` 조회 인덱스 효과를 확인했다”는 수준입니다.
- 구체적인 응답 속도 개선률, p95 달성 수치, 처리량 개선 수치는 API 부하 테스트가 없으므로 쓰면 안 됩니다.
