package com.magic.sussex.gui;

import com.magic.sussex.inputDetails.HtmlInputData;
import com.magic.util.WebDriverFactory;

public class Test extends WebDriverFactory{

	
	
	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub

		getWebDriver();
		
		String path = "D:\\AutomationProject\\CanvasDataPour\\input\\Test.html";
		
		HtmlInputData htmlInput = new HtmlInputData();
		htmlInput.getInputDocFileContentNew(path);
		
		driver.close();
	}

	
}
