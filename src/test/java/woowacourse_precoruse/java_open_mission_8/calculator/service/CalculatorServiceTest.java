package woowacourse_precoruse.java_open_mission_8.calculator.service;

import org.junit.jupiter.api.Test;
import woowacourse_precoruse.java_open_mission_8.calculator.dto.CalculatorRequest;
import woowacourse_precoruse.java_open_mission_8.calculator.dto.CalculatorResponse;

import static org.assertj.core.api.Assertions.assertThat;

class CalculatorServiceTest {
    private final CalculatorService service = new CalculatorService();

    @Test
    void 문자열_입력으로_총합을_계산한다() {
        CalculatorRequest request = new CalculatorRequest("1,2:3");
        CalculatorResponse response = service.calculate(request);

        assertThat(response.result()).isEqualTo(6);
    }

    @Test
    void 커스텀_구분자도_지원한다() {
        CalculatorRequest request = new CalculatorRequest("//;\n1;2;3");
        CalculatorResponse response = service.calculate(request);

        assertThat(response.result()).isEqualTo(6);
    }

    @Test
    void 빈문자열은_0을_반환한다() {
        CalculatorRequest request = new CalculatorRequest("");
        CalculatorResponse response = service.calculate(request);

        assertThat(response.result()).isEqualTo(0);
    }
}

