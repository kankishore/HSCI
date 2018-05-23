package com.testersconnect.api;

import org.json.XML;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.annotations.Test;

//import com.google.gson.JsonObject;
//import com.google.gson.JsonParser;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class TestClass {

	String HAPIKEY="bd123424-645f-4080-bba9-7f8c5b33dc59";

	//3As Principle
	
	//Create a Company
	@Test(priority=1)
	public void verifyCreateCompany() {
	//Arrange
		String URL="https://api.hubapi.com/companies/v2/companies?hapikey="+HAPIKEY;
		String requestBody="{\"properties\": [ { \"name\": \"name\", \"value\": \"IBM\" },  {  \"name\": \"description\",  \"value\": \"GDS Bangalore\"  },{  \"name\": \"domain\",  \"value\": \"ibm.com\"  } ]}";
		
	//Action
		given().contentType("application/json").body(requestBody)
		.when().post(URL).
		
		
	//Assert
		
		then().statusCode(200);

		
	}
	
	//Create a Contact
	@Test(priority=2)
	public void verifyCreateContact() {
		//Arrange
		String URL="https://api.hubapi.com/contacts/v1/contact/?hapikey="+HAPIKEY;
		String requestBody="{ \"properties\": [ { \"property\": \"email\", \"value\": \"mp@ibm.com\" }, { \"property\": \"firstname\",   \"value\": \"Manoj\" },  { \"property\": \"lastname\", \"value\": \"Kumar\"  }]}";
		
		//Action
			given().contentType("application/json").body(requestBody).
			when().post(URL).
			
		//Assert
			then().statusCode(200);
	}
	
	
	@Test
	public void verifyContactDelete() {
		
	}
	
	@Test
	public void verifyCompanyDelete() {
		
	}
	
	//Verify that the contact is added/associated to the company
	@Test
	public void verifyContactAddedToCompany() {
				//Arrange
		
				//Action
					
					
				//Assert
	}
	
	
	
	
	//@Test(priority=1)
	public void test2() {
		
		String body="{\"properties\": [{\"property\": \"email\", \"value\": \"testingapis141@hubspot.com\" }, {\"property\": \"firstname\", \"value\": \"Adrian\" }, {\"property\": \"lastname\", \"value\": \"Mott\" }]}";
		JSONObject json=null;
		JSONParser parser = new JSONParser();
		try {
			json = (JSONObject) parser.parse(body);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		given()
		.contentType("application/json")
		.body(json)
		.when().post("https://api.hubapi.com/contacts/v1/contact/?hapikey="+HAPIKEY)
		.then()
		.statusCode(409);
	}
	
	
	//@Test(priority=2)
	public void getCompany() {
		given()
		.contentType("application/json")
		.when().get("https://api.hubapi.com/companies/v2/companies/10444744?hapikey="+HAPIKEY)
		.then()
		
		.statusCode(404)
		.and()
		.body("isDeleted", is(false));
	}
	
	//@Test(priority=3)
	public void sampleBook() {
		
		String str="{\"name\":\"Manoj\", \"city\":\"Bangalore\"}";
		
		JSONParser parser = new JSONParser();
		JSONObject json = null;
		try {
			json = (JSONObject) parser.parse(str);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String xml = XML.toString(json);
		
		System.out.println(xml);
	}
	
	
}
