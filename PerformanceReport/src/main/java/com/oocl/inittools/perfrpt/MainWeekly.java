package com.oocl.inittools.perfrpt;

import com.oocl.inittools.perfrpt.daily.DailyHelper;

public class MainWeekly {

    public static void main(String args[]) {

        try {

            String artReportRootDir = args[0];
            DailyHelper.readIpsAvg(artReportRootDir);

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

}
