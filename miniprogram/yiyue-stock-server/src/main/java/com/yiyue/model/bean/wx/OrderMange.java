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
* create date: 2022-01-14
*/

@ApiModel(value="记录实体类",description="")
@Data
@TableName("order_mange")
public class OrderMange implements Serializable {

    @ApiModelProperty(value="主键，记录Id")
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

   /* @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value="主键，记录Id")
    private String orderId;*/

    @ApiModelProperty(value="用户")
    private String userId;

    @TableField(exist = false)
    @ApiModelProperty(value="用户")
    private String userName;

    @ApiModelProperty(value="图书馆id")
    private int libraryId;

    @TableField(exist = false)
    @ApiModelProperty(value="图书馆")
    private String libraryName2;

    @ApiModelProperty(value="座位id")
    private int seatId;

    @TableField(exist = false)
    @ApiModelProperty(value="座位")
    private String seatName2;

    @ApiModelProperty(value="订单状态")
    private String orderStatus;


    @ApiModelProperty(value="预约时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date planTime;

    @ApiModelProperty(value="结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endTime;

    @ApiModelProperty(value="创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDate;

    /**
    @TableField(exist = false)
    private String userName;
    */

}
