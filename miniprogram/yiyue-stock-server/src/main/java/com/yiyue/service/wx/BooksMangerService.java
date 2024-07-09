package com.yiyue.service.wx;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yiyue.common.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.apache.commons.lang3.StringUtils;
import com.yiyue.model.bean.wx.BooksManger;
import com.yiyue.model.dto.wx.BooksMangerDTO;
import com.yiyue.mapper.wx.BooksMangerMapper;

/**
* 
* author: liyun
* create date: 2022-01-19
*/

@Service
@Transactional
public class BooksMangerService {

    @Autowired
    private BooksMangerMapper booksMangerMapper;

    public IPage<BooksManger> findBooksMangerListPageByParam(BooksMangerDTO booksMangerDTO) {
        // 从dto对象中获得查询条件，添加到queryWrapper对象中, 查询条件还需要视情况自行修改
        QueryWrapper<BooksManger> queryWrapper=getQueryWrapper(booksMangerDTO);

        IPage<BooksManger> booksMangerList=booksMangerMapper.findBooksMangerPageList(PageUtil.getPagination(booksMangerDTO),queryWrapper);
        return booksMangerList;
    }

    private QueryWrapper getQueryWrapper(BooksMangerDTO booksMangerDTO){
        QueryWrapper<BooksManger> queryWrapper=new QueryWrapper<>();
        // 序号
        if(!StringUtils.isBlank(booksMangerDTO.getId())){
            queryWrapper.eq("id",booksMangerDTO.getId());
        }
        // 书名
        if(!StringUtils.isBlank(booksMangerDTO.getBookName())){
            queryWrapper.like("book_name","%"+booksMangerDTO.getBookName()+"%");
        }
        // 图片
        if(!StringUtils.isBlank(booksMangerDTO.getBookImg())){
            queryWrapper.eq("book_img",booksMangerDTO.getBookImg());
        }
        // 内容
        if(!StringUtils.isBlank(booksMangerDTO.getBookText())){
            queryWrapper.eq("book_text",booksMangerDTO.getBookText());
        }
        // 借阅状态(0,可借，1已借)
        if(!StringUtils.isBlank(booksMangerDTO.getUseState())){
            queryWrapper.eq("use_state",booksMangerDTO.getUseState());
        }
        // 备注
        if(!StringUtils.isBlank(booksMangerDTO.getBookMark())){
            queryWrapper.eq("book_mark",booksMangerDTO.getBookMark());
        }
        // 创建时间
        if(!StringUtils.isBlank(booksMangerDTO.getCreateDate())){
            queryWrapper.eq("create_date",booksMangerDTO.getCreateDate());
        }
        return queryWrapper;
    }

    public void insertBooksManger(BooksManger booksManger) {
        booksMangerMapper.insert(booksManger);
    }

    public void updateBooksManger(BooksManger booksManger) {
        this.booksMangerMapper.updateById(booksManger);
    }

    public void deleteBooksMangerById(String id) {
        this.booksMangerMapper.deleteById(id);
    }

    public BooksManger findBooksMangerById(String id) {
        return booksMangerMapper.selectById(id);
    }

}
