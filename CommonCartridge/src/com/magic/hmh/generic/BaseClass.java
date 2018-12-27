package com.magic.hmh.generic;

import java.awt.AWTException;
//import java.awt.Robot;
//import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
//import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
//import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
//import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import com.magic.hmh.dao.InputDetails;

//import org.testng.annotations.BeforeMethod;
//import org.testng.annotations.Test;

public class BaseClass {
	
	
	static public File file;
	static public FileInputStream fileStream ;
	static public FileOutputStream fileOutputStream;
	static public XSSFWorkbook workbook ;
	static public XSSFSheet worksheet;
	static public XSSFRow rw;
	static public XSSFCell cell;
	
	
	
	public WebDriver dvr;
	
//	public static int browserWindow = 0;
	
	String browser ;	
	// Web Driver initialization
	
	@BeforeClass
	public void initialize() throws FileNotFoundException, IOException{
		System.out.println("initialize driver");
		
		InputDetails inputDetails = new InputDetails();
		browser = inputDetails.getBrowserName();
		System.out.println(browser);	
	if(browser.equalsIgnoreCase("chrome"))
		{
			System.setProperty("webdriver.chrome.driver", "./Executable/chromedriver.exe");
			dvr=new ChromeDriver();

		}else if(browser.equalsIgnoreCase("firefox"))
		{
			System.setProperty("webdriver.gecko.driver", "Executable/geckodriver.exe");
			dvr = new FirefoxDriver();
			
		}else if(browser.equalsIgnoreCase("safari")){

			System.setProperty("webdriver.safari.driver", "./Executable/SafariDriver.safariextz");
			dvr=new SafariDriver();

		}else if(browser.equalsIgnoreCase("ie"))
		{

			System.setProperty("webdriver.ie.driver", "./Executable/IEDriverServer.exe");
			dvr=new InternetExplorerDriver();

		}else if(browser.equalsIgnoreCase("edge"))
		{

			System.setProperty("webdriver.edge.driver", "./Executable/MicrosoftWebDriver.msi");
			dvr=new EdgeDriver();
			//System.out.println("Edge opnened");

		}else{
			System.out.println("Browser mentioned in not acceptable");
		}
	

		dvr.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);

