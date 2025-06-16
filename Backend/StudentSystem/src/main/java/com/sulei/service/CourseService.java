package com.sulei.service;

import com.sulei.pojo.Course;
import com.sulei.pojo.CourseVo;

import java.util.List;

public interface CourseService {
    List<CourseVo> getCourseList(String query);

    void add(Course course);

    void delete(Integer id);

    void update(Course course);
} 