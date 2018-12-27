package com.magic;

import java.awt.Rectangle;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;
import org.apache.pdfbox.text.TextPosition;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.magic.utils.EnvironmentPropertiesReader;

public class Test2 extends PDFTextStripper  {
	
	public Test2() throws IOException {
		super();
		// TODO Auto-generated constructor stub
	}

	EnvironmentPropertiesReader envProperties ;
	
	static LinkedList<String> coordinate = new LinkedList<>();
	
	static ArrayList<String> questionCharList = new ArrayList<>();
	static ArrayList<String> questionCharFontList = new ArrayList<>();
	static ArrayList<Integer> questionQuardinateList = new ArrayList<Integer>();
	
	LinkedHashMap<String,String> linkedHashMapQuestion = new LinkedHashMap<String,String>();
	LinkedHashMap<String,ArrayList<String>> linkedHashMapAnswerOption = new LinkedHashMap<String,ArrayList<String>>();
	
	public String title="";
	public String text="";
	String heading = "";
	String discription = "";
	
	WebDriver driver;
	
	String pdfFilePath = "";
	
	// TODO Auto-generated constructor stub
	/*public PDFQuestionExtraction() throws IOException {
		super();
		envProperties = EnvironmentPropertiesReader.getInstance();
	}
	*/
	public void launchBrowser() throws InterruptedException, IOException {
		
		System.setProperty("webdriver.gecko.driver", "driver\\geckodriver.exe");
		
		String inputFolderPath = System.getProperty("user.dir");		
		String pdfFileName = envProperties.getProperty("pdfFileName");	
		
		pdfFilePath = inputFolderPath+"\\input\\"+pdfFileName;
		
		try{
			
			driver = new FirefoxDriver();
			driver.manage().window().maximize();
			
			driver.get("file:\\\\\\" + pdfFilePath);
			Thread.sleep(20000);
			
			for (int page = 1; page <= 1; page++) {			
				
				int blocksCount;
				Thread.sleep(2000);
				String pageCount = driver.findElement(By.xpath("//span[@id='numPages']")).getText();
				System.out.println("pageCount : "+pageCount);
				blocksCount = driver.findElements(By.xpath("(//div[@class='page'])["+page+"]//section[@class='squareAnnotation']")).size(); 	//(div[@class='page'])["+page+"]
																											////section[@class='squareAnnotation']
				System.out.println("Blocks Count : " + blocksCount);			
				String questionNumber = "";
				if (blocksCount>0) {
					for (int i = 1; i <= blocksCount; i++) {
						questionCharList.clear();
						questionCharFontList.clear();
						questionQuardinateList.clear();
						coordinate.clear();
						
						heading = "";
						Thread.sleep(1000);
						String commentBoxText = driver.findElement(By.xpath("((//div[@class='page'])["+page+"]//div[@class='popup'])[" + i + "]")).getAttribute("innerHTML").trim();

						commentBoxText = commentBoxText.replaceAll("<h1>", "");
						commentBoxText = commentBoxText.replaceAll("</p>", "");
						commentBoxText = commentBoxText.replaceAll("</h1><p>", "|");

						commentBoxText = commentBoxText.substring(commentBoxText.indexOf("|") + 1, commentBoxText.length());

						System.out.print("str "+commentBoxText + " --> ");
						
						WebElement wb = driver.findElement(By.xpath("((//div[@class='page'])["+page+"]//section[@class='squareAnnotation'])[" + i + "]"));
						
						String left = wb.getCssValue("left").trim();
						String top = wb.getCssValue("top").trim();
						String width = wb.getCssValue("width").trim();
						String height = wb.getCssValue("height").trim();

						String coordinates = left + " : " + top + " : " + width + " : " + height;
						
						if (left.contains("."))
							left = left.substring(0, left.indexOf("."));
						if (top.contains("."))
							top = top.substring(0, top.indexOf("."));
						if (width.contains("."))
							width = width.substring(0, width.indexOf("."));
						if (height.contains("."))
							height = height.substring(0, height.indexOf("."));

						left = left.replaceAll("px", "");
						top = top.replaceAll("px", "");
						width = width.replaceAll("px", "");
						height = height.replaceAll("px", "");
						
						coordinate.add(left + "|" + top + "|" + width + "|" + height);

						System.out.println(coordinate);
						
						int x = Integer.parseInt(left);
						int y = Integer.parseInt(top);
						int h = Integer.parseInt(width);
						int w = Integer.parseInt(height);
						
						String textInsideBox="";
//						
						if(commentBoxText.contains("question")){
							getFontWithChar(page);
							textInsideBox = getQuestion(questionCharList,questionCharFontList,questionQuardinateList);
							
							System.out.println("textInsideBox "+textInsideBox);
							if(!textInsideBox.isEmpty() || !textInsideBox.equalsIgnoreCase("")){
								
								String questionText = "";
								boolean checkQuestionStart = false;
								
								for(int charIndex = 0;charIndex<textInsideBox.length();charIndex++){
									
									char stringChar;
									stringChar = textInsideBox.charAt(charIndex);
									int stringIntValue = (int)stringChar;
									System.out.println("stringIntValue "+stringIntValue);
									
									if(!checkQuestionStart){
										if(stringIntValue>47 && stringIntValue<58){
											questionNumber = questionNumber+textInsideBox.charAt(charIndex);					
										}else if(stringIntValue == 46){
											checkQuestionStart = true;
										}
									}else{
										questionText = questionText+textInsideBox.charAt(charIndex);
									}
								}
								questionText = questionText.trim();
								questionText = getQuestionNumerAndQuestion(questionText);
								if(!questionNumber.equalsIgnoreCase("") && !questionText.equalsIgnoreCase("")){
									linkedHashMapQuestion.put(questionNumber, questionText);
								}
							}
						}else{
							textInsideBox=getRectangleText(x, y, h, w, page).trim();
							if(!textInsideBox.isEmpty() || !textInsideBox.equalsIgnoreCase("")){
								ArrayList<String> optionList = new ArrayList<String>();
								optionList = getAnswerOption(textInsideBox);
								linkedHashMapAnswerOption.put(questionNumber, optionList);
								questionNumber = "";
							}
						}
						System.out.println("questionCharList "+questionCharList);
						System.out.println("questionCharFontList "+questionCharFontList);
						System.out.println("questionQuardinateList "+questionQuardinateList);
					} 
				}
				driver.findElement(By.xpath("//button[@id='next']")).click();
			}
			
		System.out.println("linkedHashMapQuestion "+linkedHashMapQuestion);
		System.out.println("linkedHashMapAnswerOption "+linkedHashMapAnswerOption);
		
		}finally {
			driver.quit();
		}
		
	}

