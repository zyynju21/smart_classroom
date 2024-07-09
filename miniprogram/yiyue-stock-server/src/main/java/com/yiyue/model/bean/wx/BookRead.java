package com.yiyue.model.bean.wx;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
*
* author: liyun
* create date: 2022-01-19
*/

@ApiModel(value="记录实体类",description="")
@Data
@TableName("book_read")
public class BookRead implements Serializable {

    @ApiModelProperty(value="主键，记录Id")
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty(value="用户")
    private String userId;

    @ApiModelProperty(value="用户")
    @TableField(exist = false)
    private String userName;

    @ApiModelProperty(value="图书id")
    private int libraryId;

    @ApiModelProperty(value="图书名称")
    private String libraryName;

    @ApiModelProperty(value="图书名称")
    @TableField(exist = false)
    private String bookName;

    @ApiModelProperty(value="订单状态")
    private String orderStatus;

    @ApiModelProperty(value="备注")
    private String bookRemark;


    @ApiModelProperty(value="借阅时间")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date startDate;


    @ApiModelProperty(value="归还时间")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date endDate;


    @ApiModelProperty(value="创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDate;

    /**
    @TableField(exist = false)
    private String userName;
    */

}
