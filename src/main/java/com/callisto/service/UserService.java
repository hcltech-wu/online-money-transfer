package com.callisto.service;

import com.callisto.Constant.ExceptionConstants;
import com.callisto.Constant.LogMessages;
import com.callisto.dto.CustomerProfileDTO;
import com.callisto.controller.UserController;
import com.callisto.exception.FirstNameNotFoundException;
import com.callisto.exception.ResourceNotFoundException;
import com.callisto.exception.UserEmailNotFoundException;
import com.callisto.model.Address;
import com.callisto.model.User;
import com.callisto.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Log4j2
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

  private static final Logger logger = LogManager.getLogger(UserController.class);

    public CustomerProfileDTO getCustomerProfileByEmail(String email) {
        log.info(LogMessages.FETCHING_USER_BY_EMAIL, email);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException(ExceptionConstants.resourceNotFound));

        String fullName = Stream.of(user.getFirstName(), user.getMiddleName(), user.getLastName())
                .filter(Objects::nonNull).collect(Collectors.joining(" "));
        Address address = user.getAddress();
        String addressString = String.format("%s,%s,%s-%s", address.getStreet(), address.getPin(), address.getCity(),
                address.getCountry());
        String mobile = "+91 " + user.getPhoneNumber();
        final String language = "English";
        final String status = "Active";
        return new CustomerProfileDTO(fullName, user.getDob(), addressString, LogMessages.language, LogMessages.status,
                user.getEmail(), mobile);
    }


    public void saveUser(User user) {

        }
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
