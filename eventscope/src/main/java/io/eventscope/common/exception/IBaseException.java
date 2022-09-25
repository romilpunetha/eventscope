package io.eventscope.common.exception;


import io.eventscope.common.exception.util.ErrorDetails;

public interface IBaseException {

    ErrorDetails getErrorDetails();

    void setErrorDetails(ErrorDetails errorDetails);
}
