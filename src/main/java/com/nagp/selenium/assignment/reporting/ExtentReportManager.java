
package com.nagp.selenium.assignment.reporting;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.nagp.selenium.assignment.config.ConfigReader;

public class ExtentReportManager implements ITestListener, ISuiteListener {

	private static final String MMM_DD_YYYY_HH_MM_SS = "MMM dd, yyyy HH:mm:ss";
	private static final String REPORT_FILE_NAME = "report.file.name";
	private static final String CURRENT_RESULTS_DIR = "current.results.dir";

	private static final Logger logger = LogManager.getLogger(ExtentReportManager.class);
	private static ExtentReports extent;
	private static final ThreadLocal<ExtentTest> extentTestThreadLocal = new ThreadLocal<>();

	public static void initializeReport() {
		try {
			String currentResultsDir = ConfigReader.getProperty(CURRENT_RESULTS_DIR);
			String reportFileName = ConfigReader.getProperty(REPORT_FILE_NAME);

			// Create directories if they don't exist
			createDirectories(currentResultsDir);

			// Initialize ExtentReports
			String reportPath = currentResultsDir + File.separator + reportFileName;
			ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);

			// Configuration
			sparkReporter.config().setDocumentTitle("Automation Test Report");
			sparkReporter.config().setReportName("Test Execution Results");
			sparkReporter.config().setTheme(Theme.DARK);
			sparkReporter.config().setTimeStampFormat(MMM_DD_YYYY_HH_MM_SS);

			extent = new ExtentReports();
			extent.attachReporter(sparkReporter);

			// System info
			extent.setSystemInfo("OS", System.getProperty("os.name"));
			extent.setSystemInfo("Java Version", System.getProperty("java.version"));
			extent.setSystemInfo("User", System.getProperty("user.name"));

			logger.info("ExtentReports initialized at: {}", reportPath);

		} catch (Exception e) {
			logger.error("Failed to initialize ExtentReports: {}", e.getMessage(), e);
		}
	}

	private static void createDirectories(String directoryPath) throws IOException {
		Path path = Paths.get(directoryPath);
		if (!Files.exists(path)) {
			Files.createDirectories(path);
		}
	}

	public static ExtentTest createTest(String testName, String description) {
		if (extent == null) {
			initializeReport();
		}
		ExtentTest test = extent.createTest(testName, description);
		extentTestThreadLocal.set(test);
		return test;
	}

	public static ExtentTest getTest() {
		return extentTestThreadLocal.get();
	}

	public static void removeTest() {
		extentTestThreadLocal.remove();
	}

	public static void flushReport() {
		if (extent != null) {
			extent.flush();
			logger.info("Extent report flushed successfully");
		}
	}

	public static void addScreenshotToReport(String path, String title) {
		try {
			File f = new File(path);
			if (getTest() != null && f.exists())
				getTest().addScreenCaptureFromPath(path, title);
		} catch (Exception e) {
			logger.error("Failed to add screenshot: {}", e.getMessage());
		}
	}

	public static ExtentReports getExtent() {
		return extent;
	}

	@Override
	public void onTestStart(ITestResult result) {
		createTest(result.getMethod().getMethodName(), result.getMethod().getDescription());
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		getTest().pass("Test passed successfully");
	}

	@Override
	public void onTestFailure(ITestResult result) {
		getTest().fail("Test failed: " + result.getThrowable());
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		getTest().skip("Test skipped");
	}

	@Override
	public void onStart(ISuite suite) {
		logger.info("Suite started: {}", suite.getName());
	}

	@Override
	public void onFinish(ISuite suite) {
		flushReport();
		logger.info("Suite completed: {}", suite.getName());
	}
}