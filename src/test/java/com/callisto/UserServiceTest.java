package com.callisto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.callisto.dto.UserDto;
import com.callisto.exception.UserEmailNotFoundException;
import com.callisto.model.User;
import com.callisto.repository.UserRepository;
import com.callisto.service.UserService;
import com.callisto.model.Address;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService; // The service with the new update logic

    private User existingUser;
    private UserDto userUpdateDTO; // DTO that will be passed for update
    private UserDto userUpdateDTOMiddleNameEmpty; // DTO with empty middle name
    private UserDto userUpdateDTOMiddleNameNull; // DTO with null middle name
    private Address existingAddress;
    private Address updateAddress;

    @BeforeEach
    void setUp() {
        existingAddress = new Address("123 Old St", "Old City", "OC", "USA", "12345");
        existingUser = new User(1L, "OldFirstName", "OldMiddleName", "OldLastName", LocalDate.of(1980, 1, 1),
                "old.email@example.com", "9876543210", "oldPasswordHash", existingAddress, true);

        updateAddress = new Address("456 New Rd", "New City", "NC", "USA", "67890");

        // DTO with a new middle name
        userUpdateDTO = new UserDto("NewFirstName", "NewMiddleName", "NewLastName", LocalDate.of(1995, 2, 20),
                "old.email@example.com", "1234567890", updateAddress); // Email is key!

        // DTO with an empty middle name
        userUpdateDTOMiddleNameEmpty = new UserDto("NewFirstName", "", "NewLastName", LocalDate.of(1995, 2, 20),
                "old.email@example.com", "1234567890", updateAddress);

        // DTO with a null middle name
        userUpdateDTOMiddleNameNull = new UserDto("NewFirstName", null, "NewLastName", LocalDate.of(1995, 2, 20),
                "old.email@example.com", "1234567890", updateAddress);
    }

    @Test
    @DisplayName("Should update an existing user by email successfully with a new middle name")
    void success_updateUserDetails_test_withNewMiddleName() {
        // existing user with its initial state
        User userBeforeUpdate = new User(existingUser.getId(), existingUser.getFirstName(),
                existingUser.getMiddleName(), existingUser.getLastName(), existingUser.getDob(),
                existingUser.getEmail(), existingUser.getPhoneNumber(), existingUser.getPassword(),
                existingUser.getAddress(), existingUser.isTermsAccepted());

        // Mock the repository behavior
        when(userRepository.findByEmail(userUpdateDTO.getEmail())).thenReturn(Optional.of(userBeforeUpdate));
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0)); // Return the
                                                                                                        // saved entity

        // Call the service method
        userService.updateUserDetails(userUpdateDTO);

        // Capture the User object that was saved to verify its state
        verify(userRepository, times(1)).findByEmail(userUpdateDTO.getEmail());
        verify(userRepository, times(1)).save(any(User.class));

        assertEquals(userUpdateDTO.getFirstName(), userBeforeUpdate.getFirstName());
        assertEquals(userUpdateDTO.getMiddleName(), userBeforeUpdate.getMiddleName());
        assertEquals(userUpdateDTO.getLastName(), userBeforeUpdate.getLastName());
        assertEquals(userUpdateDTO.getEmail(), userBeforeUpdate.getEmail()); // Email remains the same as it's the
                                                                             // identifier
        assertEquals(userUpdateDTO.getPhoneNumber(), userBeforeUpdate.getPhoneNumber());
        assertEquals(userUpdateDTO.getDob(), userBeforeUpdate.getDob());
        assertEquals(userUpdateDTO.getAddress().getStreet(), userBeforeUpdate.getAddress().getStreet());
        assertEquals(userUpdateDTO.getAddress().getCity(), userBeforeUpdate.getAddress().getCity());
    }

    @Test
    @DisplayName("Should update an existing user by email, setting middle name to null if DTO is empty")
    void shouldUpdateExistingUserByEmail_middleNameEmpty() {
        User userBeforeUpdate = new User(existingUser.getId(), existingUser.getFirstName(), "SomeOldMiddle",
                existingUser.getLastName(), existingUser.getDob(), existingUser.getEmail(),
                existingUser.getPhoneNumber(), existingUser.getPassword(), existingUser.getAddress(),
                existingUser.isTermsAccepted());

        when(userRepository.findByEmail(userUpdateDTOMiddleNameEmpty.getEmail()))
                .thenReturn(Optional.of(userBeforeUpdate));
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        userService.updateUserDetails(userUpdateDTOMiddleNameEmpty);

        verify(userRepository, times(1)).findByEmail(userUpdateDTOMiddleNameEmpty.getEmail());
        verify(userRepository, times(1)).save(any(User.class));

        assertNull(userBeforeUpdate.getMiddleName()); // Should be null now
        assertEquals(userUpdateDTOMiddleNameEmpty.getFirstName(), userBeforeUpdate.getFirstName());
    }

    @Test
    @DisplayName("Should update an existing user by email, keeping middle name null if DTO is null")
    void shouldUpdateExistingUserByEmail_middleNameNull() {
        User userBeforeUpdate = new User(existingUser.getId(), existingUser.getFirstName(), null, // Already null
                existingUser.getLastName(), existingUser.getDob(), existingUser.getEmail(),
                existingUser.getPhoneNumber(), existingUser.getPassword(), existingUser.getAddress(),
                existingUser.isTermsAccepted());

        when(userRepository.findByEmail(userUpdateDTOMiddleNameNull.getEmail()))
                .thenReturn(Optional.of(userBeforeUpdate));
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        userService.updateUserDetails(userUpdateDTOMiddleNameNull);

        verify(userRepository, times(1)).findByEmail(userUpdateDTOMiddleNameNull.getEmail());
        verify(userRepository, times(1)).save(any(User.class));

        assertNull(userBeforeUpdate.getMiddleName()); // Should remain null
        assertEquals(userUpdateDTOMiddleNameNull.getFirstName(), userBeforeUpdate.getFirstName());
    }

    @Test
    @DisplayName("Should throw UserEmailNotFoundException when user email not found for update")
    void shouldThrowExceptionWhenUserEmailNotFoundForUpdate() {
        // Mock the repository behavior for email not found
        String nonExistentEmail = "non.existent@example.com";
        UserDto dtoWithNonExistentEmail = new UserDto("Test", null, "User", LocalDate.now(), nonExistentEmail,
                "1112223333", existingAddress);

        when(userRepository.findByEmail(nonExistentEmail)).thenReturn(Optional.empty());

        // Assert that UserEmailNotFoundException is thrown
        UserEmailNotFoundException exception = assertThrows(UserEmailNotFoundException.class, () -> {
            userService.updateUserDetails(dtoWithNonExistentEmail);
        });

        // Verify the exception message
        assertEquals("User Email not found.", exception.getMessage());

        // Verify that findByEmail was called once
        verify(userRepository, times(1)).findByEmail(nonExistentEmail);
        // Verify that save was never called
        verify(userRepository, never()).save(any(User.class));
    }

}
