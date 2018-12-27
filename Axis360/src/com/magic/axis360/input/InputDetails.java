package com.magic.axis360.input;

import com.magic.util.EnvironmentPropertiesReader;

public class InputDetails {

	EnvironmentPropertiesReader envProperties ;
	
	private String hostName;
	private String portNumber;
	private String senderEmailID;
	private String senderEmailPassword;
	private String receiverEmailId;
	private String ccEmailId;
	private String url;
	private String libraryCard;
	
	public InputDetails() {
		// TODO Auto-generated constructor stub
		envProperties = EnvironmentPropertiesReader.getInstance();
		getHostNameFromPropertiseFile();
		getPostIdFromPropertiseFile();
		getSenderEmailIdIdFromPropertiseFile();
		getSenderEmailPasswordFromPropertiseFile();
		getReceiverEmailIdFromPropertiseFile();
		getCCEmailIDFromPropertiseFile();
		getUrlFromPropertiseFile();
		getLibraryCardFromPropertiseFile();
	}	
	
	private void getUrlFromPropertiseFile(){
		url  = envProperties.getProperty("URL").toString().trim(); 
	}
	
	private void getLibraryCardFromPropertiseFile(){
		libraryCard  = envProperties.getProperty("LIBRARY_CARD").toString().trim(); 
	}
	
	private void getHostNameFromPropertiseFile(){
		hostName  = envProperties.getProperty("HOST_NAME").toString().trim(); 
	}
	
	private void getPostIdFromPropertiseFile(){
		portNumber  = envProperties.getProperty("PORT_ID").toString().trim(); 
	}
	
	private void getSenderEmailIdIdFromPropertiseFile(){
		senderEmailID =  envProperties.getProperty("SENDER_EMAIL_ID").toString().trim(); 
	}
	
	private void getSenderEmailPasswordFromPropertiseFile(){
		senderEmailPassword =  envProperties.getProperty("SENDER_EMAIL_PASSWORD").toString().trim(); 
	}
	
	private void getReceiverEmailIdFromPropertiseFile(){
		receiverEmailId =  envProperties.getProperty("RECEIVER_EMAIL_ID").toString().trim(); 
	}
	
	private void getCCEmailIDFromPropertiseFile(){
		ccEmailId =  envProperties.getProperty("CC_EMAIL_ID").toString().trim(); 
	}
	
	public String getUrl(){		
		return url;
	}
	
	public String getLibraryCard(){		
		return libraryCard;
	}
	
	public String getHostName(){		
		return hostName;
	}
	
	public String getPortNumber(){		
		return portNumber;
	}
	
	public String getSenderEmailId(){		
		return senderEmailID;
	}
	
	public String getSenderEmailPassword(){		
		return senderEmailPassword;
	}
	
	public String getReceiverEmailId(){		
		return receiverEmailId;
	}
	
	public String getCCEmailId(){		
		return ccEmailId;
	}
	
}