package com.sulei.service;

import com.sulei.pojo.Admin;

public interface AdminService {

    /**
     * Finds an admin by their username.
     * @param username The admin's username.
     * @return The Admin object or null if not found.
     */
    Admin findByUsername(String username);

    /**
     * Registers a new admin, hashing their password.
     * @param admin The admin object with plain text password.
     */
    void register(Admin admin);

    /**
     * Authenticates an admin and returns a token upon success.
     * @param username The admin's username.
     * @param password The admin's plain text password.
     * @return A JWT token or similar, or throws an exception on failure.
     */
    String login(String username, String password);
} 