package woowacourse_precoruse.java_open_mission_8.calculator.domain;

import org.junit.jupiter.api.Test;
import java.util.List;
import woowacourse_precoruse.java_open_mission_8.common.exception.BusinessLogicException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CalculatorTest {
    private final Calculator calculator = new Calculator();

    @Test
    void Numbers가_null이면_예외를_던진다() {
        assertThatThrownBy(() -> calculator.calculate(null))
                .isInstanceOf(BusinessLogicException.class)
                .hasMessageContaining("null");
    }

    @Test
    void Numbers에_값이_있으면_합계를_반환한다() {
        Numbers numbers = new Numbers(List.of("1", "2", "3"));
        int result = calculator.calculate(numbers);

        assertThat(result).isEqualTo(6);
    }

    @Test
    void Numbers가_빈_리스트이면_0을_반환한다() {
        Numbers numbers = new Numbers(List.of());
        int result = calculator.calculate(numbers);

        assertThat(result).isZero();
    }
}
