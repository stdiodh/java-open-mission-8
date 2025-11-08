package woowacourse_precoruse.java_open_mission_8.calculator.service;

import org.springframework.stereotype.Service;
import woowacourse_precoruse.java_open_mission_8.calculator.domain.Calculator;
import woowacourse_precoruse.java_open_mission_8.calculator.dto.CalculatorRequest;
import woowacourse_precoruse.java_open_mission_8.calculator.dto.CalculatorResponse;

@Service
public class CalculatorService {
    public CalculatorResponse calculate(CalculatorRequest request) {
        Calculator calculator = new Calculator();
        int result = calculator.calculate(request.expression());
        return new CalculatorResponse(result);
    }
}
