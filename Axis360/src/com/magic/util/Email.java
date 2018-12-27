package com.magic.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.openqa.selenium.By;

import com.magic.axis360.common.BaseTest;
import com.magic.axis360.input.InputDetails;
import com.magic.util.SeleniumUtil;

public class Email extends BaseTest{
	
	public void EmailSend() throws Exception{
		InputDetails inputDetails = new InputDetails();
		String hostName = inputDetails.getHostName();
		String portNumber = inputDetails.getPortNumber();
		String senderEmailID = inputDetails.getSenderEmailId();
		String senderEmailPassword = inputDetails.getSenderEmailPassword();
		String emailReceiver = inputDetails.getReceiverEmailId();
		String ccEmailReceiver = inputDetails.getCCEmailId();
		String reportpath = System.getProperty("user.dir")+"\\test-output\\ExtentReport.html";
		
		String[] emailReceiverTemp;
		String[] ccEmailReceiverTemp;
		
		
		if(emailReceiver.contains(";")){
			emailReceiverTemp = emailReceiver.toString().split(";");
		}else{
			emailReceiverTemp = emailReceiver.split(";");
		}
		
		if(ccEmailReceiver.contains(";")){
			ccEmailReceiverTemp = ccEmailReceiver.toString().split(";");
		}else{
			ccEmailReceiverTemp = ccEmailReceiver.split(";");
		}
		long executionTime = endTime - startTime;
		System.out.println("executionTime "+executionTime);
		String imagePath = System.getProperty("user.dir")+"\\FailedTestsScreenshots\\"+"extentScreenshot.png";
		System.out.println("imagePath "+imagePath);
		Email.SendEmailNow(hostName, portNumber, senderEmailID, senderEmailPassword, emailReceiverTemp, ccEmailReceiverTemp, executionTime, imagePath, reportpath);
		
		
	}
	
	public void screenShotsOfReport() throws Exception{
		driver.get(System.getProperty("user.dir")+"\\test-output\\ExtentReport.html");
		SeleniumUtil.waitForElementPresence(driver, By.xpath("//a[@view='dashboard-view']"), 30).click();
		SeleniumUtil.getScreenshot(driver, "extentScreenshot");
	}
	
	public static void SendEmailNow(String hostName, String portID, String senderEmailID, String senderPassword, String [] receiverEmailArray, String [] ccEmailArray,long pageLoadTime_Minutes, String imagePath, String reportPath) {
		
		// SMTP info
		String host = hostName;
        String port = portID;

        String mailFrom = senderEmailID;
        String password = senderPassword;
        
        
        
        long timeInSecINTemp = TimeUnit.MILLISECONDS.toSeconds(pageLoadTime_Minutes);
        long timeInMin = TimeUnit.MILLISECONDS.toMinutes(pageLoadTime_Minutes);
 
        // message info
        
        String subject = "Axis360 AUTOMATED TESTING | STATUS";
        StringBuffer body = new StringBuffer("Hi All, <br> <br>  <b> NOTE: THIS IS AUTOMATED EMAIL</b><br> <br>Please find attached file for the complete status of Automation testing. You need to Download the file to view it.<br> <br>");
        body.append("<b>BELOW IS THE DASHBOARD</b><br><br>");        
        body.append("<img src=\"cid:image1\" width=\"75%\" height=\"75%\" /><br>");
        
        String minuteString = "Minute";        
        String secondText = "Second";
        
        if(timeInMin>1){
        	minuteString = "Minutes";
        }
        
        if(timeInSecINTemp>1){
        	secondText = "Seconds";
        }
        
        if(timeInMin == 0){
			body.append("<br><br><b>TOTAL EXECUTION TIME</b>: "+"0 "+minuteString+" and "+timeInSecINTemp+" "+secondText+" <br> <br> Thanks<br>Saruabh Pandey");
		}else{
			int sec = (int) (pageLoadTime_Minutes%60);			
			if(sec>1){
				secondText = "Seconds";
			}
			body.append("<br><br><b>TOTAL EXECUTION TIME</b>: "+timeInMin+" "+minuteString+" and "+sec+" "+secondText+"<br> <br> Thanks<br>Saruabh Pandey");
		}
        body.append("</html>");
 
        // inline images
        Map<String, String> inlineImages = new HashMap<String, String>();
        inlineImages.put("image1", imagePath);
        inlineImages.put("report", reportPath);
        
        ArrayList listOfFiles = SeleniumUtil.getfileNamesFromFolder( System.getProperty("user.dir")+"\\FailedTestsScreenshots");
		System.out.println(listOfFiles);
		
		
		
		for(int i=0;i<listOfFiles.size();i++) {
			
			if(!String.valueOf(listOfFiles.get(i)).equals("extentScreenshot.png")) {
				String screenShotPath = System.getProperty("user.dir")+"/FailedTestsScreenshots/"+listOfFiles.get(i);
				
				inlineImages.put("FailedScreenshot"+i, screenShotPath);
			}
		}
 
        try {
            send(host, port, mailFrom, password, receiverEmailArray,ccEmailArray,subject, body.toString(), inlineImages);
            System.out.println("Email sent.");
        } catch (Exception ex) {
            System.out.println("Could not send email.");
            ex.printStackTrace();
        }
    }
	
	
	public static void send(String host, String port,final String userName, final String password, String[] toAddressArray,String[] ccEmailArray,
            String subject, String htmlBody,
            Map<String, String> mapInlineImages)
                throws AddressException, MessagingException {
        // sets SMTP server properties
        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.user", userName);
        properties.put("mail.password", password);
 
        // creates a new session with an authenticator
        Authenticator auth = new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(userName, password);
            }
        };
        Session session = Session.getInstance(properties, auth);
 
        // creates a new e-mail message
        Message msg = new MimeMessage(session);
 
        msg.setFrom(new InternetAddress(userName));
        for(int i=0;i<toAddressArray.length;i++) {
	    	String toAddress = toAddressArray[i];
	    	System.out.println("toAddress: "+toAddress);
	    	InternetAddress[] toAddresses = { new InternetAddress(toAddress) };
	    	msg.addRecipients(Message.RecipientType.TO, toAddresses);
	    }
        for(int i=0;i<ccEmailArray.length;i++) {
	    	String ccAddress = ccEmailArray[i];
	    	System.out.println("ccAddress: "+ccAddress);
	    	InternetAddress[] ccAddresses = { new InternetAddress(ccAddress) };
	    	msg.addRecipients(Message.RecipientType.CC, ccAddresses);
	    }
        msg.setSubject(subject);
        msg.setSentDate(new Date());
 
        // creates message part
        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setContent(htmlBody, "text/html");
 
        // creates multi-part
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);
 
        // adds inline image attachments
        if (mapInlineImages != null && mapInlineImages.size() > 0) {
            Set<String> setImageID = mapInlineImages.keySet();
             
            for (String contentId : setImageID) {
            	
            	System.out.println(contentId);
            	
                MimeBodyPart imagePart = new MimeBodyPart();
                
                if(contentId.contains("image")) {
                	imagePart.setHeader("Content-ID", "<" + contentId + ">");
                    imagePart.setDisposition(MimeBodyPart.INLINE);
                }
                String imageFilePath = mapInlineImages.get(contentId);
                try {
                    imagePart.attachFile(imageFilePath);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                multipart.addBodyPart(imagePart);
            }
        }
        msg.setContent(multipart);
        Transport.send(msg);
    }
}