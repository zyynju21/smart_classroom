package com.yiyue.common.exception;

import lombok.Getter;

/**
 * @Author: XiaoQi
 * @Date: 2020/4/29 13:52
 */
@Getter
public enum SystemErrorType implements ErrorType {
    /**
     * 系统异常返回的错误码
     */
    SYSTEM_ERROR("400", "system error.");

    /**
     * 错误类型码
     */
    private String code;

    /**
     * 错误类型描述信息
     */
    private String msg;

    SystemErrorType(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
