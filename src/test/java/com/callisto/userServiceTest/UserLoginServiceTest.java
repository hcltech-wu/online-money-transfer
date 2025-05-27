package com.callisto.userServiceTest;

import com.callisto.dto.LoginResponse;
import com.callisto.model.User;
import com.callisto.repository.UserRepository;
import com.callisto.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UserLoginServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testValidUser() {
        String email = "user@example.com";
        String rawPassword = "password123";
        String encodedPassword = "$2a$10$abcdefghijk1234567890";
        String expectedResult = "User login successfully";

        User user = new User();
        user.setEmail(email);
        user.setPassword(encodedPassword);

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(rawPassword, encodedPassword)).thenReturn(true);

        LoginResponse result = userService.isUserValid(email, rawPassword);
        assertEquals(result.getMessage(), expectedResult);
        verify(userRepository).findByEmail(email);
        verify(passwordEncoder).matches(rawPassword, encodedPassword);
    }

    @Test
    void testInvalidPassword() {
        String email = "user@example.com";
        String rawPassword = "wrongpassword";
        String encodedPassword = "$2a$10$abcdefghijk1234567890";
        String expectedResult = "Incorrect password";

        User user = new User();
        user.setEmail(email);
        user.setPassword(encodedPassword);

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(rawPassword, encodedPassword)).thenReturn(false);

        LoginResponse result = userService.isUserValid(email, rawPassword);
        assertEquals(result.getMessage(), expectedResult);
        verify(userRepository).findByEmail(email);
        verify(passwordEncoder).matches(rawPassword, encodedPassword);
    }

    @Test
    void testUserNotFound() {
        String email = "nonexistent@example.com";
        String password = "anyPassword";
        String expectedResult = "User not found";

        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        LoginResponse result = userService.isUserValid(email, password);
        assertEquals(result.getMessage(), expectedResult);
        verify(userRepository).findByEmail(email);
        verify(passwordEncoder, never()).matches(anyString(), anyString());
    }
}
