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
* create date: 2022-01-12
*/

@ApiModel(value="记录实体类",description="")
@Data
@TableName("library_type")
public class LibraryType implements Serializable {

    @ApiModelProperty(value="主键，记录Id")
    @TableId(type = IdType.ASSIGN_ID)
    private int id;

    @ApiModelProperty(value="图片")
    private String libraryImg;

    @ApiModelProperty(value="分类")
    private String libraryName;

    @ApiModelProperty(value="位置")
    private String addName;

    @ApiModelProperty(value="营业时间")
    private String businessDate;

    @ApiModelProperty(value="介绍")
    private String libraryInfo;

    @ApiModelProperty(value="座位")
    private int seatId;

    /**
    @TableField(exist = false)
    private String userName;
    */

}
