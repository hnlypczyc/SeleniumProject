package com.nancy.testscripts;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;
import org.databene.feed4testng.FeedTest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterTest;

public class NewTest extends FeedTest {
	
	private WebDriver wd;
  @Test
  public void f() {
	  wd.get("http://www.baidu.com");
  }
  @BeforeTest
  public void beforeTest() {
//	  System.setProperty("webdriver.firefox.marionette", "C:\\01Nancy\\01Nancy\\Selenium\\install\\geckodriver-v0.13.0-win32");
	  wd = new FirefoxDriver();
  }

  @AfterTest
  public void afterTest() {
	  
	 //test egefe
  }

}
