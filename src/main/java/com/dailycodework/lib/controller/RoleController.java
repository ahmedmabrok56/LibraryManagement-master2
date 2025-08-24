package com.dailycodework.lib.controller;


import com.dailycodework.lib.dto.RoleDto;
import com.dailycodework.lib.entity.Role;
import com.dailycodework.lib.mapper.RoleMapper;
import com.dailycodework.lib.service.IRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
public class RoleController {

    private final IRoleService roleService;
    private final RoleMapper roleMapper;

    @GetMapping
    public ResponseEntity<List<RoleDto>> getAllRoles() {
        List<RoleDto> dtos = roleService.findAllRoles()
                .stream()
                .map(roleMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleDto> getRoleById(@PathVariable("id") Long id) {
        return roleService.findById(id)
                .map(roleMapper::toDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<RoleDto> getRoleByName(@PathVariable("name") String name) {
        return roleService.findByName(name)
                .map(roleMapper::toDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasRole('LIBRARIAN') or hasRole('ADMIN')")
    public ResponseEntity<RoleDto> createRole(@RequestBody RoleDto roleDto) {
        Role role = roleService.save(roleMapper.toEntity(roleDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(roleMapper.toDto(role));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoleDto> updateRole(@PathVariable("id") Long id, @RequestBody RoleDto roleDto) {
        Role role = roleMapper.toEntity(roleDto);
        role.setId(id);
        Role updated = roleService.update(id, role);
        return ResponseEntity.ok(roleMapper.toDto(updated));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteRole(@PathVariable("id") Long id) {
        roleService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}