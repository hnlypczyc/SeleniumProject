package com.nancy.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class LocatorUtil {
	
	
	private Properties prop;
	
	public LocatorUtil(String objectMapFile){
		try {
			prop = new Properties();
			prop.load(getClass().getClassLoader().getResourceAsStream(objectMapFile));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	public By getLocator(String objectName){
		String value = prop.getProperty(objectName);
		String[] arrValues = value.split(">");
		String strBy = arrValues[0];
		String strLocator = arrValues[1];
		
		switch(strBy.toLowerCase()){
		case "xpath":
			return By.xpath(strLocator);
		case "id":
			return By.id(strLocator);
		case "name":
			return By.name(strLocator);
		case "classname":
			return By.className(strLocator);
		case "class":
			return By.className(strLocator);
		case "tagname":
		case "tag":
			return By.tagName(strLocator);
		case "linktext":
			return By.linkText(strLocator);
		case "link":
			return By.linkText(strLocator);
		case "partiallinktext":
			return By.partialLinkText(strLocator);
		case "cssselector":
			return By.cssSelector(strLocator);
		case "css":
			return By.cssSelector(strLocator);
		default :
			System.out.println("输入的定位类型未在程序中定义："+ strLocator);
			return null;
		}
	}
}
