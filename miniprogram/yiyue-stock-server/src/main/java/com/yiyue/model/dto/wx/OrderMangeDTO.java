package com.yiyue.model.dto.wx;

import com.yiyue.common.util.BasePageDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
*
* author: liyun
* create date: 2022-01-14
*/

@ApiModel(value="查询条件数据格式",description="所有都不是必须的")
@Data
public class OrderMangeDTO extends BasePageDTO{

    // 条件查询字段，需要自己定义添加或者修改
    @ApiModelProperty(value="序号")
    private String id;

    @ApiModelProperty(value="订单编号")
    private String orderId;

    @ApiModelProperty(value="用户")
    private String userId;

    @ApiModelProperty(value="图书馆id")
    private String libraryId;

    @ApiModelProperty(value="图书馆")
    private String libraryName;

    @ApiModelProperty(value="座位id")
    private String seatId;

    @ApiModelProperty(value="座位")
    private String seatName;

    @ApiModelProperty(value="订单状态")
    private String orderStatus;

    @ApiModelProperty(value="预约时间")
    private String planTime;

    @ApiModelProperty(value="结束时间")
    private String endTime;


    @ApiModelProperty(value="创建时间")
    private String createDate;

}
