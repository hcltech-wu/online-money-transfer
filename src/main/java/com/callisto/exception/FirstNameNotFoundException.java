package com.callisto.exception;

import com.callisto.Constant.Constants;

public class FirstNameNotFoundException extends RuntimeException {
    public FirstNameNotFoundException() {
        super(Constants.firstNameNotFound);
    }
}
