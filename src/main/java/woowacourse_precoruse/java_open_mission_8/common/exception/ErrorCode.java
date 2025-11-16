package woowacourse_precoruse.java_open_mission_8.common.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "COMMON_INVALID_INPUT", "입력값이 올바르지 않습니다."),
    ILLEGAL_ARGUMENT_ERROR(HttpStatus.BAD_REQUEST, "COMMON_ILLEGAL_ARGUMENT", "부적절한 인자입니다."),

    CALC_NEGATIVE_NUMBER(HttpStatus.BAD_REQUEST, "CALC_NEGATIVE_NUMBER", "음수는 입력할 수 없습니다."),
    CALC_INVALID_FORMAT(HttpStatus.BAD_REQUEST, "CALC_INVALID_FORMAT", "숫자 이외의 값은 포함될 수 없습니다."),
    CALC_INVALID_CUSTOM_DELIMITER(HttpStatus.BAD_REQUEST, "CALC_INVALID_CUSTOM_DELIMITER", "커스텀 구분자 형식이 올바르지 않습니다."),

    RACE_NAME_TOO_LONG(HttpStatus.BAD_REQUEST, "RACE_NAME_TOO_LONG", "자동차 이름은 5자를 초과할 수 없습니다."),
    RACE_NAME_BLANK(HttpStatus.BAD_REQUEST, "RACE_NAME_BLANK", "자동차 이름은 공백이거나 비어있을 수 없습니다."),
    RACE_NAME_DUPLICATED(HttpStatus.BAD_REQUEST, "RACE_NAME_DUPLICATED", "자동차 이름은 중복될 수 없습니다."),
    RACE_COUNT_INVALID(HttpStatus.BAD_REQUEST, "RACE_COUNT_INVALID", "시도 횟수는 1 이상이어야 합니다."),

    LOTTO_AMOUNT_INVALID_UNIT(HttpStatus.BAD_REQUEST, "LOTTO_AMOUNT_INVALID_UNIT", "구입 금액은 1,000원 단위여야 합니다."),
    LOTTO_AMOUNT_INSUFFICIENT(HttpStatus.BAD_REQUEST, "LOTTO_AMOUNT_INSUFFICIENT", "최소 구입 금액은 1,000원입니다."),
    LOTTO_SIZE_INVALID(HttpStatus.BAD_REQUEST, "LOTTO_SIZE_INVALID", "로또 번호는 6개여야 합니다."),
    LOTTO_NUMBER_OUT_OF_RANGE(HttpStatus.BAD_REQUEST, "LOTTO_NUMBER_OUT_OF_RANGE", "로또 번호는 1에서 45 사이여야 합니다."),
    LOTTO_NUMBER_DUPLICATED(HttpStatus.BAD_REQUEST, "LOTTO_NUMBER_DUPLICATED", "로또 번호 또는 보너스 번호가 중복되었습니다."),

    SERVER_INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "SERVER_INTERNAL_ERROR", "서버 내부 오류가 발생했습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;

    ErrorCode(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    // --- 리팩토링된 Getter ---
    public HttpStatus getStatus() {
        return status;
    }

    public int getStatusValue() {
        return status.value();
    }

    public String getErrorReasonPhrase() {
        return status.getReasonPhrase();
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
