package com.sulei.mapper;

import com.sulei.pojo.Admin;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface AdminMapper {

    /**
     * Finds an admin by their username.
     * @param username The username to search for.
     * @return The Admin object or null if not found.
     */
    @Select("SELECT * FROM admin WHERE username = #{username}")
    Admin findByUsername(String username);

    /**
     * Inserts a new admin into the database.
     * @param admin The admin object to insert.
     */
    @Insert("INSERT INTO admin(username, password) VALUES(#{username}, #{password})")
    void insert(Admin admin);
} 