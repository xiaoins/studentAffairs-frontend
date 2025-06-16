package com.sulei.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Score {
    private Integer id;
    private BigDecimal score;
    private String studentId;
    private Integer courseId;
} 