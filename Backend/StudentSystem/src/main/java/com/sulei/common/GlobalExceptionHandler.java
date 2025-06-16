package com.sulei.common;

import com.sulei.pojo.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 * 用于统一处理系统中的异常
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理所有异常
     * @param e 异常对象
     * @return 统一错误返回结果
     */
    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e) {
        e.printStackTrace();
        return Result.error(ResultCode.INTERNAL_SERVER_ERROR.getCode(), "系统内部错误：" + e.getMessage());
    }
} 