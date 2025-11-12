package woowacourse_precoruse.java_open_mission_8.lotto.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import woowacourse_precoruse.java_open_mission_8.lotto.dto.LottoPurchaseRequestDto;
import woowacourse_precoruse.java_open_mission_8.lotto.dto.LottoResultResponseDto;
import woowacourse_precoruse.java_open_mission_8.lotto.dto.LottosPurchaseResponseDto;
import woowacourse_precoruse.java_open_mission_8.lotto.dto.WinningLottoRequestDto;
import woowacourse_precoruse.java_open_mission_8.lotto.service.LottoService;

@RestController
@RequestMapping("/api/lottos")
public class LottoController {
    private final LottoService lottoService;

    public LottoController(LottoService lottoService) {
        this.lottoService = lottoService;
    }

    @PostMapping
    public ResponseEntity<LottosPurchaseResponseDto> purchaseLottos(
            @Valid @RequestBody LottoPurchaseRequestDto requestDto
    ) {
        LottosPurchaseResponseDto response = lottoService.purchaseLottos(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/{purchaseId}/results")
    public ResponseEntity<LottoResultResponseDto> checkWinningResult(
            @PathVariable String purchaseId,
            @Valid @RequestBody WinningLottoRequestDto requestDto
    ) {
        LottoResultResponseDto response = lottoService.checkWinningResult(purchaseId, requestDto);
        return ResponseEntity.ok(response);
    }
}
