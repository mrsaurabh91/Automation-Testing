package com.magic.sussex.canvasDataPour;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import org.openqa.selenium.WebDriver;

import com.magic.sussex.gui.GUI;
import com.magic.sussex.page.LoginPage;
import com.magic.sussex.page.ModulePage;
import com.magic.util.EnvironmentPropertiesReader;
import com.magic.util.FileUtils;
import com.magic.util.SeleniumUtil;
import com.magic.util.WebDriverFactory;

public class MainSussexPageCreation extends WebDriverFactory{

	private static EnvironmentPropertiesReader configProperty = EnvironmentPropertiesReader.getInstance();	
	
	public static LinkedHashMap<String,LinkedHashMap<String,ArrayList<String>>> htmllinkedMap = null;	
	public static ArrayList<String> listOfPageCreationType = null;
	
	public static LinkedHashMap<String,String> template ;
	public static ArrayList<String> listOfImageNotFoundInApplication;
	static LinkedHashMap<String,String> imageLinkedHashMap ;

	public static ArrayList<String> noChangeList ;
	public static ArrayList<String> newPageAlreadyFoundList ;
	public static ArrayList<String> updatePageNoFoundList;
	public static ArrayList<String> issueInNewPageCreation;
	
	final static String dummyImageName = "dummy.jpg";
	
	final String dummyImageFolder = "dummyImage";
	final String htmlPageFolder = "htmlPage";
	final String assetsFolder = "assets";
	final String imageFolder = "image";
	final String videoFolder = "video";	
	
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
							
				 			 String currentDir = System.getProperty("user.dir");				 			
				 			 FileUtils.createDirectory(currentDir+"\\"+htmlPageFolder);
						    							    		
							    			  FileUploadAndInfo fileUploadAndInfo = new FileUploadAndInfo();
							    			  
							    			  String assetFolderPath = "";
							    			  assetFolderPath = currentDir+"\\"+assetsFolder;							    			 
							    			  
								    		  if(fileUploadAndInfo.getAssetInfo(assetFolderPath)){
								    			 
								    			  String dummyImageDir = currentDir+"\\"+dummyImageFolder+"\\";
								    			  
								    			  ReadTemplate readTemplate = new ReadTemplate();
								    			  String newimageFolder = "";
								    			  newimageFolder = currentDir+"\\"+assetsFolder+"\\"+imageFolder;
								    			  
								    			  System.out.println("newimageFolder "+newimageFolder);
								    			  String dummyImage = dummyImageDir+"\\"+dummyImageName;
								    			  System.out.println("dummyImage "+dummyImage);
								    			  
								    			  readTemplate.verifyImageDetailsAvailable(dummyImage,newimageFolder);								    			  
								    			  System.out.println("listOfImageNotFoundInApplication "+listOfImageNotFoundInApplication);
								    			  
								    			  if(listOfImageNotFoundInApplication.size()>0){
								    				  
								    				  GUI.logger.info(listOfImageNotFoundInApplication.size()+" asset not available in application ");
								    				  GUI.consoleText.append(listOfImageNotFoundInApplication.size()+" asset not available in application "+"\n");
								    				  
								    			  }else{
								    				  GUI.logger.info("All images are available in application.");
								    				  GUI.consoleText.append("All images are available in application."+"\n");
								    			  }
								    			  
								    			  String htmlPagePath = currentDir+"\\"+htmlPageFolder;
								    			  
								    			  File htmlPageProgrameFolder = new File(htmlPagePath+"\\"+programeName);
								    			  
								    			  if(!htmlPageProgrameFolder.exists()){								    				  
								    				  FileUtils.createDirectory(htmlPagePath+"\\"+programeName);
								    			  }	
								    			  
								    			  System.out.println("listOfPageCreationType "+listOfPageCreationType);
								    			  readTemplate.getNoChangeListAndCreateDummyPage(htmllinkedMap,listOfPageCreationType,htmlPagePath+"\\"+programeName,programeName);
								    			  readTemplate.getInputAndPourContent(htmllinkedMap,htmlPagePath+"\\"+programeName);	
								    			  
									    		  GUI.logger.info("Execution completed, press reset button for next execution.");
									    		  GUI.consoleText.append("Execution completed, press reset button for next execution.\n");	
								    		  }else{
								    			 GUI.logger.info("Asset info not found so can't start page creation process, Please try again.");
								    			 GUI.consoleText.append("Asset info not found so can't start page creation process, Please try again.\n");
								    		  }						    	  
						}catch (Exception e) {
							// TODO: handle exception
							GUI.logger.info(e.getMessage());
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
