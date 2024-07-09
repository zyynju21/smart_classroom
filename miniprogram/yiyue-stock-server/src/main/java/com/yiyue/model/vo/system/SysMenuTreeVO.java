package com.yiyue.model.vo.system;

import lombok.Data;

import java.util.List;

@Data
public class SysMenuTreeVO {

    private String key;
    private String value;
    private String title;
    private Integer menuLevel;

    private List<SysMenuTreeVO> children;
}
