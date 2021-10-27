package com.demo.stratefy.service.impl;

import com.demo.stratefy.service.FileTypeResolveEnum;
import com.demo.stratefy.service.IFileStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author lkz
 * @date 2021/10/27 9:22
 * @description
 */
@Component
@Slf4j
public class BFileResolve implements IFileStrategy {

    @Override
    public FileTypeResolveEnum gainFileType() {
        return FileTypeResolveEnum.File_B_RESOLVE;
    }


    @Override
    public void resolve(Object objectparam) {
        log.info("B 类型解析文件，参数：{}",objectparam);
        //B类型解析具体逻辑
        System.out.println("bbbbbb");
    }
}
