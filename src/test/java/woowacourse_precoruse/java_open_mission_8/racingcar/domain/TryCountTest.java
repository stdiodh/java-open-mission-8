package woowacourse_precoruse.java_open_mission_8.racingcar.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import woowacourse_precoruse.java_open_mission_8.common.exception.BusinessLogicException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class TryCountTest {
    @Test
    void 유효한_시도_횟수일_때_정상적으로_생성한다() {
        assertThatNoException().isThrownBy(() -> new TryCount(1));
        assertThatNoException().isThrownBy(() -> new TryCount(5));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, -1, -1000})
    void 시도_횟수가_1_이상이지_않으면_예외를_발생한다(Integer input) {
        assertThatThrownBy(() -> new TryCount(input))
                .isInstanceOf(BusinessLogicException.class)
                .hasMessageContaining("1 이상");
    }

    @Test
    void value_메서드로_정확한_값을_반환한다() {
        int expectedValue = 10;
        TryCount tryCount = new TryCount(expectedValue);

        assertThat(tryCount.value()).isEqualTo(expectedValue);
    }
}
