package com.runner;

import org.testng.annotations.Test;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
		features = "src/test/resources/features/search_product.feature", 
		dryRun = !true, 
		monochrome = true, 
		glue = "com.stepdefinations", 
		plugin = {
				"pretty", 
				"html:src/test/resources/reports/reports.html"
				}, 
		tags = "@reg"
		)
@Test
public class TestRunner extends AbstractTestNGCucumberTests {

}
