package woowacourse_precoruse.java_open_mission_8.lotto.domain;

import woowacourse_precoruse.java_open_mission_8.common.exception.BusinessLogicException;
import woowacourse_precoruse.java_open_mission_8.common.exception.ErrorCode;

public record LottoNumber(int value) implements Comparable<LottoNumber> {
    private static final int MIN_NUMBER = 1;
    private static final int MAX_NUMBER = 45;

    public LottoNumber {
        if (value < MIN_NUMBER || value > MAX_NUMBER) {
            throw new BusinessLogicException(ErrorCode.LOTTO_NUMBER_OUT_OF_RANGE);
        }
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public int compareTo(LottoNumber other) {
        return Integer.compare(this.value, other.value);
    }
}
