package com.nancy.util;

import org.apache.log4j.Logger;

public class Log {
	private static Logger log = Logger.getLogger(Log.class.getName());

	public static void StartTestCase(String testCaseName) {
		log.info("--------Start running [" + testCaseName + "]------------");

	}

	public static void EndTestCase(String testCaseName) {
		log.info("--------End of [" + testCaseName + "]------------");
	}

	public static void info(String message) {
		log.info(message);
	}

	public static void warn(String message) {
		log.warn(message);
	}

	public static void error(String message) {
		log.error(message);
	}

	public static void debug(String message) {
		log.debug(message);
	}
}
