package woowacourse_precoruse.java_open_mission_8.racingcar.domain;

import woowacourse_precoruse.java_open_mission_8.common.exception.BusinessLogicException;
import woowacourse_precoruse.java_open_mission_8.common.exception.ErrorCode;

public record TryCount(int value) {
    private static final int MIN_TRY_COUNT = 1;

    public TryCount {
        validateRange(value);
    }

    private void validateRange(int number) {
        if (number < MIN_TRY_COUNT) {
            throw new BusinessLogicException(ErrorCode.RACE_COUNT_INVALID);
        }
    }
}
