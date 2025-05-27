package com.callisto.userServiceTest;

import com.callisto.Constant.ErrorConstants;
import com.callisto.Constant.ExceptionConstants;
import com.callisto.exception.FirstNameNotFoundException;
import com.callisto.exception.UserEmailExitsException;
import com.callisto.model.ErrorResponse;
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

import java.time.LocalDateTime;

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

        assertThrows(UserEmailExitsException.class, () -> userService.saveUser(validUser));
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

    @Test
    void testSaveUser_EmailAlreadyExists_ThrowsException() {
        User user = new User();
        user.setEmail("kumarsuraj1@gmail.com");

        when(userRepository.existsByEmail(user.getEmail())).thenReturn(true);

        UserEmailExitsException exception = assertThrows(UserEmailExitsException.class, () -> {
            userService.saveUser(user);
        });

        assertEquals(ExceptionConstants.emailAlreadyExits, exception.getMessage());
        verify(userRepository, never()).save(any());
    }

    @Test
    void testSaveUser_FirstNameNull_ThrowsException() {
        User user = new User();
        user.setEmail("kumarsuraj1@gmail.com");
        user.setFirstName(null);

        when(userRepository.existsByEmail(user.getEmail())).thenReturn(false);

        FirstNameNotFoundException exception = assertThrows(FirstNameNotFoundException.class, () -> {
            userService.saveUser(user);
        });

        assertEquals(ExceptionConstants.firstNameNotFound, exception.getMessage());
        verify(userRepository, never()).save(any());
    }

    @Test
    void testSaveUser_LastNameNull_SetsLastNameToFirstNameAndSaves() {
        User user = new User();
        user.setEmail("kumarsuraj1@gmail.com");
        user.setFirstName("S");
        user.setLastName(null);
        user.setPassword("rawPassword");

        when(userRepository.existsByEmail(user.getEmail())).thenReturn(false);
        when(passwordEncoder.encode("rawPassword")).thenReturn("encodedPassword");

        userService.saveUser(user);

        assertEquals("S", user.getLastName());
        assertEquals("encodedPassword", user.getPassword());

        verify(userRepository).save(user);
    }

    @Test
    void testSaveUser_AllValid_SavesUser() {
        User user = new User();
        user.setEmail("kumarsuraj1@gmail.com");
        user.setFirstName("S");
        user.setLastName("");
        user.setPassword("rawPassword");

        when(userRepository.existsByEmail(user.getEmail())).thenReturn(false);
        when(passwordEncoder.encode("rawPassword")).thenReturn("encodedPassword");

        userService.saveUser(user);

        assertEquals("S", user.getLastName());
        assertEquals("encodedPassword", user.getPassword());

        verify(userRepository).save(user);
    }
    @Test
    public void testAllArgsConstructorAndGetters() {
        LocalDateTime now = LocalDateTime.now();
        ErrorResponse errorResponse = new ErrorResponse(now, 404, ErrorConstants.EMAIL_ALREADY_REGISTERD, "Resource missing", "/api/resource");

        assertEquals(now, errorResponse.getTimestamp());
        assertEquals(404, errorResponse.getStatus());
        assertEquals(ErrorConstants.EMAIL_ALREADY_REGISTERD, errorResponse.getErrors());
        assertEquals("Resource missing", errorResponse.getMessage());
        assertEquals("/api/resource", errorResponse.getPath());
    }

    @Test
    public void testNoArgsConstructorAndSetters() {
        ErrorResponse errorResponse = new ErrorResponse();

        LocalDateTime now = LocalDateTime.now();
        errorResponse.setTimestamp(now);
        errorResponse.setStatus(500);
        errorResponse.setErrors(ErrorConstants.FIRST_NAME_NOTFOUND);
        errorResponse.setMessage("Unexpected error");
        errorResponse.setPath("/api/error");

        assertEquals(now, errorResponse.getTimestamp());
        assertEquals(500, errorResponse.getStatus());
        assertEquals(ErrorConstants.FIRST_NAME_NOTFOUND, errorResponse.getErrors());
        assertEquals("Unexpected error", errorResponse.getMessage());
        assertEquals("/api/error", errorResponse.getPath());
    }

}
