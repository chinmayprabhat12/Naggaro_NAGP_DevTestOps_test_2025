package com.nagp.selenium.assignment.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.nagp.selenium.assignment.base.BasePage;

public class HomePage extends BasePage {

	@FindBy(id = "nav-link-accountList")
	private WebElement signInButton;

	@FindBy(id = "twotabsearchtextbox")
	private WebElement searchBox;

	@FindBy(id = "nav-search-submit-button")
	private WebElement searchSubmitButton;

	public HomePage(WebDriver driver) {
		super(driver);
	}

	public void navigateToHomePage() {
		driver.get("https://www.amazon.in/");
	}

	public void clickSignIn() {
		click(signInButton);
	}

	public void searchForProduct(String productName) {
		type(searchBox, productName);
		click(searchSubmitButton);
	}
}