package com.employeeapi.testCases;

import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.employeeapi.base.TestBase;
import com.employeeapi.utilities.RandomDataGenerator;

import io.restassured.http.Method;

public class TC004_Put_Employee_Record extends TestBase{
	
	String empName= RandomDataGenerator.empName();
	String empJob= RandomDataGenerator.empJob();
	
	@BeforeClass
	public void updateEmployee() throws InterruptedException{
		logger.info("***********************TC004_Put_Employee_Record***********************");

	    // Create data to send along with the post request
	    JSONObject requestParams = new JSONObject();
	    requestParams.put("name", empName);
	    requestParams.put("job", empJob);

	    // Add a header stating the request in json body
	    httpRequest.header("Content-Type", "application/json");

	    // Add json to body of the request
	    httpRequest.body(requestParams.toJSONString());

	    // Create response object
	    response = httpRequest.request(Method.POST, "/users/"+ empID);
	    Thread.sleep(1000);
	}

	 @Test
		public void checkResponseBody() {
			logger.info("***********************checking response body***********************");
			String responseBody= response.getBody().asString();
			logger.info("Response Body==>"+responseBody);
			Assert.assertEquals(responseBody.contains(empName), true);
		    Assert.assertEquals(responseBody.contains(empJob), true);
		}
		
		@Test
		public void checkStatusCode() {
			logger.info("***********************checking Status Code***********************");
			int statusCode= response.getStatusCode(); //getting status code
			logger.info("StatusCode is ==>"+statusCode);  
			Assert.assertEquals(statusCode,201);
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
			Assert.assertEquals(statusLine,"HTTP/1.1 201 Created");
	    }
		
		@Test
		public void checkContentType() {
			logger.info("***********************checking Content Type***********************");
			String contentType = response.header("Content-Type"); //getting Content Type
			logger.info("ContentType is ==>"+contentType);  
			Assert.assertEquals(contentType,"application/json; charset=utf-8");
	    }
		
		public void checkContentLength() {
			logger.info("***********************checking Content Length***********************");
			String contentLength = response.header("Content-Length"); //getting Content Type
			logger.info("Content Length is ==>"+contentLength);  
			Assert.assertTrue(Integer.parseInt(contentLength)<1500);
		}
		
		
		@AfterClass
		public void tearDown() {
			logger.info("***********************Finished TC004_Put_Employee_Record***********************");
			
		}
		
	
}		
