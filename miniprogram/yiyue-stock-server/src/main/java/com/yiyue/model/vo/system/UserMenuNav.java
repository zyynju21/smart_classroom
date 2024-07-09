package com.yiyue.model.vo.system;

import lombok.Data;

@Data
public class UserMenuNav {

    private String id;
    private String parentId;
    private String name;
    private String component;
    private String path;

    private UserMenuNavMeta meta;
}
