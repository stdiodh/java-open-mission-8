package woowacourse_precoruse.java_open_mission_8.lotto.domain;

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
            throw new IllegalArgumentException("[ERROR] 최소 구입 금액은 " + MIN_PURCHASE_AMOUNT + "원입니다.");
        }
        if (amount > MAX_PURCHASE_AMOUNT) {
            throw new IllegalArgumentException("[ERROR] 1인당 최대 구입 금액(" + MAX_PURCHASE_AMOUNT + "원)을 초과했습니다.");
        }
        if (amount % LOTTO_PRICE != 0) {
            throw new IllegalArgumentException("[ERROR] 구입 금액은 " + LOTTO_PRICE + "원 단위여야 합니다.");
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
