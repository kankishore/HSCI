package com.testersconnect.api;

import org.json.XML;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;

//import com.google.gson.JsonObject;
//import com.google.gson.JsonParser;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class HSTestSuite extends TestCaseBase {

	String HAPIKEY="bd123424-645f-4080-bba9-7f8c5b33dc59";
	String companyID="";
	String contactID="";

	//3As Principle
	
	//Create a Company
	@Test(priority=1)
	public void verifyCreateCompany() {
	//Arrange
		logger = extent.createTest("Verify Create Company");
		String URL="https://api.hubapi.com/companies/v2/companies?hapikey="+HAPIKEY;
		String requestBody="{\"properties\": [ { \"name\": \"name\", \"value\": \"IBM\" },  {  \"name\": \"description\",  \"value\": \"GDS Bangalore\"  },{  \"name\": \"domain\",  \"value\": \"ibm.com\"  } ]}";
		
	//Action
		String id=given().contentType("application/json").body(requestBody)
		.when().post(URL).
		
		
	//Assert
		
		then().statusCode(200)
		.extract().path("companyId").toString();
		
		System.out.println(id);
		companyID=id;
		
		if(id!=null) {
			logger.log(Status.PASS, MarkupHelper.createLabel("Company Creation - PASSED", ExtentColor.GREEN));
		}else {
			logger.log(Status.FAIL, MarkupHelper.createLabel("Company Creation - FAILED", ExtentColor.RED));
		}
		
		
	}
	
	//Create a Contact
	@Test(priority=2)
	public void verifyCreateContact() {
		//Arrange
		logger = extent.createTest("Verify Create Contact");
		String URL="https://api.hubapi.com/contacts/v1/contact/?hapikey="+HAPIKEY;
		String requestBody="{ \"properties\": [ { \"property\": \"email\", \"value\": \"mp@ibm.com\" }, { \"property\": \"firstname\",   \"value\": \"Manoj\" },  { \"property\": \"lastname\", \"value\": \"Kumar\"  }]}";
		
		//Action
		contactID =	given().contentType("application/json").body(requestBody).
			when().post(URL).
			
		//Assert
			then().statusCode(200)
			.extract().path("vid").toString();
		
		if(contactID!=null) {
			logger.log(Status.PASS, MarkupHelper.createLabel("Contact Creation - PASSED", ExtentColor.GREEN));
		}else {
			logger.log(Status.FAIL, MarkupHelper.createLabel("Contact Creation - FAILED", ExtentColor.RED));
		}
			
			
	}
	
	
	@Test(priority=3)//Verifies that the Contact is automatically associated to the company
	public void verifyContactAssoicatedToCompany() {
		logger = extent.createTest("Verify Contact Association with Company");
		String URL="https://api.hubapi.com/companies/v2/companies/"+companyID+"/contacts?hapikey="+HAPIKEY;
		System.out.println(URL);
		String cID=given().contentType("application/json").when().get(URL).then().statusCode(200).extract().path("vidOffset").toString();
		System.out.println(cID+" cID");
		System.out.println(contactID+" contactID");
		//Assert.assertEquals(cID, contactID);
		
		logger.log(Status.INFO, MarkupHelper.createLabel("Contact ID= "+cID, ExtentColor.BLUE));
		
		if(cID==contactID) {
			logger.log(Status.PASS, MarkupHelper.createLabel("Contact Association to Company - PASSED", ExtentColor.GREEN));
		}else {
			logger.log(Status.FAIL, MarkupHelper.createLabel("Contact Association to Company - FAILED", ExtentColor.RED));
		}
		
	}
	
	
	@Test(priority=5)
	public void verifyContactDelete() {
		logger = extent.createTest("Verify Delete Contact");
		String URL="https://api.hubapi.com/contacts/v1/contact/vid/"+contactID+"?hapikey="+HAPIKEY;
		Response res=expect().statusCode(200).given().contentType("application/json").when().delete(URL);
		
		if(res.getStatusCode()==200) {
			logger.log(Status.PASS, MarkupHelper.createLabel("Contact Deletion - PASSED", ExtentColor.GREEN));
		}else {
			logger.log(Status.FAIL, MarkupHelper.createLabel("Contact Deletion - FAILED", ExtentColor.RED));
		}
		//given().contentType("application/json").when().delete(URL).then().statusCode(200);
		
		
	}
	
	@Test(priority=4)
	public void verifyCompanyDelete() {
		
		//Arrange
		logger = extent.createTest("Verify Delete Company");
		System.out.println(companyID);
		String URL="https://api.hubapi.com/companies/v2/companies/"+companyID+"?hapikey="+HAPIKEY;
		System.out.println(URL);
		
		//Act
		//expect().statusCode(200).given().contentType("application/json").when().delete(URL);
		Response res=given().contentType("application/json").when().delete(URL);
		
		if(res.getStatusCode()==200) {
			logger.log(Status.PASS, MarkupHelper.createLabel("Company Deletion - PASSED", ExtentColor.GREEN));
		}else {
			logger.log(Status.FAIL, MarkupHelper.createLabel("Company Deletion - FAILED", ExtentColor.RED));
		}		
		
		
		//Assert
		
	}
	
}
	