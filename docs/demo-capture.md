# Demo Capture

본 프로젝트는 백엔드 API 서버입니다. UI 흐름을 보여주는 GIF보다 Swagger UI screenshot과 curl 응답 기록이 현재 기능 증거에 더 적합합니다. `2026-06-04` 로컬 서버 실행 후 Swagger UI 이미지 생성을 시도하고, 핵심 API의 curl 응답을 확인했습니다.

## 자동 생성 시도 결과

| 항목 | 결과 |
| :--- | :--- |
| 로컬 실행 | `MONGO_URI=mongodb://localhost:27017/java-open-mission-8-demo SERVER_URL=http://localhost:8080 ./gradlew bootRun` 성공 |
| 브라우저 실행 | Chrome headless로 `/swagger-ui/index.html` screenshot 생성 시도 |
| GIF 생성 | 미생성 |
| GIF 미생성 사유 | 현재 저장소는 백엔드 API 서버이고 프론트엔드 UI가 포함되어 있지 않습니다. API 목록과 curl 응답이 증거로 더 직접적입니다. |
| Playwright 사용 | PATH에서 `playwright` 명령을 찾지 못해 Chrome headless로 대체 |

## curl 검증 결과

문자열 덧셈 계산기 성공 응답입니다.

```bash
curl -sS -w '\nHTTP_STATUS:%{http_code}\n' \
  -X POST http://localhost:8080/api/calculator/add \
  -H 'Content-Type: application/json' \
  -d '{"expression":"//;\n1;2;3"}'
```

```json
{"result":6}
HTTP_STATUS:200
```

자동차 경주 성공 응답입니다. 위치 값은 랜덤 이동 조건 때문에 실행마다 달라질 수 있습니다.

```json
{"rounds":[[{"name":"pobi","position":1},{"name":"woni","position":0},{"name":"jun","position":1}],[{"name":"pobi","position":2},{"name":"woni","position":1},{"name":"jun","position":2}],[{"name":"pobi","position":3},{"name":"woni","position":2},{"name":"jun","position":3}]],"winners":["pobi","jun"]}
HTTP_STATUS:200
```

계산기 음수 입력 오류 응답입니다.

```json
{"status":400,"error":"Bad Request","code":"CALC_NEGATIVE_NUMBER","message":"[ERROR] 음수는 입력할 수 없습니다."}
HTTP_STATUS:400
```

로또 구매와 결과 확인 응답입니다. 로컬 데모 DB 이름은 `java-open-mission-8-demo`입니다.

```json
{"purchaseId":"aa51610c-9924-402c-b79f-fd2354f81348","lottos":[{"numbers":[10,17,26,28,33,45]},{"numbers":[6,9,15,29,31,41]},{"numbers":[6,10,12,18,27,30]}],"purchaseCount":3}
HTTP_STATUS:201
```

```json
{"resultCounts":{"NOTHING":3},"profitRate":0.0}
HTTP_STATUS:200
```

## 실패 기록

| 항목 | 결과 |
| :--- | :--- |
| 인증 정보가 포함된 placeholder MongoDB URI로 로또 구매 | `500 SERVER_INTERNAL_ERROR` |
| 원인 | 로컬 MongoDB가 열려 있었지만 placeholder 인증 정보로 쓰기 작업을 수행할 때 `Authentication failed`가 발생했습니다. |
| 조치 | 인증 정보가 없는 로컬 데모 URI `mongodb://localhost:27017/java-open-mission-8-demo`로 재실행해 로또 구매/결과 조회를 확인했습니다. |

## 수동 실행 전제

1. 유효한 `MONGO_URI`를 `.env` 또는 환경 변수로 설정합니다.
2. `SERVER_URL`은 로컬 촬영이면 `http://localhost:8080`으로 둡니다.
3. MongoDB를 로컬 또는 docker-compose로 실행합니다.
4. API 서버를 실행하고 `/swagger-ui/index.html` 또는 API client로 요청을 보냅니다.
5. 민감정보가 화면에 보이지 않는지 확인한 뒤 촬영합니다.
