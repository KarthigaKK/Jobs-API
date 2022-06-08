package jobs.herokku.testCases;



import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertTrue;

import java.io.File;

import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import jobs.herokku.base.BaseClass;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
public class TC002_Post_Job_Details extends BaseClass{
	
	public RequestSpecification httpRequest = null;
	public Response response = null;

	@BeforeClass
	void postJobDetails()
	{	
		logger.info("*********Started TC001_Post_Job **********");		
		RestAssured.baseURI="https://jobs123.herokuapp.com/Jobs";
		httpRequest=RestAssured.given();	
	}
	
	
	@SuppressWarnings("unchecked")
	@Test(dataProvider = "JobAPIData",priority = 1)
	public void postNewJobDetails(String JobId,String JobTitle,String JobCompanyName,String JobLocation,String JobType,String JobPostedTime,String JobDescription)
	{
		
		//Here Create data to send in post method
		JSONObject requestParams=new JSONObject();
		
		requestParams.put("Job Id", JobId);
		requestParams.put("Job Title", JobTitle);
		requestParams.put("Job Company Name", JobCompanyName);
		requestParams.put("Job Location", JobLocation);
		requestParams.put("Job Type", JobType);
		requestParams.put("Job Posted time", JobPostedTime);
		requestParams.put("Job Description",JobDescription);
		
		// Add a header stating the Request body is a JSON
		httpRequest.header("Content-Type", "application/json");

		// Add the Json to the body of the request
		httpRequest.body(requestParams.toJSONString());
		response = httpRequest.request(Method.POST);
		
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
			assertThat(responseBody, JsonSchemaValidator.matchesJsonSchema(new File(System.getProperty("user.dir") + "\\src\\main\\resources\\schema.json")));
			logger.info("Schema validated"); 
		}
	}


	@Test(priority = 3)
	void checkResposeBody()
	{
		logger.info("***********  Checking Respose Body **********");		
		String responseBody = response.getBody().prettyPrint();
		logger.info("Response Body==>"+responseBody);
		System.out.println(responseBody);
		Assert.assertTrue(responseBody!=null);		
		logger.info("*********End TC001_Post_Job **********");
	}
	
	

}
