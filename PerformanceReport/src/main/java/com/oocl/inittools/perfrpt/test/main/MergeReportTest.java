package com.oocl.inittools.perfrpt.test.main;

import com.oocl.inittools.perfrpt.daily.DailyHelper;

public class MergeReportTest {

    public static void main(String args[]) {

        try {

            String artReportRootDir = args[0];
            
            DailyHelper.readIpsAvg(artReportRootDir);

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

}
