package com.yiyue.controller.wx;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yiyue.common.aspact.SystemPlatLog;
import com.yiyue.common.result.Result;
import lombok.extern.slf4j.Slf4j;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.yiyue.service.wx.NoticeAdviseService;
import com.yiyue.model.dto.wx.NoticeAdviseDTO;
import com.yiyue.model.bean.wx.NoticeAdvise;
import springfox.documentation.annotations.ApiIgnore;

/**
* 
* author: liyun
* create date: 2022-01-12
*/
@ApiIgnore()
@Api(value="controller",tags={"操作接口"})
@Slf4j
@RestController
@RequestMapping("/wx/noticeAdvise")
public class NoticeAdviseController {

    @Autowired
    private NoticeAdviseService noticeAdviseService;

    /**
    * 查询列表
    * @param noticeAdviseDTO
    * @return
    */
    @ApiOperation(value="查询列表",notes=" ")
    @SystemPlatLog
    @PostMapping(value = "/list",produces = "application/json;charset=utf-8")
    public Result list(@RequestBody NoticeAdviseDTO noticeAdviseDTO){
        IPage<NoticeAdvise> noticeAdviseList=noticeAdviseService.findNoticeAdviseListPageByParam(noticeAdviseDTO);
        return Result.success(noticeAdviseList);
    }

    /**
    * 新增
    * @param noticeAdvise
    * @return
    */
    @ApiOperation(value="新增",notes=" ")
    @SystemPlatLog
    @PostMapping(value = "/create",produces = "application/json;charset=utf-8")
    public Result create(@RequestBody NoticeAdvise noticeAdvise){
        noticeAdviseService.insertNoticeAdvise(noticeAdvise);
        return Result.success();
    }

    /**
    * 修改
    * @param noticeAdvise
    * @return
    */
    @ApiOperation(value="修改",notes=" ")
    @SystemPlatLog
    @PutMapping(value = "/update",produces = "application/json;charset=utf-8")
    public Result update(@RequestBody NoticeAdvise noticeAdvise){
        noticeAdviseService.updateNoticeAdvise(noticeAdvise);
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
        noticeAdviseService.deleteNoticeAdviseById(id);
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
        NoticeAdvise noticeAdvise=noticeAdviseService.findNoticeAdviseById(id);
        return Result.success(noticeAdvise);
    }

}
