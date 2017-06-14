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

import com.nancy.testscripts.AddressManagement;

public class TestNGListener extends TestListenerAdapter {

	@Override
	public void onTestFailure(ITestResult tr) {
		super.onTestFailure(tr);
		takeScreenshot(tr);
	}

	private void takeScreenshot(ITestResult tr) {
		Log.info("take screenshot");
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
		String newFileName = className + File.separator +testCaseName +"_"+  timeStamp ;
		Log.info("screenShotFolder:" + screenShotFolder.getAbsolutePath());
		Log.info("newFileName:" + newFileName);
		File NewFile = new File(screenShotFolder, newFileName + ".png");
		try {
			FileUtils.copyFile(screenFile, NewFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Reporter.log("<a href='"+NewFile.getAbsolutePath()+"'> ScreenShot of fail step"+"</a>");
}

}
