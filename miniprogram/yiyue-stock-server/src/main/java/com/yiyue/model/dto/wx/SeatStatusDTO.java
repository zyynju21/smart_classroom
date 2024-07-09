package com.yiyue.model.dto.wx;

import com.yiyue.common.util.BasePageDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
* 
* author: liyun
* create date: 2022-01-13
*/

@ApiModel(value="查询条件数据格式",description="所有都不是必须的")
@Data
public class SeatStatusDTO extends BasePageDTO{

    // 条件查询字段，需要自己定义添加或者修改
    @ApiModelProperty(value="序号")
    private String id;

    @ApiModelProperty(value="状态（0空闲；1预约；2占用）")
    private String status;

    @ApiModelProperty(value="座位")
    private String seatName;

    @ApiModelProperty(value="图书馆")
    private String libraryType;

}