package com.yiyue.model.dto.wx;

import com.baomidou.mybatisplus.annotation.TableField;
import com.yiyue.common.util.BasePageDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
*
* author: liyun
* create date: 2022-01-19
*/

@ApiModel(value="查询条件数据格式",description="所有都不是必须的")
@Data
public class BookReadDTO extends BasePageDTO{

    // 条件查询字段，需要自己定义添加或者修改
    @ApiModelProperty(value="订单号")
    private String id;

    @ApiModelProperty(value="用户")
    private String userId;

    @ApiModelProperty(value="用户")
    @TableField(exist = false)
    private String userName;

    @ApiModelProperty(value="图书")
    @TableField(exist = false)
    private String bookName;

    @ApiModelProperty(value="图书id")
    private String libraryId;

    @ApiModelProperty(value="图书名称")
    private String libraryName;

    @ApiModelProperty(value="订单状态")
    private String orderStatus;

    @ApiModelProperty(value="备注")
    private String bookRemark;

    @ApiModelProperty(value="借阅时间")
    private String startDate;

    @ApiModelProperty(value="归还时间")
    private String endDate;

    @ApiModelProperty(value="创建时间")
    private String createDate;

}
