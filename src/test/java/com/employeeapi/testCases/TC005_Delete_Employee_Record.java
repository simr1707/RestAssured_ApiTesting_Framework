package com.employeeapi.testCases;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.employeeapi.base.TestBase;

import io.restassured.http.Method;

public class TC005_Delete_Employee_Record extends TestBase {
	
	@BeforeClass
	public void deleteEmployee() throws InterruptedException {
		logger.info("***********************TC005_Delete_Employee_Record***********************");
		
		//create response object
		response= httpRequest.request(Method.DELETE,"/users"+empID);
		Thread.sleep(2000);
	}
	
	 @Test
		public void checkResponseBody() {
			logger.info("***********************checking response body***********************");
			String responseBody= response.getBody().asString();
			logger.info("Response Body==>"+responseBody);
			Assert.assertEquals(responseBody, "");
		}
		
		@Test
		public void checkStatusCode() {
			logger.info("***********************checking Status Code***********************");
			int statusCode= response.getStatusCode(); //getting status code
			logger.info("StatusCode is ==>"+statusCode);  
			Assert.assertEquals(statusCode,204);
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
			Assert.assertEquals(statusLine,"HTTP/1.1 204 No Content");
	    }
		
		public void checkContentLength() {
			logger.info("***********************checking Content Length***********************");
			String contentLength = response.header("Content-Length"); //getting Content Type
			logger.info("Content Length is ==>"+contentLength);  
			Assert.assertTrue(Integer.parseInt(contentLength)<1500);
		}
		
		public void checkServerType() {
			logger.info("***********************checking Server Type***********************");
			String serverType = response.header("Server"); //getting Content Type
			logger.info("Server Type is ==>"+serverType);  
			Assert.assertEquals(serverType,"cloudflare");
		}
		
		
		
		@AfterClass
		public void tearDown() {
			logger.info("***********************Finished TC005_Delete_Employee_Record***********************");
			
		}
		
}
