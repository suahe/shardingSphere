package com.sah.shardingSphere.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.data.CellData;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.util.ConverterUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.lang.reflect.Field;
import java.util.*;

/**
 * @author suahe
 * @date 2023/5/18 14:02
 * @describe 通用Excel导出数据校验
 */
@Slf4j
public class ExcelImportListener<T> extends AnalysisEventListener<T> {

    private final Workbook workbook;

    //数据校验器
    private Validator validator;

    private Map<Integer, String> headMap;

    private Map<Integer, ReadCellData<?>> readCellDataMap;

    //出错的CellData 集合
    private List<CellData> errorCellDataList = new ArrayList<>();

    public ExcelImportListener(Workbook workbook) {
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
        this.workbook = workbook;
    }

    @Override
    public void invoke(T data, AnalysisContext context) {
        Map<String, String> propertyMap = getPropertyMap(data.getClass());
        Integer rowIndex = context.readRowHolder().getRowIndex();
        for (int i = 0; i < headMap.size(); i++) {
            String headName = headMap.get(i);
            String fieldName = propertyMap.get(headName);
            //判断列名是否一致
            Set<ConstraintViolation<T>> violations = validator.validateProperty(data, fieldName);
            if (violations.isEmpty()) {
                //校验成功
                continue;
            }
            //校验失败
            CellData cellData = readCellDataMap.get(i);
            cellData.setRowIndex(rowIndex);
            cellData.setColumnIndex(i);
            errorCellDataList.add(cellData);
        }
    }

    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        this.headMap = headMap;
    }

    public void invokeHead(Map<Integer, ReadCellData<?>> readCellDataMap, AnalysisContext context) {
        this.invokeHeadMap(ConverterUtils.convertToStringMap(readCellDataMap, context), context);
        this.readCellDataMap = readCellDataMap;
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        Sheet sheet = workbook.getSheetAt(0);
        // 在 sheet 的所有行上标记校验错误的单元格
        for (CellData cellData : readCellDataMap.values()) {
            if (errorCellDataList.contains(cellData)) {
                // 获取当前行、列
                int row = cellData.getRowIndex();
                int col = cellData.getColumnIndex();
                // 设置单元格样式为红色背景
                CellStyle cellStyle = workbook.createCellStyle();
                cellStyle.setFillForegroundColor(IndexedColors.RED.getIndex());
                cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                Row rowData = sheet.getRow(row);
                Cell cell = rowData.getCell(col);
                cell.setCellStyle(cellStyle);
            }
        }
    }

    private Map<String, String> getPropertyMap(Class<?> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        Map<String, String> map = new HashMap<>(fields.length);
        for (Field field : fields) {
            ExcelProperty annotation = field.getAnnotation(ExcelProperty.class);
            if (annotation != null) {
                field.setAccessible(true);
                String[] values = annotation.value();
                for (String value : values) {
                    map.put(value, field.getName());
                }

            }
        }
        return map;
    }
}
