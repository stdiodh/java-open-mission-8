# Query Tuning

이번 단계에서는 로또 결과 조회 흐름의 `purchaseId` 조건 조회를 대상으로 쿼리 튜닝 가능성을 확인했습니다. 운영 데이터가 아닌 로컬 합성 데이터 기준이므로, 운영 성능 개선 수치처럼 표현하지 않습니다.

## 대상 쿼리

| 항목 | 내용 |
| :--- | :--- |
| Repository | `LottoRepository.findAllByPurchaseId(String purchaseId)` |
| Service | `LottoServiceImpl.checkWinningResult()` |
| Collection | `lottos` |
| 조건 | `{ purchaseId: targetPurchaseId }` |
| 목적 | 구매 ID에 연결된 로또 목록을 조회해 당첨 결과를 계산 |

## 측정 조건

| 항목 | 값 |
| :--- | :--- |
| DB | `java-open-mission-8-query-benchmark` |
| 문서 수 | 100,000 |
| 대상 purchaseId | `purchase-target` |
| 대상 결과 수 | 1,000 |
| 측정 도구 | `mongosh` |
| 측정 방식 | `db.lottos.find({ purchaseId }).explain("executionStats")` |

## Before/After 결과

| 상태 | index | winning stage | docs examined | keys examined | nReturned | executionTimeMillis |
| :--- | :--- | :--- | ---: | ---: | ---: | ---: |
| before | `_id_` only | `COLLSCAN` | 100,000 | 0 | 1,000 | 30 |
| after | `purchaseId_1` | `FETCH` + `IXSCAN` | 1,000 | 1,000 | 1,000 | 2 |

## 적용 변경

| 파일 | 변경 |
| :--- | :--- |
| `src/main/java/.../lotto/domain/Lotto.java` | `purchaseId` 필드에 `@Indexed(name = "purchaseId_1")` 추가 |
| `src/main/resources/application.yaml` | `spring.data.mongodb.auto-index-creation: true` 추가 |

## 적용 판단

- 결과 조회는 항상 `purchaseId`로 구매 로또 목록을 찾으므로, collection 크기가 커질수록 full scan 위험이 커집니다.
- `purchaseId`는 여러 로또 문서가 같은 값을 가지므로 unique index가 아니라 non-unique index가 맞습니다.
- 인덱스 적용 후 synthetic benchmark에서 scan 범위가 전체 100,000건에서 결과 1,000건 수준으로 줄었습니다.

## 비용과 주의점

- `POST /api/lottos` 구매 시 로또 문서 insert마다 `purchaseId_1` index도 함께 갱신되므로 쓰기 비용과 저장 공간이 증가합니다.
- collection이 큰 운영 환경에서 startup 시 index 생성 또는 확인 비용이 발생할 수 있습니다.
- 이번 수치는 로컬 합성 데이터 기준입니다. 운영 데이터 분포, MongoDB 사양, 동시성 조건에 따라 실제 latency는 달라질 수 있습니다.

## 검증

```text
MONGO_URI=mongodb://localhost:27017/java-open-mission-8-demo SERVER_URL=http://localhost:8080 ./gradlew clean test jacocoTestReport
BUILD SUCCESSFUL in 5s
```

로컬 demo DB index 확인 결과입니다.

```text
_id_
purchaseId_1
```

## 다음 측정 TODO

- 운영과 유사한 구매 건수, 구매당 로또 수, 동시 요청 조건으로 API latency를 측정합니다.
- index 적용 전후 insert latency와 index storage size를 함께 측정합니다.
- `findAllByPurchaseId` 결과 수가 큰 purchaseId와 작은 purchaseId를 나눠 executionStats를 비교합니다.
