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
@TableName("notice_advise")
public class NoticeAdvise implements Serializable {

    @ApiModelProperty(value="主键，记录Id")
    @TableId(type = IdType.ASSIGN_ID)
    private int id;

    @ApiModelProperty(value="标题")
    private String title;

    @ApiModelProperty(value="内容")
    private String noticeContent;


    @ApiModelProperty(value="时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDate;

    /**
    @TableField(exist = false)
    private String userName;
    */

}
