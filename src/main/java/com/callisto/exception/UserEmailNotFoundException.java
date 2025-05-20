package com.callisto.exception;

import com.callisto.Constant.Constants;

public class UserEmailNotFoundException extends RuntimeException {
    public UserEmailNotFoundException() {
        super(Constants.emailNotFound);
    }
}
