package com.oocl.inittools.perfrpt.art;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFHyperlink;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.oocl.inittools.perfrpt.MainProcessAllSingleProcess;
import com.oocl.inittools.perfrpt.common.ARTExcelConstant;
import com.oocl.inittools.perfrpt.common.AvgModel;
import com.oocl.inittools.perfrpt.common.AvgOutputBean;
import com.oocl.inittools.perfrpt.common.ExcelHelper;
import com.oocl.inittools.perfrpt.common.FileHelper;
import com.oocl.inittools.perfrpt.common.Model;
import com.oocl.inittools.perfrpt.common.Utils;
import com.oocl.inittools.perfrpt.daily.DailyHelper;

public class ArtModelHelper {

	public static Set<String> getProcessNameSet(List<Model> modelList) {
		Set<String> processNameSet = new TreeSet<String>();
		DailyHelper.logger.trace(" ---- to add process name to process set");

		for (int i = 0; i < modelList.size(); i++) {
			Model model = modelList.get(i);
			processNameSet.add(model.getRequestName());
			DailyHelper.logger.trace(" add " + model.getRequestName()
					+ " to set");
		}
		DailyHelper.logger.trace(" ---- end add process name to process set");
		return processNameSet;
	}

	public static Map<String, Model> getNameDateMap(List<Model> modelList) {
		DailyHelper.logger.trace(" ---- to add process name to process set");

		Map<String, Model> nameDateMap = new HashMap<String, Model>();
		for (int i = 0; i < modelList.size(); i++) {
			Model model = modelList.get(i);
			nameDateMap.put(model.getKey(), model);
			DailyHelper.logger.trace("put " + model.getKey() + " to map");
		}

		DailyHelper.logger.trace(" ---- end add process name to process set");
		return nameDateMap;
	}

	public static Map<String, List<Model>> getRequestNameMap(
			List<Model> modelList) {

		Set<String> processNameSet = getProcessNameSet(modelList);

		Map<String, Model> nameDateMap = getNameDateMap(modelList);

		Map<String, List<Model>> processNameMap = new HashMap<String, List<Model>>();
		Iterator<String> itProcessName = processNameSet.iterator();

		TreeSet<String> dss = new TreeSet<String>();

		for (int i = 0; i < modelList.size(); i++) {
			Model m = (Model) modelList.get(i);
			dss.add(m.getRequestDate());
		}

		while (itProcessName.hasNext()) {

			String processName = (String) itProcessName.next();

			List<Model> sortedModelList = new ArrayList<Model>();

			Iterator<String> itDss = dss.iterator();
			while (itDss.hasNext()) {
				String nextDate = (String) itDss.next();

				String key = processName + "-" + nextDate;
				// handle nextDate
				if (nameDateMap.containsKey(key)) {
					Model model = (Model) nameDateMap.get(key);
					sortedModelList.add(model);
					// processNameMap.put(processName, model);
					DailyHelper.logger.trace(" getRequestNameMap ----- > "
							+ processName + "-" + nextDate);
				} else {
					Model emptyModel = new Model(processName, "0", "0", "0",
							"0", "0", nextDate, "Type");
					// processNameMap.put(processName, emptyModel);
					sortedModelList.add(emptyModel);
					DailyHelper.logger.trace(" getRequestNameMap ----- > "
							+ " model 0 " + processName + "-" + nextDate);
				}

			}
			processNameMap.put(processName, sortedModelList);

		}

		DailyHelper.logger.trace(" ---- end add process name to process set");
		return processNameMap;
	}

	public static List<Model> getAllArtModels(String folder) {

		String[] fileArray = FileHelper.readDir(folder);
		List<Model> allModelList = ExcelHelper
				.getArtModelsFromExcelFiles(fileArray);

		return allModelList;

	}

