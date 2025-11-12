package woowacourse_precoruse.java_open_mission_8.lotto.dto;

import jakarta.validation.constraints.Min;
import woowacourse_precoruse.java_open_mission_8.lotto.domain.PurchaseAmount;

public class LottoPurchaseRequestDto {
    @Min(value = 1000, message = "[ERROR] 최소 구입 금액은 1,000원입니다.")
    private final int amount;

    public LottoPurchaseRequestDto(int amount) {
        this.amount = amount;
    }

    public PurchaseAmount toDomain() {
        return new PurchaseAmount(this.amount);
    }
}
