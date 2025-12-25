package com.test.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.nagp.selenium.assignment.base.BaseTest;
import com.nagp.selenium.assignment.excel.ExcelDataProvider;
import com.nagp.selenium.assignment.pages.HomePage;
import com.nagp.selenium.assignment.pages.SearchResultsPage;

public class SearchTest extends BaseTest {

	@Test(dataProvider = "searchData", dataProviderClass = ExcelDataProvider.class)
	public void testProductSearch(String productName) {
		HomePage homePage = new HomePage(driver);
		SearchResultsPage searchResultsPage = new SearchResultsPage(driver);

		homePage.navigateToHomePage();
		homePage.searchForProduct(productName);

		int resultsCount = searchResultsPage.getSearchResultsCount();
		Assert.assertTrue(resultsCount > 0, "No search results found for product: " + productName);
	}

	@Test(dataProvider = "productData", dataProviderClass = ExcelDataProvider.class)
	public void testRandomProductSearch(String product) {
		HomePage homePage = new HomePage(driver);
		SearchResultsPage searchResultsPage = new SearchResultsPage(driver);

		homePage.navigateToHomePage();
		homePage.searchForProduct(product);

		int resultsCount = searchResultsPage.getSearchResultsCount();
		Assert.assertTrue(resultsCount > 0, "No search results found for random product: " + product);
	}
}