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

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import com.nancy.constants.ProjectConstants;
import com.nancy.util.AssertType;
import com.nancy.util.LocatorUtil;
import com.nancy.util.Log;


public class CommonFunction {
	

	
	public static boolean IsElementExist(WebDriver wd, String locator){
		try {
			findElement(wd, locator);
			return true;
		} catch (NoSuchElementException exception) {
			return false;
		}
	}
//	public void UpdateObjectMapValue(String parameterName, String runTimeValue){
//		FileInputStream fis;
//		FileOutputStream fos;
//		BufferedReader br;
//		BufferedWriter bw;
		
//		try {
//			 br = new BufferedReader(new InputStreamReader(new FileInputStream(ProjectConstants.ObjectMapPath),"UTF-8"));
//			 String line = br.readLine();
//			 bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(ProjectConstants.ObjectMapPath),"UTF-8"));
//
//			 while(!(line==null)){
//				 if((!(line.startsWith("#")))&& line.contains(parameterName)){
//					 String newLine = line.replace(parameterName, runTimeValue);
////					 System.out.println(newLine);
//
//					 bw.write(newLine);
//					 bw.flush();
//				 }else{
////					 System.out.println(line);
//					 bw.write(line);
//					 bw.flush();
//					 
//				 }
//				 bw.newLine();
//				 line = br.readLine();
//
//			 }
//			 br.close();
//			 bw.flush();
//			 bw.close();
//			 
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//	}
	public static WebElement findElement(WebDriver wd, String locator){
		return wd.findElement(LocatorUtil.getLocator(locator));
	}
	
	public static void sendKeys(WebDriver wd, String locator, String Value){
		findElement(wd, locator).sendKeys(Value);
	}
	
	public static void Click(WebDriver wd, String locator){
		findElement(wd,locator).click();
	}

	public static String getText(WebDriver wd, String locator){
		return findElement(wd,locator).getText();
	}
	
	public static boolean ContainText(String AllString, String beIncluded){

			if(AllString.contains(beIncluded)){
				return true;
			}
			else{
				return false;
				}
		
	}
	
	public static void selectByVisibleText(WebDriver wd, String locator, String selectedValue){
		Select select= new Select(findElement(wd,locator));
		select.selectByVisibleText(selectedValue);
	}
	
	public static String getAttributeValue(WebDriver wd, String locator,String attribute){
		return findElement(wd, locator).getAttribute(attribute);
	}
	
	public static void Clear(WebDriver wd, String locator){
		findElement(wd, locator).clear();
	}
	
	public static void CaptureScreenShot(WebDriver wd, String filePath){
		File newFile =  ((TakesScreenshot)wd).getScreenshotAs(OutputType.FILE);
//		FileUtils.copyFile(newFile, );
	}
	public static void createReport(String TestCaseName, AssertType assertType, Object ActualResult, Object ExpectedResult,String message){
//		AssertTrue, AssertFalse,AssertSame,AssertNotSame,AssertNull,AssertNotNull,AssertEquals,AssertEqualsNoOrder,AssertNotEquals

		switch (assertType){
		case AssertEquals:
			try{
				Assert.assertEquals(ActualResult, ExpectedResult);
				Log.info(message);
			}catch(AssertionError error){
				Log.error(message);
				Log.EndTestCase(TestCaseName);
				Assert.fail(message);
			}
			break;
		case AssertTrue:
			try{
				Assert.assertTrue(true);
				Log.info(message);
			}catch(AssertionError error){
				Log.error(message);
				Log.EndTestCase(TestCaseName);
				Assert.fail(message);
			}
			break;
		}
		
	}
}