package com.nancy.commonfunction;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.Reporter;

import com.nancy.constants.ProjectConstants;
import com.nancy.util.AssertType;
import com.nancy.util.ExcelManager;
import com.nancy.util.LocatorUtil;
import com.nancy.util.Log;
import com.nancy.util.MyReporter;

public class CommonFunction {

	public static boolean IsElementExist(WebDriver wd, String locator) {
		try {
			findElement(wd, locator);
			return true;
		} catch (NoSuchElementException exception) {
			return false;
		}
	}

	public static WebElement findElement(WebDriver wd, String locator) {
		return wd.findElement(LocatorUtil.getLocator(locator));
	}
	public static List<WebElement> findElements(WebDriver wd, String objectName){
		return wd.findElements(LocatorUtil.getLocator(objectName));
	}
	public static void sendKeys(WebDriver wd, String locator, String Value) {
		findElement(wd, locator).sendKeys(Value);
	}

	public static void Click(WebDriver wd, String locator) {
		findElement(wd, locator).click();
	}

	public static String getText(WebDriver wd, String locator) {
		return findElement(wd, locator).getText();
	}

	public static boolean ContainText(String AllString, String beIncluded) {

		if (AllString.contains(beIncluded)) {
			return true;
		} else {
			return false;
		}

	}

	public static void selectByVisibleText(WebDriver wd, String locator, String selectedValue) {
		Select select = new Select(findElement(wd, locator));
		select.selectByVisibleText(selectedValue);
	}

	public static String getAttributeValue(WebDriver wd, String locator, String attribute) {
		return findElement(wd, locator).getAttribute(attribute);
	}

	public static void Clear(WebDriver wd, String locator) {
		findElement(wd, locator).clear();
	}
	
	/*
	 * Scroll down/up to make the webElement into view
	 */
	public static void ScrollIntoViewByWebElement(WebDriver wd, WebElement we){
	((JavascriptExecutor)wd).executeScript("arguments[0].scrollIntoView();", we);

	}

	public static void ScrollIntoViewByLocatorString(WebDriver wd, String locator){
	((JavascriptExecutor)wd).executeScript("arguments[0].scrollIntoView();",findElement(wd,locator));

	}
	public static void createReport(String testCaseName, String expectedResult, String actualResult,
			String checkPoint) {
		try {
			Assert.assertEquals(expectedResult, actualResult);
			Log.info(checkPoint + ", Pass！");
			MyReporter.log(checkPoint + ", Pass！");
		} catch (AssertionError error) {
			Log.error(String.format(checkPoint + ", Fail! the expected Result is %s, while the actual result is %s",
					expectedResult, actualResult));
			MyReporter.error(
					String.format(checkPoint + ", Fail! the expected Result is %s, while the actual result is %s",
							expectedResult, actualResult));
			ExcelManager em = new ExcelManager(ProjectConstants.testSuiteExcelPath);
			em.setExecuteStatusInTestCaseListSheet(testCaseName, "Fail");
//			em.setExecuteStatusInTestDataSheet(excelRowIndex, "Fail");
			MyReporter.EndTestCase(testCaseName);
			
			Assert.fail(String.format(checkPoint + ", Fail! the expected Result is %s, while the actual result is %s",
					expectedResult, actualResult));

		}
	}
	
	public static String getTestCaseNameByClassName(String className){
		int lastDotIndex = className.lastIndexOf(".");
		return className.substring(lastDotIndex+1,className.length());
	}
}