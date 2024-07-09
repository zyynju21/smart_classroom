package com.yiyue.controller.system;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yiyue.common.result.Result;
import com.yiyue.model.dto.system.SystemLogPageDTO;
import com.yiyue.model.vo.system.SystemLogPageVO;
import com.yiyue.service.system.SystemLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author XiaoQi
 * @since 2021-07-24
 */
@RestController
@RequestMapping("/system_log")
public class SystemLogController {

    @Autowired
    private SystemLogService systemLogService;

    @PostMapping("/list")
    public Result<IPage<SystemLogPageVO>> list(@RequestBody SystemLogPageDTO systemLogPageDTO) {
        IPage<SystemLogPageVO> systemLogPageVoList = systemLogService.listSystemLogPage(systemLogPageDTO);
        return Result.success(systemLogPageVoList);
    }

}
