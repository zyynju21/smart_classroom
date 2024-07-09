package com.yiyue.mapper.basicinfo;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Component;
import com.yiyue.model.bean.basicinfo.GoodsType;

import java.util.List;


@Component
@MapperScan
public interface GoodsTypeMapper extends BaseMapper<GoodsType> {

    @Select("select s1.*,so.org_name from t_goods_type s1 inner join sys_organ so on s1.org_id=so.org_id  ${ew.customSqlSegment}")
    IPage<GoodsType> findGoodsTypePageList(Page<GoodsType> pagination, @Param("ew") Wrapper<GoodsType> wrapper);

    @Select("select s1.*,so.org_name from t_goods_type s1 inner join sys_organ so on s1.org_id=so.org_id  ${ew.customSqlSegment}")
    List<GoodsType> findGoodsTypeList(@Param("ew") QueryWrapper<GoodsType> queryWrapper);

    @Select("select s1.*,so.org_name from t_goods_type s1 inner join sys_organ so on s1.org_id=so.org_id where id=#{id}")
    GoodsType findGoodsTypeById(@Param("id") String id);
}
