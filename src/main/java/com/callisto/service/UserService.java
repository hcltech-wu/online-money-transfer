package com.callisto.service;

import com.callisto.controller.UserController;
import com.callisto.exception.FirstNameNotFoundException;
import com.callisto.exception.UserEmailNotFoundException;
import com.callisto.model.User;
import com.callisto.repository.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final Logger logger = LogManager.getLogger(UserController.class);

    public void saveUser(User user) {
        logger.info("Inside saveUser()");
        logger.info("Saving user with Email: {}", user.getEmail());
        if (userRepository.existsByEmail(user.getEmail())) {
            logger.info(" User already present with Email: {}", user.getEmail());
            throw new UserEmailNotFoundException();
        }

        if (user.getFirstName() == null || user.getFirstName().isBlank()) {
            logger.info(" User not saved: firstName is blank , Email: {}", user.getEmail());
            throw new FirstNameNotFoundException();
        }

        if (user.getLastName() == null || user.getLastName().isBlank()) {
            logger.info(" User not saved: lastName is blank so updating lastname with firstname, Email of user  : {}",
                    user.getEmail());
            user.setLastName(user.getFirstName());
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.save(user);

        logger.info("user saved successfully: {}" + user.getEmail());
    }
}
