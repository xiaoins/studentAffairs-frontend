package com.sulei.common;

import lombok.Getter;

/**
 * 结果码枚举
 * 定义常用的业务状态码及其含义
 */
@Getter
public enum ResultCode {
    /** 成功 */
    SUCCESS(200, "成功"),
    /** 参数错误 */
    BAD_REQUEST(400, "参数错误"),
    /** 未授权 */
    UNAUTHORIZED(401, "未授权"),
    /** 未找到 */
    NOT_FOUND(404, "未找到"),
    /** 系统内部错误 */
    INTERNAL_SERVER_ERROR(500, "系统内部错误");

    /** 状态码 */
    private final int code;
    /** 状态描述 */
    private final String message;

    ResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
} 