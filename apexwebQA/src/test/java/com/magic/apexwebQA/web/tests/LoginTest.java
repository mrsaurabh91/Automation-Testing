package com.magic.apexwebQA.web.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.magic.apexwebQA.pages.DashboardPage;
import com.magic.apexwebQA.pages.LoginPage;
import com.magic.apexwebQA.utils.GenericUtil;
import com.magic.apexwebQA.utils.SeleniumUtil;
import com.magic.apexwebQA.web.AbstractWebTest;

public class LoginTest extends AbstractWebTest{
	
	String username = SeleniumUtil.fetchUserDetails("USERNAME");
	String password = SeleniumUtil.fetchUserDetails("PASSWORD");
	String server = SeleniumUtil.fetchUserDetails("SERVER");
	String expectedProfileName = SeleniumUtil.fetchUserDetails("PROFILENAME");
	
	@Test
	public void loginTest() {
		
		test = extent.createTest("Login Verification","This test verify whether we are able to login or not");
		test.assignCategory("Login");
		
		System.out.println(GenericUtil.getCurrentTimeMinusSecond());
		
		LoginPage loginPage = new LoginPage(driver);
		loginPage.loginUser(username, password,server);
		
		DashboardPage dashboardPage = new DashboardPage(driver);
		dashboardPage.clickProfileIcon();
		String profileName = dashboardPage.getProfileName();
		test.info("Profile name appearing on the dashboard page is: "+profileName);
		
		Assert.assertEquals(profileName, expectedProfileName);	
	}

}
