package com.oocl.inittools.perfrpt.daily;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.oocl.inittools.perfrpt.common.ARTExcelConstant;
import com.oocl.inittools.perfrpt.common.ExcelHelper;
import com.oocl.inittools.perfrpt.common.FileHelper;

public class DailyHelper {

	public static Logger logger = LogManager.getLogger(DailyHelper.class);

	public static void read(String folder) throws Exception {

		String[] files = FileHelper.readDir(folder);

		int rcTimeTotal = 0;
		int rcCntTotal = 0;

		int wsTimeTotal = 0;
		int wsCntTotal = 0;

		int bkTimeTotal = 0;
		int bkCntTotal = 0;

		for (int i = 0; i < files.length; i++) {
			String fileName = files[i];

			String rcCnt = ExcelHelper.getCellValueFromExcelFile(fileName,
					ARTExcelConstant.RC_SHEET_NO, 3, 2);
			String rcTime = ExcelHelper.getCellValueFromExcelFile(fileName,
					ARTExcelConstant.RC_SHEET_NO, 4, 2);
			String rcAvg = ExcelHelper.getCellValueFromExcelFile(fileName,
					ARTExcelConstant.RC_SHEET_NO, 5, 2);
			rcCntTotal = Integer.parseInt(rcCnt) + rcCntTotal;
			rcTimeTotal = Integer.parseInt(rcTime) + rcTimeTotal;

			String wsCnt = ExcelHelper.getCellValueFromExcelFile(fileName,
					ARTExcelConstant.WS_SHEET_NO, 2, 2);
			String wsTime = ExcelHelper.getCellValueFromExcelFile(fileName,
					ARTExcelConstant.WS_SHEET_NO, 3, 2);
			String wsAvg = ExcelHelper.getCellValueFromExcelFile(fileName,
					ARTExcelConstant.WS_SHEET_NO, 4, 2);
			wsCntTotal = Integer.parseInt(wsCnt) + wsCntTotal;
			wsTimeTotal = Integer.parseInt(wsTime) + wsTimeTotal;

			String bkCnt = ExcelHelper.getCellValueFromExcelFile(fileName,
					ARTExcelConstant.BK_SHEET_NO, 2, 1);
			String bkTime = ExcelHelper.getCellValueFromExcelFile(fileName,
					ARTExcelConstant.BK_SHEET_NO, 3, 1);
			String bkAvg = ExcelHelper.getCellValueFromExcelFile(fileName,
					ARTExcelConstant.BK_SHEET_NO, 4, 1);
			bkCntTotal = Integer.parseInt(bkCnt) + bkCntTotal;
			bkTimeTotal = Integer.parseInt(bkTime) + bkTimeTotal;

			System.out.println(fileName + " rcCnt " + rcCnt + " rcTime "
					+ rcTime + " rcAvg " + rcAvg + " wsCnt " + wsCnt
					+ " wsTime " + wsTime + " wsAvg " + wsAvg + " bkCnt "
					+ bkCnt + " bkTime " + bkTime + " bkAvg " + bkAvg);
		}

		System.out.println(rcCntTotal);
		System.out.println(rcTimeTotal / rcCntTotal);
		System.out.println(wsCntTotal);
		System.out.println(wsTimeTotal / wsCntTotal);
		System.out.println(bkCntTotal);
		System.out.println(bkTimeTotal / bkCntTotal);

	}

}
