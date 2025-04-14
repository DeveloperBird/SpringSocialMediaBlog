package com.example.exception;

public class SocialMediaException extends RuntimeException {

    public SocialMediaException(String message) {
        super(message);
    }

    public SocialMediaException(String message, Throwable cause) {
        super(message, cause);
    }
}