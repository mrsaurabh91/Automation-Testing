package com.magic.util;

import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingWorker;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.magic.sussex.gui.GUI;

public class WebDriverFactory {

	private static EnvironmentPropertiesReader configProperty = EnvironmentPropertiesReader.getInstance();
	
	public static WebDriver driver;
	static ChromeOptions chromeOptions;
	static DesiredCapabilities chromeCapabilities = DesiredCapabilities.chrome();
	static DesiredCapabilities firefoxCapabilities = DesiredCapabilities.firefox();
	
	public static WebDriver getWebDriver(){
		GUI.logger.info("Going to launch Web Browser.");
		GUI.consoleText.append("Going to launch Web Browser.\n");
		
		if(driver == null){
				System.setProperty("webdriver.chrome.driver", "./WebDriverEXE/chromedriver.exe");
				chromeOptions = new ChromeOptions();			
				List<String> list = new ArrayList<String>();
				list.add("start-maximized");
				list.add("disable-infobars");
				list.add("--headless");
				chromeOptions.addArguments(list);
				chromeCapabilities = DesiredCapabilities.chrome();
				chromeCapabilities.setCapability(CapabilityType.TAKES_SCREENSHOT, true);
				chromeCapabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
				driver = new ChromeDriver(chromeCapabilities);
				GUI.logger.info("Chrome Web Browser launched.");		
				GUI.consoleText.append("Chrome Web Browser launched.\n");
			return driver;
		}else{
			return driver;
		}
	}
	
	public static void tearDown(){
		driver.quit();
		
	}

	
	
	
}
