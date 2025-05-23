package com.callisto.controller;

import com.callisto.Constant.LogMessages;
import com.callisto.dto.CustomerProfileDTO;

import com.callisto.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/userProfile")
    public ResponseEntity<CustomerProfileDTO> getCustomerProfile(@RequestParam String email) {
        log.info(LogMessages.REQUEST_RECEIVED_EMAIL, email);
        CustomerProfileDTO profile = userService.getCustomerProfileByEmail(email);
        return ResponseEntity.ok(profile);
    }
}
