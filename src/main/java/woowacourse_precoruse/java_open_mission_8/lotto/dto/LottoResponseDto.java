package woowacourse_precoruse.java_open_mission_8.lotto.dto;

import java.util.List;
import woowacourse_precoruse.java_open_mission_8.lotto.domain.Lotto;

public record LottoResponseDto(
        List<Integer> numbers
) {
    public static LottoResponseDto from(Lotto lotto) {
        List<Integer> lottoNumbers = lotto.getNumbers().stream()
                .map(LottoResponseDto::mapLottoNumberValue)
                .toList();
        return new LottoResponseDto(lottoNumbers);
    }

    private static Integer mapLottoNumberValue(LottoNumber number) {
        return number.value();
    }
}
