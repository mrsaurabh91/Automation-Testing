package com.magic.sussex.gui;

import javax.swing.SwingWorker;

import com.magic.sussex.canvasDataPour.MainSussexPageCreation;

public class ExecutionAferPageActionTypeSelected extends SwingWorker<Void, Void> {

	
	String url;
	String userName;
	String password;
	String programeName;
	String folderName;
	String imageUpload;
	String assetsPathAtLoacalMachine;
	String docFilePath;
	String templateFolderPath;
	
	
	ExecutionAferPageActionTypeSelected(String url,String userName,String password,String programeName,String folderName,String docFilePath,String templateFolderPath){
		
		this.url = url;
		this.userName = userName;
		this.password = password;
		this.programeName = programeName;
		this.folderName = folderName;
	/*	this.imageUpload = imageUpload;
		this.assetsPathAtLoacalMachine = assetsPathAtLoacalMachine;*/
		this.docFilePath = docFilePath;
		this.templateFolderPath = templateFolderPath;
		
	}
	
	@Override
	protected Void doInBackground() throws Exception {
		// TODO Auto-generated method stub
		
		System.out.println("Given url "+url);
			
			try {
				MainSussexPageCreation mainSussexPageCreation = new MainSussexPageCreation();
				mainSussexPageCreation.perform(url, userName, password, programeName, folderName, docFilePath, templateFolderPath);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		
		
		return null;
	}

	
	
}
