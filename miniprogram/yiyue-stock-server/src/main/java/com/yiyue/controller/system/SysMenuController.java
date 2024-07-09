package com.yiyue.controller.system;

import com.yiyue.common.aspact.SystemPlatLog;
import com.yiyue.common.result.Result;
import com.yiyue.model.bean.system.SysMenu;
import com.yiyue.model.vo.system.SysMenuTreeVO;
import com.yiyue.model.vo.system.SysMenuVO;
import com.yiyue.service.system.SysMenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/system/menu")
public class SysMenuController {

    @Autowired
    private SysMenuService sysMenuService;

    /**
     * 后台管理 -> 系统管理 -> 菜单管理，查询菜单列表
     * @return
     */
    @SystemPlatLog
    @PostMapping(value = "/list",produces = "application/json;charset=utf-8")
    public Result list(){
        List<SysMenuVO> sysMenuVOList=sysMenuService.findSysMenuVOList();
        return Result.success(sysMenuVOList);
    }

    /**
     * 后台管理 -> 系统管理 ->菜单管理 -> 新增 or 修改菜单时，获得父节点菜单树
     * @return
     */
    @PostMapping(value = "/list_parent_menu_for_box",produces = "application/json;charset=utf-8")
    public Result list_parent_menu_for_box(){
        List<SysMenuTreeVO> parentMenuVOList=sysMenuService.list_parent_menu_for_box();
        log.debug(parentMenuVOList+"");
        return Result.success(parentMenuVOList);
    }

    /**
     * 后台管理 -> 系统管理 ->菜单管理 -新增操作
     * @param sysMenu
     * @return
     */
    @SystemPlatLog
    @PostMapping(value = "/create",produces = "application/json;charset=utf-8")
    public Result create(@RequestBody SysMenu sysMenu){
        log.debug("新增菜单获得请求对象 sysMenu:{}",sysMenu);
        sysMenuService.insertSysMenu(sysMenu);
        return Result.success();
    }

    /**
     * 后台管理 -> 系统管理 ->菜单管理 -删除操作
     * @param menuId
     * @return
     */
    @SystemPlatLog
    @DeleteMapping(value = "/delete/{menuId}",produces = "application/json;charset=utf-8")
    public Result delete(@PathVariable String menuId){
        sysMenuService.deleteSysMenuById(menuId);
        return Result.success();
    }

    @SystemPlatLog
    @GetMapping(value = "/get_by_id",produces = "application/json;charset=utf-8")
    public Result get_by_id(@RequestParam String menuId){
        SysMenu sysMenu=sysMenuService.findSysMenuById(menuId);
        return Result.success(sysMenu);
    }

    @SystemPlatLog
    @PutMapping(value = "/update",produces = "application/json;charset=utf-8")
    public Result update(@RequestBody SysMenu sysMenu){
        sysMenuService.updateSysMenu(sysMenu);
        return Result.success();
    }
}
