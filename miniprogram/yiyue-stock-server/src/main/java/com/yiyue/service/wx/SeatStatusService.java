package com.yiyue.service.wx;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yiyue.common.util.PageUtil;
import com.yiyue.common.vo.ItemVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.apache.commons.lang3.StringUtils;
import com.yiyue.model.bean.wx.SeatStatus;
import com.yiyue.model.dto.wx.SeatStatusDTO;
import com.yiyue.mapper.wx.SeatStatusMapper;

import java.util.ArrayList;
import java.util.List;

/**
*
* author: liyun
* create date: 2022-01-13
*/

@Service
@Transactional
public class SeatStatusService {

    @Autowired
    private SeatStatusMapper seatStatusMapper;


    public IPage<SeatStatus> findSeatStatusListPageByParam(SeatStatusDTO seatStatusDTO) {
        // 从dto对象中获得查询条件，添加到queryWrapper对象中, 查询条件还需要视情况自行修改
        QueryWrapper<SeatStatus> queryWrapper=getQueryWrapper(seatStatusDTO);

        IPage<SeatStatus> seatStatusList=seatStatusMapper.findSeatStatusPageList(PageUtil.getPagination(seatStatusDTO),queryWrapper);
        return seatStatusList;
    }

    private QueryWrapper getQueryWrapper(SeatStatusDTO seatStatusDTO){
        QueryWrapper<SeatStatus> queryWrapper=new QueryWrapper<>();
        // 序号
        if(!StringUtils.isBlank(seatStatusDTO.getId())){
            queryWrapper.eq("id",seatStatusDTO.getId());
        }
        // 状态（0空闲；1预约；2占用）
        if(!StringUtils.isBlank(seatStatusDTO.getStatus())){
            queryWrapper.eq("status",seatStatusDTO.getStatus());
        }
        // 座位
        if(!StringUtils.isBlank(seatStatusDTO.getSeatName())){
            queryWrapper.eq("seat_name",seatStatusDTO.getSeatName());
        }
        // 图书馆
        if(!StringUtils.isBlank(seatStatusDTO.getLibraryType())){
            queryWrapper.eq("library_type",seatStatusDTO.getLibraryType());
        }
        return queryWrapper;
    }

    public void insertSeatStatus(SeatStatus seatStatus) {
        seatStatusMapper.insert(seatStatus);
    }

    public void updateSeatStatus(SeatStatus seatStatus) {
        this.seatStatusMapper.updateById(seatStatus);
    }

    public void deleteSeatStatusById(String id) {
        this.seatStatusMapper.deleteById(id);
    }

    public SeatStatus findSeatStatusById(String id) {
        return seatStatusMapper.selectById(id);
    }

    public List<ItemVO> findSeatListName(String typeId) {
        ArrayList<ItemVO> arrayList = new ArrayList<>();
        QueryWrapper<SeatStatus> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("library_type",typeId);
        queryWrapper.eq("status",0);
        List<SeatStatus> seatStatusList=this.seatStatusMapper.selectList(queryWrapper);
        seatStatusList.forEach(item->{
            ItemVO itemVO = new ItemVO();
            itemVO.setKey(item.getId()+"");
            itemVO.setValue(item.getId()+"");
            itemVO.setTitle(item.getSeatName());
            arrayList.add(itemVO);
        });
        return arrayList;
    }

    public List<SeatStatus> findSeatListGetLibrary(int id) {
        QueryWrapper<SeatStatus> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("library_type",id);
        List<SeatStatus> list = seatStatusMapper.selectList(queryWrapper);
        return list;
    }

}
