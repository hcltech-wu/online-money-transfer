package com.callisto.exception;

import com.callisto.Constant.ExceptionConstants;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
