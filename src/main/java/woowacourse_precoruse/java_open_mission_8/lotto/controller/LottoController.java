package woowacourse_precoruse.java_open_mission_8.lotto.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "Lotto API", description = "로또 구매 및 당첨 결과 확인 API")
@RestController
@RequestMapping("/api/lottos")
public class LottoController {
    private final LottoService lottoService;

    public LottoController(LottoService lottoService) {
        this.lottoService = lottoService;
    }

    @Operation(summary = "로또 구매", description = "구입 금액을 받아 로또를 발급합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "로또 구매 성공",
                    content = @Content(schema = @Schema(implementation = LottosPurchaseResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 구입 금액 (예: 1000원 단위가 아님, 0원 이하)")
    })
    @PostMapping
    public ResponseEntity<LottosPurchaseResponseDto> purchaseLottos(
            @Parameter(description = "로또 구입 금액 DTO", required = true)
            @Valid @RequestBody LottoPurchaseRequestDto requestDto
    ) {
        LottosPurchaseResponseDto response = lottoService.purchaseLottos(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "로또 당첨 결과 확인", description = "구매 ID와 당첨 번호를 입력받아 당첨 결과를 반환합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "당첨 결과 조회 성공",
                    content = @Content(schema = @Schema(implementation = LottoResultResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 당첨 번호 (예: 1~45 범위 초과, 번호 중복)"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 구매 ID")
    })
    @PostMapping("/{purchaseId}/results")
    public ResponseEntity<LottoResultResponseDto> checkWinningResult(
            @Parameter(description = "구매 시 발급된 ID", required = true, example = "abc-def-123")
            @PathVariable String purchaseId,

            @Parameter(description = "당첨 번호 6개와 보너스 번호 1개 DTO", required = true)
            @Valid @RequestBody WinningLottoRequestDto requestDto
    ) {
        LottoResultResponseDto response = lottoService.checkWinningResult(purchaseId, requestDto);
        return ResponseEntity.ok(response);
    }
}
