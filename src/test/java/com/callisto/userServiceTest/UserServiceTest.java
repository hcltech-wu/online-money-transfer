package com.callisto.userServiceTest;

import com.callisto.Constant.LogMessages;
import com.callisto.dto.CustomerProfileDTO;
import com.callisto.exception.ResourceNotFoundException;
import com.callisto.model.Address;
import com.callisto.model.User;
import com.callisto.repository.UserRepository;
import com.callisto.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void TestGetCustomerProfileByEmail_success() {
        String email = "sankeb@gmail.com";

        User user = new User();
        user.setFirstName("steve");
        user.setMiddleName("n");
        user.setLastName("jose");
        user.setDob(LocalDate.of(1999, 1, 2));
        user.setEmail(email);
        user.setPhoneNumber("9876543210");

        Address address = new Address();
        address.setStreet("MG Road");
        address.setPin("400001");
        address.setCity("Pune");
        address.setCountry("India");
        user.setAddress(address);

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        CustomerProfileDTO profile = userService.getCustomerProfileByEmail(email);

        assertEquals("steve n jose", profile.getFullName());
        assertEquals(email, profile.getEmail());
        assertEquals((LogMessages.status), profile.getStatus());
        assertEquals(LogMessages.language, profile.getLanguage());
        assertEquals("MG Road,400001,Pune-India", profile.getAddress());
        assertEquals("9876543210", profile.getMobile());

    }

    @Test
    void testGetCustomerByProfileByEmail_ThrowException_WhenNotFound() {
        String email = "notThere@gmail.com";
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.getCustomerProfileByEmail(email));
    }
}
