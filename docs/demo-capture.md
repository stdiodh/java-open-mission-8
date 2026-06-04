# Demo Capture

본 프로젝트는 백엔드 API 서버입니다. UI 흐름을 보여주는 GIF보다 Swagger UI screenshot과 API 응답 기록이 현재 기능 증거에 더 적합합니다. 이번 docs 점검에서는 repository에 존재하는 Swagger UI screenshot 파일을 확인했고, GIF는 생성하지 않았습니다.

## 생성된 데모 파일

| 기능 | 파일 | 생성 방식 | 상태 |
| :--- | :--- | :--- | :--- |
| Swagger API 목록 | `docs/assets/images/swagger-ui.png` | 기존 PNG 파일 확인 | 생성 파일 확인 완료 |
| 문자열 덧셈 계산기 | `docs/assets/gifs/calculator-api-demo.gif` | 미생성 | 수동 촬영 기준 기록 |
| 자동차 경주 | `docs/assets/gifs/racingcar-api-demo.gif` | 미생성 | 수동 촬영 기준 기록 |
| 로또 구매/결과 | `docs/assets/gifs/lotto-api-demo.gif` | 미생성 | 수동 촬영 기준 기록 |
| 오류 응답 | `docs/assets/images/error-response-example.png` | 미생성 | 수동 촬영 기준 기록 |

## 자동 생성 시도 결과

| 항목 | 결과 |
| :--- | :--- |
| 로컬 실행 | 이번 점검에서 재실행하지 않음 |
| 브라우저 실행 | 기존 `docs/assets/images/swagger-ui.png` 파일을 확인 |
| GIF 생성 | 미생성 |
| GIF 미생성 사유 | 현재 저장소는 백엔드 API 서버이고 프론트엔드 UI가 포함되어 있지 않습니다. Swagger UI 또는 API client 촬영이 더 직접적인 증거입니다. |
| 이미지 파일 확인 | `docs/assets/images/swagger-ui.png`는 1280x900 PNG이며 Swagger UI의 Lotto, Calculator, RacingCar API 목록을 보여줍니다. |

## 이미지 생성 명령 후보

서버 실행 후 Swagger UI 이미지를 다시 생성할 때 사용할 수 있는 명령입니다.

```bash
/Applications/Google\ Chrome.app/Contents/MacOS/Google\ Chrome \
  --headless=new \
  --disable-gpu \
  --no-first-run \
  --no-default-browser-check \
  --screenshot=docs/assets/images/swagger-ui.png \
  --window-size=1280,900 \
  http://localhost:8080/swagger-ui/index.html
```

## API 응답 촬영 후보

아래 curl 명령은 GIF 또는 screenshot을 수동 촬영할 때 사용할 후보입니다. 이번 점검에서는 실행 결과를 새로 기록하지 않았습니다.

```bash
curl -sS -w '\nHTTP_STATUS:%{http_code}\n' \
  -X POST http://localhost:8080/api/calculator/add \
  -H 'Content-Type: application/json' \
  -d '{"expression":"//;\n1;2;3"}'
```

```bash
curl -sS -w '\nHTTP_STATUS:%{http_code}\n' \
  -X POST http://localhost:8080/api/racingcar/play \
  -H 'Content-Type: application/json' \
  -d '{"names":"pobi,woni,jun","count":3}'
```

로또는 먼저 구매 API에서 `purchaseId`를 받은 뒤 결과 API에 사용합니다.

```bash
curl -sS -w '\nHTTP_STATUS:%{http_code}\n' \
  -X POST http://localhost:8080/api/lottos \
  -H 'Content-Type: application/json' \
  -d '{"amount":3000}'
```

```bash
curl -sS -w '\nHTTP_STATUS:%{http_code}\n' \
  -X POST http://localhost:8080/api/lottos/{purchaseId}/results \
  -H 'Content-Type: application/json' \
  -d '{"winningNumbers":[1,2,3,4,5,6],"bonusNumber":7}'
```

## 수동 촬영 기준

| 기능 | 권장 파일명 | 촬영 범위 | 반드시 보여줄 액션 |
| :--- | :--- | :--- | :--- |
| 문자열 덧셈 계산기 | `docs/assets/gifs/calculator-api-demo.gif` | Swagger UI 또는 API client | `POST /api/calculator/add` 요청과 `{"result": 6}` 응답 |
| 자동차 경주 | `docs/assets/gifs/racingcar-api-demo.gif` | Swagger UI 또는 API client | `POST /api/racingcar/play` 요청, `rounds`, `winners` 응답 |
| 로또 구매/결과 | `docs/assets/gifs/lotto-api-demo.gif` | Swagger UI 또는 API client | `POST /api/lottos`로 받은 `purchaseId`를 결과 API에 사용하는 흐름 |
| 오류 응답 | `docs/assets/images/error-response-example.png` | API client 응답 영역 | `status`, `error`, `code`, `message`가 모두 보이는 400 응답 |

## 수동 실행 전제

1. 유효한 `MONGO_URI`를 `.env` 또는 환경 변수로 설정합니다.
2. `SERVER_URL`은 로컬 촬영이면 `http://localhost:8080`으로 둡니다.
3. MongoDB를 로컬 또는 docker-compose로 실행합니다.
4. API 서버를 실행하고 `/swagger-ui/index.html` 또는 API client로 요청을 보냅니다.
5. 민감정보가 화면에 보이지 않는지 확인한 뒤 촬영합니다.
