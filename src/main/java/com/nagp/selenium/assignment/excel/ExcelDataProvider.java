package com.nagp.selenium.assignment.excel;

import org.testng.annotations.DataProvider;

import com.nagp.selenium.assignment.utils.ExcelUtil;

public class ExcelDataProvider {

	@DataProvider(name = "loginData")
	public static Object[][] getLoginData() {
		return ExcelUtil.getTestData("login");
	}

	@DataProvider(name = "productData")
	public static Object[][] getProductData() {
		return ExcelUtil.getTestData("product");
	}

	@DataProvider(name = "searchData")
	public static Object[][] getSearchData() {
		return ExcelUtil.getTestData("search");
	}

}