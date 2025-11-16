package woowacourse_precoruse.java_open_mission_8.calculator.domain;

import java.util.ArrayList;
import java.util.List;
import woowacourse_precoruse.java_open_mission_8.common.exception.BusinessLogicException;
import woowacourse_precoruse.java_open_mission_8.common.exception.ErrorCode;

public class Numbers {
    private final List<Integer> values;

    public Numbers(List<String> textTokens) {
        this.values = toInts(textTokens);
        validateNoNegative();
    }

    private List<Integer> toInts(List<String> textTokens) {
        List<Integer> numbers = new ArrayList<>();
        try {
            for (String token : textTokens) {
                numbers.add(Integer.parseInt(token));
            }
        } catch (NumberFormatException e) {
            throw new BusinessLogicException(ErrorCode.CALC_INVALID_FORMAT);
        }
        return numbers;
    }

    private void validateNoNegative() {
        for (int number : values) {
            if (number < 0) {
                throw new BusinessLogicException(ErrorCode.CALC_NEGATIVE_NUMBER);
            }
        }
    }

    public int sum() {
        return values.stream().mapToInt(Integer::intValue).sum();
    }
}
