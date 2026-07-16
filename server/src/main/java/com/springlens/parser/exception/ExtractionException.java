package com.springlens.parser.exception;

/**
 * Thrown whenever a project archive
 * cannot be extracted successfully.
 */
public class ExtractionException extends RuntimeException {

    public ExtractionException(String message) {
        super(message);
    }

    public ExtractionException(String message, Throwable cause) {
        super(message, cause);
    }

}