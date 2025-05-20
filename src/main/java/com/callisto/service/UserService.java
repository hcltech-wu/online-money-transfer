package com.callisto.service;

import com.callisto.exception.FirstNameNotFoundException;
import com.callisto.exception.UserEmailNotFoundException;
import com.callisto.model.User;
import com.callisto.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
}
