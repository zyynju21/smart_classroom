package com.yiyue.mapper.basicinfo;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yiyue.model.vo.basicinfo.SupplierVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Component;
import com.yiyue.model.bean.basicinfo.Supplier;

/**
* 供应商表
* author: liyun
* create date: 2021-10-13
*/

@Component
@MapperScan
public interface SupplierMapper extends BaseMapper<Supplier> {

    @Select("select s1.*,CONCAT(s1.province,s1.city,s1.county,s1.address) full_address,so.org_name from t_supplier s1 inner join sys_organ so on s1.org_id=so.org_id  ${ew.customSqlSegment}")
    IPage<SupplierVO> findSupplierPageList(Page<SupplierVO> pagination, @Param("ew") Wrapper<SupplierVO> wrapper);

    @Select("select s1.*,so.org_name from t_supplier s1 inner join sys_organ so on s1.org_id=so.org_id where s1.id=#{id}")
    Supplier findSupplierById(@Param("id") String id);
}
