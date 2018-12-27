package com.magic.sussex.canvasDataPour;

import com.magic.util.WebDriverFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.magic.sussex.gui.GUI;
import com.magic.sussex.page.LoginPage;
import com.magic.sussex.page.ModulePage;
import com.magic.util.EnvironmentPropertiesReader;
import com.magic.util.ExcelUtils;
import com.magic.util.SeleniumUtil;

public class FileUploadAndInfo extends WebDriverFactory{
	
	private static EnvironmentPropertiesReader configProperty = EnvironmentPropertiesReader.getInstance();
	
	private By UPLOADICON = By.xpath("//header[@class='ef-header']//input[@type='file']");
	private By REPLACEIMAGE = By.xpath("//div[@class='ReactModal__Layout']//*[contains(text(),'Replace')]");
	
	static int waitTimeOut = 180;
	static String resourcesFolderName = "Resources";
	static String imageFileName = "AssetInfo";
	static String sheetName = "Sheet1";
	static int rowCount = 1;
	
	public boolean getAssetInfo(String assetsPath) throws InterruptedException, IOException {
		 GUI.logger.info("Fetching asset details");	
		 GUI.consoleText.append("Fetching asset details\n");
 	   try{
			  System.out.println("assetsPath "+assetsPath);
 		   	 File folder = new File(assetsPath);
	         File[] listOfFiles = folder.listFiles();
	         System.out.println("listOfFiles "+listOfFiles.length);
	         if(listOfFiles.length>0){
	        	 for (int i = 0; i < listOfFiles.length; i++) {
		             if (listOfFiles[i].isDirectory()) { 
		            	 String dirName = "";
		            	 dirName = listOfFiles[i].getName();
		            	 System.out.println("dirName "+dirName);
		            	 File subfolder = new File(assetsPath+"\\"+dirName+"\\");
		    	         File[] listOfSubFolders = subfolder.listFiles();
		    	         System.out.println("listOfSubFolders "+listOfSubFolders.length);
		    	         if(listOfSubFolders.length>0){
		    	        	 for (int j = 0; j < listOfSubFolders.length; j++) {
		    	        		 if(listOfSubFolders[j].isFile()){
		    	        			 String fileName = "";
			    	        		 fileName = listOfSubFolders[j].getName();
			    	        		 System.out.println("fileName "+fileName);
			    	        		 GUI.logger.info("Asset Name "+fileName+" and URL "+assetsPath+"\\"+dirName+"\\"+fileName);	
			    	        		 MainSussexPageCreation.imageLinkedHashMap.put(fileName, assetsPath+"\\"+dirName+"\\"+fileName);
		    	        		 }
		    	        	 }
		    	         }
		             }
		             GUI.logger.info("Fetching asset information completed successfull.");	
			    	 GUI.consoleText.append("Fetching asset information completed successfull.\n");
			    	 return true;
	        	 }
	         }
		   }catch (Exception e) {
			// TODO: handle exception
//			   e.printStackTrace();
			   GUI.logger.info("Files link not found");
			   GUI.consoleText.append("Files link not found\n");
			   return false;
		}
//    System.out.println("MainSussexPageCreation.imageLinkedHashMap "+MainSussexPageCreation.imageLinkedHashMap);
    return true;
	}
	
