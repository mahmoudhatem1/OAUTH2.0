package oauthApiTesting;

import files.convertingBacktoJson;
import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import deserilization.webAutomation;
import deserilization.api;
import deserilization.getCourse;

import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.testng.Assert;

public class oauthApi {
	public static void main(String[]args) {
		//Note don't run this code by using my value code you have to hit this url first
		//the url is:https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/userinfo.email&auth_url=https://accounts.google.com/o/oauth2/v2/auth&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&response_type=code&redirect_uri=https://rahulshettyacademy.com/getCourse.php
		//after hitting the url copying it in my value url and it will work
		String url="https://rahulshettyacademy.com/getCourse.php?code=4%2F0AX4XfWhjvXjMkhJqgdf2BhrwgsvIShWqkPfUcfuP_JZKHqsE8tU4TAmU9Ss27H9FtkpeaQ&scope=email+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+openid&authuser=0&prompt=consent";
				//now you need to splitting it
				String partialCode=url.split("code=")[1];
				String code=partialCode.split("&scope")[0];
				System.out.println(code);
				  
				String accessTokenResponse=
				given().log().all().queryParam("code","" ).queryParam("client_id","692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com" ).queryParam("client_secret","erZOWM9g3UtwNRj340YYaK_W" )
				.queryParam("redirect_uri","https://rahulshettyacademy.com/getCourse.php").queryParam("grant_type", "authorization_code")
				.when().log().all().post("https://www.googleapis.com/oauth2/v4/token").asString();
				JsonPath js=new JsonPath(accessTokenResponse);
				String accessTokenValue=js.getString("acess_token");
				
				
				getCourse gs=
						given().queryParam("access_token", accessTokenValue).expect().defaultParser(Parser.JSON).when().get("https://rahulshettyacademy.com/getCourse.php")
						.as(getCourse.class);
						
					
				String x=gs.getLinkedIn();
				System.out.println(x);
				//here if you know the index
				String myCourseTitle=
						gs.getcourses().getApi().get(1).getCourseTitle();
				//but if you dont know the index
				//so how to do that dynamically without using any index
				List<api> myApi=gs.getcourses().getApi();
				//now you can looping
				for(int i=0;i<myApi.size();i++) {
					String result=myApi.get(i).getCourseTitle();
					if(result.equalsIgnoreCase("SoapUI Webservices testing")) {
						String price=myApi.get(i).getPrice();
						System.out.println(price);
						break;
					}
				}
				//now its time to get all the course titles in web automation and compare it with our array
				//first declare array
				String[] compare= {"Selenium Webdriver Java","Protractor","Cypress"};
				//we declare it as an array because we know its size 
				//array is used for fixed number of elements
				//but now we have to convert it to list because we will compare it with list
				List<String> comparing=Arrays.asList(compare);
				List<webAutomation> copying=gs.getcourses().getWebAutomation();
				ArrayList<String> filling=new ArrayList<String>();
				for(int i=0;i<copying.size();i++) {
					filling.add(copying.get(i).getCourseTitle());
				}
				Assert.assertTrue(comparing.equals(filling));

		}

		
		
		

		
		
		
		
		
		
			
		}
	
	


