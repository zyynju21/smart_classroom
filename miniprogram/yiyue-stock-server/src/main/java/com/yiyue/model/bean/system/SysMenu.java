package com.yiyue.model.bean.system;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.models.auth.In;
import lombok.Data;

import java.util.Date;
import java.util.Set;

@Data
@TableName("sys_menu")
public class SysMenu {

    @TableId(type = IdType.ASSIGN_ID)
    private String menuId;
    private String parentMenuId;
    private String selectMenuId;
    private Integer menuLevel;
    private String menuName;
    private String menuUrl;
    private String menuComponent;
    private String menuIcon;
    private Integer hiddenChildren;
    private Integer menuShow;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDate;
    private Integer menuState;
    private Double menuNo;

}
