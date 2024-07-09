package com.yiyue.common.util;

import lombok.Data;

/**
 * @Author: XiaoQi
 * @Date: 2019/1/10 15:30
 */
@Data
public class BasePageDTO {

    /**
     * 行数 10
     */
    private Integer size;

    /**
     * 页码 1
     */
    private Integer current;

    /**
     * 排序列
     */
    private String sortField;

    /**
     * 排序规则 asc desc
     */
    private String sortOrder;
}

