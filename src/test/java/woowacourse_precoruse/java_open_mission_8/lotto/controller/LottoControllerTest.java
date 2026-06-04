package woowacourse_precoruse.java_open_mission_8.lotto.controller;

import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import woowacourse_precoruse.java_open_mission_8.common.exception.GlobalExceptionHandler;
import woowacourse_precoruse.java_open_mission_8.lotto.domain.Rank;
import woowacourse_precoruse.java_open_mission_8.lotto.dto.LottoPurchaseRequestDto;
import woowacourse_precoruse.java_open_mission_8.lotto.dto.LottoResponseDto;
import woowacourse_precoruse.java_open_mission_8.lotto.dto.LottoResultResponseDto;
import woowacourse_precoruse.java_open_mission_8.lotto.dto.LottosPurchaseResponseDto;
import woowacourse_precoruse.java_open_mission_8.lotto.dto.WinningLottoRequestDto;
import woowacourse_precoruse.java_open_mission_8.lotto.service.LottoService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

@WebFluxTest(LottoController.class)
@Import(GlobalExceptionHandler.class)
class LottoControllerTest {
    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private LottoService lottoService;

    @Test
    @DisplayName("로또 구매 결과를 201 응답으로 반환한다")
    void purchaseLottos() {
        LottosPurchaseResponseDto response = new LottosPurchaseResponseDto(
                "purchase-1",
                List.of(new LottoResponseDto(List.of(1, 2, 3, 4, 5, 6)))
        );
        given(lottoService.purchaseLottos(any(LottoPurchaseRequestDto.class))).willReturn(response);

        webTestClient.post()
                .uri("/api/lottos")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("""
                        {"amount":1000}
                        """)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.purchaseId").isEqualTo("purchase-1")
                .jsonPath("$.purchaseCount").isEqualTo(1)
                .jsonPath("$.lottos[0].numbers[0]").isEqualTo(1);
    }

    @Test
    @DisplayName("구매 ID와 당첨 번호로 로또 결과를 반환한다")
    void checkWinningResult() {
        LottoResultResponseDto response = new LottoResultResponseDto(Map.of(Rank.FIFTH, 1), 5.0);
        given(lottoService.checkWinningResult(eq("purchase-1"), any(WinningLottoRequestDto.class)))
                .willReturn(response);

        webTestClient.post()
                .uri("/api/lottos/purchase-1/results")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("""
                        {"winningNumbers":[1,2,3,4,5,6],"bonusNumber":7}
                        """)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.resultCounts.FIFTH").isEqualTo(1)
                .jsonPath("$.profitRate").isEqualTo(5.0);
    }

    @Test
    @DisplayName("당첨 번호 validation 실패를 COMMON_INVALID_INPUT 응답으로 반환한다")
    void checkWinningResultWithInvalidRequest() {
        webTestClient.post()
                .uri("/api/lottos/purchase-1/results")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("""
                        {"winningNumbers":[1,2,3],"bonusNumber":7}
                        """)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                .jsonPath("$.status").isEqualTo(400)
                .jsonPath("$.error").isEqualTo("Bad Request")
                .jsonPath("$.code").isEqualTo("COMMON_INVALID_INPUT")
                .jsonPath("$.message").isEqualTo("[ERROR] 당첨 번호는 6개여야 합니다.");
    }
}
