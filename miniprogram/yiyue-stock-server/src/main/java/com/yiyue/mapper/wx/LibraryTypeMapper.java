package com.yiyue.mapper.wx;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Component;
import com.yiyue.model.bean.wx.LibraryType;

/**
* 
* author: liyun
* create date: 2022-01-12
*/

@Component
@MapperScan
public interface LibraryTypeMapper extends BaseMapper<LibraryType> {

    @Select("select s1.* from library_type s1  ${ew.customSqlSegment}")
    IPage<LibraryType> findLibraryTypePageList(Page<LibraryType> pagination, @Param("ew") Wrapper<LibraryType> wrapper);

}
