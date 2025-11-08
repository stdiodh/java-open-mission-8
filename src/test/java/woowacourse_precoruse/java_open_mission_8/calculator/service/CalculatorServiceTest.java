package woowacourse_precoruse.java_open_mission_8.calculator.service;

import org.junit.jupiter.api.Test;
import woowacourse_precoruse.java_open_mission_8.calculator.dto.CalculatorRequest;
import woowacourse_precoruse.java_open_mission_8.calculator.dto.CalculatorResponse;

import static org.assertj.core.api.Assertions.assertThat;

public class CalculatorServiceTest {
    private final CalculatorService calculatorService = new CalculatorService();

    @Test
    void 서비스는_도메인을_호출하여_올바른_계산_결과를_반환한다() {
        CalculatorRequest request = new CalculatorRequest("1,2:3");

        CalculatorResponse response = calculatorService.calculate(request);

        assertThat(response.result()).isEqualTo(6);
    }
}
