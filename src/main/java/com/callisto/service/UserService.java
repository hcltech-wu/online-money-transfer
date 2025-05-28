package com.callisto.service;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.callisto.dto.LoginResponse;
import com.callisto.exception.UserEmailExitsException;
import com.callisto.exception.UserEmailNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.callisto.Constant.ExceptionConstants;
import com.callisto.Constant.LogMessages;
import com.callisto.dto.CustomerProfileDTO;

import com.callisto.dto.UserDto;
import com.callisto.exception.FirstNameNotFoundException;

import com.callisto.exception.ResourceNotFoundException;
import com.callisto.model.Address;
import com.callisto.model.User;
import com.callisto.repository.UserRepository;

import lombok.extern.log4j.Log4j2;
import jakarta.validation.Valid;

@Log4j2
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final Logger logger = LogManager.getLogger(UserService.class);

    public CustomerProfileDTO getCustomerProfileByEmail(String email) {
        log.info(LogMessages.FETCHING_USER_BY_EMAIL, email);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException(ExceptionConstants.resourceNotFound));

        String fullName = Stream.of(user.getFirstName(), user.getMiddleName(), user.getLastName())
                .filter(Objects::nonNull).collect(Collectors.joining(" "));
        Address address = user.getAddress();
        String addressString = String.format("%s,%s,%s-%s", address.getStreet(), address.getPin(), address.getCity(),
                address.getCountry());
        String mobile = user.getPhoneNumber();

        return new CustomerProfileDTO(fullName, user.getDob(), addressString, LogMessages.language, LogMessages.status,
                user.getEmail(), mobile);
    }

    public void saveUser(User user) {
        logger.info("Inside saveUser()");
        logger.info("Saving user with Email: {}", user.getEmail());
        if (userRepository.existsByEmail(user.getEmail())) {
            logger.info(" User already present with Email: {}", user.getEmail());
            throw new UserEmailExitsException();
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

    public void updateUserDetails(@Valid UserDto userDto) {
        log.info("updating user info");
        Optional<User> useropt = userRepository.findByEmail(userDto.getEmail());
        if (!useropt.isPresent()) {
            throw new UserEmailNotFoundException();
        } else {
            User existingUser = useropt.get();
            if (userDto.getFirstName() == null || userDto.getFirstName().isBlank()) {
                throw new FirstNameNotFoundException();
            }

            if (userDto.getLastName() == null || userDto.getLastName().isBlank()) {
                userDto.setLastName(userDto.getFirstName());
            }
            existingUser.setFirstName(userDto.getFirstName());
            existingUser.setMiddleName(
                    (userDto.getMiddleName() != null && userDto.getMiddleName() != "") ? userDto.getMiddleName()
                            : null);
            existingUser.setLastName(userDto.getLastName());
            existingUser.setPhoneNumber(userDto.getPhoneNumber());
            existingUser.setDob(userDto.getDob());
            existingUser.setAddress(userDto.getAddress());
            userRepository.save(existingUser);
            log.info("user info Updated successfully");
        }
    }

    public LoginResponse isUserValid(String email, String password) {
        // getting logs
        logger.info("Validating user with email: {}", email);

        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            boolean passwordMatches = passwordEncoder.matches(password, user.getPassword());
            if (passwordMatches) {
                logger.info("User {} authenticated successfully.", email);
                return new LoginResponse("Success", "User login successfully");
            } else {
                logger.warn("Incorrect password for user: {}", email);
                return new LoginResponse("Error", "Incorrect password");
            }
        } else {
            logger.warn("No user found with email: {}", email);
            return new LoginResponse("Error", "User not found");
        }

    }

}
