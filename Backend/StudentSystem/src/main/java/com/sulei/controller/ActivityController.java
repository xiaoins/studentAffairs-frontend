package com.sulei.controller;

import com.sulei.pojo.Activity;
import com.sulei.pojo.Result;
import com.sulei.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/activities")
public class ActivityController {

    @Autowired
    private ActivityService activityService;

    @GetMapping
    public Result<List<Activity>> getAllActivities() {
        List<Activity> activities = activityService.getAllActivities();
        return Result.success(activities);
    }

    @PostMapping
    public Result addActivity(@RequestBody Activity activity) {
        activityService.addActivity(activity);
        return Result.success();
    }

    @PutMapping("/{id}")
    public Result updateActivity(@PathVariable Integer id, @RequestBody Activity activity) {
        activity.setActiveId(id);
        activityService.updateActivity(activity);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result deleteActivity(@PathVariable Integer id) {
        activityService.deleteActivity(id);
        return Result.success();
    }
} 