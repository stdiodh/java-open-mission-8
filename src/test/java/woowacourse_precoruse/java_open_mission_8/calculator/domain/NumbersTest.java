package woowacourse_precoruse.java_open_mission_8.calculator.domain;

import java.util.List;
import org.junit.jupiter.api.Test;
import woowacourse_precoruse.java_open_mission_8.common.exception.BusinessLogicException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class NumbersTest {
    @Test
    void 문자열_리스트를_정수_리스트로_변환한다() {
        Numbers numbers = new Numbers(List.of("1", "2", "3"));
        assertThat(numbers.sum()).isEqualTo(6);
    }

    @Test
    void 음수가_포함되면_예외가_발생한다() {
        assertThatThrownBy(() -> new Numbers(List.of("1", "-2", "3")))
                .isInstanceOf(BusinessLogicException.class)
                .hasMessageContaining("음수");
    }

    @Test
    void 숫자가_아닌_값이_있으면_예외가_발생한다() {
        assertThatThrownBy(() -> new Numbers(List.of("1", "a", "3")))
                .isInstanceOf(BusinessLogicException.class)
                .hasMessageContaining("숫자 이외의 값은 포함");
    }
}
