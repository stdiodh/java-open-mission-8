package woowacourse_precoruse.java_open_mission_8.calculator.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import woowacourse_precoruse.java_open_mission_8.common.exception.BusinessLogicException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ParserTest {
    private final Parser parser = new Parser();

    @Test
    void 기본_구분자로_분리한다() {
        assertThat(parser.parse("1,2:3"))
                .containsExactly("1", "2", "3");
    }

    @Test
    void 커스텀_구분자로_분리한다() {
        assertThat(parser.parse("//;\n1;2;3"))
                .containsExactly("1", "2", "3");
    }

    @ParameterizedTest
    @NullAndEmptySource
    void 입력값이_null이거나_빈문자열이면_빈_리스트를_반환한다(String input) {
        assertThat(parser.parse(input)).isEmpty();
    }

    @Test
    @DisplayName("커스텀 구분자가 비어있으면 예외가 발생한다")
    void 커스텀_구분자가_비어있으면_예외_발생() {
        String invalidInput = "//\n1,2,3";

        assertThatThrownBy(() -> parser.parse(invalidInput))
                .isInstanceOf(BusinessLogicException.class);
    }
}
