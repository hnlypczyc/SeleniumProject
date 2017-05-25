package com.nancy.commonfunction;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.nancy.constants.ProjectConstants;
import com.nancy.util.LocatorUtil;
import com.nancy.util.Log;

public class CommonFunction {
	
	private LocatorUtil locatorUtil;
	public CommonFunction(){
		this.locatorUtil= new LocatorUtil(ProjectConstants.ObjectMapPath);
	}
	
	public WebElement findElement(WebDriver wd, String locator){
		return wd.findElement(locatorUtil.getLocator(locator));
	}
	
	public void sendKeys(WebDriver wd, String locator, String Value){
		findElement(wd, locator).sendKeys(Value);
	}
	
	public void Click(WebDriver wd, String locator){
		findElement(wd,locator).click();
	}

	public String getText(WebDriver wd, String locator){
		return findElement(wd,locator).getText();
	}
	
	public boolean ContainText(String AllString, String beIncluded){

			if(AllString.contains(beIncluded)){
				return true;
			}
			else{
				return false;
				}
		
	}
	
	public void selectByVisibleText(WebDriver wd, String locator, String selectedValue){
		Select select= new Select(findElement(wd,locator));
		select.selectByVisibleText(selectedValue);
	}
	
	public String getAttributeValue(WebDriver wd, String locator,String attribute){
		return findElement(wd, locator).getAttribute(attribute);
	}
}