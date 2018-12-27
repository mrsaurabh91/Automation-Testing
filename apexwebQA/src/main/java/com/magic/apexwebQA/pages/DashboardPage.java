package com.magic.apexwebQA.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import com.magic.apexwebQA.utils.SeleniumUtil;

public class DashboardPage {
	
	private WebDriver driver;
	private static final int WAIT_SECONDS = Integer.valueOf(SeleniumUtil.fetchUserDetails("GLOBAL_WAIT"));
	private static final By PROFILE_ICON = By.xpath("//button[contains(@class,'user-summary__popover-menu-trigger')]");
	private static final By PROFILE_NAME = By.xpath("(//span[@class='popover-menu__item-label user-summary page-header-user-summary__item-label'])[1]");
	private static final By MENU_TOGGLE_ICON = By.xpath("//div[@class='page-header-nav__menu-toggle']");
	
	
	public DashboardPage (WebDriver driver) {
        this.driver = driver;
	}
	
	public void clickProfileIcon() {
		SeleniumUtil.waitForElementPresence(driver, PROFILE_ICON, WAIT_SECONDS).click();		
	}
	
	public String getProfileName() {
		String profileName = SeleniumUtil.waitForElementPresence(driver, PROFILE_NAME, WAIT_SECONDS).getText();
		return profileName;
	}
	
	public void clickMenu() {
		SeleniumUtil.waitForElementPresence(driver, MENU_TOGGLE_ICON, WAIT_SECONDS).click();
	}
	
	public void selectMenuWithParameter(String menuName) {
		By MENU_WITH_PARAMETER = By.xpath("//span[text()='"+menuName+"']");
		SeleniumUtil.waitForElementPresence(driver, MENU_WITH_PARAMETER, WAIT_SECONDS).click();
	}
}
