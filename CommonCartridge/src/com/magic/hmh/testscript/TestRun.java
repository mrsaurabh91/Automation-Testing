package com.magic.hmh.testscript;

import java.io.IOException;

import org.openqa.selenium.WebDriver;

import com.magic.hmh.operation.LoginOperation;
import com.magic.hmh.dao.InputDetails;
import com.magic.hmh.operation.BrainhoneyFolderOperation;
import com.magic.hmh.operation.InputFolderOperation;
import com.magic.hmh.operation.InputFolderOperation1;
import com.magic.hmh.testscript.TestScript;



public class TestRun{
	
	private TestScript testScript;
	private WebDriver dvr;
	
	public TestRun(TestScript testScript,WebDriver dvr) {
		// TODO Auto-generated constructor stub
		
		this.testScript = testScript;
		this.dvr = dvr;
	}
	
	
	public void runSript() throws InterruptedException, IOException{
		
			// Main programe
		String executiontype = "";
		InputDetails inputDetails = new InputDetails();
		executiontype = inputDetails.getExecutionPrograme();
		
		if(executiontype.equalsIgnoreCase("0")){
			InputFolderOperation programeOperation = new InputFolderOperation(testScript, dvr);
			programeOperation.selectPrograme();
		}else if(executiontype.equalsIgnoreCase("1")){
			InputFolderOperation1 programeOperation1 = new InputFolderOperation1(testScript, dvr);
			programeOperation1.selectPrograme();
		}else{
			System.out.println("Given programe not found");
		}
		
		
		/*	
			
		*/	
			
		
			
			/*
			programeOperation1.selectPrograme();
			*/// Demo Programe

	/*		HRWOperation hrwOperation = new HRWOperation(testScript, dvr);
			hrwOperation.selectPrograme();
	*/	
  			
	}
	
	
	

	
	
	

}
