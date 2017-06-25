package com.nancy.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.By;


import com.nancy.constants.ProjectConstants;


public class LocatorUtil {
	
	private static Properties prop;
	
	static{
		prop = new Properties();
		try {
			prop.load(new FileInputStream(ProjectConstants.ObjectMapPath));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
		
	}
//	public LocatorUtil(String objectMapFile){
//		this.objectMapFile = objectMapFile;
//		try {
//			prop = new Properties();
////			prop.load(getClass().getClassLoader().getResourceAsStream(objectMapFile));
//			prop.load(new FileInputStream(objectMapFile));;
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		
//	}

	public static By getLocator(String objectName){
		String value = prop.getProperty(objectName);
		String[] arrValues = value.split(">");
		String strBy = arrValues[0];
		String strLocator = arrValues[1];
		if(strLocator.contains("$")){
			int startIndex = strLocator.indexOf("$");
			int endIndex = strLocator.indexOf("$", startIndex+1);
			Log.info("startIndex:"+startIndex);
			Log.info("endIndex:"+endIndex);
			String paramterName = strLocator.substring(startIndex, endIndex+1);
			String paramterValue = prop.getProperty(paramterName);
			Log.info("paramterName:"+paramterName);
			Log.info("paramterValue:"+paramterValue);
			strLocator = strLocator.replace(paramterName, paramterValue);
			Log.info("strLocator:"+strLocator);
		}
		
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
	
	public static void setParameterValue(String parameterName, String parameterValue){
		prop.setProperty(parameterName, parameterValue);
		try {
			prop.store(new FileOutputStream(ProjectConstants.ObjectMapPath), "update parameter:" +parameterName + "to "+parameterValue);
			prop.clear();
			prop.load(new FileInputStream(ProjectConstants.ObjectMapPath));

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			Log.error("Set the value of "+parameterName +" in ObjectMap.properties to "+parameterValue+ ", fail, FileNotFoundException.");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Log.error("Set the value of "+parameterName +" in ObjectMap.properties to "+parameterValue+ ", fail, IOException.");
			e.printStackTrace();
		}
		
		
	}
}
