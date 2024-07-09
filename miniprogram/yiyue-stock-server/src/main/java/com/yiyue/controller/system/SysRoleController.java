package com.yiyue.controller.system;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yiyue.common.aspact.SystemPlatLog;
import com.yiyue.common.result.Result;
import com.yiyue.common.vo.ItemVO;
import com.yiyue.interceptor.SystemUserContextHolder;
import com.yiyue.model.bean.system.SysOrgan;
import com.yiyue.model.bean.system.SysRole;
import com.yiyue.model.bean.system.SysRoleMenu;
import com.yiyue.model.dto.system.SysRoleDTO;
import com.yiyue.model.dto.system.SysRoleMenuDTO;
import com.yiyue.model.vo.system.LoginUser;
import com.yiyue.model.vo.system.SysMenuTreeVO;
import com.yiyue.model.vo.system.SysRoleVO;
import com.yiyue.service.system.SysMenuService;
import com.yiyue.service.system.SysOrganService;
import com.yiyue.service.system.SysRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/system/role")
public class SysRoleController {

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private SysMenuService sysMenuService;

    @Autowired
    private SysOrganService sysOrganService;

    @SystemPlatLog
    @PostMapping(value = "/list",produces = "application/json;charset=utf-8")
    public Result list(@RequestBody SysRoleDTO sysRoleDTO){
        //如果是平台管理员，查询所有角色，如果只是组织机构的管理员，只查询该管理员所属机构的角色
        LoginUser loginUser=SystemUserContextHolder.getLoginUser();
        SysOrgan sysOrgan=loginUser.getSysOrgan();
        List<ItemVO> organTreeList=new ArrayList<>();
        //左边数节点选全部,将orgId设置成空，否则就按用户选择的orgId来查询
        if("0".equals(sysRoleDTO.getOrgId())){
            sysRoleDTO.setOrgId(null);
        }
        //如果组织机构不是超级用户的组件机构类型，则只能查询所属组织机构的的角色
        if(sysOrgan.getOrgType()!=0){
            sysRoleDTO.setOrgId(sysOrgan.getOrgId());
            organTreeList=findRoleMakeLeftOrganTreeList(sysOrgan.getOrgId());
        }else{
            organTreeList=findRoleMakeLeftOrganTreeList(null);
        }
        IPage<SysRole> roleList=sysRoleService.findSysRoleList(sysRoleDTO);
        //返回角色列表查询展示的vo，左边树节点，右边是角色列表
        SysRoleVO sysRoleVO=new SysRoleVO();
        sysRoleVO.setSysOrganTree(organTreeList);
        sysRoleVO.setSysRoleList(roleList);
        sysRoleVO.setOrgType(sysOrgan.getOrgType());
        return Result.success(sysRoleVO);
    }

    // 查询角色列表是，在角色列表左侧生成组织机构树
    private List<ItemVO> findRoleMakeLeftOrganTreeList(String orgId){
        List<SysOrgan> sysOrganList=sysOrganService.findSysOrganList(orgId);
        List<ItemVO> organTrees=new ArrayList<>();
        //先设置一个所有机构的根节点
        ItemVO orgRoot=new ItemVO();
        orgRoot.setKey("0");
        orgRoot.setTitle("全部");
        List<ItemVO> childList=new ArrayList<>();
        for(SysOrgan organ:sysOrganList){
            ItemVO child=new ItemVO();
            child.setKey(organ.getOrgId());
            child.setTitle(organ.getOrgName());
            childList.add(child);
        }
        orgRoot.setChildren(childList);
        organTrees.add(orgRoot);
        return organTrees;
    }

    @SystemPlatLog
    @PostMapping(value = "/create",produces = "application/json;charset=utf-8")
    public Result create(@RequestBody SysRole sysRole){
        sysRoleService.insertSysRole(sysRole);
        return Result.success();
    }

    @SystemPlatLog
    @DeleteMapping(value = "/delete/{roleId}",produces = "application/json;charset=utf-8")
    public Result delete(@PathVariable String roleId){
        sysRoleService.deleteSysRoleById(roleId);
        return Result.success();
    }

    @SystemPlatLog
    @GetMapping(value = "/get_by_id",produces = "application/json;charset=utf-8")
    public Result get_by_id(@RequestParam String roleId){
        SysRole sysRole=sysRoleService.findSysRoleById(roleId);
        return Result.success(sysRole);
    }

    @SystemPlatLog
    @PutMapping(value = "/update",produces = "application/json;charset=utf-8")
    public Result update(@RequestBody SysRole sysRole){
        sysRoleService.updateSysRole(sysRole);
        return Result.success();
    }

    /**
     * 后台管理 -> 系统管理 ->权限管理 -> 新增 or 修改菜单时，获得父节点菜单树
     * @return
     */
    @SystemPlatLog
    @PostMapping(value = "/get_sys_menu_tree_list",produces = "application/json;charset=utf-8")
    public Result findSysMenuTreeVOList(){
        LoginUser loginUser=SystemUserContextHolder.getLoginUser();
        List<SysMenuTreeVO> menuVOList=sysMenuService.findSysMenuTreeVOListByLoginUser(loginUser);
        log.debug(menuVOList+"");
        return Result.success(menuVOList);
    }

    /**
     * 后台管理 -> 系统管理 -> 权限管理
     * @param roleId
     * @return
     */
    @SystemPlatLog
    @GetMapping(value = "/get_role_menu_list",produces = "application/json;charset=utf-8")
    public Result findRoleMenuList(@RequestParam String roleId){
        List<SysRoleMenu> roleMenuList=sysMenuService.findRoleMenuList(roleId);
        log.debug(roleMenuList+"");
        return Result.success(roleMenuList);
    }

    /**
     * 保存角色配置菜单
     * @param sysRoleMenuDTO
     * @return
     */
    @SystemPlatLog
    @PostMapping(value = "/save_role_auth_menu",produces = "application/json;charset=utf-8")
    public Result save_role_auth_menu(@RequestBody SysRoleMenuDTO sysRoleMenuDTO){
        log.debug("保存角色配置菜单权限：{}",sysRoleMenuDTO);
        sysMenuService.saveRoleAuthMenu(sysRoleMenuDTO);
        return Result.success();
    }


}
