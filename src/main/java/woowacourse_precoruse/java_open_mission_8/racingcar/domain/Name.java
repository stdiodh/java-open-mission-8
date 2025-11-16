package woowacourse_precoruse.java_open_mission_8.racingcar.domain;

import woowacourse_precoruse.java_open_mission_8.common.exception.BusinessLogicException;
import woowacourse_precoruse.java_open_mission_8.common.exception.ErrorCode;

public record Name(String value) {
    private static final int MAX_NAME_LENGTH = 5;

    public Name {
        validateNullValue(value);
        validateOverLength(value);
    }

    private void validateNullValue(String value) {
        if (value == null || value.isBlank()) {
            throw new BusinessLogicException(ErrorCode.RACE_NAME_BLANK);
        }
    }

    private void validateOverLength(String value) {
        if (value.length() > MAX_NAME_LENGTH) {
            throw new BusinessLogicException(ErrorCode.RACE_NAME_TOO_LONG);
        }
    }
}
