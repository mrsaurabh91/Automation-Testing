package com.magic.axis360.common;

import java.net.MalformedURLException;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.magic.axis360.input.InputDetails;
import com.magic.axis360.page.HomePage;
import com.magic.axis360.page.LoginPage;
import com.magic.util.Email;
import com.magic.util.WebDriverFactory;

public class BaseTest{

	protected static long startTime ;
	protected static long endTime ;	
	
	public WebDriver driver;

	public  static ExtentReports extentReports;
	public  static ExtentTest extentTest;
	public  static String reportPath = System.getProperty("user.dir")+"/test-output/ExtentReport.html";
	public  static ExtentHtmlReporter extentHtmlReporter ;	
	
	@BeforeSuite
	public void enterUrl() throws MalformedURLException{
		extentReports = createInstance(reportPath);
		System.out.println("BeforeSuite start");
		startTime = System.currentTimeMillis();
		System.out.println("BeforeSuite after");
		
	}
	
	@AfterClass
	public void closeBrowserAterClassExecuted() throws Exception{	
		driver.quit();
		
	}
	
	
	@AfterSuite
	public void closeBrowser() throws Exception{		
//		Email sendEmail = new Email();
//		sendEmail.screenShotsOfReport();
		
		driver.quit();
		endTime =  System.currentTimeMillis();
//		extent.flush();
		
//		sendEmail.EmailSend();	
	}
	
	public ExtentReports createInstance(String fileName) {
		
		extentHtmlReporter = new ExtentHtmlReporter(fileName);
		extentHtmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
		extentHtmlReporter.config().setChartVisibilityOnOpen(true);
		extentHtmlReporter.config().setTheme(Theme.STANDARD);
		extentHtmlReporter.config().setDocumentTitle("MAGIC SOFTWARE AUTOMATION REPORT");
		extentHtmlReporter.config().setEncoding("utf-8");
		extentHtmlReporter.config().setReportName("MAGIC-B&T AUTOMATION REPORT");

		extentReports = new ExtentReports();
		extentReports.setSystemInfo("OS Name:", System.getProperty("os.name"));
		extentReports.setSystemInfo("OS Version:", System.getProperty("os.version"));
		extentReports.setSystemInfo("User Name", System.getProperty("user.name"));
		extentReports.attachReporter(extentHtmlReporter);

		return extentReports;
	}
	
	public void loginApplication(String operatingSystem) throws InterruptedException, MalformedURLException {
		driver = WebDriverFactory.getWebDriver(operatingSystem);
		HomePage homePage =  null;
		InputDetails inputDetails = new InputDetails();
		driver.get(inputDetails.getUrl());
		LoginPage loginPage = new LoginPage(driver);		
		homePage = loginPage.loginLibrary(inputDetails.getLibraryCard(),operatingSystem);
		homePage.verifyLogoutButton();
		
	}
	
}