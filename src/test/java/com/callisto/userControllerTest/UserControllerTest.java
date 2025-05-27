package com.callisto.userControllerTest;

import com.callisto.Constant.LogMessages;
import com.callisto.controller.UserController;
import com.callisto.dto.CustomerProfileDTO;

import com.callisto.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;
    private CustomerProfileDTO profile;

    @BeforeEach
    void setUp() {

        profile = new CustomerProfileDTO("Steve N Jose", LocalDate.of(1999, 3, 3), "MG Road, 400001, Mumbai-India",
                LogMessages.language, LogMessages.status, "test@example.com", "9999999999");

    }

    @Test

    void testGetCustomerProfile_ReturnsProfile() {

        String email = "test@example.com";
        when(userService.getCustomerProfileByEmail(email)).thenReturn(profile);
        ResponseEntity<CustomerProfileDTO> response = userController.getCustomerProfile(email);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(profile.getFullName(), response.getBody().getFullName());
        assertEquals(profile.getEmail(), response.getBody().getEmail());
        assertEquals(profile.getStatus(), response.getBody().getStatus());

    }

}
