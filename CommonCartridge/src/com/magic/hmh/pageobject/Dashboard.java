package com.magic.hmh.pageobject;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.magic.hmh.generic.BaseAction;



public class Dashboard  extends BaseAction{
	
	//Using FindBy for locating elements
	
	@FindBy(xpath="//input[@id='searchbox']")
	private WebElement searchBox;
	
	@FindBy(xpath="//a[@class='content-link']")
	private WebElement pmt;
	
	@FindBy(xpath="//div[@class='db-result-time']")
	private WebElement pmtSearchCount;
	
	@FindBy(xpath="//div[@id='browsebtn']/button")
	private WebElement browseBtn;
	
	@FindBy(xpath="//*[text()='Program Structure']")
	private WebElement programeStructure;
	
	
	
	
	
	public Dashboard(WebDriver driver){		
		PageFactory.initElements(driver, this);
	}		
	
	public WebElement getSearchBox() {		
		return searchBox;		
	}	
	
	public WebElement getPmtLink() {		
		return pmt;		
	} 	

	public WebElement getPmtSearchCount() {		
		return pmtSearchCount;		
	}
	
	public WebElement getbrowseBtn() {		
		return browseBtn;		
	}
	
	public WebElement getProgrameStructure() {		
		return programeStructure;		
	}
	
	
	public int searchCount(){
		String str = pmtSearchCount.getText().split(" ")[1];
		int countS = count(str);
		return countS;
	}
	
	public int count(String count){
		return Integer.valueOf(count);
	}	
	
}


