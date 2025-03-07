package com.stepdefinations;

import org.openqa.selenium.support.PageFactory;

import com.model.PageContext;
import com.pages.Search_Page;

import io.cucumber.java.en.When;

public class Search_defi extends Search_Page {

	PageContext context;
	Search_Page sp;
	public Search_defi(PageContext context) {
		super(context);
		this.context=context;
		PageFactory.initElements(context.getDriver(), this);
	}

	@When("User should enter the search {string}")
	public void user_should_enter_the_search(String string) {
		sp=new Search_Page(context);
		sp.clickSearchBox();
		sp.typeSearchProduct(string);
		sp.pressEnterKey();
	}


}
