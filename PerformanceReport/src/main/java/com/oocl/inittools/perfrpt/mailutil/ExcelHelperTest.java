package com.oocl.inittools.perfrpt.mailutil;

import java.io.File;

import com.oocl.inittools.perfrpt.common.ExcelHelper;

public class ExcelHelperTest {
    
    
    public static void main(String args[]){
        
        String  root  = "D:\\ToDel\\mailSec\\srcExcels";
        String fileName = "ARPDailyReport.2013-01-19.xls";
        
        String fullFileName = root + File.separator + fileName;
        
        try {
            String value = ExcelHelper.getCellValueFromExcelFile(fullFileName, "Sheet1", 2,1);
            
            
            //String value = ExcelHelper.fi(fullFileName, "Sheet1", 2,1);
            
            System.out.println(value);
            
        } catch (Exception e) {
    
            e.printStackTrace();
        }
        
        
    }

}
