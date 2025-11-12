package woowacourse_precoruse.java_open_mission_8.lotto.domain;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class LottoTest {
    private List<LottoNumber> createLottoNumbers(List<Integer> numbers) {
        return numbers.stream()
                .map(LottoNumber::new)
                .collect(Collectors.toList());
    }

    @Test
    void 로또번호가_6개가_아니면_예외가_발생한다() {
        List<LottoNumber> numbers = createLottoNumbers(List.of(1, 2, 3, 4, 5));

        assertThatThrownBy(() -> new Lotto(numbers, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("로또 번호는 6개여야 합니다.");
    }

    @Test
    void 로또번호에_중복이_있으면_예외가_발생한다() {
        List<LottoNumber> numbers = createLottoNumbers(List.of(1, 2, 3, 4, 5, 5));

        assertThatThrownBy(() -> new Lotto(numbers, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("로또 번호는 중복될 수 없습니다.");
    }

    @Test
    void 유효한_번호로_로또를_생성하며_번호가_정렬된다() {
        List<LottoNumber> numbers = createLottoNumbers(List.of(45, 2, 3, 1, 5, 6));
        List<LottoNumber> sortedNumbers = createLottoNumbers(List.of(1, 2, 3, 5, 6, 45));

        Lotto lotto = new Lotto(numbers, null);

        assertThat(lotto.getNumbers()).isEqualTo(sortedNumbers);
    }
}
