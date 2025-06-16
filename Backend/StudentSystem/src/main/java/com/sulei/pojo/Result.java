package com.sulei.pojo;

import com.sulei.common.ResultCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 统一返回结果
 * 用于封装接口返回的数据结构
 * @param <T> 返回数据类型
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> {
    /**
     * 业务状态码
     */
    private Integer code;
    /**
     * 提示信息
     */
    private String msg;
    /**
     * 数据
     */
    private T data;

    /**
     * 成功返回（带数据）
     * @param data 返回数据
     * @return 统一结果对象
     */
    public static <T> Result<T> success(T data) {
        return new Result<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), data);
    }

    /**
     * 成功返回（无数据）
     * @return 统一结果对象
     */
    public static Result success() {
        return new Result(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), null);
    }

    /**
     * 自定义错误返回
     * @param code 错误码
     * @param msg 错误信息
     * @return 统一结果对象
     */
    public static Result error(Integer code, String msg) {
        return new Result(code, msg, null);
    }

    /**
     * 通过ResultCode返回错误
     * @param resultCode 结果码枚举
     * @return 统一结果对象
     */
    public static Result error(ResultCode resultCode) {
        return new Result(resultCode.getCode(), resultCode.getMessage(), null);
    }
}
