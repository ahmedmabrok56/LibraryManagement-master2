package com.dailycodework.librarymanagement.service;

import com.dailycodework.librarymanagement.entity.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {

    List<User> findAllUsers();

    Optional<User> findById(Long id);

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    List<User> findActiveUsers();

    List<User> findByRoleName(String roleName);

    User save(User user);

    User update(Long id, User userDetails);

    void deleteById(Long id);

}
