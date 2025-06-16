package com.sulei.mapper;

import com.sulei.pojo.Course;
import com.sulei.pojo.CourseVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CourseMapper {
    List<CourseVo> getCourseList(@Param("query") String query);

    int add(Course course);

    int delete(Integer id);

    int update(Course course);
} 