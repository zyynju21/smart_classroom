package com.yiyue.controller.file;

import com.yiyue.common.exception.CustomException;
import com.yiyue.common.result.Result;
import com.yiyue.common.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Date;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/file")
public class FileUploadController {

    @Value("${upload.file-path}")
    private String uploadFilePath;

    @PostMapping("/upload")
    public Result upload(@RequestParam("file") MultipartFile file) throws Exception {
        if (file.isEmpty()) {
            throw new CustomException("上传文件不能为空");
        }
        String fileDir=uploadFilePath + "/" + DateUtils.formatMonth(new Date());
        File dir = new File(fileDir);
        if (!dir.exists()) {
            dir.mkdirs();  //判断并创建文件夹
        }
        //上传文件
        //String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        byte[] bytes = file.getBytes();
        String uuid = UUID.randomUUID().toString();
        String fileName=uuid + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."), file.getOriginalFilename().length());
        String savePath = dir.getAbsolutePath() + File.separator + fileName;
        File fileToSave = new File(savePath);
        FileCopyUtils.copy(bytes, fileToSave);

        String fileUrl=fileDir + "/" + fileName;
        return Result.success(fileUrl);
    }
}