	public static List<Model> getTypedArtModelsInRange(String folder,
			String type, String from, String to) {

		String[] fileArray = FileHelper.readDir(folder);

		List<String> toHandleFiles = new ArrayList<String>();

		boolean started = false;
		for (int i = 0; i < fileArray.length; i++) {

			String fileFullName = fileArray[i];
			System.out.println(fileFullName);
			String abstracted = fileFullName.substring(fileFullName.length()
					- "20120405.xls".length(),
					fileFullName.length() - ".xls".length());
			System.out.println("---> " + abstracted);

			if (abstracted.equals(from)) {
				started = true;
			}
			if (started) {
				toHandleFiles.add(fileFullName);
				System.out.println("bingle: " + fileFullName);
			}
			if (abstracted.equals(to)) {
				break;
			}

		}

		String[] toHandleFileArray = toHandleFiles.toArray(new String[0]);

		List<Model> allModelList = ExcelHelper
				.getArtModelsFromExcelFiles(toHandleFileArray);

		List<Model> filterTypeModelList = new ArrayList<Model>();
		for (Model model : allModelList) {
			if (model.getType().equals(type)) {
				filterTypeModelList.add(model);
			}
		}

		return filterTypeModelList;

	}

	public static String getNextName(String datePattern) {

		return null;
	}

	public static List<Model> getModelListByType(List<Model> modelList,
			String type) {
		List<Model> typeModels = new ArrayList<Model>();
		for (Model model : modelList) {
			if (type.equals(model.getType())) {
				typeModels.add(model);
			}
		}
		return typeModels;
	}

	public static void createWorkbookByType(HSSFWorkbook wb1,
			List<Model> modelList) {

		List<Model> clientModels = getModelListByType(modelList,
				ARTExcelConstant.IPS_CLIENT);
		ExcelHelper
				.fillBookByModels(wb1, ARTExcelConstant.IPS_CLIENT, clientModels);

		List<Model> wsModels = getModelListByType(modelList,
				ARTExcelConstant.IPS_WS);
		ExcelHelper.fillBookByModels(wb1, ARTExcelConstant.IPS_WS, wsModels);

		List<Model> bkModels = getModelListByType(modelList,
				ARTExcelConstant.IPS_BK);
		ExcelHelper.fillBookByModels(wb1, ARTExcelConstant.IPS_BK, bkModels);

	}

	public static Map<String, List<Model>> getModelMaps(List<Model> modelList) {

		Map<String, List<Model>> processNameMap = getRequestNameMap(modelList);

		return processNameMap;

	}

	public static void outputArtRecordsBySheet(String artXlsSrcDir,
			String toFile) {

		HSSFWorkbook wbBySheet = new HSSFWorkbook();

		List<Model> allModelList = getAllArtModels(artXlsSrcDir);
		Map<String, List<Model>> allModelMap = getModelMaps(allModelList);

		createWorkbookBySheet(wbBySheet, allModelMap);
		ExcelHelper.writeWorkbook(wbBySheet, toFile);

	}

	public static List<AvgOutputBean> mergeMap(Map<String, AvgModel> map1,
			Map<String, AvgModel> map2) {
		List<AvgOutputBean> list = new ArrayList<AvgOutputBean>();
		Set<String> set1 = map1.keySet();
		Set<String> set2 = map2.keySet();

		Iterator<String> it1 = map1.keySet().iterator();
		while (it1.hasNext()) {
			String key1 = it1.next();
			AvgModel avg1 = map1.get(key1);

			AvgModel avg2 = new AvgModel(key1, "0", "0", "0");
			if (set2.contains(key1)) {
				avg2 = map2.get(key1);
				AvgOutputBean aob = new AvgOutputBean();
				aob.setProcessName(key1);
				aob.setModel1(avg1);
				aob.setModel2(avg2);

				list.add(aob);
			}
		}

		Iterator<String> it2 = map2.keySet().iterator();
		while (it2.hasNext()) {
			String key2 = it2.next();
			AvgModel avg2 = map2.get(key2);
			AvgModel avg1 = new AvgModel(key2, "0", "0", "0");
			if (set1.contains(key2)) {
				continue;
			} else {
				avg2 = map2.get(key2);
				AvgOutputBean aob = new AvgOutputBean();
				aob.setProcessName(key2);
				aob.setModel1(avg1);
				aob.setModel2(avg2);
			}
		}

		return list;

	}

