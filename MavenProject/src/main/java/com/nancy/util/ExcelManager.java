package com.nancy.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.nancy.constants.ProjectConstants;

public class ExcelManager {
	private String excelPathName;
	private Workbook excelWorkBook;
	private Sheet excelSheet;
	private Cell excelCell;
	private Row exceColumn;

	public ExcelManager(String excelPathName) {
		this.excelPathName = excelPathName;
		File excelFile = new File(excelPathName);
		if (excelFile.exists()) {
			try {
				FileInputStream fis = new FileInputStream(excelFile);
				String fileExtend = excelPathName.substring(excelPathName.indexOf("."));
				if (fileExtend == "xls") {
					excelWorkBook = new HSSFWorkbook(fis);
				} else if (fileExtend == "xlsx") {
					excelWorkBook = new XSSFWorkbook(fis);
				} else {
					MyReporter.error("Invalid file format, here need excel file!");
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
			MyReporter.error("Invalid file path, the actual file path is: " + excelPathName);
		}

	}

	public Sheet getSheet(String sheetName) {
		return this.excelWorkBook.getSheet(sheetName);
	}

	public String getCellData(String sheetName, int rowNum, int colNum) {
		Sheet currentSheet = getSheet(sheetName);
		Row currentRow = currentSheet.getRow(rowNum);
		Cell currentCell = currentRow.getCell(colNum);
		if (currentCell.getCellTypeEnum() == CellType.NUMERIC) {
			return String.valueOf(currentCell.getNumericCellValue());
		} else {

			return currentCell.getStringCellValue();
		}

	}

	public void setCellData(String sheetName, int rowNum, int colNum, String value) {
		Sheet currentSheet = getSheet(sheetName);
		Row currentRow = currentSheet.getRow(rowNum);
		Cell currentCell = currentRow.getCell(colNum, MissingCellPolicy.RETURN_BLANK_AS_NULL);
		if (currentCell == null) {
			currentCell = currentRow.createCell(colNum);
		}
		currentCell.setCellValue(value);
		try {
			FileOutputStream fos = new FileOutputStream(excelPathName);
			excelWorkBook.write(fos);
			fos.flush();
			fos.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setExecuteStatusInTestCaseListSheet(String testCaseName, String value){
//		Sheet currentSheet = getSheet(sheetName);
		String sheetName = ProjectConstants.testCaseListSheet;
		String columnName = ProjectConstants.executeResultColumn;
		int rowIndex = this.getSingleRowIndexByTestCaseName(sheetName, testCaseName);
		int colIndex = this.getColumnIndex(sheetName, columnName);
		this.setCellData(sheetName, rowIndex, colIndex, value);
	}
	public void setExecuteStatusInTestDataSheet(String RowIndex,String Value){
		String sheetName = ProjectConstants.testDataSheet;
		String columnName = ProjectConstants.executeResultColumn;
		int colIndex = this.getColumnIndex(sheetName, columnName);
		this.setCellData(sheetName, Integer.parseInt(RowIndex), colIndex, Value);
	}
	public void SaveAndClose() {
		try {
			FileOutputStream fos = new FileOutputStream(excelPathName);
			excelWorkBook.write(fos);
			fos.flush();
			fos.close();
			excelWorkBook.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getTestCaseExecuteFlag(String sheetName, String testCaseName) {
		Sheet currentSheet = this.getSheet(sheetName);
		int rowIndex = this.getSingleRowIndexByTestCaseName(sheetName, testCaseName);
		int colIndex = this.getColumnIndex(sheetName, ProjectConstants.executeOrNotColumn);
		Row row = currentSheet.getRow(rowIndex);
		Cell cell = row.getCell(colIndex);
		return getCellValue(cell);

	}

	public String getCellValue(Cell cell) {
		if (cell.getCellTypeEnum() == CellType.NUMERIC) {
			return String.valueOf(cell.getNumericCellValue());
		} else {
			return cell.getStringCellValue();
		}
	}

	public int getSingleRowIndexByTestCaseName(String sheetName, String testCaseName) {
		Sheet currentSheet = this.getSheet(ProjectConstants.testCaseListSheet);
		int firstRowNum = currentSheet.getFirstRowNum();
		int lastRowNum = currentSheet.getLastRowNum();
		int colIndex = getColumnIndex(ProjectConstants.testCaseListSheet, ProjectConstants.testCaseNameColumn);
		Cell cell;
		String cellValue;
		for (int i = firstRowNum; i <= lastRowNum; i++) {
			cell = currentSheet.getRow(i).getCell(colIndex);
			cellValue = getCellValue(cell);
			if (cellValue == testCaseName) {
				return i;
			}
		}
		return -1;
	}

	public List getAllRowIndexByTestCaseName(String sheetName, String testCaseName){
		Sheet currentSheet = this.getSheet(ProjectConstants.testDataSheet);
		int firstRowNum = currentSheet.getFirstRowNum();
		int lastRowNum = currentSheet.getLastRowNum();
		int colIndex = getColumnIndex(ProjectConstants.testCaseListSheet, ProjectConstants.testCaseNameColumn);
		Cell cell;
		String cellValue;
		List rowIndexArr = new ArrayList();
		for (int i = firstRowNum; i <= lastRowNum; i++) {
			cell = currentSheet.getRow(i).getCell(colIndex);
			cellValue = getCellValue(cell);
			if (cellValue == testCaseName) {
				rowIndexArr.add(i);
			}
		}
		return rowIndexArr;
	}
	public int getColumnIndex(String sheetName, String columnName) {
		Sheet currentSheet = this.getSheet(sheetName);
		Row row = currentSheet.getRow(0);
		int lastColumnIndex = row.getLastCellNum();
		Cell cell;
		String cellValue;
		for (int i = 0; i <= lastColumnIndex; i++) {
			cell = row.getCell(i);
			cellValue = getCellValue(cell);
			if (cellValue.toLowerCase().trim() == columnName) {
				return i;
			}
		}
		return -1;
	}
	
	public Iterator<Object[]> getAllTestData(String testCaseName){
		String sheetName=ProjectConstants.testDataSheet;
		Sheet currentSheet = this.getSheet(sheetName);
		List rowIndexList = this.getAllRowIndexByTestCaseName(sheetName, testCaseName);
		List<Object[]> testDataList = new ArrayList<Object[]>();
		int executeFlagColumnIndex =this.getColumnIndex(sheetName, ProjectConstants.executeOrNotColumn);
		int lastColumnIndex = currentSheet.getRow(0).getLastCellNum();
		Object field[] = new String[lastColumnIndex+1-3+1];//最后一列+1 （总列数）-3（非测试数据列）+(用于存放rowIndex)
		for(int i = 0; i<rowIndexList.size();i++){
			int executeCount=0;
			int rowIndex = (int)rowIndexList.get(i);
			String executeFlag = this.getCellData(sheetName, rowIndex, executeFlagColumnIndex);
			if(executeFlag.toLowerCase().trim()=="y"){
				field[0]=String.valueOf(rowIndex);
				for(int j =3;j<=lastColumnIndex-1;j++){//test data column is from column index 3.
				field[j-2]=this.getCellData(sheetName, rowIndex, j);
				}
				testDataList.add(field);
			}
			
		}
		return testDataList.iterator();
	}
}