	public static String getExactURL(String url){		
		
		String newURL = "";
		
		int indexCount = url.length();
		
		for(int i = url.length()-1;i>0;i--){
			if(url.charAt(i) == '?'){
				indexCount = i;
				break;
			}
		}
		newURL = url.substring(0, indexCount);
		return newURL;
	}
	
	
	public void createImageAssetInfoInExcel1(String folderName) throws InterruptedException, IOException {

	    	   try{
    			   SeleniumUtil.waitForElementVisibility(driver, By.xpath("//a[@title='Files']") ,waitTimeOut).click();
    		       SeleniumUtil.waitForElementVisibility(driver, By.xpath("//a[@role='presentation']//*[contains(text(),'"+folderName+"')]") ,waitTimeOut).click();
    		       Thread.sleep(3000);
    		       SeleniumUtil.waitForElementVisibility(driver, By.xpath("(//div[@aria-label='File List']//div[@role='row'])") ,waitTimeOut);
    		       List<WebElement> count=driver.findElements(By.xpath("(//div[@aria-label='File List']//div[@role='row'])"));
    		       System.out.println(count.size());
    		       ExcelUtils.createExcelFile(resourcesFolderName, imageFileName, sheetName);
    		       ExcelUtils.getExcelFile(resourcesFolderName, imageFileName);
    		       ExcelUtils.getSheet(sheetName);
    		       
    		       ExcelUtils.writeExcel(0, 0, "Image Name", "yes", "");
    			   ExcelUtils.writeExcel(0, 1, "Image URL", "" , "");
    		       
    		       for(int i=0;i<count.size();i++){
    		    	   Thread.sleep(3000);
    		    	 SeleniumUtil.waitForElementVisibility(driver, By.xpath("(//div[@aria-label='File List']//div[@role='row'])["+(i+1)+"]//div[@role='rowheader']/a") ,waitTimeOut).click();
    		    	 WebElement ele = SeleniumUtil.waitForElementVisibility(driver,By.xpath("//h1[@class='ef-file-preview-header-filename']"),waitTimeOut);
    		    	 String title = ele.getText();
    		    	 System.out.println(title);
    		    	 String url1 = driver.getCurrentUrl();
    		    	 System.out.println(url1);
    		    	 url1 = getCorrectURL(url1);
    			    ExcelUtils.writeExcel(rowCount, 0, title, "yes", "");
    			    ExcelUtils.writeExcel(rowCount, 1, url1, "" , "");
    				Thread.sleep(1000);
    		        SeleniumUtil.waitForElementVisibility(driver,By.xpath("//span[contains(text(),' Close')]"),waitTimeOut).click(); 
    		        rowCount++;
    		       }
    		       
    		       ExcelUtils.closeExcelFile(resourcesFolderName,imageFileName );
    		       
    		   }catch (Exception e) {
				// TODO: handle exception
    			   e.printStackTrace();
			}
	       
		}


	public static String getReverseString(String text){
	
	StringBuilder input1 = new StringBuilder();
    input1.append(text);
    input1 = input1.reverse();
    
    return input1.toString();
	
}

	public static String getCorrectURL(String oldURL){
	
	String url = "";
	
	String str1 = "";
	String str2 = "";
	for(int i = 0;i<oldURL.length();i++){
		str1 = str1+oldURL.charAt(i);
		if(str1.toLowerCase().contains("files")){	
			str1 = str1+"/";
			break;
		}
	}
	for(int i = oldURL.length()-1;i>0;i--){			
		if(oldURL.charAt(i) == '?'){
			break;
		}
		str2 = str2+oldURL.charAt(i);
	}
	str2 = getReverseString(str2);
	if(str2.contains("=")){
		ArrayList al = new ArrayList();
		al = ReadTemplate.getListSplitedByChar(str2,'=');
		for(int i = al.size()-1; i>=0; i--){
			if(i == 0){
				str1 = str1+al.get(i).toString().trim();
			}else{
				str1 = str1+al.get(i).toString().trim()+"/";
				}
		}
	}
	url = str1;
	System.out.println("url "+url);
	return url;
}
	

	public static void getAssetInformation() throws IOException, InterruptedException{
		System.out.println("getAssetInformation");
		ExcelUtils.getExcelFile(resourcesFolderName, imageFileName);
		ExcelUtils.getSheet(sheetName);
		
		int countOfRecord = ExcelUtils.getRowCount(resourcesFolderName, imageFileName, sheetName);
		
		for(int row = 1; row<=countOfRecord; row++){
			String imageName = "";
			String imageURL = "";
			imageName = (String) ExcelUtils.readExcelUserInput(resourcesFolderName, imageFileName, sheetName, row, 0);			
			imageURL = (String) ExcelUtils.readExcelUserInput(resourcesFolderName, imageFileName, sheetName, row, 1);	
			System.out.println("imageName "+imageName);
			System.out.println("imageURL "+imageURL);
		}
	
	}
	
