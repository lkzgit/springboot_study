package com.demo.word.utils;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
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
     * @Author lkz
     * @Description //TODO 替换文本中的字段
     * @Date 10:30 2021/9/23
     * @Param [map 要替换的数据, path 路径, fileName 文件名]
     * @return java.io.File
     **/
    public static File readWriteWord(Map<String, String> map, String path, String fileName){
        // 读取word模板文件
        InputStream in = null;

        XWPFDocument hdt = null;
        try {
            in = new FileInputStream(new File(path + fileName));
            // 转换成文档
            hdt = new XWPFDocument(in);
            in.close();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        // 替换段落中的指定文字
        Iterator<XWPFParagraph> itPara = hdt.getParagraphsIterator();
        while (itPara.hasNext()) {
            XWPFParagraph paragraph = (XWPFParagraph) itPara.next();
            List<XWPFRun> runs;
            Matcher matcher;

            if (matcher(paragraph.getParagraphText()).find()) {
                runs = paragraph.getRuns();
                for (int i = 0; i < runs.size(); i++) {
                    XWPFRun run = runs.get(i);
                    String runText = run.toString();
                    int index = 1;
                    //判断获取是否为$，如果是则进行拼接
                    if ("$".equals(runText)) {
                        //循环拼接
                        for (int j = 0; j < index; j++) {
                            //如果大小大于字段数则退出循环
                            if(i+index>=runs.size()) {
                                break;
                            }
                            XWPFRun run1 = runs.get(i + index);
                            runText += run1.toString();
                            //如果拼接的字符不是}，那么继续拼接
                            if (!"}".equals(run1.toString())) {
                                index++;
                            }
                        }
                    }
                    //判断拼接好的字段是否是需要替换的
                    if(map.get(runText)!=null) {
                        matcher = matcher(runText);
                        if (matcher.find()) {
                            runText = map.get(runText);
                            //开始剔除需要替换的字符
                            for (int j = 0; j < index + 1; j++) {
                                paragraph.removeRun(i);
                            }
                            // 重新插入run里内容格式可能与原来模板的格式不一致
                            paragraph.insertNewRun(i).setText(runText);
                        }
                    }
                }
            }
        }
        FileOutputStream outStream = null;
        try {
            outStream = new FileOutputStream(path + fileName);
            hdt.write(outStream);
            outStream.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return new File(path + fileName);



    }

    /**
     * 正则匹配字符串
     *
     * @param str
     * @return
     */
    private static Matcher matcher(String str) {
        Pattern pattern = Pattern.compile("\\$\\{(.+?)\\}", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(str);
        return matcher;
    }

}
