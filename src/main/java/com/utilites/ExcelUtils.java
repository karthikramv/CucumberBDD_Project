package com.utilites;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtils {

    private XSSFWorkbook wBook;
    private FileInputStream read;
    private FileOutputStream write;
    private File path;
    
    // Constructor to initialize the file path and workbook
    public ExcelUtils() throws IOException {
        path = new File("src/test/resources/TestData/FlipkartTestData.xlsx");
        read = new FileInputStream(path);
        wBook = new XSSFWorkbook(read);
    }
    // Method to get a specific sheet by name
    public XSSFSheet getSheet(String sheetName) {
        return wBook.getSheet(sheetName);  // Returns the sheet by name
    }

    // Method to read data from Excel
    public String excelReadData(String sheetName, int rowNum, int colNum) {
        XSSFSheet sheet = wBook.getSheet(sheetName);
        
        // Check if sheet is null
        if (sheet == null) {
            throw new IllegalArgumentException("Sheet with name '" + sheetName + "' not found.");
        }
        
        // Get the row and check if it is null
        XSSFRow row = sheet.getRow(rowNum);
        if (row == null) {
            System.out.println("Row " + rowNum + " is empty or does not exist.");
        }

        // Get the cell and return its value
        XSSFCell cell = row.getCell(colNum);
        if (cell == null) {
            return "";  // Return an empty string if the cell is empty
        }

        return cell.toString();  // Convert cell to string and return the value
    }

    // Method to write data to the Excel sheet
    public void excelWrite(String sheetName, int rowNum, int colNum, String data) throws IOException {
        XSSFSheet sheet = getSheet(sheetName);
        XSSFRow row = sheet.getRow(rowNum);
        if (row == null) {
            row = sheet.createRow(rowNum);  // Create a new row if it doesn't exist
        }
        XSSFCell cell = row.createCell(colNum);
        cell.setCellValue(data);  // Write the data to the cell
        
        write = new FileOutputStream(path);  // Open the file in write mode
        wBook.write(write);  // Write the changes to the file
    }

    // Method to close resources
    public void closeResources() throws IOException {
        read.close();
        if (write != null) {
            write.close();
        }
        wBook.close();
    }
	
}
