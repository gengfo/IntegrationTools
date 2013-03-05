package com.oocl.inittools.perfrpt.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertyLoading {

    private Properties propertie;

    private FileInputStream inputFile;

    private FileOutputStream outputFile;

    public PropertyLoading() {
        propertie = new Properties();
    }

    public PropertyLoading(String filePath) {
        propertie = new Properties();
        try {
            inputFile = new FileInputStream(filePath);
            propertie.load(inputFile);
            inputFile.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public String getValue(String key) {
        if (propertie.containsKey(key)) {
            String value = propertie.getProperty(key);
            return value;
        } else
            return "";
    }

    public String getValue(String fileName, String key) {
        try {
            String value = "";
            inputFile = new FileInputStream(fileName);
            propertie.load(inputFile);
            inputFile.close();
            if (propertie.containsKey(key)) {
                value = propertie.getProperty(key);
                return value;
            } else
                return value;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return "";
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        } catch (Exception ex) {
            ex.printStackTrace();
            return "";
        }
    }

    public void clear() {
        propertie.clear();
    }

    public void setValue(String key, String value) {
        propertie.setProperty(key, value);
    }

    public void saveFile(String fileName, String description) {
        try {
            outputFile = new FileOutputStream(fileName);
            propertie.store(outputFile, description);
            outputFile.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public static void main(String[] args) {
        
        File file = new File(".");
        
        System.out.println(file.getAbsolutePath());
        
        PropertyLoading rc = new PropertyLoading("D:\\ToShare\\arp-iris4\\ARP_APP_Server\\ARP_Build\\build-tools\\performance-report\\src\\main\\resources\\nextToHandleData.txt");

        String nextToHandleDate = rc.getValue("nextToHandleDate");

        System.out.println("nextToHandleDate = " + nextToHandleDate);

        //PropertyLoading cf = new PropertyLoading();
        String nextToHandleDate1 = rc.getValue("D:\\ToShare\\arp-iris4\\ARP_APP_Server\\ARP_Build\\build-tools\\performance-report\\src\\main\\resources\\nextToHandleData.txt", "nextToHandleDate");
        System.out.println("nextToHandleDate = " + nextToHandleDate1);

        //
        // // cf.clear();
        // cf.setValue("min", "999");
        // cf.setValue("max", "1000");
        // cf.saveFile(".\config\save.perperties", "test");
        //
        // // Configuration saveCf = new Configuration();
        // // saveCf.setValue("min", "10");
        // // saveCf.setValue("max", "1000");
        // // saveCf.saveFile(".\config\save.perperties");

    }
}
