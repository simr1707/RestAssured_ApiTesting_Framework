package com.employeeapi.base;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.testng.annotations.BeforeClass;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class TestBase {
	public static RequestSpecification httpRequest;
	public static Response response;
	public String empID="7"; //to get detail of single employee or to update employee
	
	public Logger logger; 
	
	@BeforeClass
	public void setup() {
		logger =Logger.getLogger("Employeeapi");//added logger   and "Employeeapi" is just the name of the logger
		PropertyConfigurator.configure("log4j.properties");
		logger.setLevel(Level.DEBUG);
		
		//set base uri
		RestAssured.baseURI= "https://reqres.in/api";
		
		//create request object
		httpRequest=RestAssured.given();
	}
	
}
