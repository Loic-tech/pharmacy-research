package com.search.pharmacy.advice;

import com.search.pharmacy.common.exception.InvalidParamException;
import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApplicationExceptionHandler {

  @ExceptionHandler(AccessDeniedException.class)
  @ResponseStatus(HttpStatus.FORBIDDEN)
  public Map<String, String> handleAccessDeniedException(
      AccessDeniedException ex, HttpServletRequest request) {

    Map<String, String> errorMap = new HashMap<>();
    errorMap.put("Error Message", ex.getMessage() + " : You don't have the necessary permissions.");

    return errorMap;
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public Map<String, String> handleInvalidArguments(MethodArgumentNotValidException e) {
    Map<String, String> errorMap = new HashMap<>();
    e.getBindingResult()
        .getFieldErrors()
        .forEach(error -> errorMap.put(error.getField(), error.getDefaultMessage()));
    return errorMap;
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(IllegalArgumentException.class)
  public Map<String, String> handleInvalidArguments(IllegalArgumentException e) {
    Map<String, String> errorMap = new HashMap<>();
    errorMap.put("Error Message", e.getMessage());
    return errorMap;
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(InvalidParamException.class)
  public Map<String, String> handleInvalidParams(InvalidParamException e) {
    Map<String, String> errorMap = new HashMap<>();
    errorMap.put("Error Message", e.getMessage());
    return errorMap;
  }
}
