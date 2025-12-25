package com.nagp.selenium.assignment.pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.nagp.selenium.assignment.base.BasePage;

public class ProductPage extends BasePage {

	private By productTitleLocators = By.xpath("//span[@id='productTitle'] | " + "//h1[contains(@class, 'title')] | "
			+ "//h1[contains(@class, 'product-title')] | " + "//h1[contains(@class, 'a-size-large')] | "
			+ "//div[@id='title']//span | " + "//h1");

	private By productPriceLocators = By.xpath("//span[@class='a-price-whole'] | "
			+ "//span[contains(@class, 'priceToPay')]//span[@class='a-offscreen'] | "
			+ "//span[contains(@class, 'a-price')] | " + "//span[contains(@class, 'price')] | "
			+ "//span[contains(text(), '₹')][1]");

	private By addToCartButtonLocators = By.xpath("//button[@id='a-autoid-3-announce']");

	private By noThanksButtonLocators = By.xpath("//input[@aria-labelledby*='attachSiNoCoverage'] | "
			+ "//button[@id='attachSiNoCoverage-announce'] | " + "//span[contains(text(), 'No Thanks')] | "
			+ "//button[contains(text(), 'No Thanks')] | " + "//a[contains(text(), 'No Thanks')]");

	private By addedToCartMessageLocators = By.xpath("//span[@id='nav-cart-count']");

	public ProductPage(WebDriver driver) {
		super(driver);
	}

	public String getProductTitle() {
		waitForPageLoad();

		try {
			WebElement titleElement = findFirstVisibleElement(productTitleLocators, 15);
			return titleElement.getText().trim();
		} catch (Exception e) {
			throw new RuntimeException("Could not find product title on the page", e);
		}
	}

	public String getProductPrice() {
		try {
			WebElement priceElement = findFirstVisibleElement(productPriceLocators, 15);
			return priceElement.getText().trim();
		} catch (Exception e) {
			throw new RuntimeException("Could not find product price on the page", e);
		}
	}

	public void addToCart() {
		try {
			WebElement addToCartButton = findFirstVisibleElement(addToCartButtonLocators, 15);
			scrollToElement(addToCartButton);
			click(addToCartButton);

			// Handle warranty popup if it appears
			handleWarrantyPopup();

		} catch (Exception e) {
			throw new RuntimeException("Could not find or click Add to Cart button", e);
		}
	}

	private void handleWarrantyPopup() {
		try {
			// Wait a moment for popup to appear
			Thread.sleep(2000);

			WebElement noThanksButton = findFirstVisibleElement(noThanksButtonLocators, 3);
			if (noThanksButton != null) {
				click(noThanksButton);
			}
		} catch (Exception e) {
			// Popup not shown, continue silently
		}
	}

	public boolean isAddedToCart() {
		try {
			// Wait for cart confirmation
			Thread.sleep(3000);

			WebElement confirmationElement = findFirstVisibleElement(addedToCartMessageLocators, 10);
			return confirmationElement != null && confirmationElement.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	// Helper method to find the first visible element from multiple possible
	// locators
	private WebElement findFirstVisibleElement(By locator, int timeoutInSeconds) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
			return wait.until(driver -> {
				List<WebElement> elements = driver.findElements(locator);
				for (WebElement element : elements) {
					try {
						if (element.isDisplayed()) {
							return element;
						}
					} catch (Exception e) {
						// Element not visible, continue to next
					}
				}
				return null;
			});
		} catch (Exception e) {
			return null;
		}
	}

	private void scrollToElement(WebElement element) {
		((JavascriptExecutor) driver).executeScript(
				"arguments[0].scrollIntoView({behavior: 'smooth', block: 'center', inline: 'center'});", element);
	}

	private void waitForPageLoad() {
		try {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			for (int i = 0; i < 30; i++) {
				if (js.executeScript("return document.readyState").equals("complete")) {
					break;
				}
				Thread.sleep(100);
			}
			// Additional wait for dynamic content
			Thread.sleep(1000);
		} catch (Exception e) {
			// Continue execution
		}
	}

}