	public static ArrayList<String> getAnswerOption(String str){
		String questionText = "";
		ArrayList<String> optionList = new ArrayList<String>();
		
		int checkingStartLink = 0;
		
		for(int i = 0;i<str.length();i++){
			
				char stringChar;
				stringChar = str.charAt(i);
				int stringIntValue = (int)stringChar;
				
				if(checkingStartLink == 1){
					questionText = questionText+str.charAt(i);
				}
				
				if(i == 0){
					if(stringIntValue>64 && stringIntValue<91){
						checkingStartLink = 1;
						continue;
					}
				}else if(i == str.length()-1){
					if(checkingStartLink == 2){
						questionText = questionText+str.charAt(i);
					}
					questionText = questionText.trim();
					optionList.add(questionText);
				}else{
					if(questionText.contains("\n")){
						checkingStartLink = 2;
					}
					if(checkingStartLink == 2){
						if(stringIntValue>64 && stringIntValue<91){
							questionText = questionText.trim();
							optionList.add(questionText);
							checkingStartLink = 1;
							questionText = "";
						}else{
							questionText = questionText+str.charAt(i);
						}
					}
				}
		}
		System.out.println("optionList "+optionList);
		return optionList;
	}
	
/*	public ArrayList<String> getAnswerOption(String str){
		String questionText = "";
		ArrayList<String> optionList = new ArrayList<String>();
		
		boolean checkQuestionStart = false;
		
		for(int i = 0;i<str.length();i++){
			
			char stringChar;
			stringChar = str.charAt(i);
			int stringIntValue = (int)stringChar;
			
				if(stringIntValue>64 && stringIntValue<69){
					if(!questionText.equalsIgnoreCase("") || !questionText.isEmpty()){
						optionList.add(questionText.trim());
					}
					questionText = "";
					continue;
				}else{
					questionText = questionText+str.charAt(i);
				}
				if(i == str.length()-1){
					if(!questionText.equalsIgnoreCase("") || !questionText.isEmpty()){
						optionList.add(questionText.trim());
					}
				}
		}		
		System.out.println("optionList "+optionList);
		return optionList;
	}
	*/
	public void getFontWithChar(int page) throws InvalidPasswordException, IOException {
		System.out.println("getFontWithChar");
		PDDocument document = null;
		String fileName = pdfFilePath;
		try {
			document = PDDocument.load(new File(fileName));
			System.out.println("Doc Load");
			PDFQuestionExtraction stripper = new PDFQuestionExtraction();
			stripper.setSortByPosition(true);
			stripper.setStartPage(page);
			// stripper.setEndPage(document.getNumberOfPages());
			stripper.setEndPage(page);
			Writer dummy = new OutputStreamWriter(new ByteArrayOutputStream());
			stripper.writeText(document, dummy);
		}
		finally {
			if (document != null) {
				document.close();
			}
		}
	}
	
