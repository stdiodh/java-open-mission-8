package woowacourse_precoruse.java_open_mission_8.lotto.dto;

import java.util.List;
import woowacourse_precoruse.java_open_mission_8.lotto.domain.Lotto;

public class LottosPurchaseResponseDto {
    private final List<LottoResponseDto> lottos;
    private final int purchaseCount;

    public LottosPurchaseResponseDto(List<LottoResponseDto> lottos) {
        this.lottos = lottos;
        this.purchaseCount = lottos.size();
    }

    public static LottosPurchaseResponseDto from(List<Lotto> purchasedLottos) {
        List<LottoResponseDto> lottoResponses = purchasedLottos.stream()
                .map(LottoResponseDto::from)
                .toList();
        return new LottosPurchaseResponseDto(lottoResponses);
    }

    public List<LottoResponseDto> getLottos() {
        return lottos;
    }
}
