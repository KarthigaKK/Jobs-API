package jobs.herokku.testCases;

import static org.hamcrest.MatcherAssert.assertThat;

import java.io.File;

import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import jobs.herokku.base.BaseClass;
import jobs.herokku.utility.ReadUtils;


public class TC004_Put_Job_Update extends BaseClass {
	String jobId="1034";
	//String jobId=ReadUtils.jobId();
	String jobTitle=ReadUtils.jobTitle();
	public RequestSpecification httpRequest = null;
	public Response response = null;
	
	
	@SuppressWarnings("unchecked")
	@BeforeClass
	void updateJobs()
	{
		logger.info("*********Started TC004_Put_Employee_Record **********");		
		RestAssured.baseURI = "https://jobs123.herokuapp.com/Jobs";
		httpRequest = RestAssured.given();
		JSONObject requestParams = new JSONObject();
		requestParams.put("Job Id", "\"1034\""); // Cast
		requestParams.put("Job Title", jobTitle);
		// Add a header stating the Request body is a JSON
		httpRequest.header("Content-Type","application/json");
		// Add the Json to the body of the request
		httpRequest.body(requestParams.toJSONString());
		response = httpRequest.request(Method.PUT);
	}
	
	@Test
	void checkResposeBody()
	{
		String responseBody = response.getBody().asString();				
		Assert.assertEquals(responseBody.contains(jobTitle), true);		
	}
		
	@Test
	void checkStatusCodeAndSchema()
	{
		int statusCode = response.getStatusCode(); // Getting status code
		Assert.assertEquals(statusCode, 200);
		Assert.assertEquals(statusCode, 200);	
		if (statusCode == 200) {
			String responseBody = response.getBody().asString().replaceAll("NaN", "\"9 hrs\"");
			assertThat(responseBody, JsonSchemaValidator.matchesJsonSchema(new File(System.getProperty("user.dir") + "\\src\\main\\resources\\schema.json")));
			logger.info("Schema validated"); 
		}
	}
		
	@Test
	void checkstatusLine()
	{
		String statusLine = response.getStatusLine(); // Getting status Line
		Assert.assertEquals(statusLine, "HTTP/1.1 200 OK");
		
	}
	
	@Test
	void checkContentType()
	{
		String contentType = response.header("Content-Type");
		Assert.assertEquals(contentType, "application/json");
	}

	@Test
	void checkserverType()
	{
		String serverType = response.header("Server");
		Assert.assertEquals(serverType, "gunicorn");
	}

	
	
	@AfterClass
	void tearDown()
	{
		logger.info("*********  Finished TC004_Put_Job_Update **********");
	}

}
