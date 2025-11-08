package woowacourse_precoruse.java_open_mission_8.calculator.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.assertj.core.api.Assertions.assertThat;

public class CalculatorTest {

    @ParameterizedTest
    @NullAndEmptySource
    void 입력값이_null이거나_빈_문자열이면_0을_반환한다(String input) {
        Calculator calculator = new Calculator();
        assertThat(calculator.calculate(input)).isZero();
    }

    @Test
    void 쉼표나_콜론을_구분자로_숫자를_더한다() {
        Calculator calculator = new Calculator();
        assertThat(calculator.calculate("1,2:3")).isEqualTo(6);
    }
}
