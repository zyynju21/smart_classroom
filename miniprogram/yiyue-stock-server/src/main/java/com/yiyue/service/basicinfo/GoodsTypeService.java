package com.yiyue.service.basicinfo;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yiyue.common.util.PageUtil;
import com.yiyue.common.vo.ItemVO;
import com.yiyue.model.bean.system.SysMenu;
import com.yiyue.model.vo.system.SysMenuTreeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.apache.commons.lang3.StringUtils;
import com.yiyue.model.bean.basicinfo.GoodsType;
import com.yiyue.model.dto.basicinfo.GoodsTypeDTO;
import com.yiyue.mapper.basicinfo.GoodsTypeMapper;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class GoodsTypeService {

    @Autowired
    private GoodsTypeMapper goodsTypeMapper;

    public IPage<GoodsType> findGoodsTypeListPageByParam(GoodsTypeDTO goodsTypeDTO) {
        // 从dto对象中获得查询条件，添加到queryWrapper对象中, 查询条件还需要视情况自行修改
        QueryWrapper<GoodsType> queryWrapper=getQueryWrapper(goodsTypeDTO);

        IPage<GoodsType> goodsTypeList=goodsTypeMapper.findGoodsTypePageList(PageUtil.getPagination(goodsTypeDTO),queryWrapper);
        return goodsTypeList;
    }

    private QueryWrapper getQueryWrapper(GoodsTypeDTO goodsTypeDTO){
        QueryWrapper<GoodsType> queryWrapper=new QueryWrapper<>();
        // 主键
        if(!StringUtils.isBlank(goodsTypeDTO.getId())){
            queryWrapper.eq("id",goodsTypeDTO.getId());
        }
        // 类别名
        if(!StringUtils.isBlank(goodsTypeDTO.getName())){
            queryWrapper.like("name",goodsTypeDTO.getName());
        }
        // 父级类别id
        if(!StringUtils.isBlank(goodsTypeDTO.getParentId())){
            queryWrapper.eq("parent_id",goodsTypeDTO.getParentId());
        }
        // 节点类型
        if(!StringUtils.isBlank(goodsTypeDTO.getIsLeaf())){
            queryWrapper.eq("is_leaf",goodsTypeDTO.getIsLeaf());
        }
        // 节点图标
        if(!StringUtils.isBlank(goodsTypeDTO.getIcon())){
            queryWrapper.eq("icon",goodsTypeDTO.getIcon());
        }
        // 组织机构id
        if(!StringUtils.isBlank(goodsTypeDTO.getOrgId())){
            queryWrapper.eq("org_id",goodsTypeDTO.getOrgId());
        }
        return queryWrapper;
    }

    public void insertGoodsType(GoodsType goodsType) {
        goodsTypeMapper.insert(goodsType);
    }

    public void updateGoodsType(GoodsType goodsType) {
        this.goodsTypeMapper.updateById(goodsType);
    }

    public void deleteGoodsTypeById(String id) {
        this.goodsTypeMapper.deleteById(id);
    }

    public void deleteGoodsTypeByIds(String[] ids) {
        for(String id:ids){
            goodsTypeMapper.deleteById(id);
        }
    }

    public GoodsType findGoodsTypeById(String id) {
        GoodsType goodsType=goodsTypeMapper.findGoodsTypeById(id);
        return goodsType;
    }

    public List<ItemVO> list_parent_goods_type_for_box(GoodsTypeDTO goodsTypeDTO) {
        QueryWrapper<GoodsType> queryWrapper=new QueryWrapper();
        boolean isShowOrgName=true;
        if(!StringUtils.isBlank(goodsTypeDTO.getOrgId())){
            queryWrapper.eq("so.org_id",goodsTypeDTO.getOrgId());
            isShowOrgName=false;
        }
        if(!StringUtils.isBlank(goodsTypeDTO.getOrgName())){
            queryWrapper.like("so.org_name","%"+goodsTypeDTO.getOrgName()+"%");
        }
        List<GoodsType> goodsTypeList=this.goodsTypeMapper.findGoodsTypeList(queryWrapper);
        List<ItemVO> goodsTypeTreeList=new ArrayList<>();
        this.getGoodsTypeTreeVOList(goodsTypeList,"0",goodsTypeTreeList,isShowOrgName);
        return goodsTypeTreeList;
    }
    private void getGoodsTypeTreeVOList(List<GoodsType> goodsTypeList, String parentId, List<ItemVO> goodsTypeTreeList,boolean isShowOrgName){
        for(GoodsType goodsType:goodsTypeList){
            if(goodsType.getParentId().equals(parentId)){
                ItemVO vo=new ItemVO();
                vo.setValue(goodsType.getId());
                vo.setKey(goodsType.getId());
                //如果是管理机构查看商品分类数，在分类上显示出机构名称
                if(isShowOrgName) {
                    vo.setTitle("("+goodsType.getOrgName()+")"+goodsType.getName());
                }else {
                    vo.setTitle(goodsType.getName());
                }

                List<ItemVO> child=new ArrayList<>();
                getGoodsTypeTreeVOList(goodsTypeList,goodsType.getId(),child,isShowOrgName);
                vo.setChildren(child);
                goodsTypeTreeList.add(vo);
            }
        }
    }


}
