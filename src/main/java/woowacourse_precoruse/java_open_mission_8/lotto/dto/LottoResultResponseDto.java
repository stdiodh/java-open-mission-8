package woowacourse_precoruse.java_open_mission_8.lotto.dto;

import java.util.Map;
import woowacourse_precoruse.java_open_mission_8.lotto.domain.LottoResult;
import woowacourse_precoruse.java_open_mission_8.lotto.domain.PurchaseAmount;
import woowacourse_precoruse.java_open_mission_8.lotto.domain.Rank;

public record LottoResultResponseDto(
        Map<Rank, Integer> resultCounts,
        double profitRate
) {
    public static LottoResultResponseDto from(LottoResult lottoResult, PurchaseAmount purchaseAmount) {
        Map<Rank, Integer> counts = lottoResult.getResultCounts();
        double rate = lottoResult.calculateProfitRate(purchaseAmount);

        return new LottoResultResponseDto(counts, rate);
    }
}
