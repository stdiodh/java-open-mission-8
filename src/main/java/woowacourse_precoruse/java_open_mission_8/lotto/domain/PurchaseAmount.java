package woowacourse_precoruse.java_open_mission_8.lotto.domain;

import woowacourse_precoruse.java_open_mission_8.common.exception.BusinessLogicException;
import woowacourse_precoruse.java_open_mission_8.common.exception.ErrorCode;

public class PurchaseAmount {
    public static final int LOTTO_PRICE = 1_000;

    private static final int MAX_PURCHASE_AMOUNT = 100_000;
    private static final int MIN_PURCHASE_AMOUNT = LOTTO_PRICE;

    private final int amount;

    public PurchaseAmount(int amount) {
        validateAmount(amount);
        this.amount = amount;
    }

    private void validateAmount(int amount) {
        if (amount < MIN_PURCHASE_AMOUNT) {
            throw new BusinessLogicException(ErrorCode.LOTTO_AMOUNT_INSUFFICIENT);
        }
        if (amount > MAX_PURCHASE_AMOUNT) {
            throw new BusinessLogicException(ErrorCode.LOTTO_AMOUNT_EXCEEDS_LIMIT);
        }
        if (amount % LOTTO_PRICE != 0) {
            throw new BusinessLogicException(ErrorCode.LOTTO_AMOUNT_INVALID_UNIT);
        }
    }

    public int calculateLottoCount() {
        return amount / LOTTO_PRICE;
    }

    public int getAmount() {
        return amount;
    }

    public double calculateProfitRate(long totalPrize) {
        return (double) totalPrize / this.amount;
    }
}
