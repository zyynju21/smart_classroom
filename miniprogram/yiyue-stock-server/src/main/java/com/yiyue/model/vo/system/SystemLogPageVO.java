package com.yiyue.model.vo.system;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author XiaoQi
 * @since 2020-08-10
 */
@Data
public class SystemLogPageVO {

    private String systemLogId;

    private String controllerName;

    private String methodName;

    private String paras;

    private String result;

    private Integer logType;

    private Integer status;

    private Long responseTime;

    private String clientIp;

    private String clientSystem;

    private String clientBrowser;

    private String serverIp;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createdGmt;

    private String operator;
}
