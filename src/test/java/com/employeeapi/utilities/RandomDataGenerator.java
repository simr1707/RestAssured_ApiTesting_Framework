package com.employeeapi.utilities;

import org.apache.commons.lang3.RandomStringUtils;

public class RandomDataGenerator {
	
	public static String empName() {
		String generatedString=RandomStringUtils.randomAlphabetic(1);
		return ("Simran"+generatedString);
	}
	
	public static String empJob() {
		String generatedString=RandomStringUtils.randomNumeric(1);
		return ("Job"+generatedString);
	}
	
}
