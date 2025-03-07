package com.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.model.PageContext;

public class Library {

	FileInputStream input;
	RemoteWebDriver driver;
	Logger logger;
	PageContext context;

	public Library(PageContext context) {
		this.context = context;
	}

	public void launchApp() {
		try {
			input = new FileInputStream("src/test/resources/properties/config.property");
		} catch (FileNotFoundException e) {
			throw new RuntimeException("File not found");
		}
		Properties prop = new Properties();
		try {
			prop.load(input);
		} catch (IOException e) {
			throw new RuntimeException("Can't get the property");
		}
		context.setProp(prop);

		if (context.getProp().getProperty("browser").equalsIgnoreCase("chrome")) {
			driver = new ChromeDriver();
			context.setDriver(driver);
		} else if (context.getProp().getProperty("browser").equalsIgnoreCase("firefox")) {
			driver = new FirefoxDriver();
			context.setDriver(driver);
		}
		WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(20));
		context.setWait(wait);
		
		logger=LogManager.getLogger(Library.class);
		
		logger.info("Browser launched");
		context.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		context.getDriver().manage().window().maximize();
		context.getDriver().get(context.getProp().getProperty("url"));
	}
	
	public void tearDown() {
//		logger.info("Webdriver closed");
		context.getDriver().quit();
	}
	public void navigateBack() {
		context.getDriver().navigate().back();
	}

}
