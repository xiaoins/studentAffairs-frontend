package com.sulei.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 用于课程信息连接查询的视图对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseVo {
    // Course fields
    private Integer id;
    private String courseName;
    private String courseCode;
    private String teacherName;
    private BigDecimal credits;
} 