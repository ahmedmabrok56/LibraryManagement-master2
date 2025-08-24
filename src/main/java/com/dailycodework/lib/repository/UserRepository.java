package com.dailycodework.lib.repository;

import com.dailycodework.lib.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    // optional , No null pointer exception
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);

    //SELECT * FROM users WHERE active = true;
    List<User> findByActiveTrue();


    @Query("SELECT u FROM User u JOIN u.roles r WHERE r.name = :roleName AND u.active = true")
    List<User> findByRoleName(@Param("roleName") String roleName);
}