	public static List<AvgOutputBean> mergeMapWithPct(
			Map<String, AvgModel> map1, Map<String, AvgModel> map2,
			double percentage) {
		List<AvgOutputBean> list = new ArrayList<AvgOutputBean>();
		Set<String> set1 = map1.keySet();
		Set<String> set2 = map2.keySet();

		Iterator<String> it1 = map1.keySet().iterator();
		while (it1.hasNext()) {
			String key1 = it1.next();
			AvgModel avg1 = map1.get(key1);

			AvgOutputBean aob = new AvgOutputBean();
			aob.setProcessName(key1);
			aob.setModel1(avg1);
			AvgModel avg2 = null;
			if (set2.contains(key1)) {
				avg2 = map2.get(key1);
				if (overPct(avg1, avg2, percentage)) {
					aob.setModel2(avg2);
					list.add(aob);
				}
			} else {
				avg2 = new AvgModel(key1, "0", "0", "0");
				aob.setModel2(avg2);
				list.add(aob);
			}

		}

		Iterator<String> it2 = map2.keySet().iterator();
		while (it2.hasNext()) {
			String key2 = it2.next();
			AvgModel avg2 = map2.get(key2);

			if (set1.contains(key2)) {
				// already handled
				continue;
			} else {
				avg2 = map2.get(key2);
				AvgOutputBean aob = new AvgOutputBean();
				aob.setProcessName(key2);
				AvgModel avg1 = new AvgModel(key2, "0", "0", "0");
				aob.setModel1(avg1);
				aob.setModel2(avg2);
				list.add(aob);
			}
		}

		return list;

	}

	public static boolean overPct(AvgModel avg1, AvgModel avg2, double pct) {

		double d1 = Double.valueOf(avg1.getAvgElapsedTime());
		double d2 = Double.valueOf(avg2.getAvgElapsedTime());

		if (d1 == d2) {
			return false;
		}
		double apct = pct/100;
		if (d1 > d2) {
			double dp = (d1 - d2) / d2;
			
			return dp > apct;
		} else {
			double dp = (d2 - d1) / d1;
			return dp > apct;
		}

	}

	public static List<AvgOutputBean> getTypedArtRecordsAvgInRange(
			String artXlsSrcDir, String type, String from1, String to1,
			String from2, String to2) {

		List<Model> modelList1 = getTypedArtModelsInRange(artXlsSrcDir, type,
				from1, to1);

		Map<String, List<Model>> processNameMap1 = getRequestNameMap(modelList1);
		Map<String, AvgModel> processMap1 = getArtAvgTimeByProcess(processNameMap1);
		showProcessMap4AvgModel(processMap1);

		List<Model> modelList2 = getTypedArtModelsInRange(artXlsSrcDir, type,
				from2, to2);
		Map<String, List<Model>> processNameMap2 = getRequestNameMap(modelList2);
		Map<String, AvgModel> processMap2 = getArtAvgTimeByProcess(processNameMap2);
		showProcessMap4AvgModel(processMap2);

		List<AvgOutputBean> result = mergeMap(processMap1, processMap2);

		return result;
	}

	public static List<AvgOutputBean> getTypedArtRecordsAvgInRangeWithPctFileter(
			String artXlsSrcDir, String type, String from1, String to1,
			String from2, String to2, double percentage) {

		List<Model> modelList1 = getTypedArtModelsInRange(artXlsSrcDir, type,
				from1, to1);

		Map<String, List<Model>> processNameMap1 = getRequestNameMap(modelList1);
		Map<String, AvgModel> processMap1 = getArtAvgTimeByProcess(processNameMap1);
		showProcessMap4AvgModel(processMap1);

		List<Model> modelList2 = getTypedArtModelsInRange(artXlsSrcDir, type,
				from2, to2);
		Map<String, List<Model>> processNameMap2 = getRequestNameMap(modelList2);
		Map<String, AvgModel> processMap2 = getArtAvgTimeByProcess(processNameMap2);
		showProcessMap4AvgModel(processMap2);

		List<AvgOutputBean> result = mergeMapWithPct(processMap1, processMap2,
				percentage);

		return result;
	}

