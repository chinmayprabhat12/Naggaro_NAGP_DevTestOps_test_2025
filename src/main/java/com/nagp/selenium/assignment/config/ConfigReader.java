package com.nagp.selenium.assignment.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
	
	private static final String SRC_MAIN_RESOURCES_CONFIG_PROPERTIES = "src/main/resources/config.properties";
	private static Properties properties;

	static {
		properties = new Properties();
		try {
			FileInputStream fis = new FileInputStream(SRC_MAIN_RESOURCES_CONFIG_PROPERTIES);
			properties.load(fis);
			fis.close();
		} catch (IOException e) {
			throw new RuntimeException("Failed to load config.properties file: " + e.getMessage());
		}
	}

	public static String getProperty(String key) {
		return properties.getProperty(key);
	}

	public static int getIntProperty(String key) {
		return Integer.parseInt(properties.getProperty(key));
	}

	public static long getLongProperty(String key) {
		return Long.parseLong(properties.getProperty(key));
	}
}