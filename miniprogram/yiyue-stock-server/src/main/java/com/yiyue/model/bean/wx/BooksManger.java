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
@TableName("books_manger")
public class BooksManger implements Serializable {

    @ApiModelProperty(value="主键，记录Id")
    @TableId(type = IdType.ASSIGN_ID)
    private int id;

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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDate;

    /**
    @TableField(exist = false)
    private String userName;
    */

}
