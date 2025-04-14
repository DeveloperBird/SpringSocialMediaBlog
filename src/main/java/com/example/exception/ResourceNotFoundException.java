package com.example.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

 //NOTE: This should be NOT_FOUND (404) and not BAD_REQUEST (400), but because of instructions this has to be BAD_REQUEST.
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ResourceNotFoundException extends SocialMediaException {

    public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s not found with %s : '%s'", resourceName, fieldName, fieldValue));
    }
}