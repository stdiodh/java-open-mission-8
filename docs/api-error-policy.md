# API Error Policy

본 프로젝트는 프론트엔드가 오류를 일관되게 처리할 수 있도록 HTTP status, 표준 error 문구, 애플리케이션 error code, message를 포함한 JSON 오류 응답을 사용합니다.

## 오류 응답 형식

```json
{
  "status": 400,
  "error": "Bad Request",
  "code": "COMMON_INVALID_INPUT",
  "message": "[ERROR] 입력값이 올바르지 않습니다."
}
```

| 필드 | 출처 | 설명 |
| :--- | :--- | :--- |
| `status` | `ErrorCode.getStatusValue()` | HTTP status code 숫자 |
| `error` | `ErrorCode.getErrorReasonPhrase()` | HTTP status reason phrase |
| `code` | `ErrorCode.getCode()` | 클라이언트 분기용 애플리케이션 오류 코드 |
| `message` | `ErrorCode.getMessage()` 또는 validation message | 사용자에게 보여줄 수 있는 오류 메시지 |

## DTO Validation

현재 DTO validation은 `@Valid`가 적용된 controller 요청에서 사용됩니다.

| API | DTO | validation |
| :--- | :--- | :--- |
| `POST /api/racingcar/play` | `RacingCarRequest` | `names`는 `@NotBlank`, `count`는 `@Min(1)` |
| `POST /api/lottos` | `LottoPurchaseRequestDto` | `amount`는 `@Min(1000)` |
| `POST /api/lottos/{purchaseId}/results` | `WinningLottoRequestDto` | `winningNumbers`는 `@NotNull`, `@Size(6)`, 각 값 `1..45`, `bonusNumber`는 `1..45` |
| `POST /api/calculator/add` | `CalculatorRequest` | 현재 DTO annotation은 없고 domain parsing 단계에서 검증합니다. |

`GlobalExceptionHandler`는 `MethodArgumentNotValidException` 발생 시 첫 번째 validation message를 사용하고, error code는 `COMMON_INVALID_INPUT`으로 고정합니다.

## Custom Exception

도메인 규칙 위반은 `BusinessLogicException`이 `ErrorCode`를 담아 던지는 방식으로 처리합니다.

| 영역 | 대표 error code |
| :--- | :--- |
| 계산기 | `CALC_NEGATIVE_NUMBER`, `CALC_INVALID_FORMAT`, `CALC_INVALID_CUSTOM_DELIMITER`, `CALC_NULL_INPUT` |
| 자동차 경주 | `RACE_NAME_TOO_LONG`, `RACE_NAME_BLANK`, `RACE_NAME_DUPLICATED`, `RACE_COUNT_INVALID`, `RACE_CAR_COUNT_INSUFFICIENT` |
| 로또 | `LOTTO_AMOUNT_INVALID_UNIT`, `LOTTO_AMOUNT_INSUFFICIENT`, `LOTTO_AMOUNT_EXCEEDS_LIMIT`, `LOTTO_SIZE_INVALID`, `LOTTO_NUMBER_OUT_OF_RANGE`, `LOTTO_NUMBER_DUPLICATED` |
| 공통 | `COMMON_INVALID_INPUT`, `COMMON_ILLEGAL_ARGUMENT`, `SERVER_INTERNAL_ERROR` |

## RestControllerAdvice

`GlobalExceptionHandler`는 다음 예외를 처리합니다.

| handler | 응답 정책 |
| :--- | :--- |
| `MethodArgumentNotValidException` | `COMMON_INVALID_INPUT`과 첫 번째 validation message 반환 |
| `WebExchangeBindException` | WebFlux DTO validation 실패를 `COMMON_INVALID_INPUT`으로 반환 |
| `BusinessLogicException` | exception에 담긴 `ErrorCode` 기준으로 status/code/message 반환 |
| `Exception` | 로그 기록 후 `SERVER_INTERNAL_ERROR` 반환 |

## 설계 판단

- 클라이언트가 message 문자열 대신 `code`를 기준으로 오류 UI를 분기할 수 있도록 error code를 분리했습니다.
- validation 실패와 domain 규칙 실패를 같은 응답 구조로 반환해 프론트엔드 처리 방식을 단순화했습니다.
- domain 내부에서는 HTTP를 직접 알지 않고 `BusinessLogicException`과 `ErrorCode`만 사용합니다.

## 현재 제한사항

- `LottoServiceImpl.checkWinningResult()`에서 존재하지 않는 `purchaseId`는 `IllegalArgumentException`을 던집니다. 현재 handler에는 `IllegalArgumentException` 전용 처리가 없어 generic `Exception` handler로 500 응답이 될 수 있습니다.
- `LottoController` Swagger annotation에는 존재하지 않는 구매 ID에 대해 404 설명이 있지만, 현재 코드의 예외 처리 정책만 보면 404 매핑은 확인되지 않습니다.
- WebFlux DTO validation 실패는 `WebExchangeBindException` handler로 400 응답을 반환하도록 보완했습니다.
