package woowacourse_precoruse.java_open_mission_8.calculator.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import woowacourse_precoruse.java_open_mission_8.calculator.dto.CalculatorRequest;
import woowacourse_precoruse.java_open_mission_8.calculator.dto.CalculatorResponse;
import woowacourse_precoruse.java_open_mission_8.calculator.service.CalculatorService;
import woowacourse_precoruse.java_open_mission_8.common.exception.BusinessLogicException;
import woowacourse_precoruse.java_open_mission_8.common.exception.ErrorCode;
import woowacourse_precoruse.java_open_mission_8.common.exception.GlobalExceptionHandler;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@WebFluxTest(CalculatorController.class)
@Import(GlobalExceptionHandler.class)
class CalculatorControllerTest {
    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private CalculatorService calculatorService;

    @Test
    @DisplayName("문자열 덧셈 요청 결과를 200 응답으로 반환한다")
    void add() {
        given(calculatorService.calculate(any(CalculatorRequest.class)))
                .willReturn(new CalculatorResponse(6));

        webTestClient.post()
                .uri("/api/calculator/add")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("""
                        {"expression":"//;\\n1;2;3"}
                        """)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.result").isEqualTo(6);
    }

    @Test
    @DisplayName("도메인 예외를 표준 오류 응답으로 변환한다")
    void addWithDomainException() {
        given(calculatorService.calculate(any(CalculatorRequest.class)))
                .willThrow(new BusinessLogicException(ErrorCode.CALC_NEGATIVE_NUMBER));

        webTestClient.post()
                .uri("/api/calculator/add")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("""
                        {"expression":"1,-2,3"}
                        """)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                .jsonPath("$.status").isEqualTo(400)
                .jsonPath("$.error").isEqualTo("Bad Request")
                .jsonPath("$.code").isEqualTo("CALC_NEGATIVE_NUMBER")
                .jsonPath("$.message").isEqualTo("[ERROR] 음수는 입력할 수 없습니다.");
    }
}
