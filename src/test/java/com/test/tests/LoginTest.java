package com.test.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.nagp.selenium.assignment.base.BaseTest;
import com.nagp.selenium.assignment.excel.ExcelDataProvider;
import com.nagp.selenium.assignment.pages.HomePage;
import com.nagp.selenium.assignment.pages.LoginPage;

public class LoginTest extends BaseTest {

	@Test(priority = 1)
	public void testEmptyEmailLogin() {
		HomePage homePage = new HomePage(driver);
		LoginPage loginPage = new LoginPage(driver);

		homePage.navigateToHomePage();
		homePage.clickSignIn();

		loginPage.enterEmail("");

		Assert.assertTrue(loginPage.getRequiredErrorMessage().contains("Enter your mobile number or email1"));
	}

	@Test(priority = 2, dataProvider = "loginData", dataProviderClass = ExcelDataProvider.class)
	public void testInvalidLogin(String email, String password) {
		HomePage homePage = new HomePage(driver);
		LoginPage loginPage = new LoginPage(driver);

		homePage.navigateToHomePage();
		homePage.clickSignIn();

		loginPage.enterEmail(email);
		loginPage.enterPassword(password);

		if (loginPage.isCaptchaPresent()) {
			System.out.println("CAPTCHA detected, skipping test");
			return;
		}

		String errorMessage = loginPage.getErrorMessage();
		Assert.assertTrue(
				errorMessage.toLowerCase().contains("incorrect") || errorMessage.toLowerCase().contains("problem"),
				"Expected error message not found. Actual: " + errorMessage);
	}
}
