package com.magic.utils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

/**
 * ScreenshotManager to take screenshots 
 *
 */

public class ScreenshotManager {

	private static void captureScreenShot(WebDriver driver,String filePath) throws IOException{
		File f=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(f, new File(filePath));	
	}	
	
	/**
     * takeScreenshot to take screenshots by passing driver and fileName as parameter with
     * date and time
	 * @throws IOException 
     *
     */
    public static void takeScreenshot(WebDriver driver,String fileName) throws IOException {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat( "yyyyMMdd-hhmmss-SSS" );
        String filePath = "screenshots/"+fileName+" "+ sdf.format( cal.getTime() ) + ".jpg";
        captureScreenShot(driver,filePath);
    }
	
}
