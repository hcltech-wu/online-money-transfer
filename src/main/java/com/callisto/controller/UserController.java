package com.callisto.controller;

import com.callisto.Constant.LogMessages;
import com.callisto.dto.CustomerProfileDTO;

import com.callisto.model.User;
import com.callisto.service.UserService;
import lombok.extern.log4j.Log4j2;
import com.callisto.Constant.SmConstants;
import com.callisto.model.User;

import com.callisto.Constant.SmConstants;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequestMapping("/api/user")
public class UserController {

    private static final Logger logger = LogManager.getLogger(UserController.class);
    @Autowired
    private UserService userService;

    @GetMapping("/userProfile")
    public ResponseEntity<CustomerProfileDTO> getCustomerProfile(@RequestParam String email) {
        log.info(LogMessages.REQUEST_RECEIVED_EMAIL, email);
        CustomerProfileDTO profile = userService.getCustomerProfileByEmail(email);
        return ResponseEntity.ok(profile);
    }

}
