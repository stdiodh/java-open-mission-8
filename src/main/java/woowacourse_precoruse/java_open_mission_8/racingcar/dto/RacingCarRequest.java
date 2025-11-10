package woowacourse_precoruse.java_open_mission_8.racingcar.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record RacingCarRequest(
        @NotBlank(message = "[ERROR] 자동차 이름은 필수입니다.")
        String names,
        @Min(value = 1, message = "[ERROR] 시도 횟수는 1회 이상이어야 합니다.")
        int count
) {
}
