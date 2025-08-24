package com.dailycodework.lib.service;

import com.dailycodework.lib.entity.Role;

import java.util.List;
import java.util.Optional;

public interface IRoleService {
    List<Role> findAllRoles();
    Optional<Role> findById(Long id);
    Optional<Role> findByName(String name);
    Role save(Role role);
    Role update(Long id , Role roleDetails);
    void deleteById(Long id);


}
