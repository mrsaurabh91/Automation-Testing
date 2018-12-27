package com.magic.sussex.canvasDataPour;

import java.util.ArrayList;
import java.util.LinkedHashMap;


import com.magic.sussex.gui.GUI;
import com.magic.sussex.page.LoginPage;
import com.magic.sussex.page.ModulePage;
import com.magic.util.EnvironmentPropertiesReader;
import com.magic.util.WebDriverFactory;

public class MainSussexPageCreation extends WebDriverFactory{

	private static EnvironmentPropertiesReader configProperty = EnvironmentPropertiesReader.getInstance();
	
	
	public static LinkedHashMap<String,LinkedHashMap<String,ArrayList<String>>> htmllinkedMap = null;	
	public static ArrayList<String> listOfPageCreationType = null;
	
	public static LinkedHashMap<String,String> template ;
	public static ArrayList<String> listOfImageNotFoundInApplication;
	static LinkedHashMap<String,String> imageLinkedHashMap ;

	final static String dummyImageName = "dummy.jpg";	
	
	public static ArrayList<String> noChangeList ;
	public static ArrayList<String> newPageAlreadyFoundList ;
	public static ArrayList<String> updatePageNoFoundList;
	public static ArrayList<String> issueInNewPageCreation;
	
	
	public MainSussexPageCreation() {
		// TODO Auto-generated constructor stub		
		noChangeList = new ArrayList<String>();
		newPageAlreadyFoundList = new ArrayList<String>();
		updatePageNoFoundList = new ArrayList<String>();
		issueInNewPageCreation = new ArrayList<String>();
		
		template = new LinkedHashMap<String,String>();
		listOfImageNotFoundInApplication = new ArrayList<String>();
		imageLinkedHashMap = new LinkedHashMap<String,String>();
	}
	
	/**
	 * @param args
	 * @throws Exception 
	 */
	public void perform(String url,String userName,String password,String programeName,String folderName,String docFilePath,String templateFolderPath) throws Exception {
		
		try{
			
			template = ReadTemplate.getTemplate(templateFolderPath);
			 
			  if(template.size() >0){
				  
				  try{
						 GUI.logger.info("Accessing web application for ingestion of content.");
						 GUI.consoleText.append("Accessing web application for ingestion of content.\n");
						 driver.get(url);
						
						LoginPage loginPage = new LoginPage(driver);
						loginPage.getDashboard(userName, password);
						
						try{	
							
						     Thread.sleep(5000);
						     ModulePage modulePage = new ModulePage(driver);
						     if(modulePage.clickModule(programeName)){
						    	 GUI.logger.info("Login is successfull.");
								 GUI.consoleText.append("Login is successfull.\n");
						    	 GUI.logger.info("Course "+programeName+" selected.");
					    		 GUI.consoleText.append("Course "+programeName+" selected."+"\n");						    		
							    			  FileUploadAndInfo fileUploadAndInfo = new FileUploadAndInfo();
								    		  if(fileUploadAndInfo.getAssetInfo(programeName,folderName)){
								    			  String currentDir = System.getProperty("user.dir");
								    			  String imageDir = currentDir+"\\"+"image\\";	
								    			  
								    			  ReadTemplate readTemplate = new ReadTemplate();
								    			  readTemplate.verifyImageDetailsAvailable(imageDir,dummyImageName);
								    			  
								    			  System.out.println("listOfImageNotFoundInApplication "+listOfImageNotFoundInApplication);
								    			  if(listOfImageNotFoundInApplication.size()>0){
								    				  GUI.logger.info(listOfImageNotFoundInApplication.size()+" asset not available in application ");
								    				  GUI.consoleText.append(listOfImageNotFoundInApplication.size()+" asset not available in application "+"\n");
								    				  fileUploadAndInfo.createNewDummyPagesAndUpload(programeName,folderName,listOfImageNotFoundInApplication,imageDir);								    				  
								    				  for(int i = 0;i<listOfImageNotFoundInApplication.size();i++){
								    					  String fileName = "";
								    					  fileName = listOfImageNotFoundInApplication.get(i);
								    					  ReadTemplate.deleteFile(imageDir+fileName);
								    				  }
								    			  }else{
								    				  GUI.logger.info("All images are available in application.");
								    				  GUI.consoleText.append("All images are available in application."+"\n");
								    			  }
								    			  		
								    			  readTemplate.getNoChangeListAndCreateDummyPage(htmllinkedMap,listOfPageCreationType);
								    			  readTemplate.getInputAndPourContent(htmllinkedMap);									    		  
									    		  GUI.logger.info("Execution completed, press reset button for next execution.");
									    		  GUI.consoleText.append("Execution completed, press reset button for next execution.\n");						    		  
								    		  }else{
								    			 GUI.logger.info("Asset info not found so can't start page creation process, Please try again.");
								    			 GUI.consoleText.append("Asset info not found so can't start page creation process, Please try again.\n");
								    		  }
						    	  }else{
						    		  GUI.logger.info("Module "+programeName+" not found.");
						    		  GUI.consoleText.append("Module "+programeName+" not found."+"\n");
						    	  }
						}catch (Exception e) {
							// TODO: handle exception
							GUI.logger.info(e.getMessage());
						}			
					}catch (Exception e) {
						// TODO: handle exception
						GUI.logger.info("Press reset button and try again.");
						GUI.consoleText.append("Press reset button and try again.\n");
					}
			  }else{
				  GUI.logger.info(templateFolderPath+" does not contain any html file, Please try again.");
				  GUI.consoleText.append(templateFolderPath+" does not contain any html file, Please try again.\n"); 
			  }
		}finally {
			ReadTemplate.deleteFile(GUI.assetsPathAtLoacalMachine);
			if(driver != null){
				driver.quit();
				driver = null;
				GUI.resetBtn.setEnabled(true);
			}
		}
	}
}
