package com.yiyue.model.vo.system;

import lombok.Data;

@Data
public class UserMenuNavMeta {

    private String icon;
    private String title;
    private boolean show;
    private boolean hideChildren;
    private String selectedKeys;
}
