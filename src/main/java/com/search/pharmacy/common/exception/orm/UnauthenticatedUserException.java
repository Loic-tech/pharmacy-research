package com.search.pharmacy.common.exception.orm;

public class UnauthenticatedUserException extends RuntimeException {
  public UnauthenticatedUserException(String message) {
    super(message);
  }
}
