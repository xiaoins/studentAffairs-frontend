package com.sulei.controller;

import com.sulei.pojo.Result;
import com.sulei.pojo.Score;
import com.sulei.pojo.ScoreVo;
import com.sulei.pojo.Student;
import com.sulei.service.ScoreService;
import com.sulei.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/scores")
public class ScoreController {

    private static final Logger log = LoggerFactory.getLogger(ScoreController.class);

    @Autowired
    private ScoreService scoreService;

    @Autowired
    private StudentService studentService;

    @GetMapping
    public Result<List<ScoreVo>> getScoreList(@RequestParam(required = false) String name) {
        List<ScoreVo> scoreList = scoreService.getScoreList(name);
        return Result.success(scoreList);
    }

    @PostMapping
    public Result<?> addScore(@RequestBody Score score) {
        try {
            Integer studentPkId = Integer.parseInt(score.getStudentId());
            Student student = studentService.findStudentById(studentPkId);

            if (student == null) {
                return Result.error(404, "未找到ID为 " + studentPkId + " 的学生");
            }
            score.setStudentId(student.getStudentId());
            scoreService.add(score);
            return Result.success();
        } catch (NumberFormatException e) {
            log.error("Error parsing studentId: ", e);
            return Result.error(400, "学生ID格式不正确，无法完成成绩录入。");
        } catch (Exception e) {
            log.error("Error adding score: ", e);
            return Result.error(500, "录入成绩失败: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public Result<?> deleteScore(@PathVariable Integer id) {
        try {
            scoreService.delete(id);
            return Result.success();
        } catch (Exception e) {
            log.error("Error deleting score with id {}: ", id, e);
            return Result.error(500, "删除成绩失败: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public Result<?> updateScore(@PathVariable Integer id, @RequestBody Score score) {
        try {
            score.setId(id);
            Integer studentPkId = Integer.parseInt(score.getStudentId());
            Student student = studentService.findStudentById(studentPkId);

            if (student == null) {
                return Result.error(404, "更新失败：未找到ID为 " + studentPkId + " 的学生");
            }
            score.setStudentId(student.getStudentId());
            scoreService.update(score);
            return Result.success();
        } catch (NumberFormatException e) {
            log.error("Error parsing studentId for update: ", e);
            return Result.error(400, "学生ID格式不正确，无法完成成绩更新。");
        } catch (Exception e) {
            log.error("Error updating score with id {}: ", id, e);
            return Result.error(500, "更新成绩失败: " + e.getMessage());
        }
    }
} 