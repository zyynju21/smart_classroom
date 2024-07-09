package com.yiyue.controller.system;


import com.baomidou.mybatisplus.core.metadata.IPage;

import com.yiyue.common.aspact.SystemPlatLog;
import com.yiyue.common.result.Result;
import com.yiyue.common.vo.ItemVO;
import com.yiyue.interceptor.SystemUserContextHolder;
import com.yiyue.model.bean.system.SysOrgan;
import com.yiyue.model.bean.system.SysUser;
import com.yiyue.model.dto.system.SysUserDTO;
import com.yiyue.model.dto.system.SysUserRoleDTO;
import com.yiyue.model.dto.system.UserPasswordDTO;
import com.yiyue.model.vo.system.LoginUser;
import com.yiyue.model.vo.system.SysOrganUserVO;
import com.yiyue.model.vo.system.SysUserVO;
import com.yiyue.service.system.SysOrganService;
import com.yiyue.service.system.SysRoleService;
import com.yiyue.service.system.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@RestController
@RequestMapping("/system/user")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private SysOrganService sysOrganService;

    /**
     * 后台管理->系统管理->查询所有系统用户，返回用户列表
     * @param sysUserDTO 分页参数 查询条件 排序条件
     * @return 用户列表
     */
    @SystemPlatLog
    @PostMapping(value = "/list",produces = "application/json;charset=utf-8")
    public Result list(@RequestBody SysUserDTO sysUserDTO){
        //如果是平台管理员，查询所有用户，如果只是组织机构的管理员，只查询该管理员所属机构的用户
        LoginUser loginUser=SystemUserContextHolder.getLoginUser();
        SysOrgan sysOrgan=loginUser.getSysOrgan();
        List<ItemVO> organTreeList=new ArrayList<>();
        //左边数节点选全部,将orgId设置成空，否则就按用户选择的orgId来查询
        if("0".equals(sysUserDTO.getOrgId())){
            sysUserDTO.setOrgId(null);
        }
        //如果组织机构不是超级用户的组件机构类型，则只能查询所属组织机构的的用户
        if(sysOrgan.getOrgType()!=0){
            sysUserDTO.setOrgId(sysOrgan.getOrgId());
            organTreeList=findRoleMakeLeftOrganTreeList(sysOrgan.getOrgId());
        }else{
            organTreeList=findRoleMakeLeftOrganTreeList(null);
        }
        IPage<SysUserVO> userList=sysUserService.findSysUserList(sysUserDTO);
        SysOrganUserVO sysOrganUserVO=new SysOrganUserVO();
        sysOrganUserVO.setSysOrganTree(organTreeList);
        sysOrganUserVO.setSysUserList(userList);
        sysOrganUserVO.setOrgType(sysOrgan.getOrgType());
        return Result.success(sysOrganUserVO);
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

    /**
     * 后台管理->系统管理->修改用户信息，查询并指定userId的sysUser对象
     * @param userId
     * @return
     */
    @SystemPlatLog
    @GetMapping(value = "/get_by_id",produces = "application/json;charset=utf-8")
    public Result get_by_id(@RequestParam String userId){
        SysUser sysUser=sysUserService.findSysUserByUserId(userId);
        return Result.success(sysUser);
    }

    /**
     * 新建
     * @param sysUser
     * @return
     */
    @SystemPlatLog
    @PostMapping(value = "/create",produces = "application/json;charset=utf-8")
    public Result create(@RequestBody SysUser sysUser){
        sysUserService.insertSysUser(sysUser);
        return Result.success();
    }

    /**
     * 删除
     * @param userId
     * @return
     */
    @SystemPlatLog
    @DeleteMapping(value = "/delete/{userId}",produces = "application/json;charset=utf-8")
    public Result delete(@PathVariable String userId){
        sysUserService.deleteSysUserById(userId);
        return Result.success();
    }

    /**
     * 修改
     * @param sysUser
     * @return
     */
    @SystemPlatLog
    @PutMapping(value = "/update",produces = "application/json;charset=utf-8")
    public Result update(@RequestBody SysUser sysUser){
        sysUserService.updateSysUser(sysUser);
        return Result.success();
    }

    /**
     * 重置密码
     * @param userId
     * @return
     */
    @SystemPlatLog
    @GetMapping(value = "/reset_user_login_pass",produces = "application/json;charset=utf-8")
    public Result reset_user_login_pass(@RequestParam String userId){
        sysUserService.reset_user_login_pass(userId);
        return Result.success();
    }

    /**
     * 修改密码
     * @param userPasswordDTO
     * @return
     */
    @SystemPlatLog
    @RequestMapping(value = "/change_password",produces = "application/json;charset=utf-8")
    public Result change_password(@RequestBody UserPasswordDTO userPasswordDTO) {
        sysUserService.updateLoginPassword(userPasswordDTO);
        return Result.success();
    }

    /**
     * 后台管理 - > 系统管理 -> 用户配置角色，先获得角色列表
     * @return
     */
    @SystemPlatLog
    @PostMapping(value = "/get_role_to_item_list",produces = "application/json;charset=utf-8")
    public Result get_role_to_item_list(){
        List<ItemVO> roleList=sysRoleService.findSysRole2ItemVOList();
        return Result.success(roleList);
    }

    /**
     * 后台管理 - > 系统管理 -> 用户配置角色，再获得用户配置的角色列表
     * @param userId
     * @return
     */
    @SystemPlatLog
    @GetMapping(value = "/get_user_auth_role_item_list",produces = "application/json;charset=utf-8")
    public Result get_user_auth_role_item_list(@RequestParam String userId){
        List<String> userRoleList=sysRoleService.findUserAuthRoleList(userId);
        log.debug(userRoleList+"");
        return Result.success(userRoleList);
    }

    /**
     * 后台管理 - > 系统管理 -> 用户配置角色，保存用户配置角色
     * @param sysUserRoleDTO
     * @return
     */
    @SystemPlatLog
    @PostMapping(value = "/save_user_auth_role",produces = "application/json;charset=utf-8")
    public Result save_user_auth_role(@RequestBody SysUserRoleDTO sysUserRoleDTO){
        sysRoleService.saveUserAuthRole(sysUserRoleDTO);
        return Result.success();
    }


    /**
     * @return 所有用户列表
     */
    @SystemPlatLog
    @PostMapping(value = "/allList",produces = "application/json;charset=utf-8")
    public Result allList(@RequestBody SysUserDTO sysUserDTO){
        IPage<SysUser> userList=sysUserService.selectAllList(sysUserDTO);
        SysOrganUserVO sysOrganUserVO=new SysOrganUserVO();
        sysOrganUserVO.setSysAllUserList(userList);
        return Result.success(sysOrganUserVO);
    }
}
