package com.callisto.dto;

import lombok.Data;

@Data
public class LoginResponse {
    String status;
    String message;

    public LoginResponse(String status, String message) {
        this.status = status;
        this.message = message;
    }
}
