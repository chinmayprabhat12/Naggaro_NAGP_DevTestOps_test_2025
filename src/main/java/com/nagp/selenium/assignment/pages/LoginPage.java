package com.nagp.selenium.assignment.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.nagp.selenium.assignment.base.BasePage;

public class LoginPage extends BasePage {

	@FindBy(id = "ap_email_login")
	private WebElement emailInput;

	@FindBy(xpath = "//input[@type='submit']")
	private WebElement continueButton;

	@FindBy(id = "ap_password")
	private WebElement passwordInput;

	@FindBy(id = "signInSubmit")
	private WebElement signInSubmitButton;

	@FindBy(className = "a-alert-content")
	private WebElement errorMessage;

	@FindBy(xpath = "//div[@id='empty-claim-alert']")
	private WebElement requiredErrorMessage;

	@FindBy(css = "form[action*='verify'] input[type='text'], #auth-captcha-image, .a-box.a-alert-inline")
	private WebElement captchaElement;

	public LoginPage(WebDriver driver) {
		super(driver);
	}

	public void enterEmail(String email) {
		type(emailInput, email);
		click(continueButton);
	}

	public void enterPassword(String password) {
		type(passwordInput, password);
		click(signInSubmitButton);
	}

	public String getErrorMessage() {
		waitForElement(errorMessage);
		return getText(errorMessage);
	}

	public String getRequiredErrorMessage() {
		waitForElement(requiredErrorMessage);
		return getText(requiredErrorMessage);
	}

	public boolean isCaptchaPresent() {
		try {
			return captchaElement.isDisplayed() || driver.getPageSource().contains("captcha")
					|| driver.getPageSource().contains("puzzle") || driver.getPageSource().contains("verify");
		} catch (Exception e) {
			return false;
		}
	}
}