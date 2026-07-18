package com.springlens.parser.gradle.exception;

public class GradleParseException extends RuntimeException {

    public GradleParseException(String message) {
        super(message);
    }

    public GradleParseException(String message, Throwable cause) {
        super(message, cause);
    }

}