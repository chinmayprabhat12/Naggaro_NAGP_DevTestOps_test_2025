package com.test.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.nagp.selenium.assignment.base.BaseTest;
import com.nagp.selenium.assignment.excel.ExcelDataProvider;
import com.nagp.selenium.assignment.pages.HomePage;
import com.nagp.selenium.assignment.pages.ProductPage;
import com.nagp.selenium.assignment.pages.SearchResultsPage;

public class ProductTest extends BaseTest {

//	@Test(dataProvider = "productData", dataProviderClass = ExcelDataProvider.class)
//	public void testProductDetailsDisplay(String product) {
//		HomePage homePage = new HomePage(driver);
//		SearchResultsPage searchResultsPage = new SearchResultsPage(driver);
//		ProductPage productPage = new ProductPage(driver);
//
//		homePage.navigateToHomePage();
//		homePage.searchForProduct(product);
//		searchResultsPage.clickFirstSearchResult();
//
//		String productTitle = productPage.getProductTitle();
//		String productPrice = productPage.getProductPrice();
//
//		Assert.assertNotNull(productTitle, "Product title should not be null");
//		Assert.assertFalse(productTitle.isEmpty(), "Product title should not be empty");
//		Assert.assertNotNull(productPrice, "Product price should not be null");
//		Assert.assertFalse(productPrice.isEmpty(), "Product price should not be empty");
//	}

	@Test(dataProvider = "productData", dataProviderClass = ExcelDataProvider.class)
	public void testAddToCartFunctionality(String product) {
		HomePage homePage = new HomePage(driver);
		ProductPage productPage = new ProductPage(driver);

		homePage.navigateToHomePage();
		homePage.searchForProduct(product);
		productPage.addToCart();

		boolean isAdded = productPage.isAddedToCart();
		Assert.assertTrue(isAdded, "Product should be added to cart successfully");
	}

}