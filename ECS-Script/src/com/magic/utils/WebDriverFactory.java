package com.magic.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

public class WebDriverFactory {

	private static EnvironmentPropertiesReader configProperty = EnvironmentPropertiesReader.getInstance();
	
	static WebDriver driver;
	static ChromeOptions chromeOptions;
	static DesiredCapabilities chromeCapabilities = DesiredCapabilities.chrome();
	static DesiredCapabilities firefoxCapabilities = DesiredCapabilities.firefox();
	
	public static WebDriver getWebDriver(){
		
		String browser = null;
		
		if(driver == null){
				System.setProperty("webdriver.chrome.driver", "./driver/chromedriver.exe");
				chromeOptions = new ChromeOptions();			
				List<String> list = new ArrayList<String>();
				list.add("start-maximized");
				list.add("disable-infobars");
				chromeOptions.addArguments(list);
				chromeCapabilities = DesiredCapabilities.chrome();
				chromeCapabilities.setCapability(CapabilityType.TAKES_SCREENSHOT, true);
				chromeCapabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
				driver = new ChromeDriver(chromeCapabilities);
				driver.manage().timeouts().implicitlyWait(120,TimeUnit.SECONDS);
			return driver;
		}else{
			return driver;
		}
	}
}
