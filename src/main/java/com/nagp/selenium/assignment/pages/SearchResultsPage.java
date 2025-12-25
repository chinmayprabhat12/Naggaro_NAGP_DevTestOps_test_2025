package com.nagp.selenium.assignment.pages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.nagp.selenium.assignment.base.BasePage;

public class SearchResultsPage extends BasePage {

	
	@FindBy(xpath = "(//a[@class='a-link-normal s-line-clamp-2 s-line-clamp-3-for-col-12 s-link-style a-text-normal'])[3]")
	private List<WebElement> searchResults;

	@FindBy(xpath = "//div[@data-component-type='s-search-result']//span[@class='a-price-whole']")
	private List<WebElement> productPrices;

	public SearchResultsPage(WebDriver driver) {
		super(driver);
	}

	public void clickFirstSearchResult() {
		waitForElement(searchResults.get(0));
		click(searchResults.get(0));
	}

	public String getFirstProductPrice() {
		waitForElement(productPrices.get(0));
		return getText(productPrices.get(0));
	}

	public int getSearchResultsCount() {
		return searchResults.size();
	}
}