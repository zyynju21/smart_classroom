package com.yiyue.model.dto.system;

import com.yiyue.common.util.BasePageDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * @Author: XiaoQi
 * @Date: 2021/7/17 21:30
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SystemLogPageDTO extends BasePageDTO {

    private Integer logType;

    private Integer status;

    private String operator;

    private String startDate;

    private String endDate;
}
