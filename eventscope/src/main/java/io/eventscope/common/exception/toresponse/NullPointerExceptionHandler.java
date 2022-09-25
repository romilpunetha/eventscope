package io.eventscope.common.exception.toresponse;

import io.eventscope.common.constant.Constant;
import io.eventscope.common.exception.util.ErrorCode;
import io.eventscope.common.exception.util.ErrorDetails;
import io.eventscope.common.exception.util.ErrorLevel;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.StatusCode;
import io.quarkus.arc.Priority;
import io.quarkus.logging.Log;
import org.jboss.resteasy.reactive.RestResponse;
import org.jboss.resteasy.reactive.server.ServerExceptionMapper;

import javax.ws.rs.Priorities;
import javax.ws.rs.ext.Provider;

@Provider
@Priority(Priorities.ENTITY_CODER + 800)
public class NullPointerExceptionHandler {


    @ServerExceptionMapper
    public RestResponse<ErrorDetails> toResponse(NullPointerException e) {

        Span span = Span.current();
        span.recordException(e);
        span.setStatus(StatusCode.ERROR, e.getMessage());
        Log.error(e.getMessage(), e);
        ErrorDetails errorDetails = ErrorDetails.builder()
                .title(e.getClass().getSimpleName())
                .errorCode(ErrorCode.INTERNAL_ERROR)
                .level(ErrorLevel.ERROR)
                .message(e.getMessage())
                .userMessage(Constant.DEFAULT_ERROR_MESSAGE)
                .build();

        return RestResponse.status(
                RestResponse.Status.fromStatusCode(errorDetails.getErrorCode().getErrorType().getStatusCode()),
                errorDetails);
    }
}
