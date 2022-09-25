package io.eventscope.common.exception;


import io.eventscope.common.exception.util.ErrorCode;
import io.eventscope.common.exception.util.ErrorDetails;
import io.eventscope.common.exception.util.ErrorLevel;

public class NotImplementedException extends BaseRuntimeException {

    public NotImplementedException() {
        super(ErrorLevel.FATAL, ErrorCode.INTERNAL_ERROR, "NotImplementedException", "Operation not implemented yet");
    }

    public NotImplementedException(ErrorDetails errorDetails) {
        super(errorDetails);
    }

    public NotImplementedException(ErrorDetails errorDetails, Throwable throwable) {
        super(errorDetails, throwable);
    }

    public NotImplementedException(String message) {
        super(ErrorLevel.FATAL, ErrorCode.INTERNAL_ERROR, "NotImplementedException", message);
    }

    public NotImplementedException(String message, String userMessage) {
        super(ErrorLevel.FATAL, ErrorCode.INTERNAL_ERROR, "NotImplementedException", message, userMessage);
    }

}
