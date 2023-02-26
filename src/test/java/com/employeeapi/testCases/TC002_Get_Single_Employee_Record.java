package com.employeeapi.testCases;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.employeeapi.base.TestBase;

import io.restassured.http.Method;

public class TC002_Get_Single_Employee_Record extends TestBase{
	
	@BeforeClass
	public void getEmployeeData() throws InterruptedException {
		logger.info("***********************Started TC002_Get_Single_Employee_Record***********************");
		
		//create response object
		response= httpRequest.request(Method.GET,"/users/"+empID);
		Thread.sleep(1000);
	}
	
	@Test
	public void checkResponseBody() {
		logger.info("***********************checking response body***********************");
		String responseBody= response.getBody().asString();
		logger.info("Response Body==>"+responseBody);
		Assert.assertEquals(responseBody.contains(empID),true);
	}
	
	@Test
	public void checkStatusCode() {
		logger.info("***********************checking Status Code***********************");
		int statusCode= response.getStatusCode(); //getting status code
		logger.info("StatusCode is ==>"+statusCode);  
		Assert.assertEquals(statusCode,200);
    }
	
	@Test
	public void checkResponseTime() {
		logger.info("***********************checking Response Time***********************");
		long responseTime= response.getTime(); //getting ResponseTime
		logger.info("Response Time is==>"+responseTime);  
		
		if(responseTime>4000) {
			logger.warn("Response time is greater than 4000");
		}
		Assert.assertTrue(responseTime<4000);
	}
	
	@Test
	public void checkStatusLine() {
		logger.info("***********************checking Status Line***********************");
		String statusLine= response.getStatusLine(); //getting status Line
		logger.info("StatusLine is ==>"+statusLine);  
		Assert.assertEquals(statusLine,"HTTP/1.1 200 OK");
    }
	
	@Test
	public void checkContentType() {
		logger.info("***********************checking Content Type***********************");
		String contentType = response.header("Content-Type"); //getting Content Type
		logger.info("ContentType is ==>"+contentType);  
		Assert.assertEquals(contentType,"application/json; charset=utf-8");
    }
	
	@Test
	public void checkContentEncoding() {
		logger.info("***********************checking Content Encoding***********************");
		String contentEncoding = response.header("Content-Encoding"); //getting Content Type
		logger.info("Content Encoding is ==>"+contentEncoding);  
		Assert.assertEquals(contentEncoding,"gzip");
    }
	
	//@Test
	public void checkContentLength() {
		logger.info("***********************checking Content Length***********************");
		String contentLength = response.header("Content-Length"); //getting Content Type
		logger.info("Content Length is ==>"+contentLength);  
		Assert.assertTrue(Integer.parseInt(contentLength)<1500);
	}
	
	@AfterClass
	public void tearDown() {
		logger.info("***********************Finished TC002_Get_Single_Employee_Record***********************");
		
	}
	
	
	
}
