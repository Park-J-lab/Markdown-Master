package com.markdownMaster.auth.exceptions;

public class InvalidTokenException extends Exception {

    public InvalidTokenException(String message, RuntimeException e) {
        super(message, e);
    }

    public InvalidTokenException(String s) {
        super(s);
    }
}

