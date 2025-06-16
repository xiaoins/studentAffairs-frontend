package com.sulei.controller;

import com.sulei.pojo.Admin;
import com.sulei.pojo.Result;
import com.sulei.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/register")
    public Result register(@RequestBody Admin admin) {
        adminService.register(admin);
        return Result.success();
    }

    @PostMapping("/login")
    public Result login(@RequestBody Map<String, String> credentials) {
        String token = adminService.login(credentials.get("username"), credentials.get("password"));
        // In a real app, you'd return the token. For simplicity, we'll just return success.
        return Result.success();
    }
} 