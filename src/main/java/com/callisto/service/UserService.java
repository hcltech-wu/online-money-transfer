package com.callisto.service;

<<<<<<< HEAD
import com.callisto.Constant.ExceptionConstants;
import com.callisto.Constant.LogMessages;
import com.callisto.dto.CustomerProfileDTO;
=======
import com.callisto.controller.UserController;
>>>>>>> a265d9b76f095a9cfe4e15bbf1077d487cc0a063
import com.callisto.exception.FirstNameNotFoundException;
import com.callisto.exception.ResourceNotFoundException;
import com.callisto.exception.UserEmailNotFoundException;
import com.callisto.model.Address;
import com.callisto.model.User;
import com.callisto.repository.UserRepository;
<<<<<<< HEAD
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
=======
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
>>>>>>> a265d9b76f095a9cfe4e15bbf1077d487cc0a063
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

<<<<<<< HEAD
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
=======
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
>>>>>>> a265d9b76f095a9cfe4e15bbf1077d487cc0a063
    }
}
