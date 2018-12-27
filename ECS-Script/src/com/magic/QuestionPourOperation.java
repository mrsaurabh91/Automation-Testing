package com.magic;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import com.magic.utils.EnvironmentPropertiesReader;
import com.magic.utils.SeleniumUtils;
import com.magic.utils.WebDriverFactory;

public class QuestionPourOperation {

	EnvironmentPropertiesReader envProperties = EnvironmentPropertiesReader.getInstance();
	
	WebDriver driver;
	
	int waitSecond = 30;
	
	By userNameBox = By.xpath("//input[@id='id_username']");
	By passwordBox = By.xpath("//input[@id='id_password']");
	By submitButton = By.xpath("//input[@id='id_next']");
	By courseElement = By.xpath("//a[text()='Courses']");
	By course = By.xpath("//a[text()='Magic']");
	By questionBank = By.xpath("//a[text()='Question bank']");
	By categoryDowpDown = By.xpath("//select[@id='id_selectacategory']");
	By createNewQuestion = By.xpath("//div[@class='createnewquestion']//div[@class='singlebutton']//input[@type='submit']");
	By multiChoice = By.xpath("//div[@id='chooseform']//input[@value='multichoice']");
	By multiChoiceElement = By.xpath("//div[@id='chooseform']//input[@value='multichoice']/following-sibling::span[@class='typename']");
	By questionName = By.xpath("//input[@id='id_name']");
	
	//By questionText = By.xpath("//div[@id='id_questiontexteditable']");
	By questionText = By.xpath("//textarea[@id='id_questiontext']");
	
	By numberOfChoices = By.xpath("//select[@id='id_answernumbering']");
	
	//By choiceBox1 = By.xpath("//div[@id='id_answer_0editable']");
	By choiceBox1 = By.xpath("//textarea[@id='id_answer_0']");
	By choiceBox1GradeDropdown = By.xpath("//select[@id='id_fraction_0']");
	
	//By choiceBox2 = By.xpath("//div[@id='id_answer_1editable']");
	By choiceBox2 = By.xpath("//textarea[@id='id_answer_1']");
	By choiceBox2GradeDropdown = By.xpath("//select[@id='id_fraction_1']");
	
	//By choiceBox3 = By.xpath("//div[@id='id_answer_2editable']");
	By choiceBox3 = By.xpath("//textarea[@id='id_answer_2']");
	By choiceBox3GradeDropdown = By.xpath("//select[@id='id_fraction_2']");
	
	//By choiceBox4 = By.xpath("//div[@id='id_answer_3editable']");
	By choiceBox4 = By.xpath("//textarea[@id='id_answer_3']");
	By choiceBox4GradeDropdown = By.xpath("//select[@id='id_fraction_3']");
	
