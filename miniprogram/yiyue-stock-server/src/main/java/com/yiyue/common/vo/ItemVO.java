package com.yiyue.common.vo;

import lombok.Data;

import java.util.List;

@Data
public class ItemVO {

    private String key;
    private String title;
    private String value;
    private List<ItemVO> children;
}
