# Java Open Mission API 명세서

기준 컨트롤러:
- `CalculatorController`
- `RacingCarController`
- `LottoController`

## 공통 에러 응답 포맷

```json
{
  "status": 400,
  "error": "Bad Request",
  "code": "COMMON_INVALID_INPUT",
  "message": "[ERROR] 입력값이 올바르지 않습니다."
}
```

## 1. Calculator API (`/api/calculator`)

| Method | URI | 설명 |
|---|---|---|
| POST | `/add` | 문자열 덧셈 계산 |

### Request
| 필드 | 타입 | 필수 | 설명 |
|---|---|---|---|
| `expression` | String | Y | 계산할 문자열 (기본 구분자 `,`, `:` / 커스텀 구분자 지원) |

**요청 예시**
```json
{ "expression": "//;\\n1;2;3" }
```

**성공 응답 (200)**
```json
{ "result": 6 }
```

**대표 에러 코드**
- `CALC_NEGATIVE_NUMBER`
- `CALC_INVALID_FORMAT`
- `CALC_INVALID_CUSTOM_DELIMITER`
- `CALC_NULL_INPUT`

## 2. RacingCar API (`/api/racingcar`)

| Method | URI | 설명 |
|---|---|---|
| POST | `/play` | 자동차 경주 실행 |

### Request
| 필드 | 타입 | 필수 | 제약 |
|---|---|---|---|
| `names` | String | Y | `@NotBlank`, 쉼표 구분 자동차 이름 |
| `count` | int | Y | `@Min(1)` |

**요청 예시**
```json
{ "names": "pobi,woni,jun", "count": 5 }
```

**성공 응답 (200)**
```json
{
  "rounds": [
    [
      { "name": "pobi", "position": 1 },
      { "name": "woni", "position": 0 }
    ]
  ],
  "winners": ["pobi"]
}
```

**대표 에러 코드**
- `RACE_NAME_TOO_LONG`
- `RACE_NAME_BLANK`
- `RACE_NAME_DUPLICATED`
- `RACE_COUNT_INVALID`
- `RACE_CAR_COUNT_INSUFFICIENT`

## 3. Lotto API (`/api/lottos`)

| Method | URI | 설명 |
|---|---|---|
| POST | `` | 로또 구매 |
| POST | `/{purchaseId}/results` | 당첨 결과 계산 |
| DELETE | `/reset` | 저장 데이터 초기화(테스트 용) |

### 3-1) POST `/api/lottos`

#### Request
| 필드 | 타입 | 필수 | 제약 |
|---|---|---|---|
| `amount` | int | Y | `@Min(1000)`, 1000원 단위 |

**요청 예시**
```json
{ "amount": 8000 }
```

**성공 응답 (201)**
```json
{
  "purchaseId": "uuid-value",
  "lottos": [
    { "numbers": [8, 13, 21, 28, 34, 42] }
  ],
  "purchaseCount": 8
}
```

### 3-2) POST `/api/lottos/{purchaseId}/results`

#### Path
| 이름 | 타입 | 필수 |
|---|---|---|
| `purchaseId` | String | Y |

#### Request
| 필드 | 타입 | 필수 | 제약 |
|---|---|---|---|
| `winningNumbers` | List<Integer> | Y | 6개, 각 1~45 |
| `bonusNumber` | int | Y | 1~45 |

**요청 예시**
```json
{
  "winningNumbers": [1,2,3,4,5,6],
  "bonusNumber": 7
}
```

**성공 응답 (200)**
```json
{
  "resultCounts": {
    "FIFTH": 2,
    "FOURTH": 1,
    "THIRD": 0,
    "SECOND": 0,
    "FIRST": 0
  },
  "profitRate": 62.5
}
```

### 3-3) DELETE `/api/lottos/reset`
- 상태 코드: `204 No Content`

## 4. 에러 처리 규칙
- DTO 검증 실패(`MethodArgumentNotValidException`): `INVALID_INPUT_VALUE`
- 비즈니스 예외(`BusinessLogicException`): 해당 `ErrorCode` 반환
- 미처리 예외: `SERVER_INTERNAL_ERROR` (`500`)
