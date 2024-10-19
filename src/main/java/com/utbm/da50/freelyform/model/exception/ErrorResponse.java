package com.utbm.da50.freelyform.model.exception;

/**
 * ErrorResponse is a simple model to encapsulate error details.
 */
public class ErrorResponse {
    private String error;
    private String message;

    /**
     * Constructs a new ErrorResponse with the specified error and message.
     *
     * @param error   the error type
     * @param message the error message
     */
    public ErrorResponse(String error, String message) {
        this.error = error;
        this.message = message;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }
}

