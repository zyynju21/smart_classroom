package com.yiyue.model.dto.system;

import com.yiyue.common.util.BasePageDTO;
import lombok.Data;

@Data
public class SysOrganDTO extends BasePageDTO {

    private String orgName;
    private String orgPerson;
    private String orgPhone;
}
