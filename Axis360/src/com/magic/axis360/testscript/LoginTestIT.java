package com.magic.axis360.testscript;


import java.net.MalformedURLException;

import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.magic.axis360.common.BaseTest;
import com.magic.axis360.input.InputDetails;
import com.magic.axis360.page.HomePage;
import com.magic.axis360.page.LoginPage;
import com.magic.util.Log;
import com.magic.util.WebDriverFactory;

public class LoginTestIT extends BaseTest{
	
	@Test(priority=1)
	@Parameters({"loginExpectedMsg","operatingSystem"})
	public void verifyLogin(String loginExpectedMsg,String operatingSystem) throws InterruptedException, MalformedURLException{
		
		
		
		
		/*
		if(operatingSystem.equalsIgnoreCase("Windows")) {
			reportPath = System.getProperty("user.dir")+"/test-output/WindowsExtentReport.html";
			extentReports = createInstance(reportPath);
		}
		else if(operatingSystem.equalsIgnoreCase("Android")) {
			reportPath = System.getProperty("user.dir")+"/test-output/AndroidExtentReport.html";
			extentReports = createInstance(reportPath);
		}
		else {
			System.out.println("operatingSystem not match");
		}*/
	//	System.out.println("loginExpectedMsg "+loginExpectedMsg);
		System.out.println("operatingSystem "+operatingSystem);
		
		driver = WebDriverFactory.getWebDriver(operatingSystem);
		
		HomePage homePage =  null;
		try
		{
			InputDetails inputDetails = new InputDetails();
			driver.get(inputDetails.getUrl());
			LoginPage loginPage = new LoginPage(driver);
			
			homePage = loginPage.loginLibrary(inputDetails.getLibraryCard(),operatingSystem);
			Log.info("Successfully Logged In");
			extentTest = extentReports.createTest("Login Test","This test verifies that user is able to login or not");
			extentTest.assignCategory("Log In");
		}
		catch (Exception e) {
			
			// TODO: handle exception
			e.printStackTrace();
		}
		
		Assert.assertEquals(homePage.verifyLogoutButton(), true);
	}	
}