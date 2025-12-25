
package com.nagp.selenium.assignment.base;

import java.io.File;
import java.lang.reflect.Method;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.aventstack.extentreports.ExtentTest;
import com.nagp.selenium.assignment.config.ConfigReader;
import com.nagp.selenium.assignment.reporting.ExtentReportManager;

@Listeners({ ExtentReportManager.class })
public class BaseTest {

	protected WebDriver driver;
	protected ExtentTest test;
	protected static final Logger logger = LogManager.getLogger(BaseTest.class);

	@BeforeSuite
	public void setUpSuite() {
		ExtentReportManager.initializeReport();
		logger.info("Test suite setup completed");
	}

	@AfterSuite
	public void tearDownSuite() {
		ExtentReportManager.flushReport();
		logger.info("Test suite teardown completed");
	}

	@BeforeMethod
	@Parameters("browser")
	public void setUp(@Optional("chrome") String browser, Method method) {
		// Create test in Extent Report
		ExtentReportManager.createTest(method.getName(), "Test: " + method.getName());
		test = ExtentReportManager.getTest();
		logger.info("Starting test: {}", method.getName());

		browser = ConfigReader.getProperty("browser");

		initializeDriver(browser);

		driver.manage().window().maximize();
		driver.manage().timeouts()
				.implicitlyWait(java.time.Duration.ofSeconds(ConfigReader.getIntProperty("implicit.wait")));
	}

	private void initializeDriver(String browser) {
		switch (browser.toLowerCase()) {
		case "chrome":
			driver = new ChromeDriver();
			break;
		case "firefox":
			driver = new FirefoxDriver();
			break;
		case "edge":
			driver = new EdgeDriver();
			break;
		default:
			throw new IllegalArgumentException("Unsupported browser: " + browser);
		}
		logger.info("Initialized {} driver", browser);
	}

	@AfterMethod
	public void tearDown(ITestResult result) {
		// Capture screenshot on failure
		if (result.getStatus() == ITestResult.FAILURE) {
			captureScreenshot(result.getName());
		}

		// Quit driver
		if (driver != null) {
			driver.quit();
			logger.info("WebDriver closed");
		}

		// Clean up ExtentTest thread local
		ExtentReportManager.removeTest();
	}

	protected void captureScreenshot(String testName) {
		try {
			if (driver != null) {
				File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

				String screenshotPath = System.getProperty("user.dir").concat(File.separator)
						.concat(ConfigReader.getProperty("screenshot.dir").concat(File.separator) + testName + "_"
								+ "InvalidCase" + ".png");

				FileUtils.copyFile(screenshot, new java.io.File(screenshotPath));

				ExtentReportManager.addScreenshotToReport(screenshotPath, "Failure Screenshot");
				logger.info("Screenshot captured: {}", screenshotPath);
			}
		} catch (Exception e) {
			logger.error("Failed to capture screenshot: {}", e.getMessage());
		}
	}
}