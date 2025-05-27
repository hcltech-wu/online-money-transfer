package com.callisto.exception;

import com.callisto.Constant.ExceptionConstants;

public class UserEmailExitsException extends RuntimeException {
    public UserEmailExitsException() {
        super(ExceptionConstants.emailAlreadyExits);
    }
}