	@Override
	protected void writeString(String str, List<TextPosition> textPositions) throws IOException {
		
		for (TextPosition position : textPositions) {

			int x = (int) position.getXDirAdj();
			int y = (int) position.getYDirAdj();			
			
			for (int i = coordinate.size()-1; i < coordinate.size(); i++) {

				String temp[] = coordinate.get(i).split("\\|");
				
				int xmin = Integer.parseInt(temp[0]);
				int ymin = Integer.parseInt(temp[1]);
				int xmax = Integer.parseInt(temp[2]);
				int ymax = Integer.parseInt(temp[3]);
				
				if (x > xmin && x < xmax + xmin && y > ymin && y < ymin + ymax) {
					String text = "";
					String fontName = "";
					text = position.getUnicode();
					fontName = position.getFont().getName();				
					System.out.println("text-- "+text+"---"+fontName);
			//		if(!text.equalsIgnoreCase("") || !text.isEmpty()){
						questionCharList.add(text);
						questionCharFontList.add(fontName);
						questionQuardinateList.add(y);
						
			//		}
				}
			}
		}
	}
	
	public String getRectangleText(int x, int y, int h, int w, int pageNumber) throws IOException {

		PDDocument document = PDDocument.load(new File(pdfFilePath));
		PDFTextStripperByArea stripper = new PDFTextStripperByArea();
		stripper.setSortByPosition(true);
		// Rectangle rect = new Rectangle(40, 625, 97, 20);
		Rectangle rect = new Rectangle(x, y, h, w);
		stripper.addRegion("class1", rect);
		PDPage page = document.getPage(pageNumber-1);
		stripper.extractRegions(page);
		String text = stripper.getTextForRegion("class1");
		document.close();
		return text;
	}
	
