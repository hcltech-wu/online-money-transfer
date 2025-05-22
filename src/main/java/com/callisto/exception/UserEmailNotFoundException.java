package com.callisto.exception;

import com.callisto.Constant.ExceptionConstants;

public class UserEmailNotFoundException extends RuntimeException {
    public UserEmailNotFoundException() {
        super(ExceptionConstants.emailNotFound);
    }
}
