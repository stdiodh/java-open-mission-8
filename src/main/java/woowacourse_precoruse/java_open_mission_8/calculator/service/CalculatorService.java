package woowacourse_precoruse.java_open_mission_8.calculator.service;

import java.util.List;
import org.springframework.stereotype.Service;
import woowacourse_precoruse.java_open_mission_8.calculator.domain.Calculator;
import woowacourse_precoruse.java_open_mission_8.calculator.domain.Numbers;
import woowacourse_precoruse.java_open_mission_8.calculator.domain.Parser;
import woowacourse_precoruse.java_open_mission_8.calculator.dto.CalculatorRequest;
import woowacourse_precoruse.java_open_mission_8.calculator.dto.CalculatorResponse;

@Service
public class CalculatorService {
    private final Parser parser;
    private final Calculator calculator;

    public CalculatorService() {
        this.parser = new Parser();
        this.calculator = new Calculator();
    }

    public CalculatorResponse calculate(CalculatorRequest request) {
        String expression = request.expression();

        List<String> tokens = parser.parse(expression);
        Numbers numbers = new Numbers(tokens);

        int result = calculator.calculate(numbers);

        return new CalculatorResponse(result);
    }
}
