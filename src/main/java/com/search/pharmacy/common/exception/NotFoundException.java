package com.search.pharmacy.common.exception;

import org.springframework.core.NestedRuntimeException;

public class NotFoundException extends NestedRuntimeException {

  public NotFoundException(String message) {
    super(message);
  }
}
