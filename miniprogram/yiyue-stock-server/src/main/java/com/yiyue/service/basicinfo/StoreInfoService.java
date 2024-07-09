package com.yiyue.service.basicinfo;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yiyue.common.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.apache.commons.lang3.StringUtils;
import com.yiyue.model.bean.basicinfo.StoreInfo;
import com.yiyue.model.dto.basicinfo.StoreInfoDTO;
import com.yiyue.mapper.basicinfo.StoreInfoMapper;

@Service
@Transactional
public class StoreInfoService {

    @Autowired
    private StoreInfoMapper storeInfoMapper;

    public IPage<StoreInfo> findStoreInfoListPageByParam(StoreInfoDTO storeInfoDTO) {
        // 从dto对象中获得查询条件，添加到queryWrapper对象中, 查询条件还需要视情况自行修改
        QueryWrapper<StoreInfo> queryWrapper=getQueryWrapper(storeInfoDTO);

        IPage<StoreInfo> storeInfoList=storeInfoMapper.findStoreInfoPageList(PageUtil.getPagination(storeInfoDTO),queryWrapper);
        return storeInfoList;
    }

    private QueryWrapper getQueryWrapper(StoreInfoDTO storeInfoDTO){
        QueryWrapper<StoreInfo> queryWrapper=new QueryWrapper<>();
        // 主键
        if(!StringUtils.isBlank(storeInfoDTO.getId())){
            queryWrapper.eq("id",storeInfoDTO.getId());
        }
        // 仓库名字
        if(!StringUtils.isBlank(storeInfoDTO.getStoreName())){
            queryWrapper.like("store_name","%"+storeInfoDTO.getStoreName()+"%");
        }
        // 负责人
        if(!StringUtils.isBlank(storeInfoDTO.getContact())){
            queryWrapper.like("contact","%"+storeInfoDTO.getContact()+"%");
        }
        // 联系电话
        if(!StringUtils.isBlank(storeInfoDTO.getPhone())){
            queryWrapper.like("phone","%"+storeInfoDTO.getPhone()+"%");
        }
        // 备注
        if(!StringUtils.isBlank(storeInfoDTO.getRemarks())){
            queryWrapper.like("remarks","%"+storeInfoDTO.getRemarks()+"%");
        }
        // 组织机构id
        if(!StringUtils.isBlank(storeInfoDTO.getOrgId())){
            queryWrapper.eq("so.org_id",storeInfoDTO.getOrgId());
        }
        if(!StringUtils.isBlank(storeInfoDTO.getOrgName())){
            queryWrapper.like("so.org_name","%"+storeInfoDTO.getOrgName()+"%");
        }
        return queryWrapper;
    }

    public void insertStoreInfo(StoreInfo storeInfo) {
        storeInfoMapper.insert(storeInfo);
    }

    public void updateStoreInfo(StoreInfo storeInfo) {
        this.storeInfoMapper.updateById(storeInfo);
    }

    public void deleteStoreInfoById(String id) {
        this.storeInfoMapper.deleteById(id);
    }

    public StoreInfo findStoreInfoById(String id) {
        StoreInfo storeInfo=storeInfoMapper.findStoreInfoById(id);
        return storeInfo;
    }

}
