package com.nancy.util;

import org.testng.Reporter;

public class MyReporter extends Reporter {

	public static void StartTestCase(String testCaseName) {
		Reporter.log(String.format("-----Start test case: %s ------<br>", testCaseName));
	}

	public static void EndTestCase(String testCaseName) {
		Reporter.log(String.format("-----End test case: %s ------<br>", testCaseName));
	}

	public static void log(String message) {
		Reporter.log(message + "<br>");
	}

	public static void error(String message) {
		Reporter.log("<p style=\"color:red\">" + message + "</p><br>");
	}
}
