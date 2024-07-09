package com.yiyue.model.dto.system;

import com.yiyue.common.util.BasePageDTO;
import lombok.Data;

@Data
public class SysUserDTO extends BasePageDTO {

    private String loginName;
    private String userName;
    private String userSex;
    private String phone;
    private String orgId;
    private String orgName;
}
