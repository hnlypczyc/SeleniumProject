package com.nancy.testscripts;

import org.testng.annotations.Test;

import com.nancy.actions.LoginAction;
import com.nancy.commonfunction.CommonFunction;
import com.nancy.constants.ProjectConstants;
import com.nancy.util.DriverBase;
import com.nancy.util.ExcelManager;
import com.nancy.util.LocatorUtil;
import com.nancy.util.Log;
import com.nancy.util.MyReporter;
import com.nancy.util.TestNGListener;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.databene.benerator.anno.Source;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;

//@Listeners({ TestNGListener.class }) 
public class AddressManagement extends DriverBase {

	private String className;
	private boolean ExecuteFlag;
	private ExcelManager em;
	private String testCaseName;

//	@DataProvider(name = "AddressMangement")
////	public Object[][] GetAddressName() {
////
////		return new Object[][] { { "HomeAddress" ,"HomeAddressUpdate"} ,{ "OfficeAddress" ,"OfficeAddressUpdate"}};
////	}
	
	@DataProvider(name = "AddressMangement")
	public Iterator<Object[]> getTestData(){
		System.out.println("getTestData:");
		return em.getAllTestData(testCaseName);
	}

	@DataProvider(name = "AddressNameUpdate")
	public Object[][] GetUpdateAddressName() {

		return new Object[][] { { "HomeAddressUpdate" } };
	}

	@Test(dataProvider = "AddressMangement")
	// @Test(dataProvider = "feeder")
	// @Source(".\\src\\test\\resources\\excelFolder\\TestCase.csv")

	public void AddressManagement(String rowIndex,String strAddressName, String strAddressNameUpdate) throws InterruptedException {
		AddNewAddress(strAddressName);
		UpdateNewAddress(strAddressNameUpdate);
		deleteAddress(strAddressNameUpdate);
		CommonFunction.Click(driver, "MyAddress.BackToYourAcount.Button");
	}

	public void AddNewAddress(String strAddressName) {
		MyReporter.StartTestCase("Add New Address: " + strAddressName);
		CommonFunction.Click(driver, "MyAccountPage.MyAddress.Button");
		CommonFunction.Click(driver, "MyAddress.AddANewAddress.Button");
		CommonFunction.Clear(driver, "MyAddress.AddNewAddress.FirstName.Edit");
		CommonFunction.sendKeys(driver, "MyAddress.AddNewAddress.FirstName.Edit", "Zhou");
		CommonFunction.Clear(driver, "MyAddress.AddNewAddress.LastName.Edit");
		CommonFunction.sendKeys(driver, "MyAddress.AddNewAddress.LastName.Edit", "YingChun");
		CommonFunction.Clear(driver, "MyAddress.AddNewAddress.Company.Edit");
		CommonFunction.sendKeys(driver, "MyAddress.AddNewAddress.Company.Edit", "Company1");
		CommonFunction.sendKeys(driver, "MyAddress.AddNewAddress.Address.Edit", "Address1");
		CommonFunction.sendKeys(driver, "MyAddress.AddNewAddresss.Address2.Edit", "Address2");
		CommonFunction.sendKeys(driver, "MyAddress.AddNewAddress.City.Edit", "City1");
		CommonFunction.selectByVisibleText(driver, "MyAddress.AddNewAddress.State.Select", "Arizona");
		CommonFunction.sendKeys(driver, "MyAddress.AddNewAddress.ZipCode.Edit", "23456");
		CommonFunction.selectByVisibleText(driver, "MyAddress.AddNewAddress.Country.Select", "United States");
		CommonFunction.sendKeys(driver, "MyAddress.AddNewAddress.HomePhone.Edit", "12345678909");
		CommonFunction.sendKeys(driver, "MyAddress.AddNewAddress.AdditionalInformation.Edit", "additional infomation");
		CommonFunction.Clear(driver, "MyAddress.AddNewAddress.AddressTitle.Edit");
		CommonFunction.sendKeys(driver, "MyAddress.AddNewAddress.AddressTitle.Edit", strAddressName);
		CommonFunction.Click(driver, "MyAddress.AddNewAddress.Save.Button");
		LocatorUtil.setParameterValue("$AddressName$", strAddressName);

		boolean isExist = CommonFunction.IsElementExist(driver, "MyAddress.AddressList.AddressTitle.Text");
		CommonFunction.createReport(className, "true", String.valueOf(isExist), "验证新加的Address显示在address list里面");

		MyReporter.EndTestCase("Add New Address: " + strAddressName);
	}

