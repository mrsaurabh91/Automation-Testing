package com.magic;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import com.magic.utils.EnvironmentPropertiesReader;
import com.magic.utils.SeleniumUtils;
import com.magic.utils.WebDriverFactory;

public class EscScriptMain {
	
	
	
	public static void main(String[] args) throws InterruptedException, IOException {
		PDFQuestionExtraction mainPage = new PDFQuestionExtraction();
		mainPage.launchBrowser();
		
	/*	QuestionPourOperation questionPourOperation = new QuestionPourOperation();		
		questionPourOperation.createQuestion();
		*/
		
		
	}

}
