package woowacourse_precoruse.java_open_mission_8.lotto.domain;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class RankTest {
    @ParameterizedTest
    @CsvSource({
            "6, false, FIRST",      // 1등
            "5, true,  SECOND",     // 2등
            "5, false, THIRD",      // 3등
            "4, false, FOURTH",     // 4등
            "3, false, FIFTH",      // 5등
            "2, false, NOTHING",    // 낙첨 (3개 미만)
            "2, true,  NOTHING",    // 낙첨 (3개 미만 + 보너스)
            "1, false, NOTHING",    // 낙첨
            "0, false, NOTHING"     // 낙첨
    })
    void 일치_개수와_보너스볼_유무로_정확한_등수를_반환한다(int matchCount, boolean bonusMatch, Rank expectedRank) {
        Rank result = Rank.valueOfRank(matchCount, bonusMatch);

        assertThat(result).isEqualTo(expectedRank);
    }
}
