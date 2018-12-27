package com.magic.hmh.generic;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;/*
import org.jsoup.Jsoup;*/
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.support.ui.Select;

public class BaseAction {	
	
	Properties properties;
	
	public BaseAction() {
		// TODO Auto-generated constructor stub
		properties = new Properties();
		try {
			properties.load(new FileInputStream("./input/details.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	
	// Enter Text in textbox
	
	public void enterTextInTextBox(WebElement webElement,String str){
		webElement.sendKeys(str);		
	}
	
	// click on Element
	
	public void click(WebElement btn){
		btn.click();
	}
	
	// Press Enter in Textbox
	
	public void pressEnter(WebElement enter){
		enter.sendKeys(Keys.ENTER);
	}	
	
	// Get Text on WebElement
	
	public String getTextValue(WebElement text){
		return text.getText().toString();
	}
	
	// Clear TextBox
	
	public void clearTextBox(WebElement text){
		text.clear();
	} 
	
	// Perform Action in Select Option
	
	public void selectOption(WebElement wb,String text){
		Select selectText=new Select(wb);
		selectText.selectByVisibleText(text);
	}
	
	// Read data from UserInput Sheet 1
	
	public String readExcel(int rowNum, int cellNum){
		String data = "";
		try {
			File file = new File("./input/UserInput.xlsx");
			FileInputStream fileInputStream = new FileInputStream(file);			
//			Workbook wb = WorkbookFactory.create(fileInputStream);
			Workbook wb = new XSSFWorkbook(fileInputStream);
			data = wb.getSheet("Sheet1").getRow(rowNum).getCell(cellNum).getStringCellValue();
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
	
	public double readExcelNumeric(int rowNum, int cellNum){
		double data = 0;
		try {
			File file = new File("./input/UserInput.xlsx");
			FileInputStream fileInputStream = new FileInputStream(file);		

			Workbook wb = new XSSFWorkbook(fileInputStream);
			data = wb.getSheet("Sheet1").getRow(rowNum).getCell(cellNum).getNumericCellValue();
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
	public static String getLinkedGUID(String href){
		String subStr = "";
		String tempStr = "";
		String str = href;
		
		for(int i = str.length()-1 ;i>=0;i--){			
			if(str.charAt(i) == '='){
				break;
			}
			subStr = subStr + str.charAt(i);				
		}	
		for(int j = subStr.length()-1;j>=0;j--){
			tempStr = tempStr + subStr.charAt(j);
		}
		
		return tempStr.trim();
	}
	
	public int countRowsFromFile(String fileName,String sheet) throws IOException{

		int numOFRows = 0;
		try {
		
				File file = new File("./input/"+fileName);
				FileInputStream fileInputStream = new FileInputStream(file);
				Workbook wb = new XSSFWorkbook(fileInputStream);
				numOFRows= wb.getSheet(sheet).getLastRowNum();
			} catch (FileNotFoundException e) {

			} catch (IOException e) {

			} catch (EncryptedDocumentException e) {
			// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return numOFRows;
	}
	
	public HashMap<String,String> readExcelUserInput(String fileName,String sheet,int startRow, int guidCol,int urlCol) throws IOException{
//		System.out.println("readExcelUserInput");
		HashMap<String, String> mdsMap = new HashMap<String,String>();
		
		int numberOfRow = countRowsFromFile(fileName,sheet);
//		System.out.println("numberOfRow "+numberOfRow);
		String data = "";
		try {
			String fileLocation = "./input/"+fileName; 
			File file = new File(fileLocation);
			FileInputStream fileInputStream = new FileInputStream(file);
			XSSFWorkbook wb = new XSSFWorkbook(fileInputStream);
			for(int rowNum = startRow; rowNum<= numberOfRow;rowNum++){
				String key = "";
				String value = "";				
				key = wb.getSheet(sheet).getRow(rowNum).getCell(guidCol).getStringCellValue();				
				value = wb.getSheet(sheet).getRow(rowNum).getCell(urlCol).getStringCellValue();	
				mdsMap.put(key, value);
			}	
		} catch (FileNotFoundException e) {

		} catch (IOException e) {

		} catch (EncryptedDocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mdsMap;		
	}
	
	
	
}
