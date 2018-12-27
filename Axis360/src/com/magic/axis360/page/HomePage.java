package com.magic.axis360.page;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.magic.util.Log;
import com.magic.util.SeleniumUtil;

public class HomePage {

	public WebDriver driver;

	private final By welcomeUser = By.xpath("//div[@class='account-header-toprow']//span[contains(text(),'Welcome')]");
	private final By logoutButton = By.xpath("//button[@id='btnLogout']");
	private final By mobileLogoutButton = By.xpath("//button[@id='btnLogoutMobile']");
	public final By searchBox = By.xpath("//input[@id='txtSearch']");
	private final By searchedBookResult = By.xpath("(//div[@role='listbox']/div)");
	private final By searchedBookTitleResult = By.xpath("(//div[@role='listbox']/div)//h3");
	private final By searchButton = By.xpath("//button[@id='btnSearch']");
	private final By advancedSearch = By.xpath("//button[@id='advanceSearchLink']/*");
	private final By searchByTitle = By.xpath("//input[@value='TITLE']");
	private final By searchByAuthor = By.xpath("//input[@value='AUTHOR']");
	private final By searchByISBN = By.xpath("//input[@value='ISBN']");
	private final By searchedAuthorResult = By.xpath("//div[@role='listbox']//span[contains(@class,'AuthorName')]");
	private final By loadMoreButton = By.xpath("//Button[@id='btnLoadMore_Search']");
	private final By searchMessage = By.xpath("//div[contains(@class,'breadcrumb-search')]//span[contains(@class,'text-truncate')]");
	private final By ISBNNumber = By.xpath("//div[@role='listbox']//div[contains(@class,'	')]");
	private final By refineButton = By.xpath("//button[@id='refinerBtn']");
	private final By searchContainer = By.xpath("//div[contains(@class,'search-container')]");
	private final By advancedSearchType = By.xpath("//div[@id='dvSearchBar']//*[@id='advanceSearchRadioGroup']//label");
	private final By advanceSearchOption = By.xpath("//div[@class='search-bar-width container-fullsearchbar']");
	private final By allRadioButtonAtAdvanceSearch = By.xpath("//input[@value='ALL']");
	private final By closeButtonAtAdvanceSearchOption = By.xpath("//button[@id='advanceSearchCloseBtn']");

	private int waitInSeconds = 30;

	public HomePage(WebDriver driver){
		this.driver = driver;
	}

