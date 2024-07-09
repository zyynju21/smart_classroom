package com.yiyue.controller.basicinfo;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yiyue.common.aspact.SystemPlatLog;
import com.yiyue.common.result.Result;
import com.yiyue.interceptor.SystemUserContextHolder;
import com.yiyue.model.bean.system.SysOrgan;
import com.yiyue.model.vo.basicinfo.CustomerVO;
import com.yiyue.model.vo.system.LoginUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.yiyue.service.basicinfo.CustomerService;
import com.yiyue.model.dto.basicinfo.CustomerDTO;
import com.yiyue.model.bean.basicinfo.Customer;

/**
* 客户表
* author: liyun
* create date: 2021-10-13
*/
@Slf4j
@RestController
@RequestMapping("/basicinfo/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    /**
    * 查询列表
    * @param customerDTO
    * @return
    */
    @SystemPlatLog
    @PostMapping(value = "/list",produces = "application/json;charset=utf-8")
    public Result list(@RequestBody CustomerDTO customerDTO){
        LoginUser loginUser=SystemUserContextHolder.getLoginUser();
        SysOrgan organ=loginUser.getSysOrgan();
        if(organ.getOrgType()==0){
            customerDTO.setOrgId(null);
        }else{
            customerDTO.setOrgId(organ.getOrgId());
        }
        IPage<CustomerVO> customerList=customerService.findCustomerListPageByParam(customerDTO);
        return Result.success(customerList);
    }

    /**
    * 新增
    * @param customer
    * @return
    */
    @SystemPlatLog
    @PostMapping(value = "/create",produces = "application/json;charset=utf-8")
        public Result create(@RequestBody Customer customer){
        customerService.insertCustomer(customer);
        return Result.success();
    }

    /**
    * 修改
    * @param customer
    * @return
    */
    @SystemPlatLog
    @PutMapping(value = "/update",produces = "application/json;charset=utf-8")
    public Result update(@RequestBody Customer customer){
        customerService.updateCustomer(customer);
        return Result.success();
    }

    /**
    * 删除操作
    * @param id
    * @return
    */
    @SystemPlatLog
    @DeleteMapping(value = "/delete/{id}",produces = "application/json;charset=utf-8")
    public Result delete(@PathVariable String id){
        customerService.deleteCustomerById(id);
        return Result.success();
    }

    /**
    * 根据id查询，返回实体bean
    * @param id
    * @return
    */
    @SystemPlatLog
    @GetMapping(value = "/get_by_id",produces = "application/json;charset=utf-8")
    public Result get_by_id(@RequestParam String id){
        Customer customer=customerService.findCustomerById(id);
        return Result.success(customer);
    }

}
