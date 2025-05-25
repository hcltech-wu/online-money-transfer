package com.callisto.exception;

import com.callisto.Constant.ExceptionConstants;

public class UserEmailNotFoundException extends RuntimeException {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public UserEmailNotFoundException() {
        super(ExceptionConstants.emailNotFound);
    }

    public UserEmailNotFoundException(String msg) {
        super(msg);
    }
}