	public boolean verifyLogoutButton(){
		try {
			//			if(SeleniumUtil.waitForElementPresence(driver, mobileLogoutButton, waitInSeconds).isDisplayed()) 
			//			{
			//				return true;
			//			}
			//			else 
			//			{
			//				return false;
			//			}
			driver.findElement(mobileLogoutButton);
			return true;
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
			// TODO: handle exception
			try {
				//				if(SeleniumUtil.waitForElementPresence(driver, logoutButton, waitInSeconds).isDisplayed()) {
				//					return true;
				//				}else {
				//					return false;
				//				}
				driver.findElement(logoutButton);
				return true;
			}
			catch (Exception e1) 
			{
				e1.printStackTrace();
				// TODO: handle exception
				return false;
			}
			//		}
			//		try
			//		{
			//			Thread.sleep(15000);
			//		driver.findElement(logoutButton).click();
			//		return true;
			//		}
			//		catch (Exception e) {
			//			return false;
			//			// TODO: handle exception
			//		}

		}

		public String getUserDisplayName(){
			return SeleniumUtil.waitForElementPresence(driver, welcomeUser, waitInSeconds).getText().trim();
		}

		public LoginPage clickLogOutButton(){
			SeleniumUtil.waitForElementPresence(driver, logoutButton, waitInSeconds).click();
			return new LoginPage(driver);
		}

		public boolean getSearchBox(){
			try{
				SeleniumUtil.waitForElementPresence(driver, searchBox, waitInSeconds);
				Log.info("Search box present");
				return true;
			}catch (Exception e) {
				Log.info("Search box not present");
				// TODO: handle exception
			}
			return false;
		}

		public boolean verifySearchedByKeywordResult(String expectedData,String searchType) throws InterruptedException{	
			SeleniumUtil.waitForElementPresence(driver, searchBox, waitInSeconds).clear();
			if(searchType.equalsIgnoreCase("advance")){
				//			SeleniumUtil.waitForElementPresence(driver, advancedSearch, waitInSeconds).click();
			}
			SeleniumUtil.waitForElementPresence(driver, searchBox, waitInSeconds).sendKeys(expectedData);
			SeleniumUtil.waitForElementPresence(driver, searchButton, waitInSeconds).click();
			SeleniumUtil.waitForElementPresence(driver, searchedBookTitleResult, waitInSeconds);
			List<WebElement> searchedResultList = driver.findElements(searchedBookTitleResult);		
			for(int searchedListCount = 0; searchedListCount<searchedResultList.size(); searchedListCount++){
				String searchedBoolTitle = "";
				searchedBoolTitle = searchedResultList.get(searchedListCount).getText();
				expectedData = expectedData.toLowerCase();
				searchedBoolTitle = searchedBoolTitle.toLowerCase();
				if(!searchedBoolTitle.contains(expectedData)){
					Log.info(expectedData + " not found in " +searchedBoolTitle);
					return false;
				}
			}
			Log.info("Keyword Search Successful");
			return true;
		}

		public boolean verifySearchedByTitleResult(String expectedData,String searchType) throws InterruptedException{	
			SeleniumUtil.waitForElementPresence(driver, searchBox, waitInSeconds).clear();
			if(searchType.equalsIgnoreCase("advance")){
				SeleniumUtil.waitForElementPresence(driver, advancedSearch, waitInSeconds).click();
				//			SeleniumUtil.waitForElementPresence(driver, searchByTitle, waitInSeconds).sendKeys("TITLE");
				JavascriptExecutor executor = (JavascriptExecutor)driver;
				executor.executeScript("arguments[0].click();", driver.findElement(searchByTitle));
			}
			SeleniumUtil.waitForElementPresence(driver, searchBox, waitInSeconds).sendKeys(expectedData);
			SeleniumUtil.waitForElementPresence(driver, searchButton, waitInSeconds).click();
			SeleniumUtil.waitForElementPresence(driver, searchedBookTitleResult, waitInSeconds);
			List<WebElement> searchedResultList = driver.findElements(searchedBookTitleResult);		
			for(int searchedListCount = 0; searchedListCount<searchedResultList.size(); searchedListCount++){
				String searchedBoolTitle = "";
				searchedBoolTitle = searchedResultList.get(searchedListCount).getText();
				expectedData = expectedData.toLowerCase();
				searchedBoolTitle = searchedBoolTitle.toLowerCase();
				if(!searchedBoolTitle.contains(expectedData)){
					Log.info(expectedData + " not found in " +searchedBoolTitle);
					return false;
				}
			}
			Log.info("Title Search Successful");
			return true;
		}

		public boolean verifySearchedByAuthorResult(String expectedData,String searchType) throws InterruptedException{	
			SeleniumUtil.waitForElementPresence(driver, searchBox, waitInSeconds).clear();
			if(searchType.equalsIgnoreCase("advance")){
				SeleniumUtil.waitForElementPresence(driver, advancedSearch, waitInSeconds).click();
				//			SeleniumUtil.waitForElementPresence(driver, searchByAuthor, waitInSeconds).click();
				JavascriptExecutor executor = (JavascriptExecutor)driver;
				executor.executeScript("arguments[0].click();", driver.findElement(searchByAuthor));
			}

			SeleniumUtil.waitForElementPresence(driver, searchBox, waitInSeconds).sendKeys(expectedData);
			SeleniumUtil.waitForElementPresence(driver, searchButton, waitInSeconds).click();
			SeleniumUtil.waitForElementPresence(driver, searchedAuthorResult, waitInSeconds);
			List<WebElement> searchedResultList = driver.findElements(searchedAuthorResult);		
			for(int searchedListCount = 0; searchedListCount<searchedResultList.size(); searchedListCount++){
				String searchedBoolTitle = "";
				searchedBoolTitle = searchedResultList.get(searchedListCount).getText();
				expectedData = expectedData.toLowerCase();
				searchedBoolTitle = searchedBoolTitle.toLowerCase();
				if(!searchedBoolTitle.contains(expectedData)){
					Log.info(expectedData + " not found in " +searchedBoolTitle);
					return false;
				}
			}
			Log.info("Author Search Successful");
			return true;
		}

		public boolean verifySearchedByISBNResult(String expectedData,String searchType) throws InterruptedException{	
			SeleniumUtil.waitForElementPresence(driver, searchBox, waitInSeconds).clear();
			if(searchType.equalsIgnoreCase("advance")){
				SeleniumUtil.waitForElementPresence(driver, advancedSearch, waitInSeconds).click();
				//			SeleniumUtil.waitForElementPresence(driver, searchByTitle, waitInSeconds).click();
				JavascriptExecutor executor = (JavascriptExecutor)driver;
				executor.executeScript("arguments[0].click();", driver.findElement(searchByISBN));
			}
			SeleniumUtil.waitForElementPresence(driver, searchBox, waitInSeconds).sendKeys(expectedData);
			SeleniumUtil.waitForElementPresence(driver, searchButton, waitInSeconds).click();
			SeleniumUtil.waitForElementPresence(driver, searchedBookTitleResult, waitInSeconds);
			List<WebElement> searchedResultList = driver.findElements(ISBNNumber);		
			for(int searchedListCount = 0; searchedListCount<searchedResultList.size(); searchedListCount++){
				String searchedBoolTitle = "";
				searchedBoolTitle = searchedResultList.get(searchedListCount).getAttribute("data-isbn");
				expectedData = expectedData.toLowerCase();
				searchedBoolTitle = searchedBoolTitle.toLowerCase();
				if(!searchedBoolTitle.contains(expectedData)){
					Log.info(expectedData + " not found in " +searchedBoolTitle);
					return false;
				}
			}
			Log.info("ISBN Search Successful");
			return true;
		}

		public boolean verifySearchedByPublisherResult(String expectedData) throws InterruptedException{	
			SeleniumUtil.waitForElementPresence(driver, searchBox, waitInSeconds).clear();
			//		SeleniumUtil.waitForElementPresence(driver, advancedSearch, waitInSeconds).click();
			//		SeleniumUtil.waitForElementPresence(driver, searchByAuthor, waitInSeconds).click();
			SeleniumUtil.waitForElementPresence(driver, searchBox, waitInSeconds).sendKeys(expectedData);
			SeleniumUtil.waitForElementPresence(driver, searchButton, waitInSeconds).click();
			SeleniumUtil.waitForElementPresence(driver, searchContainer, waitInSeconds);
			String searchedResult = driver.findElement(searchContainer).getAttribute("data-searchterm");		
			searchedResult = searchedResult.toLowerCase();

			if(searchedResult.equalsIgnoreCase(expectedData)){
				Log.info("Search Successful");
				return true;
			}else{
				Log.info(expectedData + " is not equal to " +searchedResult);
				return false;
			}
		}


		private int countOfSearchedBook(){

			try{
				Thread.sleep(5000);
				SeleniumUtil.scrollDownViewElement(driver, loadMoreButton);
				while(SeleniumUtil.waitForElementPresence(driver, loadMoreButton, waitInSeconds).isDisplayed()){
					Thread.sleep(3000);
					SeleniumUtil.scrollDownViewElement(driver, loadMoreButton);
					SeleniumUtil.waitForElementPresence(driver, loadMoreButton, waitInSeconds).click();
					Thread.sleep(3000);
				}			
			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}		
			SeleniumUtil.waitForElementPresence(driver, searchedBookResult, waitInSeconds);		
			List<WebElement> searchedResultList = driver.findElements(searchedBookResult);
			return searchedResultList.size();
		}

		public boolean verifySearchedResultMessage(String searchText){

			int countOfSearchedBook = countOfSearchedBook();		
			String expectedMessage = " results for \""+searchText+"\"";
			expectedMessage = String.valueOf(countOfSearchedBook)+expectedMessage;
			String acutalResult = "";
			acutalResult = SeleniumUtil.waitForVisibility(driver, searchMessage, waitInSeconds).getText().trim();
			//	acutalResult = SeleniumUtil.waitForElementPresence(driver, searchMessage, waitInSeconds).getText().trim();
			if(expectedMessage.equalsIgnoreCase(acutalResult)){
				Log.info("Search result message present");
				return true;
			}else{
				Log.info("Search result message either missing or incorrect");
				return false;
			}
		}

		public boolean checkAdvanceSearchAppear(){		
			try{

				if(SeleniumUtil.waitForElementPresence(driver, advancedSearch, waitInSeconds).isDisplayed()) {
					Log.info("Advanced Search present");
					return true;
				}else {
					return false;
				}
			}catch (Exception e) {
				// TODO: handle exception
				Log.info("Advanced Search not present");
				return false;
			}
		}

		public boolean checkRadioButtonWithText(String searchType,String clickAdvanceSearch){		
			if(clickAdvanceSearch.equalsIgnoreCase("yes")){
				SeleniumUtil.waitForElementPresence(driver, advancedSearch, waitInSeconds).click();
			}
			List<WebElement> advanvceSearchCategoryList = driver.findElements(advancedSearchType);

			for(int advanceSearchCategoryCount = 0; advanceSearchCategoryCount<advanvceSearchCategoryList.size(); advanceSearchCategoryCount++){
				String text = "";
				text = advanvceSearchCategoryList.get(advanceSearchCategoryCount).getText();
				text = text.toLowerCase().trim();
				searchType  = searchType.toLowerCase().trim();
				if(text.equals(searchType)){
					Log.info(searchType+ " present");
					return true;
				}
			}
			Log.info(searchType+ " not present");
			return false;
		}

		public boolean verifyRefineButtonPresence(){
			try{
				SeleniumUtil.waitForElementPresence(driver, refineButton, waitInSeconds);
				Log.info("Refine button present");
				return true;
			}catch (Exception e) {
				// TODO: handle exception
			}
			Log.info("Refine button not present");
			return false;		
		}

		public boolean verifyKeywordRetentionInSearchBox(String expectedData) {
			String actualData = SeleniumUtil.waitForElementPresence(driver, searchBox, waitInSeconds).getAttribute("value").toLowerCase();
			if(actualData.equals(expectedData.toLowerCase())) {
				Log.info(expectedData+ " retained in searchbox");
				return true;
			}
			else {
				Log.info(expectedData+ " not retained in searchbox");
				return false;
			}
		}

		public boolean checkAdvanceSearchOptionAppear(){

			try{
				Thread.sleep(5000);
				SeleniumUtil.waitForElementPresence(driver, advancedSearch, waitInSeconds).click();
				if(SeleniumUtil.waitForElementPresence(driver, advanceSearchOption, waitInSeconds).isDisplayed()){
					Log.info("Advanced Search options present");
					return true;
				}else{
					Log.info("Advanced Search options not present");
					return false;	
				}
			}catch (Exception e) {
				// TODO: handle exception
			}
			Log.info("Advanced Search options not present");
			return false;

		}

		public String checkRadioButtonSelected(){

			String actualRestul = SeleniumUtil.waitForElementPresence(driver, allRadioButtonAtAdvanceSearch, waitInSeconds).getAttribute("checked");
			Log.info("'All' selected by default");
			return actualRestul;
		}

		public boolean checkCloseButtonCloseAdvanceButtonSearchOption() throws InterruptedException{

			Thread.sleep(5000);
			SeleniumUtil.waitForElementPresence(driver, advancedSearch, waitInSeconds).click();
			Thread.sleep(2000);
			SeleniumUtil.waitForElementPresence(driver, closeButtonAtAdvanceSearchOption, waitInSeconds).click();
			Thread.sleep(5000);
			try{

				if(SeleniumUtil.waitForElementPresence(driver, advanceSearchOption, waitInSeconds).isDisplayed()){
					Log.info("Advanced search not closed");
					return false;
				}else{
					return true;
				}
			}catch (Exception e) {
				// TODO: handle exception
				Log.info("Advanced search closed");
				e.printStackTrace();
				return true;
			}
		}

	}
