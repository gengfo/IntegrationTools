package com.oocl.inittools.perfrpt.test.main;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.oocl.inittools.perfrpt.common.ExcelHelper;
import com.oocl.inittools.perfrpt.mailutil.EmailAttachmentUtil;
import com.oocl.inittools.perfrpt.mailutil.MailAccount;

public class CombineExcelTest {
    
    
    public static final String nextToHandleDataFile = "D:\\ToShare\\arp-iris4\\ARP_APP_Server\\ARP_Build\\build-tools\\performance-report\\src\\main\\resources\\nextToHandleData.txt";
    
    
    public static void main(String args[]){
        
        
        
        
        MailAccount ma = new MailAccount();
        String mailFolderName = "inbox";
        String attachFolderRoot = "D:\\ToDel\\mailSec";

        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        EmailAttachmentUtil.collectMailAttachment(ma, mailFolderName, attachFolderRoot, "ARPDailyReport", "2013-01-19", "2013-01-20",
                formatter);
        
        
        try {
            ExcelHelper.getCellValueFromExcelFile(attachFolderRoot, 0, 0, 0);
        } catch (Exception e) {
            
            
            
            e.printStackTrace();
        }
        
        
        
        
        
    }
    

}
