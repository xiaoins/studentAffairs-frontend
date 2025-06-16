package com.sulei.service.impl;

import com.sulei.mapper.ActivityMapper;
import com.sulei.pojo.Activity;
import com.sulei.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    private ActivityMapper activityMapper;

    @Override
    public List<Activity> getAllActivities() {
        return activityMapper.getAllActivities();
    }

    @Override
    @Transactional
    public void addActivity(Activity activity) {
        activityMapper.addActivity(activity);
    }

    @Override
    @Transactional
    public void updateActivity(Activity activity) {
        activityMapper.updateActivity(activity);
    }

    @Override
    @Transactional
    public void deleteActivity(Integer id) {
        activityMapper.deleteActivity(id);
    }
} 