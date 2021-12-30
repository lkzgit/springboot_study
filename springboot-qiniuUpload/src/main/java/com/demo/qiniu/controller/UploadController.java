package com.demo.qiniu.controller;

import com.demo.qiniu.service.UploadImageService;
import com.demo.qiniu.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author lkz
 * @ClassName: UploadController
 * @description: TODO
 * @date 2021/12/29 10:40
 */
@Slf4j
@RestController
@RequestMapping("/qiniu")
@CrossOrigin
public class UploadController {

    @Resource
    UploadImageService uploadImageService;

    @PostMapping(value = "/image")
    private String upLoadImage(@RequestParam("file") MultipartFile file) throws IOException {

        // 获取文件的名称
        String fileName = file.getOriginalFilename();

        // 使用工具类根据上传文件生成唯一图片名称
        String imgName = StringUtil.getRandomImgName(fileName);

        if (!file.isEmpty()) {

            FileInputStream inputStream = (FileInputStream) file.getInputStream();

            String path = uploadImageService.uploadQNImg(inputStream, imgName);
            System.out.print("七牛云返回的图片链接:" + path);
            return path;
        }
        return "上传失败";
    }

}
