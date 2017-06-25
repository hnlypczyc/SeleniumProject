package com.nancy.testscripts;

import org.testng.annotations.Test;

import com.nancy.commonfunction.CommonFunction;
import com.nancy.constants.ProjectConstants;
import com.nancy.util.DriverBase;
import com.nancy.util.LocatorUtil;
import com.nancy.util.MyReporter;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;

public class SearchFromMenuCategory extends DriverBase{


	@Test
	public void SearchFromMenuCategory() throws Exception {
		String testCaseName= this.getClass().getName();
		MyReporter.StartTestCase(testCaseName);
		Actions action = new Actions(driver);
		String displayedText = null;
		WebElement searchResult = null;
		WebDriverWait driverw = new WebDriverWait(driver, 30);
		driverw.until(ExpectedConditions.presenceOfElementLocated(LocatorUtil.getLocator("HomePage.DRESSES.Menu")));
		// 方法1.将两个操作同时放到action里面一起perform
		WebElement menu = driver.findElement(LocatorUtil.getLocator("HomePage.DRESSES.Menu"));
		System.out.println("menu: " + menu.getAttribute("title"));
		action.moveToElement(menu).perform();
		Thread.sleep(1000L);
		WebElement subMenu1 = driver.findElement(LocatorUtil.getLocator("HomePage.DRESSES.EveningDresses.SubMenu"));
		System.out.println("subMenu1: " + subMenu1.getAttribute("title"));
		action.moveToElement(subMenu1);
		action.click();
		action.perform();
		displayedText = CommonFunction.findElement(driver, "HomePage.SearchResultByDRESSESEVENINGDRESSES").getText();		
		CommonFunction.createReport(testCaseName, "EVENING DRESSES", displayedText.trim(), "根据DRESSES->Evening Dresses category search后的结果验证");
		// 方法2.先用action perform move to parent menu,再perform move to sub menu,并且
		// perform click
		driver.manage().timeouts().pageLoadTimeout(100, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		driverw.until(ExpectedConditions.presenceOfElementLocated(LocatorUtil.getLocator("HomePage.WOMEN.Menu2")));

		Actions action2 = new Actions(driver);
		action2.moveToElement(driver.findElement(LocatorUtil.getLocator("HomePage.WOMEN.Menu2"))).perform();
		Thread.sleep(1000L);

		WebElement subMenu = driver.findElement(LocatorUtil.getLocator("HomePage.WOMEN.subMenu"));
		// System.out.println("subMenu2:"+subMenu.getAttribute("title"));
		action2.moveToElement(subMenu);
		action2.click();
		action2.perform();
		driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
		searchResult = driver.findElement(LocatorUtil.getLocator("HomePage.SearchResultByWOMENBLOUSE"));
		boolean isSearched = searchResult.isDisplayed();
			if (isSearched) {
				displayedText = searchResult.getText();
			}
		CommonFunction.createReport(testCaseName, "BLOUSES", displayedText.trim(), "根据WOMEN->BLOUSE category search后的结果验证");
		MyReporter.EndTestCase(this.getClass().getName());

	}
	@Parameters("browser")
	@BeforeClass
	public void BeforeTest(String browser) {

		this.setDriver(browser);
		driver.get(ProjectConstants.BaseUrl);
		driver.manage().timeouts().pageLoadTimeout(100, TimeUnit.SECONDS);
	}

	@AfterClass
	public void AfterTest() {
		driver.close();
	}
}
