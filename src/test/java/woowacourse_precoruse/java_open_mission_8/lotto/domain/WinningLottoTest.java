package woowacourse_precoruse.java_open_mission_8.lotto.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;
import java.util.stream.Stream;
import woowacourse_precoruse.java_open_mission_8.common.exception.BusinessLogicException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class WinningLottoTest {
    private Lotto createLotto(List<Integer> numbers) {
        List<LottoNumber> lottoNumbers = numbers.stream()
                .map(LottoNumber::new)
                .toList();
        return new Lotto(lottoNumbers, null);
    }

    @Test
    void 유효한_당첨번호와_보너스번호로_객체를_생성한다() {
        Lotto winning = createLotto(List.of(1, 2, 3, 4, 5, 6));
        LottoNumber bonus = new LottoNumber(7);

        WinningLotto winningLotto = new WinningLotto(winning, bonus);

        assertThat(winningLotto.getWinningNumbers()).isEqualTo(winning);
        assertThat(winningLotto.getBonusNumber()).isEqualTo(bonus);
    }

    @Test
    void 보너스번호가_당첨번호와_중복되면_예외가_발생한다() {
        Lotto winning = createLotto(List.of(1, 2, 3, 4, 5, 6));
        LottoNumber bonus = new LottoNumber(6); // 6 중복

        assertThatThrownBy(() -> new WinningLotto(winning, bonus))
                .isInstanceOf(BusinessLogicException.class)
                .hasMessageContaining("[ERROR] 로또 번호 또는 보너스 번호가 중복되었습니다.");
    }

    @ParameterizedTest
    @CsvSource({
            "'1, 2, 3, 4, 5, 6', FIRST",
            "'1, 2, 3, 4, 5, 7', SECOND",
            "'1, 2, 3, 4, 5, 8', THIRD",
            "'1, 2, 3, 4, 8, 9', FOURTH",
            "'1, 2, 3, 8, 9, 10', FIFTH",
            "'1, 2, 8, 9, 10, 11', NOTHING",
            "'8, 9, 10, 11, 12, 13', NOTHING"
    })
    void 구매한_로또와_비교하여_정확한_등수를_반환한다(String userNumbersCsv, Rank expectedRank) {
        Lotto winning = createLotto(List.of(1, 2, 3, 4, 5, 6));
        LottoNumber bonus = new LottoNumber(7);
        WinningLotto winningLotto = new WinningLotto(winning, bonus);

        List<Integer> userInts = Stream.of(userNumbersCsv.split(","))
                .map(String::trim)
                .map(Integer::parseInt)
                .toList();
        Lotto userLotto = createLotto(userInts);

        Rank result = winningLotto.match(userLotto);

        assertThat(result).isEqualTo(expectedRank);
    }

    @Test
    void 로또가_특정_번호를_포함하는지_확인한다() {
        Lotto lotto = createLotto(List.of(1, 2, 3, 4, 5, 6));

        assertThat(lotto.contains(new LottoNumber(3))).isTrue();
        assertThat(lotto.contains(new LottoNumber(7))).isFalse();
    }

    @Test
    void 다른_로또와_일치하는_번호의_개수를_반환한다() {
        Lotto lotto1 = createLotto(List.of(1, 2, 3, 4, 5, 6));
        Lotto lotto2 = createLotto(List.of(1, 2, 3, 7, 8, 9)); // 3개 일치

        int matchCount = lotto1.countMatchingNumbers(lotto2);

        assertThat(matchCount).isEqualTo(3);
    }
}
