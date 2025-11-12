package woowacourse_precoruse.java_open_mission_8.lotto.dto;

import java.util.List;
import woowacourse_precoruse.java_open_mission_8.lotto.domain.Lotto;

public record LottosPurchaseResponseDto(
        String purchaseId,
        List<LottoResponseDto> lottos,
        int purchaseCount
) {
    public LottosPurchaseResponseDto(String purchaseId, List<LottoResponseDto> lottos) {
        this(purchaseId, lottos, lottos.size());
    }

    public static LottosPurchaseResponseDto from(List<Lotto> purchasedLottos, String purchaseId) {
        List<LottoResponseDto> lottoResponses = purchasedLottos.stream()
                .map(LottoResponseDto::from)
                .toList();

        // 2-argument 비-표준 생성자 호출
        return new LottosPurchaseResponseDto(purchaseId, lottoResponses);
    }
}
