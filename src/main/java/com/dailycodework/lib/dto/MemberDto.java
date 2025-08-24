package com.dailycodework.lib.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class MemberDto {
    private Long id;
    private String membershipNumber;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String address;
    private LocalDate dateOfBirth;
    private LocalDate membershipDate;
    private LocalDate membershipExpiry;
    private boolean active;
}
