package com.oocl.inittools.perfrpt.daily;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.oocl.inittools.perfrpt.common.ARTExcelConstant;
import com.oocl.inittools.perfrpt.common.ExcelHelper;
import com.oocl.inittools.perfrpt.common.FileHelper;
import com.oocl.inittools.perfrpt.common.Model;
import com.oocl.inittools.perfrpt.common.Utils;

public class DailyHelper {

    public static Logger logger = LogManager.getLogger(DailyHelper.class);

    public static void readIpsAvg(String folder) throws Exception {

        String[] files = FileHelper.readDir(folder);

        int rcTimeTotal = 0;
        int rcCntTotal = 0;

        int wsTimeTotal = 0;
        int wsCntTotal = 0;

        int bkTimeTotal = 0;
        int bkCntTotal = 0;

        for (int i = 0; i < files.length; i++) {
            String fileName = files[i];

            // String rcCnt = ExcelHelper.getCellValueFromExcelFile(fileName, ARTExcelConstant.RC_SHEET_NO, 3, 2);

            String rcCnt = ExcelHelper.getIpsRcCntHeader(fileName);
            String rcTime = ExcelHelper.getIpsRcTimeHeader(fileName);
            String rcAvg = ExcelHelper.getIpsRcAvgHeader(fileName);

            rcCntTotal = Integer.parseInt(rcCnt) + rcCntTotal;
            rcTimeTotal = Integer.parseInt(rcTime) + rcTimeTotal;

            String wsCnt = ExcelHelper.getIpsWsCntHeader(fileName);
            String wsTime = ExcelHelper.getIpsWsTimeHeader(fileName);
            String wsAvg = ExcelHelper.getIpsWsAvgHeader(fileName);
            ;

            wsCntTotal = Integer.parseInt(wsCnt) + wsCntTotal;
            wsTimeTotal = Integer.parseInt(wsTime) + wsTimeTotal;

            String bkCnt = ExcelHelper.getIpsBkCntHeader(fileName);
            String bkTime = ExcelHelper.getIpsBkTimeHeader(fileName);
            String bkAvg = ExcelHelper.getIpsBkAvgHeader(fileName);

            bkCntTotal = Integer.parseInt(bkCnt) + bkCntTotal;
            bkTimeTotal = Integer.parseInt(bkTime) + bkTimeTotal;

            System.out.println(fileName + " rcCnt " + rcCnt + " rcTime " + rcTime + " rcAvg " + rcAvg + " wsCnt "
                    + wsCnt + " wsTime " + wsTime + " wsAvg " + wsAvg + " bkCnt " + bkCnt + " bkTime " + bkTime
                    + " bkAvg " + bkAvg);

        }

        System.out.println(rcCntTotal);
        System.out.println(rcTimeTotal / rcCntTotal);
        System.out.println(wsCntTotal);
        System.out.println(wsTimeTotal / wsCntTotal);
        System.out.println(bkCntTotal);
        System.out.println(bkTimeTotal / bkCntTotal);

    }

