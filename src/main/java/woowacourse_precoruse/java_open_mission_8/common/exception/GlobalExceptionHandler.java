package woowacourse_precoruse.java_open_mission_8.common.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import woowacourse_precoruse.java_open_mission_8.common.dto.ErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {

        BindingResult bindingResult = ex.getBindingResult();
        List<ObjectError> allErrors = bindingResult.getAllErrors();
        ObjectError firstError = allErrors.getFirst();
        String errorMessage = firstError.getDefaultMessage();

        ErrorCode errorCode = ErrorCode.INVALID_INPUT_VALUE;
        ErrorResponse response = ErrorResponse.of(errorCode, errorMessage); // 커스텀 메시지 사용
        HttpStatus status = errorCode.getStatus();

        return new ResponseEntity<>(response, status);
    }
    @ExceptionHandler(BusinessLogicException.class)
    public ResponseEntity<ErrorResponse> handleBusinessLogicException(BusinessLogicException ex) {

        ErrorCode errorCode = ex.getErrorCode();
        HttpStatus status = errorCode.getStatus();

        ErrorResponse response = ErrorResponse.of(errorCode);

        return new ResponseEntity<>(response, status);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleInternalServerError(Exception ex) {
        log.error("예상치 못한 500 예외를 발견했습니다 : ", ex);

        ErrorCode errorCode = ErrorCode.SERVER_INTERNAL_ERROR;
        ErrorResponse response = ErrorResponse.of(errorCode);
        HttpStatus status = errorCode.getStatus();

        return new ResponseEntity<>(response, status);
    }
}
