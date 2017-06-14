package com.nancy.testscripts;

import org.testng.annotations.Test;

import com.nancy.commonfunction.CommonFunction;
import com.nancy.constants.ProjectConstants;
import com.nancy.util.LocatorUtil;
import com.nancy.util.Log;

import org.testng.annotations.BeforeTest;

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

public class SearchFromMenuCategory {
	private WebDriver wd;
//	private LocatorUtil LocatorUtil;

	@Test
	public void SearchFromMenuCategory() throws Exception {
		Log.StartTestCase(this.getClass().getName());
		Actions action = new Actions(wd);
		String displayedText=null;
		WebElement searchResult =null;
//		wd.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
		WebDriverWait wdw = new WebDriverWait(wd,30);
		wdw.until(ExpectedConditions.presenceOfElementLocated(LocatorUtil.getLocator("HomePage.DRESSES.Menu")));
		// 方法1.将两个操作同时放到action里面一起perform
		WebElement menu = wd.findElement(LocatorUtil.getLocator("HomePage.DRESSES.Menu"));
		System.out.println("menu: "+menu.getAttribute("title"));
		action.moveToElement(menu).perform();
		Thread.sleep(1000L);
//		wd.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		WebElement subMenu1 =wd.findElement(LocatorUtil.getLocator("HomePage.DRESSES.EveningDresses.SubMenu"));
		System.out.println("subMenu1: "+subMenu1.getAttribute("title"));
		action.moveToElement(subMenu1);
		action.click();
		action.perform();
		
		try{
			displayedText = CommonFunction.findElement(wd, "HomePage.SearchResultByDRESSESEVENINGDRESSES").getText();
			Assert.assertEquals(displayedText.trim(), "EVENING DRESSES");
			Log.info("根据DRESSES->Evening Dresses category search后的结果验证，Pass");
		}catch(AssertionError error){
			Log.error("根据DRESSES->Evening Dresses category search后的结果验证，Fail!, the actual result is: "+displayedText);
			Log.EndTestCase(this.getClass().getName());
			Assert.fail("根据DRESSES->Evening Dresses category search后的结果验证");
		}
//		 方法2.先用action perform move to parent menu,再perform move to sub menu,并且
//		 perform click
		wd.manage().timeouts().pageLoadTimeout(100, TimeUnit.SECONDS);
		wd.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

	wdw.until(ExpectedConditions.presenceOfElementLocated(LocatorUtil.getLocator("HomePage.WOMEN.Menu2")));
		System.out.println("Menu2:" + wd.findElement(LocatorUtil.getLocator("HomePage.WOMEN.Menu2")).getAttribute("title"));
	
		Actions action2 = new Actions(wd);
		action2.moveToElement(wd.findElement(LocatorUtil.getLocator("HomePage.WOMEN.Menu2"))).perform();
		Thread.sleep(1000L);

		WebElement subMenu = wd.findElement(LocatorUtil.getLocator("HomePage.WOMEN.subMenu"));
//		System.out.println("subMenu2:"+subMenu.getAttribute("title"));
		action2.moveToElement(subMenu);
		action2.click();
		action2.perform();
		wd.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
		searchResult = wd.findElement(LocatorUtil.getLocator("HomePage.SearchResultByWOMENBLOUSE"));
		boolean isSearched = searchResult.isDisplayed();
		try{
			if(isSearched){
			    displayedText = searchResult.getText();
				String ExpectedText = "BLOUSES";
				Assert.assertEquals(displayedText.trim(),ExpectedText);
				Log.info("根据WOMEN->BLOUSE category search后的结果验证，Pass");

			}else{
			Log.info("根据WOMEN->BLOUSE category search后的结果验证，Fail," +isSearched);
			}
		}catch(AssertionError error){
			Log.info("根据WOMEN->BLOUSE category search后的结果验证，Fail, Displayed text in page is: " +displayedText);
			Log.EndTestCase(this.getClass().getName());
			Assert.fail("根据WOMEN->BLOUSE category search后的结果验证");
			
		}
		Log.EndTestCase(this.getClass().getName());

	}

	@BeforeClass
	public void BeforeTest() {
//		System.setProperty("webdriver.chrome.driver", ProjectConstants.ChromeDrivePath);
//		wd = new ChromeDriver();
		System.setProperty("webdriver.firefox.marionette",ProjectConstants.FireFoxGeckoDriverPath);
		wd=new FirefoxDriver();
		
		wd.get(ProjectConstants.BaseUrl);
		wd.manage().timeouts().pageLoadTimeout(100, TimeUnit.SECONDS);
	}

	@AfterClass
	public void AfterTest() {
		 wd.close();
	}
}
