package woowacourse_precoruse.java_open_mission_8.racingcar.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import woowacourse_precoruse.java_open_mission_8.racingcar.dto.RacingCarRequest;
import woowacourse_precoruse.java_open_mission_8.racingcar.dto.RacingCarResponse;
import woowacourse_precoruse.java_open_mission_8.racingcar.service.RacingCarService;

@RestController
@RequestMapping("/api/racingcar")
public class RacingCarController {
    private final RacingCarService racingCarService;

    public RacingCarController(RacingCarService racingCarService) {
        this.racingCarService = racingCarService;
    }

    @PostMapping("/play")
    public ResponseEntity<RacingCarResponse> play(@RequestBody @Valid RacingCarRequest request) {
        RacingCarResponse response = racingCarService.play(request);
        return ResponseEntity.ok(response);
    }
}
