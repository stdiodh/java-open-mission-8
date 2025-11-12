package woowacourse_precoruse.java_open_mission_8.lotto.dto;

import java.util.Map;
import woowacourse_precoruse.java_open_mission_8.lotto.domain.LottoResult;
import woowacourse_precoruse.java_open_mission_8.lotto.domain.PurchaseAmount;
import woowacourse_precoruse.java_open_mission_8.lotto.domain.Rank;

public class LottoResultResponseDto {
    private final Map<Rank, Integer> resultCounts;
    private final double profitRate;

    public LottoResultResponseDto(Map<Rank, Integer> resultCounts, double profitRate) {
        this.resultCounts = resultCounts;
        this.profitRate = profitRate;
    }
    public static LottoResultResponseDto from(LottoResult lottoResult, PurchaseAmount purchaseAmount) {
        Map<Rank, Integer> counts = lottoResult.getResultCounts();
        double rate = lottoResult.calculateProfitRate(purchaseAmount);

        return new LottoResultResponseDto(counts, rate);
    }
}
