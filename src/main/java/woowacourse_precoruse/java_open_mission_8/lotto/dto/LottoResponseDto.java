package woowacourse_precoruse.java_open_mission_8.lotto.dto;

import java.util.List;
import woowacourse_precoruse.java_open_mission_8.lotto.domain.Lotto;
import woowacourse_precoruse.java_open_mission_8.lotto.domain.LottoNumber;

public class LottoResponseDto {
    private final List<Integer> numbers;

    public LottoResponseDto(List<Integer> numbers) {
        this.numbers = numbers;
    }

    public static LottoResponseDto from(Lotto lotto) {
        List<Integer> lottoNumbers = lotto.getNumbers().stream()
                .map(LottoResponseDto::mapLottoNumberValue) // 1-dot-rule 준수
                .toList();
        return new LottoResponseDto(lottoNumbers);
    }

    private static Integer mapLottoNumberValue(LottoNumber number) {
        return number.value();
    }

    public List<Integer> getNumbers() {
        return numbers;
    }
}