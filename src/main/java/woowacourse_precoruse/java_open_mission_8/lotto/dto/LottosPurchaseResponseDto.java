package woowacourse_precoruse.java_open_mission_8.lotto.dto;

import java.util.List;
import woowacourse_precoruse.java_open_mission_8.lotto.domain.Lotto;

public class LottosPurchaseResponseDto {

    private final String purchaseId;
    private final List<LottoResponseDto> lottos;
    private final int purchaseCount;

    public LottosPurchaseResponseDto(String purchaseId, List<LottoResponseDto> lottos) {
        this.purchaseId = purchaseId;
        this.lottos = lottos;
        this.purchaseCount = lottos.size();
    }

    public static LottosPurchaseResponseDto from(List<Lotto> purchasedLottos, String purchaseId) {
        List<LottoResponseDto> lottoResponses = purchasedLottos.stream()
                .map(LottoResponseDto::from)
                .toList();

        return new LottosPurchaseResponseDto(purchaseId, lottoResponses);
    }
}
