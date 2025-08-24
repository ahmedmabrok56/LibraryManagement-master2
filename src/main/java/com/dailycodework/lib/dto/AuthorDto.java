package com.dailycodework.lib.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class AuthorDto {
    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private LocalDate deathDate;
    private String nationality;
    private String biography;
}
