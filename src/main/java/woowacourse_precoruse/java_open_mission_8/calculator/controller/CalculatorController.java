package woowacourse_precoruse.java_open_mission_8.calculator.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import woowacourse_precoruse.java_open_mission_8.calculator.dto.CalculatorRequest;
import woowacourse_precoruse.java_open_mission_8.calculator.dto.CalculatorResponse;
import woowacourse_precoruse.java_open_mission_8.calculator.service.CalculatorService;

@Tag(name = "Calculator API", description = "간단한 계산기 API")
@RestController
@RequestMapping("/api/calculator")
public class CalculatorController {
    private final CalculatorService calculatorService;

    public CalculatorController(CalculatorService calculatorService) {
        this.calculatorService = calculatorService;
    }

    @Operation(summary = "문자열 덧셈 계산",
            description = "쉼표(,) 또는 콜론(:)으로 구분된 숫자 문자열을 받아 총합을 반환합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "계산 성공",
                    content = @Content(schema = @Schema(implementation = CalculatorResponse.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 수식 입력"),
            @ApiResponse(responseCode = "500", description = "0으로 나누기 등 산술 오류")
    })
    @PostMapping("/add")
    public ResponseEntity<CalculatorResponse> add(
            @Parameter(description = "계산할 수식 문자열(예: '1,2:3')이 담긴 DTO", required = true)
            @RequestBody CalculatorRequest request
    ) {
        CalculatorResponse response = calculatorService.calculate(request);
        return ResponseEntity.ok(response);
    }
}
