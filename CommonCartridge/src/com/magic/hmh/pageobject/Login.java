package com.magic.hmh.pageobject;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.magic.hmh.generic.BaseAction;




public class Login extends BaseAction{	
	
	//Using FindBy for locating elements
	
	@FindBy(xpath="//select[@id='cnty_dd_id']")
	private WebElement country;
	
	@FindBy(xpath="//select[@id='state_dd_id']")
	private WebElement state;
	
	@FindBy(xpath="//select[@id='district_dd_id']")
	private WebElement district;
	
	@FindBy(xpath="//select[@id='school_dd_id']")
	private WebElement school;
	
	@FindBy(xpath="//input[@id='userName']")
	private WebElement userName;
	
	@FindBy(xpath="//input[@id='password']")
	private WebElement password;
	
	@FindBy(xpath="//input[@id='login2']")
	private WebElement logInButton;
	
	@FindBy(xpath="//input[@class='butt_eval']")
	private WebElement evaluatorButton;
	
	@FindBy(xpath="//input[@name='eMail']")
	private WebElement email;	
	
	@FindBy(xpath="//input[@id='continue']")
	private WebElement continueButton;		
	
	@FindBy(xpath="//input[@id='Username']")
	private WebElement hrwUserName;
	
	@FindBy(xpath="//input[@name='password']")
	private WebElement hrwPassword;
	
	@FindBy(xpath="//input[@id='loginSubmitBtn']")
	private WebElement hrwLoginButton;
	
	
	
	
	
	
	public Login(WebDriver driver){		
		PageFactory.initElements(driver, this);
	}
	
	
	
	public WebElement getEvaluatorButton() {	
		return evaluatorButton;		
	}
	
	public WebElement getEmail() {		
		return email;		
	} 
	
	public WebElement getContinueButton() {		
		return continueButton;		
	} 
	
	public WebElement getCountry() {		
		return country;		
	} 
	
	public WebElement getState() {		
		return state;		
	} 
	
	public WebElement getDistrict() {		
		return district;		
	} 
	
	public WebElement getSchool() {		
		return school;		
	} 
	
	public WebElement getUserName() {		
		return userName;		
	} 
		
	
	public WebElement getPassword() {		
		return password;		
	} 
	
	public WebElement getLogInButton() {		
		return logInButton;		
	} 
	
	public WebElement getHrwUserName() {		
		return hrwUserName;		
	} 
	
	public WebElement getHrwPassword() {		
		return hrwPassword;		
	} 
	
	public WebElement getHrwLogInButton() {		
		return hrwLoginButton;		
	} 	
	
	
	
	
	
}
