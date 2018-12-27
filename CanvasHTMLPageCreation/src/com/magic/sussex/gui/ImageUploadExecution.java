package com.magic.sussex.gui;

import java.io.File;

import javax.swing.SwingWorker;

import com.magic.sussex.canvasDataPour.FileUploadAndInfo;

public class ImageUploadExecution extends SwingWorker<Void, Void> {

	
	String url = "";
	String userName = "";
	String password = "";
	String programe = "";
	String folderName = "";
	String localmachineAssetsPath = "";
	
	
	public ImageUploadExecution(String url,String userName,String password,String programe,String folderName,String localmachineAssetsPath) {
		// TODO Auto-generated constructor stub
		this.url = url;
		this.userName = userName;
		this.password = password;
		this.programe = programe;
		this.folderName = folderName;
		this.localmachineAssetsPath = localmachineAssetsPath;
		
	}
	
	@Override
	protected Void doInBackground() throws Exception {
		// TODO Auto-generated method stub
		
		
		if(checkFileAvaible(localmachineAssetsPath)){			
			FileUploadAndInfo fileUploadAndInfo = new FileUploadAndInfo();
			fileUploadAndInfo.startImageUpload(url, userName, password, programe, folderName, localmachineAssetsPath);
		}else{
			GUI.imageUploadResetButton.setEnabled(true);
			GUI.logger.info("No any asset found in given folder so asset upload execution not started, please reset, fill all required information and run again");
  		    GUI.consoleText.append("No any asset found in given folder so asset upload execution not started, please reset, fill all required information and run again\n");	
		}
		return null;
	}
	
	
	public boolean checkFileAvaible(String fullFilePath){
		
		File folder = new File(fullFilePath);
        File[] listOfFiles = folder.listFiles();
        if(listOfFiles.length>0){       	 
        	return true;
        }else{
        	 return false;
        }
	}
}