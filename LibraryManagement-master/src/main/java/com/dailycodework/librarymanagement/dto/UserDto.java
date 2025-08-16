package com.dailycodework.librarymanagement.dto;

import lombok.Data;
import java.util.Set;

@Data
public class UserDto {
    private Long id;
    private String username;
    private String password; // Note: Should be handled carefully, consider separate DTOs for create/update
    private String email;
    private String firstName;
    private String lastName;
    private String phone;
    private boolean active;
    private Set<String> roles; // Role names
}