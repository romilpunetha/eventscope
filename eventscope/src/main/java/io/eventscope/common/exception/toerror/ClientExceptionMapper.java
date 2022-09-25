package io.eventscope.common.exception.toerror;

import io.eventscope.common.exception.BaseRuntimeException;
import io.eventscope.common.exception.util.ErrorCode;
import io.eventscope.common.exception.util.ErrorDetails;
import io.eventscope.common.exception.util.ErrorLevel;
import io.opentelemetry.api.trace.Span;
import io.quarkus.arc.Priority;
import org.eclipse.microprofile.rest.client.ext.ResponseExceptionMapper;

import javax.ws.rs.core.Response;

@Priority(4500)
public class ClientExceptionMapper implements ResponseExceptionMapper<Throwable> {

    @Override
    public BaseRuntimeException toThrowable(Response response) {

        Span span = Span.current();

        try {
            response.bufferEntity();
            BaseRuntimeException exception = new BaseRuntimeException(response.readEntity(ErrorDetails.class));
            span.recordException(exception);
            return exception;
        } catch (Exception e) {
            span.recordException(e);
            BaseRuntimeException exception = new BaseRuntimeException(
                    ErrorLevel.ERROR,
                    ErrorCode.get(response.getStatus()),
                    "Unhandled Client Exception",
                    response.readEntity(String.class)
            );
            span.recordException(exception);
            return exception;
        }
    }
}
