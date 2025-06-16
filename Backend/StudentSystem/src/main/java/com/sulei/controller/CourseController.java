package com.sulei.controller;

import com.sulei.pojo.Course;
import com.sulei.pojo.CourseVo;
import com.sulei.pojo.Result;
import com.sulei.service.CourseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/courses")
public class CourseController {

    private static final Logger log = LoggerFactory.getLogger(CourseController.class);

    @Autowired
    private CourseService courseService;

    @GetMapping
    public Result<List<CourseVo>> getCourseList(@RequestParam(required = false) String query) {
        List<CourseVo> courseList = courseService.getCourseList(query);
        return Result.success(courseList);
    }

    @PostMapping
    public Result<?> addCourse(@RequestBody Course course) {
        try {
            courseService.add(course);
            return Result.success();
        } catch (Exception e) {
            log.error("Error adding course: ", e);
            return Result.error(500, "添加课程失败: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public Result<?> deleteCourse(@PathVariable Integer id) {
        try {
            courseService.delete(id);
            return Result.success();
        } catch (Exception e) {
            log.error("Error deleting course with id {}: ", id, e);
            return Result.error(500, "删除课程失败: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public Result<?> updateCourse(@PathVariable Integer id, @RequestBody Course course) {
        try {
            course.setId(id);
            courseService.update(course);
            return Result.success();
        } catch (Exception e) {
            log.error("Error updating course: ", e);
            return Result.error(500, "更新课程失败: " + e.getMessage());
        }
    }
} 