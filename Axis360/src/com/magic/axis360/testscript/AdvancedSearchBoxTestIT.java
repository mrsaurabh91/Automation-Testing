package com.magic.axis360.testscript;

import java.net.MalformedURLException;

import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.magic.axis360.common.BaseTest;
import com.magic.axis360.page.HomePage;

public class AdvancedSearchBoxTestIT extends BaseTest {
	
	@Test(priority=25)
	@Parameters({"operatingSystem"})
	public void verifyAdvanceSearchButton(String operatingSystem) throws InterruptedException, MalformedURLException{
		loginApplication(operatingSystem);
		boolean actualResult = false;
		try {
			HomePage homePage = new HomePage(driver);		
			actualResult = homePage.checkAdvanceSearchAppear();
			extentTest = extentReports.createTest("Verify Advanced Search Presence","This test verifies Advanced Search Button present or not");
			extentTest.assignCategory("Advanced Search");
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		Assert.assertEquals(actualResult, true);
	}
	
//	@Test(dependsOnMethods={"verifyAdvanceSearchButton"})
	@Test(priority=26)
	public void verifyAdvanceSearchOptionAfterClickOnAdvanceSearch() throws InterruptedException{
		HomePage homePage = new HomePage(driver);		
		boolean actualResult = homePage.checkAdvanceSearchOptionAppear();
		extentTest = extentReports.createTest("Verify Advanced Search Options Presence","This test verifies Advanced Search Options present or not");
		extentTest.assignCategory("Advanced Search");
		Assert.assertEquals(actualResult, true);
	}
	
//	@Test(dependsOnMethods={"verifyAdvanceSearchOptionAfterClickOnAdvanceSearch"})
	@Test(priority=27)
	public void verifyRadioButtonWithFieldALLAtAdvanceSearchButton() throws InterruptedException{
		HomePage homePage = new HomePage(driver);	
		String advanceSearchOption  = "ALL";
		boolean actualResult = homePage.checkRadioButtonWithText(advanceSearchOption,"no");
		extentTest = extentReports.createTest("Verify Advanced Search 'ALL' Presence","This test verifies 'ALL' option present or not");
		extentTest.assignCategory("Advanced Search");
		Assert.assertEquals(actualResult, true);
	}
	
//	@Test(dependsOnMethods={"verifyRadioButtonWithFieldALLAtAdvanceSearchButton"})
	@Test(priority=28)
	public void verifyRadioButtonWithFieldALLSelectedByDefaultAtAdvanceSearchButton() throws InterruptedException{
		HomePage homePage = new HomePage(driver);	
//		String advanceSearchOption  = "ALL";
//		String actualResult = homePage.checkRadioButtonSelected(advanceSearchOption,"no");
		String actualResult = homePage.checkRadioButtonSelected();
		extentTest = extentReports.createTest("Verify ALL selected by default","This test verifies 'ALL' option selected by default or not");
		extentTest.assignCategory("Advanced Search");
		Assert.assertEquals(actualResult, "true");
	}
	
//	@Test(dependsOnMethods={"verifyRadioButtonWithFieldALLSelectedByDefaultAtAdvanceSearchButton"})
	@Test(priority=29)
	public void verifyRadioButtonWithFieldTITLEAtAdvanceSearchButton() throws InterruptedException{
		HomePage homePage = new HomePage(driver);	
		String advanceSearchOption  = "TITLE";
		boolean actualResult = homePage.checkRadioButtonWithText(advanceSearchOption,"no");
		extentTest = extentReports.createTest("Verify Advanced Search 'TITLE' Presence","This test verifies 'TITLE' option present or not");
		extentTest.assignCategory("Advanced Search");
		Assert.assertEquals(actualResult, true);
	}


//	@Test(dependsOnMethods={"verifyRadioButtonWithFieldTITLEAtAdvanceSearchButton"})
	@Test(priority=30)
	public void verifyRadioButtonWithFieldTITLEAtAUTHORSearchButton() throws InterruptedException{
		HomePage homePage = new HomePage(driver);	
		String advanceSearchOption  = "AUTHOR";
		boolean actualResult = homePage.checkRadioButtonWithText(advanceSearchOption,"no");
		extentTest = extentReports.createTest("Verify Advanced Search 'AUTHOR' Presence","This test verifies 'AUTHOR' option present or not");
		extentTest.assignCategory("Advanced Search");
		Assert.assertEquals(actualResult, true);
	}

		
//	@Test(dependsOnMethods={"verifyRadioButtonWithFieldTITLEAtAUTHORSearchButton"})
	@Test(priority=31)
	public void verifyRadioButtonWithFieldISBNAtAuthorSearchButton() throws InterruptedException{
		HomePage homePage = new HomePage(driver);	
		String advanceSearchOption  = "ISBN";
		boolean actualResult = homePage.checkRadioButtonWithText(advanceSearchOption,"no");
		extentTest = extentReports.createTest("Verify Advanced Search 'ISBN' Presence","This test verifies 'ISBN' option present or not");
		extentTest.assignCategory("Advanced Search");
		Assert.assertEquals(actualResult, true);
	}
	
//	@Test(dependsOnMethods={"verifyRadioButtonWithFieldISBNAtAuthorSearchButton"})
	@Test(priority=32)
	@Parameters({"searchByKeyword"})
	public void verifySearchForTitleByKeywordAtAdvanceSearch(String searchByKeyword) throws InterruptedException{
		HomePage homePage = new HomePage(driver);		
		String expectedData = searchByKeyword;		
		boolean actualResult = homePage.verifySearchedByKeywordResult(expectedData,"advance");
		extentTest = extentReports.createTest("Verify Advanced Search by keyword","This test verifies Advanced search by keyword");
		extentTest.assignCategory("Advanced Search");
		Assert.assertEquals(actualResult, true);
	}
	
//	@Test(dependsOnMethods={"verifySearchForTitleByKeywordAtAdvanceSearch"})
	@Test(priority=33)
	@Parameters({"searchByTitle"})
	public void verifySearchForTitleByTitleAtAdvanceSearch(String searchByTitleText) throws InterruptedException{
		HomePage homePage = new HomePage(driver);		
		String expectedData = searchByTitleText;		
		boolean actualResult = homePage.verifySearchedByTitleResult(expectedData,"advance");
		extentTest = extentReports.createTest("Verify Advanced Search by Title","This test verifies Advanced search by Title");
		extentTest.assignCategory("Advanced Search");
		Assert.assertEquals(actualResult, true);
	}
	
//	@Test(dependsOnMethods={"verifySearchForTitleByTitleAtAdvanceSearch"})
	@Test(priority=34)
	@Parameters({"searchByAuthor"})
	public void veriySearchForTitleByAuthorAtAdvanceSearch(String searchByAuthorText) throws InterruptedException{
		HomePage homePage = new HomePage(driver);		
		String expectedData = searchByAuthorText;		
		boolean actualResult = homePage.verifySearchedByAuthorResult(expectedData,"advance");
		extentTest = extentReports.createTest("Verify Advanced Search by Author","This test verifies Advanced search by Author");
		extentTest.assignCategory("Advanced Search");
		Assert.assertEquals(actualResult, true);
	}
	
//	@Test(dependsOnMethods={"veriySearchForTitleByAuthorAtAdvanceSearch"})
	@Test(priority=35)
	@Parameters({"searchByISBN"})
	public void verifySearchForTitleByISBNAtAdvanceSearchAtAdvanceSearch(String searchByISBNText) throws InterruptedException{
		HomePage homePage = new HomePage(driver);		
		String expectedData = searchByISBNText;		
		boolean actualResult = homePage.verifySearchedByISBNResult(expectedData,"advance");
		extentTest = extentReports.createTest("Verify Advanced Search by ISBN","This test verifies Advanced search by ISBN");
		extentTest.assignCategory("Advanced Search");
		Assert.assertEquals(actualResult, true);
	}
	
/*		@Test(dependsOnMethods={"verifySearchForTitleByISBNAtAdvanceSearchAtAdvanceSearch"})
	public void verifySearchOptionCloseButtonAtAdvanceSearchButtonClosesAdvanceSearchOption() throws InterruptedException{
		HomePage homePage = new HomePage(driver);		
		boolean actualResult = homePage.checkCloseButtonCloseAdvanceButtonSearchOption();
		Assert.assertEquals(actualResult, true);
	}
	*/

}
