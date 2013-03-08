package com.oocl.inittools.perfrpt.test;

import com.oocl.inittools.perfrpt.excel.ExcelHelper;

public class FillCellValueToExcelFileTest {

    public static void main(String args[]) {

        String sourceFile = "D:\\GengFo\\MyProgs\\GitHub\\IntegrationTools\\PerformanceReport\\src\\main\\resources\\performance_report_template.xls";
        int sheetno = 0;
        int rowNo = 1;
        int columnNo = 1;
        String value = "test";

        ExcelHelper.fillCellValueToExcelFile(sourceFile, sheetno, rowNo, columnNo, value);

        System.out.println("done");
    }

}
