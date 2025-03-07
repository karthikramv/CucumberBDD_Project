package com.reusable_functions;

import java.io.File;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.base.Library;
import com.model.PageContext;

public class SeleniumResusable extends Library {
	
	PageContext context;

	public SeleniumResusable(PageContext context) {
		super(context);
		this.context = context;
	}

	public void click(WebElement element) {
		element.click();
	}

	public void enterValueAndEnter(WebElement element, String text) {
		element.sendKeys(text, Keys.ENTER);
	}
	
	public void enterValue(WebElement element,String text) {
		((WebElement) element).sendKeys(text);
	}
	
	public void getTitle() {
		context.getDriver().getTitle();
	}
	
	public void waitsForInputVisibility(WebElement element) {
		context.getWait().until(ExpectedConditions.elementToBeClickable(element));
	}
	
	public void screendshotFile(String path, String filename) {
		File screenshotAs = context.getDriver().getScreenshotAs(OutputType.FILE);
		try {
			FileHandler.copy(screenshotAs, new File(path, filename));
		} catch (IOException e) {
			throw new RuntimeException("Couldn't take screenshot");
		}
	}

}
