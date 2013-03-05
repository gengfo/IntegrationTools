package com.oocl.inittools.perfrpt.test.main;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.oocl.inittools.perfrpt.common.Constants;
import com.oocl.inittools.perfrpt.mailutil.EmailAttachmentUtil;
import com.oocl.inittools.perfrpt.mailutil.MailAccount;

public class EmailAttachmentUtilTest {
    
    public static void main(String args[]) {

        MailAccount ma = new MailAccount();
        String mailFolderName = "inbox";
        String attachFolderRoot = "D:\\ToDel\\mailSec";

        DateFormat formatter = new SimpleDateFormat(Constants.DATE_FORMAT);

        EmailAttachmentUtil.collectMailAttachment(ma, mailFolderName, attachFolderRoot, "ARPDailyReport", "20130119", "20130305",
                formatter);
        
        System.out.println("Done");
        

    }


}
