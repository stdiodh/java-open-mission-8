package woowacourse_precoruse.java_open_mission_8.lotto.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import woowacourse_precoruse.java_open_mission_8.lotto.domain.Lotto;
import woowacourse_precoruse.java_open_mission_8.lotto.domain.LottoResult;
import woowacourse_precoruse.java_open_mission_8.lotto.domain.PurchaseAmount;
import woowacourse_precoruse.java_open_mission_8.lotto.domain.Rank;
import woowacourse_precoruse.java_open_mission_8.lotto.domain.WinningLotto;
import woowacourse_precoruse.java_open_mission_8.lotto.dto.LottoPurchaseRequestDto;
import woowacourse_precoruse.java_open_mission_8.lotto.dto.LottoResultResponseDto;
import woowacourse_precoruse.java_open_mission_8.lotto.dto.LottosPurchaseResponseDto;
import woowacourse_precoruse.java_open_mission_8.lotto.dto.WinningLottoRequestDto;
import woowacourse_precoruse.java_open_mission_8.lotto.repository.LottoRepository;

@Service
public class LottoServiceImpl implements LottoService {
    private final LottoRepository lottoRepository;
    private final LottoNumberGenerator lottoNumberGenerator;

    public LottoServiceImpl(LottoRepository lottoRepository, LottoNumberGenerator lottoNumberGenerator) {
        this.lottoRepository = lottoRepository;
        this.lottoNumberGenerator = lottoNumberGenerator;
    }

    @Transactional
    @Override
    public LottosPurchaseResponseDto purchaseLottos(LottoPurchaseRequestDto requestDto) {
        PurchaseAmount purchaseAmount = requestDto.toDomain();
        int lottoCount = purchaseAmount.calculateLottoCount();

        String purchaseId = UUID.randomUUID().toString();

        List<Lotto> purchasedLottos = new ArrayList<>();
        for (int i = 0; i < lottoCount; i++) {
            Lotto newLotto = lottoNumberGenerator.generate(purchaseId);
            purchasedLottos.add(newLotto);
        }

        lottoRepository.saveAll(purchasedLottos);

        return LottosPurchaseResponseDto.from(purchasedLottos, purchaseId);
    }

    @Transactional(readOnly = true)
    @Override
    public LottoResultResponseDto checkWinningResult(String purchaseId, WinningLottoRequestDto requestDto) {
        WinningLotto winningLotto = requestDto.toDomain();

        List<Lotto> userLottos = lottoRepository.findAllByPurchaseId(purchaseId);

        if (userLottos.isEmpty()) {
            throw new IllegalArgumentException("[ERROR] 해당 구매 ID(" + purchaseId + ")로 구매한 로또가 없습니다.");
        }

        PurchaseAmount purchaseAmount = new PurchaseAmount(userLottos.size() * PurchaseAmount.LOTTO_PRICE);

        Map<Rank, Integer> resultMap = new EnumMap<>(Rank.class);
        for (Lotto userLotto : userLottos) {
            Rank rank = winningLotto.match(userLotto);
            resultMap.put(rank, resultMap.getOrDefault(rank, 0) + 1);
        }
        LottoResult lottoResult = new LottoResult(resultMap);

        return LottoResultResponseDto.from(lottoResult, purchaseAmount);
    }
}
