package woowacourse_precoruse.java_open_mission_8.lotto.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PurchaseAmountTest {
    private static final int LOTTO_PRICE = PurchaseAmount.LOTTO_PRICE;

    @Test
    void 유효한_금액으로_구입금액_객체를_생성한다() {
        int validAmount = 5_000;

        PurchaseAmount purchaseAmount = new PurchaseAmount(validAmount);

        assertThat(purchaseAmount.getAmount()).isEqualTo(validAmount);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 500, 999})
    void 최소_구입금액보다_적은_금액으로_생성시_예외가_발생한다(int amount) {
        assertThatThrownBy(() -> new PurchaseAmount(amount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 최소 구입 금액은");
    }

    @Test
    void 최대_구입금액보다_많은_금액으로_생성시_예외가_발생한다() {
        int invalidAmount = 100_001;

        assertThatThrownBy(() -> new PurchaseAmount(invalidAmount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 1인당 최대 구입 금액");
    }

    @ParameterizedTest
    @ValueSource(ints = {1_001, 2_500, 1_999})
    void 구입금액이_로또가격_단위가_아니면_예외가_발생한다(int amount) {
        assertThatThrownBy(() -> new PurchaseAmount(amount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 구입 금액은 " + LOTTO_PRICE + "원 단위여야 합니다.");
    }

    @Test
    void 구입금액으로_구매가능한_로또_개수를_계산한다() {
        PurchaseAmount purchaseAmount = new PurchaseAmount(14_000);

        int lottoCount = purchaseAmount.calculateLottoCount();

        assertThat(lottoCount).isEqualTo(14);
    }
}
