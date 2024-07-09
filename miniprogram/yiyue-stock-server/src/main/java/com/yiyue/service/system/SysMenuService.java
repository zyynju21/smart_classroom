package com.yiyue.service.system;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yiyue.common.util.Assert;
import com.yiyue.mapper.system.SysMenuMapper;
import com.yiyue.mapper.system.SysRoleMenuMapper;
import com.yiyue.model.bean.system.SysMenu;
import com.yiyue.model.bean.system.SysRoleMenu;
import com.yiyue.model.dto.system.SysRoleMenuDTO;
import com.yiyue.model.vo.system.LoginUser;
import com.yiyue.model.vo.system.SysMenuTreeVO;
import com.yiyue.model.vo.system.SysMenuVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class SysMenuService {

    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;

    /**
     * 后端 系统管理->菜单管理 查询菜单列表
     * @return
     */
    public List<SysMenuVO> findSysMenuVOList() {
        List<SysMenu> sysMenuList=sysMenuMapper.findSysMenuAllList();
        List<SysMenuVO> sysMenuVOList=new ArrayList<>();
        getSysMenuVo(sysMenuList,"0",sysMenuVOList);
        return sysMenuVOList;
    }

    /**
     * 后端 系统管理->菜单管理 findSysMenuVOList 方法里 递归查询
     * @param sysMenuList
     * @param parentId
     * @param sysMenuVOList
     */
    private void getSysMenuVo(List<SysMenu> sysMenuList,String parentId, List<SysMenuVO> sysMenuVOList){
        for(SysMenu menu:sysMenuList){
            if(menu.getMenuLevel()==2){
                if(menu.getSelectMenuId().equals(parentId)){
                    menu2treeVoList(menu,sysMenuList,sysMenuVOList);
                }
            }else {
                if (menu.getParentMenuId().equals(parentId)) {
                    menu2treeVoList(menu,sysMenuList,sysMenuVOList);
                }
            }
            /*if (menu.getParentMenuId().equals(parentId)) {
                menu2treeVoList(menu,sysMenuList,sysMenuVOList);
            }*/
        }
    }

    private void menu2treeVoList(SysMenu menu,List<SysMenu> sysMenuList,List<SysMenuVO> sysMenuVOList){
        SysMenuVO sysMenuVO = new SysMenuVO();
        sysMenuVO.setMenuId(menu.getMenuId());
        sysMenuVO.setMenuName(menu.getMenuName());
        sysMenuVO.setMenuUrl(menu.getMenuUrl());
        sysMenuVO.setMenuComponent(menu.getMenuComponent());
        sysMenuVO.setMenuIcon(menu.getMenuIcon());
        sysMenuVO.setMenuState(menu.getMenuState());
        sysMenuVO.setMenuNo(menu.getMenuNo());
        sysMenuVO.setMenuLevel(menu.getMenuLevel());

        List<SysMenuVO> childMenuVOList = new ArrayList<>();
        getSysMenuVo(sysMenuList, menu.getMenuId(), childMenuVOList);
        sysMenuVO.setChildren(childMenuVOList);
        sysMenuVOList.add(sysMenuVO);
    }

    /**
     * 后端 系统管理-菜单管理->新增or 修改菜单 ，加载级联选择上级父菜单
     * @return
     */
    public List<SysMenuTreeVO> list_parent_menu_for_box() {
        List<SysMenu> sysMenuList=sysMenuMapper.findSysMenuAllList();
        List<SysMenuTreeVO> parentMenus=new ArrayList<>();
        getMenuTreeVOList(sysMenuList,"0",parentMenus,true);
        return parentMenus;
    }

    /** 后端 系统管理->权限管理 -> 角色授权菜单，加载所有菜单 */
    public List<SysMenuTreeVO> findSysMenuTreeVOList() {
        List<SysMenu> sysMenuList=sysMenuMapper.findSysMenuAllList();
        List<SysMenuTreeVO> parentMenus=new ArrayList<>();
        getMenuTreeVOList(sysMenuList,"0",parentMenus,false);
        return parentMenus;
    }

    /**
     * 后端 系统管理->权限管理 -> 角色授权菜单，加载用户拥有权限所有菜单
     * @param loginUser
     * @return
     */
    public List<SysMenuTreeVO> findSysMenuTreeVOListByLoginUser(LoginUser loginUser) {
        List<SysMenu> sysMenuList = new ArrayList<>();
        if(loginUser.getSysOrgan().getOrgType()==0){
            sysMenuList=sysMenuMapper.findSysMenuAllList();
        }else {
            sysMenuList = sysMenuMapper.findSysMenuByLoginUserId(loginUser.getUserId());
        }
        List<SysMenuTreeVO> parentMenus=new ArrayList<>();
        getMenuTreeVOList(sysMenuList,"0",parentMenus,true);
        return parentMenus;
    }

    /**
     *
     * @param sysMenuList 菜单集合
     * @param parentId  父节点id
     * @param parentMenus   待返回的treeVO菜单树
     * @param excludeChild  是否排查按钮节点 true排除 false不排除
     */
    private void getMenuTreeVOList(List<SysMenu> sysMenuList,String parentId,List<SysMenuTreeVO> parentMenus,boolean excludeChild){
        for(SysMenu menu:sysMenuList){
            if(menu.getParentMenuId().equals(parentId)){
                SysMenuTreeVO vo=new SysMenuTreeVO();
                vo.setValue(menu.getMenuId()+"");
                vo.setKey(menu.getMenuId()+"");
                vo.setTitle(menu.getMenuName());
                vo.setMenuLevel(menu.getMenuLevel());
                if(excludeChild){
                    if(menu.getMenuLevel()==2){
                        continue;
                    }
                }
                List<SysMenuTreeVO> child=new ArrayList<>();
                getMenuTreeVOList(sysMenuList,menu.getMenuId(),child,excludeChild);
                vo.setChildren(child);
                parentMenus.add(vo);
            }
        }
    }


    private void setSysMenuParam(SysMenu sysMenu){
        Integer menuLevel=sysMenu.getMenuLevel();
        if(menuLevel==0){
            sysMenu.setParentMenuId("0");
            sysMenu.setSelectMenuId("");
        }
        if(menuLevel==1){
            SysMenu parentMenu=getParentSysMenu(sysMenu.getParentMenuId(),0);
            sysMenu.setParentMenuId(parentMenu.getMenuId());
            sysMenu.setSelectMenuId("");
        }
        if(menuLevel==2){
            SysMenu parentMenu=getParentSysMenu(sysMenu.getParentMenuId(),1);
            sysMenu.setParentMenuId(parentMenu.getParentMenuId());
            sysMenu.setSelectMenuId(parentMenu.getMenuId());
        }
        Assert.isBlank(sysMenu.getMenuName(),"菜单名不能为空");
        Assert.isBlank(sysMenu.getMenuLevel()+"","菜单等级不能未空");
        sysMenu.setCreateDate(new Date());
    }
    private SysMenu getParentSysMenu(String parendId,Integer menuLevel){
        SysMenu parentMenu=sysMenuMapper.selectById(parendId);
        if(parentMenu.getMenuLevel().equals(menuLevel)){
            return parentMenu;
        }else{
            return getParentSysMenu(parentMenu.getParentMenuId(),menuLevel);
        }
    }

    public void updateSysMenu(SysMenu sysMenu) {
        setSysMenuParam(sysMenu);
        this.sysMenuMapper.updateById(sysMenu);
    }
    public void insertSysMenu(SysMenu sysMenu) {
        setSysMenuParam(sysMenu);
        sysMenuMapper.insert(sysMenu);
    }

    public void deleteSysMenuById(String menuId) {
        SysMenu sysMenu=this.sysMenuMapper.selectById(menuId);
        sysMenu.setMenuState(-1);
        this.sysMenuMapper.updateById(sysMenu);
    }

    public SysMenu findSysMenuById(String menuId) {
        return sysMenuMapper.selectById(menuId);
    }

    /**
     * 获得角色配置的菜单权限列表
     * @param roleId
     * @return
     */
    public List<SysRoleMenu> findRoleMenuList(String roleId) {
        QueryWrapper<SysRoleMenu> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("role_id",roleId);
        List<SysRoleMenu> roleMenuList=sysRoleMenuMapper.selectList(queryWrapper);
        for(SysRoleMenu rm:roleMenuList){
            SysMenu sysMenu=sysMenuMapper.selectById(rm.getMenuId());
            rm.setSysMenu(sysMenu);
        }
        return roleMenuList;
    }

    public void saveRoleAuthMenu(SysRoleMenuDTO sysRoleMenuDTO) {
        String roleId=sysRoleMenuDTO.getRoleId();
        List<String> menuIds=sysRoleMenuDTO.getMenuIds();
        QueryWrapper<SysRoleMenu> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("role_id",roleId);
        sysRoleMenuMapper.delete(queryWrapper);

        List<SysMenu> sysMenuList=sysMenuMapper.findSysMenuAllList();
        if(null!=menuIds&&menuIds.size()>0){
            for(String menuId:menuIds){
                SysRoleMenu srm=new SysRoleMenu();
                srm.setRoleId(roleId);
                srm.setMenuId(menuId);
                sysRoleMenuMapper.insert(srm);
                //按钮子菜单在授权时未被选中，需要重新添加
                List<SysMenu> btnChildMenus=sysMenuList.stream().filter(sysMenu -> menuId.equals(sysMenu.getSelectMenuId())).collect(Collectors.toList());
                for(SysMenu sysMenu:btnChildMenus){
                    SysRoleMenu csrm=new SysRoleMenu();
                    csrm.setRoleId(roleId);
                    csrm.setMenuId(sysMenu.getMenuId());
                    sysRoleMenuMapper.insert(csrm);
                }
            }
        }
    }


}
