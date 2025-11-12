package woowacourse_precoruse.java_open_mission_8.lotto.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;
import java.util.stream.Collectors;
import woowacourse_precoruse.java_open_mission_8.lotto.domain.Lotto;
import woowacourse_precoruse.java_open_mission_8.lotto.domain.LottoNumber;
import woowacourse_precoruse.java_open_mission_8.lotto.domain.WinningLotto;

public class WinningLottoRequestDto {
    @NotNull(message = "[ERROR] 당첨 번호는 null일 수 없습니다.")
    @Size(min = 6, max = 6, message = "[ERROR] 당첨 번호는 6개여야 합니다.")
    private final List<@Min(1) @Max(45) Integer> winningNumbers;

    @Min(value = 1, message = "[ERROR] 보너스 번호는 1 이상이어야 합니다.")
    @Max(value = 45, message = "[ERROR] 보너스 번호는 45 이하여야 합니다.")
    private final int bonusNumber;

    public WinningLottoRequestDto(List<Integer> winningNumbers, int bonusNumber) {
        this.winningNumbers = winningNumbers;
        this.bonusNumber = bonusNumber;
    }

    public WinningLotto toDomain() {
        Lotto winningLottoDomain = new Lotto(convertToLottoNumbers(this.winningNumbers));
        LottoNumber bonusLottoNumber = new LottoNumber(this.bonusNumber);

        return new WinningLotto(winningLottoDomain, bonusLottoNumber);
    }

    private List<LottoNumber> convertToLottoNumbers(List<Integer> numbers) {
        return numbers.stream()
                .map(LottoNumber::new)
                .collect(Collectors.toList());
    }
}
