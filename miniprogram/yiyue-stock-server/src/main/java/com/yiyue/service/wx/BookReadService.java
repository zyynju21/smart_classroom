package com.yiyue.service.wx;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yiyue.common.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.apache.commons.lang3.StringUtils;
import com.yiyue.model.bean.wx.BookRead;
import com.yiyue.model.dto.wx.BookReadDTO;
import com.yiyue.mapper.wx.BookReadMapper;

/**
*
* author: liyun
* create date: 2022-01-19
*/

@Service
@Transactional
public class BookReadService {

    @Autowired
    private BookReadMapper bookReadMapper;

    public IPage<BookRead> findBookReadListPageByParam(BookReadDTO bookReadDTO) {
        // 从dto对象中获得查询条件，添加到queryWrapper对象中, 查询条件还需要视情况自行修改
        QueryWrapper<BookRead> queryWrapper=getQueryWrapper(bookReadDTO);

        IPage<BookRead> bookReadList=bookReadMapper.findBookReadPageList(PageUtil.getPagination(bookReadDTO),queryWrapper);
        return bookReadList;
    }

    private QueryWrapper getQueryWrapper(BookReadDTO bookReadDTO){
        QueryWrapper<BookRead> queryWrapper=new QueryWrapper<>();
        // 订单号
        if(!StringUtils.isBlank(bookReadDTO.getId())){
            queryWrapper.eq("id",bookReadDTO.getId());
        }
        // 用户
        if(!StringUtils.isBlank(bookReadDTO.getUserId())){
            queryWrapper.eq("user_id",bookReadDTO.getUserId());
        }
        if(!StringUtils.isBlank(bookReadDTO.getUserName())){
            queryWrapper.eq("s2.user_name",bookReadDTO.getUserName());
        }
        if(!StringUtils.isBlank(bookReadDTO.getBookName())){
            queryWrapper.like("s3.book_name","%"+bookReadDTO.getBookName()+"%");
        }
        // 图书id
        if(!StringUtils.isBlank(bookReadDTO.getLibraryId())){
            queryWrapper.eq("library_id",bookReadDTO.getLibraryId());
        }
        // 图书名称
        if(!StringUtils.isBlank(bookReadDTO.getLibraryName())){
            queryWrapper.eq("library_name",bookReadDTO.getLibraryName());
        }
        // 订单状态
        if(!StringUtils.isBlank(bookReadDTO.getOrderStatus())){
            queryWrapper.eq("order_status",bookReadDTO.getOrderStatus());
        }
        // 备注
        if(!StringUtils.isBlank(bookReadDTO.getBookRemark())){
            queryWrapper.eq("book_remark",bookReadDTO.getBookRemark());
        }
        // 借阅时间
        if(!StringUtils.isBlank(bookReadDTO.getStartDate())){
            queryWrapper.eq("start_date",bookReadDTO.getStartDate());
        }
        // 归还时间
        if(!StringUtils.isBlank(bookReadDTO.getEndDate())){
            queryWrapper.eq("end_date",bookReadDTO.getEndDate());
        }
        // 创建时间
        if(!StringUtils.isBlank(bookReadDTO.getCreateDate())){
            queryWrapper.eq("create_date",bookReadDTO.getCreateDate());
        }
        return queryWrapper;
    }

    public void insertBookRead(BookRead bookRead) {
        bookReadMapper.insert(bookRead);
    }

    public void updateBookRead(BookRead bookRead) {
        this.bookReadMapper.updateById(bookRead);
    }

    public void deleteBookReadById(String id) {
        this.bookReadMapper.deleteById(id);
    }

    public BookRead findBookReadById(String id) {
        return bookReadMapper.selectById(id);
    }

}
