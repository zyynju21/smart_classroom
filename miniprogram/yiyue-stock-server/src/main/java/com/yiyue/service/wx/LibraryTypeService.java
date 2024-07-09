package com.yiyue.service.wx;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yiyue.common.util.PageUtil;
import com.yiyue.common.vo.ItemVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.apache.commons.lang3.StringUtils;
import com.yiyue.model.bean.wx.LibraryType;
import com.yiyue.model.dto.wx.LibraryTypeDTO;
import com.yiyue.mapper.wx.LibraryTypeMapper;

import java.util.ArrayList;
import java.util.List;

/**
*
* author: liyun
* create date: 2022-01-12
*/

@Service
@Transactional
public class LibraryTypeService {

    @Autowired
    private LibraryTypeMapper libraryTypeMapper;

    public IPage<LibraryType> findLibraryTypeListPageByParam(LibraryTypeDTO libraryTypeDTO) {
        // 从dto对象中获得查询条件，添加到queryWrapper对象中, 查询条件还需要视情况自行修改
        QueryWrapper<LibraryType> queryWrapper=getQueryWrapper(libraryTypeDTO);

        IPage<LibraryType> libraryTypeList=libraryTypeMapper.findLibraryTypePageList(PageUtil.getPagination(libraryTypeDTO),queryWrapper);
        return libraryTypeList;
    }

    private QueryWrapper getQueryWrapper(LibraryTypeDTO libraryTypeDTO){
        QueryWrapper<LibraryType> queryWrapper=new QueryWrapper<>();
        // 序号
        if(!StringUtils.isBlank(libraryTypeDTO.getId())){
            queryWrapper.eq("id",libraryTypeDTO.getId());
        }
        // 图片
        if(!StringUtils.isBlank(libraryTypeDTO.getLibraryImg())){
            queryWrapper.eq("library_img",libraryTypeDTO.getLibraryImg());
        }
        // 分类
        if(!StringUtils.isBlank(libraryTypeDTO.getLibraryName())){
            queryWrapper.like("library_name","%"+libraryTypeDTO.getLibraryName()+"%");
        }
        // 位置
        if(!StringUtils.isBlank(libraryTypeDTO.getAddName())){
            queryWrapper.eq("add_name",libraryTypeDTO.getAddName());
        }
        // 营业时间
        if(!StringUtils.isBlank(libraryTypeDTO.getBusinessDate())){
            queryWrapper.eq("business_date",libraryTypeDTO.getBusinessDate());
        }
        // 介绍
        if(!StringUtils.isBlank(libraryTypeDTO.getLibraryInfo())){
            queryWrapper.eq("library_info",libraryTypeDTO.getLibraryInfo());
        }
        // 座位
        if(!StringUtils.isBlank(libraryTypeDTO.getSeatId())){
            queryWrapper.eq("seat_id",libraryTypeDTO.getSeatId());
        }
        return queryWrapper;
    }

    public void insertLibraryType(LibraryType libraryType) {
        libraryTypeMapper.insert(libraryType);
    }

    public void updateLibraryType(LibraryType libraryType) {
        this.libraryTypeMapper.updateById(libraryType);
    }

    public void deleteLibraryTypeById(String id) {
        this.libraryTypeMapper.deleteById(id);
    }

    public LibraryType findLibraryTypeById(String id) {
        return libraryTypeMapper.selectById(id);
    }

    public List<ItemVO> findLibraryName() {
        List<LibraryType> libraryNameList=libraryTypeMapper.selectList(null);
        List<ItemVO> list=new ArrayList<>();
        libraryNameList.forEach(item->{
            ItemVO vo=new ItemVO();
            vo.setKey(String.valueOf(item.getId()));
            vo.setTitle(item.getLibraryName());
            vo.setValue(String.valueOf(item.getId()));
            list.add(vo);
        });
        return list;
    }
}
