package com.demo.word.utils;

import cn.afterturn.easypoi.word.WordExportUtil;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author lkz
 * @date 2021/9/23 10:28
 * @description
 */
public class WordUtils {



    /**
     * 导出word 任意格式
     * <p>第一步生成替换后的word文件，只支持docx</p>
     * <p>第二步下载生成的文件</p>
     * <p>第三步删除生成的临时文件</p>
     * 模版变量中变量格式：{{foo}}
     * @param templatePath word模板地址
     * @param temDir 生成临时文件存放地址
     * @param fileName 文件名
     * @param params 替换的参数
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     */
    public static void exportWord(String templatePath, String temDir, String fileName, Map<String, Object> params, HttpServletRequest request, HttpServletResponse response) {
        Assert.notNull(templatePath,"模板路径不能为空");
        Assert.notNull(temDir,"临时文件路径不能为空");
        Assert.notNull(fileName,"导出文件名不能为空");
        Assert.isTrue(fileName.endsWith(".docx"),"word导出请使用docx格式");
        if (!temDir.endsWith("/")){
            temDir = temDir + File.separator;
        }
        File dir = new File(temDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        try {
            String userAgent = request.getHeader("user-agent").toLowerCase();
//            if (userAgent.contains("msie") || userAgent.contains("like gecko")) {
//                fileName = URLEncoder.encode(fileName, "UTF-8");
//            } else {
//                fileName = new String(fileName.getBytes("utf-8"), "ISO-8859-1");
//            }
            XWPFDocument doc = WordExportUtil.exportWord07(templatePath, params);
            String tmpPath = temDir + fileName;
            FileOutputStream fos = new FileOutputStream(tmpPath);
            doc.write(fos);

            // 设置强制下载不打开
            response.setContentType("application/force-download");
            // 设置文件名
            response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);
            OutputStream out = response.getOutputStream();
            doc.write(out);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
           // delFileUtil(temDir);//这一步看具体需求，要不要删
        }
    }

    /**
     * 文件删除
     *
     * @param path
     * @return boolean
     * @author wangr
     * @date 2020/12/15 16:14
     */
    public static boolean delFileUtil(String path) {
        File file = new File(path);
        if (file.exists() && file.isFile()) {
            // 删除文件
            file.delete();
        }
        return false;
    }




//        /**
//         * @Author lkz
//         * @Description //TODO 替换文本中的字段
//         * @Date 10:30 2021/9/23
//         * @Param [map 要替换的数据, path 路径, fileName 文件名]
//         * @return java.io.File
//         **/
//    public static File  readWriteWord(Map<String, String> map, String path, String fileName){
//        // 读取word模板文件
//        InputStream in = null;
//
//        XWPFDocument hdt = null;
//        try {
//            in = new FileInputStream(new File(path + fileName));
//            // 转换成文档
//            hdt = new XWPFDocument(in);
//            in.close();
//        } catch (Exception e1) {
//            e1.printStackTrace();
//        }
//        // 替换段落中的指定文字
//        Iterator<XWPFParagraph> itPara = hdt.getParagraphsIterator();
//        while (itPara.hasNext()) {
//            XWPFParagraph paragraph = (XWPFParagraph) itPara.next();
//            List<XWPFRun> runs;
//            Matcher matcher;
//
//            if (matcher(paragraph.getParagraphText()).find()) {
//                runs = paragraph.getRuns();
//                for (int i = 0; i < runs.size(); i++) {
//                    XWPFRun run = runs.get(i);
//                    String runText = run.toString();
//                    int index = 1;
//                    //判断获取是否为$，如果是则进行拼接
//                    if ("$".equals(runText)) {
//                        //循环拼接
//                        for (int j = 0; j < index; j++) {
//                            //如果大小大于字段数则退出循环
//                            if(i+index>=runs.size()) {
//                                break;
//                            }
//                            XWPFRun run1 = runs.get(i + index);
//                            runText += run1.toString();
//                            //如果拼接的字符不是}，那么继续拼接
//                            if (!"}".equals(run1.toString())) {
//                                index++;
//                            }
//                        }
//                    }
//                    //判断拼接好的字段是否是需要替换的
//                    if(map.get(runText)!=null) {
//                        matcher = matcher(runText);
//                        if (matcher.find()) {
//                            runText = map.get(runText);
//                            //开始剔除需要替换的字符
//                            for (int j = 0; j < index + 1; j++) {
//                                paragraph.removeRun(i);
//                            }
//                            // 重新插入run里内容格式可能与原来模板的格式不一致
//                            paragraph.insertNewRun(i).setText(runText);
//                        }
//                    }
//                }
//            }
//        }
//        FileOutputStream outStream = null;
//        try {
//            outStream = new FileOutputStream(path + fileName);
//            hdt.write(outStream);
//            outStream.close();
//        } catch (Exception e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        return new File(path + fileName);
//
//
//
//    }
//
//    /**
//     * 正则匹配字符串
//     *
//     * @param str
//     * @return
//     */
//    private static Matcher matcher(String str) {
//        Pattern pattern = Pattern.compile("\\$\\{(.+?)\\}", Pattern.CASE_INSENSITIVE);
//        Matcher matcher = pattern.matcher(str);
//        return matcher;
//    }

}
