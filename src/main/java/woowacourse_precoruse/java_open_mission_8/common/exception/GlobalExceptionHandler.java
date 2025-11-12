package woowacourse_precoruse.java_open_mission_8.common.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * @Valid 어노테이션에서 발생한 예외 (DTO 유효성 검사 실패)
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getAllErrors().getFirst().getDefaultMessage();
        Map<String, String> errorResponse = Map.of("error", errorMessage);

        return ResponseEntity.badRequest().body(errorResponse);
    }

    /**
     * 도메인 또는 서비스 로직에서 발생한 예외 (예: 금액 단위 오류, 중복 번호)
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleIllegalArgumentException(IllegalArgumentException ex) {
        Map<String, String> errorResponse = Map.of("error", ex.getMessage());

        return ResponseEntity.badRequest().body(errorResponse);
    }

    /**
     * 기타 500 에러
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleInternalServerError(Exception ex) {
        Map<String, String> errorResponse = Map.of("error", "서버 내부 오류가 발생했습니다.");

        return ResponseEntity.internalServerError().body(errorResponse);
    }
}
