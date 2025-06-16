package com.sulei.service;

import com.sulei.pojo.Activity;
import java.util.List;

/**
 * 活动业务逻辑接口
 */
public interface ActivityService {
    List<Activity> getAllActivities();
    void addActivity(Activity activity);
    void updateActivity(Activity activity);
    void deleteActivity(Integer id);
} 