	public void uploadImage(String programeName,String folderName,String filePath){
		
		GUI.logger.info("Image Upload is in Progress....");
		GUI.consoleText.append("Image Upload is in Progress....\n");
		ModulePage modulePage = new ModulePage(driver);
		try{
				modulePage.clickFiles();
		      	if(getClickOnFolderName(programeName,folderName)){
		      		
		      		Thread.sleep(15000);
			      	String fullFilePath = "";
				
			      	fullFilePath = filePath;
			      	 File folder = new File(fullFilePath);
			         File[] listOfFiles = folder.listFiles();
			         if(listOfFiles.length>0){
			        	 GUI.logger.info("Total Count of files "+listOfFiles.length);
			        	 GUI.consoleText.append("Total Count of files "+listOfFiles.length+"\n");
				         for (int i = 0; i < listOfFiles.length; i++) {
				             if (listOfFiles[i].isFile()) {
				                 String filename = listOfFiles[i].getName();
				                 try{
				                	 SeleniumUtil.waitForElementPresence(driver, UPLOADICON, waitTimeOut).sendKeys(fullFilePath+"\\"+filename);				                	
				                	 SeleniumUtil.waitForElementPresence(driver, REPLACEIMAGE, 5).click();				                	
				                	 GUI.logger.info(filename+" replaced");
				                 }catch(Exception e){
				                	 }
				            }
				         }
				         driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
				         {
				        	 GUI.consoleText.append("Uploading images...."+"\n"); 
		            	  }while(driver.findElements(By.xpath("//div[@class='current_uploads__uploaders']")).size() >0);
				         
		            	  GUI.logger.info("Image upload successfull, press reset button for next execution");
	            		  GUI.consoleText.append("Image upload successfull, press reset button for next execution"+"\n"); 
			         }else{
			        	 GUI.logger.info(filePath+" does not contains any asset so can't start Asset upload process, Please try again."); 
			        	 GUI.consoleText.append(filePath+" does not contains any asset so can't start Asset upload process, Please try again."+"\n");
			         }
		      	}else{
		      		 GUI.logger.info("Folder name "+ folderName+ " not found in "+programeName+" so can't start Asset upload process, Please try again.");
		      		GUI.consoleText.append("Folder name "+ folderName+ " not found in "+programeName+" so can't start Asset upload process, Please try again."+"\n");
		      	} 
		   }catch (Exception e) {
			// TODO: handle exception
			   e.printStackTrace();
			   GUI.logger.info("Files link not found so can't start Asset upload process, Please try again.");
			   GUI.consoleText.append("Files file not found so can't start Asset upload process, Please try again."+"\n");
			   
		}
	}
	
	
	public boolean getClickOnFolderName(String courseName,String folderName) throws InterruptedException{
		
		try{
			Thread.sleep(5000);
	
	/*	SeleniumUtil.waitForElementVisibility(driver, By.xpath("//div[@class='ef-folder-list']//*[@role='tree']/*[contains(@role,'treeitem')]/*[contains(@class,'treeLabel')]") ,waitTimeOut);
		List<WebElement> courseList = driver.findElements(By.xpath("//div[@class='ef-folder-list']//*[@role='tree']/*[contains(@role,'treeitem')]/*[contains(@class,'treeLabel')]"));
		for(int courseCount = 0; courseCount<courseList.size();courseCount++){
			String tempCourseName = "";
			tempCourseName = courseList.get(courseCount).getText().trim();
			if(tempCourseName.equalsIgnoreCase(courseName.trim())){
				try{
					SeleniumUtil.waitForElementVisibility(driver, By.xpath("(//div[@class='ef-folder-list']//*[@role='tree']/*[contains(@role,'treeitem')])["+(courseCount+1)+"]//*[@class='treeLabel expanded']") ,5);
					
				}catch (Exception e) {
					// TODO: handle exception
					courseList.get(courseCount).click();
					GUI.logger.info("Course Click");
					Thread.sleep(10000);
				}				
				SeleniumUtil.waitForElementVisibility(driver, By.xpath("(//div[@class='ef-folder-list']//*[@role='tree']/*[contains(@role,'treeitem')])["+(courseCount+1)+"]//*[@class='collectionViewItems']//li//*[contains(@class,'treeLabel')]") ,waitTimeOut);
				List<WebElement> folderList = driver.findElements(By.xpath("(//div[@class='ef-folder-list']//*[@role='tree']/*[contains(@role,'treeitem')])["+(courseCount+1)+"]//*[@class='collectionViewItems']//li//*[contains(@class,'treeLabel')]"));
				for(int folderCount = 0; folderCount<folderList.size();folderCount++){
					String tempFolderName = "";
					tempFolderName = folderList.get(folderCount).getText().trim();
					if(tempFolderName.equalsIgnoreCase(folderName.trim())){						
						folderList.get(folderCount).click();
						GUI.logger.info("Folder "+folderName+" get clicked");
						return true;
					}
				}
			}	
		}
		*/
			try{
				SeleniumUtil.waitForElementVisibility(driver, By.xpath("(//div[@class='ef-folder-list']//*[@role='tree']/*[contains(@role,'treeitem')])/a[@class='treeLabel']") ,10).click();
				
			}catch (Exception e) {
				// TODO: handle exception
			}
			
			
			SeleniumUtil.waitForElementVisibility(driver, By.xpath("//div[@class='ef-folder-list']//*[@role='tree']//*[@class='collectionViewItems']//li//*[contains(@class,'treeLabel')]") ,waitTimeOut);
			List<WebElement> folderList = driver.findElements(By.xpath("//div[@class='ef-folder-list']//*[@role='tree']//*[@class='collectionViewItems']//li//*[contains(@class,'treeLabel')]"));
//			System.out.println("folderList "+folderList.size());
			for(int folderCount = 0; folderCount<folderList.size();folderCount++){
				String tempFolderName = "";
				tempFolderName = folderList.get(folderCount).getText().trim();
				if(tempFolderName.equalsIgnoreCase(folderName.trim())){						
					folderList.get(folderCount).click();
					GUI.logger.info("Folder "+folderName+" get clicked");
					return true;
				}
			}	
			
			
			
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			GUI.logger.info("Upload folder "+folderName +" not found in "+courseName);
		}
		return false;
	}
	
	
	public ArrayList getFileNames(String type) throws FileNotFoundException, IOException {

        ArrayList filenames = new ArrayList();
     
        File folder = new File("./image");
        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {

                String filename = listOfFiles[i].getName();

                if (filename.contains(type)) {
                    filenames.add(filename);

                }

            }
        }

