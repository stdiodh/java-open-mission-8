package woowacourse_precoruse.java_open_mission_8.lotto.service;

import woowacourse_precoruse.java_open_mission_8.lotto.dto.LottoPurchaseRequestDto;
import woowacourse_precoruse.java_open_mission_8.lotto.dto.LottoResultResponseDto;
import woowacourse_precoruse.java_open_mission_8.lotto.dto.LottosPurchaseResponseDto;
import woowacourse_precoruse.java_open_mission_8.lotto.dto.WinningLottoRequestDto;

public interface LottoService {
    LottosPurchaseResponseDto purchaseLottos(LottoPurchaseRequestDto requestDto);
    LottoResultResponseDto checkWinningResult(String purchaseId, WinningLottoRequestDto requestDto);

    void resetLottos();
}
