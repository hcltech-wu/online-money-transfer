package com.callisto.userServiceTest;

import com.callisto.exception.FirstNameNotFoundException;
import com.callisto.exception.UserEmailNotFoundException;
import com.callisto.model.User;
import com.callisto.repository.UserRepository;
import com.callisto.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserRegisterServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private User validUser;

    @BeforeEach
    void setUp() {
        validUser = new User();
        validUser.setEmail("kumarsuraj1@gmail.com");
        validUser.setFirstName("suraj");
        validUser.setLastName("kumar");
        validUser.setPassword("password123");
    }

    @Test
    void saveUser_successfullySavesUser() {
        when(userRepository.existsByEmail(validUser.getEmail())).thenReturn(false);
        when(passwordEncoder.encode(validUser.getPassword())).thenReturn("encodedPassword");

        userService.saveUser(validUser);

        assertEquals("suraj", validUser.getFirstName());
        assertEquals("kumar", validUser.getLastName());
        assertEquals("encodedPassword", validUser.getPassword());
        verify(userRepository).save(validUser);
    }

    @Test
    void saveUser_throwsWhenEmailAlreadyExists() {
        when(userRepository.existsByEmail(validUser.getEmail())).thenReturn(true);

        assertThrows(UserEmailNotFoundException.class, () -> userService.saveUser(validUser));
        verify(userRepository, never()).save(any());
    }

    @Test
    void saveUser_throwsWhenFirstNameIsBlank() {
        validUser.setFirstName(" ");

        assertThrows(FirstNameNotFoundException.class, () -> userService.saveUser(validUser));
        verify(userRepository, never()).save(any());
    }

    @Test
    void saveUser_setsLastNameIfBlank() {
        validUser.setLastName(" ");
        when(userRepository.existsByEmail(validUser.getEmail())).thenReturn(false);
        when(passwordEncoder.encode(validUser.getPassword())).thenReturn("encodedPassword");

        userService.saveUser(validUser);

        assertEquals(validUser.getFirstName(), validUser.getLastName());
        verify(userRepository).save(validUser);
    }
}
