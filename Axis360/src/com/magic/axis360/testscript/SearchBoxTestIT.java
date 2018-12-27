package com.magic.axis360.testscript;


import java.net.MalformedURLException;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.magic.axis360.common.BaseTest;
import com.magic.axis360.page.HomePage;
import com.magic.util.Log;
import com.magic.util.SeleniumUtil;

public class SearchBoxTestIT extends BaseTest{
	
//	public WebDriver driver;
	
	@Test(priority=2)
	@Parameters({"operatingSystem"})
	public void verifySearchBox(String operatingSystem) throws InterruptedException, MalformedURLException{
		loginApplication(operatingSystem);
		System.out.println("driver "+driver);
		HomePage homePage = new HomePage(driver);
		boolean actualResult = homePage.getSearchBox();
		extentTest = extentReports.createTest("Verify SearchBox Presence","This test verifies SearchBox present or not");
		extentTest.assignCategory("Simple Search");
		Assert.assertEquals(actualResult, true);	
	}
	
//	@Test(dependsOnMethods={"verifySearchBox"})
	@Test(priority=3)
	public void verifySearchBoxNotPreFilled() throws InterruptedException{
		HomePage homePage = new HomePage(driver);
		String actualResult = SeleniumUtil.waitForElementPresence(driver, homePage.searchBox, 30).getAttribute("value");
		extentTest = extentReports.createTest("Verify SearchBox Not prefilled","This test verifies SearchBox is prefilled or not");
		extentTest.assignCategory("Simple Search");
		Assert.assertEquals(actualResult, "");
		Log.info("Search box not pre-filled");
	}
	
//	@Test(dependsOnMethods={"verifySearchBoxNotPreFilled"})
	@Test(priority=4)
	@Parameters({"searchByAlphaNumeric"})
	public void verifyAlphaNumericCharacterInSearchBox(String AlphaNumericText) throws InterruptedException{
		HomePage homePage = new HomePage(driver);
		String expectedResult = AlphaNumericText;
		SeleniumUtil.waitForElementPresence(driver, homePage.searchBox, 30).sendKeys(expectedResult);
		String actualResult = SeleniumUtil.waitForElementPresence(driver, homePage.searchBox, 30).getAttribute("value").trim();
		extentTest = extentReports.createTest("Verify Alphanumerice characters in SearchBox","This test verifies whether alphanumeric characters can be passed to SearchBox or not");
		extentTest.assignCategory("Simple Search");
		Assert.assertEquals(actualResult, expectedResult);
		Log.info("Alphanumeric characters can be passed to searchbox");
	}
	
//	@Test(dependsOnMethods={"verifyAlphaNumericCharacterInSearchBox"})
	@Test(priority=5)
	@Parameters({"searchByKeyword"})
	public void verifySearchForTitleByKeyword(String searchByKeyword) throws InterruptedException{
		HomePage homePage = new HomePage(driver);		
		String expectedData = searchByKeyword;		
		boolean actualResult = homePage.verifySearchedByKeywordResult(expectedData,"simple");
		extentTest = extentReports.createTest("Verify Search by keyword","This test verifies search by keyword");
		extentTest.assignCategory("Simple Search");
		Assert.assertEquals(actualResult, true);
	}
	
