package com.yiyue.service.basicinfo;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yiyue.common.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.apache.commons.lang3.StringUtils;
import com.yiyue.model.bean.basicinfo.GoodsUnit;
import com.yiyue.model.dto.basicinfo.GoodsUnitDTO;
import com.yiyue.mapper.basicinfo.GoodsUnitMapper;

@Service
@Transactional
public class GoodsUnitService {

    @Autowired
    private GoodsUnitMapper goodsUnitMapper;

    public IPage<GoodsUnit> findGoodsUnitListPageByParam(GoodsUnitDTO goodsUnitDTO) {
        // 从dto对象中获得查询条件，添加到queryWrapper对象中, 查询条件还需要视情况自行修改
        QueryWrapper<GoodsUnit> queryWrapper=getQueryWrapper(goodsUnitDTO);

        IPage<GoodsUnit> goodsUnitList=goodsUnitMapper.findGoodsUnitPageList(PageUtil.getPagination(goodsUnitDTO),queryWrapper);
        return goodsUnitList;
    }

    private QueryWrapper getQueryWrapper(GoodsUnitDTO goodsUnitDTO){
        QueryWrapper<GoodsUnit> queryWrapper=new QueryWrapper<>();
        // 单位名
        if(!StringUtils.isBlank(goodsUnitDTO.getName())){
            queryWrapper.like("name","%"+goodsUnitDTO.getName()+"%");
        }
        // 组织机构名称
        if(!StringUtils.isBlank(goodsUnitDTO.getOrgName())){
            queryWrapper.like("so.org_name","%"+goodsUnitDTO.getOrgName()+"%");
        }
        // 组织机构id
        if(!StringUtils.isBlank(goodsUnitDTO.getOrgId())){
            queryWrapper.eq("so.org_id",goodsUnitDTO.getOrgId());
        }
        return queryWrapper;
    }

    public void insertGoodsUnit(GoodsUnit goodsUnit) {
        goodsUnitMapper.insert(goodsUnit);
    }

    public void updateGoodsUnit(GoodsUnit goodsUnit) {
        this.goodsUnitMapper.updateById(goodsUnit);
    }

    public void deleteGoodsUnitById(String id) {
        this.goodsUnitMapper.deleteById(id);
    }

    public GoodsUnit findGoodsUnitById(String id) {
        GoodsUnit goodsUnit=goodsUnitMapper.findGoodsUnitById(id);
        return goodsUnit;
    }

}
