package woowacourse_precoruse.java_open_mission_8.lotto.domain;

import java.util.Arrays;

public enum Rank {
    FIRST(6, 2_000_000_000L),
    SECOND(5, 30_000_000L),
    THIRD(5, 1_500_000L),
    FOURTH(4, 50_000L),
    FIFTH(3, 5_000L),
    NOTHING(0, 0L); // MISS -> NOTHING 으로 변경

    private final int matchCount;
    private final long prizeMoney;

    Rank(int matchCount, long prizeMoney) {
        this.matchCount = matchCount;
        this.prizeMoney = prizeMoney;
    }

    public static Rank valueOfRank(int matchCount, boolean bonusMatch) {
        if (matchCount == 6) {
            return FIRST;
        }
        if (matchCount == 5 && bonusMatch) {
            return SECOND;
        }
        if (matchCount == 5) {
            return THIRD;
        }

        return Arrays.stream(values())
                .filter(rank -> rank.matchCount == matchCount)
                .findFirst()
                .orElse(NOTHING);
    }

    public int getMatchCount() {
        return matchCount;
    }

    public long getPrizeMoney() {
        return prizeMoney;
    }

    public long calculateTotalPrize(int count) {
        return this.prizeMoney * count;
    }
}
