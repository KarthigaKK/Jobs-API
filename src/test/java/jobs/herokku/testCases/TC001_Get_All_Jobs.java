package jobs.herokku.testCases;

import static org.hamcrest.MatcherAssert.assertThat;

import java.io.File;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.http.Method;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import jobs.herokku.base.BaseClass;

public class TC001_Get_All_Jobs extends BaseClass {
	
	public RequestSpecification httpRequest = null;
	public Response response = null;
	
	@BeforeClass
	void getAllEmployees() 
	{
		logger.info("*********Started TC001_Get_All_Employees **********");
		RestAssured.baseURI = "https://jobs123.herokuapp.com";
		RestAssured.basePath = "/Jobs";
		httpRequest=RestAssured.given();
		response=httpRequest.request(Method.GET);
	}
			
	@Test(priority = 1)
	void checkResposeBody()
	{
		logger.info("***********  Checking Respose Body **********");
		String responseBody = response.getBody().prettyPrint();
		logger.info("Response Body==>"+responseBody);
		System.out.println(responseBody);
		Assert.assertTrue(responseBody!=null);		
	}
	
	@Test(priority = 2)
	void checkStatusCodeAndSchema()
	{
		logger.info("***********  Checking Status Code **********");		
		int statusCode = response.getStatusCode(); // Getting status code
		logger.info("Status Code is ==>" + statusCode); //200
		Assert.assertEquals(statusCode, 200);	
		if (statusCode == 200) {
			String responseBody = response.getBody().asString().replaceAll("NaN", "\"9 hrs\"");
			assertThat(responseBody , JsonSchemaValidator.matchesJsonSchema(new File(System.getProperty("user.dir") + "\\src\\main\\resources\\schema.json")));
			logger.info("Schema validated"); 
		}
	}
		
	@Test(priority = 3)
	void checkResponseTime()
	{
		logger.info("***********  Checking Response Time **********");		
		long responseTime = response.getTime(); // Getting status Line
		logger.info("Response Time is ==>" + responseTime);		
		if(responseTime>2000)
			logger.warn("Response Time is greater than 2000");		
		Assert.assertTrue(responseTime<2000);			
	}
	
	@Test(priority = 4)
	void checkstatusLine()
	{
		logger.info("***********  Checking Status Line **********");		
		String statusLine = response.getStatusLine(); // Getting status Line
		logger.info("Status Line is ==>" + statusLine);
		Assert.assertEquals(statusLine, "HTTP/1.1 200 OK");		
	}
	
	
	@Test(priority = 5)
	void checkContentType()
	{
		logger.info("***********  Checking Content Type **********");		
		String contentType = response.header("Content-Type");
		logger.info("Content type is ==>" + contentType);
		Assert.assertEquals(contentType, "application/json");
	}

	@Test(priority = 6)
	void checkserverType()
	{
		logger.info("***********  Checking Server Type **********");		
		String serverType = response.header("Server");
		logger.info("Server Type is =>" +serverType); 
		Assert.assertEquals(serverType, "gunicorn");	
	}


	@Test(priority = 7)
	void checkContentLenght()
	{
		logger.info("***********  Checking Content Lenght**********");		
		String contentLength = response.header("Content-Length");
		logger.info("Content Length is==>" +contentLength); 		
		if(Integer.parseInt(contentLength)<10000)
			logger.warn("Content Length is less than 100");		
		Assert.assertTrue(Integer.parseInt(contentLength)>10000);		
	}
	
	
	@Test(priority = 8)
	public void checkContentEncoding()
	{
		//Validate content encoding
		String contentEncoding=response.header("Content-Encoding");
		logger.info("Content Encoding is==>" +contentEncoding); 		
		Assert.assertEquals(contentEncoding, null);	
	}
	
	@Test(priority = 9)
	public void checkAllHeaders()
	{
		Headers allheaders=response.headers();
		logger.info("Headers is==>" +allheaders); 
		
		for (Header header:allheaders)
		{
			System.out.println("header name"+header.getName());
			System.out.println("header value"+header.getValue());
		}
	}
	
	@AfterClass
	void tearDown()
	{
		logger.info("*********  Finished TC001_Get_All_Jobs **********");
	}
	

}