	@Test(priority=6)
//	@Test(dependsOnMethods={"verifySearchForTitleByKeyword"})
	public void verifyRefineButtonPresenceForKeywordTest() throws InterruptedException{
		HomePage homePage = new HomePage(driver);
		boolean actualResult = homePage.verifyRefineButtonPresence();
		extentTest = extentReports.createTest("Verify Refine Button Presence","This test verifies Refine button present or not");
		extentTest.assignCategory("Simple Search");
		Assert.assertEquals(actualResult, true);
	}
	
//	@Test(dependsOnMethods={"verifySearchForTitleByKeyword"})
	@Test(priority=7)
	@Parameters({"searchByKeyword"})
	public void verifyKeywordRetentionInSearchBoxForKeywordTest(String searchByKeyword) throws InterruptedException{
		HomePage homePage = new HomePage(driver);
		boolean actualResult = homePage.verifyKeywordRetentionInSearchBox(searchByKeyword);
		extentTest = extentReports.createTest("Verify Keyword Retention","This test verifies keyword retention in searchbox after search");
		extentTest.assignCategory("Simple Search");
		Assert.assertEquals(actualResult, true);
	}
	
//	@Test(dependsOnMethods={"verifySearchForTitleByKeyword"})
	@Test(priority=8)
	@Parameters({"searchByKeyword"})
	public void verifySearchedMessageSearchForTitleByKeyword(String searchByKeyword) throws InterruptedException{
		HomePage homePage = new HomePage(driver);
		boolean actualResult = homePage.verifySearchedResultMessage(searchByKeyword);
		extentTest = extentReports.createTest("Verify Search message presence","This test verifies whether search message present or not");
		extentTest.assignCategory("Simple Search");
		Assert.assertEquals(actualResult, true);
	}
	
	
//	@Test(dependsOnMethods={"verifySearchedMessageSearchForTitleByKeyword"})
	@Test(priority=9)
	@Parameters({"searchByTitle"})
	public void verifySearchForTitleByTitle(String searchByTitleText) throws InterruptedException{
		HomePage homePage = new HomePage(driver);		
		String expectedData = searchByTitleText;		
		boolean actualResult = homePage.verifySearchedByTitleResult(expectedData,"simple");
		extentTest = extentReports.createTest("Verify Search by Title","This test verifies search by Title");
		extentTest.assignCategory("Simple Search");
		Assert.assertEquals(actualResult, true);
	}
	
//	@Test(dependsOnMethods={"verifySearchForTitleByTitle"})
	@Test(priority=10)
	public void verifyRefineButtonPresenceForTitleTest() throws InterruptedException{
		HomePage homePage = new HomePage(driver);
		boolean actualResult = homePage.verifyRefineButtonPresence();
		extentTest = extentReports.createTest("Verify Refine Button Presence","This test verifies Refine button present or not");
		extentTest.assignCategory("Simple Search");
		Assert.assertEquals(actualResult, true);
	}
	
//	@Test(dependsOnMethods={"verifySearchForTitleByTitle"})
	@Test(priority=11)
	@Parameters({"searchByTitle"})
	public void verifyKeywordRetentionInSearchBoxForTitleByTitle(String searchByKeyword) throws InterruptedException{
		HomePage homePage = new HomePage(driver);
		boolean actualResult = homePage.verifyKeywordRetentionInSearchBox(searchByKeyword);
		extentTest = extentReports.createTest("Verify Keyword Retention","This test verifies keyword retention in searchbox after search");
		extentTest.assignCategory("Simple Search");
		Assert.assertEquals(actualResult, true);
	}
	
//	@Test(dependsOnMethods={"verifySearchForTitleByTitle"})
	@Test(priority=12)
	@Parameters({"searchByTitle"})
	public void verifySearchedMessageSearchForTitleByTitle(String searchByKeyword) throws InterruptedException{
		HomePage homePage = new HomePage(driver);
		boolean actualResult = homePage.verifySearchedResultMessage(searchByKeyword);
		extentTest = extentReports.createTest("Verify Search message presence","This test verifies whether search message present or not");
		extentTest.assignCategory("Simple Search");
		Assert.assertEquals(actualResult, true);
	}
			
//	@Test(dependsOnMethods={"verifySearchedMessageSearchForTitleByTitle"})
	@Test(priority=13)
	@Parameters({"searchByAuthor"})
	public void veriySearchForTitleByAuthor(String searchByAuthorText) throws InterruptedException{
		HomePage homePage = new HomePage(driver);		
		String expectedData = searchByAuthorText;		
		boolean actualResult = homePage.verifySearchedByAuthorResult(expectedData,"simple");
		extentTest = extentReports.createTest("Verify Search by Author","This test verifies search by Author");
		extentTest.assignCategory("Simple Search");
		Assert.assertEquals(actualResult, true);
	}
	
//	@Test(dependsOnMethods={"veriySearchForTitleByAuthor"})
	@Test(priority=14)
	public void verifyRefineButtonPresenceForAuthorTest() throws InterruptedException{
		HomePage homePage = new HomePage(driver);
		boolean actualResult = homePage.verifyRefineButtonPresence();
		extentTest = extentReports.createTest("Verify Refine Button Presence","This test verifies Refine button present or not");
		extentTest.assignCategory("Simple Search");
		Assert.assertEquals(actualResult, true);
	}
	
//	@Test(dependsOnMethods={"veriySearchForTitleByAuthor"})
	@Test(priority=15)
	@Parameters({"searchByAuthor"})
	public void verifyKeywordRetentionInSearchBoxForAuthorTest(String searchByAuthorText) throws InterruptedException{
		HomePage homePage = new HomePage(driver);
		boolean actualResult = homePage.verifyKeywordRetentionInSearchBox(searchByAuthorText);
		extentTest = extentReports.createTest("Verify Keyword Retention","This test verifies keyword retention in searchbox after search");
		extentTest.assignCategory("Simple Search");
		Assert.assertEquals(actualResult, true);
	}
	
//	@Test(dependsOnMethods={"veriySearchForTitleByAuthor"})
	@Test(priority=16)
	@Parameters({"searchByAuthor"})
	public void verifySearchedMessageSearchForTitleByAuthor(String searchByTitleText) throws InterruptedException{
		HomePage homePage = new HomePage(driver);		
		String expectedData = searchByTitleText;		
		boolean actualResult = homePage.verifySearchedResultMessage(expectedData);
		extentTest = extentReports.createTest("Verify Search message presence","This test verifies whether search message present or not");
		extentTest.assignCategory("Simple Search");
		Assert.assertEquals(actualResult, true);
	}
	
//	@Test(dependsOnMethods={"verifySearchedMessageSearchForTitleByAuthor"})
	@Test(priority=17)
	@Parameters({"searchByISBN"})
	public void verifySearchForTitleByISBN(String searchByISBNText) throws InterruptedException{
		HomePage homePage = new HomePage(driver);		
		String expectedData = searchByISBNText;		
		boolean actualResult = homePage.verifySearchedByISBNResult(expectedData,"simple");
		extentTest = extentReports.createTest("Verify Search by ISBN","This test verifies search by ISBN");
		extentTest.assignCategory("Simple Search");
		Assert.assertEquals(actualResult, true);
	}
	
//	@Test(dependsOnMethods={"verifySearchForTitleByISBN"})
	@Test(priority=18)
	public void verifyRefineButtonPresenceForTitleByISBN() throws InterruptedException{
		HomePage homePage = new HomePage(driver);
		boolean actualResult = homePage.verifyRefineButtonPresence();
		extentTest = extentReports.createTest("Verify Refine Button Presence","This test verifies Refine button present or not");
		extentTest.assignCategory("Simple Search");
		Assert.assertEquals(actualResult, true);
	}
	
//	@Test(dependsOnMethods={"verifySearchForTitleByISBN"})
	@Test(priority=19)
	@Parameters({"searchByISBN"})
	public void verifyKeywordRetentionInSearchBoxForTitleByISBN(String searchByISBNText) throws InterruptedException{
		HomePage homePage = new HomePage(driver);
		boolean actualResult = homePage.verifyKeywordRetentionInSearchBox(searchByISBNText);
		extentTest = extentReports.createTest("Verify Keyword Retention","This test verifies keyword retention in searchbox after search");
		extentTest.assignCategory("Simple Search");
		Assert.assertEquals(actualResult, true);
	}
	
//	@Test(dependsOnMethods={"verifySearchForTitleByISBN"})
	@Test(priority=20)
	@Parameters({"searchByISBN"})
	public void verifySearchedMessageSearchForTitleByISBN(String searchByISBNText) throws InterruptedException{
		HomePage homePage = new HomePage(driver);		
		String expectedData = searchByISBNText;		
		boolean actualResult = homePage.verifySearchedResultMessage(expectedData);
		extentTest = extentReports.createTest("Verify Search message presence","This test verifies whether search message present or not");
		extentTest.assignCategory("Simple Search");
		Assert.assertEquals(actualResult, true);
	}
	
//	@Test(dependsOnMethods={"verifySearchedMessageSearchForTitleByISBN"})
	@Test(priority=21)
	@Parameters({"searchByPublisher"})
	public void verifySearchForTitleByPublisher(String searchByPublisherText) throws InterruptedException{
		HomePage homePage = new HomePage(driver);		
		String expectedData = searchByPublisherText;		
		boolean actualResult = homePage.verifySearchedByPublisherResult(expectedData);
		extentTest = extentReports.createTest("Verify Search by Publisher","This test verifies search by Publisher");
		extentTest.assignCategory("Simple Search");
		Assert.assertEquals(actualResult, true);
	}
	
//	@Test(dependsOnMethods={"verifySearchForTitleByPublisher"})
	@Test(priority=22)
	public void verifyRefineButtonPresenceForTitleByPublisher() throws InterruptedException{
		HomePage homePage = new HomePage(driver);
		boolean actualResult = homePage.verifyRefineButtonPresence();
		extentTest = extentReports.createTest("Verify Refine Button Presence","This test verifies Refine button present or not");
		extentTest.assignCategory("Simple Search");
		Assert.assertEquals(actualResult, true);
	}
	
//	@Test(dependsOnMethods={"verifySearchForTitleByPublisher"})
	@Test(priority=23)
	@Parameters({"searchByPublisher"})
	public void verifyKeywordRetentionInSearchBoxForTitleByPublisher(String searchByPublisherText) throws InterruptedException{
		HomePage homePage = new HomePage(driver);
		boolean actualResult = homePage.verifyKeywordRetentionInSearchBox(searchByPublisherText);
		extentTest = extentReports.createTest("Verify Keyword Retention","This test verifies keyword retention in searchbox after search");
		extentTest.assignCategory("Simple Search");
		Assert.assertEquals(actualResult, true);
	}
	
//	@Test(dependsOnMethods={"verifySearchForTitleByPublisher"})
	@Test(priority=24)
	@Parameters({"searchByPublisher"})
	public void verifySearchedMessageSearchForTitleByPublisher(String searchByPublisherText) throws InterruptedException{
		HomePage homePage = new HomePage(driver);		
		String expectedData = searchByPublisherText;		
		boolean actualResult = homePage.verifySearchedResultMessage(expectedData);
		extentTest = extentReports.createTest("Verify Search message presence","This test verifies whether search message present or not");
		extentTest.assignCategory("Simple Search");
		Assert.assertEquals(actualResult, true);
	}
}