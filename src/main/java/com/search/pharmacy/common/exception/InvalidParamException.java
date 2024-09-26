package com.search.pharmacy.common.exception;

import org.springframework.core.NestedRuntimeException;

public class InvalidParamException extends NestedRuntimeException {

    public InvalidParamException(String message) {
        super(message);
    }

    public InvalidParamException(String message, Throwable cause) {
        super(message, cause);
    }
}