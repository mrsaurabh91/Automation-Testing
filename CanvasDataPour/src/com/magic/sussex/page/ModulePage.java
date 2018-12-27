package com.magic.sussex.page;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.magic.util.EnvironmentPropertiesReader;
import com.magic.util.SeleniumUtil;

public class ModulePage {

	private static EnvironmentPropertiesReader configProperty = EnvironmentPropertiesReader.getInstance();
	
	int waitInSeconds = 180;
	
	private final By MODULEICON = By.xpath("//*[@id='global_nav_courses_link']");
	private final By DASHBOARDICON = By.xpath("//*[@id='menu']//div[contains(text(),'Dashboard')]");
	private final By ACCOUNTICON = By.xpath("//*[@id='menu']//div[contains(text(),'Account')]");
	private final By PAGES  = By.xpath("//div[@id='left-side']//*[contains(text(),'Pages')]");
	private final By FILES = By.xpath("//div[@id='left-side']//*[contains(text(),'Files')]");
	
	WebDriver driver = null;
	
	public ModulePage(WebDriver driver) {
		// TODO Auto-generated constructor stub
		this.driver = driver;
	}	
	
	private void clickModuleIcon(){			
		SeleniumUtil.waitForElementPresence(driver, MODULEICON, waitInSeconds).click();
//		SeleniumUtil.waitForElementClickable(driver, MODULEICON, waitInSeconds).click();
	}
	
	public boolean clickModule(String moduleName){
		
		try{
			clickModuleIcon();
			SeleniumUtil.waitForElementPresence(driver, By.xpath("//div[contains(@class,'tray-with-space-for-global-nav')]//ul//a"), waitInSeconds);
			List<WebElement> listOfCources = driver.findElements(By.xpath("//div[contains(@class,'tray-with-space-for-global-nav')]//ul//a"));
			
			for(int i = 0;i<listOfCources.size();i++){
				String courseName = "";
				courseName = listOfCources.get(i).getText().trim();
				if(moduleName.equalsIgnoreCase(courseName)){
					listOfCources.get(i).click();
					return true;
				}
			}
		}catch (Exception e) {
			// TODO: handle exception
		}
		return false;		
		
//		SeleniumUtil.waitForElementPresence(driver, By.xpath("//*[@class='_6q8Mxga']//span[contains(text(),'"+moduleName+"')]"), waitInSeconds).click();
	}
	
	public void clickPages(){		
		SeleniumUtil.waitForElementPresence(driver, PAGES, waitInSeconds).click();
	}
	
	public void clickFiles(){		
		SeleniumUtil.waitForElementPresence(driver, FILES, waitInSeconds).click();
	}
}
