package com.callisto.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "USER_OMT")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    @NotBlank
    @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;

    @Embedded
    private Address address;

    @AssertTrue(message = "You must accept terms and conditions")
    private boolean termsAccepted;

}
