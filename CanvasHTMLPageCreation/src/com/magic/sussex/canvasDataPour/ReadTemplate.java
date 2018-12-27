package com.magic.sussex.canvasDataPour;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.magic.sussex.gui.GUI;
import com.magic.sussex.inputDetails.HtmlInputData;
import com.magic.util.EnvironmentPropertiesReader;
import com.magic.util.ExcelUtils;
import com.magic.util.SeleniumUtil;
import com.magic.util.WebDriverFactory;

public class ReadTemplate extends WebDriverFactory{
	
	public static String resourcesFolderName = "Resources";
	public static String pageDetailsFileName = "PageNameAndURL";
	public static String pageDetailsSheetName = "Sheet1";
	
	private static EnvironmentPropertiesReader configProperty = EnvironmentPropertiesReader.getInstance();

//	static LinkedHashMap<String,String> imageLinkedHashMap;
	
	public static int waitSeconds = 180;
	
	/*private static String pageHtmlCode = "";
	private static String pageCreationFileName = "NewOrUpdatePage";
	private static String pageCreationSheetName = "Sheet1";*/
	
	public static int pageCount = 0;
	
	static int rowCount = 0;
	
	
	
	public static ArrayList<String> pageURLList = new ArrayList<String>();
	

	/*public ReadTemplate() {
		// TODO Auto-generated constructor stub
			
	}*/
	
	
	public void getNoChangeListAndCreateDummyPage(LinkedHashMap<String,LinkedHashMap<String,ArrayList<String>>> htmllinkedMap,ArrayList<String> listOfPageCreationType,String htmlPagePath,String programeName) throws Exception{
		
		redirectToAllPages(htmlPagePath);
		System.out.println("htmllinkedMap "+htmllinkedMap);
		
		try{
			
			for(int pageCreationTypeCount = 0; pageCreationTypeCount<listOfPageCreationType.size();pageCreationTypeCount++){
				String value = "";
				value = listOfPageCreationType.get(pageCreationTypeCount);
				ArrayList<String> tempList = new ArrayList<String>();
				tempList = getListSplitedByChar(value, '|');
				
			//	System.out.println("tempList "+tempList);
				
					String pageName = "";
					String type = "";		
					
					boolean checkPageAvailable = false;
					
					for(int tempCount = 0; tempCount<tempList.size(); tempCount++){							
						if(tempCount == 0){pageName = tempList.get(tempCount).trim();}
						if(tempCount == 1){type = tempList.get(tempCount).trim();}
					}
					
					if(type.toLowerCase().equalsIgnoreCase("nochange")){
						MainSussexPageCreation.noChangeList.add(getTableCount(htmllinkedMap,pageName));					
						continue;
					}else{
							checkPageAvailable = verifyPageAlredyAvailableInMap(pageName);
							
							if(type.toLowerCase().equalsIgnoreCase("new")){
								if(!checkPageAvailable){
									if(!createNewBlankHTMLPage(htmlPagePath,pageName)){
										MainSussexPageCreation.issueInNewPageCreation.add(getTableCount(htmllinkedMap,pageName));
									}
								}else{
									MainSussexPageCreation.newPageAlreadyFoundList.add(getTableCount(htmllinkedMap,pageName));
								}
							}else if(type.toLowerCase().equalsIgnoreCase("update")){
								if(!checkPageAvailable){
									MainSussexPageCreation.updatePageNoFoundList.add(getTableCount(htmllinkedMap,pageName));
								}
							}
					}
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		System.out.println("noChangeList "+MainSussexPageCreation.noChangeList);
		System.out.println("newPageAlreadyFoundList "+MainSussexPageCreation.newPageAlreadyFoundList);
		System.out.println("updatePageNoFoundList "+MainSussexPageCreation.updatePageNoFoundList);
		System.out.println("issueInNewPageCreation "+MainSussexPageCreation.issueInNewPageCreation);
		System.out.println("listOfPageCreationType "+MainSussexPageCreation.listOfPageCreationType);
		System.out.println("pageURLList "+pageURLList);		
	}	
	
	public void getInputAndPourContent(LinkedHashMap<String,LinkedHashMap<String,ArrayList<String>>> htmllinkedMap,String programeName) throws Exception {
		System.out.println("getInputAndPourContent");
		// TODO Auto-generated method stub
	
		Thread.sleep(5000);		
		
		if(pageURLList.size()>0){
		
			GUI.logger.info("Ingestion of data table wise is getting started ");
			GUI.consoleText.append("Ingestion of data table wise is getting started "+"\n");
			
			for(Entry<String,LinkedHashMap<String,ArrayList<String>>> tableHtmlEntrySet:htmllinkedMap.entrySet()){
				String tableHtmlKey  = "";
				tableHtmlKey  = tableHtmlEntrySet.getKey().toString().trim();
//			System.out.println("tableHtmlKey "+tableHtmlKey);
			GUI.logger.info("Table "+tableHtmlKey);
			GUI.consoleText.append("Table "+tableHtmlKey+"\n");
			
			LinkedHashMap<String,ArrayList<String>> htmlLinkedHashMap = tableHtmlEntrySet.getValue();
			String htmlCodeAfterContentPour = "";
			String fullHTMLCode = "";
			
			String PageName = "";
			String templateName = "";
			
			for(Entry<String,ArrayList<String>> htmlEntrySet:htmlLinkedHashMap.entrySet()){
				
				String htmlKey  = "" ;
				htmlKey  = htmlEntrySet.getKey().trim();
				
				if(htmlKey.equalsIgnoreCase("PageTitle")){
					ArrayList<String> tempList = new ArrayList<String>();
					tempList = htmlEntrySet.getValue();
//					System.out.println("tempList "+tempList);
					String templateCode = "";
					for(int i = 0;	i < tempList.size();	i++){
						templateCode = templateCode+tempList.get(i).toString().trim();
						if(i == 0){
							PageName = tempList.get(i).toString().trim();
						}						
					}
				}
				if(htmlKey.equalsIgnoreCase("TemplateName")){
					String templateCode = "";
					ArrayList<String> tempList = new ArrayList<String>();
					tempList = htmlEntrySet.getValue();					
					
					for(int i = 0;	i < tempList.size();	i++){
						templateCode = templateCode+tempList.get(i).toString().trim();
						if(i == 0){
							templateName = tempList.get(i).toString().trim();
						}						
					}	
				}	
			}
			/*System.out.println("noChangeList "+MainSussexPageCreation.noChangeList);
			System.out.println("newPageAlreadyFoundList "+MainSussexPageCreation.newPageAlreadyFoundList);
			System.out.println("updatePageNoFoundList "+MainSussexPageCreation.updatePageNoFoundList);
			System.out.println("issueInNewPageCreation "+MainSussexPageCreation.issueInNewPageCreation);*/
			
			if(MainSussexPageCreation.newPageAlreadyFoundList.contains(tableHtmlKey)){
				GUI.logger.info(PageName+" already exist so can not create new page");
				GUI.consoleText.append(PageName+" already exist so can not create new page"+"\n");
				continue;
			}
			if(MainSussexPageCreation.updatePageNoFoundList.contains(tableHtmlKey)){
				GUI.logger.info(PageName+" not available in already existing pages list hence we can not update");
				GUI.consoleText.append(PageName+" not available in already existing pages list hence we can not update"+"\n");
				continue;
			}			
			if(MainSussexPageCreation.noChangeList.contains(tableHtmlKey)){
				GUI.logger.info("For "+PageName+" No Change");
				GUI.consoleText.append("For "+PageName+" No Change"+"\n");
				continue;
			}
			if(MainSussexPageCreation.issueInNewPageCreation.contains(tableHtmlKey)){
				GUI.logger.info("Issue in creation of new page so please check and run it again "+PageName);
				GUI.consoleText.append("Issue in creation of new page so please check and run it again "+PageName+"\n");
				continue;
			}
			
			boolean checkTemplateAvailable = false;
			String fullFilePath = "";
			for(Entry<String,String> template:MainSussexPageCreation.template.entrySet()){
				String templateKey = "";
				templateKey = template.getKey();
				if(templateKey.contains(".html")){
					templateKey = templateKey.replaceAll(".html", "");
				}
				if(templateName.equalsIgnoreCase(templateKey)){
					checkTemplateAvailable = true;
					fullFilePath = template.getValue();
					break;
				}
			}
			if(checkTemplateAvailable){
				GUI.logger.info("Template "+templateName+" mentioned found in template folder");
				GUI.consoleText.append("Template "+templateName+" mentioned found in template folder"+"\n");				
				
				GUI.logger.info("Page Title "+PageName);
				GUI.consoleText.append("Page Title "+PageName+"\n");
				
				driver.get(fullFilePath);
				fullHTMLCode = getFullHTMLCode();
				CommonDataPour commonDataPour = new CommonDataPour();
				htmlCodeAfterContentPour = commonDataPour.getHomePageTemplateHTMLCode(htmlLinkedHashMap,fullHTMLCode,pageURLList);
				if((!htmlCodeAfterContentPour.equals("")) && (!htmlCodeAfterContentPour.isEmpty())){					
					GUI.logger.info("Ready to create final html file");
					GUI.consoleText.append("Ready to create final html file"+"\n");
					GUI.logger.info("Final HTML Code of page "+PageName+"--> "+htmlCodeAfterContentPour);
//					createPage(PageName,htmlCodeAfterContentPour);
					createHTMLPage(PageName,htmlCodeAfterContentPour,programeName);
//					System.out.println("Page Creation of " +PageName+" completed successfully");
				}else{
					GUI.logger.info("Issue in creating final html");
					GUI.consoleText.append("Issue in creating final html"+"\n");
				}
//				System.out.println("htmlCodeAfterContentPour----> "+htmlCodeAfterContentPour);
			}else{
				GUI.logger.info("Template "+templateName+" not found in template folder");
				GUI.consoleText.append("Template "+templateName+" not found in template folder"+"\n");
			}			
		}
	}else{
		GUI.logger.info("No new or updation in existing page found");
		GUI.consoleText.append("No new or updation in existing page found"+"\n");
	}
		
}
	public static boolean createNewBlankHTMLPage(String htmlPagePath,String pageName) throws Exception{
		System.out.println("createNewBlankHTMLPage");		
	/*		GUI.logger.info("Going to enter HTML code in "+pageName);
			GUI.consoleText.append("Going to enter HTML code in "+pageName+"\n");*/
		System.out.println("htmlPagePath "+htmlPagePath);
		File file = new File(htmlPagePath+"\\"+pageName+".html");
		String url = file.getAbsolutePath();
		System.out.println("url "+url);
		pageURLList.add(pageName+"|"+url);
		return true;
		
			/*if((!pageName.equals("")) || (!pageName.isEmpty())){				
				String url = "";
				Thread.sleep(3000);
				SeleniumUtil.waitForElementPresence(driver, By.xpath("//a[@title='Pages']"), waitSeconds).click();
				SeleniumUtil.waitForElementPresence(driver, By.xpath("//a[@class='btn btn-primary view_all_pages']"), waitSeconds).click();
				SeleniumUtil.waitForElementPresence(driver, By.xpath("//a[@aria-label='Add a page']"), waitSeconds).click();
				Thread.sleep(5000);
				SeleniumUtil.waitForElementPresence(driver, By.xpath("//input[@name='title']"), waitSeconds).sendKeys(pageName);
				Thread.sleep(2000);
				String verifyInteredValue = "";
				verifyInteredValue = SeleniumUtil.waitForElementPresence(driver, By.xpath("//input[@name='title']"), waitSeconds).getAttribute("value");
				SeleniumUtil.waitForElementPresence(driver, By.xpath("//button[text()='Save']"), waitSeconds).click();
				Thread.sleep(5000);
				
				if(verifyInteredValue.equalsIgnoreCase(pageName)){
					url = driver.getCurrentUrl();
//					System.out.println("url "+url);
					pageURLList.add(pageName+"|"+url);
					GUI.logger.info("Blank page "+pageName+" created successflly");
					GUI.consoleText.append("Blank page "+pageName+" created successflly\n");
					return true;
				}else{
					return false;
				}
			}else{
				return false;
			}*/
	}
	
	
	public static String getTableCount(LinkedHashMap<String,LinkedHashMap<String,ArrayList<String>>> htmllinkedMap,String pageName){
//		System.out.println("getTableCount");
//		System.out.println("pageName "+pageName);
		if(htmllinkedMap.size()>0){
		
			for(Entry<String,LinkedHashMap<String,ArrayList<String>>> tableHtmlEntrySet:htmllinkedMap.entrySet()){
				
				String tableHtmlKey  = tableHtmlEntrySet.getKey();
//				System.out.println("tableHtmlKey "+tableHtmlKey);
				
				LinkedHashMap<String,ArrayList<String>> htmlLinkedHashMap = tableHtmlEntrySet.getValue();
				
				String htmlValue = "";
				
				for(Entry<String,ArrayList<String>> htmlEntrySet:htmlLinkedHashMap.entrySet()){
					String htmlKey  = htmlEntrySet.getKey();					
										
					if(htmlKey.equalsIgnoreCase("PageTitle")){
						ArrayList<String> tempList = new ArrayList<String>();
						tempList = htmlEntrySet.getValue();
						if(tempList.size()>0){
							htmlValue = tempList.get(0);
							if(htmlValue.equalsIgnoreCase(pageName)){				
								return tableHtmlKey;
							}
						}
					}					
				}				
			}
			return "";
		}else{
			return "";
		}
	}	
	
	public static boolean verifyPageAlredyAvailableInMap(String pageName){	
		
		for(int i = 0;i<pageURLList.size();i++){
			String str = "";
			str = pageURLList.get(i);
			ArrayList<String> al = new ArrayList<String>();
			al = getListSplitedByChar(str, '|');
			
			for(int j = 0;j<al.size();j++){
				if(al.get(j).toString().equalsIgnoreCase(pageName)){
					return true;
				}
			}
		}
		return false;
	}
	
	public static void redirectToAllPages(String htmlPagePath) throws InterruptedException{
		
		GUI.logger.info("Fetching the details of already created pages...");
		GUI.consoleText.append("Fetching the details of already created pages...\n");
		
		try{			
			
			File folder = new File(htmlPagePath);
			if(folder !=null){
				 File[] listOfFiles = folder.listFiles();
		         
		         GUI.logger.info("Total count of already created pages "+listOfFiles.length);
				 GUI.consoleText.append("Total count of already created pages "+listOfFiles.length+"\n");
				 
		         if(listOfFiles.length>0){
		        	 for (int i = 0; i < listOfFiles.length; i++) {
			            	 String fileName = "";
			            	 fileName = listOfFiles[i].getName().trim();
			            	 String tempFileName = fileName.replace(".html", "");
			    	         pageURLList.add(tempFileName+"|"+htmlPagePath+"\\"+fileName);
		        	 } 
		        	 GUI.logger.info("Details of already existing pages fetched successfully.");
					 GUI.consoleText.append("Details of already existing pages fetched successfully."+"\n");	
		         }else{
		        	 GUI.logger.info("Not found any already existing page.");
					 GUI.consoleText.append("Not found any already existing page."+"\n");
		         }
			}
		}catch (Exception e) {
			// TODO: handle exception
			GUI.logger.info("Issue in getting the already existing page information.");	
			GUI.consoleText.append("Issue in getting the already existing page information."+"\n");
		}
	} 
	
	
/*	public static void getNoChangeListAndCreateDummyPage1(LinkedHashMap<String,LinkedHashMap<String,ArrayList<String>>> htmllinkedMap) throws Exception{
		
		if(rowCount == 0){
			ExcelUtils.createExcelFile(resourcesFolderName, pageDetailsFileName, pageDetailsSheetName);
			ExcelUtils.getExcelFile(resourcesFolderName, pageDetailsFileName);
			ExcelUtils.getSheet(pageDetailsSheetName);
			ExcelUtils.writeExcel(rowCount, 0, "Page Name", "yes", "");
			ExcelUtils.writeExcel(rowCount, 1, "URL", "", "");
			ExcelUtils.closeExcelFile(resourcesFolderName, pageDetailsFileName);
			rowCount++;
		}
		
		System.out.println("MainSussexPageCreation.listOfPageCreationType "+MainSussexPageCreation.listOfPageCreationType);
		
		if(MainSussexPageCreation.listOfPageCreationType.size()>0){
			
			for(Entry<String,LinkedHashMap<String,ArrayList<String>>> tableHtmlEntrySet:htmllinkedMap.entrySet()){
				
				String tableHtmlKey  = tableHtmlEntrySet.getKey();
				System.out.println("tableHtmlKey "+tableHtmlKey);
				
				LinkedHashMap<String,ArrayList<String>> htmlLinkedHashMap = tableHtmlEntrySet.getValue();
				
				String htmlValue = "";
				
				for(Entry<String,ArrayList<String>> htmlEntrySet:htmlLinkedHashMap.entrySet()){
					String htmlKey  = htmlEntrySet.getKey();					
										
					if(htmlKey.equalsIgnoreCase("PageTitle")){
						ArrayList<String> tempList = new ArrayList<String>();
						tempList = htmlEntrySet.getValue();
						if(tempList.size()>0){
							htmlValue = tempList.get(0);
						}
						break;
					}					
				}
				
				if(!(htmlValue.equals("")) || (!htmlValue.isEmpty())){
					
					for(int pageCreationTypeCount = 0; pageCreationTypeCount<MainSussexPageCreation.listOfPageCreationType.size();pageCreationTypeCount++){
						String value = "";
						value = MainSussexPageCreation.listOfPageCreationType.get(pageCreationTypeCount);
						ArrayList<String> tempList = new ArrayList<String>();
						tempList = getListSplitedByChar(value, '|');
						
						if(tempList.contains(htmlValue)){
							
							String pageName = "";
							String type = "";		
							
							boolean checkPageAvailable = false;
							
							for(int tempCount = 0; tempCount<tempList.size(); tempCount++){							
								if(tempCount == 0){pageName = tempList.get(tempCount);}
								if(tempCount == 1){type = tempList.get(tempCount);}
							}
							
							if(type.toLowerCase().equalsIgnoreCase("No Change")){
								break;
							}else{
								if(htmlValue.toLowerCase().equalsIgnoreCase(pageName.toLowerCase())){
									
									checkPageAvailable = verifyPageAlredyAvailable(pageName);
									
									if(type.equalsIgnoreCase("new")){
										if(!checkPageAvailable){
											createHTMLPage(pageName);
										}else{
											newPageAlreadyFoundList.add(tableHtmlKey);
										}
									}else if(type.equalsIgnoreCase("update")){
										if(checkPageAvailable){
											getUpdatePageDetails(pageName);
										}else{
											updatePageNoFoundList.add(tableHtmlKey);
										}
									}else{
										noChangeList.add(tableHtmlKey);
									}
									break;
								}
							}
						}
					}
				}else{
					System.out.println("Page name not found for page creation type");
				}
			}
		}else{
			System.out.println("Page Creation type not found in "+pageCreationFileName+" file");
		}
		System.out.println("noChangeList "+noChangeList);
		System.out.println("newPageAlreadyFoundList "+newPageAlreadyFoundList);
		System.out.println("updatePageNoFoundList "+updatePageNoFoundList);

	}
*/	
	public static LinkedHashMap<String,String> getTemplate(String filelocation){
		
		LinkedHashMap<String,String> template = new LinkedHashMap<String,String>();
		
/*		String filelocation = "";
		filelocation = configProperty.getProperty("tempatePagePath");
		*/
//		ArrayList filenames = new ArrayList();
 //       System.out.println("fileLocation pre"+filelocation);
        filelocation.replace("\\", "\\\\");
 //       System.out.println("fileLocation post"+filelocation);
        File folder = new File(filelocation);
        File[] listOfFiles = folder.listFiles();

        if(listOfFiles.length > 0){
 //       	GUI.consoleText.append("Reading Templates....\n");
        	for (int i = 0; i < listOfFiles.length; i++) {
                if (listOfFiles[i].isFile()) {
                    String filename = listOfFiles[i].getName();

                    if (filename.contains(".html") || filename.contains(".htm")) {
                    	String url = "";
                    	url = "file:///"+filelocation+"/"+filename;
                    	template.put(filename, url);                    
 //                       System.out.println(template);
                    }
                }
            }
        }else{
        	GUI.consoleText.append("No Any Template found\n");
        }		
		return template;
	}
	
	
	public static boolean verifyPageAlredyAvailable(String pageName) throws InterruptedException{		
		System.out.println("verifyPageAlredyAvailable");
		boolean verifyPage = false;		
				
		Thread.sleep(10000);
		SeleniumUtil.waitForElementPresence(driver, By.xpath("//a[@title='Pages']"), waitSeconds).click();
		Thread.sleep(10000);
		SeleniumUtil.waitForElementPresence(driver, By.xpath("//a[@class='btn btn-primary view_all_pages']"), waitSeconds).click();
		Thread.sleep(10000);
		
		SeleniumUtil.waitForElementPresence(driver, By.xpath("(//div[@id='content']//tbody[@class='collectionViewItems']//tr)//td[1]//a"), waitSeconds);
		int countOfPage = driver.findElements(By.xpath("(//div[@id='content']//tbody[@class='collectionViewItems']//tr)//td[1]//a")).size();
		
		String url = "";
		System.out.println("countOfPage "+countOfPage);
		if(countOfPage>0){
			for(int pageCount = 1;pageCount <=countOfPage; pageCount++){
				String webPageName = "";
				webPageName = SeleniumUtil.waitForElementPresence(driver, By.xpath("(//div[@id='content']//tbody[@class='collectionViewItems']//tr)["+pageCount+"]//td[1]//a"), waitSeconds).getText();
				if(webPageName.equalsIgnoreCase(pageName)){
					verifyPage = true;
					break;
				}
			}
		}		
		return verifyPage;
	}
	
	
	
	public static void getUpdatePageDetails(String pageName) throws IOException, InterruptedException{
		System.out.println("getUpdatePageDetails");		
		Thread.sleep(10000);
		SeleniumUtil.waitForElementPresence(driver, By.xpath("//a[@title='Pages']"), waitSeconds).click();
		Thread.sleep(5000);
		SeleniumUtil.waitForElementPresence(driver, By.xpath("//a[@class='btn btn-primary view_all_pages']"), waitSeconds).click();
		Thread.sleep(5000);
		
		SeleniumUtil.waitForElementPresence(driver, By.xpath("(//div[@id='content']//tbody[@class='collectionViewItems']//tr)//td[1]//a"), waitSeconds);
		int countOfPage = driver.findElements(By.xpath("(//div[@id='content']//tbody[@class='collectionViewItems']//tr)//td[1]//a")).size();
		
		String url = "";
		System.out.println("countOfPage "+countOfPage);
		for(int pageCount = 1;pageCount <=countOfPage; pageCount++){
			String webPageName = "";
			webPageName = SeleniumUtil.waitForElementPresence(driver, By.xpath("(//div[@id='content']//tbody[@class='collectionViewItems']//tr)["+pageCount+"]//td[1]//a"), waitSeconds).getText();
			if(webPageName.equalsIgnoreCase(pageName)){
				SeleniumUtil.waitForElementPresence(driver, By.xpath("(//div[@id='content']//tbody[@class='collectionViewItems']//tr)["+pageCount+"]//td[1]//a"), waitSeconds).click();
				Thread.sleep(5000);
				String header = "";
				header = SeleniumUtil.waitForElementPresence(driver, By.xpath("//div[@id='wiki_page_show']//div[@class='show-content user_content clearfix enhanced']//h1"), waitSeconds).getText();
				if(webPageName.toLowerCase().equalsIgnoreCase(header.toLowerCase())){
					url = driver.getCurrentUrl();
					ExcelUtils.getExcelFile(resourcesFolderName, pageDetailsFileName);
					ExcelUtils.getSheet(pageDetailsSheetName);
					ExcelUtils.writeExcel(rowCount, 0, pageName.trim(), "yes", "");
					ExcelUtils.writeExcel(rowCount, 1, url, "", "");
					ExcelUtils.closeExcelFile(resourcesFolderName, pageDetailsFileName);	
					rowCount++;
					break;
				}
			}
		}
	}
	
	
	public static String createDummyPageAndgStoreDetails(String templateName) throws InterruptedException{
		
		String url = "";
		Thread.sleep(10000);
		SeleniumUtil.waitForElementPresence(driver, By.xpath("//a[@title='Pages']"), waitSeconds).click();
		Thread.sleep(2000);
		SeleniumUtil.waitForElementPresence(driver, By.xpath("//a[@class='btn btn-primary view_all_pages']"), waitSeconds).click();
		Thread.sleep(2000);
		SeleniumUtil.waitForElementPresence(driver, By.xpath("//a[@aria-label='Add a page']"), waitSeconds).click();
		Thread.sleep(5000);
		SeleniumUtil.waitForElementPresence(driver, By.xpath("//input[@name='title']"), waitSeconds).sendKeys(templateName);
//		Thread.sleep(10000);
//		SeleniumUtil.waitForElementPresence(driver, By.xpath("//a[contains(text(),'HTML Editor')]"), waitSeconds).click();
		Thread.sleep(2000);
		String verifyInteredValue = "";
		verifyInteredValue = SeleniumUtil.waitForElementPresence(driver, By.xpath("//input[@name='title']"), waitSeconds).getAttribute("value");
		System.out.println("verifyInteredValue "+verifyInteredValue);
		SeleniumUtil.waitForElementPresence(driver, By.xpath("//button[text()='Save']"), waitSeconds).click();
		Thread.sleep(10000);
		
		if(verifyInteredValue.equalsIgnoreCase(templateName)){
			url = driver.getCurrentUrl();
			System.out.println("url "+url);
		}
		return url; 
	}
	
	public static void getInputAndPourContent1(LinkedHashMap<String,LinkedHashMap<String,ArrayList<String>>> htmllinkedMap) throws Exception {
		// TODO Auto-generated method stub
	
		Thread.sleep(5000);		
		
		System.out.println("Count of Table "+htmllinkedMap.size());
		
		ArrayList<String> pageURLList = new ArrayList<String>();
		pageURLList = getHTMLPageURL();
		System.out.println("pageURLList "+pageURLList);
		if(pageURLList.size()>0){
		
			for(Entry<String,LinkedHashMap<String,ArrayList<String>>> tableHtmlEntrySet:htmllinkedMap.entrySet()){
			
			String tableHtmlKey  = tableHtmlEntrySet.getKey();
			System.out.println("tableHtmlKey "+tableHtmlKey);
			
			if(MainSussexPageCreation.updatePageNoFoundList.contains(tableHtmlKey)){continue;}
			
			
			LinkedHashMap<String,ArrayList<String>> htmlLinkedHashMap = tableHtmlEntrySet.getValue();
			String htmlCodeAfterContentPour = "";
			String fullHTMLCode = "";
			
			String PageName = "";
			String templateName = "";
			
			for(Entry<String,ArrayList<String>> htmlEntrySet:htmlLinkedHashMap.entrySet()){
				
				String htmlKey  = htmlEntrySet.getKey();
				System.out.println("htmlKey "+htmlKey);
				
				if(htmlKey.equalsIgnoreCase("PageTitle")){
					ArrayList<String> tempList = new ArrayList<String>();
					tempList = htmlEntrySet.getValue();
					System.out.println("tempList "+tempList);
					String templateCode = "";
					for(int i = 0;	i < tempList.size();	i++){
						templateCode = templateCode+tempList.get(i).toString().trim();
						if(i == 0){
							PageName = tempList.get(i).toString().trim();
						}						
					}
				}
				
				if(htmlKey.equalsIgnoreCase("TemplateName")){
					String templateCode = "";
					ArrayList<String> tempList = new ArrayList<String>();
					tempList = htmlEntrySet.getValue();					
					
					for(int i = 0;	i < tempList.size();	i++){
						templateCode = templateCode+tempList.get(i).toString().trim();
						if(i == 0){
							templateName = tempList.get(i).toString().trim();
						}						
					}	
				}	
			}	
			
			boolean checkTemplateAvailable = false;
			String fullFilePath = "";
			for(Entry<String,String> template:MainSussexPageCreation.template.entrySet()){
				String templateKey = "";
				templateKey = template.getKey();
				if(templateKey.contains(".html")){
					templateKey = templateKey.replaceAll(".html", "");
				}
				if(templateName.equalsIgnoreCase(templateKey)){
					checkTemplateAvailable = true;
					fullFilePath = template.getValue();
					break;
				}
			}
			System.out.println("checkTemplateAvailable "+checkTemplateAvailable);
			if(checkTemplateAvailable){
				System.out.println("PageName "+PageName);
				System.out.println("templateName "+templateName);
				driver.get(fullFilePath);
				fullHTMLCode = getFullHTMLCode();
				CommonDataPour commonDataPour = new CommonDataPour();
				htmlCodeAfterContentPour = commonDataPour.getHomePageTemplateHTMLCode(htmlLinkedHashMap,fullHTMLCode,pageURLList);
				if((!htmlCodeAfterContentPour.equals("")) && (!htmlCodeAfterContentPour.isEmpty())){
					createPage(PageName,htmlCodeAfterContentPour);
					System.out.println("Page Creation of " +PageName+" completed successfully");
				}else{
					System.out.println("HTML code not found");
				}
				System.out.println("htmlCodeAfterContentPour----> "+htmlCodeAfterContentPour);
			}else{
				System.out.println(templateName+" Template not found");
			}			
		}
	}else{
		System.out.println("No any URL Found");
	}
		
}
	
	
	public static void createHTMLPage(String PageName,String HTMLCode,String programeName) throws InterruptedException{
//		System.out.println("createHTMLPage");
		
		String htmlDir = programeName;
		
		File file = new File(htmlDir+"\\"+PageName+".html");
		
		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;
		
		try {
				fileWriter = new FileWriter(file);
				bufferedWriter = new BufferedWriter(fileWriter);
			
				bufferedWriter.append(HTMLCode);

				 GUI.logger.info("HTML Page "+PageName+" created successfully.");
	    		 GUI.consoleText.append("HTML Page "+PageName+" created successfully.\n");	
				bufferedWriter.flush();
				fileWriter.flush();

			} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}finally{
			
				try {

					bufferedWriter.close();
					fileWriter.close();
				} catch (IOException e) {
			// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		
	}
	
	public static void createPage(String PageName,String HTMLCode) throws InterruptedException{
				
		for(int i = 0;i<pageURLList.size();i++){
			String page = "";
			page = pageURLList.get(i);
			ArrayList<String> tempList = new ArrayList<String>();
			tempList = getListSplitedByChar(page, '|');
			
			String tempPageName = "";
			String url = "";		
			
			for(int tempCount = 0; tempCount<tempList.size(); tempCount++){							
				if(tempCount == 0){tempPageName = tempList.get(tempCount);}
				if(tempCount == 1){url = tempList.get(tempCount);}
			}
//			System.out.println("tempPageName "+tempPageName);
			if(PageName.equalsIgnoreCase(tempPageName)){	
				GUI.logger.info("Going to pour html code in "+PageName);
				GUI.consoleText.append("Going to pour html code in "+PageName+"\n");
				
				try{
					
				}catch (Exception e) {
					// TODO: handle exception
				}
				
				driver.get(url);			
				String header = "";
				header = SeleniumUtil.waitForElementPresence(driver, By.xpath("//div[@id='wiki_page_show']//div[@class='show-content user_content clearfix enhanced']//h1"), waitSeconds).getText();
				Thread.sleep(2000);
				if(PageName.equalsIgnoreCase(header)){
					SeleniumUtil.waitForElementPresence(driver, By.xpath("//*[@class='btn edit-wiki']"), waitSeconds).click();					
					SeleniumUtil.waitForElementPresence(driver, By.xpath("//a[contains(text(),'HTML Editor')]"), waitSeconds).click();
					Thread.sleep(2000);
					SeleniumUtil.waitForElementPresence(driver, By.xpath("//textarea[@id='wiki_page_body']"), waitSeconds).clear();
					Thread.sleep(2000);
						
					WebElement element = driver.findElement(By.xpath("//textarea[@id='wiki_page_body']")); 
					JavascriptExecutor js = (JavascriptExecutor) driver;  
					js.executeScript("arguments[0].value = arguments[1];", element, HTMLCode);
					
	//				SeleniumUtil.waitForElementPresence(driver, By.xpath("//textarea[@id='wiki_page_body']"), waitSeconds).sendKeys(HTMLCode);		
					Thread.sleep(2000);
					SeleniumUtil.waitForElementPresence(driver, By.xpath("//button[text()='Save']"), waitSeconds).click();
					Thread.sleep(5000);
					String checkPageUpdate = "";
					checkPageUpdate = SeleniumUtil.waitForElementPresence(driver, By.xpath("//div[@id='wiki_page_show']//div[@class='show-content user_content clearfix enhanced']//h1"), waitSeconds).getText();
					if(!header.equalsIgnoreCase(checkPageUpdate)){
						GUI.logger.info("Page Name not matched with header after enter html code");
						GUI.consoleText.append("Page Name not matched with header after enter html code"+"\n");
					}else{
						GUI.logger.info("Page "+PageName +" created successfully");
						GUI.consoleText.append("Page "+PageName +" created successfully"+"\n");
					}
				}else{
					GUI.logger.info("Page Name not matched with header");
					GUI.consoleText.append("Page Name not matched with header"+"\n");
				}
				break;
			}
		}
	}
	
	public static void createPage1(String PageName,String HTMLCode,ArrayList<String> pageURL) throws InterruptedException{
		System.out.println("createPage");
	/*	SeleniumUtil.waitForElementPresence(driver, By.xpath("//a[@title='Pages']"), waitSeconds).click();
		Thread.sleep(5000);
		SeleniumUtil.waitForElementPresence(driver, By.xpath("//a[@class='btn btn-primary view_all_pages']"), waitSeconds).click();
		Thread.sleep(5000);*/
		
		System.out.println("PageName "+PageName);
		System.out.println("pageURL "+pageURL);
		
		ArrayList<String> tempPageURL = new ArrayList<String>();
		tempPageURL = pageURL;
		
		for(int i = 0;i<tempPageURL.size();i++){
			String page = "";
			page = tempPageURL.get(i);
			ArrayList<String> tempList = new ArrayList<String>();
			tempList = getListSplitedByChar(page, '|');
			
			String tempPageName = "";
			String url = "";		
			
			for(int tempCount = 0; tempCount<tempList.size(); tempCount++){							
				if(tempCount == 0){tempPageName = tempList.get(tempCount);}
				if(tempCount == 1){url = tempList.get(tempCount);}
			}
			System.out.println("tempPageName "+tempPageName);
			if(PageName.equalsIgnoreCase(tempPageName)){			
				driver.get(url);
				Thread.sleep(10000);
				String header = "";
				header = SeleniumUtil.waitForElementPresence(driver, By.xpath("//div[@id='wiki_page_show']//div[@class='show-content user_content clearfix enhanced']//h1"), waitSeconds).getText();
				Thread.sleep(2000);
				if(PageName.equalsIgnoreCase(header)){
					SeleniumUtil.waitForElementPresence(driver, By.xpath("//*[@class='btn edit-wiki']"), waitSeconds).click();
					Thread.sleep(2000);	
					SeleniumUtil.waitForElementPresence(driver, By.xpath("//a[contains(text(),'HTML Editor')]"), waitSeconds).click();
					Thread.sleep(2000);
					SeleniumUtil.waitForElementPresence(driver, By.xpath("//textarea[@id='wiki_page_body']"), waitSeconds).clear();
					Thread.sleep(2000);
					SeleniumUtil.waitForElementPresence(driver, By.xpath("//textarea[@id='wiki_page_body']"), waitSeconds).sendKeys(HTMLCode);				
					SeleniumUtil.waitForElementPresence(driver, By.xpath("//button[text()='Save']"), waitSeconds).click();
					Thread.sleep(10000);
				}else{
					System.out.println("Page Name not matched with header");
				}
				break;
			}
		}
	}
	
	
	public static ArrayList<String> getHTMLPageURL() throws InterruptedException, IOException{
		System.out.println("getHTMLPageURL");
		
		int countOfRow = ExcelUtils.getRowCount(resourcesFolderName, pageDetailsFileName, pageDetailsSheetName);
		System.out.println("countOfRow "+countOfRow);
		ArrayList<String> al = new ArrayList<String>();
		
		for(int rowNum = 1;rowNum<=countOfRow;rowNum++){
			String pageName = "";
			String url = "";
			pageName = (String) ExcelUtils.readExcelUserInput(resourcesFolderName, pageDetailsFileName, pageDetailsSheetName, rowNum, 0);
			url = (String) ExcelUtils.readExcelUserInput(resourcesFolderName, pageDetailsFileName, pageDetailsSheetName, rowNum, 1);	
			if((!pageName.isEmpty()) || (!pageName.equals(""))){
				al.add(pageName+"|"+url);
			}
		}
		System.out.println("al "+al);
		return al;
	}
	
	public static String pourdata(String pourData,String templateName) throws Exception{
		System.out.println("pourdata");
		String url = "";
	/*	
		
		pageCount++;
		if(pageCount == 1){
			driver.get("https://canvas.instructure.com/login/canvas");
			
			SeleniumUtil.waitForElementPresence(driver, By.id("pseudonym_session_unique_id"), waitSeconds).sendKeys("rishiraj.gera@magicsw.com");
			
			SeleniumUtil.waitForElementPresence(driver, By.id("pseudonym_session_password"), waitSeconds).sendKeys("Pass@1word");
			
			SeleniumUtil.waitForElementPresence(driver, By.xpath("(//button[@class='Button Button--login'])[1]"), waitSeconds).click();
		}
		*/
		
	//	SeleniumUtil.waitForElementPresence(driver, By.xpath("//span[text()='Demo_Data_Pour']"), waitSeconds).click();
		
		SeleniumUtil.waitForElementPresence(driver, By.xpath("//a[@title='Pages']"), waitSeconds).click();
		Thread.sleep(5000);
		SeleniumUtil.waitForElementPresence(driver, By.xpath("//a[@class='btn btn-primary view_all_pages']"), waitSeconds).click();
		Thread.sleep(5000);
		SeleniumUtil.waitForElementPresence(driver, By.xpath("//a[@aria-label='Add a page']"), waitSeconds).click();
		
		SeleniumUtil.waitForElementPresence(driver, By.xpath("//input[@name='title']"), waitSeconds).sendKeys(templateName);
		
		SeleniumUtil.waitForElementPresence(driver, By.xpath("//a[contains(text(),'HTML Editor')]"), waitSeconds).click();
		Thread.sleep(10000);
		SeleniumUtil.waitForElementPresence(driver, By.xpath("//textarea[@id='wiki_page_body']"), waitSeconds).sendKeys(pourData);
		
		SeleniumUtil.waitForElementPresence(driver, By.xpath("//button[text()='Save']"), waitSeconds).click();
		Thread.sleep(10000);
		url = driver.getCurrentUrl();
		System.out.println("url "+url);
		return url; 
	}
	
	public static void createHTMLPage(String pageName) throws Exception{
		System.out.println("createHTMLPage");
		
		System.out.println("rowCount "+rowCount);
			String url = "";
			if((!pageName.equals("")) || (!pageName.isEmpty())){				
				url = createDummyPageAndgStoreDetails(pageName);
				ExcelUtils.getExcelFile(resourcesFolderName, pageDetailsFileName);
				ExcelUtils.getSheet(pageDetailsSheetName);
				ExcelUtils.writeExcel(rowCount, 0, pageName.trim(), "yes", "");
				ExcelUtils.writeExcel(rowCount, 1, url, "", "");
				ExcelUtils.closeExcelFile(resourcesFolderName, pageDetailsFileName);	
				rowCount++;
			}
	}
	
	public static LinkedHashMap<String,String> getElementViseHTMLCode(){
		
		LinkedHashMap<String,String> templateHTMLCode = new LinkedHashMap<String,String>();
		List<WebElement> listOfComponent =  driver.findElements(By.xpath("//*[@id]"));		
		
		for(int componentNumber = 0; componentNumber<listOfComponent.size();componentNumber++){
			String componentName = "";
			String htmlCode = "";
			componentName = listOfComponent.get(componentNumber).getAttribute("id").toString().trim();
			htmlCode = listOfComponent.get(componentNumber).getAttribute("outerHTML");			
			if(!componentName.isEmpty() || !componentName.equals("") ){
				templateHTMLCode.put(componentName, htmlCode);
			}
		}
		/*for(Entry<String, String> entry:templateHTMLCode.entrySet()){
			System.out.println("Key "+entry.getKey());
			System.out.println("Val "+entry.getValue());
		}*/
		
		return templateHTMLCode;
	}
	
	
	public static LinkedHashMap<String,String> getComponentHTMLCode(){
//		System.out.println("getComponentHTMLCode");
		LinkedHashMap<String,String> templateHTMLCode = new LinkedHashMap<String,String>();
		int listOfComponent =  driver.findElements(By.xpath("//*[@id]")).size();		
		
		for(int componentNumber = 1; componentNumber<=listOfComponent;componentNumber++){
			String componentName = "";
			String htmlCode = "";
			componentName = driver.findElement(By.xpath("(//*[@id])["+componentNumber+"]")).getAttribute("id").toString().trim();
			List<WebElement> listOfSubComponent =  driver.findElements(By.xpath("(//*[@id])["+componentNumber+"]//*[@component]"));
			
		/*	if(listOfSubComponent.size() == 0){
				listOfSubComponent =  driver.findElements(By.xpath("(//*[@id]/following-sibling::div)["+componentNumber+"][@component]"));	
				htmlCode = driver.findElement(By.xpath("(//*[@id]/following-sibling::div)["+componentNumber+"][@component]")).getAttribute("outerHTML");
			}else{
				htmlCode = driver.findElement(By.xpath("(//*[@id])["+componentNumber+"]//*[@component]")).getAttribute("outerHTML");
			}*/
			
//			ArrayList<String> subComponentList = new ArrayList<String>();
			
			for(int subComponent = 1; subComponent<=listOfSubComponent.size(); subComponent++){
				String subComponentName = "";
				subComponentName = listOfSubComponent.get((subComponent-1)).getAttribute("component").toString().trim();
				htmlCode = driver.findElement(By.xpath("((//*[@id])["+componentNumber+"]//*[@component])["+subComponent+"]")).getAttribute("outerHTML");
				templateHTMLCode.put(componentName+"|"+subComponentName, htmlCode);				
			}
		}
		return templateHTMLCode;
	}
	
	public static String getFullHTMLCode(){
		String fullHtmlCode = "";		
		fullHtmlCode =  driver.findElement(By.xpath("//body")).getAttribute("innerHTML");
		return fullHtmlCode;
	}
	
	
/*	public static void getModuleHomePageTemplateHTMLCode(LinkedHashMap<String,ArrayList<String>> htmlLinkedHashMap) throws Exception{
			System.out.println("getModuleHomePageTemplateHTMLCode");
			System.out.println("htmlLinkedHashMap "+htmlLinkedHashMap.size());

			LinkedHashMap<String,String> templateHTMLCode = new LinkedHashMap<String,String>();
			templateHTMLCode = getElementViseHTMLCode();
			
			Thread.sleep(10000);
			
			System.out.println("templateHTMLCode "+templateHTMLCode.size());
			
			ArrayList<String> extraTagList = new ArrayList<String>();
			
			for(Entry<String,ArrayList<String>> htmlEntrySet:htmlLinkedHashMap.entrySet()){
				String inputHTMLKey = "";
				inputHTMLKey = htmlEntrySet.getKey();
				if(inputHTMLKey.equalsIgnoreCase("TemplateName")){
					continue;
				}
				boolean htmlKey = false;				
				for(Entry<String,String> templateEntrySet:templateHTMLCode.entrySet()){
					String templateHTMLKey = "";
					templateHTMLKey = templateEntrySet.getKey();
					if(inputHTMLKey.contains(templateHTMLKey)){						
						htmlKey = true;
					}	
				}
				if(!htmlKey){
					extraTagList.add(inputHTMLKey);
					System.out.println("inputHTMLKeyNotFound "+inputHTMLKey);
				}
			}
			System.out.println("extraTagList "+extraTagList.size());
			if(extraTagList.size() == 0){
				
				String fullHTMLCode = "";
				fullHTMLCode = getFullHTMLCode();
				System.out.println("fullHTMLCode "+fullHTMLCode);
				
				for(Entry<String,ArrayList<String>> htmlEntrySet:htmlLinkedHashMap.entrySet()){
					String changedHTMLCode = "";
					String htmlKey  = htmlEntrySet.getKey().trim();
					System.out.println("htmlKey "+htmlKey);
					if(htmlKey.equalsIgnoreCase("TemplateName")){
						continue;
					}
					String htmlCode = "";
					htmlCode = templateHTMLCode.get(htmlKey);
					System.out.println("htmlCode "+htmlCode);
				
					ArrayList<String> tempList = new ArrayList<String>();
					tempList = htmlEntrySet.getValue();				
				
				
				System.out.println("changedHTMLCode "+changedHTMLCode);
				pageHtmlCode = pageHtmlCode+changedHTMLCode;				
			}
			}else{
				System.out.println("Blocks Not Matched");
				System.out.println("extraTagList "+extraTagList);
			}
		}
*/
	public static String readFileAsString(String fileName)throws Exception{
		String data = "";
		data = new String(Files.readAllBytes(Paths.get(fileName)));
		return data;
	}


	private static int countOfTile(LinkedHashMap<String,ArrayList<String>> htmlLinkedHashMap,String Type){
		
		int countOfTile = 0;
		
		for(Entry<String,ArrayList<String>> htmlEntrySet:htmlLinkedHashMap.entrySet()){
			boolean checkAvailable = false;
			String templateHTMLKey = "";
			templateHTMLKey = htmlEntrySet.getKey();
			if(templateHTMLKey.equalsIgnoreCase(Type+"Main")){continue;}
			if(templateHTMLKey.contains(Type)){countOfTile++;}
			}
		return countOfTile;	
	}
		
	
	
	public String getMainBlockAfterDelete(LinkedHashMap<String,ArrayList<String>> htmlLinkedHashMap,LinkedHashMap<String,String> templateHTMLCode,String elementHTMLCode,String Type){
		System.out.println("getMainBlockAfterDelete");
		
		ArrayList<String> htmlNotFoundInInputList = new ArrayList<String>();
		
		for(Entry<String,String> templateEntrySet:templateHTMLCode.entrySet()){
			boolean checkAvailable = false;
			String templateHTMLKey = "";
			templateHTMLKey = templateEntrySet.getKey();
			if(templateHTMLKey.toLowerCase().contains("main")){continue;}
			if(templateHTMLKey.contains(Type)){
				for(Entry<String,ArrayList<String>> htmlEntrySet:htmlLinkedHashMap.entrySet()){
					String changedHTMLCode = "";
					changedHTMLCode  = htmlEntrySet.getKey().trim();
					if(changedHTMLCode.equalsIgnoreCase(templateHTMLKey)){						
						checkAvailable = true;
						break;
					}
				}
				if(!checkAvailable){
					htmlNotFoundInInputList.add(templateEntrySet.getValue());
				}
			}
		}
		if(htmlNotFoundInInputList.size()>0){
			for(int count = 0;count < htmlNotFoundInInputList.size(); count++){
				String tempCode = "";
				tempCode = htmlNotFoundInInputList.get(count).trim();
				elementHTMLCode = elementHTMLCode.replaceFirst(tempCode, "");
			}
		}
		return elementHTMLCode;
	}
	
	
	public String getIDBlockAfterDelete(LinkedHashMap<String,ArrayList<String>> htmlLinkedHashMap,LinkedHashMap<String,String> templateHTMLCode,String fullHTMLCode){
//		System.out.println("getIDBlockAfterDelete");
		
		ArrayList<String> htmlNotFoundInInputList = new ArrayList<String>();
		
		for(Entry<String,String> templateEntrySet:templateHTMLCode.entrySet()){
			boolean checkAvailable = false;
			String templateHTMLKey = "";
			templateHTMLKey = templateEntrySet.getKey();
			if(templateHTMLKey.toLowerCase().equalsIgnoreCase("templatename")){continue;}
				for(Entry<String,ArrayList<String>> htmlEntrySet:htmlLinkedHashMap.entrySet()){
					String changedHTMLCode = "";
					changedHTMLCode  = htmlEntrySet.getKey().trim();
					if(changedHTMLCode.equalsIgnoreCase(templateHTMLKey)){						
						checkAvailable = true;
						break;
					}
				}
				if(!checkAvailable){
					htmlNotFoundInInputList.add(templateEntrySet.getValue());
				}
			
		}
		if(htmlNotFoundInInputList.size()>0){
			for(int count = 0;count < htmlNotFoundInInputList.size(); count++){
				String tempCode = "";
				tempCode = htmlNotFoundInInputList.get(count).trim();
				fullHTMLCode = fullHTMLCode.replace(tempCode, "");
			}
		}
		return fullHTMLCode;
	}
	
	
	
	private static String getHTMLOfSyllabusAfterDeleteTag(LinkedHashMap<String,String> templateHTMLCode,ArrayList<String> tempList,String htmlCode){
		
		ArrayList<String> allTagList = new ArrayList<String>();
		allTagList = deleteTagFromSyllabus();
		
		ArrayList<String> tagNotFoundList = new ArrayList<String>();
		
		boolean goToPageMain = false;
		for(int i = 0;i < allTagList.size(); i++){
			String templateData = "";
			templateData = allTagList.get(i);
			boolean checkTemplateData = false;						
			for(int j = 0; j<tempList.size(); j++){
				String deleteTag = "";
				deleteTag = tempList.get(j).toString().trim();
				ArrayList<String> splitByCharList = new ArrayList<String>();
				splitByCharList = getListSplitedByChar(deleteTag,'|');
				deleteTag = splitByCharList.get(0);
				
				if(templateData.equalsIgnoreCase(deleteTag)){
					if(templateData.equalsIgnoreCase("GoToPage")){
						goToPageMain = true;
					}								
					checkTemplateData = true;
				}
			}
			if(!checkTemplateData){
				tagNotFoundList.add(templateData);
			}
		}
		if(!goToPageMain){
			tagNotFoundList.add("GoToPageMain");
			tagNotFoundList.remove("GoToPage");
		}
		System.out.println("tagNotFoundList "+tagNotFoundList);
		for(int removeTagCount = 0; removeTagCount<tagNotFoundList.size(); removeTagCount++){
			String tag = "";
			tag = tagNotFoundList.get(removeTagCount).trim();
			for(Entry<String,String> templateEntrySet:templateHTMLCode.entrySet()){
				String templateHTMLKey = "";
				templateHTMLKey = templateEntrySet.getKey();
				if(templateHTMLKey.equalsIgnoreCase(tag)){
					htmlCode = htmlCode.replaceFirst(templateEntrySet.getValue(), "");
				}
			}
		}
		return htmlCode;
	}
	
	
	private static ArrayList<String> deleteTagFromSyllabus(){
		
		ArrayList<String> syllabusArrayList = new ArrayList<String>();
		
		syllabusArrayList.add("Heading");
		syllabusArrayList.add("Para");
		syllabusArrayList.add("GoToPage");
		
		return syllabusArrayList;
	}
	
	
	private String pourDataInTag(ArrayList<String> tempList,String htmlCode){
		System.out.println("pourDataAndGetHTML");
		String newHTMLCode = "";
//		System.out.println("tempList.size() "+tempList.size());
		
		for(int i = 0;	i < tempList.size();	i++){
			String templateCode = "";
			templateCode = tempList.get(i).toString().trim();
			ArrayList subStringList = new ArrayList();
			subStringList = getListSplitedByChar(templateCode,'|');
			
			String placeHolder = "";
	//		System.out.println("subStringList "+subStringList.size());
			for(int spanCount = 0; spanCount < subStringList.size(); spanCount++){
	//			System.out.println("spanCount "+spanCount);
				String spanText = "";
				spanText = subStringList.get(spanCount).toString().trim();
				if(spanCount == 0){
					placeHolder = spanText;
				}else{
					newHTMLCode = newHTMLCode+writeImageAltAndURL(htmlCode,placeHolder);
				}				
			}
		}
		System.out.println("pourDataAndGetHTMLnewHTMLCode "+newHTMLCode);
		return newHTMLCode;
	}
	
	
	public String pourDataInBlock(ArrayList<String> tempList,String htmlCode){
		/*System.out.println("pourDataInBlock");
		System.out.println("tempList.size() "+tempList.size());*/
		for(int i = 0;	i < tempList.size();	i++){
			String templateCode = "";
			templateCode = tempList.get(i).toString().trim();
			ArrayList subStringList = new ArrayList();
			subStringList = getListSplitedByChar(templateCode,'|');
			
			String placeHolder = "";
		//	System.out.println("subStringList "+subStringList.size());
			for(int spanCount = 0; spanCount < subStringList.size(); spanCount++){
		//		System.out.println("spanCount "+spanCount);
				String spanText = "";
				spanText = subStringList.get(spanCount).toString().trim();
				if(spanCount == 0){
					placeHolder = spanText;
				}else{
					int getTagType = 0;
					getTagType = getTagType(htmlCode,placeHolder);
			//		System.out.println("getTagType "+getTagType);
					if(getTagType == 1){
						htmlCode = getNewHtmlCode(htmlCode,placeHolder,spanText);
					}else if(getTagType == 2){
						String lowerCaseAlt = placeHolder.toLowerCase();
						if(lowerCaseAlt.equalsIgnoreCase("alt") || lowerCaseAlt.contains("alt") ){
							htmlCode = writeImageAltAndURL(htmlCode,spanText);
						}else{
							htmlCode = changeAttributeValue(htmlCode,placeHolder,spanText);
						}
					}else{
						System.out.println("Given value not found in HTML Template");
					}
				}				
			}
		}
		System.out.println("pourDataAndGetHTMLnewHTMLCode "+htmlCode);
		return htmlCode;
	}
	
	// This method return 1 is the placeholder is of typeTag, 2 id placeholder is of type attribute and 0 if not found in htmlCode
	
	private static int getTagType(String htmlCode,String placeHolder){
		System.out.println("getTagType");
		
		String str = "";		
		boolean checkEndTag = false;
		boolean checkIdTag = false;
		
		for(int i = 0;i<htmlCode.length(); i++){
			if(!checkEndTag){
				str = str+htmlCode.charAt(i);
			}
				if(str.contains("id=\""+placeHolder+"\"")){
					checkIdTag = true;
					break;					
				}
		}
		if(checkIdTag){
			return 1;
		}else{
			str = "";
			checkEndTag = false;
			checkIdTag = false;
			for(int i = 0;i<htmlCode.length(); i++){
				if(!checkEndTag){
					str = str+htmlCode.charAt(i);
				}
					if(str.contains(placeHolder+"=\"")){
						checkIdTag = true;
						break;					
					}
				}
			if(checkIdTag){
				return 2;
			}else{
				return 0;
			}
		}
	}
	
	
/*	private static String pourDataInTag(ArrayList<String> tempList,String htmlCode){
		System.out.println("pourDataInTag");
		String newHTMLCode = "";
		
		for(int i = 0;	i < tempList.size();	i++){
			String templateCode = "";
			templateCode = tempList.get(i).toString().trim();
			ArrayList subStringList = new ArrayList();
			subStringList = getListSplitedByChar(templateCode,'|');
			
			String placeHolder = "";
			
			for(int spanCount = 0; spanCount < subStringList.size(); spanCount++){
				String spanText = "";
				spanText = subStringList.get(spanCount).toString().trim();
				if(spanCount == 0){
					placeHolder = spanText;
				}else{
					newHTMLCode = newHTMLCode+getNewHtmlCode(htmlCode,placeHolder,spanText);
				}				
			}
		}
		System.out.println("pourDataAndGetHTMLnewHTMLCode "+newHTMLCode);
		return newHTMLCode;
	}*/
	
	
	public static String getHtmlCodeAfterAttributeChange(String htmlCode,String component,String placeHolder,String pourData){
		String str = "";
		String componentStr = "";
		String titleAttribute = "";
		
		int countOfQuote = 0;	
		int countOfPrarThisis = 0;
		boolean checkPlaceHolder = false;
		
		for(int i = 0;i<htmlCode.length(); i++){			
			str = str+htmlCode.charAt(i);
			if(countOfQuote == 0 || countOfQuote == 1){
				if(str.contains("component=\""+component+"\"")){
					countOfQuote++;
					if(countOfQuote == 1){
						for(int j = i-1;j<=i;j--){
							if(htmlCode.charAt(j) == '<'){
								componentStr = componentStr +htmlCode.charAt(j);
								componentStr = new StringBuilder(componentStr).reverse().toString();
								countOfQuote++;
								break;
							}else{
								componentStr = componentStr +htmlCode.charAt(j);
							}
						}
					}
				}	
			}
			if(countOfQuote==2){
				if(componentStr.contains(placeHolder) && countOfPrarThisis == 0){
					titleAttribute = placeHolder+htmlCode.charAt(i);
					componentStr = componentStr +htmlCode.charAt(i);
					checkPlaceHolder = true;
					countOfPrarThisis++;
					continue;
				}				
				if(checkPlaceHolder){
					if(countOfPrarThisis<=2){
						titleAttribute = titleAttribute+htmlCode.charAt(i);
						if(htmlCode.charAt(i) == '\"'){
							countOfPrarThisis++;
						}
					}
				}				
				if(htmlCode.charAt(i) == '>'){
					componentStr = componentStr +htmlCode.charAt(i);
					countOfQuote++;					
				}else{
					componentStr = componentStr +htmlCode.charAt(i);
				}
			}
		}
		
		if( countOfQuote>2 && countOfPrarThisis >2 ){
			String temp = "";
			temp = componentStr;			
			componentStr = componentStr.replaceAll(titleAttribute, placeHolder+"=\""+pourData+"\"");
			str = str.replace(temp, componentStr);
			return str;
		}else{
			return str;
		}
	}
	
	
	/*public String getHtmlCodeAfterAttributeChange(String htmlCode,String component,String placeHolder,String pourData){
		System.out.println("getHtmlCodeAfterAttributeChange");
		System.out.println("htmlCode "+htmlCode);
		System.out.println("component "+component);
		System.out.println("placeHolder "+placeHolder);
		System.out.println("pourData "+pourData);
		String newHTMLCode = "";
		
		String str = "";
		String newStr = ""; 
		int countOfQuote = 0;
		int count  = 0;
		
		boolean checkComponent = false;	
		boolean checkPlaceHolder = false;
		
		for(int i = 0;i<htmlCode.length(); i++){			
			str = str+htmlCode.charAt(i);
			if(str.contains("component=\""+component+"\"")){
				checkComponent = true;
			}
			if(checkComponent){
				if(countOfQuote == 0){
					if(str.contains(placeHolder+"=")){
						if(count == 0){
							newStr = newStr+placeHolder+"=";
							checkPlaceHolder = true;
							count++;
						}
					}
				}
			}
			if(checkComponent){
				if(checkPlaceHolder){
						if(countOfQuote<3){
							if(htmlCode.charAt(i) == '"'){
								newStr = newStr+htmlCode.charAt(i);
								countOfQuote++;
							}
						}
				}
			}
			if(countOfQuote==2){
				System.out.println("str "+str);
				System.out.println("newStr "+newStr);
				System.out.println("ChangedTo "+placeHolder+"=\""+pourData+"\"");
				str = str.replace(newStr, placeHolder+"=\""+pourData+"\"");
				countOfQuote++;
			}
		}
		newHTMLCode = str;
//		System.out.println("newHTMLCode "+newHTMLCode);
		return newHTMLCode;
	}
	*/
	public String getHtmlCodeAfterPourContent(String htmlCode,String placeHolder,String pourData){
	/*	System.out.println("getHtmlCodeAfterPourContent");
		System.out.println("htmlCode "+htmlCode);
		System.out.println("placeHolder "+placeHolder);
		System.out.println("pourData "+pourData);*/
		String newHTMLCode = "";
		
		String str = "";
		boolean checkPourData = false;
		boolean checkEndTag = false;
		
		int countOfcheck = 0;
		
		for(int i = 0;i<htmlCode.length(); i++){
			if(!checkEndTag){
				str = str+htmlCode.charAt(i);
			}
			if(countOfcheck == 0){
				if(str.contains("component=\""+placeHolder+"\"")){
					checkPourData = true;
					countOfcheck++;
				}
			}
			if(checkPourData){
				if(htmlCode.charAt(i) == '>'){	
					checkEndTag = true;
					str = str+pourData;
					checkPourData = false;
				}
			}
			if(checkEndTag){
				if(htmlCode.charAt(i) == '<'){
					str = str+htmlCode.charAt(i);
					checkEndTag = false;
				}
			}
		}
		newHTMLCode = str;
//		System.out.println("newHTMLCode "+newHTMLCode);
		return newHTMLCode;
	}
	
	public String getHtmlCodeAfterPourContentInPreviousPage(String htmlCode,String placeHolder,String pourData,ArrayList<String> pageURL){
			/*System.out.println("getHtmlCodeAfterPourContentInPreviousPage");
			System.out.println("htmlCode "+htmlCode);
			System.out.println("placeHolder "+placeHolder);
			System.out.println("pourData "+pourData);
			*/
			ArrayList<String> tempPageURL = new ArrayList<String>();
			tempPageURL = pageURL;
			
			String tempPageName = "";
			String url = "";
			
			outer:
			for(int i = 0;i<tempPageURL.size();i++){
				String page = "";
				page = tempPageURL.get(i);
				ArrayList<String> tempList = new ArrayList<String>();
				tempList = getListSplitedByChar(page, '|');
				tempPageName = "";
				url = "";
				for(int tempCount = 0; tempCount<tempList.size(); tempCount++){							
					if(tempCount == 0){
						tempPageName = tempList.get(tempCount);
					}if(tempPageName.equalsIgnoreCase(pourData)){
						if(tempCount == 1){
							url = tempList.get(tempCount);
							break outer;
						}
					}					
				}
			}
			
			/*System.out.println("tempPageName "+tempPageName);
			System.out.println("URL "+url);
			*/
			
			if(url.isEmpty() || url.equalsIgnoreCase("")){
				GUI.logger.info("For Page "+pourData+" URL "+url+" entered in HTML");
			}else{
				GUI.logger.info(pourData+" HTML page not found so URL not entered in HTML code");
			}
			
			String newHTMLCode = "";
			
			String str = "";
			boolean checkEndTag = false;		
			int countOfcheck = 0;
			int countOfLessIcon = 0;		
			String newString = "";		
			String startTag = "";
			String hrefValue = "";
			int countOfReverse = 0;
			
			
			for(int i = 0;i<htmlCode.length(); i++){
				if(!checkEndTag){
					str = str+htmlCode.charAt(i);
				}
				if(countOfcheck == 0){
					if(str.contains("component=\""+placeHolder+"\"")){					
						countOfcheck++;
					}
				}
				
				if(countOfcheck == 1){
					countOfcheck++;
					for(int j = i;j>= 0; j--){
						i--;
						startTag = startTag+htmlCode.charAt(j);
						
						if(htmlCode.charAt(j) == ' '){	
							startTag = "";
						}
						if(htmlCode.charAt(j) == '<'){
							startTag = startTag.replace("<", "");
							startTag= new StringBuilder(startTag).reverse().toString();
							str = "<";
							i++;
							break;
						}
					}
				}
				
				if(countOfcheck == 2){
					newString = newString+htmlCode.charAt(i);
					
					if(countOfLessIcon == 0){
						if(str.contains("href=\"")){
							countOfLessIcon++;
							hrefValue = "href=";						
						}
					}
					if(countOfLessIcon == 1 || countOfLessIcon == 2){
						hrefValue = hrefValue+htmlCode.charAt(i);
						if(htmlCode.charAt(i) == '"'){
							countOfLessIcon++;
						}
					}
					if(countOfLessIcon == 3){
						countOfLessIcon++;
						str = str.replace(hrefValue, "href=\""+url+"\"");
					}
				}			
				if(countOfcheck == 2){
					if(htmlCode.charAt(i) == '>'){
						if(str.contains("</"+startTag+">")){
							String tempString = "";
							
							if(countOfReverse == 0){							
								for(int j = i;j<=i;j--){
									/*System.out.println("j "+j);
									System.out.println("htmlCode.charAt(j) "+htmlCode.charAt(j));
									System.out.println("tempString "+tempString);*/
									tempString = tempString + htmlCode.charAt(j);
									if(htmlCode.charAt(j) == '>'){
										countOfReverse++;
									}
									if(countOfReverse == 2){
										break;
									}								
								}
							}
							tempString = new StringBuilder(tempString).reverse().toString();
//							System.out.println("tempString "+tempString);
//							str = str.replace("</"+startTag+">", pourData+"</"+startTag+">");	
							str = str.replace(tempString, ">"+pourData+"</"+startTag+">");
							checkEndTag = true;
							countOfcheck++;
						}
					}
				}
			}
			newHTMLCode = htmlCode.replace(newString,str);
//			System.out.println(newHTMLCode);
			return newHTMLCode;
		}	
	
	public static String getNewHtmlCode(String htmlCode,String placeHolder,String pourData){
		/*System.out.println("getNewHtmlCode");
		System.out.println("htmlCode "+htmlCode);
		System.out.println("placeHolder "+placeHolder);
		System.out.println("pourData "+pourData);*/
		String newHTMLCode = "";
		
		String str = "";
		boolean checkPourData = false;
		boolean checkEndTag = false;
		
		int countOfcheck = 0;
		
		for(int i = 0;i<htmlCode.length(); i++){
			if(!checkEndTag){
				str = str+htmlCode.charAt(i);
			}
			if(countOfcheck == 0){
				if(str.contains("id=\""+placeHolder+"\"")){
					checkPourData = true;
					countOfcheck++;
				}
			}
			if(checkPourData){
				if(htmlCode.charAt(i) == '>'){	
					checkEndTag = true;
					str = str+pourData;
					checkPourData = false;
				}
			}
			if(checkEndTag){
				if(htmlCode.charAt(i) == '<'){
					str = str+htmlCode.charAt(i);
					checkEndTag = false;
				}
			}
		}
		newHTMLCode = str;
//		System.out.println("newHTMLCode "+newHTMLCode);
		return newHTMLCode;
	}
	
	public static String changeAttributeValue(String htmlCode,String placeHolder,String htmlvalue){
		System.out.println("changeAttributeValue");
		String attribute  = "";
		String tempAttribute = "";
		attribute = getAttributeData(htmlCode,placeHolder);
		tempAttribute = attribute;
//		System.out.println("attribute "+attribute);
		
		if((!attribute.equalsIgnoreCase("")) || (!attribute.isEmpty())){
			int countOfQuote = 0;
			String str = "";
			
			for(int i = tempAttribute.length()-1;i>0;i--){
				str = str+tempAttribute.charAt(i);
				if(tempAttribute.charAt(i) == '"'){
					countOfQuote++;
				}
				if(countOfQuote == 2){
					break;
				}
			}
			tempAttribute = tempAttribute.replaceFirst(str, "");
//			System.out.println("Changed Attribute "+tempAttribute);
			
			htmlCode = htmlCode.replaceFirst(attribute, tempAttribute+"\""+htmlvalue+"\"");
//			System.out.println("changeAttributeValuehtmlCode "+htmlCode);
		}
		return htmlCode;
	}
	
	public static String getAttributeData(String htmlCode,String placeHolder){
	/*	System.out.println("getAttributeData");
		System.out.println("htmlCode "+htmlCode);
		System.out.println("placeHolder "+placeHolder);*/
		String str = "";
		
		boolean checkAttribute = false;
		
		for(int i = 0;i<htmlCode.length(); i++){
			if(!checkAttribute){
				str = str+htmlCode.charAt(i);
				if(str.contains(placeHolder+"=\"")){
					str = placeHolder+"=\"";
					checkAttribute = true;
					continue;
				}
			}			
			if(checkAttribute){
				str = str+htmlCode.charAt(i);
				if(htmlCode.charAt(i) == '"'){
					break;
				}
			}
		}	
		System.out.println("str "+str);		
		return str;
	}
	
	public String getHTMLCodeAfterImageAltAndURL(String htmlCode,String placeHolder,String htmlValue){
//		System.out.println("getHTMLCodeAfterImageAltAndURL");
		
		String str = "";
		boolean checkPourData = false;
		boolean checkEndTag = false;
		
		int countOfcheck = 0;
		
		String tempHtmlValue = "";
		tempHtmlValue = htmlCode;
		
		for(int i = 0;i<htmlCode.length(); i++){
			if(!checkEndTag){
				str = str+htmlCode.charAt(i);
			}
			if(countOfcheck == 0){
				if(str.contains("component=\""+placeHolder+"\"")){
					checkPourData = true;
					countOfcheck++;
				}
			}
			if(checkPourData){
				if(htmlCode.charAt(i) == '>'){	
					checkEndTag = true;
					checkPourData = false;
				}
			}			
		}
		
		ArrayList<String> imageList = new ArrayList<String>();
		imageList = imageSrc(str);
		for(int imageListCount = 0; imageListCount<imageList.size(); imageListCount++){
			if(imageList.get(imageListCount).contains("alt")){
				htmlCode =htmlCode.replace(imageList.get(imageListCount), "alt=\""+htmlValue+"\"");
			}else if(imageList.get(imageListCount).contains("src")){
				String imageU  = "";
				try{
					imageU = getImageURL(htmlValue);
					if(imageU.equalsIgnoreCase("") || imageU.isEmpty()){
						GUI.consoleText.append("Image "+htmlValue+ "not found in Image List\n");
						GUI.logger.info("Image "+htmlValue+ "not found in Image List");
					}else{
						GUI.logger.info("URL of Image  "+htmlValue+" is "+imageU);
						htmlCode =htmlCode.replace(imageList.get(imageListCount), "src=\""+imageU+"\"");
					}
				}catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();					
					GUI.consoleText.append("Image "+htmlValue+ "not found in Image List\n");
					GUI.logger.info("Image "+htmlValue+ "not found in Image List");
					GUI.logger.info(e.getMessage());
				}
			}
		}
		htmlCode = htmlCode.replace(str, htmlCode);
		return htmlCode;
	}
	
	
	public String getAfterImageAltAndURL(String htmlCode,String htmlValue){		
		
		ArrayList<String> imageList = new ArrayList<String>();
		imageList = imageSrc(htmlCode);
		for(int imageListCount = 0; imageListCount<imageList.size(); imageListCount++){
			if(imageList.get(imageListCount).contains("alt")){
				htmlCode =htmlCode.replaceAll(imageList.get(imageListCount), "alt=\""+htmlValue+"\"");
			}else if(imageList.get(imageListCount).contains("src")){
				htmlCode =htmlCode.replaceFirst(imageList.get(imageListCount), "src=\""+getImageURL(htmlValue)+"\"");
			}
		}
		return htmlCode;
	}
	
	public String writeImageAltAndURL(String htmlCode,String htmlValue){
		
		ArrayList<String> imageList = new ArrayList<String>();
		imageList = imageSrc(htmlCode);
		for(int imageListCount = 0; imageListCount<imageList.size(); imageListCount++){
			if(imageList.get(imageListCount).contains("alt")){
				htmlCode =htmlCode.replaceAll(imageList.get(imageListCount), "alt=\""+htmlValue+"\"");
			}else if(imageList.get(imageListCount).contains("src")){
				htmlCode =htmlCode.replaceFirst(imageList.get(imageListCount), "src=\""+getImageURL(htmlValue)+"\"");
			}
		}		
		return htmlCode;
	}
	
	
	public static ArrayList<String> imageSrc(String src){
		GUI.logger.info("imageSrc");
		/*System.out.println("imageSrc");
		System.out.println("src "+src);*/
		
		
		ArrayList<String> arrayList = new ArrayList<String>();
		
		boolean imageSource = false;
		boolean source  = false;
		boolean alt = false;
		int countOfQuote = 0;
		int countOfAlt = 0;
		
		String str = "";
		for(int i = 0;i<src.length();i++){
			str = str+src.charAt(i);
			if(!imageSource){
				if(str.contains("<img")){
					str = "<img";
					imageSource = true;
				}
			}
			if(imageSource){
				if(src.charAt(i) == '>'){
					imageSource = false;
				}
			}
			if(imageSource && !source){
				if(str.contains("src")){
					str = "src";
					source = true;
				}				
			}
			if(imageSource && !alt){
				if(str.contains("alt")){
					str = "alt";
					alt = true;
				}				
			}
			if(imageSource && source){
				if(src.charAt(i) == '"'){
					countOfQuote++; 
				}
			}
			if(imageSource && alt){
				if(src.charAt(i) == '"'){
					countOfAlt++; 
				}
			}
			if(countOfQuote == 2){
				arrayList.add(str);
				str = "";
				if(source){
					source  = false;
				}
				countOfQuote = 0;				
			}if(countOfAlt == 2){
				arrayList.add(str);
				str = "";
				if(alt){
					alt  = false;
				}
				countOfAlt = 0;				
			}
		}
//		System.out.println("arrayList "+arrayList);
		GUI.logger.info("arrayList "+arrayList);
		return arrayList;
	}
	
	static String getImageURL(String contentToPour){
		String url = null;
		
		try{
			url = MainSussexPageCreation.imageLinkedHashMap.get(contentToPour);
		}catch (NullPointerException e) {
			// TODO: handle exception
			e.printStackTrace();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return url;
	}
	
	static String getPageURL(String contentToPour){
		/*System.out.println("getPageURL");
		System.out.println("pageURLList "+pageURLList);
		System.out.println("contentToPour "+contentToPour);*/
		
		String url = "";
		outer:
		for(int i = 0;i<pageURLList.size();i++){
			
			String val = "";
			val = pageURLList.get(i);
			ArrayList<String> tempList = new ArrayList<String>();
			tempList = getListSplitedByChar(val, '|');
			
			String tempPageName = "";			
			
			for(int tempCount = 0; tempCount<tempList.size(); tempCount++){							
				if(tempCount == 0){tempPageName = tempList.get(tempCount).trim();}
				if(tempCount == 1){
					if(tempPageName.equalsIgnoreCase(contentToPour)){
						url = tempList.get(tempCount);
						break outer ;						
					}
				}
			}
		}
//		System.out.println("URL  "+url);
		return url;
	}
	
	public static String getPlaceHolederChangedHTMLCode(ArrayList<String> tempList,String htmlCode){
		
		String changedHTMLCode = "";
		
		for(int i = 0;	i < tempList.size();	i++){
			String templateCode = "";
			templateCode = tempList.get(i).toString().trim();
			ArrayList subStringList = new ArrayList();
			subStringList = getListSplitedByChar(templateCode,'|');
			
			String placeHolder = "";
			for(int spanCount = 0; spanCount < subStringList.size(); spanCount++){
				if(spanCount == 0){
					placeHolder = subStringList.get(spanCount).toString().trim();
				}else{
					changedHTMLCode = changedHTMLCode+htmlCode.replaceAll("##"+placeHolder+"##", subStringList.get(spanCount).toString().trim());
				}
			}
		}
		return changedHTMLCode;
	}
	
	
	public static ArrayList getListSplitedByChar(String inputText,char splitChar){
		
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
	
	public static void deleteFile(String file){	
		
		String fileName = "";
		fileName = file;
		try{
            Files.deleteIfExists(Paths.get(fileName));
        }
        catch(NoSuchFileException e){
            GUI.logger.info("No such file/directory exists");
        }
        catch(DirectoryNotEmptyException e){
            GUI.logger.info("Directory is not empty");
        }
        catch(IOException e){
            GUI.logger.info("Invalid permissions");
        }         

    }
       
   public void verifyImageDetailsAvailable(String dummyImageDir,String actualImageFolder) throws IOException{
	   
	   ArrayList<String> listOfNotFoundImages = new ArrayList<String>();
	   
//	   if(pageURLList.size()>0){
			
			GUI.logger.info("Verifying required images available in application.");
			GUI.consoleText.append("Verifying required images available in application."+"\n");
			
			for(Entry<String,LinkedHashMap<String,ArrayList<String>>> tableHtmlEntrySet:MainSussexPageCreation.htmllinkedMap.entrySet()){
			
				String tableHtmlKey  = tableHtmlEntrySet.getKey();
			
				if(MainSussexPageCreation.newPageAlreadyFoundList.contains(tableHtmlKey)){continue;}
				if(MainSussexPageCreation.updatePageNoFoundList.contains(tableHtmlKey)){continue;}			
				if(MainSussexPageCreation.noChangeList.contains(tableHtmlKey)){continue;}
				if(MainSussexPageCreation.issueInNewPageCreation.contains(tableHtmlKey)){continue;}
			
				GUI.logger.info("Table "+tableHtmlKey);
//				GUI.consoleText.append("Table "+tableHtmlKey+"\n");
			
				LinkedHashMap<String,ArrayList<String>> htmlLinkedHashMap = tableHtmlEntrySet.getValue();		
			
				for(Entry<String,ArrayList<String>> htmlEntrySet:htmlLinkedHashMap.entrySet()){				
					String htmlKey  = htmlEntrySet.getKey();				
					if(htmlKey.equalsIgnoreCase("PageTitle")){continue;}
					if(htmlKey.equalsIgnoreCase("TemplateName")){continue;}					
				
					ArrayList<String> inputDataComponentList = new ArrayList<String>();
					inputDataComponentList = htmlEntrySet.getValue();
				
					for(int inputDataComponentCount = 0; inputDataComponentCount<inputDataComponentList.size();inputDataComponentCount++){
						String inputDataComponent = "";
						inputDataComponent = inputDataComponentList.get(inputDataComponentCount).trim();
						ArrayList<String> spittedInputList = new ArrayList<String>();
						spittedInputList = getListSplitedByChar(inputDataComponent, '|');
						boolean imageCheck = false;
						
						for(int i=0;i<spittedInputList.size();i++){
							String fileName = "";
							fileName  = spittedInputList.get(i).trim();
							if(i == 0){
								if(fileName.toLowerCase().contains("alt")){
									imageCheck = true;									
								}
							}
							if(i == 1){
								if(imageCheck){
									if(fileName.contains(".jpg") || fileName.contains(".png") || fileName.contains(".jpeg")){
										listOfNotFoundImages.add(fileName);
									}
								}
							}							
						}
					}
			}	
		}	
			
		if(listOfNotFoundImages.size()>0){
			createDummyImageName(listOfNotFoundImages,dummyImageDir,actualImageFolder);
		}	
	/*}else{
		GUI.logger.info("No new or updation in existing page found");
		GUI.consoleText.append("No new or updation in existing page found"+"\n");
	}*/
   }
		
   public static void createDummyImageName( ArrayList<String> imageNameList,String dummyImageDir,String actualImageFolder) throws IOException{
	   ArrayList<String> imageList = new ArrayList<String>();
	   imageList = imageNameList;
	   
	   for(int i = 0;i<imageList.size();i++){
		   String imageName = "";
		   imageName = imageList.get(i).trim();
		   
		   boolean checkImageAlreadyAvailable = false;
		   
		   for(Entry<String, String> imageMap:MainSussexPageCreation.imageLinkedHashMap.entrySet()){
			   String mapImage = "";
			   mapImage = imageMap.getKey().trim();			   
			   if(imageName.equalsIgnoreCase(mapImage)){
				   checkImageAlreadyAvailable = true;
				   break;
			   }
		   }
		   if(!checkImageAlreadyAvailable){
			   
			   File f1 = new File(dummyImageDir);
			   File f2 = new File(actualImageFolder+"\\"+imageName);
			   
			   try{
				   copyFileUsingApacheCommonsIO(f1,f2);
				   MainSussexPageCreation.listOfImageNotFoundInApplication.add(imageName);
				   MainSussexPageCreation.imageLinkedHashMap.put(imageName,actualImageFolder+"\\"+imageName);
			   }catch (Exception e) {
				// TODO: handle exception
				   GUI.logger.info("Issue in creating "+imageName+" dummy image");
				   GUI.logger.info(e.getMessage());
			   }
		   }
	   }
	} 	
	
   private static void copyFileUsingApacheCommonsIO(File source, File dest) throws IOException {
	    FileUtils.copyFile(source, dest);
	}
	
}
