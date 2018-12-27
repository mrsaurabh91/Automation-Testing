package com.magic.input;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import com.magic.main.Main;
import com.magic.util.EnvironmentPropertiesReader;
import com.magic.util.SeleniumUtil;

public class HtmlInputData {
	
	static WebDriver driver;
	private static EnvironmentPropertiesReader configProperty = EnvironmentPropertiesReader.getInstance();
	
	private static By table = By.xpath("//table");

	public static LinkedHashMap<String,LinkedHashMap<String,ArrayList<String>>> getInputDocFileContentNew(String filePath) throws InterruptedException {		
		
		String fullFilePath = "";
		fullFilePath = "file:///"+filePath;	
		
		System.setProperty("webdriver.chrome.driver", "driver/chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
        options.addArguments("headless");
		//WebDriver driver=new ChromeDriver(options);
		WebDriver driver=new ChromeDriver();
//		System.out.println("fullFilePath "+fullFilePath);
		driver.get(fullFilePath);
/*		GUI.logger.info("Loading created input html page");
		GUI.consoleText.append("Loading created input html page\n");*/
		
		LinkedHashMap<String,LinkedHashMap<String,ArrayList<String>>> mainInputLinkedHashMap= new  LinkedHashMap<String,LinkedHashMap<String,ArrayList<String>>>();
		
		try{
			
			SeleniumUtil.waitForElementPresence(driver, table, 180);
			
			List<WebElement> listOftableElement = driver.findElements(table);
			
			if(listOftableElement.size()>0){
			
			
			for(int tableCount = 1;tableCount <= listOftableElement.size(); tableCount++){
				System.out.println("Fetching data from table "+tableCount);
				//Main.consoleText.append("\nFetching data from table "+tableCount);
		
				
				LinkedHashMap<String,ArrayList<String>> inputLinkedHashMap= new  LinkedHashMap<String,ArrayList<String>>();			
				ArrayList<String> inputList = new ArrayList<String>();
				
				List<WebElement> listOfTableRowsElement = driver.findElements(By.xpath("(//table)["+tableCount+"]/tbody/tr"));
//				System.out.println("listOfTableRowsElement "+listOfTableRowsElement.size());
				for(int rowElement = 1 ; rowElement <= listOfTableRowsElement.size(); rowElement++){
//					System.out.println("rowElement "+rowElement);
					List<WebElement> listOfTableRowsCellsElement = driver.findElements(By.xpath("((//table)["+tableCount+"]/tbody/tr)["+rowElement+"]/td"));
//					System.out.println("listOfTableRowsCellsElement "+listOfTableRowsCellsElement.size());
					String cellValue = "";
					
					if(listOfTableRowsCellsElement.size() == 2){
						for(int cellElement = 1 ; cellElement <= listOfTableRowsCellsElement.size(); cellElement++){
//							System.out.println("cellElement "+cellElement);
							
							if(cellElement == 1){
								cellValue = cellValue+driver.findElement(By.xpath("(((//table)["+tableCount+"]/tbody/tr)["+rowElement+"]/td)["+cellElement+"]//span")).getAttribute("innerHTML").trim()+"|";
								
							}else{							
								List<WebElement> listOfSpanElement = driver.findElements(By.xpath("(((//table)["+tableCount+"]/tbody/tr)["+rowElement+"]/td)["+cellElement+"]/p"));
								
								for(int spanCount = 1;spanCount <= listOfSpanElement.size(); spanCount++){
									String tag = "";
									String tagValue = "";
									
									tag = driver.findElement(By.xpath("((((//table)["+tableCount+"]/tbody/tr)["+rowElement+"]/td)["+cellElement+"]/p)["+spanCount+"]")).getAttribute("innerHTML").trim();
									
									tagValue = getTextWithTag(tag);
									
									if(spanCount != 1){
										cellValue = cellValue+"|"+tagValue;
									}else{
										cellValue = cellValue+tagValue;
									}
//									
								}
							}
						}
					}else{
						for(int cellElement = 1 ; cellElement <= listOfTableRowsCellsElement.size(); cellElement++){
//							System.out.println("cellElement "+cellElement);
							
							if(cellElement == 1){
								cellValue = cellValue+driver.findElement(By.xpath("(((//table)["+tableCount+"]/tbody/tr)["+rowElement+"]/td)["+cellElement+"]//p")).getText().trim()+"|";							
							}else if(cellElement == 2){
								cellValue = cellValue+driver.findElement(By.xpath("(((//table)["+tableCount+"]/tbody/tr)["+rowElement+"]/td)["+cellElement+"]//p")).getText().trim()+"|";
							}else{
								List<WebElement> listOfSpanElement = driver.findElements(By.xpath("(((//table)["+tableCount+"]/tbody/tr)["+rowElement+"]/td)["+cellElement+"]/p"));
								for(int spanCount = 1;spanCount <= listOfSpanElement.size(); spanCount++){
									String tag = "";
									String tagValue = "";
									
									tag = driver.findElement(By.xpath("((((//table)["+tableCount+"]/tbody/tr)["+rowElement+"]/td)["+cellElement+"]/p)["+spanCount+"]")).getAttribute("innerHTML").trim();
//									System.out.println("Before "+tag);
									
									tagValue = getrequiredText(tag);
									
									if(spanCount != 1){
										cellValue = cellValue+"|"+tagValue;
									}else{
										cellValue = cellValue+tagValue;
									}
								}
							}
						}
					}
//					System.out.println("cellValue "+cellValue);
					inputList.add(cellValue);
				}
				
				for(int i = 0;i<inputList.size();i++){
					String temp = "";
					temp = inputList.get(i);
					ArrayList al = new ArrayList();
					ArrayList tempAl = new ArrayList();
					al = getListSplitedByChar(temp,'|');
					
					
					if(al.size()>1){
						
						if(al.size() == 2){
							String mainValue = "";						
							String mainKey = "";
							mainKey = al.get(0).toString().trim();
							mainValue = al.get(1).toString().trim();
							tempAl.add(mainValue);
							
							if(inputLinkedHashMap.containsKey(mainKey)){
								for(Entry<String,ArrayList<String>> htmlEntrySet:inputLinkedHashMap.entrySet()){
									
									String htmlEntryMapkey = "";
									htmlEntryMapkey = htmlEntrySet.getKey().toString().trim();
									if(htmlEntryMapkey.equalsIgnoreCase(mainKey)){
							//			System.out.println("Pass");
										ArrayList<String> tAl = htmlEntrySet.getValue();
										tAl.add(mainValue);
							//			System.out.println("tAl "+tAl);
										inputLinkedHashMap.put(mainKey, tAl);
										break;
									}
								}
							}else{
								inputLinkedHashMap.put(mainKey, tempAl);
							}
						}else if(al.size() > 2){
							String mainValue = "";						
							String mainKey = "";
							mainKey = al.get(0).toString().trim();
							
							for(int alValue = 1;alValue <al.size();alValue++){
								if(alValue == 1){
									mainValue = mainValue + al.get(alValue).toString().trim();
								}else{
									mainValue = mainValue +"|"+al.get(alValue).toString().trim();
								}							
							}
							
							tempAl.add(mainValue);
							if(inputLinkedHashMap.containsKey(mainKey)){
								for(Entry<String,ArrayList<String>> htmlEntrySet:inputLinkedHashMap.entrySet()){
									String htmlEntryMapkey = "";
									htmlEntryMapkey = htmlEntrySet.getKey().toString().trim();
									
									if(htmlEntryMapkey.equalsIgnoreCase(mainKey)){
								//		System.out.println("pass");
										ArrayList<String> tAl = htmlEntrySet.getValue();
										tAl.add(mainValue);
								//		System.out.println("tAl "+tAl);
										inputLinkedHashMap.put(mainKey, tAl);
										break;
									}
								}
							}else{
								inputLinkedHashMap.put(mainKey, tempAl);
							}
							
						}
					}
				}
//				System.out.println("inputLinkedHashMapSize "+inputLinkedHashMap.size()+" inputLinkedHashMap "+inputLinkedHashMap);
				mainInputLinkedHashMap.put(String.valueOf(tableCount), inputLinkedHashMap);	
			}
			
		}else{
			System.out.println("Issue in fetching data from input file.");
			
		}
		}catch (Exception e) {
			// TODO: handle exception
			System.out.println("Issue in fetching data from input file.");
			e.printStackTrace();
		}
//		System.out.println("mainInputLinkedHashMap "+mainInputLinkedHashMap.size()+" mainInputLinkedHashMap "+mainInputLinkedHashMap);
		driver.close();
		driver.quit();
		return mainInputLinkedHashMap;
	}
	
	
	
	public static LinkedHashMap<String,LinkedHashMap<String,ArrayList<String>>> getInputDocFileContent(String filePath) throws InterruptedException {		
		
		String fullFilePath = "";
		fullFilePath = "file:///"+filePath;		
		
		driver.get(fullFilePath);
		System.out.println("Going to launch input");
		
		LinkedHashMap<String,LinkedHashMap<String,ArrayList<String>>> mainInputLinkedHashMap= new  LinkedHashMap<String,LinkedHashMap<String,ArrayList<String>>>();
		
		try{
			
			SeleniumUtil.waitForElementPresence(driver, table, 180);
			
			List<WebElement> listOftableElement = driver.findElements(table);
			
			if(listOftableElement.size()>0){
			
			
			for(int tableCount = 1;tableCount <= listOftableElement.size(); tableCount++){
				System.out.println("tableCount "+tableCount);
				
				
				LinkedHashMap<String,ArrayList<String>> inputLinkedHashMap= new  LinkedHashMap<String,ArrayList<String>>();			
				ArrayList<String> inputList = new ArrayList<String>();
				
				List<WebElement> listOfTableRowsElement = driver.findElements(By.xpath("(//table)["+tableCount+"]/tbody/tr"));
				
				for(int rowElement = 1 ; rowElement < listOfTableRowsElement.size(); rowElement++){
				//	System.out.println("rowElement "+rowElement);
					List<WebElement> listOfTableRowsCellsElement = driver.findElements(By.xpath("((//table)["+tableCount+"]/tbody/tr)["+rowElement+"]/td"));
					String cellValue = "";
				//	String tag = "";
					
					if(listOfTableRowsCellsElement.size() == 2){
						for(int cellElement = 1 ; cellElement <= listOfTableRowsCellsElement.size(); cellElement++){
					//		System.out.println("cellElement "+cellElement);
							
							if(cellElement == 1){
								cellValue = cellValue+driver.findElement(By.xpath("(((//table)["+tableCount+"]/tbody/tr)["+rowElement+"]/td)["+cellElement+"]//span")).getAttribute("innerHTML").trim()+"|";
								
							}else{							
								List<WebElement> listOfSpanElement = driver.findElements(By.xpath("(((//table)["+tableCount+"]/tbody/tr)["+rowElement+"]/td)["+cellElement+"]/p"));
								
								for(int spanCount = 1;spanCount <= listOfSpanElement.size(); spanCount++){
									String tag = "";
									String tagValue = "";
									
//									tagValue = driver.findElement(By.xpath("(((//table/tbody/tr)["+rowElement+"]/td)["+cellElement+"]/p)["+spanCount+"]")).getAttribute("innerHTML").trim();
									tag = driver.findElement(By.xpath("((((//table)["+tableCount+"]/tbody/tr)["+rowElement+"]/td)["+cellElement+"]/p)["+spanCount+"]")).getAttribute("innerHTML").trim();
									
									tagValue = getTextWithTag(tag);
									//tagValue = removeTag(tagValue);
									
									if(spanCount != 1){
										cellValue = cellValue+"|"+tagValue;
									}else{
										cellValue = cellValue+tagValue;
									}
//									tag = driver.findElement(By.xpath("(((//table/tbody/tr)["+rowElement+"]/td)["+cellElement+"]//span)["+spanCount+"]/ancestor::p/*")).getAttribute("outerHTML").trim();
		//							cellValue = getTextWithTag(cellValue,tag);
								}
							}
						}
					}else{
						for(int cellElement = 1 ; cellElement <= listOfTableRowsCellsElement.size(); cellElement++){
						//	System.out.println("cellElement "+cellElement);
							
							if(cellElement == 1){
								cellValue = cellValue+driver.findElement(By.xpath("(((//table)["+tableCount+"]/tbody/tr)["+rowElement+"]/td)["+cellElement+"]//span")).getAttribute("innerHTML").trim()+"|";							
							}else if(cellElement == 2){
								cellValue = cellValue+driver.findElement(By.xpath("(((//table)["+tableCount+"]/tbody/tr)["+rowElement+"]/td)["+cellElement+"]//span")).getAttribute("innerHTML").trim()+"|";
							}else{
								List<WebElement> listOfSpanElement = driver.findElements(By.xpath("(((//table)["+tableCount+"]/tbody/tr)["+rowElement+"]/td)["+cellElement+"]/p"));
								for(int spanCount = 1;spanCount <= listOfSpanElement.size(); spanCount++){
									String tag = "";
									String tagValue = "";
									
//									tagValue = driver.findElement(By.xpath("(((//table/tbody/tr)["+rowElement+"]/td)["+cellElement+"]/p)["+spanCount+"]")).getAttribute("innerHTML").trim();
									tag = driver.findElement(By.xpath("((((//table)["+tableCount+"]/tbody/tr)["+rowElement+"]/td)["+cellElement+"]/p)["+spanCount+"]")).getAttribute("innerHTML").trim();
									tagValue = getTextWithTag(tag);
								//	tagValue = removeTag(tagValue);
									
									if(spanCount != 1){
										cellValue = cellValue+"|"+tagValue;
									}else{
										cellValue = cellValue+tagValue;
									}
							//		tag = driver.findElement(By.xpath("(((//table/tbody/tr)["+rowElement+"]/td)["+cellElement+"]//span)["+spanCount+"]/ancestor::p/*")).getAttribute("outerHTML").trim();
							//		cellValue = getTextWithTag(cellValue,tag);
								}
							}
						}
					}
					
					inputList.add(cellValue);
				}
				
				for(int i = 0;i<inputList.size();i++){
					String temp = "";
					temp = inputList.get(i);
					ArrayList al = new ArrayList();
					ArrayList tempAl = new ArrayList();
					al = getListSplitedByChar(temp,'|');
					
					
					if(al.size()>1){
						
						if(al.size() == 2){
							String mainValue = "";						
							String mainKey = "";
							mainKey = al.get(0).toString().trim();
							mainValue = al.get(1).toString().trim();
							tempAl.add(mainValue);
							
							if(inputLinkedHashMap.containsKey(mainKey)){
								for(Entry<String,ArrayList<String>> htmlEntrySet:inputLinkedHashMap.entrySet()){
									
									String htmlEntryMapkey = "";
									htmlEntryMapkey = htmlEntrySet.getKey().toString().trim();
									if(htmlEntryMapkey.equalsIgnoreCase(mainKey)){
							//			System.out.println("Pass");
										ArrayList<String> tAl = htmlEntrySet.getValue();
										tAl.add(mainValue);
							//			System.out.println("tAl "+tAl);
										inputLinkedHashMap.put(mainKey, tAl);
										break;
									}
								}
							}else{
								inputLinkedHashMap.put(mainKey, tempAl);
							}
						}else if(al.size() > 2){
							String mainValue = "";						
							String mainKey = "";
							mainKey = al.get(0).toString().trim();
							
							for(int alValue = 1;alValue <al.size();alValue++){
								if(alValue == 1){
									mainValue = mainValue + al.get(alValue).toString().trim();
								}else{
									mainValue = mainValue +"|"+al.get(alValue).toString().trim();
								}							
							}
							
							tempAl.add(mainValue);
							if(inputLinkedHashMap.containsKey(mainKey)){
								for(Entry<String,ArrayList<String>> htmlEntrySet:inputLinkedHashMap.entrySet()){
									String htmlEntryMapkey = "";
									htmlEntryMapkey = htmlEntrySet.getKey().toString().trim();
									
									if(htmlEntryMapkey.equalsIgnoreCase(mainKey)){
								//		System.out.println("pass");
										ArrayList<String> tAl = htmlEntrySet.getValue();
										tAl.add(mainValue);
								//		System.out.println("tAl "+tAl);
										inputLinkedHashMap.put(mainKey, tAl);
										break;
									}
								}
							}else{
								inputLinkedHashMap.put(mainKey, tempAl);
							}
							
						}
					}
				}
//				System.out.println("inputLinkedHashMapSize "+inputLinkedHashMap.size()+" inputLinkedHashMap "+inputLinkedHashMap);
				mainInputLinkedHashMap.put(String.valueOf(tableCount), inputLinkedHashMap);	
			}
			
		}else{
			System.out.println("Input data not found");
		}
		}catch (Exception e) {
			// TODO: handle exception
			System.out.println("Issue in abstarcting Input");
		}
		
		
//		System.out.println("mainInputLinkedHashMap "+mainInputLinkedHashMap.size()+" mainInputLinkedHashMap "+mainInputLinkedHashMap);
		return mainInputLinkedHashMap;
	}
	
	private static ArrayList getListSplitedByChar(String inputText,char splitChar){
		
		ArrayList subStringList = new ArrayList();
		String subString = "";
		for(int i = 0;i<inputText.length();i++){								
			if(inputText.charAt(i) == splitChar){
				subStringList.add(subString);
				subString = "";
				continue;
			}
			subString = subString+inputText.charAt(i);
			if(i==inputText.length()-1){
				subStringList.add(subString);
			}
		}		
		return subStringList;
	}
	
/*	private static String getTextWithTag1(String cellValue,String tag){
		
		System.out.println("cellValue "+cellValue);
		System.out.println("tag Value "+tag);
		String getSpan = "";
		boolean checkSpan = false;
		for(int tagChar = 0; tagChar<tag.length();tagChar++){								
			getSpan = getSpan+tag.charAt(tagChar);
			if(!checkSpan){
				if(tag.charAt(tagChar) == '>'){
					getSpan = "";
				}	
			}								
			if(getSpan.equalsIgnoreCase("<span")){
				checkSpan = true;									
			}
			if(getSpan.contains("</span>")){
				break;
			}
		}
		tag = tag.replace(getSpan, cellValue);
		String strongStart = "<strong style="+"\""+"font-weight: bold;"+"\""+">";
		String strongEnd = "</strong>";
		System.out.println("Value "+tag);
		if((tag.contains("<b>")) && (tag.contains("</b>"))){
			tag = tag.replace("<b>", strongStart);
			tag = tag.replace("</b>", strongEnd);
		}
		System.out.println("New Value "+tag);
		
		return tag;
	}*/
	
	
	private static String getrequiredText(String str){

		String originalText = "";

		String boldTagInHTML = "font-weight:bold";
		String startBoldTag = "<strong style=\"font-weight: bold;\">";
		String endBoldTag = "</strong>";

		String tagValue = "";

		boolean boldCheck = false;

		int startTag = 0;

		for(int i = 0;i<str.length();i++){
		if(str.charAt(i) == '<'){
		startTag = 0;
		if(boldCheck){
		originalText = originalText+endBoldTag;
		boldCheck = false;
		}
		}
		if(startTag == 0){
		tagValue = tagValue+str.charAt(i);	
		}
		if(str.charAt(i) == '>'){	
		if(tagValue.contains(boldTagInHTML)){
		originalText = originalText+startBoldTag;
		boldCheck = true;
		}

		tagValue = "";
		startTag = 1;
		continue;
		}
		if(startTag == 1){
		originalText = originalText+str.charAt(i);
		}
		}
		return originalText;
		}
	
	
	private static String getTextWithTag(String tag){
//		System.out.println("getTextWithTag "+tag);
		String getSpan = "";
		String spanText = "";
		boolean checkSpan = false;
		boolean checkLoop = false;
		for(int tagChar = 0; tagChar<tag.length();tagChar++){								
			getSpan = getSpan+tag.charAt(tagChar);
			if(!checkSpan){
				if(tag.charAt(tagChar) == '>'){
					getSpan = "";
				}	
			}else{
				spanText = spanText+tag.charAt(tagChar);
			}							
			if(getSpan.equalsIgnoreCase("<span")){
				spanText = "<span";
				checkSpan = true;
			}
			if(checkSpan){
				if(tag.charAt(tagChar) == '>'){
					tag = tag.replace(spanText, "");
					tag = getTextWithTag(tag);
					break;
				}
			}
			if(tagChar == (tag.length()-1)){
				checkLoop = true;
			}
		}
		
		if(true){
			String strongStart = "<strong style="+"\""+"font-weight: bold;"+"\""+">";
			String strongEnd = "</strong>";
//			System.out.println("Value "+tag);
			if((tag.contains("<b>")) && (tag.contains("</b>"))){
				tag = tag.replace("<b>", strongStart);
				tag = tag.replace("</b>", strongEnd);
			}
			if(tag.contains("</span>")){
				tag = tag.replace("</span>", "");
			}
//			System.out.println("New Value "+tag);
		}
		
		return tag;
	}
	
	private static String removeTag(String tag){
//		System.out.println("removeTag "+tag);
		String strongStart = "<strong style="+"\""+"font-weight: bold;"+"\""+">";
		String strongEnd = "</strong>";
//		System.out.println("Value "+tag);
		if((tag.contains("<b>")) && (tag.contains("</b>"))){
			tag = tag.replace("<b>", strongStart);
			tag = tag.replace("</b>", strongEnd);
		}
		if(tag.contains("</span>")){
			tag = tag.replace("</span>", "");
		}
//		System.out.println("New Value "+tag);
		
		return tag;
		
	}
	
}
