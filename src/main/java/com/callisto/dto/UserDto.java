package com.callisto.dto;

import java.time.LocalDate;

import com.callisto.model.Address;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    @NotBlank
    @Pattern(regexp = "^[A-Za-z]+$", message = "First name must contain only letters")
    private String firstName;

    @Pattern(regexp = "^[A-Za-z]*$", message = "Middle name must contain only letters")
    private String middleName;

    @Pattern(regexp = "^[A-Za-z]*$", message = "Last name must contain only letters")
    private String lastName;

    @NotNull
    @Past(message = "DOB must be in the past")
    private LocalDate dob;

    @NotBlank
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank
    @Pattern(regexp = "^[6-9]\\d{9}$", message = "Invalid phone number")
    private String phoneNumber;

    private Address address;
}
