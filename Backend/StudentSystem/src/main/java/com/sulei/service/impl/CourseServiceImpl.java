package com.sulei.service.impl;

import com.sulei.mapper.CourseMapper;
import com.sulei.pojo.Course;
import com.sulei.pojo.CourseVo;
import com.sulei.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseMapper courseMapper;

    @Override
    public List<CourseVo> getCourseList(String query) {
        return courseMapper.getCourseList(query);
    }

    @Override
    public void add(Course course) {
        courseMapper.add(course);
    }

    @Override
    public void delete(Integer id) {
        courseMapper.delete(id);
    }

    @Override
    public void update(Course course) {
        courseMapper.update(course);
    }
} 