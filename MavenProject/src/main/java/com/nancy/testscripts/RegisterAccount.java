package com.nancy.testscripts;

import org.testng.annotations.Test;

import com.nancy.constants.ProjectConstants;
import com.nancy.util.LocatorUtil;
import com.nancy.util.Log;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;

public class RegisterAccount {
	private WebDriver wd;
	private WebElement webElement;

	@DataProvider(name = "NewEmailAddress")
	public Object[][] getNewEmailAddress() {
		return new Object[][] { { "testselenium28@gmail.com" } };
	}

	@Test(dataProvider = "NewEmailAddress")
	public void RegisterAccount(String EmailAddress) {
		Log.StartTestCase(this.getClass().getName());
		// 1.点击Sign in
		wd.findElement(LocatorUtil.getLocator("HomePage.Signin")).click();

		// 2. 输入email；
		wd.findElement(LocatorUtil.getLocator("CreateAccountPage.EmailAddress.Edit")).sendKeys(EmailAddress);
		// 3. 点击Create an Account
		wd.findElement(LocatorUtil.getLocator("CreateAccountPage.CreateAnAccount.Button")).click();
		wd.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		// 输入注册信息
		wd.findElement(LocatorUtil.getLocator("CreateAccountPage.Title.Mrs.Radio")).click();
		wd.findElement(LocatorUtil.getLocator("CreateAccountPage.FirstName.Edit")).sendKeys("Nancy");
		wd.findElement(LocatorUtil.getLocator("CreateAccountPage.LastName.Edit")).sendKeys("Zhou");
		wd.findElement(LocatorUtil.getLocator("CreateAccountPage.Email.Edit")).clear();
		wd.findElement(LocatorUtil.getLocator("CreateAccountPage.Email.Edit")).sendKeys(EmailAddress);
		wd.findElement(LocatorUtil.getLocator("CreateAccountPage.Password.Edit")).sendKeys("123456");
		Select select = new Select(wd.findElement(LocatorUtil.getLocator("CreateAccountPage.DateOfBirth.date.Select")));
		select.selectByValue("10");
		select = new Select(wd.findElement(LocatorUtil.getLocator("CreateAccountPage.DateOfBirth.month.Select")));
		select.selectByValue("1");
		select = new Select(wd.findElement(LocatorUtil.getLocator("CreateAccountPage.DateOfBirth.year.Select")));
		select.selectByValue("1986");
		wd.findElement(LocatorUtil.getLocator("CreateAccountPage.SignUpForOurNewsletter.Checkbox")).click();
		wd.findElement(LocatorUtil.getLocator("CreateAccountPage.YourAddress.FirstName.Edit")).sendKeys("FirstName");
		wd.findElement(LocatorUtil.getLocator("CreateAccountPage.YourAddress.LastName.Edit")).sendKeys("LastName");
		wd.findElement(LocatorUtil.getLocator("CreateAccountPage.YourAddress.Company.Edit")).sendKeys("DXC");
		wd.findElement(LocatorUtil.getLocator("CreateAccountPage.YourAddress.Address.Edit")).sendKeys("Address");
		wd.findElement(LocatorUtil.getLocator("CreateAccountPage.YourAddress.AddressLine2.Edit"))
				.sendKeys("AddressLine2");
		wd.findElement(LocatorUtil.getLocator("CreateAccountPage.YourAddress.City.Edit")).sendKeys("Shanghai");
		select = new Select(wd.findElement(LocatorUtil.getLocator("CreateAccountPage.YourAddress.State.Select")));
		select.selectByIndex(1);
		wd.findElement(LocatorUtil.getLocator("CreateAccountPage.YourAddress.ZipCode.Edit")).sendKeys("23012");
		select = new Select(wd.findElement(LocatorUtil.getLocator("CreateAccountPage.YourAddress.Country.Select")));
		select.selectByIndex(1);
		wd.findElement(LocatorUtil.getLocator("CreateAccountPage.YourAddress.MobilePhone.Edit"))
				.sendKeys("13876543456");
		wd.findElement(LocatorUtil.getLocator("CreateAccountPage.YourAddress.AssignAnAddressAlias.Edit"))
				.sendKeys("AddressAlias");
		wd.findElement(LocatorUtil.getLocator("CreateAccountPage.Register.Button")).click();

		// 点击signout链接登出，用注册的账号重新登录
		wd.findElement(LocatorUtil.getLocator("MyAccountPage.SignOut.Link")).click();
		wd.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
		wd.findElement(LocatorUtil.getLocator("LoginPage.EmailAddress.Edit")).sendKeys(EmailAddress);
		wd.findElement(LocatorUtil.getLocator("LoginPage.Password.Edit")).sendKeys("123456");
		wd.findElement(LocatorUtil.getLocator("LoginPage.SignIn.Button")).click();
		String loginAccount = wd.findElement(LocatorUtil.getLocator("MyAccountPage.AccountName.Link")).getText();
		try {
			Assert.assertEquals(loginAccount, "Nancy Zhou", "比较实际登录名和期望登录名");
		} catch (AssertionError error) {
			Log.error("比较实际登录名和期望登录名失败!");
			Log.EndTestCase(this.getClass().getName());
			// error.printStackTrace();
			Assert.fail("实际登录名与期望登录名不一致，实际登录名是：" + loginAccount + ",期望登录名是111Nancy Zhou111");
		}
		Log.EndTestCase(this.getClass().getName());

	}

	@BeforeClass
	public void beforeTest() {
		System.setProperty("webdriver.chrome.driver", ProjectConstants.ChromeDrivePath);
		// LocatorUtil = new LocatorUtil(ProjectConstants.ObjectMapPath);
		wd = new ChromeDriver();
		wd.get(ProjectConstants.BaseUrl);
	}

	@AfterClass
	public void afterTest() {
		wd.findElement(LocatorUtil.getLocator("MyAccountPage.SignOut.Link")).click();
		wd.close();
		wd.quit();

	}

}
