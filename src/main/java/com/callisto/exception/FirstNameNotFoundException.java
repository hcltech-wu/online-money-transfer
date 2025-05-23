package com.callisto.exception;

import com.callisto.Constant.ExceptionConstants;

public class FirstNameNotFoundException extends RuntimeException {
    public FirstNameNotFoundException() {
        super(ExceptionConstants.firstNameNotFound);
    }
}
