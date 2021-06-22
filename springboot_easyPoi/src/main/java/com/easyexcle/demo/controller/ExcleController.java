package com.easyexcle.demo.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.builder.ExcelReaderBuilder;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.easyexcle.demo.listener.UserListener;
import com.easyexcle.demo.pojo.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import static com.easyexcle.demo.excleUntil.ExcelUtil.writeExcelWithModel;

@RestController
public class ExcleController {

    /**
     * 学习网址；https://www.yuque.com/easyexcel/doc/read
     * 参考网站：https://www.jianshu.com/p/cfa6aa5c9329
     */

    @Resource
    private UserListener userListener;


    @GetMapping("test")
    public String test(){
        return "test ok";
    }

    /**
     * 直接下载 没有设置样式
     * @param response
     * @throws IOException
     */
    @GetMapping("download")
    public void downExcle(HttpServletResponse response) throws IOException {

        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        // 防止中文乱码
        String fileName = URLEncoder.encode("测试下载", "UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename*=UTF-8''" + fileName + ".xlsx");

        //响应的输入流
        ServletOutputStream outputStream = response.getOutputStream();

        // workbook
        ExcelWriterBuilder writeWorkBook = EasyExcel.write(outputStream, User.class);

        // sheet
        writeWorkBook.sheet().doWrite(initData());

    }

    /**
     * 测试模板类型 导出
     */
    @GetMapping("down2")
    public void downTest() throws FileNotFoundException {
        OutputStream outputStream = null;
        //指定位置
        outputStream = new FileOutputStream(new File("D:\\模板导出.xls"));
        String sheetName = "导出文件";
        writeExcelWithModel(outputStream, sheetName, initData(), User.class);
    }

    // 上传
    @RequestMapping("upload")
    @ResponseBody
    public String upload(MultipartFile excelFile) throws IOException {

        try {
            // 构建一个工作簿对象
            ExcelReaderBuilder readWorkBook = EasyExcel.read(excelFile.getInputStream(),
                    User.class, userListener);

            // 获取他的工作表 & 读
            readWorkBook.sheet().doRead();
            return "success";

        } catch (IOException e) {
            e.printStackTrace();
            return "fail";
        }
    }



    private List<User> initData(){
        List<User> list=new ArrayList<>();
        for(int i=0;i<=5;i++){
            User u=new User();
            u.setUser(i);
            u.setName("张"+i);
            u.setAge(i);
            list.add(u);
        }
        return list;
    }
}
