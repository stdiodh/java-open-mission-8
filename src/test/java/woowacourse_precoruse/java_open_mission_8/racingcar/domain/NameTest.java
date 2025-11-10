package woowacourse_precoruse.java_open_mission_8.racingcar.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class NameTest {

    @Test
    void 유효한_이름은_정상적으로_생성됨() {
        assertThatNoException().isThrownBy(() -> new Name("pobi"));
        assertThatNoException().isThrownBy(() -> new Name("a"));
        assertThatNoException().isThrownBy(() -> new Name("javaj"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "  "})
    void 이름이_null이거나_공백이면_예외_발생함(String input) {
        assertThatThrownBy(() -> new Name(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("공백일 수 없음");
    }

    @Test
    void 이름이_5자를_초과하면_예외_발생함() {
        assertThatThrownBy(() -> new Name("woowatech"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("5자를 초과할 수 없음");
    }
}
