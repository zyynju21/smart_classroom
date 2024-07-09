package com.yiyue.service.wx;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yiyue.common.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.apache.commons.lang3.StringUtils;
import com.yiyue.model.bean.wx.NoticeAdvise;
import com.yiyue.model.dto.wx.NoticeAdviseDTO;
import com.yiyue.mapper.wx.NoticeAdviseMapper;

/**
* 
* author: liyun
* create date: 2022-01-12
*/

@Service
@Transactional
public class NoticeAdviseService {

    @Autowired
    private NoticeAdviseMapper noticeAdviseMapper;

    public IPage<NoticeAdvise> findNoticeAdviseListPageByParam(NoticeAdviseDTO noticeAdviseDTO) {
        // 从dto对象中获得查询条件，添加到queryWrapper对象中, 查询条件还需要视情况自行修改
        QueryWrapper<NoticeAdvise> queryWrapper=getQueryWrapper(noticeAdviseDTO);

        IPage<NoticeAdvise> noticeAdviseList=noticeAdviseMapper.findNoticeAdvisePageList(PageUtil.getPagination(noticeAdviseDTO),queryWrapper);
        return noticeAdviseList;
    }

    private QueryWrapper getQueryWrapper(NoticeAdviseDTO noticeAdviseDTO){
        QueryWrapper<NoticeAdvise> queryWrapper=new QueryWrapper<>();
        // 序号
        if(!StringUtils.isBlank(noticeAdviseDTO.getId())){
            queryWrapper.eq("id",noticeAdviseDTO.getId());
        }
        // 标题
        if(!StringUtils.isBlank(noticeAdviseDTO.getTitle())){
            queryWrapper.like("title","%"+noticeAdviseDTO.getTitle()+"%");
        }
        // 内容
        if(!StringUtils.isBlank(noticeAdviseDTO.getNoticeContent())){
            queryWrapper.eq("notice_content",noticeAdviseDTO.getNoticeContent());
        }
        // 时间
        if(!StringUtils.isBlank(noticeAdviseDTO.getCreateDate())){
            queryWrapper.eq("create_date",noticeAdviseDTO.getCreateDate());
        }
        return queryWrapper;
    }

    public void insertNoticeAdvise(NoticeAdvise noticeAdvise) {
        noticeAdviseMapper.insert(noticeAdvise);
    }

    public void updateNoticeAdvise(NoticeAdvise noticeAdvise) {
        this.noticeAdviseMapper.updateById(noticeAdvise);
    }

    public void deleteNoticeAdviseById(String id) {
        this.noticeAdviseMapper.deleteById(id);
    }

    public NoticeAdvise findNoticeAdviseById(String id) {
        return noticeAdviseMapper.selectById(id);
    }

}
