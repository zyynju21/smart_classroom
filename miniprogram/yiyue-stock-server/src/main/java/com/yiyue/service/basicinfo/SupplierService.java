package com.yiyue.service.basicinfo;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yiyue.common.util.PageUtil;
import com.yiyue.model.vo.basicinfo.SupplierVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.apache.commons.lang3.StringUtils;
import com.yiyue.model.bean.basicinfo.Supplier;
import com.yiyue.model.dto.basicinfo.SupplierDTO;
import com.yiyue.mapper.basicinfo.SupplierMapper;

/**
* 供应商表
* author: liyun
* create date: 2021-10-13
*/

@Service
@Transactional
public class SupplierService {

    @Autowired
    private SupplierMapper supplierMapper;

    public IPage<SupplierVO> findSupplierListPageByParam(SupplierDTO supplierDTO) {
        // 从dto对象中获得查询条件，添加到queryWrapper对象中, 查询条件还需要视情况自行修改
        QueryWrapper<SupplierVO> queryWrapper=getQueryWrapper(supplierDTO);

        IPage<SupplierVO> supplierList=supplierMapper.findSupplierPageList(PageUtil.getPagination(supplierDTO),queryWrapper);
        return supplierList;
    }

    private QueryWrapper getQueryWrapper(SupplierDTO supplierDTO){
        QueryWrapper<Supplier> queryWrapper=new QueryWrapper<>();
        // 主键
        if(!StringUtils.isBlank(supplierDTO.getId())){
            queryWrapper.eq("id",supplierDTO.getId());
        }
        // 省份
        if(!StringUtils.isBlank(supplierDTO.getProvince())){
            queryWrapper.like("province","%"+supplierDTO.getProvince()+"%");
        }
        // 城市
        if(!StringUtils.isBlank(supplierDTO.getCity())){
            queryWrapper.like("city","%"+supplierDTO.getCity()+"%");
        }
        // 区县
        if(!StringUtils.isBlank(supplierDTO.getCounty())){
            queryWrapper.like("county","%"+supplierDTO.getCounty()+"%");
        }
        // 联系地址
        if(!StringUtils.isBlank(supplierDTO.getAddress())){
            queryWrapper.like("address","%"+supplierDTO.getAddress()+"%");
        }
        // 联系人
        if(!StringUtils.isBlank(supplierDTO.getContact())){
            queryWrapper.like("contact","%"+supplierDTO.getContact()+"%");
        }
        // 供应商名称
        if(!StringUtils.isBlank(supplierDTO.getName())){
            queryWrapper.like("name",supplierDTO.getName());
        }
        // 联系电话
        if(!StringUtils.isBlank(supplierDTO.getPhone())){
            queryWrapper.like("phone","%"+supplierDTO.getPhone()+"%");
        }
        // 备注
        if(!StringUtils.isBlank(supplierDTO.getRemarks())){
            queryWrapper.like("remarks","%"+supplierDTO.getRemarks()+"%");
        }
        // 是否删除
        if(!StringUtils.isBlank(supplierDTO.getIsDel())){
            queryWrapper.eq("is_del",supplierDTO.getIsDel());
        }
        // 组织机构id
        if(!StringUtils.isBlank(supplierDTO.getOrgId())){
            queryWrapper.eq("so.org_id",supplierDTO.getOrgId());
        }
        // 组织机构名
        if(!StringUtils.isBlank(supplierDTO.getOrgName())){
            queryWrapper.like("so.org_name","%"+supplierDTO.getOrgName()+"%");
        }
        return queryWrapper;
    }

    public void insertSupplier(Supplier supplier) {
        supplierMapper.insert(supplier);
    }

    public void updateSupplier(Supplier supplier) {
        this.supplierMapper.updateById(supplier);
    }

    public void deleteSupplierById(String id) {
        this.supplierMapper.deleteById(id);
    }

    public Supplier findSupplierById(String id) {
        Supplier supplier=supplierMapper.findSupplierById(id);
        return supplier;
    }

}
