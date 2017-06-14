package com.nancy.util;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.nancy.constants.ProjectConstants;

public class DriverBase {
	public WebDriver driver;
	
	public WebDriver getDriver(){
		return this.driver;
	}
	
	public void setDriver(String driverType){
		switch (driverType.toLowerCase()){
		case "chrome":
			System.setProperty("webdriver.chrome.driver", ProjectConstants.ChromeDrivePath);
			driver = new ChromeDriver();
			break;
		case "firefox":
			System.setProperty("webdriver.firefox.marionette",ProjectConstants.FireFoxGeckoDriverPath);
			 driver=new FirefoxDriver();
			break;
		}
		
		
	}

}
