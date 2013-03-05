package com.oocl.inittools.perfrpt.common;

import java.io.File;


public class FileHelper {

	public static String[] readDir(String folder) {
	
		File dir = new File(folder);
		if (!dir.isDirectory())
			throw new IllegalArgumentException("FileLister: no such directory");
	
		File[] files = dir.listFiles();
		String[] fileFullNames = new String[files.length];
		for (int i = 0; i < files.length; i++) {
			fileFullNames[i] = files[i].getAbsolutePath();
		}
	
		Sorter.sort(fileFullNames);
		return fileFullNames;
	}

}
