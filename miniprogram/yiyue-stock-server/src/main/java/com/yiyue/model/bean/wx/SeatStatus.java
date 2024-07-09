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
* create date: 2022-01-13
*/

@ApiModel(value="记录实体类",description="")
@Data
@TableName("seat_status")
public class SeatStatus implements Serializable {

    @ApiModelProperty(value="主键，记录Id")
    @TableId(type = IdType.ASSIGN_ID)
    private int id;

    @ApiModelProperty(value="状态（0空闲；1预约；2占用）")
    private int status;

    @ApiModelProperty(value="座位")
    private String seatName;

    @ApiModelProperty(value="图书馆")
    private String libraryType;

    @TableField(exist = false)
    @ApiModelProperty(value="图书馆名称")
    private String libraryName;

    /**
    @TableField(exist = false)
    private String userName;
    */

}
