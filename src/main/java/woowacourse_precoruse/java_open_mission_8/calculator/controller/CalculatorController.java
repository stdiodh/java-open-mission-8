package woowacourse_precoruse.java_open_mission_8.calculator.controller;

import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import woowacourse_precoruse.java_open_mission_8.calculator.dto.CalculatorRequest;
import woowacourse_precoruse.java_open_mission_8.calculator.dto.CalculatorResponse;
import woowacourse_precoruse.java_open_mission_8.calculator.service.CalculatorService;

@RestController
@RequestMapping("/api/calculator")
public class CalculatorController {
    private final CalculatorService calculatorService;

    public CalculatorController(CalculatorService calculatorService) {
        this.calculatorService = calculatorService;
    }

    @PostMapping("/add")
    public ResponseEntity<CalculatorResponse> add(@RequestBody CalculatorRequest request) {
        CalculatorResponse response = calculatorService.calculate(request);
        return ResponseEntity.ok(response);
    }
}
