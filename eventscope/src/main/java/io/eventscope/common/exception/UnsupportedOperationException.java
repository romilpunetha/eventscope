package io.eventscope.common.exception;

import io.eventscope.common.exception.util.ErrorCode;
import io.eventscope.common.exception.util.ErrorDetails;
import io.eventscope.common.exception.util.ErrorLevel;

public class UnsupportedOperationException extends BaseRuntimeException {

    public UnsupportedOperationException() {
        super(ErrorLevel.FATAL, ErrorCode.FORBIDDEN, "UnsupportedOperationException", "This operation is not supported");
    }

    public UnsupportedOperationException(ErrorDetails errorDetails) {
        super(errorDetails);
    }

    public UnsupportedOperationException(ErrorDetails errorDetails, Throwable throwable) {
        super(errorDetails, throwable);
    }

    public UnsupportedOperationException(String message) {
        super(ErrorLevel.FATAL, ErrorCode.FORBIDDEN, "UnsupportedOperationException", message);
    }

    public UnsupportedOperationException(String message, String namespace) {
        super(ErrorLevel.FATAL, ErrorCode.FORBIDDEN, "UnsupportedOperationException", message, namespace);
    }

}