	// @Test(dataProvider = "AddressNameUpdate")
	public void UpdateNewAddress(String strAddressNameUpdate) {
		MyReporter.StartTestCase("Update Address to " + strAddressNameUpdate);
		CommonFunction.Click(driver, "MyAddress.AddressList.Update.Button");
		driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
		CommonFunction.Clear(driver, "MyAddress.AddNewAddress.FirstName.Edit");
		CommonFunction.sendKeys(driver, "MyAddress.AddNewAddress.FirstName.Edit", "ZhouUpdate");
		CommonFunction.Clear(driver, "MyAddress.AddNewAddress.LastName.Edit");
		CommonFunction.sendKeys(driver, "MyAddress.AddNewAddress.LastName.Edit", "YingChunUpdate");
		CommonFunction.Clear(driver, "MyAddress.AddNewAddress.Company.Edit");
		CommonFunction.sendKeys(driver, "MyAddress.AddNewAddress.Company.Edit", "Company1Update");
		CommonFunction.sendKeys(driver, "MyAddress.AddNewAddress.Address.Edit", "Address1Update");
		CommonFunction.sendKeys(driver, "MyAddress.AddNewAddresss.Address2.Edit", "Address2Update");
		CommonFunction.sendKeys(driver, "MyAddress.AddNewAddress.City.Edit", "City1Update");
		CommonFunction.selectByVisibleText(driver, "MyAddress.AddNewAddress.State.Select", "Florida");
		CommonFunction.Clear(driver, "MyAddress.AddNewAddress.ZipCode.Edit");
		CommonFunction.sendKeys(driver, "MyAddress.AddNewAddress.ZipCode.Edit", "65432");
		CommonFunction.selectByVisibleText(driver, "MyAddress.AddNewAddress.Country.Select", "United States");
		CommonFunction.Clear(driver, "MyAddress.AddNewAddress.HomePhone.Edit");
		CommonFunction.sendKeys(driver, "MyAddress.AddNewAddress.HomePhone.Edit", "9876543210");
		CommonFunction.Clear(driver, "MyAddress.AddNewAddress.AdditionalInformation.Edit");
		CommonFunction.sendKeys(driver, "MyAddress.AddNewAddress.AdditionalInformation.Edit",
				"additional infomation Update");
		CommonFunction.Clear(driver, "MyAddress.AddNewAddress.AddressTitle.Edit");
		CommonFunction.sendKeys(driver, "MyAddress.AddNewAddress.AddressTitle.Edit", strAddressNameUpdate);
		CommonFunction.Click(driver, "MyAddress.AddNewAddress.Save.Button");
		LocatorUtil.setParameterValue("$AddressName$", strAddressNameUpdate);
		boolean flag = CommonFunction.IsElementExist(driver, "MyAddress.AddressList.AddressTitle.Text");

		CommonFunction.createReport(className, "true", String.valueOf(flag), "验证update之后的Address 显示在address list里面");
		MyReporter.EndTestCase("Update Address to " + strAddressNameUpdate);
	}

	// @Test(dataProvider = "AddressNameUpdate")
	public void deleteAddress(String strAddressNameDelete) throws InterruptedException {
		MyReporter.StartTestCase("Delete Address " + strAddressNameDelete);
		LocatorUtil.setParameterValue("$AddressName$", strAddressNameDelete);
		CommonFunction.Click(driver, "MyAddress.AddressList.Delete.Button");
		driver.switchTo().alert().accept();
		Thread.sleep(5000L);
		boolean isDeleted = CommonFunction.IsElementExist(driver, "MyAddress.AddressList.AddressTitle.Text");

		CommonFunction.createReport(className, "false", String.valueOf(isDeleted),
				"删除Address:" + strAddressNameDelete + ", 验证删除后的address没有显示在页面上");
		MyReporter.EndTestCase("Delete Address " + strAddressNameDelete);
	}

	@Parameters("browser")
	@BeforeTest
	public void beforeTest(String browser) {
		// 1. 获取当前TestCaseName
		// 2. 判断当前Test是否需要执行
		// 3. 如果执行，初始化Driver,打开URl，登录操作，以及其他需要调测试方法执行需要进行的准备。
		// 如果不执行，则跳过当前Test。
		className = this.getClass().getSimpleName();
		testCaseName = CommonFunction.getTestCaseNameByClassName(className);
		em = new ExcelManager(ProjectConstants.testSuiteExcelPath);
		System.out.println("testCaseName:"+testCaseName);
//		String executeFlag = em.getTestCaseExecuteFlag("", testCaseName);
//		if (executeFlag == "y") {
//			ExecuteFlag = true;
			this.setDriver(browser);
			this.driver.get(ProjectConstants.BaseUrl);
			driver.manage().timeouts().pageLoadTimeout(40, TimeUnit.SECONDS);
			LoginAction loginAction = new LoginAction(driver, ProjectConstants.LoginAccount,
					ProjectConstants.LoginPassword);
			loginAction.doLogin();
//		} else {
//			ExecuteFlag = false;
//		}

	}

	@AfterTest
	public void afterTest() {
		// driver.close();
		// driver.quit();
	}

}