    public static List<Model> readArpModel(String folder) {

        String[] files = FileHelper.readDir(folder);

        List<Model> modelList = new ArrayList<Model>();

        for (int i = 0; i < files.length; i++) {
            String sourceFile = files[i];

            String fileDateString = Utils.getDate(sourceFile);

            InputStream myxls = null;
            try {
                myxls = new FileInputStream(sourceFile);

                HSSFWorkbook workbook = new HSSFWorkbook(myxls);

                HSSFSheet rcSheet = workbook.getSheetAt(ARTExcelConstant.ARP_RC_SHEET_NO);

                List<Model> rcModelList = ExcelHelper.getArpRowDataFromXlsSheet(rcSheet, fileDateString,
                        ARTExcelConstant.ARP_RC_FIRST_ROW_NO, ARTExcelConstant.ARP_RC_COLUMN_REQUEST_NAME,
                        ARTExcelConstant.ARP_RC_COLUMN_COUNT, ARTExcelConstant.ARP_RC_COLUMN_MAX_ELAPSED_TIME,
                        ARTExcelConstant.ARP_RC_COLUMN_MIN_ELAPSED_TIME, ARTExcelConstant.ARP_RC_COLUMN_AVG_ELAPSED_TIME,
                        ARTExcelConstant.ARP_RC_COLUMN_TOTAL_ELAPSED_TIME, ARTExcelConstant.ARP_CLIENT);
                modelList.addAll(rcModelList);

                HSSFSheet wsSheet = workbook.getSheetAt(ARTExcelConstant.ARP_WS_SHEET_NO);
                List<Model> wsModelList = ExcelHelper.getArpRowDataFromXlsSheet(wsSheet, fileDateString,
                        ARTExcelConstant.ARP_WS_FIRST_ROW_NO, ARTExcelConstant.ARP_WS_COLUMN_REQUEST_NAME,
                        ARTExcelConstant.ARP_WS_COLUMN_COUNT, ARTExcelConstant.ARP_WS_COLUMN_MAX_ELAPSED_TIME,
                        ARTExcelConstant.ARP_WS_COLUMN_MIN_ELAPSED_TIME, ARTExcelConstant.ARP_WS_COLUMN_AVG_ELAPSED_TIME,
                        ARTExcelConstant.ARP_WS_COLUMN_TOTAL_ELAPSED_TIME, ARTExcelConstant.ARP_WS);
                modelList.addAll(wsModelList);
                
                

                myxls.close();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        return modelList;

        // String[] files = FileHelper.readDir(folder);
        //
        // int rcTimeTotal = 0;
        // int rcCntTotal = 0;
        //
        // int wsTimeTotal = 0;
        // int wsCntTotal = 0;
        //
        // int bkTimeTotal = 0;
        // int bkCntTotal = 0;
        //
        // for (int i = 0; i < files.length; i++) {
        // String fileName = files[i];
        //
        // String rcCnt = ExcelHelper.getCellValueFromExcelFile(fileName,
        // ARTExcelConstant.RC_SHEET_NO, 3, 2);
        // String rcTime = ExcelHelper.getCellValueFromExcelFile(fileName,
        // ARTExcelConstant.RC_SHEET_NO, 4, 2);
        // String rcAvg = ExcelHelper.getCellValueFromExcelFile(fileName,
        // ARTExcelConstant.RC_SHEET_NO, 5, 2);
        // rcCntTotal = Integer.parseInt(rcCnt) + rcCntTotal;
        // rcTimeTotal = Integer.parseInt(rcTime) + rcTimeTotal;
        //
        // String wsCnt = ExcelHelper.getCellValueFromExcelFile(fileName,
        // ARTExcelConstant.WS_SHEET_NO, 2, 2);
        // String wsTime = ExcelHelper.getCellValueFromExcelFile(fileName,
        // ARTExcelConstant.WS_SHEET_NO, 3, 2);
        // String wsAvg = ExcelHelper.getCellValueFromExcelFile(fileName,
        // ARTExcelConstant.WS_SHEET_NO, 4, 2);
        // wsCntTotal = Integer.parseInt(wsCnt) + wsCntTotal;
        // wsTimeTotal = Integer.parseInt(wsTime) + wsTimeTotal;
        //
        // String bkCnt = ExcelHelper.getCellValueFromExcelFile(fileName,
        // ARTExcelConstant.BK_SHEET_NO, 2, 1);
        // String bkTime = ExcelHelper.getCellValueFromExcelFile(fileName,
        // ARTExcelConstant.BK_SHEET_NO, 3, 1);
        // String bkAvg = ExcelHelper.getCellValueFromExcelFile(fileName,
        // ARTExcelConstant.BK_SHEET_NO, 4, 1);
        // bkCntTotal = Integer.parseInt(bkCnt) + bkCntTotal;
        // bkTimeTotal = Integer.parseInt(bkTime) + bkTimeTotal;
        //
        // System.out.println(fileName + " rcCnt " + rcCnt + " rcTime "
        // + rcTime + " rcAvg " + rcAvg + " wsCnt " + wsCnt
        // + " wsTime " + wsTime + " wsAvg " + wsAvg + " bkCnt "
        // + bkCnt + " bkTime " + bkTime + " bkAvg " + bkAvg);
        // }
        //
        // System.out.println(rcCntTotal);
        // System.out.println(rcTimeTotal / rcCntTotal);
        // System.out.println(wsCntTotal);
        // System.out.println(wsTimeTotal / wsCntTotal);
        // System.out.println(bkCntTotal);
        // System.out.println(bkTimeTotal / bkCntTotal);

    }
    
    public static List<Model> readIpsModel(String folder) {

        String[] files = FileHelper.readDir(folder);

        List<Model> modelList = new ArrayList<Model>();

        for (int i = 0; i < files.length; i++) {
            String sourceFile = files[i];

            String fileDateString = Utils.getDate(sourceFile);

            InputStream myxls = null;
            try {
                myxls = new FileInputStream(sourceFile);

                HSSFWorkbook workbook = new HSSFWorkbook(myxls);

                HSSFSheet rcSheet = workbook.getSheetAt(ARTExcelConstant.IPS_RC_SHEET_NO);
                List<Model> rcModelList = ExcelHelper.getIpsRowDataFromXlsSheet(rcSheet, fileDateString,
                        ARTExcelConstant.ARP_RC_FIRST_ROW_NO, ARTExcelConstant.IPS_RC_COLUMN_REQUEST_NAME,
                        ARTExcelConstant.IPS_RC_COLUMN_COUNT, ARTExcelConstant.IPS_RC_COLUMN_MAX_ELAPSED_TIME,
                        ARTExcelConstant.IPS_RC_COLUMN_MIN_ELAPSED_TIME, ARTExcelConstant.IPS_RC_COLUMN_AVG_ELAPSED_TIME,
                        ARTExcelConstant.IPS_RC_COLUMN_TOTAL_ELAPSED_TIME, ARTExcelConstant.IPS_CLIENT);
                modelList.addAll(rcModelList);

                HSSFSheet wsSheet = workbook.getSheetAt(ARTExcelConstant.IPS_WS_SHEET_NO);
                List<Model> wsModelList = ExcelHelper.getIpsRowDataFromXlsSheet(wsSheet, fileDateString,
                        ARTExcelConstant.ARP_WS_FIRST_ROW_NO, ARTExcelConstant.IPS_WS_COLUMN_REQUEST_NAME,
                        ARTExcelConstant.IPS_WS_COLUMN_COUNT, ARTExcelConstant.IPS_WS_COLUMN_MAX_ELAPSED_TIME,
                        ARTExcelConstant.IPS_WS_COLUMN_MIN_ELAPSED_TIME, ARTExcelConstant.IPS_WS_COLUMN_AVG_ELAPSED_TIME,
                        ARTExcelConstant.IPS_WS_COLUMN_TOTAL_ELAPSED_TIME, ARTExcelConstant.IPS_WS);
                modelList.addAll(wsModelList);
                
                HSSFSheet bkSheet = workbook.getSheetAt(ARTExcelConstant.IPS_BK_SHEET_NO);
                List<Model> bkModelList = ExcelHelper.getIpsRowDataFromXlsSheet(bkSheet, fileDateString,
                        ARTExcelConstant.BK_FIRST_ROW_NO, ARTExcelConstant.BK_COLUMN_REQUEST_NAME,
                        ARTExcelConstant.BK_COLUMN_COUNT, ARTExcelConstant.BK_COLUMN_MAX_ELAPSED_TIME,
                        ARTExcelConstant.BK_COLUMN_MIN_ELAPSED_TIME, ARTExcelConstant.BK_COLUMN_AVG_ELAPSED_TIME,
                        ARTExcelConstant.BK_COLUMN_TOTAL_ELAPSED_TIME, ARTExcelConstant.IPS_BK);
                modelList.addAll(bkModelList);

                myxls.close();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        return modelList;

        // String[] files = FileHelper.readDir(folder);
        //
        // int rcTimeTotal = 0;
        // int rcCntTotal = 0;
        //
        // int wsTimeTotal = 0;
        // int wsCntTotal = 0;
        //
        // int bkTimeTotal = 0;
        // int bkCntTotal = 0;
        //
        // for (int i = 0; i < files.length; i++) {
        // String fileName = files[i];
        //
        // String rcCnt = ExcelHelper.getCellValueFromExcelFile(fileName,
        // ARTExcelConstant.RC_SHEET_NO, 3, 2);
        // String rcTime = ExcelHelper.getCellValueFromExcelFile(fileName,
        // ARTExcelConstant.RC_SHEET_NO, 4, 2);
        // String rcAvg = ExcelHelper.getCellValueFromExcelFile(fileName,
        // ARTExcelConstant.RC_SHEET_NO, 5, 2);
        // rcCntTotal = Integer.parseInt(rcCnt) + rcCntTotal;
        // rcTimeTotal = Integer.parseInt(rcTime) + rcTimeTotal;
        //
        // String wsCnt = ExcelHelper.getCellValueFromExcelFile(fileName,
        // ARTExcelConstant.WS_SHEET_NO, 2, 2);
        // String wsTime = ExcelHelper.getCellValueFromExcelFile(fileName,
        // ARTExcelConstant.WS_SHEET_NO, 3, 2);
        // String wsAvg = ExcelHelper.getCellValueFromExcelFile(fileName,
        // ARTExcelConstant.WS_SHEET_NO, 4, 2);
        // wsCntTotal = Integer.parseInt(wsCnt) + wsCntTotal;
        // wsTimeTotal = Integer.parseInt(wsTime) + wsTimeTotal;
        //
        // String bkCnt = ExcelHelper.getCellValueFromExcelFile(fileName,
        // ARTExcelConstant.BK_SHEET_NO, 2, 1);
        // String bkTime = ExcelHelper.getCellValueFromExcelFile(fileName,
        // ARTExcelConstant.BK_SHEET_NO, 3, 1);
        // String bkAvg = ExcelHelper.getCellValueFromExcelFile(fileName,
        // ARTExcelConstant.BK_SHEET_NO, 4, 1);
        // bkCntTotal = Integer.parseInt(bkCnt) + bkCntTotal;
        // bkTimeTotal = Integer.parseInt(bkTime) + bkTimeTotal;
        //
        // System.out.println(fileName + " rcCnt " + rcCnt + " rcTime "
        // + rcTime + " rcAvg " + rcAvg + " wsCnt " + wsCnt
        // + " wsTime " + wsTime + " wsAvg " + wsAvg + " bkCnt "
        // + bkCnt + " bkTime " + bkTime + " bkAvg " + bkAvg);
        // }
        //
        // System.out.println(rcCntTotal);
        // System.out.println(rcTimeTotal / rcCntTotal);
        // System.out.println(wsCntTotal);
        // System.out.println(wsTimeTotal / wsCntTotal);
        // System.out.println(bkCntTotal);
        // System.out.println(bkTimeTotal / bkCntTotal);

    }
    
   
    
   
    public static List<Model> retrieveDailyART(String filePathRoot) {

        String[] flies = FileHelper.readDir(filePathRoot);

        // for (){
        //
        //
        // }

        return null;

    }

}
