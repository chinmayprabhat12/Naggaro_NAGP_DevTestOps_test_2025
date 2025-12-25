
package com.nagp.selenium.assignment.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.nagp.selenium.assignment.config.ConfigReader;

public class ExcelUtil {
	
	private static final String TEST_DATA_PATH = "test.data.path";

	public static Object[][] getTestData(String sheetName) {
		List<Object[]> testData = new ArrayList<>();

		try (FileInputStream fis = new FileInputStream(ConfigReader.getProperty(TEST_DATA_PATH));
				Workbook workbook = new XSSFWorkbook(fis)) {

			Sheet sheet = workbook.getSheet(sheetName);
			int rowCount = sheet.getPhysicalNumberOfRows();
			int colCount = sheet.getRow(0).getPhysicalNumberOfCells();

			for (int i = 1; i < rowCount; i++) {
				Row row = sheet.getRow(i);
				Object[] rowData = new Object[colCount];

				for (int j = 0; j < colCount; j++) {
					Cell cell = row.getCell(j);
					rowData[j] = getCellValue(cell);
				}
				testData.add(rowData);
			}
		} catch (IOException e) {
			throw new RuntimeException("Error reading Excel file", e);
		}

		return testData.toArray(new Object[0][]);
	}

	private static Object getCellValue(Cell cell) {
		if (cell == null)
			return "";

		switch (cell.getCellType()) {
		case STRING:
			return cell.getStringCellValue();
		case NUMERIC:
			double numericValue = cell.getNumericCellValue();
			if (numericValue == Math.floor(numericValue)) {
				return String.valueOf((int) numericValue);
			} else {
				return String.valueOf(numericValue);
			}
		case BOOLEAN:
			return String.valueOf(cell.getBooleanCellValue());
		default:
			return "";
		}
	}
}