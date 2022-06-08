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

public class TC005_Delete_Job extends BaseClass {
	public RequestSpecification httpRequest = null;
	public Response response = null;
	
	int JobID=5000;
	@SuppressWarnings("unchecked")
	@BeforeClass
	void deleteEmployee()
	{
		logger.info("*********Started TC005_Delete_Job **********");		
		RestAssured.baseURI="https://jobs123.herokuapp.com/Jobs";
		httpRequest=RestAssured.given();		
		JSONObject requestParams = new JSONObject();
	   	requestParams.put("Job Id", JobID); // Cast
	
		// Add a header stating the Request body is a JSON
		httpRequest.header("Content-Type", "application/json");
		// Add the Json to the body of the request
		httpRequest.body(requestParams.toJSONString());
		response = httpRequest.request(Method.DELETE);

	}
	
	
	@Test
	void checkStatusCodeAndSchema()
	{
		int statusCode = response.getStatusCode(); // Getting status code
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

	@Test
	void checkcontentEncoding()
	{
		String contentEncoding = response.header("Content-Encoding");
		Assert.assertEquals(contentEncoding, null);

	}

	@AfterClass
	void tearDown()
	{
		logger.info("*********  Finished TC005_Delete_Job**********");
	}

}
