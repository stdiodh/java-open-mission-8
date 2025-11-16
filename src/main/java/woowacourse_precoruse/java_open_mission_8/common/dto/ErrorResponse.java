package woowacourse_precoruse.java_open_mission_8.common.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import woowacourse_precoruse.java_open_mission_8.common.exception.ErrorCode;

public class ErrorResponse {
    @JsonProperty("status")
    private final int status;

    @JsonProperty("error")
    private final String error;

    @JsonProperty("code")
    private final String code;

    @JsonProperty("message")
    private final String message;

    private ErrorResponse(ErrorCode errorCode, String customMessage) {
        this.status = errorCode.getStatusValue();
        this.error = errorCode.getErrorReasonPhrase();
        this.code = errorCode.getCode();
        this.message = (customMessage != null) ? customMessage : errorCode.getMessage();
    }

    public static ErrorResponse of(ErrorCode errorCode) {
        return new ErrorResponse(errorCode, null);
    }

    public static ErrorResponse of(ErrorCode errorCode, String customMessage) {
        return new ErrorResponse(errorCode, customMessage);
    }
}
