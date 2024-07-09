package com.yiyue.service.basicinfo;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yiyue.common.util.PageUtil;
import com.yiyue.model.vo.basicinfo.CustomerVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.apache.commons.lang3.StringUtils;
import com.yiyue.model.bean.basicinfo.Customer;
import com.yiyue.model.dto.basicinfo.CustomerDTO;
import com.yiyue.mapper.basicinfo.CustomerMapper;

/**
* 客户表
* author: liyun
* create date: 2021-10-13
*/

@Service
@Transactional
public class CustomerService {

    @Autowired
    private CustomerMapper customerMapper;

    public IPage<CustomerVO> findCustomerListPageByParam(CustomerDTO customerDTO) {
        // 从dto对象中获得查询条件，添加到queryWrapper对象中, 查询条件还需要视情况自行修改
        QueryWrapper<CustomerVO> queryWrapper=getQueryWrapper(customerDTO);

        IPage<CustomerVO> customerList=customerMapper.findCustomerPageList(PageUtil.getPagination(customerDTO),queryWrapper);
        return customerList;
    }

    private QueryWrapper getQueryWrapper(CustomerDTO customerDTO){
        QueryWrapper<Customer> queryWrapper=new QueryWrapper<>();
        // 主键
        if(!StringUtils.isBlank(customerDTO.getId())){
            queryWrapper.eq("id",customerDTO.getId());
        }
        // 省份
        if(!StringUtils.isBlank(customerDTO.getProvince())){
            queryWrapper.like("province","%"+customerDTO.getProvince()+"%");
        }
        // 城市
        if(!StringUtils.isBlank(customerDTO.getCity())){
            queryWrapper.like("city","%"+customerDTO.getCity()+"%");
        }
        // 区县
        if(!StringUtils.isBlank(customerDTO.getCounty())){
            queryWrapper.like("county","%"+customerDTO.getCounty()+"%");
        }
        // 客户地址
        if(!StringUtils.isBlank(customerDTO.getAddress())){
            queryWrapper.like("address","%"+customerDTO.getAddress()+"%");
        }
        // 联系人
        if(!StringUtils.isBlank(customerDTO.getContact())){
            queryWrapper.like("contact","%"+customerDTO.getContact()+"%");
        }
        // 客户名称
        if(!StringUtils.isBlank(customerDTO.getName())){
            queryWrapper.like("name","%"+customerDTO.getName()+"%");
        }
        // 客户联系电话
        if(!StringUtils.isBlank(customerDTO.getPhone())){
            queryWrapper.like("phone","%"+customerDTO.getPhone()+"%");
        }
        // 备注
        if(!StringUtils.isBlank(customerDTO.getRemarks())){
            queryWrapper.like("remarks","%"+customerDTO.getRemarks()+"%");
        }
        // 是否删除
        if(!StringUtils.isBlank(customerDTO.getIsDel())){
            queryWrapper.eq("is_del",customerDTO.getIsDel());
        }
        // 组织机构id
        if(!StringUtils.isBlank(customerDTO.getOrgId())){
            queryWrapper.eq("so.org_id",customerDTO.getOrgId());
        }
        // 组织机构名
        if(!StringUtils.isBlank(customerDTO.getOrgName())){
            queryWrapper.like("so.org_name","%"+customerDTO.getOrgName()+"%");
        }
        return queryWrapper;
    }

    public void insertCustomer(Customer customer) {
        customerMapper.insert(customer);
    }

    public void updateCustomer(Customer customer) {
        this.customerMapper.updateById(customer);
    }

    public void deleteCustomerById(String id) {
        this.customerMapper.deleteById(id);
    }

    public Customer findCustomerById(String id) {
        Customer customer=this.customerMapper.findCustomerById(id);
        return customer;
    }

}
