package com.yiyue.common.handler;


import com.yiyue.common.exception.CustomException;
import com.yiyue.common.result.Result;
import com.yiyue.common.util.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Author: XiaoQi
 * @Date: 2021/7/24 14:35
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    /**
     * 全局异常捕捉处理
     */
    @ExceptionHandler(value = Exception.class)
    public Result errorHandler(Exception ex) {
        log.error("exception:{}", ExceptionUtil.getStackTrace(ex));
        return Result.fail(ExceptionUtil.getStackTrace(ex));
    }

    /**
     * 自定义异常 CustomException.class
     */
    @ExceptionHandler(value = CustomException.class)
    public Result httpResult(CustomException ex) {
        log.error("CustomException:{}", ex.getMsg());
        return Result.fail(ex.getMsg());
    }
}
