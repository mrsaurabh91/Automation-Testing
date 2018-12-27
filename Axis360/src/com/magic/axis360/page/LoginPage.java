package com.magic.axis360.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.magic.util.SeleniumUtil;

public class LoginPage {

	public By loginButton = By.xpath("//button[@id='loginBtn']");
	public By mobileLoginButton = By.xpath("//button[@id='loginBtnMobile']");
	private By loginBoxCardIdBox = By.xpath("//div[@class='login-box']//input[@name='LogOnModel_UserName']");
	private By loginBoxLoginButton = By.xpath("//button[contains(text(),'login')]");
	
	int waitInSeconds = 30;
	
	private WebDriver driver;
	
	public LoginPage(WebDriver driver){
		this.driver = driver;
	}
	
	public HomePage loginLibrary(String cardID,String operatingSystem) throws InterruptedException{
		Thread.sleep(10000);
		clickLoginButton(operatingSystem);
		Thread.sleep(10000);
		enterCardID(cardID);
		clickLoginBoxLoginButton();
		return new HomePage(driver);
	}
	
	private void clickLoginButton(String operatingSystem){
		
		if(operatingSystem.equalsIgnoreCase("Windows")) {
			SeleniumUtil.waitForVisibility(driver, loginButton, waitInSeconds).click();	
		}else if(operatingSystem.equalsIgnoreCase("Android")){
			SeleniumUtil.waitForVisibility(driver, mobileLoginButton, waitInSeconds).click();
		}
		/*try {
			SeleniumUtil.waitForVisibility(driver, mobileLoginButton, waitInSeconds).click();	
		}catch (Exception e) {
			// TODO: handle exception
			SeleniumUtil.waitForVisibility(driver, loginButton, waitInSeconds).click();	
		}*/
	}
	
	private void enterCardID(String cardID){
		SeleniumUtil.waitForVisibility(driver, loginBoxCardIdBox, waitInSeconds).sendKeys(cardID);		
	}
	
	private void clickLoginBoxLoginButton(){
		SeleniumUtil.waitForVisibility(driver, loginBoxLoginButton, waitInSeconds).click();
	}
	
}
