package com.yiyue.model.dto.system;

import lombok.Data;

import java.util.List;

@Data
public class SysUserRoleDTO {

    private String userId;
    private List<String> roleIds;

}
