package com.org.adobevalidations;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;

public class ReadDataSheet {

	public HSSFWorkbook wb;
	public HSSFSheet ws;
	public int rowCount;
	public String className;
	public String sheetName;
	public String colName;
	public String value;


	public int getRownumber(String testCaseName, String colHeader) throws Exception {
		int rownumber = 0;
		rowCount = ws.getLastRowNum();
		for (int j = 1; j <= rowCount; j++) {
			HSSFRow row = ws.getRow(j);
		
			if (row.getCell(0).getStringCellValue().equalsIgnoreCase(testCaseName)) {
				rownumber = j;
				break;
			}
		}
		if (rownumber == 0) {
			throw new Exception("Class Entry missing in DataSheet");
		}
		getColumnNumber(colHeader);
		return rownumber;
	}

	public int getRownumber(String testCaseName) throws Exception {
		int rownumber = 0;
		rowCount = ws.getLastRowNum();
		for (int j = 1; j <= rowCount; j++) {
			HSSFRow row = ws.getRow(j);
			if (row.getCell(0).getStringCellValue().equalsIgnoreCase(testCaseName)) {
				rownumber = j;
				break;
			}
		}
		/*
		 * if (rownumber == 0) { throw new Exception(
		 * "Class Entry missing in DataSheet"); }
		 */
		// getColumnNumber(colHeader);
		return rownumber;
	}

	public int getColumnNumber(String columnHeader) throws Exception {
		HSSFRow row = ws.getRow(0);
		int columnNumber = 0;
		int isValid = 0;
		for (int j = ws.getFirstRowNum(); j < row.getPhysicalNumberOfCells(); j++) {
			// System.out.println("corresponding cell value is "+
			// row.getCell(j).toString());
			if (row.getCell(j).toString().equalsIgnoreCase(columnHeader)) {
				columnNumber = j;
				isValid = 1;
				break;
			}
		}
		if (isValid == 0) {
			throw new Exception("Enter proper column in DataSheet");
		}
		// ;
		return columnNumber;
	}

	public String getValue(String SheetName, String className, String columnHeader) throws Exception {
		try {
			FileInputStream file = new FileInputStream(new File("./DataSheet.xls"));
			wb = new HSSFWorkbook(file);
			ws = wb.getSheet(SheetName);
			// System.out.println("ClassName From Datasheet:"+className);
			int rownumber = getRownumber(className, columnHeader);
			int columnNumber = getColumnNumber(columnHeader);
			HSSFCell cell = ws.getRow(rownumber).getCell(columnNumber);
			if (cell != null) {
				value = cell.toString();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return value;
	}

	public String getValue(String SheetName, String className) throws Exception {
		try {
			FileInputStream file = new FileInputStream(new File("./DataSheet.xls"));
			wb = new HSSFWorkbook(file);
			ws = wb.getSheet(SheetName);
			int rownumber = getRownumber(className);
			// int columnNumber = getColumnNumber(columnHeader);
			HSSFCell cell = ws.getRow(rownumber).getCell(1);
			if (cell != null) {
				value = cell.toString();
			}
			// List<> testName = new ArrayList<>();
			/*
			 * for (int i = 2; i <= 3; i++) { for (int j = 1; j <= 3; j++) {
			 * HSSFCell cell1 = ws.getRow(i).getCell(j); } }
			 */
			// System.out.println("value is " + value);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return value;
	}

	public String getAppProperties(String key) throws IOException {
		String value = "";
		try {
			FileInputStream fileInputStream = new FileInputStream("data.properties");
			Properties property = new Properties();
			property.load(fileInputStream);
			value = property.getProperty(key);
			fileInputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}

	private String getCellValueAsString(HSSFCell cell, FormulaEvaluator formulaEvaluator) {
		if ((cell == null) || (cell.getCellType() == 3)) {
			return "";
		}
		if (formulaEvaluator.evaluate(cell).getCellType() == 5) {
			// throw new FrameworkException("Error in formula within this cell!
			// Error code: " +
			// cell.getErrorCellValue());
		}
		DataFormatter dataFormatter = new DataFormatter();
		return dataFormatter.formatCellValue(formulaEvaluator.evaluateInCell(cell));
	}

	public String getNumericValue(String SheetName, String className, String columnHeader) throws Exception {
		try {
			FileInputStream file = new FileInputStream(new File("./DataSheet.xls"));
			wb = new HSSFWorkbook(file);
			ws = wb.getSheet(SheetName);
			int rownumber = getRownumber(className, columnHeader);
			int columnNumber = getColumnNumber(columnHeader);
			HSSFCell cell = ws.getRow(rownumber).getCell(columnNumber);
			if (cell != null) {
				Long i = (long) cell.getNumericCellValue();
				value = i.toString();
			}
			// ;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return value;
	}
	// Method to get column values from Datasheet
		public static ArrayList<String> getColumValues(String columnWanted, String sheetName) throws Exception {
			FileInputStream fileIn = new FileInputStream(new File("./DataSheet.xls"));
			// read file
			POIFSFileSystem fs = new POIFSFileSystem(fileIn);
			HSSFWorkbook filename = new HSSFWorkbook(fs);
			// open sheet 0 which is first sheet of your worksheet
			HSSFSheet comscoreSheet = filename.getSheet(sheetName);
			// we will search for column index containing string "Your Column Name"
			// in the row 0 (which is first row of a worksheet
			Integer columnNo = null;
			// output all not null values to the list
			List<Cell> cells = new ArrayList<Cell>();
			List<String> excelParametersList = new ArrayList<String>();
			Row firstRow = comscoreSheet.getRow(0);
			for (Cell cell : firstRow) {
				if (cell.getStringCellValue().equals(columnWanted)) {
					columnNo = cell.getColumnIndex();
				}
			}
			if (columnNo != null) {
				for (Row row : comscoreSheet) {
					Cell c = row.getCell(columnNo);
					if (c == null || c.getCellType() == Cell.CELL_TYPE_BLANK) {
						// Nothing in the cell in this row, skip it
					} else {
						cells.add(c);
						excelParametersList.add(c.toString());
					}
				}
			} else {
				System.out.println("could not find column " + columnWanted + " in first row of " + fileIn.toString());
			}
			return (ArrayList<String>) excelParametersList;
		}
}
