package com.utilites;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import com.base.Library;
import com.model.PageContext;
import com.reusable_functions.SeleniumResusable;

public class ExcelUtility extends Library {

	PageContext context;
	private FileInputStream read;
	private XSSFWorkbook wBook;
	private SeleniumResusable se;
	private File path;

	public ExcelUtility(PageContext context) {
		super(context);
		this.context = context;
	}

	// Method to read data from Excel and perform actions on the provided element
	public void readAndEnterDataFromExcel(String sheet, WebElement element) {
		path = new File("src/test/resources/TestData/FlipkartTestData.xlsx");
		try {
			read = new FileInputStream(path);
			wBook = new XSSFWorkbook(read);
		} catch (FileNotFoundException e) {
			throw new RuntimeException("Excel file not found", e);
		} catch (IOException e) {
			throw new RuntimeException("Failed to load Excel file", e);
		}

		XSSFSheet sh = wBook.getSheet(sheet);
		if (sh == null) {
			throw new RuntimeException("Sheet " + sheet + " not found in the Excel file");
		}

		int lastRowNum = sh.getLastRowNum();
		short lastCellNum = sh.getRow(0).getLastCellNum();

		for (int i = 1; i <= lastRowNum; i++) {
			XSSFRow row = sh.getRow(i);
			if (row == null)
				continue; // Skip empty rows

			for (int j = 0; j < lastCellNum; j++) {
				XSSFCell cell = row.getCell(j);
				if (cell == null)
					continue; // Skip empty cells

				String excelReadData = cell.getStringCellValue();
				if (excelReadData != null && !excelReadData.trim().isEmpty()) {
					// Initialize Selenium resusable object if not already done
					se = new SeleniumResusable(context);

					se.enterValue(element, excelReadData);

					// Simulate pressing the ENTER key to submit the search
					element.sendKeys(Keys.ENTER);

					System.out.println("Entered Value: " + excelReadData);

					se.navigateBack();
				} else {
					System.out.println("Empty or invalid data found at row " + (i + 1) + " column " + (j + 1));
				}
			}
		}
	}

	public void writeAndEnterDatatoExcel(String sheet, WebElement element, WebElement element1, String text) {
		File path = new File("src/test/resources/TestData/FlipkartTestData.xlsx");
		FileInputStream read = null;
		XSSFWorkbook wBook = null;
		SeleniumResusable se = null; // Initialize Selenium object once

		try {
			// Open the Excel file and create workbook object
			read = new FileInputStream(path);
			wBook = new XSSFWorkbook(read);
		} catch (FileNotFoundException e) {
			throw new RuntimeException("Excel file not found", e);
		} catch (IOException e) {
			throw new RuntimeException("Failed to load Excel file", e);
		}

		XSSFSheet sh = wBook.getSheet(sheet);
		if (sh == null) {
			throw new RuntimeException("Sheet " + sheet + " not found in the Excel file");
		}

		int lastRowNum = sh.getLastRowNum();
		short lastCellNum = sh.getRow(0).getLastCellNum();

		// Initialize Selenium Resusable object once outside the loops
		se = new SeleniumResusable(context);

		// Open the FileOutputStream only once after the Excel data update
		FileOutputStream write = null;

		// Iterate over the rows in the sheet (starting from row 1 to skip the header)
		for (int i = 1; i <= lastRowNum; i++) {
			XSSFRow row = sh.getRow(i);
			if (row == null)
				continue; // Skip empty rows

			// Iterate over the cells (starting from column 1 to skip the first column)
			for (int j = 1; j < lastCellNum; j++) {
				XSSFCell cell = row.getCell(j);
				if (cell == null)
					continue; // Skip empty cells

				String excelReadData = cell.getStringCellValue();
				if (excelReadData != null && !excelReadData.trim().isEmpty()) {
					// Log the entered value
					System.out.println("Entering Value: " + excelReadData);

					// Enter the value in the WebElement using Selenium Resusable object
					se.enterValue(element, excelReadData);

					// Simulate pressing the ENTER key to submit the form
					element.sendKeys(Keys.ENTER);

					// Navigate back after submitting the value
					se.navigateBack();

					// Determine pass/fail status based on element1 visibility
					String result = element1.isDisplayed() ? "pass" : "fail";

					// Update the cell value with the result (i.e., pass or fail)
					XSSFCell resultCell = row.createCell(lastCellNum); // Create a new column for the result if not
					// already present
					resultCell.setCellValue(result);
				} else {
					System.out.println("Empty or invalid data found at row " + (i + 1) + " column " + (j + 1));
				}
			}
		}

		// Write the updated data to Excel file after all processing is done
		try {
			write = new FileOutputStream(path);
			wBook.write(write);
		} catch (IOException e) {
			throw new RuntimeException("Failed to write updated data to Excel", e);
		} finally {
			try {
				if (read != null) {
					read.close();
				}
				if (write != null) {
					write.close();
				}
				if (wBook != null) {
					wBook.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