	public static void outputArtRecordsAvgInRange(String artXlsSrcDir,
			String type, String from1, String to1, String from2, String to2,
			String avgFile) {

		List<AvgOutputBean> result = getTypedArtRecordsAvgInRange(artXlsSrcDir,
				type, from1, to1, from2, to2);

		outputArtRecordsAvgInRangeToExcel(result, avgFile);

	}

	public static void outputArtRecordsAvgInRangeWithPctFilter(
			String artXlsSrcDir, String type, String from1, String to1,
			String from2, String to2, double percentage, String avgFile) {

		List<AvgOutputBean> result = getTypedArtRecordsAvgInRangeWithPctFileter(
				artXlsSrcDir, type, from1, to1, from2, to2, percentage);

		outputArtRecordsAvgInRangeToExcel(result, avgFile);

	}

	public static void outputArtRecordsAvgInRangeToExcel(
			List<AvgOutputBean> result, String avgFile) {

		for (int i = 0; i < result.size(); i++) {
			AvgOutputBean aob = result.get(i);

			System.out.println(aob.getModel1().toString());
			System.out.println(aob.getModel2().toString());
		}

		HSSFWorkbook wbAvg = new HSSFWorkbook();
		createAvgWorkbook(wbAvg, result);
		ExcelHelper.writeWorkbook(wbAvg, avgFile);

	}

	public static void outputArtRecordsAvgInRangeToExcelWithPctFilter(
			List<AvgOutputBean> result, int percentage, String avgFile) {

		for (int i = 0; i < result.size(); i++) {
			AvgOutputBean aob = result.get(i);

			System.out.println(aob.getModel1().toString());
			System.out.println(aob.getModel2().toString());
		}

		HSSFWorkbook wbAvg = new HSSFWorkbook();
		createAvgWorkbook(wbAvg, result);
		ExcelHelper.writeWorkbook(wbAvg, avgFile);

	}

	public static void outputArtRecordsAvgInRangeToExcelWithPctFilter(
			List<AvgOutputBean> result, String avgFile) {

		for (int i = 0; i < result.size(); i++) {
			AvgOutputBean aob = result.get(i);

			System.out.println(aob.getModel1().toString());
			System.out.println(aob.getModel2().toString());
		}

		HSSFWorkbook wbAvg = new HSSFWorkbook();
		createAvgWorkbook(wbAvg, result);
		ExcelHelper.writeWorkbook(wbAvg, avgFile);

	}

	public static void createAvgWorkbook(HSSFWorkbook wbAvg,
			List<AvgOutputBean> result) {

		ExcelHelper.fillBookByAvg(wbAvg, "avg", result);

	}

	public static void showProcessMap(Map<String, Model> processMap) {

		Iterator<String> it = processMap.keySet().iterator();

		while (it.hasNext()) {
			String pn = (String) it.next();
			Model avg = processMap.get(pn);

			System.out.println(avg.toString());
		}

	}

	public static void showProcessMap4AvgModel(Map<String, AvgModel> processMap) {

		Iterator<String> it = processMap.keySet().iterator();

		while (it.hasNext()) {
			String pn = (String) it.next();
			AvgModel avg = processMap.get(pn);

			System.out.println(avg.toString());
		}

	}

	public static void outputArtRecordsByType(String artXlsSrcDir, String toFile) {

		HSSFWorkbook wbType = new HSSFWorkbook();
		List<Model> allModelList = getAllArtModels(artXlsSrcDir);

		createWorkbookByType(wbType, allModelList);
		ExcelHelper.writeWorkbook(wbType, toFile);

	}

