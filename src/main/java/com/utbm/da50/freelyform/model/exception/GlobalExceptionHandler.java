package com.utbm.da50.freelyform.model.exception;

import com.utbm.da50.freelyform.exceptions.ResourceNotFoundException;
import com.utbm.da50.freelyform.exceptions.UniqueResponseException;
import com.utbm.da50.freelyform.exceptions.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * GlobalExceptionHandler handles exceptions thrown by the application and returns structured error responses.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

  /**
   * Handles ResourceNotFoundException and returns a structured response.
   *
   * @param ex the ResourceNotFoundException thrown
   * @return a ResponseEntity containing the error message and status
   */
  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleResourceNotFound(ResourceNotFoundException ex) {
    ErrorResponse errorResponse = new ErrorResponse("Resource Not Found", ex.getMessage());
    return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
  }

  /**
   * Handles ValidationException and returns a structured response.
   *
   * @param ex the ValidationException thrown
   * @return a ResponseEntity containing the error message and status
   */
  @ExceptionHandler(ValidationException.class)
  public ResponseEntity<ErrorResponse> handleValidationException(ValidationException ex) {
    ErrorResponse errorResponse = new ErrorResponse("Validation Error", ex.getMessage());
    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
  }

  /**
   * Handles UniqueResponseException and returns a structured response.
   *
   * @param ex the UniqueResponseException thrown
   * @return a ResponseEntity containing the error message and status
   */
  @ExceptionHandler(UniqueResponseException.class)
  public ResponseEntity<ErrorResponse> handleUniqueResponseException(UniqueResponseException ex) {
    ErrorResponse errorResponse = new ErrorResponse("Unique Response Error", ex.getMessage());
    return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
  }

  /**
   * Handles all other exceptions and returns a generic error response.
   *
   * @param ex the Exception thrown
   * @return a ResponseEntity containing a generic error message and status
   */
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
    ErrorResponse errorResponse = new ErrorResponse("Internal Server Error", "An unexpected error occurred.");
    return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
