package com.callisto.model;

import jakarta.persistence.Embeddable;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Embeddable
@Data
public class Address {

    @NotBlank
    private String street;

    @NotBlank
    private String city;

    @NotBlank
    private String state;

    @NotBlank
    private String country;

    @Pattern(regexp = "^[1-9][0-9]{5}$", message = "Invalid PIN code")
    private String pin;
}
