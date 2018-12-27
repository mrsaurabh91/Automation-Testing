package com.magic.apexwebQA.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

import com.magic.apexwebQA.utils.SeleniumUtil;

public class LoginPage {
	
	private WebDriver driver;
	
	private static final int WAIT_SECONDS = Integer.valueOf(SeleniumUtil.fetchUserDetails("GLOBAL_WAIT"));
	private static final By USERNAME_TEXT_FIELD = By.name("username");
	private static final By PASSWORD_TEXT_FIELD = By.name("password");
	private static final By LOGIN_BUTTON = By.xpath("//button[text()='Log In']");
		
	public LoginPage (WebDriver driver) {
        this.driver = driver;
	}
	
	public void clickLogin() {
        driver.findElement(LOGIN_BUTTON).click();
    }

    public void typeUserName(String username) {
        SeleniumUtil.waitForElementPresence(driver, USERNAME_TEXT_FIELD, WAIT_SECONDS).sendKeys(username);
    }

    public void typePassword(String password) {
        SeleniumUtil.waitForElementPresence(driver, PASSWORD_TEXT_FIELD, WAIT_SECONDS).sendKeys(password);
    }
    
    public void selectDropDown(String value) {
    	Select dropdown = new Select(driver.findElement(By.name("tradingServer")));
    	dropdown.selectByVisibleText(value);
    }

    public void loginUser(String username, String password, String server) {
        
        SeleniumUtil.turnOffImplicitWaits(driver);
        
        try {
        	
        	if(driver.findElement(By.name("tradingServer")).isDisplayed()) {
        		selectDropDown(server);
        	}
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("SERVER SELECTION NOT FOUND OR VALUE IS NOT GIVEN IN PROPERTIES FILE");
		}finally {
			SeleniumUtil.turnOnImplicitWaits(driver);
		}
        
        typeUserName(username);
        typePassword(password);
                
        clickLogin();
    }

}
