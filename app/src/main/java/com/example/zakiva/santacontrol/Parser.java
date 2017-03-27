package com.example.zakiva.santacontrol;

import android.util.Log;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by MLB on 3/27/2017.
 */

public class Parser {

    public static Object formatCell(HSSFCell cell) {

        int type;
        Object result = null;
        type = cell.getCellType();
        if (type == 0) {
            result = (int) cell.getNumericCellValue();
        } else {
            result = cell.getStringCellValue();
        }
        if (result.toString().contains(",")) {
            return mySplit(result.toString());
        } else {
            return result.toString().trim();
        }
    }


    public static Object mySplit(String str) {
        ArrayList<String> aList = new ArrayList<String>(Arrays.asList(str.split(",")));
        ArrayList<String> a = new ArrayList<>();
        for (String s : aList) {
            String st =s.trim();
            a.add(st);
        }
        return a;
    }

    public ArrayList<HashMap<String, Object>> readExcelFile(String SheetName, InputStream im) {

        HashMap<Integer, String> headers = new HashMap<>();
        ArrayList<HashMap<String, Object>> cellArrayHolder = new ArrayList<>();
        try {
            //FileInputStream myInput = new FileInputStream(fileName);
            POIFSFileSystem myFileSystem = new POIFSFileSystem(im);
            HSSFWorkbook myWorkBook = new HSSFWorkbook(myFileSystem);
            HSSFSheet mySheet = myWorkBook.getSheet(SheetName);
            Iterator<Row> rowIter = mySheet.rowIterator();
            HSSFRow myRow = (HSSFRow) rowIter.next();
            Iterator<Cell> cellIter = myRow.cellIterator();
            HSSFCell myCell;

            while (cellIter.hasNext()) {
                myCell = (HSSFCell) cellIter.next();
                headers.put(myCell.getColumnIndex(), myCell.toString());
            }
            while (rowIter.hasNext()) {
                myRow = (HSSFRow) rowIter.next();
                cellIter = myRow.cellIterator();
                HashMap<String, Object> line = new HashMap<>();
                while (cellIter.hasNext()) {
                    myCell = (HSSFCell) cellIter.next();
                    line.put(headers.get(myCell.getColumnIndex()), formatCell(myCell));
                }
                cellArrayHolder.add(line);
            }
            //myWorkBook.close(); no need in new version
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d(MainActivity.TAG, "returning cell array holder ");
        return cellArrayHolder;
    }
    public void saveSheetToFirebase(String sheetName, InputStream im) {
        ArrayList<HashMap<String, Object>> data = readExcelFile(sheetName, im);
        Infra.addSheet(sheetName, data);
    }
    public void saveQuestionsToFirebase(String sheetName, InputStream im) {
        ArrayList<HashMap<String, Object>> data = readExcelFile(sheetName, im);
        Infra.addQuestions(sheetName, data);
        Log.d(MainActivity.TAG, "success");
    }
}
