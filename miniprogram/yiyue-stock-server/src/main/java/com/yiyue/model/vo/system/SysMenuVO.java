package com.yiyue.model.vo.system;

import lombok.Data;

import java.util.List;

@Data
public class SysMenuVO {

    private String menuId;
    private String menuName;
    private String menuUrl;
    private String menuComponent;
    private String menuIcon;
    private Integer menuState;
    private Double menuNo;
    private Integer menuLevel;

    private List<SysMenuVO> children;
}
