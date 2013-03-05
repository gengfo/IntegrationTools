package com.oocl.inittools.perfrpt.common;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.oocl.inittools.perfrpt.daily.DailyHelper;

public class Utils {

	public static String getDate(String sourceFile) {
	
		return sourceFile.substring(
				sourceFile.length() - "yyyymmdd.xls".length(),
				sourceFile.length() - ".xls".length());
	
	}

	public static void showModels(List<Model> models) {
		DailyHelper.logger.trace("--------------------enter show models -----------------");
	
		for (int i = 0; i < models.size(); i++) {
			Model model = models.get(i);
			DailyHelper.logger.trace(model.toString());
		}
		DailyHelper.logger.trace("--------------------exit show models -----------------");
	}

	public static Date createDate(String dateString) {
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		ParsePosition pos = new ParsePosition(0);
		return formatter.parse(dateString, pos);
	
	}

	public static void showModel(Map<String, List<Model>> modelMap) {
		Set ks = modelMap.keySet();
		Iterator kit = ks.iterator();
		while (kit.hasNext()) {
			String kProcessName = (String) kit.next();
			System.out.println("------------------------ " + kProcessName
					+ " ------------------------");
	
			List<Model> ml = (List<Model>) modelMap.get(kProcessName);
			for (int i = 0; i < ml.size(); i++) {
				Model m = (Model) ml.get(i);
				System.out.println(m.toString());
			}
	
		}
	
	}

	public static void showModel(List<Model> ml) {
	
		for (int i = 0; i < ml.size(); i++) {
			Model m = (Model) ml.get(i);
			System.out.println(m.toString());
		}
	
	}

}
