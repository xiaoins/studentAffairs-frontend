package com.sulei.service;

import com.sulei.pojo.Student;

import java.util.List;

/**
 * 学生服务接口
 * 定义学生相关的业务操作
 */
public interface StudentService {
    /**
     * 获取所有学生信息
     * @return 学生列表
     */
    List<Student> getAllStudents();

    /**
     * 根据条件查询学生
     * @param name 姓名
     * @param studentId 学号
     * @return 学生列表
     */
    List<Student> list(String name, String studentId);

    /**
     * 新增学生
     * @param student 学生对象
     */
    void addStudent(Student student);

    /**
     * 删除学生
     * @param id 学生主键ID
     */
    void deleteStudent(Integer id);

    /**
     * 根据主键ID获取学生信息
     * @param id 学生主键ID
     * @return 学生对象
     */
    Student getById(Integer id);

    /**
     * 更新学生信息
     * @param student 学生对象
     */
    void updateStudent(Student student);

    /**
     * 根据学号（业务ID）查询学生信息
     * @param studentId 学号
     * @return 学生对象
     */
    Student findByStudentId(String studentId);

    Student findStudentById(Integer id);
}
