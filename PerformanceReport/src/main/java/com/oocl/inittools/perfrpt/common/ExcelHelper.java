package com.oocl.inittools.perfrpt.common;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class ExcelHelper {

    public static String getCellValueFromExcelFile(String sourceFile, int sheetno, int rowNo, int columnNo)
            throws Exception {

        String result = "";

        InputStream myxls = new FileInputStream(sourceFile);
        HSSFWorkbook workbook = new HSSFWorkbook(myxls);

        HSSFSheet sheet = workbook.getSheetAt(sheetno);

        HSSFRow hssfRow = sheet.getRow(rowNo);

        HSSFCell nameCell = hssfRow.getCell(columnNo);

        if (null != nameCell) {

            try {
                double d = nameCell.getNumericCellValue();
                result = new BigDecimal(d).toString();
            } catch (Exception e) {

                String d = nameCell.getStringCellValue();
                result = d;
            }

        }

        myxls.close();

        return result;

    }

    public static String getCellValueFromExcelFile(String sourceFile, String sheetName, int rowNo, int columnNo)
            throws Exception {

        String result = "";

        InputStream myxls = new FileInputStream(sourceFile);
        HSSFWorkbook workbook = new HSSFWorkbook(myxls);

        HSSFSheet sheet = workbook.getSheet(sheetName);

        HSSFRow hssfRow = sheet.getRow(rowNo);

        HSSFCell nameCell = hssfRow.getCell(columnNo);

        if (null != nameCell) {

            try {
                double d = nameCell.getNumericCellValue();
                result = new BigDecimal(d).toString();
            } catch (Exception e) {

                String d = nameCell.getStringCellValue();
                result = d;
            }

        }

        myxls.close();

        return result;

    }

    public static String getCellValueFromExcelFile(HSSFWorkbook workbook, int sheetno, int rowNo, int columnNo)
            throws Exception {

        String result = "";

        HSSFSheet sheet = workbook.getSheetAt(sheetno);

        HSSFRow hssfRow = sheet.getRow(rowNo);

        HSSFCell nameCell = hssfRow.getCell(columnNo);

        if (null != nameCell) {

            try {
                double d = nameCell.getNumericCellValue();
                result = new BigDecimal(d).toString();
            } catch (Exception e) {

                String d = nameCell.getStringCellValue();
                result = d;
            }

        }

        return result;

    }

    public static List<Model> getDataFromXlsSheet(HSSFSheet sheet, String fileDateString, int firstRow,
            int requestNameCn, int countCn, int maxCn, int minCn, int avgCn, int totalCn, String type) {

        List<Model> modelList = new ArrayList<Model>();

        HSSFRow hssfRow = sheet.getRow(firstRow);
        boolean flag = (null != hssfRow.getCell(2));

        int nextRow = firstRow;
        while (flag) {

            String requestName = ExcelHelper.getColumnValue(hssfRow, requestNameCn);
            String count = ExcelHelper.getColumnValue(hssfRow, countCn);
            String max = ExcelHelper.getColumnValue(hssfRow, maxCn);
            String min = ExcelHelper.getColumnValue(hssfRow, minCn);
            String avg = ExcelHelper.getColumnValue(hssfRow, avgCn);
            String total = ExcelHelper.getColumnValue(hssfRow, totalCn);

            Model model = new Model(requestName, count, max, min, avg, total, fileDateString, type);

            modelList.add(model);

            nextRow = nextRow + 1;
            hssfRow = sheet.getRow(nextRow);
            // System.out.println("--------------TO get row " + nextRow);
            flag = (null != hssfRow && null != hssfRow.getCell(2));
        }

        // HSSFCell nameCell = hssfRow.getCell(columnNo);

        return modelList;
    }

    public static String getColumnValue(HSSFRow hssfRow, int columnNo) {
        String result = "";

        HSSFCell nameCell = hssfRow.getCell(columnNo);

        if (null != nameCell) {

            try {

                double d = nameCell.getNumericCellValue();
                result = new BigDecimal(d).toString();

            } catch (Exception e) {

                String d = nameCell.getStringCellValue();
                result = d;
            }

        }

        return result;

    }

    public static void fillBookByModels(HSSFWorkbook wb1, String name, List<Model> modelList) {

        HSSFSheet sheet = ExcelHelper.createSheetByName(wb1, name);
        ExcelHelper.fillSheetByModels(sheet, modelList);

    }

    public static void fillBookByAvg(HSSFWorkbook wb1, String name, List<AvgOutputBean> modelList) {

        HSSFSheet sheet = ExcelHelper.createSheetByName(wb1, name);
        ExcelHelper.fillSheetByAvg(sheet, modelList);

    }

    public static HSSFSheet createSheetByName(HSSFWorkbook wb1, String name) {
        return wb1.createSheet(name);
    }

    public static void fillSheetByModels(HSSFSheet sheet, List<Model> modelList) {

        int clientR = 0;
        for (Model model : modelList) {

            // since second row
            HSSFRow row = sheet.createRow((short) (clientR + 1));

            HSSFCell cellRequestName = row.createCell(0);
            cellRequestName.setCellValue(model.getRequestName());

            HSSFCell cellRequestDate = row.createCell(1);

            cellRequestDate.setCellValue(Utils.createDate(model.getRequestDate()));

            HSSFCell cellHitCount = row.createCell(2);
            double hitCount = Double.parseDouble(model.getHitCount());
            cellHitCount.setCellValue(hitCount);

            HSSFCell cellMax = row.createCell(3);
            cellMax.setCellValue(Double.parseDouble(model.getMaxElapsedTime()));

            HSSFCell cellMin = row.createCell(4);
            cellMin.setCellValue(Double.parseDouble(model.getMinElapsedTime()));

            HSSFCell cellAvg = row.createCell(5);
            cellAvg.setCellValue(Double.parseDouble(model.getAvgElapsedTime()));

            HSSFCell cellTotal = row.createCell(6);
            double total = Double.parseDouble(model.getTotalElapsedTime());
            cellTotal.setCellValue(total);

            clientR = clientR + 1;
        }

    }

    public static void fillSheet(HSSFSheet sheet, int rowNo, int columnNo, String value) {

        // since second row
        HSSFRow row = sheet.getRow((short) (rowNo));

        HSSFCell cellRequestName = row.createCell(columnNo);
        cellRequestName.setCellValue(value);

    }

    public static void fillSheet(String fileName, String sheetName, int rowNo, int columnNo, String value) {

        InputStream myxls;
        try {
            myxls = new FileInputStream(fileName);

            HSSFWorkbook workbook = new HSSFWorkbook(myxls);

            HSSFSheet sheet = workbook.getSheet(sheetName);

            // since second row
            HSSFRow row = sheet.getRow((short) (rowNo));

            HSSFCell cellRequestName = row.createCell(columnNo);
            cellRequestName.setCellValue(value);
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void fillSheetByAvg(HSSFSheet sheet, List<AvgOutputBean> modelList) {

        // set header
        HSSFRow rowHeader = sheet.createRow((short) (1));

        int clientR = 1;
        for (AvgOutputBean bean : modelList) {
            AvgModel model1 = bean.getModel1();
            HSSFRow row = sheet.createRow((short) (clientR + 1));
            HSSFCell cellRequestName = row.createCell(0);
            cellRequestName.setCellValue(model1.getRequestName());

            HSSFCell cellHitCount = row.createCell(1);
            double hitCount = Double.parseDouble(model1.getHitCount());
            cellHitCount.setCellValue(hitCount);

            HSSFCell cellAvg = row.createCell(2);
            double avg1 = Double.parseDouble(model1.getAvgElapsedTime());
            cellAvg.setCellValue(avg1);

            HSSFCell cellTotal = row.createCell(3);
            double total = Double.parseDouble(model1.getTotalElapsedTime());
            cellTotal.setCellValue(total);

            //
            HSSFCell empty = row.createCell(4);
            //
            AvgModel model2 = bean.getModel2();
            HSSFCell cellHitCount2 = row.createCell(5);
            double hitCount2 = Double.parseDouble(model2.getHitCount());
            cellHitCount2.setCellValue(hitCount2);

            HSSFCell cellAvg2 = row.createCell(6);
            double avg2 = Double.parseDouble(model2.getAvgElapsedTime());
            cellAvg2.setCellValue(avg2);

            HSSFCell cellTotal2 = row.createCell(7);
            double total2 = Double.parseDouble(model2.getTotalElapsedTime());
            cellTotal2.setCellValue(total2);

            HSSFCell empty2 = row.createCell(8);
            HSSFCell pct = row.createCell(9);

            double pctvalue = 0;
            if ((avg1 == avg2) || avg2 == 0 || avg1 == 0) {
                pctvalue = 0;
            }
            if (avg1 > avg2) {
                pctvalue = ((avg1 - avg2) / avg2) * 100;
                empty2.setCellValue("-");
            } else {
                pctvalue = ((avg2 - avg1) / avg1) * 100;
                empty2.setCellValue("+");
            }

            pct.setCellValue(pctvalue);

            clientR = clientR + 1;
        }

    }

    public static void createColumns(HSSFRow row, AvgModel model, int startRow, boolean withProcessName) {

        if (withProcessName) {
            HSSFCell cellRequestName = row.createCell(startRow);
            cellRequestName.setCellValue(model.getRequestName());
        } else {
            HSSFCell cellRequestName = row.createCell(startRow);
        }

        HSSFCell cellHitCount = row.createCell(startRow + 1);
        double hitCount = Double.parseDouble(model.getHitCount());
        cellHitCount.setCellValue(hitCount);

        HSSFCell cellAvg = row.createCell(startRow + 2);
        cellAvg.setCellValue(Double.parseDouble(model.getAvgElapsedTime()));

        HSSFCell cellTotal = row.createCell(startRow + 3);
        double total = Double.parseDouble(model.getTotalElapsedTime());
        cellTotal.setCellValue(total);

    }

    public static List<Model> getArtModelsFromExcelFiles(String[] fileArray) {

        List<Model> modelList = new ArrayList<Model>();

        for (int i = 0; i < fileArray.length; i++) {
            String artXlsFile = fileArray[i];

            try {
                List<Model> fileModelList = ExcelHelper.getArtModelsFromExcelFile(artXlsFile);

                modelList.addAll(fileModelList);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return modelList;
    }

    public static void creatRowHeader(HSSFSheet sheet) {

        HSSFRow row0 = sheet.createRow((short) 0);

        HSSFCell cellRequestName0 = row0.createCell(0);
        cellRequestName0.setCellValue(".getRequestName()");

        HSSFCell cellRequestDate0 = row0.createCell(1);
        cellRequestDate0.setCellValue(".getRequestDate()");

        HSSFCell cellHitCount0 = row0.createCell(2);
        cellHitCount0.setCellValue(".getHitCount()");

        HSSFCell cellMax0 = row0.createCell(3);
        cellMax0.setCellValue(".getMaxElapsedTime()");

        HSSFCell cellMin0 = row0.createCell(4);
        cellMin0.setCellValue(".getMinElapsedTime()");

        HSSFCell cellAvg0 = row0.createCell(5);
        cellAvg0.setCellValue("getAvgElapsedTime()");

        HSSFCell cellTotal0 = row0.createCell(6);
        cellTotal0.setCellValue("getTotalElapsedTime()");

    }

    public static List<Model> getArtModelsFromExcelFile(String sourceFile) throws Exception {
        List<Model> modelList = new ArrayList<Model>();

        // System.out.println("-------- handle sourcefile " + sourceFile);

        InputStream myxls = new FileInputStream(sourceFile);
        HSSFWorkbook workbook = new HSSFWorkbook(myxls);

        String fileDateString = Utils.getDate(sourceFile);

        HSSFSheet rcSheet = workbook.getSheetAt(ARTExcelConstant.RC_SHEET_NO);

        List<Model> rcModelList = getDataFromXlsSheet(rcSheet, fileDateString, ARTExcelConstant.RC_FIRST_ROW_NO,
                ARTExcelConstant.RC_COLUMN_REQUEST_NAME, ARTExcelConstant.RC_COLUMN_COUNT,
                ARTExcelConstant.RC_COLUMN_MAX_ELAPSED_TIME, ARTExcelConstant.RC_COLUMN_MIN_ELAPSED_TIME,
                ARTExcelConstant.RC_COLUMN_AVG_ELAPSED_TIME, ARTExcelConstant.RC_COLUMN_TOTAL_ELAPSED_TIME,
                ARTExcelConstant.CLIENT);
        modelList.addAll(rcModelList);

        HSSFSheet wsSheet = workbook.getSheetAt(ARTExcelConstant.WS_SHEET_NO);
        List<Model> wsModelList = getDataFromXlsSheet(wsSheet, fileDateString, ARTExcelConstant.WS_FIRST_ROW_NO,
                ARTExcelConstant.WS_COLUMN_REQUEST_NAME, ARTExcelConstant.WS_COLUMN_COUNT,
                ARTExcelConstant.WS_COLUMN_MAX_ELAPSED_TIME, ARTExcelConstant.WS_COLUMN_MIN_ELAPSED_TIME,
                ARTExcelConstant.WS_COLUMN_AVG_ELAPSED_TIME, ARTExcelConstant.WS_COLUMN_TOTAL_ELAPSED_TIME,
                ARTExcelConstant.WS);
        modelList.addAll(wsModelList);

        HSSFSheet bkSheet = workbook.getSheetAt(ARTExcelConstant.BK_SHEET_NO);
        List<Model> bkModelList = getDataFromXlsSheet(bkSheet, fileDateString, ARTExcelConstant.BK_FIRST_ROW_NO,
                ARTExcelConstant.BK_COLUMN_REQUEST_NAME, ARTExcelConstant.BK_COLUMN_COUNT,
                ARTExcelConstant.BK_COLUMN_MAX_ELAPSED_TIME, ARTExcelConstant.BK_COLUMN_MIN_ELAPSED_TIME,
                ARTExcelConstant.BK_COLUMN_AVG_ELAPSED_TIME, ARTExcelConstant.BK_COLUMN_TOTAL_ELAPSED_TIME,
                ARTExcelConstant.BK);

        modelList.addAll(bkModelList);

        myxls.close();

        return modelList;

    }

    public static void writeWorkbook(HSSFWorkbook wb, String toFile) {
        FileOutputStream fileOut;
        try {
            fileOut = new FileOutputStream(toFile);
            wb.write(fileOut);
            fileOut.close();
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
