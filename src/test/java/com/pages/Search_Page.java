package com.pages;

import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.model.PageContext;
import com.reusable_functions.SeleniumResusable;
import com.utilites.ExcelUtility;
import com.utilites.ExcelUtils;

public class Search_Page extends ExcelUtility {

	ExcelUtils excel;
	PageContext context;
	SeleniumResusable se;

	@FindBy(name = "q")
	WebElement search;
	
	By searchInput=By.name("q");
	
	@FindBy(xpath = "//*[@id='container']/div/div[2]/div/div")
	WebElement homepage;
	
	By homepageLink=By.xpath("//*[@id='container']/div/div[2]/div/div");

	public Search_Page(PageContext context) {
		super(context);
		this.context = context;
		PageFactory.initElements(context.getDriver(), this);
	}


	public void clickSearchBox() {
		se = new SeleniumResusable(context); // Assuming this class handles selenium operations
		search.click();
	}

	public void typeSearchProduct(String text) {
		se.enterValueAndEnter(search, text);
	}

	public void pressEnterKey() {
		search.sendKeys(Keys.ENTER);
	}

	public void waits() {
		se.waitsForInputVisibility(homepage);
	}
	
	

	public void closeUp() {
		tearDown();
	}

	public void dataReadFromExcel(String sheet) throws IOException {

		excel = new ExcelUtils();

		// Get the sheet "Sheet1"
		XSSFSheet sh = excel.getSheet(sheet);

		int lastRowNum = sh.getLastRowNum(); // Get the number of rows dynamically

		for (int i = 1; i <= lastRowNum; i++) {
			// Read data from the Excel sheet for each row
			XSSFRow row = sh.getRow(i);
			if (row == null) {
				continue;
			}
			for (int j = 0; j <= 0; j++) {
				String excelReadData = excel.excelReadData(sheet, i, j);

				// Assuming searchText is a WebElement representing the search input box
				se.enterValue(search, excelReadData); // Enter the value into the search input
				search.sendKeys(Keys.ENTER); // Simulate pressing ENTER key

				System.out.println(excelReadData);

				// Take screenshot with the product name as the filename
				se.screendshotFile("src/test/resources/screenshots/", excelReadData + ".png");

				// Wait for the page to load
//	        se.waits();  // Ensure this method is correctly implemented for waiting (e.g., Thread.sleep(), WebDriverWait)

				// Check if the homepage is displayed
				if (homepage.isDisplayed()) {
					excel.excelWrite("Sheet1", i, 1, "pass"); // Write "pass" if homepage is displayed
				} else {
					excel.excelWrite("Sheet1", i, 1, "fail"); // Write "fail" if homepage is not displayed
				}

				// Navigate back to the previous page (ensure this method is defined in
				// seleniumReusable)
				se.navigateBack();
			}
		}
		// Close resources once done
		excel.closeResources(); // Close the Excel resources
	}

}
