package com.magic.sussex.inputDetails;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.magic.sussex.gui.GUI;
import com.magic.util.EnvironmentPropertiesReader;
import com.magic.util.SeleniumUtil;
import com.magic.util.WebDriverFactory;

public class HtmlInputData extends WebDriverFactory{
	
	
	private static EnvironmentPropertiesReader configProperty = EnvironmentPropertiesReader.getInstance();
	
	private static By table = By.xpath("//table");

	public static LinkedHashMap<String,LinkedHashMap<String,ArrayList<String>>> getInputDocFileContentNew(String filePath) throws InterruptedException {		
		
		String fullFilePath = "";
		fullFilePath = "file:///"+filePath;		
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
				GUI.logger.info("Fetching data from table "+tableCount);
				GUI.consoleText.append("Fetching data from table "+tableCount+"\n");
				
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
							//		System.out.println("Before "+tag);
									
									tagValue = getrequiredText(tag);
							//		System.out.println("After "+tagValue);
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
			GUI.logger.info("There is some problem in fetching data from input file. Please try again.");
			GUI.consoleText.append("There is some problem in fetching data from input file. Please try again.\n");
		}
		}catch (Exception e) {
			// TODO: handle exception
			GUI.logger.info("There is some problem in fetching data from input file. Please try again.");
			GUI.consoleText.append("There is some problem in fetching data from input file. Please try again.\n");
		}
//		System.out.println("mainInputLinkedHashMap "+mainInputLinkedHashMap.size()+" mainInputLinkedHashMap "+mainInputLinkedHashMap);
		return mainInputLinkedHashMap;
	}
	
	
	
	public static LinkedHashMap<String,LinkedHashMap<String,ArrayList<String>>> getInputDocFileContent(String filePath) throws InterruptedException {		
		
		String fullFilePath = "";
		fullFilePath = "file:///"+filePath;		
		
		driver.get(fullFilePath);
		GUI.logger.info("Going to launch input");
		GUI.consoleText.append("Going to launch input\n");
		
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
			GUI.logger.info("Input data not found");
			GUI.consoleText.append("Input data not found");
		}
		}catch (Exception e) {
			// TODO: handle exception
			GUI.logger.info("Issue in abstarcting Input");
			GUI.consoleText.append("Issue in abstarcting Input");
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
	
	private static String getrequiredTextNew(String str){
		
		String originalText = "";
		
		String boldTagInHTML = "font-weight:bold";
		String startBoldTag = "<strong style=\"font-weight: bold;\">";
		String endBoldTag = "</strong>";
		
		String startSpanTag = "<span";
		String endSpanTag = "</span>";
		
		String newStr = "";
		
		int countOfSpanText = 0;
		
		boolean bold = false;
		
		
		for(int i = 0;i<str.length();i++){
			
			newStr = newStr+str.charAt(i);	
			if(newStr.contains(startSpanTag) && countOfSpanText == 0){
				countOfSpanText++;
			}
			if(countOfSpanText == 1){
				if(str.charAt(i) == '>'){
					countOfSpanText++;
					if(newStr.contains(boldTagInHTML)){
						bold = true;
						originalText = originalText+startBoldTag;
					}
					continue;
				}
			}
			if(countOfSpanText == 2){
				if(str.charAt(i) == '<'){
					if(bold){
						originalText = originalText+endBoldTag;
					}
					countOfSpanText++;
				}else{
					originalText = originalText+str.charAt(i);
				}
			}
			if(countOfSpanText == 3 &&  newStr.contains(endSpanTag)){
				newStr = "";				
				countOfSpanText = 0;				
				bold = false;
			}
			
			
		}
		if(originalText.length()>0){
			return originalText;
		}else{
			return str;
		}
		
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
