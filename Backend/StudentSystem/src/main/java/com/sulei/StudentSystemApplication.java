package com.sulei;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Spring Boot 应用程序入口类
 * 该类用于启动整个学生管理系统后端服务
 */
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
@EnableTransactionManagement // 启用注解式事务管理
public class StudentSystemApplication {

    /**
     * 主方法，程序入口
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        SpringApplication.run(StudentSystemApplication.class, args); // 启动Spring Boot应用
    }

}