        return filenames;

    }
	
	public LinkedHashMap<String,String> imageInfo() throws IOException, InterruptedException{
		LinkedHashMap<String,String> imageDetailsLinkedHashMap = new LinkedHashMap<String,String>();
		
		System.out.println("getAssetInformation");
		ExcelUtils.getExcelFile(resourcesFolderName, FileUploadAndInfo.imageFileName);
		ExcelUtils.getSheet(FileUploadAndInfo.sheetName);
		
		int countOfRecord = ExcelUtils.getRowCount(resourcesFolderName, FileUploadAndInfo.imageFileName, FileUploadAndInfo.sheetName);
		
		for(int row = 1; row<=countOfRecord; row++){
			String imageName = "";
			String imageURL = "";
			imageName = (String) ExcelUtils.readExcelUserInput(resourcesFolderName, FileUploadAndInfo.imageFileName, FileUploadAndInfo.sheetName, row, 0);			
			imageURL = (String) ExcelUtils.readExcelUserInput(resourcesFolderName, FileUploadAndInfo.imageFileName, FileUploadAndInfo.sheetName, row, 1);	
			System.out.println("imageName "+imageName);
			System.out.println("imageURL "+imageURL);
			imageDetailsLinkedHashMap.put(imageName, imageURL);
		}
		return imageDetailsLinkedHashMap;		
	}
	
	
	public void createNewDummyPagesAndUpload(String programeName,String folderName,ArrayList<String> imageNameList,String filePath) throws InterruptedException{
			     
		GUI.consoleText.append("Going to upload dummy images"+"\n"); 
		GUI.logger.info("Going to upload dummy images");
		
		for(int fileCount = 0; fileCount<imageNameList.size();fileCount++){
				String imageFileName = "";
				imageFileName = imageNameList.get(fileCount);
			
				String fullPath = "";
				fullPath = filePath+imageFileName;
			
				try{
           	 		SeleniumUtil.waitForElementPresence(driver, UPLOADICON, waitTimeOut).sendKeys(fullPath);				                	
           	 		SeleniumUtil.waitForElementPresence(driver, REPLACEIMAGE, 5).click();				                	
				}catch(Exception e){
           	 	}	
		}
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		{
			GUI.consoleText.append("Uploading dummy images...."+"\n"); 
			GUI.logger.info("Uploading dummy images....");
		}while(driver.findElements(By.xpath("//div[@class='current_uploads__uploaders']")).size() >0);
     
		GUI.logger.info("Dummy image upload successfull");
		GUI.consoleText.append("Dummy image upload successfull"+"\n");
		
			List<WebElement> count=driver.findElements(By.xpath("(//div[@aria-label='File List']//div[@role='row'])"));
		       GUI.logger.info("Total file count "+count.size());		 
		       GUI.consoleText.append("Total file count "+count.size()+"\n");
		       
		       if(count.size()>0){
		    	   
		    	   for(int i=0;i<count.size();i++){		    	
				    	 String title = "";
				    	 String url = "";
				    	 
				    	 try{
				    		 title = SeleniumUtil.waitForElementVisibility(driver, By.xpath("(//div[@aria-label='File List']//div[@role='row'])["+(i+1)+"]//div[@role='rowheader']/a") ,waitTimeOut).getText();
					    	  
				    		 for(int fileCount = 0; fileCount<imageNameList.size();fileCount++){
				    				String imageFileName = "";
				    				imageFileName = imageNameList.get(fileCount);
				    		 
				    		 
				    		 if(title.equalsIgnoreCase(imageFileName)){
					    		  url = SeleniumUtil.waitForElementVisibility(driver, By.xpath("(//div[@aria-label='File List']//div[@role='row'])["+(i+1)+"]//div[@role='rowheader']/a") ,waitTimeOut).getAttribute("href");
							      url = getExactURL(url);
					    		 MainSussexPageCreation.imageLinkedHashMap.put(title, url);
					    		 GUI.logger.info("Image Name "+title+" and URL "+url);	
//					    		 GUI.consoleText.append("Image Name "+title+" and URL "+url+"\n");
					    	 }
				    		} 
				    	 }catch (Exception e) {
							// TODO: handle exception
				    		 GUI.logger.info("Issue in extracting dummy assets details of "+title);	
				    		 GUI.consoleText.append("Issue in extracting dummy assets details of "+title+"\n");
						}
				       }
				       GUI.logger.info("Fetching dummy asset information completed successfull");	
			    	   GUI.consoleText.append("Fetching dummy asset information completed successfull\n");
		       }
		}
		
	public void startImageUpload(String url,String userName,String password,String programeName,String folderName,String assetsPathAtLoacalMachine){
		WebDriverFactory.getWebDriver();
		try{
			 GUI.logger.info("Accessing web application for Upload Asset.");
			 GUI.consoleText.append("Accessing web application for Upload Asset.\n");
			 driver.get(url);
			
			LoginPage loginPage = new LoginPage(driver);
			loginPage.getDashboard(userName, password);
			
			try{				
			     Thread.sleep(5000);
			    
			     ModulePage modulePage = new ModulePage(driver);
			     if(modulePage.clickModule(programeName)){
			    	 GUI.logger.info("Login is successfull");
					 GUI.consoleText.append("Login is successfull\n");
					 GUI.logger.info("Course "+programeName+" selected.");
		    		 GUI.consoleText.append("Course "+programeName+" selected."+"\n");		
		    		 uploadImage(programeName,folderName,assetsPathAtLoacalMachine);
		    		  
			     }else{
		    		  GUI.logger.info("Course "+programeName+" not found");
		    		  GUI.consoleText.append("Course "+programeName+" not found"+"\n");
		    		  GUI.logger.info("Press reset button and try again.");
					 GUI.consoleText.append("Press reset button and try again.\n");
		    	  }
			}catch (Exception e) {
				// TODO: handle exception
				GUI.logger.info(e.getMessage());
			}
			
			
		}catch (Exception e) {
			// TODO: handle exception
			GUI.logger.info("Press reset button and try again.");
			GUI.consoleText.append("Press reset button and try again.\n");
		}finally {	
			GUI.imageUploadResetButton.setEnabled(true);
			driver.quit();
			driver = null;
		}
		
	}	
	
	
}
