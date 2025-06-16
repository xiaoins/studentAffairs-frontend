package com.sulei.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 用于成绩信息连接查询的视图对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScoreVo {
    // Score fields
    private Integer id;
    private BigDecimal score;

    // Student fields
    private String studentId;
    private String studentName;
    private String className;

    // Course fields
    private Integer courseId;
    private String courseName;
    private String teacherName;
} 