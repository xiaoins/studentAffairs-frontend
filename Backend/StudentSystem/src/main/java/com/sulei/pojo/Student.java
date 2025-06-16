package com.sulei.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 学生实体类
 * 用于封装学生的基本信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    /** 学生ID */
    private Integer id;
    /** 学号 */
    private String studentId;
    /** 学生姓名 */
    private String name;
    /** 学生性别 */
    private String gender;
    /** 学院名称 */
    private String collegeName;
    /** 专业名称 */
    private String majorName;
    /** 班级名称 */
    private String className;
    /** 入学时间 */
    private LocalDate enrollmentDate;
    /** 毕业时间 */
    private LocalDate graduationDate;
}
