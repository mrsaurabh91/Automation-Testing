package com.magic.hmh.dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class InputDetails {
	
	public Properties properties;	
	
	public InputDetails() {
		// TODO Auto-generated constructor stub
		properties = new Properties();
		try {
			properties.load(new FileInputStream("./input/details.properties"));
        } catch (IOException e) {
            e.printStackTrace();
         }	
	}

	
	public String getBrowserName(){
		String browserName = "";
		browserName = properties.getProperty("browserName");		
		return browserName;
	}
	
	public String getInputEnvironment(){
		String inputEnvironment = "";
		inputEnvironment = properties.getProperty("inputEnvironment");		
		return inputEnvironment;
	}
	
	public String evalOrProd(){
		String evalOrProd = "";
		evalOrProd = properties.getProperty("evalORprod");		
		return evalOrProd;
	}
	
	public String evalEMail(){
		String evalEMail = "";
		evalEMail = properties.getProperty("evalEMail");		
		return evalEMail;
	}
	
	public String evalSite(){
		String evalSite = "";
		evalSite = properties.getProperty("evalSite");		
		return evalSite;
	}
	
	public String getInputURL(){
		String inputUrl = "";
		inputUrl = properties.getProperty("inputURl");	
		return inputUrl;
	}
	
	public String getUserName(){
		String userName = "";
		userName = properties.getProperty("inputUserName");	
		return userName;
	}
	
	public String getPassword(){
		String password = "";
		password = properties.getProperty("inputPassword");	
		return password;
	}
	
	public String getInputCourse(){
		String inputPrograme = "";
		inputPrograme = properties.getProperty("inputCourse");	
		return inputPrograme;
	}
	
	public String getEnvironment(){
		String environment = "";
		environment = properties.getProperty("environment");		
		return environment;
	}
	
	public String geturl(){
		String url = "";
		url = properties.getProperty("url");	
		return url;
	}
	
	
	public String getBrainhoneyUserName(){
		String userName = "";
		userName = properties.getProperty("brainhoneyUserName");	
		return userName;
	}
	
	public String getBrainhoneyPassword(){
		String password = "";
		password = properties.getProperty("brainhoneyPassword");	
		return password;
	}
	
	public String getBrainhoneyurl(){
		String url = "";
		url = properties.getProperty("url");	
		return url;
	}
	
	public String getCourseName(){
		String course = "";
		course = properties.getProperty("course");	
		return course;
	}
	
	public String getMdsFileName(){
		String fileName = "";
		fileName = properties.getProperty("mdsFileName");	
		return fileName;
	}
	
	public String getMdsSheetName(){
		String sheetName = "";
		sheetName = properties.getProperty("mdsSheetName");	
		return sheetName;
	}
	
	public String getExecutionType(){
		String executionType = "";
		executionType = properties.getProperty("executionType");	
		return executionType;
	}
	
	public String getHrwUnitNumber(){
		String hrwUnitNumber = "";
		hrwUnitNumber = properties.getProperty("hrwUnitNumber");	
		return hrwUnitNumber;
	}
	
	public String getBrainHoneyFolderNumber(){
		String brainHoneyFolderNumber = "";
		brainHoneyFolderNumber = properties.getProperty("brainHoneyFolderNumber");	
		return brainHoneyFolderNumber;
	}
	
	public String getExecutionPrograme(){
		String executionPrograme = "";
		executionPrograme = properties.getProperty("executionPrograme");	
		return executionPrograme;
	}
}
