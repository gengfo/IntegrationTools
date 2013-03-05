package com.oocl.inittools.perfrpt;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.oocl.inittools.perfrpt.art.ArtModelHelper;
import com.oocl.inittools.perfrpt.common.ARTExcelConstant;

public class MainProcessAllSingleProcess {

	public static Logger logger = LogManager
			.getLogger(MainProcessAllSingleProcess.class);

	private static final String artExcelSrcDir = "D:\\GitRoot\\ips-server\\ivo_sha\\IVO_AEServer\\build\\IntTools\\performance-report\\DailyArtRpt";

	public static void main(String args[]) {

		try {
			// output excel with each process each sheet
			ArtModelHelper.outputArtRecordsBySheet(artExcelSrcDir,
					"allArtByProcess.xls");
			// output excel with all process by type
			ArtModelHelper.outputArtRecordsByType(artExcelSrcDir,
					"allArtByType.xls");

			// output the process which differ more than percentage
			ArtModelHelper.outputArtRecordsAvgInRange(artExcelSrcDir,
					ARTExcelConstant.IPS_WS, "20120327", "20120327", "20120328",
					"20120328", "rangeAvgAll-" + System.currentTimeMillis()
							+ ".xls");

			// ArtModelHelper.outputArtRecordsAvgInRangeWithPctFilter(artExcelSrcDir,
			// ARTExcelConstant.WS, "20120327", "20120327", "20120328",
			// "20120328", 20.0, "rangeAvgPct-" + System.currentTimeMillis()
			// + ".xls");

			String from1 = "20120312";
			String to1 = "20120318";
			String from2 = "20120319";
			String to2 = "20120325";

			ArtModelHelper.outputArtRecordsAvgInRangeWithPctFilter(
					artExcelSrcDir, ARTExcelConstant.IPS_WS, from1,
					to1, from2, to2, 20.0, from1 + "-" + to1
							+ "-cmp-" + from2 + "-" + to2 + "rangeAvgPct-"
							+ System.currentTimeMillis() + ".xls");
			
			System.out.println("Done");

		} catch (Exception e) {

			e.printStackTrace();

		}

	}
}
