package com.yiyue.common.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author: XiaoQi
 * @Date: 2020/9/10 14:53
 */
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class CustomException extends RuntimeException {

    /**
     * 状态码
     */
    private String code;

    /**
     * 信息
     */
    private String msg;


    public CustomException(String msg) {
        this.code = SystemErrorType.SYSTEM_ERROR.getCode();
        this.msg = msg;
    }

}
