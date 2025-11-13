package woowacourse_precoruse.java_open_mission_8.racingcar.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import woowacourse_precoruse.java_open_mission_8.racingcar.dto.RacingCarRequest;
import woowacourse_precoruse.java_open_mission_8.racingcar.dto.RacingCarResponse;
import woowacourse_precoruse.java_open_mission_8.racingcar.service.RacingCarService;

@Tag(name = "RacingCar API", description = "자동차 경주 게임 API")
@RestController
@RequestMapping("/api/racingcar")
public class RacingCarController {
    private final RacingCarService racingCarService;

    public RacingCarController(RacingCarService racingCarService) {
        this.racingCarService = racingCarService;
    }

    @Operation(summary = "자동차 경주 시작", description = "자동차 이름들과 시도 횟수를 받아 경주를 시작하고 최종 우승자를 반환합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "경주 성공",
                    content = @Content(schema = @Schema(implementation = RacingCarResponse.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 입력 값 (예: 이름 5자 초과, 시도 횟수 0 이하 등)")
    })
    @PostMapping("/play")
    public ResponseEntity<RacingCarResponse> play(
            @Parameter(description = "자동차 이름 목록 및 시도 횟수 DTO", required = true)
            @RequestBody @Valid RacingCarRequest request
    ) {
        RacingCarResponse response = racingCarService.play(request);
        return ResponseEntity.ok(response);
    }
}