	public static String getQuestion(ArrayList<String> questionCharList,ArrayList<String> questionCharFontList,ArrayList<Integer> questionQuardinateList){
		String tempQuestion = "";
		
		String boldStartTag = "<b>";
		String boldEndTag = "</b>";
		
		String italicStartTag = "<i>";
		String italicEndTag = "</i>";
		
		// 1 if bold and 2 if Italic
		int checkOfTag = 0;
		
		int tempQuardinate = 0;
		
		int mappingStarted = 0;;
		
		for(int questionChar = 0;questionChar<questionCharList.size();questionChar++){			
			
			for(int questionFont = 0;questionFont<questionCharFontList.size();questionFont++){
				
				for(int questionCharQuardinate = 0;questionCharQuardinate<questionQuardinateList.size();questionCharQuardinate++){
					
					if(questionChar == questionFont && questionFont == questionCharQuardinate){
						
						String charText = "";
						String font = "";
						int actualQuardiante = 0;
						actualQuardiante = questionQuardinateList.get(questionCharQuardinate);
						
						if(mappingStarted == 0){
							tempQuardinate = actualQuardiante;
							mappingStarted++;
						}else{
							if(tempQuardinate != actualQuardiante){
								tempQuestion = tempQuestion+"\n";
								tempQuardinate = actualQuardiante;
							}
						}
						charText = questionCharList.get(questionChar);
						font = questionCharFontList.get(questionFont);
					//	System.out.println(font);
						
							if(font.toLowerCase().contains("bold")){
								if(checkOfTag  == 0 ){
									checkOfTag = 1;
									tempQuestion = tempQuestion+boldStartTag+charText;
								}else if(checkOfTag == 2){
									tempQuestion = tempQuestion+italicEndTag+boldStartTag+charText;
									checkOfTag = 1;
								}else if(checkOfTag  == 1){
									tempQuestion = tempQuestion+charText;
								}
							}else if(font.toLowerCase().contains("italic")){
								if(checkOfTag == 0 ){
									checkOfTag = 2;
									tempQuestion = tempQuestion+italicStartTag+charText;
								}else if(checkOfTag == 1){
									tempQuestion = tempQuestion+boldEndTag+italicStartTag+charText;
									checkOfTag = 2;
								}else if(checkOfTag  == 2){
									tempQuestion = tempQuestion+charText;
								}
							}else{
								if(checkOfTag == 0){
									tempQuestion = tempQuestion+charText;
								}else if(checkOfTag == 1){
									tempQuestion = tempQuestion+boldEndTag+charText;
									checkOfTag = 0;
								}else if(checkOfTag == 2){
									tempQuestion = tempQuestion+italicEndTag+charText;
									checkOfTag = 0;
								}
							}
					}
				}
			}
		}
		return tempQuestion.trim();
	}
	
	public String removeStartEndTag(String questionText){
		String startTagRemovedText = "";
		
		String actualQuestion = "";
		boolean checkTagStart = false;
		
		String tag = "";
		
		String startBoldTag = "<b>";
		String endBoldTag = "</b>";
		String startItalicTag = "<i>";
		String endItalicTag = "</i>";
		
		for(int i = 0;i<questionText.length();i++){
			
			char stringChar;
			stringChar = questionText.charAt(i);
			
			if(i == 0 && stringChar == '<'){
				tag = tag + questionText.charAt(i);
				checkTagStart = true;
				continue;
			}
			if(checkTagStart){
				tag = tag + questionText.charAt(i);
				if(stringChar == '>'){
					if(tag.equalsIgnoreCase(endBoldTag) || tag.equalsIgnoreCase(endItalicTag)){
						checkTagStart = false;
					}
					continue;
				}
			}if(!checkTagStart){
				actualQuestion = actualQuestion+questionText.charAt(i);
			}
		}
		
		actualQuestion = actualQuestion.replaceAll(startBoldTag+endBoldTag, "");
		actualQuestion = actualQuestion.replaceAll(startItalicTag+endItalicTag, "");
		return actualQuestion;
	}
	
	public String getQuestionNumerAndQuestion(String str){
		
		String questionNumber = "";
		String questionText = "";
		boolean checkQuestionStart = false;
		
		for(int i = 0;i<str.length();i++){
			
			char stringChar;
			stringChar = str.charAt(i);
			int stringIntValue = (int)stringChar;
			System.out.println("stringIntValue "+stringIntValue);
			
			if(!checkQuestionStart){
				if(stringIntValue>47 && stringIntValue<58){
					questionNumber = questionNumber+str.charAt(i);					
				}else if(stringIntValue == 46){
					checkQuestionStart = true;
				}
			}else{
				questionText = questionText+str.charAt(i);
			}
		}
		String tempQuestion = "";
		questionText = questionText.trim();
		tempQuestion = removeStartEndTag(questionText);
		return tempQuestion;
	}
	
}
