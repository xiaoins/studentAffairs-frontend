package com.sulei.mapper;

import com.sulei.pojo.Student;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 学生数据访问层接口
 * 负责与数据库进行交互
 */
@Mapper
public interface StudentMapper {
    /**
     * 查询所有学生信息
     * @return 学生列表
     */
    List<Student> getAllStudents();

    /**
     * 新增学生信息
     * @param student 学生对象
     */
    void addStudent(Student student);

    /**
     * 更新学生信息
     * @param student 学生对象
     */
    void updateStudent(Student student);

    /**
     * 删除学生信息
     * @param id 学生ID
     */
    void deleteStudent(Integer id);

    List<Student> list(String name, String studentId);

    void add(Student student);

    void delete(Integer id);

    Student getById(Integer id);

    void update(Student student);

    Student findByStudentId(@Param("studentId") String studentId);

    Student findStudentById(Integer id);
}
