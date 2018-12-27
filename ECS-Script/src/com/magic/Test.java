package com.magic;

import java.util.ArrayList;

public class Test {

	static ArrayList<String> questionCharList = new ArrayList<String>();
	static ArrayList<String> questionCharFontList = new ArrayList<String>();
	static ArrayList<Integer> questionQuardinateList = new ArrayList<Integer>();
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

/*		String question = "<b>100. </b><b></b><b>Based</b> on the story, the reader can conclude that the gum got into Cameron’s hair—";
		getQuestionNumerAndQuestion(question);
*/	/*	String questionOption = "A as he was\n combing it\nB while he was sleeping\nC when he was at school\nD after he woke \nin the \nmorning";
		getAnswerOption(questionOption);
	*/	
	/*	questionCharList.add(" ");
		questionCharList.add(" ");
		questionCharList.add("1");
		questionCharList.add(".");
		questionCharList.add(" ");
		questionCharList.add("B");
		questionCharList.add("a");
		questionCharList.add("s");
		questionCharList.add("e");
		questionCharList.add("d");
		questionCharList.add(" ");
		questionCharList.add(" ");
		
		questionCharFontList.add("LANGIJ+AGaramondPro-Regular");
		questionCharFontList.add("LANGIJ+AGaramondPro-Regular");
		questionCharFontList.add("LANGIK+AGaramondPro-Bold");
		questionCharFontList.add("LANGIK+AGaramondPro-Bold");
		questionCharFontList.add("LANGIK+AGaramondPro-Bold");
		questionCharFontList.add("LANGIJ+AGaramondPro-Regular");
		questionCharFontList.add("LANGIJ+AGaramondPro-Regular");
		questionCharFontList.add("LANGIK+AGaramondPro-Italic");
		questionCharFontList.add("LANGIK+AGaramondPro-Italic");
		questionCharFontList.add("LANGIK+AGaramondPro-Italic");
		questionCharFontList.add("LANGIJ+AGaramondPro-Regular");
		questionCharFontList.add("LANGIJ+AGaramondPro-Regular");
		
		questionQuardinateList.add(83);
		questionQuardinateList.add(83);
		questionQuardinateList.add(83);
		questionQuardinateList.add(83);
		questionQuardinateList.add(83);
		questionQuardinateList.add(83);
		questionQuardinateList.add(83);
		questionQuardinateList.add(91);
		questionQuardinateList.add(91);
		questionQuardinateList.add(91);
		questionQuardinateList.add(91);
		questionQuardinateList.add(91);
		
		System.out.println(getQuestion(questionCharList,questionCharFontList,questionQuardinateList));
		String question = getQuestion(questionCharList,questionCharFontList,questionQuardinateList);
		getQuestionNumerAndQuestion(question);
		*/
//		System.out.println("expect "+"  <b>1. </b>Ba<i>sed</i>  ");
		String correctAnswer = "1 D\n2 D\n10 A\n17 B";
		getCorrectAnswer(correctAnswer);
		
	}
	
	public static void getCorrectAnswer(String anserText){
		
		String[] answerSplitedByNewLine = anserText.split("\n");
		
		for(int splitedAnswerCount = 0; splitedAnswerCount<answerSplitedByNewLine.length; splitedAnswerCount++){
			String questionNumberWithCorrectAnser = "";
			if(!questionNumberWithCorrectAnser.isEmpty() || !questionNumberWithCorrectAnser.equalsIgnoreCase(" ")){
				questionNumberWithCorrectAnser = answerSplitedByNewLine[splitedAnswerCount];
				String[] answerSplitedBySpace = questionNumberWithCorrectAnser.split(" ");
				
				String questionNumber = "";
				String correctAnser = "";
				for(int splitedAnswer = 0; splitedAnswer<answerSplitedBySpace.length;splitedAnswer++){
					
					if(splitedAnswer == 0){
						questionNumber = answerSplitedBySpace[splitedAnswer];
					}else if(splitedAnswer == 1){
						correctAnser = answerSplitedBySpace[splitedAnswer];
					}
				}
				System.out.println("questionNumber "+questionNumber);
				System.out.println("correctAnser "+correctAnser);
			}
		}
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
		return tempQuestion;
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
	
	
	
	
	public static void getQuestionNumerAndQuestion(String textInsideBox){
		
		String questionNumber = "";
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
		System.out.println("questionNumber "+questionNumber);
		System.out.println("questionText "+questionText);
		removeStartEndTag(questionText);
	}
	
	public static String removeStartEndTag(String questionText){
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
	
	public static void getQuestionNumerAndQuestionTest(String str){
		
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
				}else{
					checkQuestionStart = true;
				}
			}else{
				questionText = questionText+str.charAt(i);
			}
		}
		
		System.out.println("questionNumber "+questionNumber);
		System.out.println("questionText "+questionText);
	}

}
