package com.yiyue.model.dto.system;

import lombok.Data;

import java.util.List;

@Data
public class SysRoleMenuDTO {
    private String roleId;
    private List<String> menuIds;
}
