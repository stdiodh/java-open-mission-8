package woowacourse_precoruse.java_open_mission_8.lotto.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.EnumMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class LottoResultTest {
    private Map<Rank, Integer> resultMap;
    private LottoResult lottoResult;

    @BeforeEach
    void setUp() {
        resultMap = new EnumMap<>(Rank.class);
    }

    @Test
    void 특정_등수의_당첨_횟수를_정확히_반환한다() {
        resultMap.put(Rank.FIFTH, 1);
        lottoResult = new LottoResult(resultMap);

        assertThat(lottoResult.getCount(Rank.FIFTH)).isEqualTo(1);
        assertThat(lottoResult.getCount(Rank.FOURTH)).isEqualTo(0);
    }

    @Test
    void 총_당첨금을_정확히_계산한다() {
        resultMap.put(Rank.FIFTH, 1);
        resultMap.put(Rank.FOURTH, 1);
        lottoResult = new LottoResult(resultMap);

        long totalPrize = lottoResult.getTotalPrize();

        assertThat(totalPrize).isEqualTo(55_000L);
    }

    @Test
    void 당첨_결과가_없으면_총_당첨금은_0원이다() {
        lottoResult = new LottoResult(resultMap);

        long totalPrize = lottoResult.getTotalPrize();

        assertThat(totalPrize).isEqualTo(0L);
    }

    @Test
    void 수익률_계산을_PurchaseAmount에_정확히_위임한다() {
        resultMap.put(Rank.FIFTH, 1);
        lottoResult = new LottoResult(resultMap);
        PurchaseAmount purchaseAmount = new PurchaseAmount(10_000);

        double profitRate = lottoResult.calculateProfitRate(purchaseAmount);

        assertThat(profitRate).isEqualTo(0.5);
    }
}
