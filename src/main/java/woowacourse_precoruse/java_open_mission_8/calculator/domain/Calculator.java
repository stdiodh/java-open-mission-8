package woowacourse_precoruse.java_open_mission_8.calculator.domain;

import woowacourse_precoruse.java_open_mission_8.common.exception.BusinessLogicException;
import woowacourse_precoruse.java_open_mission_8.common.exception.ErrorCode;

public class Calculator {
    public int calculate(Numbers numbers) {
        if (numbers == null) {
            throw new BusinessLogicException(ErrorCode.CALC_NULL_INPUT);
        }
        return numbers.sum();
    }
}
