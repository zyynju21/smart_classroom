package com.yiyue.common.enums;

import lombok.Getter;

/**
 * @Author: XiaoQi
 * @Date: 2020/11/18 15:54
 */
@Getter
public enum LogTypeEnum {

    /**
     * 系统
     */
    SYSTEM(1, "系统");

    private Integer logTypeId;
    private String logTypeValue;

    LogTypeEnum(Integer logTypeId, String logTypeValue) {
        this.logTypeId = logTypeId;
        this.logTypeValue = logTypeValue;
    }
    }
