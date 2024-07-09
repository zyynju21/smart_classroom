package com.yiyue.model.bean.system;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author XiaoQi
 * @since 2021-07-24
 */
@Data
@TableName("system_log")
public class SystemLog {

    private static final long serialVersionUID = 1L;

    @TableId(value = "system_log_id", type = IdType.ASSIGN_ID)
    private String systemLogId;

    private String controllerName;

    private String methodName;

    private byte[] paras;

    private byte[] result;

    private Integer logType;

    private Integer status;

    private Long responseTime;

    private String clientIp;

    private String clientSystem;

    private String clientBrowser;

    private String serverIp;
    private String createdBy;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createdGmt;

}
