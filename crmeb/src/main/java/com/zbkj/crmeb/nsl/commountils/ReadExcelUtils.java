package com.zbkj.crmeb.nsl.commountils;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReadExcelUtils {
    public static List<Map> readExcel(MultipartFile file){

        List list = new ArrayList();
        String filename = file.getOriginalFilename();
        if(".xlsx".equals(filename.substring(filename.lastIndexOf(".")))){
            try {
                InputStream inputStream = file.getInputStream();
                XSSFWorkbook xwb = new XSSFWorkbook(inputStream);
                XSSFSheet sheet = xwb.getSheetAt(0);

                int numberOfRows = sheet.getPhysicalNumberOfRows();
                XSSFRow rowFirst = sheet.getRow(0);
                XSSFRow rowLast = sheet.getRow(numberOfRows - 1);

                for (int i = 1; i < numberOfRows-1; i++) {
                    XSSFRow row = sheet.getRow(i);
                    double radius = row.getCell(0).getNumericCellValue();//作业半径

                    for (int j = 1; j < row.getPhysicalNumberOfCells(); j++) {
                        XSSFCell cell = row.getCell(j);
                        if(cell != null && cell.getNumericCellValue() != 0){
                            double primaryLength = rowFirst.getCell(j).getNumericCellValue();//臂长
                            String way = rowLast.getCell(j).getStringCellValue(); //组合方式
                            double weight = cell.getNumericCellValue();//起重量

                            Map map = new HashMap();
                            map.put("primaryLength",primaryLength);
                            map.put("radius",radius);
                            map.put("way",way);
                            map.put("weight",weight);
                            list.add(map);
                        }
                    }
                }
                inputStream.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }else {
            try {
                InputStream inputStream = file.getInputStream();
                HSSFWorkbook hwb = new HSSFWorkbook(inputStream);
                HSSFSheet sheet = hwb.getSheetAt(0);

                int numberOfRows = sheet.getPhysicalNumberOfRows();
                HSSFRow rowFirst = sheet.getRow(0);
                HSSFRow rowLast = sheet.getRow(numberOfRows - 1);

                for (int i = 1; i < numberOfRows-1; i++) {
                    HSSFRow row = sheet.getRow(i);
                    double radius = row.getCell(0).getNumericCellValue();//作业半径

                    for (int j = 1; j < row.getPhysicalNumberOfCells(); j++) {
                        HSSFCell cell = row.getCell(j);
                        if(cell != null && cell.getNumericCellValue() != 0){
                            double primaryLength = rowFirst.getCell(j).getNumericCellValue();//臂长
                            String way = rowLast.getCell(j).getStringCellValue(); //组合方式
                            double weight = cell.getNumericCellValue();//起重量

                            Map map = new HashMap();
                            map.put("primaryLength",primaryLength);
                            map.put("radius",radius);
                            map.put("way",way);
                            map.put("weight",weight);
                            list.add(map);

                        }
                    }
                }
                inputStream.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return list;
    }
}
