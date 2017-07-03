package com.nancy.util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.TestListenerAdapter;

import com.nancy.constants.ProjectConstants;
import com.nancy.testscripts.AddressManagement;

public class TestNGListener extends TestListenerAdapter {

	@Override
	public void onTestFailure(ITestResult tr) {
		super.onTestFailure(tr);
		takeScreenshot(tr);
	}
	@Override
	public void onTestSuccess(ITestResult tr){
		super.onTestSuccess(tr);
		ExcelManager em = new ExcelManager(ProjectConstants.testSuiteExcelPath);
		String testCaseName = tr.getName();
		em.setExecuteStatusInTestCaseListSheet(testCaseName, "Pass");
	}
	private void takeScreenshot(ITestResult tr) {
		MyReporter.log("Start take screenshot");
		DriverBase db = (DriverBase) tr.getInstance();
		String className = tr.getInstanceName();
		WebDriver wd = db.getDriver();
		File screenFile = ((TakesScreenshot) wd).getScreenshotAs(OutputType.FILE);

		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
		String timeStamp = sdf.format(d);
		String testCaseName = tr.getName();

		File screenShotFolder = new File("ScreenShotFolder");
		if (!screenShotFolder.exists()) {
			screenShotFolder.mkdir();
		}
		String newFileName = className + File.separator + testCaseName + "_" + timeStamp;
		MyReporter.log("screenShotFolder:" + screenShotFolder.getAbsolutePath());
		MyReporter.log("screenShotFolderFileName:" + newFileName);
		File NewFile = new File(screenShotFolder, newFileName + ".png");
		try {
			FileUtils.copyFile(screenFile, NewFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		MyReporter.log("<a href=" + NewFile.getAbsolutePath()
				+ " target=_blank style=\"color:red\"> ScreenShot for fail step" + "</a><br>", true);
		MyReporter.EndTestCase(className);
	
	}

}
