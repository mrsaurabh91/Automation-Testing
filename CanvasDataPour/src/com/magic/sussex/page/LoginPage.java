package com.magic.sussex.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.magic.sussex.gui.GUI;
import com.magic.util.EnvironmentPropertiesReader;
import com.magic.util.SeleniumUtil;
import com.magic.util.WebDriverFactory;

public class LoginPage {

	
	private final By USERNAME = By.xpath("//input[@id='userNameInput']");  
	private final By PASSWORD = By.xpath("//input[@id='passwordInput']");  
	private final By SIGNINBUTTON = By.xpath("//*[@id='submitButton']");
	
	private int waitInSeconds = 180;
	
	
	WebDriver driver = null;
	
	public LoginPage(WebDriver driver) {
		// TODO Auto-generated constructor stub
		this.driver = driver;
	}
	
	
	public void getDashboard(String userName,String password){
		enterUserName(userName);
		enterPassword(password);
		clickSignInButton();
	}
	
	private void enterUserName(String userName){
		try{
			SeleniumUtil.waitForElementPresence(driver, USERNAME, waitInSeconds).sendKeys(userName);
		}catch(Exception e){
			 GUI.logger.info("User Name input field not found in canvas web application.");
			 GUI.consoleText.append("User Name input field not found in canvas web application.\n");
		}
	}
	
	private void enterPassword(String password){
		try{
			SeleniumUtil.waitForElementPresence(driver, PASSWORD, waitInSeconds).sendKeys(password);
		}catch(Exception e){
			GUI.logger.info("Password input field not found in canvas web application.");
			GUI.consoleText.append("Password input field not found in canvas web application.\n");			
		}
		
	}
	
	private void clickSignInButton(){
		try{
			SeleniumUtil.waitForElementPresence(driver, SIGNINBUTTON, waitInSeconds).click();
		}catch(Exception e){
			GUI.logger.info("Sign In Button not found.");
			GUI.consoleText.append("Sign In Button not found.\n");
		}
	}
}
