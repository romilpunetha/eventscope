package io.eventscope.common.exception;


import io.eventscope.common.exception.util.ErrorCode;
import io.eventscope.common.exception.util.ErrorDetails;
import io.eventscope.common.exception.util.ErrorLevel;

public class RedisLockWaitException extends BaseRuntimeException {

    public RedisLockWaitException() {
        super(ErrorLevel.ERROR, ErrorCode.INTERNAL_ERROR, "RedisLockWaitException", "Redis lock wait failed");
    }

    public RedisLockWaitException(ErrorDetails errorDetails) {
        super(errorDetails);
    }

    public RedisLockWaitException(ErrorDetails errorDetails, Throwable throwable) {
        super(errorDetails, throwable);
    }

    public RedisLockWaitException(String message) {
        super(ErrorLevel.ERROR, ErrorCode.INTERNAL_ERROR, "RedisLockWaitException", message);
    }

    public RedisLockWaitException(String message, String userMessage) {
        super(ErrorLevel.ERROR, ErrorCode.INTERNAL_ERROR, "RedisLockWaitException", message, userMessage);
    }
}
