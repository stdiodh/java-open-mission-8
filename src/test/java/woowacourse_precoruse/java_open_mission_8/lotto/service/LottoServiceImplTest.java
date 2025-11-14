package woowacourse_precoruse.java_open_mission_8.lotto.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import woowacourse_precoruse.java_open_mission_8.lotto.domain.Lotto;
import woowacourse_precoruse.java_open_mission_8.lotto.domain.LottoNumber;
import woowacourse_precoruse.java_open_mission_8.lotto.domain.Rank;
import woowacourse_precoruse.java_open_mission_8.lotto.dto.LottoPurchaseRequestDto;
import woowacourse_precoruse.java_open_mission_8.lotto.dto.LottoResultResponseDto;
import woowacourse_precoruse.java_open_mission_8.lotto.dto.LottosPurchaseResponseDto;
import woowacourse_precoruse.java_open_mission_8.lotto.dto.WinningLottoRequestDto;
import woowacourse_precoruse.java_open_mission_8.lotto.repository.LottoRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class LottoServiceImplTest {
    @InjectMocks
    private LottoServiceImpl lottoService;

    @Mock
    private LottoRepository lottoRepository;

    @Mock
    private LottoNumberGenerator lottoNumberGenerator;

    private Lotto createTestLotto(List<Integer> numbers, String purchaseId) {
        return new Lotto(numbers.stream()
                .map(LottoNumber::new)
                .toList(), purchaseId);
    }

    @Test
    void 로또를_구매하면_개수만큼_생성하고_DB에_저장한다() {
        LottoPurchaseRequestDto requestDto = new LottoPurchaseRequestDto(3000);
        String purchaseId = "test-purchase-id";

        Lotto lotto1 = createTestLotto(List.of(1, 2, 3, 4, 5, 6), purchaseId);
        Lotto lotto2 = createTestLotto(List.of(7, 8, 9, 10, 11, 12), purchaseId);
        Lotto lotto3 = createTestLotto(List.of(13, 14, 15, 16, 17, 18), purchaseId);

        given(lottoNumberGenerator.generate(anyString()))
                .willReturn(lotto1, lotto2, lotto3);

        LottosPurchaseResponseDto response = lottoService.purchaseLottos(requestDto);

        verify(lottoNumberGenerator, times(3)).generate(anyString());

        @SuppressWarnings("unchecked")
        ArgumentCaptor<List<Lotto>> captor = ArgumentCaptor.forClass(List.class);
        verify(lottoRepository, times(1)).saveAll(captor.capture());
        assertThat(captor.getValue()).hasSize(3);

        assertThat(response.purchaseCount()).isEqualTo(3);
        assertThat(response.lottos()).hasSize(3);
        assertThat(response.lottos().getFirst().numbers()).isEqualTo(List.of(1, 2, 3, 4, 5, 6));
    }

    @Test
    void 당첨_결과를_정확히_계산하여_반환한다() {
        WinningLottoRequestDto requestDto = new WinningLottoRequestDto(
                List.of(1, 2, 3, 4, 5, 6), 7
        );
        String purchaseId = "test-purchase-id";

        Lotto lottoFifth = createTestLotto(List.of(1, 2, 3, 10, 11, 12), purchaseId);
        Lotto lottoMiss = createTestLotto(List.of(10, 11, 12, 13, 14, 15), purchaseId);
        List<Lotto> purchasedLottos = List.of(lottoFifth, lottoMiss);

        given(lottoRepository.findAllByPurchaseId(purchaseId))
                .willReturn(purchasedLottos);

        LottoResultResponseDto response = lottoService.checkWinningResult(purchaseId, requestDto);

        verify(lottoRepository, times(1)).findAllByPurchaseId(purchaseId);

        assertThat(response.resultCounts().get(Rank.FIFTH)).isEqualTo(1);
        assertThat(response.resultCounts().getOrDefault(Rank.FOURTH, 0)).isEqualTo(0);

        assertThat(response.profitRate()).isEqualTo(2.5);
    }

    @Test
    void 유효하지_않은_구매ID로_결과조회시_예외가_발생한다() {
        WinningLottoRequestDto requestDto = new WinningLottoRequestDto(
                List.of(1, 2, 3, 4, 5, 6), 7
        );
        String invalidId = "invalid-id";

        given(lottoRepository.findAllByPurchaseId(invalidId))
                .willReturn(List.of());

        assertThatThrownBy(() -> lottoService.checkWinningResult(invalidId, requestDto))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 해당 구매 ID");
    }

    @Test
    void resetLottos는_레포지토리의_deleteAll을_호출한다() {
        lottoService.resetLottos();

        verify(lottoRepository, times(1)).deleteAll();
    }
}
