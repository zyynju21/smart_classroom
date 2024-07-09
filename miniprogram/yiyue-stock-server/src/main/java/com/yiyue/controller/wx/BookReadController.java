package com.yiyue.controller.wx;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yiyue.common.aspact.SystemPlatLog;
import com.yiyue.common.result.Result;
import com.yiyue.model.bean.wx.BooksManger;
import com.yiyue.service.wx.BooksMangerService;
import lombok.extern.slf4j.Slf4j;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.yiyue.service.wx.BookReadService;
import com.yiyue.model.dto.wx.BookReadDTO;
import com.yiyue.model.bean.wx.BookRead;
import springfox.documentation.annotations.ApiIgnore;

/**
*
* author: liyun
* create date: 2022-01-19
*/
@ApiIgnore()
@Api(value="controller",tags={"操作接口"})
@Slf4j
@RestController
@RequestMapping("/wx/bookRead")
public class BookReadController {

    @Autowired
    private BookReadService bookReadService;

    @Autowired
    private BooksMangerService booksMangerService;

    /**
    * 查询列表
    * @param bookReadDTO
    * @return
    */
    @ApiOperation(value="查询列表",notes=" ")
    @SystemPlatLog
    @PostMapping(value = "/list",produces = "application/json;charset=utf-8")
    public Result list(@RequestBody BookReadDTO bookReadDTO){
        IPage<BookRead> bookReadList=bookReadService.findBookReadListPageByParam(bookReadDTO);
        return Result.success(bookReadList);
    }

    /**
    * 新增
    * @param bookRead
    * @return
    */
    @ApiOperation(value="新增",notes=" ")
    @SystemPlatLog
    @PostMapping(value = "/create",produces = "application/json;charset=utf-8")
    public Result create(@RequestBody BookRead bookRead){
        BooksManger booksManger = new BooksManger();
        booksManger.setId(bookRead.getLibraryId());
        booksManger.setUseState("1");
        booksMangerService.updateBooksManger(booksManger);
        bookReadService.insertBookRead(bookRead);
        return Result.success();
    }

    /**
    * 修改
    * @param bookRead
    * @return
    */
    @ApiOperation(value="修改",notes=" ")
    @SystemPlatLog
    @PutMapping(value = "/update",produces = "application/json;charset=utf-8")
    public Result update(@RequestBody BookRead bookRead){
        BooksManger booksManger = new BooksManger();
        booksManger.setId(bookRead.getLibraryId());
        booksManger.setUseState("0");
        booksMangerService.updateBooksManger(booksManger);
        bookReadService.updateBookRead(bookRead);
        return Result.success();
    }

    /**
    * 删除操作
    * @param id
    * @return
    */
    @ApiOperation(value="删除",notes=" ")
    @SystemPlatLog
    @DeleteMapping(value = "/delete/{id}",produces = "application/json;charset=utf-8")
    public Result delete(@PathVariable String id){
        bookReadService.deleteBookReadById(id);
        return Result.success();
    }

    /**
    * 根据id查询，返回实体bean
    * @param id
    * @return
    */
    @ApiOperation(value="根据id查询",notes=" ")
    @SystemPlatLog
    @GetMapping(value = "/get_by_id",produces = "application/json;charset=utf-8")
    public Result get_by_id(@RequestParam String id){
        BookRead bookRead=bookReadService.findBookReadById(id);
        return Result.success(bookRead);
    }

}
