# API Flows

본 프로젝트의 핵심 기능별 API 흐름입니다. 엔드포인트와 요청/응답 예시는 controller, DTO, service, domain 코드 기준으로 작성했습니다.

| 기능 | 문서 | Endpoint |
| :--- | :--- | :--- |
| 문자열 덧셈 계산기 | [calculator-add.md](./calculator-add.md) | `POST /api/calculator/add` |
| 자동차 경주 | [racingcar-play.md](./racingcar-play.md) | `POST /api/racingcar/play` |
| 로또 구매/결과 | [lotto-purchase-result.md](./lotto-purchase-result.md) | `POST /api/lottos`, `POST /api/lottos/{purchaseId}/results` |

## 공통 오류 응답

```json
{
  "status": 400,
  "error": "Bad Request",
  "code": "COMMON_INVALID_INPUT",
  "message": "[ERROR] 입력값이 올바르지 않습니다."
}
```

상세 정책은 [API Error Policy](../api-error-policy.md)를 기준으로 봅니다.
