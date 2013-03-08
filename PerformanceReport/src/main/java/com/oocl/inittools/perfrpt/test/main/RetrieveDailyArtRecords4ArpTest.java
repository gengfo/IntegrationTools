package com.oocl.inittools.perfrpt.test.main;

import java.util.List;

import com.oocl.inittools.perfrpt.common.Model;
import com.oocl.inittools.perfrpt.daily.DailyHelper;
import com.oocl.inittools.perfrpt.excel.ExcelHelper;

public class RetrieveDailyArtRecords4ArpTest {

    public static void main(String args[]) {

        String folder = "D:\\ToDel\\mailSec\\desExcles\\ARP";

        List<Model> mList = DailyHelper.readArpModel(folder);

        ExcelHelper.outputListToTxtFile(mList, "C:\\art.csv");

        System.out.println("Done");
    }

}
