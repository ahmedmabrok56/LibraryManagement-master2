package com.dailycodework.lib.service.impl;

import com.dailycodework.lib.entity.Role;
import com.dailycodework.lib.repository.RoleRepository;
import com.dailycodework.lib.service.IRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RoleServiceImpl implements IRoleService {
    private final RoleRepository roleRepository;
    @Override
    public List<Role> findAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public Optional<Role> findById(Long id) {
        return roleRepository.findById(id);
    }

    @Override
    public Optional<Role> findByName(String name) {
        return roleRepository.findByName(name);
    }

    @Override
    @Transactional
    public Role save(Role role) {
        return roleRepository.save(role);
    }

    @Override
    @Transactional
    public Role update(Long id, Role roleDetails) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Role not found with id "+id));
        BeanUtils.copyProperties(roleDetails,role,"id");
        return roleRepository.save(role);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Role not found with id "+id));
        roleRepository.delete(role);
    }
}
