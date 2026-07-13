package com.springlens.common.exception;

public class badRequestException extends RuntimeException {

    public badRequestException(String message) {
        super(message);
    }

}