	By saveButton = By.xpath("//input[@id='id_submitbutton']");
	
//	public void createQuestion() throws InterruptedException{
	public void createQuestion(LinkedHashMap<String,String> linkedHashMapQuestion,LinkedHashMap<String,ArrayList<String>> linkedHashMapAnswerOption,LinkedHashMap<String,String> linkedHashMapAnswer) throws InterruptedException{
	
		
		this.driver = WebDriverFactory.getWebDriver();
		
		String url = "";
		String userName = "";
		String password = "";
		String category = "";
		
		url = envProperties.getProperty("URL");
		userName = envProperties.getProperty("userName");
		password = envProperties.getProperty("password");
		
		driver.get(url);
		
		SeleniumUtils.waitForVisibility(driver, userNameBox, waitSecond).sendKeys(userName);
		SeleniumUtils.waitForVisibility(driver, passwordBox, waitSecond).sendKeys(password);		
		SeleniumUtils.waitForVisibility(driver, submitButton, waitSecond).click();
		
		//optional 3 line
		/*SeleniumUtils.waitForVisibility(driver, courseElement, waitSecond).click();
		SeleniumUtils.waitForVisibility(driver, course, waitSecond).click();
		SeleniumUtils.waitForVisibility(driver, questionBank, waitSecond).click();*/
		
		SeleniumUtils.waitForVisibility(driver, categoryDowpDown, waitSecond);
		
		category = envProperties.getProperty("category");
		
		WebElement selectElement = SeleniumUtils.waitForVisibility(driver, categoryDowpDown, waitSecond);
		Select select = new Select(selectElement);
		select.selectByValue(category);
		System.out.println("Category Selected");
		SeleniumUtils.waitForVisibility(driver, categoryDowpDown, waitSecond);
		Thread.sleep(5000);
	
		//int testCount = 0;
		for (Entry<String, String> questionEntry : linkedHashMapQuestion.entrySet()) {
			String questionNumberFromQuestionMap = "";
			questionNumberFromQuestionMap = questionEntry.getKey();
			if (linkedHashMapAnswerOption.containsKey(questionNumberFromQuestionMap)&& linkedHashMapAnswer.containsKey(questionNumberFromQuestionMap)) {
				//if (testCount == 0) {
					//testCount++;
					String question = "";
					String correctAnswer = "";
					question = questionEntry.getValue();
					ArrayList<String> answerOption = new ArrayList<String>();
					answerOption = linkedHashMapAnswerOption.get(questionNumberFromQuestionMap);
					correctAnswer = linkedHashMapAnswer.get(questionNumberFromQuestionMap);
					createNewQuestion(question, answerOption, correctAnswer);
				//	SeleniumUtils.waitForVisibility(driver, saveButton, waitSecond).click();
					System.out.println("Question Created");
				//}
			}	
		}
	}
	
	public void createNewQuestion(String question,ArrayList<String> answerOption,String correctAnswer){
		
		SeleniumUtils.waitForVisibility(driver, createNewQuestion, waitSecond).click();
		SeleniumUtils.waitForVisibility(driver, multiChoice, waitSecond).click();
		System.out.println("Clicked on multichoice");
		Actions action = new Actions(driver);
		WebElement multichoice =  driver.findElement(multiChoiceElement);
		action.doubleClick(multichoice).build().perform();
		System.out.println("Double Click on multichoice");
		
		
		SeleniumUtils.waitForVisibility(driver, questionName, waitSecond).sendKeys("Testing");
		SeleniumUtils.waitForVisibility(driver, questionText, waitSecond).sendKeys(question);
		SeleniumUtils.waitForVisibility(driver, numberOfChoices, waitSecond);
		
		Select numberOfChoicesSelect = new Select(driver.findElement(numberOfChoices));
		numberOfChoicesSelect.selectByValue("ABCD");
		
		for(int questionCount = 0; questionCount<answerOption.size(); questionCount++){
			String questionText = "";
			questionText = answerOption.get(questionCount);
			if(questionCount == 0){
				SeleniumUtils.waitForVisibility(driver, choiceBox1, waitSecond).sendKeys(questionText);
			}if(questionCount == 1){
				SeleniumUtils.waitForVisibility(driver, choiceBox2, waitSecond).sendKeys(questionText);
			}if(questionCount == 2){
				SeleniumUtils.waitForVisibility(driver, choiceBox3, waitSecond).sendKeys(questionText);
			}if(questionCount == 3){
				SeleniumUtils.waitForVisibility(driver, choiceBox4, waitSecond).sendKeys(questionText);
			}
		}
		
		if(correctAnswer.equalsIgnoreCase("A")){
			Select correctAnswerSelect = new Select(driver.findElement(choiceBox1GradeDropdown));
			correctAnswerSelect.selectByVisibleText("100%");
		}else if(correctAnswer.equalsIgnoreCase("B")){
			Select correctAnswerSelect = new Select(driver.findElement(choiceBox2GradeDropdown));
			correctAnswerSelect.selectByVisibleText("100%");
		}else if(correctAnswer.equalsIgnoreCase("C")){
			Select correctAnswerSelect = new Select(driver.findElement(choiceBox3GradeDropdown));
			correctAnswerSelect.selectByVisibleText("100%");
		}else if(correctAnswer.equalsIgnoreCase("D")){
			Select correctAnswerSelect = new Select(driver.findElement(choiceBox4GradeDropdown));
			correctAnswerSelect.selectByVisibleText("100%");
		}
		
	}
	
}
