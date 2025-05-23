package com.callisto.exception;

import com.callisto.Constant.Constants;

public class UserEmailNotFoundException extends RuntimeException {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public UserEmailNotFoundException() {
        super(Constants.emailNotFound);
    }

    public UserEmailNotFoundException(String msg) {
        super(msg);
    }
}
