package com.yiyue.model.dto.wx;

import com.yiyue.common.util.BasePageDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
* 
* author: liyun
* create date: 2022-01-12
*/

@ApiModel(value="查询条件数据格式",description="所有都不是必须的")
@Data
public class NoticeAdviseDTO extends BasePageDTO{

    // 条件查询字段，需要自己定义添加或者修改
    @ApiModelProperty(value="序号")
    private String id;

    @ApiModelProperty(value="标题")
    private String title;

    @ApiModelProperty(value="内容")
    private String noticeContent;

    @ApiModelProperty(value="时间")
    private String createDate;

}