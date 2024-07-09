package com.yiyue.mapper.system;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yiyue.model.bean.system.SysMenu;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
@MapperScan
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    @Select("select * from sys_menu where menu_state=0 order by menu_no")
    List<SysMenu> findSysMenuAllList();

    @Select("select DISTINCT t1.menu_id,t1.parent_menu_id,t1.select_menu_id," +
            " t1.menu_level,t1.menu_name,t1.menu_url,t1.menu_component,t1.menu_icon," +
            " t1.hidden_children,t1.menu_show,t1.create_date,t1.menu_state,t1.menu_no,t6.org_name" +
            " from sys_menu t1,sys_role t2,sys_user t3,sys_user_role t4,sys_role_menu t5,sys_organ t6" +
            " where t3.user_id=t4.user_id and t4.role_id=t2.role_id and t5.role_id=t2.role_id and t5.menu_id=t1.menu_id and t3.org_id=t6.org_id" +
            " and t3.user_id=#{userId} and t1.menu_state=0" +
            " order by t1.menu_no")
    List<SysMenu> findSysMenuByLoginUserId(@Param("userId") String userId);
}
