package com.callisto.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.callisto.Constant.LogMessages;
import com.callisto.Constant.SmConstants;
import com.callisto.dto.CustomerProfileDTO;
import com.callisto.dto.UserDto;
import com.callisto.model.User;
import com.callisto.service.UserService;

import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;

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

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody User user) {
        logger.info("Received request to /api/user/register with params: {}", user.getEmail());
        userService.saveUser(user);
        return ResponseEntity.ok(SmConstants.USER_REGISTERED);

    }

    @PutMapping("/updateUser")
    public ResponseEntity<String> updateUserDetails(@Valid @RequestBody UserDto userDto) {
        userService.updateUserDetails(userDto);
        return ResponseEntity.ok("User update successfully");

    }
}
