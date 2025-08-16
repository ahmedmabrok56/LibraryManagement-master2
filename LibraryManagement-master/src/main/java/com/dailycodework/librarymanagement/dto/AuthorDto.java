package com.dailycodework.librarymanagement.dto;

import com.dailycodework.librarymanagement.entity.Author;
import lombok.Data;
import java.time.LocalDate;
import java.util.Set;

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