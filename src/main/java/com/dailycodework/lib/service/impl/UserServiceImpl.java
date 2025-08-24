package com.dailycodework.lib.service.impl;

import com.dailycodework.lib.entity.User;
import com.dailycodework.lib.repository.UserRepository;
import com.dailycodework.lib.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements IUserService {
    private final UserRepository userRepository;
    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<User> findActiveUsers() {
        return userRepository.findByActiveTrue();
    }

    @Override
    public List<User> findByRoleName(String roleName) {
        return userRepository.findByRoleName(roleName);
    }

    @Override
    @Transactional
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public User update(Long id, User userDetails) {
        User user = userRepository.findById(id)
                .orElseThrow(() ->new RuntimeException("User not found with id "+id));
        BeanUtils.copyProperties(userDetails , user ,"id");

        return userRepository.save(user);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() ->new RuntimeException("User not found with id "+id));
        userRepository.deleteById(id);
    }
}
