package com.nancy.util;

import org.databene.feed4testng.FeedTest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;

import com.nancy.constants.ProjectConstants;

public class DriverBase extends FeedTest{
	public WebDriver driver;

	public WebDriver getDriver() {
		return this.driver;
	}

	public void setDriver(String driverType) {
		switch (driverType.toLowerCase()) {
		case "chrome":
			System.setProperty("webdriver.chrome.driver", ProjectConstants.ChromeDrivePath);
			driver = new ChromeDriver();
			break;
		case "firefox":
			System.setProperty("webdriver.firefox.bin", ProjectConstants.FireFoxPath);

			System.setProperty("webdriver.firefox.marionette", ProjectConstants.FireFoxGeckoDriverPath);
//			ProfilesIni pi = new ProfilesIni ();
//			FirefoxProfile fpf = pi.getProfile("default");
//			fpf.setPreference("browser.sessionstore.enabled", false);
//			driver = new FirefoxDriver(fpf);
			driver = new FirefoxDriver();
			driver.manage().window().maximize();
//			driver.manage().deleteAllCookies();
			break;
		}

	}

}
