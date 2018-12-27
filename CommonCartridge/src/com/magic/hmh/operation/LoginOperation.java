package com.magic.hmh.operation;

import java.io.IOException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.magic.hmh.dao.InputDetails;
import com.magic.hmh.pageobject.Login;
import com.magic.hmh.testscript.TestScript;

public class LoginOperation {
	
	private TestScript testScript;
	private WebDriver dvr;
	
	
	public LoginOperation(TestScript testScript,WebDriver dvr) {
		// TODO Auto-generated constructor stub
		this.testScript = testScript;
		this.dvr = dvr;
	}
		
	// User Login according to input Sheet
	
	public void TCKLogin() throws InterruptedException, IOException{	
		int inputRow = 1; int inputCol = 2;
		
		String hrwUrlContain = "hrw";
		String tcUrlContain = "thinkcentral";
	
	
	//	System.out.println("h"+dvr);
		Login login = new Login(dvr);
//		System.out.println(login);
		InputDetails inputDetails = new InputDetails();
		String urlInSheet = inputDetails.getInputURL();
//		System.out.println(urlInSheet);
		
		// URL Entered in Browser
		testScript.enterURL(urlInSheet);
		Thread.sleep(3000);
		
		// Get URL from Browser		
		String browserUrl = dvr.getCurrentUrl();
		
			if(browserUrl.contains(hrwUrlContain)){
				
				int hrwUNameRow = 5;
				int hrwPasswordRow = 6;
			
			// Login through HRW
			
			String inputHrwUName =inputDetails.getUserName();
			String inputHhrwPassword = inputDetails.getPassword();
			
			WebElement hrwUserName = login.getHrwUserName();
			login.enterTextInTextBox(hrwUserName, inputHrwUName);
			
			
			WebElement hrwUserPassword = login.getHrwPassword();
			login.enterTextInTextBox(hrwUserPassword,inputHhrwPassword);
			
			WebElement checkLogin =login.getHrwLogInButton();
			login.click(checkLogin);
			
		}

			else if(browserUrl.contains(tcUrlContain)){
			
			// Login through Think Central	

			
			
			String tcEnvironment = inputDetails.evalOrProd();
			
			if(tcEnvironment.equalsIgnoreCase("prod")){
				int prodCountryNameRow = 10;
				int prodStateNameRow = 11;
				int prodDistrictNameRow = 12;
				int prodSchoolNameRow = 13;
				int prodUserNameRow = 14;
				int prodPasswordRow =15;
				
				
				String prodCountryNameInSheet = login.readExcel(prodCountryNameRow,inputCol);
				String prodStateNameInSheet = login.readExcel(prodStateNameRow,inputCol);
				String prodDistrictNameInSheet = login.readExcel(prodDistrictNameRow,inputCol);
				String prodSchoolNameInSheet = login.readExcel(prodSchoolNameRow,inputCol);
				String prodUserNameInSheet = login.readExcel(prodUserNameRow,inputCol);
				String prodPasswordInSheet = login.readExcel(prodPasswordRow,inputCol);
				
				
				WebElement countryname =login.getCountry();
				login.selectOption(countryname, prodCountryNameInSheet);
				testScript.threadSleep(2);
				WebElement statename =login.getState();
				login.selectOption(statename, prodStateNameInSheet);
				testScript.threadSleep(2);
				WebElement districtName =login.getDistrict();			
				login.selectOption(districtName, prodDistrictNameInSheet);
				testScript.threadSleep(2);
				WebElement schoolName =login.getSchool();
				login.selectOption(schoolName, prodSchoolNameInSheet);
				testScript.threadSleep(2);
				WebElement userName =login.getUserName();
				login.enterTextInTextBox(userName, prodUserNameInSheet);
				testScript.threadSleep(2);
				WebElement password =login.getPassword();
				login.enterTextInTextBox(password, prodPasswordInSheet);
				testScript.threadSleep(2);
				WebElement checkLogin =login.getLogInButton();
				login.click(checkLogin);
				
				
			}else if(tcEnvironment.equalsIgnoreCase("eval")){
				
				/*int evalEmailRow = 18;
				int evalEvalutionSitesRow = 19;
				*/
				String evalEmailInSheet = inputDetails.evalEMail();
				String evalutionSites = inputDetails.evalSite();
				WebElement eval = login.getEvaluatorButton();			
						
				login.click(eval);				
								
				WebElement emailText = login.getEmail();								
				login.enterTextInTextBox(emailText,evalEmailInSheet);				
				WebElement contBtn = login.getContinueButton();						
				login.click(contBtn);
				System.out.println("cont clicked");
				testScript.threadSleep(3);
				String logInButtonXpath =  "//td[text()='"+evalutionSites+"']/following-sibling::td/input[@value='Log In']";
				testScript.scrolltoviewelement(logInButtonXpath);
				
				WebElement log = dvr.findElement(By.xpath(logInButtonXpath));
				login.click(log);
							
				testScript.switchwindow(1);				
				
				System.out.println("eval done");
			}
		}else{
			System.out.println("Please provide correct URl");
		}				
	}

	// Thread Sleep
	
	
	public void brainhoneyLogin() throws InterruptedException{
		
		InputDetails inputDetails = new InputDetails();
		
		String url = inputDetails.geturl();
		dvr.get(url);
		testScript.waitForVisibility(dvr.findElement(By.xpath("//input[@id='username']")));
		
		String userName = inputDetails.getBrainhoneyUserName();
		String password = inputDetails.getBrainhoneyPassword();
		System.out.println(userName);
		System.out.println(password);
		
		
		
		dvr.findElement(By.xpath("//input[@id='username']")).sendKeys(userName);
		Thread.sleep(2000);
		dvr.findElement(By.xpath("//input[@name='password']")).sendKeys(password);
		Thread.sleep(2000);
		dvr.findElement(By.xpath("//input[@id='localsubmit']")).click();
		
		
		
		
		
	}
	
	
}
