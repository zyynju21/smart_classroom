package com.yiyue.mapper.wx;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Component;
import com.yiyue.model.bean.wx.BooksManger;

/**
* 
* author: liyun
* create date: 2022-01-19
*/

@Component
@MapperScan
public interface BooksMangerMapper extends BaseMapper<BooksManger> {

    @Select("select s1.* from books_manger s1  ${ew.customSqlSegment}")
    IPage<BooksManger> findBooksMangerPageList(Page<BooksManger> pagination, @Param("ew") Wrapper<BooksManger> wrapper);

}
