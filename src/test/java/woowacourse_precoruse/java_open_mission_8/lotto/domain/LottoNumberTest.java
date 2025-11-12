package woowacourse_precoruse.java_open_mission_8.lotto.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class LottoNumberTest {
    @Test
    @DisplayName("1~45 범위의 숫자로 정상적으로 생성된다")
    void 유효한_범위의_숫자로_생성된다() {
        LottoNumber number = new LottoNumber(1);

        assertThat(number.value()).isEqualTo(1);
    }

    @ParameterizedTest(name = "입력값: {0}")
    @ValueSource(ints = {0, 46, -1, 100})
    @DisplayName("1~45 범위를 벗어나면 예외가 발생한다")
    void 범위를_벗어나면_예외가_발생한다(int invalidInput) {
        assertThatThrownBy(() -> new LottoNumber(invalidInput))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("1부터 45 사이");
    }

    @Test
    @DisplayName("값이 같은 LottoNumber 객체는 동등하게(equals) 취급된다")
    void 값이_같으면_동일한_객체이다() {
        LottoNumber number1 = new LottoNumber(7);
        LottoNumber number2 = new LottoNumber(7);

        assertThat(number1).isEqualTo(number2);
        assertThat(number1.hashCode()).isEqualTo(number2.hashCode());
    }

    @Test
    @DisplayName("toString 호출 시 숫자 문자열을 반환한다")
    void toString_은_숫자_문자열을_반환한다() {
        LottoNumber number = new LottoNumber(45);

        // record 기본 toString()은 "LottoNumber[value=45]" 형태이지만,
        // 우리가 재정의했다면 "45"가 나와야 함.
        assertThat(number.toString()).isEqualTo("45");
    }

    @Test
    @DisplayName("compareTo로 대소 비교가 가능하다")
    void compareTo_로_대소_비교를_한다() {
        LottoNumber smaller = new LottoNumber(10);
        LottoNumber larger = new LottoNumber(20);

        assertThat(smaller.compareTo(larger)).isNegative(); // 음수 (smaller < larger)
        assertThat(larger.compareTo(smaller)).isPositive(); // 양수 (larger > smaller)
    }
}
