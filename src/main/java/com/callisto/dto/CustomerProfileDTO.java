package com.callisto.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CustomerProfileDTO {
    private String fullName;
    private LocalDate dob;
    private String address;
    private String language;
    private String status;
    private String email;
    private String mobile;

}
