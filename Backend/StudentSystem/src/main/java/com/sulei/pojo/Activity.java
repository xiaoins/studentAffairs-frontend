package com.sulei.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 活动实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Activity {
    /** 活动ID */
    private Integer activeId;

    /** 活动名称 */
    private String activityName;

    /** 发布日期 */
    private LocalDateTime publishDate;

    /** 结束日期 */
    private LocalDateTime endDate;

    /** 学生评价 */
    private String studentReview;
} 