	public static Map<String, AvgModel> getArtAvgTimeByProcess(
			Map<String, List<Model>> allModelMap) {

		Map<String, AvgModel> result = new HashMap<String, AvgModel>();

		Set<String> reuqestNameSet = allModelMap.keySet();
		Iterator<String> it = reuqestNameSet.iterator();

		int ii = 0;

		while (it.hasNext()) {

			ii = ii + 1;

			String requestName = (String) it.next();
			MainProcessAllSingleProcess.logger.trace("to add sheet ---> "
					+ requestName);

			List<Model> modelList = allModelMap.get(requestName);

			double hitCountAll = 0;
			double totalAll = 0;

			for (int i = 0; i < modelList.size(); i++) {

				Model model = (Model) modelList.get(i);

				double hitCount = Double.parseDouble(model.getHitCount());
				double total = Double.parseDouble(model.getTotalElapsedTime());

				hitCountAll = hitCountAll + hitCount;
				totalAll = totalAll + total;

			}
			double avg = totalAll / hitCountAll;

			AvgModel avgModel = new AvgModel();
			avgModel.setRequestName(requestName);
			avgModel.setHitCount(String.valueOf(hitCountAll));
			avgModel.setTotalElapsedTime(String.valueOf(totalAll));
			avgModel.setAvgElapsedTime(String.valueOf(avg));

			result.put(requestName, avgModel);

		}

		return result;

	}

	public static void createWorkbookBySheet(HSSFWorkbook wb,
			Map<String, List<Model>> allModelMap) {

		Set<String> reuqestNameSet = allModelMap.keySet();
		Iterator<String> it = reuqestNameSet.iterator();

		int ii = 0;

		HSSFSheet sheetIndex = wb.createSheet("index");

		while (it.hasNext()) {

			ii = ii + 1;

			String requestName = (String) it.next();
			MainProcessAllSingleProcess.logger.trace("to add sheet ---> "
					+ requestName);

			String simpleName = requestName;
			if (requestName.lastIndexOf(".") > 0) {
				simpleName = requestName
						.substring(requestName.lastIndexOf(".") + 1);
			}

			String sheetName = "S" + ii + "-" + simpleName;

			HSSFSheet sheet = null;
			sheet = wb.createSheet(sheetName);

			// add row to index sheet
			HSSFRow rowIndex = sheetIndex.createRow((short) ii);
			HSSFCell cellIndex = rowIndex.createCell(0);
			cellIndex.setCellValue(sheetName);

			HSSFHyperlink link = new HSSFHyperlink(HSSFHyperlink.LINK_DOCUMENT);
			link.setAddress("'" + sheetName + "'!A1");
			cellIndex.setHyperlink(link);

			List<Model> modelList = allModelMap.get(requestName);

			// ------------------- start
			// header row

			ExcelHelper.creatRowHeader(sheet);

			// ------------------- end

			double hitCountAll = 0;
			double totalAll = 0;

			for (int i = 0; i < modelList.size(); i++) {

				Model model = (Model) modelList.get(i);
				// since second row
				HSSFRow row = sheet.createRow((short) (i + 1));

				HSSFCell cellRequestName = row.createCell(0);
				cellRequestName.setCellValue(model.getRequestName());

				HSSFCell cellRequestDate = row.createCell(1);

				cellRequestDate.setCellValue(Utils.createDate(model
						.getRequestDate()));

				HSSFCell cellHitCount = row.createCell(2);
				double hitCount = Double.parseDouble(model.getHitCount());
				cellHitCount.setCellValue(hitCount);

				HSSFCell cellMax = row.createCell(3);
				cellMax.setCellValue(Double.parseDouble(model
						.getMaxElapsedTime()));

				HSSFCell cellMin = row.createCell(4);
				cellMin.setCellValue(Double.parseDouble(model
						.getMinElapsedTime()));

				HSSFCell cellAvg = row.createCell(5);
				cellAvg.setCellValue(Double.parseDouble(model
						.getAvgElapsedTime()));

				HSSFCell cellTotal = row.createCell(6);
				double total = Double.parseDouble(model.getTotalElapsedTime());
				cellTotal.setCellValue(total);

				hitCountAll = hitCountAll + hitCount;
				totalAll = totalAll + total;

			}
			// list th summary

			HSSFCell hitCountCell = rowIndex.createCell(2); // ws count all
			hitCountCell.setCellValue(hitCountAll);

			HSSFCell totalCell = rowIndex.createCell(3); // ws total all
			totalCell.setCellValue(totalAll);

			HSSFCell totalAvgCell = rowIndex.createCell(4); // ws total all
			totalAvgCell.setCellValue(totalAll / hitCountAll);

		}

	}

}
