package com.greenplan.app.exeption;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ProjectExceptionHandler {

  private static final Logger logger = LoggerFactory.getLogger(ProjectExceptionHandler.class);

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, Object>> handleValidationErrors(MethodArgumentNotValidException ex) {
    var errors = ex.getBindingResult()
      .getFieldErrors()
      .stream()
      .collect(Collectors.toMap(
        error -> error.getField(),
        error -> error.getDefaultMessage()
      ));

    logger.error("Validation errors: {}", errors);

    return ResponseEntity.badRequest().body(Map.of(
      "error", "Validation failed",
      "details", errors
    ));
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<Map<String, Object>> handleGenericError(Exception ex) {
    logger.error("Error creating project", ex);
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
      "error", "Internal server error",
      "message", ex.getMessage()
    ));
  }
}
