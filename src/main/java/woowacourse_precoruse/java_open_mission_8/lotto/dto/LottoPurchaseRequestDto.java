package woowacourse_precoruse.java_open_mission_8.lotto.dto;

import jakarta.validation.constraints.Min;
import woowacourse_precoruse.java_open_mission_8.lotto.domain.PurchaseAmount;

public record LottoPurchaseRequestDto(
        @Min(value = 1000, message = "[ERROR] 최소 구입 금액은 1,000원입니다.")
        int amount
) {
    public PurchaseAmount toDomain() {
        return new PurchaseAmount(this.amount);
    }
}
