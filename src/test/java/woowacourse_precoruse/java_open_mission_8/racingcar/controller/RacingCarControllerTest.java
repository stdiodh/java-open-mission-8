package woowacourse_precoruse.java_open_mission_8.racingcar.controller;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import woowacourse_precoruse.java_open_mission_8.common.exception.GlobalExceptionHandler;
import woowacourse_precoruse.java_open_mission_8.racingcar.dto.CarDto;
import woowacourse_precoruse.java_open_mission_8.racingcar.dto.RacingCarRequest;
import woowacourse_precoruse.java_open_mission_8.racingcar.dto.RacingCarResponse;
import woowacourse_precoruse.java_open_mission_8.racingcar.service.RacingCarService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@WebFluxTest(RacingCarController.class)
@Import(GlobalExceptionHandler.class)
class RacingCarControllerTest {
    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private RacingCarService racingCarService;

    @Test
    @DisplayName("자동차 경주 결과를 200 응답으로 반환한다")
    void play() {
        RacingCarResponse response = new RacingCarResponse(
                List.of(
                        List.of(new CarDto("pobi", 1), new CarDto("woni", 0)),
                        List.of(new CarDto("pobi", 2), new CarDto("woni", 1))
                ),
                List.of("pobi")
        );
        given(racingCarService.play(any(RacingCarRequest.class))).willReturn(response);

        webTestClient.post()
                .uri("/api/racingcar/play")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("""
                        {"names":"pobi,woni","count":2}
                        """)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.rounds[0][0].name").isEqualTo("pobi")
                .jsonPath("$.rounds[1][0].position").isEqualTo(2)
                .jsonPath("$.winners[0]").isEqualTo("pobi");
    }

    @Test
    @DisplayName("DTO validation 실패를 COMMON_INVALID_INPUT 응답으로 반환한다")
    void playWithInvalidRequest() {
        webTestClient.post()
                .uri("/api/racingcar/play")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("""
                        {"names":"","count":0}
                        """)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                .jsonPath("$.status").isEqualTo(400)
                .jsonPath("$.error").isEqualTo("Bad Request")
                .jsonPath("$.code").isEqualTo("COMMON_INVALID_INPUT")
                .jsonPath("$.message").exists();
    }
}