		dvr.manage().window().maximize();

	}
	

	// Capture Screen Shot
	
	public void capturescreenshot(String filename) throws IOException{
		File f=((TakesScreenshot)dvr).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(f, new File("./screenshots//"+filename));	
	}

	// Scroll to View Element
	
	public void scrolltoviewelement(String element) throws IOException, InterruptedException{		
		((JavascriptExecutor) dvr).executeScript("arguments[0].scrollIntoView(true);", dvr.findElement(By.xpath(element)));
		Thread.sleep(1000);	
	}
	
	// Switch between window
	
	public void switchwindow(int windowno)
	{
		
		Set<String> window=dvr.getWindowHandles();
		dvr.switchTo().window(window.toArray()[windowno].toString());
		
	}

	// Scroll to top
	
	public void scrolltotop()
	{

		JavascriptExecutor js = ((JavascriptExecutor) dvr);
		js.executeScript("scroll(0,-2000);");

	}
	
	// Read Data form PmtDataExcel.xlsx Sheet1
	

	public String readExcelUserInput(String fileName,String sheet,int rowNum, int cellNum){
//		System.out.println("readExcelUserInput");
		String data = "";
		try {
			String fileLocation = "./input/"+fileName; 
			File file = new File(fileLocation);
			FileInputStream fileInputStream = new FileInputStream(file);			
//			Workbook wb = WorkbookFactory.create(fileInputStream);
			XSSFWorkbook wb = new XSSFWorkbook(fileInputStream);
			data = wb.getSheet(sheet).getRow(rowNum).getCell(cellNum).getStringCellValue();
			
		} catch (FileNotFoundException e) {

		} catch (IOException e) {

		} catch (EncryptedDocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} /*catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
//		System.out.println(data);
		return data;
		
	}
	
	public int readExcelNumericInput(String fileName,String sheet,int rowNum, int cellNum){
//		System.out.println("readExcelUserInput");
		int data = 0;
		try {
			String fileLocation = "./input/"+fileName; 
			File file = new File(fileLocation);
			FileInputStream fileInputStream = new FileInputStream(file);			
//			Workbook wb = WorkbookFactory.create(fileInputStream);
			XSSFWorkbook wb = new XSSFWorkbook(fileInputStream);
			data = (int)wb.getSheet(sheet).getRow(rowNum).getCell(cellNum).getNumericCellValue();
//			System.out.println("Data - "+data);
		} catch (FileNotFoundException e) {

		} catch (IOException e) {

		} catch (EncryptedDocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} /*catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
//		System.out.println(data);
		return data;
		
	}
	
	
	
	
	// Write into Detailed_Report.xlsx
	
	public void writexls(int rowvalue,int cellvalue, String value, String createvalue, String resultcolor) throws IOException, InterruptedException{
	
		try{
		file = new File("./final_result/Detailed_Report.xlsx");
		fileStream = new FileInputStream(file);
		workbook = new XSSFWorkbook(fileStream);
		worksheet = workbook.getSheet("Sheet1");
		/*XSSFRow rw;
		XSSFCell cell;*/

		if(createvalue.equalsIgnoreCase("yes"))
		{

			rw = worksheet.createRow(rowvalue);

		}else{

			rw = worksheet.getRow(rowvalue);

		}

		cell = rw.createCell(cellvalue); 
		cell.setCellValue(value);
		
		
		if(resultcolor.equalsIgnoreCase("green")){
			
			cell.setCellType(Cell.CELL_TYPE_STRING);

			CellStyle style = workbook.createCellStyle();

			style.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
			style.setFillPattern(CellStyle.SOLID_FOREGROUND);

			cell.setCellStyle(style);
			
		}else if(resultcolor.equalsIgnoreCase("red")){
			
			cell.setCellType(Cell.CELL_TYPE_STRING);

			CellStyle style = workbook.createCellStyle();

			style.setFillForegroundColor(IndexedColors.LIGHT_ORANGE.getIndex());
			style.setFillPattern(CellStyle.SOLID_FOREGROUND);

			cell.setCellStyle(style);
		}else{
		}

		}catch(Exception e){
			FileOutputStream outfile = new FileOutputStream(new File("./final_result/Detailed_Report.xlsx"));

			workbook.write(outfile);

			outfile.flush();
			outfile.close();

			Thread.sleep(500);
		}

		
		
	}

	// Get Responce Code from URL
	
	public int responsecode(String appurl) throws IOException{

		URL url = new URL(appurl);
		HttpURLConnection connection = (HttpURLConnection)url.openConnection();
		connection.setRequestMethod("GET");
		connection.connect();

		int code = connection.getResponseCode();

		System.out.println("Response code- "+code);

		String codevalue = Integer.toString(code);

//		return codevalue;
		return code;

	}

	// Wait until get a particular Element
	
	public void waitForVisibility(WebElement xpath){

		WebDriverWait wait = new WebDriverWait(dvr, 180);
		wait.until(ExpectedConditions.visibilityOf(xpath));

	}
	
	
	public void waitForClickable(WebElement xpath){

		WebDriverWait wait = new WebDriverWait(dvr, 180);
		wait.until(ExpectedConditions.elementToBeClickable(xpath));

	}

	// Mouse Hover
	
	public void mouseHover(By element) throws InterruptedException, IOException{

		Actions act=new Actions(dvr);
		act.moveToElement(dvr.findElement(element)).perform();



	}
	
	// Clear Cache of browser

	public void clearCache() throws AWTException, InterruptedException, IOException{

		if(browser.equals("chrome")){

			String path = ".\\input\\ChromeClearCacheanddata.bat";

			Process p = Runtime.getRuntime().exec("cmd /c " + path);

			System.out.println("Waiting for batch file ...");
			p.waitFor();
			System.out.println("Batch file done.");


		}else if(browser.equals("firefox")){


			String path = ".\\input\\FirefoxClearCacheanddata.bat";

			Process p = Runtime.getRuntime().exec("cmd /c " + path);

			System.out.println("Waiting for batch file ...");
			p.waitFor();
			System.out.println("Batch file done.");

		}else if(browser.equals("safari")){

			String path = ".\\input\\SafariClearCacheanddata.bat";

			Process p = Runtime.getRuntime().exec("cmd /c " + path);

			System.out.println("Waiting for batch file ...");
			p.waitFor();
			System.out.println("Batch file done.");

		}else if(browser.equals("ie")){


			String path = ".\\input\\IEClearCacheanddata.bat";

			Process p = Runtime.getRuntime().exec("cmd /c " + path);

			System.out.println("Waiting for batch file ...");
			p.waitFor();
			System.out.println("Batch file done.");

		}else if(browser.equals("edge")){

		}else{

			System.out.println("Check browser");
		}
		return;
	}
	
	// Enter URL in Browser
	
	public void enterURL(String URL){		
		
		dvr.navigate().to(URL);
	}
	
	// Thread Sleep 
	
	public void threadSleep() throws InterruptedException{		
		Thread.sleep(1000);
	}
	
	// Thread Sleep
	
	public void threadSleep(int num) throws InterruptedException{
		int sleepCount = (num*1000); 
		Thread.sleep(sleepCount);
	}
	
	
	// Count Window
	
	public int countWindow(){	
		
		Set<String> windowHandle = dvr.getWindowHandles();
		int count = windowHandle.size();
		
		return count;		
	}
	
	// Get Response message from URl
	
	public String getResMes(String appurl) throws IOException{
		
		URL url = new URL(appurl);
		HttpURLConnection connection = (HttpURLConnection)url.openConnection();
		connection.setRequestMethod("GET");
		connection.connect();
		
		connection.getResponseCode();
		String 	responseMsg = connection.getResponseMessage();
		System.out.println(responseMsg);
		return responseMsg;			
	}
	

	public int countRowsFromFile(String fileName,String sheet) throws IOException{
//		System.out.println("countRowsFromFile");
		int numOFRows = 0;
		try {
//			File file = new File("./input/PmtDataExcel.xlsx");			
			File file = new File("./input/"+fileName);
			FileInputStream fileInputStream = new FileInputStream(file);			
//			Workbook wb = WorkbookFactory.create(fileInputStream);
			Workbook wb = new XSSFWorkbook(fileInputStream);
			numOFRows= wb.getSheet(sheet).getLastRowNum();
//			numOFRows= wb.getSheet("Sheet1").getPhysicalNumberOfRows();
//			System.out.println(numOFRows);
//			numOFRows = (numOFRows-4);
		} catch (FileNotFoundException e) {

		} catch (IOException e) {

		} catch (EncryptedDocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}/* catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
//		System.out.println(data);
		return numOFRows;
	}
	
	// Explicit Wait
	public void explicitWait(WebElement element){
		WebDriverWait wait = new WebDriverWait(dvr, 300);
		WebElement waitForElement = wait.until(ExpectedConditions.visibilityOf(element));
	}
		
	
	
	
	// Close Bowser
	
	@AfterClass
	public void tearDown() throws IOException, InterruptedException{
		System.out.println("tearDown");		
		dvr.quit();
		}
	
	public static void createExcelFile(String fileName) throws IOException, InterruptedException{
		Thread.sleep(500);
		fileOutputStream = new FileOutputStream(new File("./final_result/"+fileName+".xlsx"));
		workbook = new XSSFWorkbook();	
	}
	public static void createSheet(String sheetName){
		worksheet = workbook.createSheet(sheetName);
	}
	
	public static void writeExcel(String fileName) throws IOException{
		FileOutputStream outfile = new FileOutputStream(new File("./final_result/"+fileName+".xlsx"));
		workbook.write(outfile);
		outfile.flush();
		outfile.close();		
	}
	
	
	
	

}
