package com.nancy.testscripts;

import org.testng.annotations.Test;

import com.nancy.commonfunction.CommonFunction;
import com.nancy.constants.ProjectConstants;
import com.nancy.util.DriverBase;
import com.nancy.util.LocatorUtil;
import com.nancy.util.MyReporter;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;

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

public class RegisterAccount extends DriverBase{
	private WebElement webElement;

	@DataProvider(name = "NewEmailAddress")
	public Object[][] getNewEmailAddress() {
		return new Object[][] { { "testselenium28@gmail.com" } };
	}

	@Test(dataProvider = "NewEmailAddress")
	public void RegisterAccount(String EmailAddress) {
		MyReporter.StartTestCase(this.getClass().getName());
		// 1.点击Sign in
		driver.findElement(LocatorUtil.getLocator("HomePage.Signin")).click();

		// 2. 输入email；
		driver.findElement(LocatorUtil.getLocator("CreateAccountPage.EmailAddress.Edit")).sendKeys(EmailAddress);
		// 3. 点击Create an Account
		driver.findElement(LocatorUtil.getLocator("CreateAccountPage.CreateAnAccount.Button")).click();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		// 输入注册信息
		driver.findElement(LocatorUtil.getLocator("CreateAccountPage.Title.Mrs.Radio")).click();
		driver.findElement(LocatorUtil.getLocator("CreateAccountPage.FirstName.Edit")).sendKeys("Nancy");
		driver.findElement(LocatorUtil.getLocator("CreateAccountPage.LastName.Edit")).sendKeys("Zhou");
		driver.findElement(LocatorUtil.getLocator("CreateAccountPage.Email.Edit")).clear();
		driver.findElement(LocatorUtil.getLocator("CreateAccountPage.Email.Edit")).sendKeys(EmailAddress);
		driver.findElement(LocatorUtil.getLocator("CreateAccountPage.Password.Edit")).sendKeys("123456");
		Select select = new Select(driver.findElement(LocatorUtil.getLocator("CreateAccountPage.DateOfBirth.date.Select")));
		select.selectByValue("10");
		select = new Select(driver.findElement(LocatorUtil.getLocator("CreateAccountPage.DateOfBirth.month.Select")));
		select.selectByValue("1");
		select = new Select(driver.findElement(LocatorUtil.getLocator("CreateAccountPage.DateOfBirth.year.Select")));
		select.selectByValue("1986");
		driver.findElement(LocatorUtil.getLocator("CreateAccountPage.SignUpForOurNewsletter.Checkbox")).click();
		driver.findElement(LocatorUtil.getLocator("CreateAccountPage.YourAddress.FirstName.Edit")).sendKeys("FirstName");
		driver.findElement(LocatorUtil.getLocator("CreateAccountPage.YourAddress.LastName.Edit")).sendKeys("LastName");
		driver.findElement(LocatorUtil.getLocator("CreateAccountPage.YourAddress.Company.Edit")).sendKeys("DXC");
		driver.findElement(LocatorUtil.getLocator("CreateAccountPage.YourAddress.Address.Edit")).sendKeys("Address");
		driver.findElement(LocatorUtil.getLocator("CreateAccountPage.YourAddress.AddressLine2.Edit"))
				.sendKeys("AddressLine2");
		driver.findElement(LocatorUtil.getLocator("CreateAccountPage.YourAddress.City.Edit")).sendKeys("Shanghai");
		select = new Select(driver.findElement(LocatorUtil.getLocator("CreateAccountPage.YourAddress.State.Select")));
		select.selectByIndex(1);
		driver.findElement(LocatorUtil.getLocator("CreateAccountPage.YourAddress.ZipCode.Edit")).sendKeys("23012");
		select = new Select(driver.findElement(LocatorUtil.getLocator("CreateAccountPage.YourAddress.Country.Select")));
		select.selectByIndex(1);
		driver.findElement(LocatorUtil.getLocator("CreateAccountPage.YourAddress.MobilePhone.Edit"))
				.sendKeys("13876543456");
		driver.findElement(LocatorUtil.getLocator("CreateAccountPage.YourAddress.AssignAnAddressAlias.Edit"))
				.sendKeys("AddressAlias");
		driver.findElement(LocatorUtil.getLocator("CreateAccountPage.Register.Button")).click();

		// 点击signout链接登出，用注册的账号重新登录
		driver.findElement(LocatorUtil.getLocator("MyAccountPage.SignOut.Link")).click();
		driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
		driver.findElement(LocatorUtil.getLocator("MyReporterinPage.EmailAddress.Edit")).sendKeys(EmailAddress);
		driver.findElement(LocatorUtil.getLocator("MyReporterinPage.Password.Edit")).sendKeys("123456");
		driver.findElement(LocatorUtil.getLocator("MyReporterinPage.SignIn.Button")).click();
		String MyReporterinAccount = driver.findElement(LocatorUtil.getLocator("MyAccountPage.AccountName.Link")).getText();
		CommonFunction.createReport(this.getClass().getName(), "Nancy Zhou", MyReporterinAccount, "比较实际登录名和期望登录名");
		MyReporter.EndTestCase(this.getClass().getName());

	}
	@Parameters("browser")
	@BeforeClass
	public void beforeTest(String browser) {
//		System.setProperty("webdriver.chrome.driver", ProjectConstants.ChromeDrivePath);
		// LocatorUtil = new LocatorUtil(ProjectConstants.ObjectMapPath);
		this.setDriver(browser);
		driver.get(ProjectConstants.BaseUrl);
	}

	@AfterClass
	public void afterTest() {
		driver.findElement(LocatorUtil.getLocator("MyAccountPage.SignOut.Link")).click();
		driver.close();
		driver.quit();

	}

}
