package com.yiyue.common.result;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;

import java.time.Instant;
import java.time.ZonedDateTime;

/**
 * @Author: XiaoQi
 * @Date: 2020/4/29 13:49
 */
@ApiModel(description = "rest请求的返回模型，所有rest正常都返回该类的对象")
@Data
public class Result<T> {

    public static final String SUCCESSFUL_CODE = HttpStatus.SUCCESS;
    public static final String SUCCESSFUL_MSG = "Success";

    @ApiModelProperty(value = "处理结果code", required = true)
    private String code;

    @ApiModelProperty(value = "处理结果描述信息")
    private String msg;

    @ApiModelProperty(value = "请求结果生成时间戳")
    private Instant time;

    @ApiModelProperty(value = "处理结果数据信息")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    @ApiModelProperty(value = "处理结果List总数信息")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer count;

    /**
     * 初始化
     */
    public Result() {
        this.time = ZonedDateTime.now().toInstant();
    }

    /**
     * 内部使用，用于构造成功的结果
     *
     * @param code
     * @param msg
     * @param data
     */
    private Result(String code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.time = ZonedDateTime.now().toInstant();
    }

    /**
     * 内部使用，用于构造成功的结果
     *
     * @param code
     * @param msg
     * @param data
     */
    private Result(String code, String msg, Integer count, T data) {
        this.code = code;
        this.msg = msg;
        this.count = count;
        this.data = data;
        this.time = ZonedDateTime.now().toInstant();
    }

    /**
     * 快速创建成功结果并返回结果数据
     *
     * @param data
     * @return Result
     */
    public static Result success(Object data) {
        return new Result<>(SUCCESSFUL_CODE, SUCCESSFUL_MSG, data);
    }


    /**
     * 快速创建成功结果并返回结果数据
     *
     * @param data
     * @return Result
     */
    public static Result success(String msg, Object data) {
        return new Result<>(SUCCESSFUL_CODE, msg, data);
    }

    /**
     * 快速创建成功结果
     *
     * @return Result
     */
    public static Result success() {
        return success(null);
    }

    /**
     * 快速创建成功结果并返回结果数据(表格)
     *
     * @param count
     * @param data
     * @return
     */
    public static Result success(Integer count, Object data) {
        return new Result<>(SUCCESSFUL_CODE, SUCCESSFUL_MSG, count, data);
    }


    /**
     * 系统异常类没有返回数据
     *
     * @return Result
     */
    public static Result fail(String msg) {
        return new Result(HttpStatus.SERVER_EXCEPTION, msg, null);
    }

    /**
     * 系统异常类并返回结果数据
     *
     * @param data
     * @param msg
     * @return
     */
    public static Result fail(Object data, String msg) {
        return new Result<>(HttpStatus.SERVER_EXCEPTION, msg, data);
    }

    /**
     * 系统异常类并没有返回数据
     *
     * @param code
     * @param msg
     * @return
     */
    public static Result fail(String code, String msg) {
        return new Result<>(code, msg, null);
    }

    /**
     * Excel导入表格返回结果
     *
     * @param code
     * @param msg
     * @param data
     * @return Result
     */
    public static Result success(String code, String msg, Object data) {
        return new Result<>(code, msg, data);
    }
}
