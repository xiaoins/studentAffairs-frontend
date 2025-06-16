package com.sulei.controller;

import com.sulei.common.ResultCode;
import com.sulei.pojo.Result;
import com.sulei.pojo.Student;
import com.sulei.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 学生控制器
 * 负责处理与学生相关的HTTP请求
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    /**
     * 获取所有学生信息
     * @return 学生列表的统一返回结果
     */
    @GetMapping
    public Result<List<Student>> getAllStudents() {
        List<Student> students = studentService.getAllStudents();
        return Result.success(students);
    }

    /**
     * 新增学生信息
     * @param student 前端传递的学生对象
     * @return 操作结果
     */
    @PostMapping
    public Result addStudent(@RequestBody Student student) {
        if (student.getName() == null || student.getGender() == null || student.getCollegeName() == null || student.getMajorName() == null || student.getClassName() == null) {
            return Result.error(ResultCode.BAD_REQUEST);
        }
        studentService.addStudent(student);
        return Result.success();
    }

    /**
     * 更新学生信息
     * @param id 学生ID
     * @param student 修改后的学生对象
     * @return 操作结果
     */
    @PutMapping("/{id}")
    public Result updateStudent(
            @PathVariable Integer id,
            @RequestBody Student student) {
        student.setId(id);
        studentService.updateStudent(student);
        return Result.success();
    }

    /**
     * 删除学生信息
     * @param id 学生ID
     * @return 操作结果
     */
    @DeleteMapping("/{id}")
    public Result deleteStudent(@PathVariable Integer id) {
        studentService.deleteStudent(id);
        return Result.success();
    }
}
