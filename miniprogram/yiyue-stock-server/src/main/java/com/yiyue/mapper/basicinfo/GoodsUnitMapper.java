package com.yiyue.mapper.basicinfo;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Component;
import com.yiyue.model.bean.basicinfo.GoodsUnit;


@Component
@MapperScan
public interface GoodsUnitMapper extends BaseMapper<GoodsUnit> {

    @Select("select s1.*,so.org_name from t_goods_unit s1 inner join sys_organ so on s1.org_id=so.org_id ${ew.customSqlSegment}")
    IPage<GoodsUnit> findGoodsUnitPageList(Page<GoodsUnit> pagination, @Param("ew") Wrapper<GoodsUnit> wrapper);

    @Select("select s1.*,so.org_name from t_goods_unit s1 inner join sys_organ so on s1.org_id=so.org_id where s1.id=#{id}")
    GoodsUnit findGoodsUnitById(@Param("id") String id);
}
