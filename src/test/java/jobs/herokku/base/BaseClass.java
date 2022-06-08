package jobs.herokku.base;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;


import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import jobs.herokku.utility.XLUtils;



public class BaseClass {
	// Define a static logger variable so that it references the
	public static final Logger logger = LogManager.getLogger(BaseClass.class.getName());

	
    
	@DataProvider(name = "JobAPIData")
	public String[][] getData() throws IOException {
		String path = System.getProperty("user.dir") + "/src/test/java/jobs/herokku/testdata/JobsAPI.xlsx";
		int rownum = XLUtils.getRowCount(path, "Sheet1");
		int cellcount = XLUtils.getCellCount(path, "Sheet1", 1);
		String getAPIJobdata[][] = new String[rownum][cellcount];

		for (int i = 1; i <= rownum; i++) {
			for (int j = 0; j < cellcount; j++) {
				getAPIJobdata[i - 1][j] = XLUtils.getCellData(path, "Sheet1", i, j);// 1 0
			}
		}
		return(getAPIJobdata);
	}
	
	
	protected int getStatusCode(Response response) {
		return response.getStatusCode();
	}
	

}
