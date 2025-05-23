package com.callisto.controller;

import com.callisto.dto.UserDto;
import com.callisto.model.User;
import com.callisto.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody User user) {
        userService.saveUser(user);
        return ResponseEntity.ok("User registered successfully");
    }

    @PutMapping("/updateUser")
    public ResponseEntity<String> updateUserDetails(@Valid @RequestBody UserDto userDto) {
        userService.updateUserDetails(userDto);
        return ResponseEntity.ok("User update successfully");

    }
}
