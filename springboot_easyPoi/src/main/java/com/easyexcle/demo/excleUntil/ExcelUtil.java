package com.easyexcle.demo.excleUntil;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;
import com.alibaba.excel.write.handler.CellWriteHandler;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.easyexcle.demo.listener.AnalysisEventListenerImpl;
import com.easyexcle.demo.pojo.User;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class ExcelUtil {



    /**
     * 使用 模型 来写入Excel
     *
     * @param outputStream Excel的输出流
     * @param dataList         要写入的以 模型 为单位的数据
     * @param clazz        模型的类
     */
    public static void writeExcelWithModel(OutputStream outputStream, String sheetName, List<? extends Object> dataList, Class<? extends Object> clazz) {
        //这里指定需要表头，因为model通常包含表头信息
        ExcelWriter writer = EasyExcel.write(outputStream, clazz).build();
        //创建writeSheet，设置基本信息
        WriteSheet writeSheet = new WriteSheet();
        writeSheet.setSheetName(sheetName);
        writer.write(dataList, writeSheet);
        writer.finish();
    }


    /**
     * @param outputStream 输出流
     * @param sheetName 定义sheet名称
     * @param headList sheet表头
     * @param lineList sheet行数据
     */
    public static void writeExcel(OutputStream outputStream,String sheetName,List<String> headList,List<List<Object>> lineList){
        List<List<String>> list = new ArrayList<>();
        if(headList != null){
            headList.forEach(h -> list.add(Collections.singletonList(h)));
        }
        EasyExcel.write(outputStream).head(list).sheet(sheetName).doWrite(lineList);
    }








    /**
     * 使用 模型 来读取Excel
     * @param fileInputStream Excel的输入流
     * @param clazz 模型的类
     * @return 返回 模型 的列表(为object列表,需强转)
     */
    public static List<Object> readExcelWithModel(InputStream fileInputStream, Class<? extends Object> clazz) throws IOException{
        //监听器
        AnalysisEventListenerImpl<Object> listener = new AnalysisEventListenerImpl<Object>();
        ExcelReader excelReader = EasyExcel.read(fileInputStream,clazz, listener).build();
        ReadSheet readSheet = EasyExcel.readSheet(0).build();
        excelReader.read(readSheet);
        excelReader.finish();
        return listener.getList();
    }


    /**
     * 自定义样式导出
     */
    /**
     * 导出表头必填字段标红色
     * @param outputStream 输入流
     * @param dataList 导入数据
     * @param headList 表头列表
     * @param sheetName sheetname
     * @param cellWriteHandlers
     */
    public static void writeExcelWithModel(OutputStream outputStream, List<? extends Object> dataList, List<String> headList, String sheetName, CellWriteHandler... cellWriteHandlers) {
        List<List<String>> list = new ArrayList<>();
        if(headList != null){
            headList.forEach(h -> list.add(Collections.singletonList(h)));
        }

        // 头的策略
        WriteCellStyle headWriteCellStyle = new WriteCellStyle();
        // 单元格策略
        WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
        // 初始化表格样式
        HorizontalCellStyleStrategy horizontalCellStyleStrategy = new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle);

        ExcelWriterSheetBuilder excelWriterSheetBuilder = EasyExcel.write(outputStream).head(list).sheet(sheetName).registerWriteHandler(horizontalCellStyleStrategy);
        if(null != cellWriteHandlers && cellWriteHandlers.length>0){
            for(int i = 0 ; i < cellWriteHandlers.length;i++){
                excelWriterSheetBuilder.registerWriteHandler(cellWriteHandlers[i]);
            }
        }
        // 开始导出
        excelWriterSheetBuilder.doWrite(dataList);
    }
    /**
     * 导出表头必填字段标红色
     * @param outputStream 输入流
     * @param dataList 导入数据
     * @param headList 表头列表
     * @param sheetName sheetname
     * @param cellWriteHandlers
     */
    public static void writeExcelWithModel(OutputStream outputStream, List<? extends Object> dataList, Class<? extends Object> headList, String sheetName, CellWriteHandler... cellWriteHandlers) {

        // 头的策略
        WriteCellStyle headWriteCellStyle = new WriteCellStyle();
        // 单元格策略
        WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
        // 初始化表格样式
        HorizontalCellStyleStrategy horizontalCellStyleStrategy = new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle);

        ExcelWriterSheetBuilder excelWriterSheetBuilder = EasyExcel.write(outputStream,headList).sheet(sheetName).registerWriteHandler(horizontalCellStyleStrategy);
        if(null != cellWriteHandlers && cellWriteHandlers.length>0){
            for(int i = 0 ; i < cellWriteHandlers.length;i++){
                excelWriterSheetBuilder.registerWriteHandler(cellWriteHandlers[i]);
            }
        }
        // 开始导出
        excelWriterSheetBuilder.doWrite(dataList);
    }

    /**
     * 测试自定义样式
     * @param args
     * @throws Exception
     */
//    public static void main(String[] args) throws Exception {
//        // 输出流
//        OutputStream outputStream = null;
//        outputStream = new FileOutputStream(new File("D:\\1.xlsx"));
//
//        // 导出的数据
//        List<User> dataList = new ArrayList<>();
//        for (int i = 0; i < 10; i++) {
//            User testVO = new User();
//            testVO.setAge(i + 20);
//            testVO.setName("vo" + i);
//            dataList.add(testVO);
//        }
//
//        // 标题
//        List<String> headList = Arrays.asList("姓名", "年龄", "学校");
//
//        String sheetName = "导出文件";
//
//        List<Integer> columnIndexs = Arrays.asList(0,1,2);
//        List<Integer> rowIndexs = Arrays.asList(0);
//        TitleColorSheetWriteHandler titleColorSheetWriteHandler = new TitleColorSheetWriteHandler(rowIndexs, columnIndexs, IndexedColors.RED.index);
//
//        List<Integer> columnIndexs1 = Arrays.asList(0,1);
//        List<Integer> rowIndexs1 = Arrays.asList(1,2,3,4);
//        CellColorSheetWriteHandler colorSheetWriteHandler = new CellColorSheetWriteHandler(rowIndexs1, columnIndexs1, IndexedColors.RED.index);
//
//        writeExcelWithModel(outputStream, dataList, headList, sheetName, titleColorSheetWriteHandler,colorSheetWriteHandler);
//    }



    /**
     * 测试导入
     */
    public static void main(String[] args) throws Exception {
        String filePath = "D:\\模板导出.xls";
        InputStream inputStream = null;
        inputStream = new FileInputStream(new File(filePath));
        List<Object> list = readExcelWithModel(inputStream, User.class);
        list.forEach((user)->{
            User user1 = (User) user;
            System.out.println(user1.getName()+", "+user1.getAge());
        });
    }

}
