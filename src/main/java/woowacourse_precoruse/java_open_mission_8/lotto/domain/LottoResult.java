package woowacourse_precoruse.java_open_mission_8.lotto.domain;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

public class LottoResult {
    private final Map<Rank, Integer> resultCounts;

    public LottoResult(Map<Rank, Integer> resultCounts) {
        this.resultCounts = Collections.unmodifiableMap(new EnumMap<>(resultCounts));
    }

    public int getCount(Rank rank) {
        return resultCounts.getOrDefault(rank, 0);
    }

    public long getTotalPrize() {
        return resultCounts.entrySet().stream()
                .mapToLong(this::calculatePrizeForEntry)
                .sum();
    }

    private long calculatePrizeForEntry(Map.Entry<Rank, Integer> entry) {
        Rank rank = entry.getKey();
        int count = entry.getValue();

        return rank.calculateTotalPrize(count);
    }

    public Map<Rank, Integer> getResultCounts() {
        return this.resultCounts;
    }

    public double calculateProfitRate(PurchaseAmount purchaseAmount) {
        long totalPrize = this.getTotalPrize(); // 1 dot

        return purchaseAmount.calculateProfitRate(totalPrize);
    }
}
