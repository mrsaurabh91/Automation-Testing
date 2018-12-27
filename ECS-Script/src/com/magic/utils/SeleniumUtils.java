package com.magic.utils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;
import java.time.Instant;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * SeleniumUtils class consists wait for ElementPresence,ElementClickable,,ElementViseble with user defined max time
 * This class is also used for getting the response code of URL , getting the count of Window, switch to the different window 
 * and is used globally in all classes and methods
 *
 */

public class SeleniumUtils {

	public static final WebElement waitForElementPresence(WebDriver driver, By findByCondition, int waitInSeconds) {
        waitIgnoringStale(driver, waitInSeconds).until(ExpectedConditions.presenceOfElementLocated(findByCondition));
        return driver.findElement(findByCondition);
    }
	
	public static WebElement waitForClickable(WebDriver driver,By findByCondition,int waitInSeconds){
		waitIgnoringStale(driver, waitInSeconds).until(ExpectedConditions.elementToBeClickable(findByCondition));
		return driver.findElement(findByCondition);		
	}
	
	public static WebElement waitForVisibility(WebDriver driver,By findByCondition,int waitInSeconds){
   	 waitIgnoringStale(driver, waitInSeconds).until(ExpectedConditions.visibilityOf(driver.findElement(findByCondition)));
   	 return driver.findElement(findByCondition);
	}

  /*  public static void turnOffImplicitWaits(WebDriver driver,int waitInSeconds) {
        driver.manage().timeouts().implicitlyWait(waitInSeconds, TimeUnit.SECONDS);
    }
    
    public static void changeImplicitWaits(WebDriver driver,int waitInSeconds) {
        driver.manage().timeouts().implicitlyWait(waitInSeconds, TimeUnit.SECONDS);
    }*/

    public static final WebElement scrollDownViewElement(WebDriver driver, By findByCondition){
        WebElement element = driver.findElement(findByCondition);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        return driver.findElement(findByCondition);
    }
    
    public static final WebElement scrollUpViewElement(WebDriver driver, By findByCondition){
    	WebElement element = driver.findElement(findByCondition);
		JavascriptExecutor js = ((JavascriptExecutor) driver);
		js.executeScript("scroll(0,-2000);",element);
		return driver.findElement(findByCondition);
	}
    
    public static FluentWait<WebDriver> waitIgnoringStale(WebDriver driver,int waitInSeconds) {
        return new WebDriverWait(driver, waitInSeconds).ignoring(StaleElementReferenceException.class).pollingEvery(500,TimeUnit.MILLISECONDS);
    }    
		
	public static void mouseHover(WebDriver driver,By element) throws InterruptedException, IOException{
		Actions act=new Actions(driver);
		act.moveToElement(driver.findElement(element)).perform();
	}

	public static int responsecode(String appurl) throws IOException{
		URL url = new URL(appurl);
		HttpURLConnection connection = (HttpURLConnection)url.openConnection();
		connection.setRequestMethod("GET");
		connection.connect();
		int code = connection.getResponseCode();
		String codevalue = Integer.toString(code);
		return code;
	}

	public static int countWindow(WebDriver driver){
		Set<String> windowHandle = driver.getWindowHandles();
		int count = windowHandle.size();		
		return count;		
	}
	
	public static void switchwindow(WebDriver driver,int windowno){		
		Set<String> window=driver.getWindowHandles();
		driver.switchTo().window(window.toArray()[windowno].toString());		
	}
	
	public static Instant getTime(){
		Instant time = Instant.now();
		return time;
	}
	
	public static Duration getTimeDifference(Instant start,Instant end){
		Duration timeElapsed = Duration.between(start, end);
		return timeElapsed;
	}
    
	public static void tearDown(WebDriver driver) throws IOException, InterruptedException{			
		driver.quit();
	}
	
}
