package com.magic.sussex.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.magic.util.SeleniumUtil;

public class PagesIcon {

	
	int waitInSeconds = 180;
	private By VIEWALLPAGES = By.xpath("//*[contains(text(),'View all pages')]");
	private By ADDPAGE = By.xpath("//div[@class='header-bar']//*[contains(text(),'Page')]");
	
	
	
	WebDriver driver = null;
	
	public PagesIcon(WebDriver driver) {
		// TODO Auto-generated constructor stub
		this.driver = driver;
	}
	
	public void clickViewAllPages(){
		SeleniumUtil.waitForElementPresence(driver, VIEWALLPAGES, waitInSeconds).click();
		
	}
	
	public void clickAddPages(){
		SeleniumUtil.waitForElementPresence(driver, ADDPAGE, waitInSeconds).click();
	}
	
	public void createHTMLPage(String pageName,String HTMLCode){		
		SeleniumUtil.waitForElementPresence(driver, By.xpath("//input[@name='title']"), waitInSeconds).sendKeys(pageName);
		SeleniumUtil.waitForElementPresence(driver, By.xpath("//a[contains(text(),'HTML Editor')]"), waitInSeconds).click();
		SeleniumUtil.waitForElementPresence(driver, By.xpath("//textarea[@id='wiki_page_body']"), waitInSeconds).sendKeys(HTMLCode);
		SeleniumUtil.waitForElementPresence(driver, By.xpath("//button[text()='Save']"), waitInSeconds).click();
//		SeleniumUtil.waitForElementPresence(driver, By.xpath("//button[text()='Save & publish']"), waitInSeconds).click();
	}
}
