package woowacourse_precoruse.java_open_mission_8.calculator.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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

    @Test
    void 커스텀_구분자를_사용하여_숫자를_더한다() {
        Calculator calculator = new Calculator();
        assertThat(calculator.calculate("//;\n1;2;3")).isEqualTo(6);
    }

    @Test
    void 음수를_입력하면_예외가_발생한다() {
        Calculator calculator = new Calculator();
        assertThatThrownBy(() -> calculator.calculate("-1,2,3"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("음수");
    }

    @Test
    void 숫자_이외의_값을_입력하면_예외가_발생한다() {
        Calculator calculator = new Calculator();
        assertThatThrownBy(() -> calculator.calculate("1,a,3"))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
