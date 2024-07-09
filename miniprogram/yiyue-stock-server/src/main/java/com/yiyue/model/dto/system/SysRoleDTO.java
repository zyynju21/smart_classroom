package com.yiyue.model.dto.system;

import com.yiyue.common.util.BasePageDTO;
import lombok.Data;

@Data
public class SysRoleDTO extends BasePageDTO {

    private String roleName;
    private String orgId;
    private String orgName;
}
