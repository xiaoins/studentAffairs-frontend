package com.sulei.service.impl;

import com.sulei.mapper.AdminMapper;
import com.sulei.pojo.Admin;
import com.sulei.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Admin findByUsername(String username) {
        return adminMapper.findByUsername(username);
    }

    @Override
    public void register(Admin admin) {
        // Check if username already exists
        Assert.isNull(adminMapper.findByUsername(admin.getUsername()), "Username already exists");
        // Hash the password before saving
        String hashedPassword = passwordEncoder.encode(admin.getPassword());
        admin.setPassword(hashedPassword);
        adminMapper.insert(admin);
    }

    @Override
    public String login(String username, String password) {
        Admin admin = adminMapper.findByUsername(username);
        Assert.notNull(admin, "User not found");

        // Check if the provided password matches the stored hash
        if (!passwordEncoder.matches(password, admin.getPassword())) {
            throw new IllegalArgumentException("Invalid password");
        }

        // Login successful. In a real application, generate and return a JWT token here.
        return "login_success_token";
    }
} 