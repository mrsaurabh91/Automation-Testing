package com.magic.sussex.canvasDataPour;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;


public class AbstractWebTest {
	
	public static WebDriver driver;
	public static WebDriverWait wait;
	public static Capabilities cap;
	

	
	public static void initTest() throws Exception{
		
		if(System.getProperty("os.name").contains("Linux")) {
			driver = getLinuxDriver();
		}else if(System.getProperty("os.name").contains("Windows")) {
			driver = getWindowsDriver();
		}else {
			System.out.println("Invalid OS");
		}		
		
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
	}
	
	public static WebDriver getLinuxDriver() throws MalformedURLException, Exception{
		if(driver==null) {
			new DesiredCapabilities();
	        URL serverurl = new URL("http://localhost:9515");
	        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
	        driver = new RemoteWebDriver(serverurl,capabilities);
			return driver;
		}else {return driver;}
	}
	
	public static WebDriver getWindowsDriver() throws MalformedURLException, Exception{
		if(driver==null) {
			System.setProperty("webdriver.chrome.driver",".\\Resources\\Drivers\\chromedriver.exe");
			driver = new ChromeDriver();
			return driver;
		}else {return driver;}
	}
	
		
	
	public void tearDown(){
		
		//driver.quit();
	}
	
	

}
