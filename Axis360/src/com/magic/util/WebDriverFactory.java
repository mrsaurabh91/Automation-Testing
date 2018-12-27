package com.magic.util;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.swing.SwingWorker;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class WebDriverFactory {

	private static EnvironmentPropertiesReader configProperty = EnvironmentPropertiesReader.getInstance();
	
	
	
//	static DesiredCapabilities firefoxCapabilities = DesiredCapabilities.firefox();
	
	public static WebDriver getWebDriver(String operatingSystem) throws MalformedURLException{
	
		WebDriver driver = null;
		
//		if(driver == null){
		ChromeOptions chromeOptions;
		DesiredCapabilities chromeCapabilities = DesiredCapabilities.chrome();	
		
			if(operatingSystem.equalsIgnoreCase("Windows")) {
				System.setProperty("webdriver.chrome.driver", "./WebDriverEXE/chromedriver.exe");
				chromeOptions = new ChromeOptions();			
				List<String> list = new ArrayList<String>();
				list.add("start-maximized");
				list.add("disable-infobars");
				chromeOptions.addArguments(list);
				chromeCapabilities = DesiredCapabilities.chrome();
				chromeCapabilities.setCapability(CapabilityType.TAKES_SCREENSHOT, true);
				chromeCapabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
				driver = new ChromeDriver(chromeCapabilities);
				
				
			}else if(operatingSystem.equalsIgnoreCase("Android")){
				chromeCapabilities.setCapability("browserName", "chrome");
				chromeCapabilities.setCapability("version", "4.2.2"); 
				chromeCapabilities.setCapability("deviceName","GT-P5113");
				chromeCapabilities.setCapability("platformName","android");
				chromeCapabilities.setCapability("deviceId", "c16072910d9404f");
				chromeCapabilities.setCapability("platform", "ANDROID");
				driver= new RemoteWebDriver(new URL("http://"+"10.11.3.81"+":4444/wd/hub"),chromeCapabilities);	
			
			}	
			driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
			driver.manage().timeouts().pageLoadTimeout(50, TimeUnit.SECONDS);
			return driver;
			
		/*}else{
			return driver;
		}*/
		
	}
	
		
}
