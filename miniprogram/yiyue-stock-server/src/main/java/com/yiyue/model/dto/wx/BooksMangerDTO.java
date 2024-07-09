package com.yiyue.model.dto.wx;

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
public class BooksMangerDTO extends BasePageDTO{

    // 条件查询字段，需要自己定义添加或者修改
    @ApiModelProperty(value="序号")
    private String id;

    @ApiModelProperty(value="书名")
    private String bookName;

    @ApiModelProperty(value="图片")
    private String bookImg;

    @ApiModelProperty(value="内容")
    private String bookText;

    @ApiModelProperty(value="借阅状态(0,可借，1已借)")
    private String useState;

    @ApiModelProperty(value="备注")
    private String bookMark;

    @ApiModelProperty(value="创建时间")
    private String createDate;

}