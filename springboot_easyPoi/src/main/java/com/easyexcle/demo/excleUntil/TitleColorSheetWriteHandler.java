package com.easyexcle.demo.excleUntil;

import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.util.StyleUtil;
import com.alibaba.excel.write.handler.CellWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.ss.usermodel.*;

import java.util.List;

/*
    自定义样式
    表头样式处理类
 */
@Slf4j
public class TitleColorSheetWriteHandler implements CellWriteHandler {

    //操作行
    private List<Integer> columnIndexs;
    //操作列
    private List<Integer> rowIndexs;
    //颜色
    private Short colorIndex;

    //构造
    public TitleColorSheetWriteHandler(List<Integer> rowIndexs, List<Integer> columnIndexs, Short colorIndex) {
        this.rowIndexs = rowIndexs;
        this.columnIndexs = columnIndexs;
        this.colorIndex = colorIndex;
    }

    public TitleColorSheetWriteHandler() {
    }

    @Override
    public void beforeCellCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Row row, Head head, Integer columnIndex, Integer relativeRowIndex, Boolean isHead) {
    }

    @Override
    public void afterCellCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Cell cell, Head head, Integer relativeRowIndex, Boolean isHead) {
    }

    @Override
    public void afterCellDispose(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, List<CellData> cellDataList, Cell cell, Head head, Integer relativeRowIndex, Boolean isHead) {
        // 只处理第一行
        if( 0 == cell.getRowIndex()){
            // 设置列宽
            Sheet sheet = writeSheetHolder.getSheet();
            sheet.setColumnWidth(cell.getColumnIndex(), 14 * 256);
            // 设置行高
            writeSheetHolder.getSheet().getRow(0).setHeight((short)(1.8*256));
            // 获取workbook
            Workbook workbook = writeSheetHolder.getSheet().getWorkbook();
            // 获取样式实例
            WriteCellStyle headWriteCellStyle = new WriteCellStyle();
            // 获取字体实例
            WriteFont headWriteFont = new WriteFont();
            // 设置字体样式
            headWriteFont.setFontName("宋体");
            // 设置字体大小
            headWriteFont.setFontHeightInPoints((short)14);
            // 边框
            headWriteFont.setBold(true);
            headWriteCellStyle.setWriteFont(headWriteFont);
            // 设置背景颜色为灰色
            headWriteCellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
            if (CollectionUtils.isNotEmpty(columnIndexs) &&
                    CollectionUtils.isNotEmpty(rowIndexs) &&
                    colorIndex != null &&
                    cell.getRowIndex() == 0 &&
                    columnIndexs.contains(cell.getColumnIndex())) {
                // 设置指定单元格字体自定义颜色
                headWriteFont.setColor(colorIndex);
            }
            // 获取样式实例
            CellStyle cellStyle = StyleUtil.buildHeadCellStyle(workbook, headWriteCellStyle);
            // 单元格设置样式
            cell.setCellStyle(cellStyle);
        }
    }
}
