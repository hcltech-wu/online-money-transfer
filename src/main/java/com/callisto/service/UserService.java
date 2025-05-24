package com.callisto.service;

import com.callisto.dto.UserDto;
import com.callisto.exception.FirstNameNotFoundException;
import com.callisto.exception.UserEmailNotFoundException;
import com.callisto.model.User;
import com.callisto.repository.UserRepository;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void saveUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new UserEmailNotFoundException();
        }

        if (user.getFirstName() == null || user.getFirstName().isBlank()) {
            throw new FirstNameNotFoundException();
        }

        if (user.getLastName() == null || user.getLastName().isBlank()) {
            user.setLastName(user.getFirstName());
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.save(user);
    }

    public void updateUserDetails(@Valid UserDto userDto) {
    	log.info("updating user info");
        Optional<User> useropt = userRepository.findByEmail(userDto.getEmail());
        if (!useropt.isPresent()) {
            throw new UserEmailNotFoundException("User is not exist with the email address");
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
}
