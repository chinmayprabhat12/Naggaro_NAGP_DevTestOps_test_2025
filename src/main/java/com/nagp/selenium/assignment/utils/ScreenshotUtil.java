package com.nagp.selenium.assignment.utils;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.nagp.selenium.assignment.config.ConfigReader;

public class ScreenshotUtil {

	private static final String SCREENSHOT_DIR = "screenshot.dir";
	private static final String YYYY_M_MDD_H_HMMSS = "yyyyMMdd_HHmmss";

	public static String captureScreenshot(WebDriver driver, String testName) {
		
		String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern(YYYY_M_MDD_H_HMMSS));
		String fileName = testName + "_" + timestamp + ".png";
		
		String screenShotDir = ConfigReader.getProperty(SCREENSHOT_DIR);

		String filePath = screenShotDir.concat(File.separator) + fileName;

		try {
			File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(screenshot, new File(filePath));
			return filePath;
		} catch (IOException e) {
			throw new RuntimeException("Failed to capture screenshot: " + e.getMessage());
		}
	}
}