package com.stepdefinations;

import java.io.IOException;

import org.openqa.selenium.support.PageFactory;

import com.model.PageContext;
import com.pages.Search_Page;
import com.reusable_functions.SeleniumResusable;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class Search_Stepdefination extends Search_Page {

	PageContext context;
	SeleniumResusable sr;
	Search_Page sp;

	public Search_Stepdefination(PageContext context) {
		super(context);
		this.context = context;
		PageFactory.initElements(context.getDriver(), this);
	}

	@Given("User should enter the url")
	public void user_should_enter_the_url() {
		launchApp();
	}

	@When("User should click input box")
	public void user_should_click_input_box() {
		sr = new SeleniumResusable(context);
		sp = new Search_Page(context);
		sp.clickSearchBox();
	}

	@When("User should enter the search products")
	public void user_should_enter_the_search_products() throws IOException {
		sp.dataReadFromExcel("Sheet1");
	}

	@When("user should click enter")
	public void user_should_click_enter() {
		sp.pressEnterKey();
	}

	@Then("User get the result")
	public void user_get_the_result() {
		sr.getTitle();
		sp.closeUp();
	}
}
