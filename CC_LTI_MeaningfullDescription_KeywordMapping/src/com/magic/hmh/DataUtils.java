package com.magic.hmh;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class DataUtils {

	int displayTitlecol = 1;
	int descriptionCol = 2;
	int keywordCol = 36;
	int l1Col = 11;
	int l1LabelCol = 12;
	int l1TitleCol = 13;
	int l2Col = 14;
	int l2LabelCol = 15;
	int l2TitleCol = 16;
	int l3Col = 17;
	int l3LabelCol = 18;
	int l3TitleCol = 19;
	int l4Col = 20;
	int l4LabelCol = 21;
	int l4TitleCol = 22;
	int l5Col = 23;
	int l5LabelCol = 24;
	int l5TitleCol = 25;
	int l6Col = 26;
	int l6LabelCol = 27;	
	int l6TitleCol = 28;
	int guidCol = 29;
	int componentCol = 30;
	int mediaTypeCol = 32;
	
	/*public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		DataUtils dataUtils = new DataUtils();
		Map<String,LinkedHashMap<String,String>> inputLinkedHashMap = new LinkedHashMap<String,LinkedHashMap<String,String>>();
		inputLinkedHashMap = dataUtils.getExceldData("input","World_Geography_2019_CC-OneCMS_9780544673649.xlsx",2);
		System.out.println(inputLinkedHashMap.size());
	}*/
	
	public Map<String,LinkedHashMap<String,String>> getExceldData(String folderName, String fileName,int startRow) throws IOException{
		
		Map<String,LinkedHashMap<String,String>> inputLinkedHashMap = new LinkedHashMap<String,LinkedHashMap<String,String>>();
		
		XSSFWorkbook workbook = null;
		try {
			String fileLocation = "./"+folderName+"/"+fileName; 
			File file = new File(fileLocation);
			FileInputStream fileInputStream = new FileInputStream(file);			
			workbook = new XSSFWorkbook(fileInputStream);
			XSSFSheet workSheet= workbook.getSheetAt(0);
			
			int countOfRow = workSheet.getLastRowNum();
			
			if(startRow<countOfRow){
				
				for(int rowNum = (startRow-1); rowNum <=countOfRow; rowNum++){
					
					LinkedHashMap<String,String> inputGuidDescriptionKeyword = new LinkedHashMap<String,String>();
					
					String finalMeaningfullDescription = "";
					String finalKeyword = "";
					int descriptionCount = 0;
					String tempDescription = "";
					String guid = "";
					String keyword = "";
					String l1Value = "";
					String l1Label = "";
					String l1Title = "";
					String l2Value = "";
					String l2Label = "";
					String l2Title = "";
					String l3Value = "";
					String l3Label = "";
					String l3Title = "";
					String l4Value = "";
					String l4Label = "";
					String l4Title = "";
					String l5Value = "";
					String l5Label = "";
					String l5Title = "";
					String l6Value = "";
					String l6Label = "";
					String l6Title = "";
					
					String keyword1 = ""; 
					String keyword2 = "";
					String keyword3 = "";
					String keyword4 = "";
					String keyword5 = "";
					String keyword6 = "";
					
					String displayTitle = "";
					String componentValue = "";
					String mediaType = "";
					
					XSSFRow row = workSheet.getRow(rowNum);
					if(row != null){
						
						XSSFCell guidCell = row.getCell(guidCol);
						if(guidCell != null){
							guid = convertXSSFCellToString(row.getCell(guidCol));
							
							XSSFCell tempDescriptionCell = row.getCell(descriptionCol);
							if(tempDescriptionCell != null){
								tempDescription = convertXSSFCellToString(row.getCell(descriptionCol));
							}
							
							XSSFCell keywordCell = row.getCell(keywordCol);
							if(keywordCell != null){
								keyword = convertXSSFCellToString(row.getCell(keywordCol));
							}
							
							XSSFCell l1ValueCell = row.getCell(l1Col);
							if(l1ValueCell != null){
								l1Value = convertXSSFCellToString(row.getCell(l1Col ));
							}
							
							XSSFCell l1LabelCell = row.getCell(l1LabelCol);
							if(l1LabelCell != null){
								l1Label = convertXSSFCellToString(row.getCell(l1LabelCol));
							}
							
							XSSFCell l1TitleCell = row.getCell(l1TitleCol);
							if(l1TitleCell != null){
								l1Title = convertXSSFCellToString(row.getCell(l1TitleCol));
							}
							
							XSSFCell l2ValueCell = row.getCell(l2Col);
							if(l2ValueCell != null){
								l2Value = convertXSSFCellToString(row.getCell(l2Col));
							}
							
							XSSFCell l2LabelCell = row.getCell(l2LabelCol);
							if(l2LabelCell != null){
								l2Label = convertXSSFCellToString(row.getCell(l2LabelCol));
							}
							
							XSSFCell l2TitleCell = row.getCell(l2TitleCol);
							if(l2TitleCell != null){
								l2Title = convertXSSFCellToString(row.getCell(l2TitleCol));
							}
							
							XSSFCell l3ValueCell = row.getCell(l3Col);
							if(l3ValueCell != null){
								l3Value = convertXSSFCellToString(row.getCell(l3Col));
							}
							
							XSSFCell l3LabelCell = row.getCell(l3LabelCol);
							if(l3LabelCell != null){
								l3Label = convertXSSFCellToString(row.getCell(l3LabelCol));
							}
							
							XSSFCell l3TitleCell = row.getCell(l3TitleCol);
							if(l3TitleCell != null){
								l3Title = convertXSSFCellToString(row.getCell(l3TitleCol));
							}
							
							XSSFCell l4ValueCell = row.getCell(l4Col);
							if(l4ValueCell != null){
								l4Value = convertXSSFCellToString(row.getCell(l4Col));
							}
							
							XSSFCell l4LabelCell = row.getCell(l4LabelCol);
							if(l4LabelCell != null){
								l4Label = convertXSSFCellToString(row.getCell(l4LabelCol));
							}
							
							XSSFCell l4TitleCell = row.getCell(l4TitleCol);
							if(l4TitleCell != null){
								l4Title = convertXSSFCellToString(row.getCell(l4TitleCol));
							}
							
							XSSFCell l5ValueCell = row.getCell(l5Col);
							if(l5ValueCell != null){
								l5Value = convertXSSFCellToString(row.getCell(l5Col));
							}
							
							XSSFCell l5LabelCell = row.getCell(l5LabelCol);
							if(l5LabelCell != null){
								l5Label = convertXSSFCellToString(row.getCell(l5LabelCol));
							}
							
							XSSFCell l5TitleCell = row.getCell(l5TitleCol);
							if(l5TitleCell != null){
								l5Title = convertXSSFCellToString(row.getCell(l5TitleCol));
							}
							
							XSSFCell l6ValueCell = row.getCell(l6Col);
							if(l6ValueCell != null){
								l6Value = convertXSSFCellToString(row.getCell(l6Col));
							}
							
							XSSFCell l6LabelCell = row.getCell(l6LabelCol);
							if(l6LabelCell != null){
								l6Label = convertXSSFCellToString(row.getCell(l6LabelCol));
							}
							
							XSSFCell l6TitleCell = row.getCell(l6TitleCol);
							if(l6TitleCell != null){
								l6Title = convertXSSFCellToString(row.getCell(l6TitleCol));
							}
							
							XSSFCell displayTitleCell = row.getCell(displayTitlecol);
							if(displayTitleCell != null){
								displayTitle = convertXSSFCellToString(row.getCell(displayTitlecol));
							}
							
							XSSFCell componentValueCell = row.getCell(componentCol);
							if(componentValueCell != null){
								componentValue = convertXSSFCellToString(row.getCell(componentCol));
							}
							
							XSSFCell mediaTypeCell = row.getCell(mediaTypeCol);
							if(mediaTypeCell != null){
								mediaType = convertXSSFCellToString(row.getCell(mediaTypeCol));
							}
							
							/*System.out.println("tempDescription  "+tempDescription);
							System.out.println("guid "+guid);
							System.out.println("keyword "+keyword);
							System.out.println("l1Value "+l1Value);
							System.out.println("l1Label "+l1Label);
							System.out.println("l1Title "+l1Title);
							System.out.println("l2Value "+l2Value);
							System.out.println("l2Label "+l2Label);
							System.out.println("l2Title "+l2Title);
							System.out.println("l3Value "+l3Value);
							System.out.println("l3Label "+l3Label);
							System.out.println("l3Title "+l3Title);
							System.out.println("l4Value "+l4Value);
							System.out.println("l4Label "+l4Label);
							System.out.println("l4Title "+l4Title);
							System.out.println("l5Value "+l5Value);
							System.out.println("l5Label "+l5Label);
							System.out.println("l5Title "+l5Title);
							System.out.println("l6Value "+l6Value);
							System.out.println("l6Label "+l6Label);
							System.out.println("l6Title "+l6Title);
							System.out.println("displayTitle "+displayTitle);
							System.out.println("componentValue "+componentValue);
							System.out.println("mediaType "+mediaType);*/
							
							
							if(!l1Label.isEmpty() && l2Label.isEmpty() && l3Label.isEmpty() && l4Label.isEmpty() && l5Label.isEmpty() && l6Label.isEmpty()) {
								descriptionCount = 2;
							}else if(!l2Label.isEmpty() && !l1Label.isEmpty() && l3Label.isEmpty() && l4Label.isEmpty() && l5Label.isEmpty() && l6Label.isEmpty()){
								if(!l2Label.equalsIgnoreCase(l1Label)){
									descriptionCount = 3;
								}else{
									descriptionCount = 2;
								}
							}else if(!l3Label.isEmpty() && !l2Label.isEmpty() && !l1Label.isEmpty() && l4Label.isEmpty() && l5Label.isEmpty() && l6Label.isEmpty()){
								if(!l2Label.equalsIgnoreCase(l3Label)){
									descriptionCount = 4;
								}else{
									if(l2Label.equalsIgnoreCase(l1Label)){
										descriptionCount = 2;
									}else {
										descriptionCount = 3;
									}
								}
							}else if(!l3Label.isEmpty() && !l2Label.isEmpty() && !l1Label.isEmpty() && !l4Label.isEmpty() && l5Label.isEmpty() && l6Label.isEmpty()){
								if(!l4Label.equalsIgnoreCase(l3Label)){
									descriptionCount = 5;
								}else{
									if(l2Label.equalsIgnoreCase(l1Label)){
										descriptionCount = 2;
									}else if(l2Label.equalsIgnoreCase(l3Label)){
										descriptionCount = 3;
									}else{
										descriptionCount = 4;
									}
								}
							}else if(!l3Label.isEmpty() && !l2Label.isEmpty() && !l1Label.isEmpty() && !l4Label.isEmpty() && !l5Label.isEmpty() && l6Label.isEmpty()){
								if(!l5Label.equalsIgnoreCase(l4Label)){
									descriptionCount = 6;
								}else{
									if(l2Label.equalsIgnoreCase(l1Label)){
										descriptionCount = 2;
									}else if(l2Label.equalsIgnoreCase(l3Label)){
										descriptionCount = 3;
									}else if(l4Label.equalsIgnoreCase(l3Label)){
										descriptionCount = 4;
									}else{
										descriptionCount = 5;
									}
								}
							}else if(!l3Label.isEmpty() && !l2Label.isEmpty() && !l1Label.isEmpty() && !l4Label.isEmpty() && !l5Label.isEmpty() && !l6Label.isEmpty()){
								if(!l5Label.equalsIgnoreCase(l6Label)){
									descriptionCount = 7;
								}else{
									if(l2Label.equalsIgnoreCase(l1Label)){
										descriptionCount = 2;
									}else if(l2Label.equalsIgnoreCase(l3Label)){
										descriptionCount = 3;
									}else if(l4Label.equalsIgnoreCase(l3Label)){
										descriptionCount = 4;
									}else if(l5Label.equalsIgnoreCase(l4Label)){
										descriptionCount = 5;
									}else {
										descriptionCount = 6;
									}
								}
							}
							
							if(descriptionCount == 0){
								finalMeaningfullDescription = tempDescription;
							}
							else if(descriptionCount == 2){
								finalMeaningfullDescription = l1Label+" "+l1Value+": "+tempDescription;		
							}else if(descriptionCount == 3){
								finalMeaningfullDescription = l2Label+" "+l2Value+": "+tempDescription;
							}else if(descriptionCount == 4){
								finalMeaningfullDescription = l2Label+" "+l2Value+", "+l3Label+" "+l3Value+": "+tempDescription;
							}else if(descriptionCount == 5){
								finalMeaningfullDescription = l3Label+" "+l3Value+", "+l4Label+" "+l4Value+": "+tempDescription;
							}else if(descriptionCount == 6){
							}else if(descriptionCount == 7){
							}
							
							if(!l1Label.isEmpty() && !l1Title.isEmpty()) {
								keyword1 = l1Label+" "+l1Value+","+l1Title+",";
							}else if(!l1Label.isEmpty()){
								keyword1 = l1Label+" "+l1Value+",";
							}
							if(!l1Label.equalsIgnoreCase(l2Label)){
								if(!l2Label.isEmpty() && !l2Title.isEmpty()) {
									keyword2 = l2Label+" "+l2Value+","+l2Title+",";
								}else if(!l2Label.isEmpty()){
									keyword2 = l2Label+" "+l2Value+",";
								}
							}
							if(!l2Label.equalsIgnoreCase(l3Label)){
								if(!l3Label.isEmpty() && !l3Title.isEmpty()) {
									keyword3 = l3Label+" "+l3Value+","+l3Title+",";
								}else if(!l3Label.isEmpty()){
									keyword3 = l3Label+" "+l3Value+",";
								}
							}
							if(!l3Label.equalsIgnoreCase(l4Label)){
								if(!l4Label.isEmpty() && !l4Title.isEmpty()) {
									keyword4 = l4Label+" "+l4Value+","+l4Title+",";
								}else if(!l4Label.isEmpty()){
									keyword4 = l4Label+" "+l4Value+",";
								}
							}
							if(!l4Label.equalsIgnoreCase(l5Label)){
								if(!l5Label.isEmpty() && !l5Title.isEmpty()) {
									keyword5 = l5Label+" "+l5Value+","+l5Title+",";
								}else if(!l5Label.isEmpty()){
									keyword5 = l5Label+" "+l5Value+",";
								}
							}
							if(l5Label.equalsIgnoreCase(l6Title)){
								if(!l6Label.isEmpty() && !l6Title.isEmpty()) {
									keyword6 = l6Label+" "+l6Value+","+l6Title+",";
								}else if(!l6Label.isEmpty()){
									keyword6 = l6Label+" "+l6Value+",";
								}
							}
							
							finalKeyword  = keyword1 + keyword2 + keyword3 + keyword4 + keyword5 + keyword6 + displayTitle+","+componentValue+","+mediaType+","+keyword;
							finalMeaningfullDescription = removeSpecialChar(finalMeaningfullDescription);
							finalKeyword = removeSpecialChar(finalKeyword);
							inputGuidDescriptionKeyword.put("desc", finalMeaningfullDescription);
							inputGuidDescriptionKeyword.put("keyword", finalKeyword);
							
							if(!inputLinkedHashMap.containsKey(guid)) {
								inputLinkedHashMap.put(guid, inputGuidDescriptionKeyword);
							}	
							
							
						}else{
							System.out.println("GUID not found");
						}
					}else{
						System.out.println("Data not found in row");
					}
				}
			}else{
				System.out.println("Given startRow is less then totalRow Count");
			}
		}  catch (FileNotFoundException e) {
			
		}
		return inputLinkedHashMap;
	}
	
	public String convertXSSFCellToString(XSSFCell cell) {
		String cellValue = "";
		if (cell != null) {
			int cellType = cell.getCellType();
			if(cellType == 0){
				cellValue = Integer.valueOf((int) cell.getNumericCellValue()).toString().trim();
			}else if(cellType == 1){
				cellValue = String.valueOf(cell);
				cellValue = cellValue.trim();
			}
		} else {
			cellValue = "";
		}
		return cellValue;
	}

	public String removeSpecialChar(String str){
		
		ArrayList<String> specialCharList = new ArrayList<String>();
		ArrayList<String> changedCharList = new ArrayList<String>();
		
		specialCharList.add("&quot;");
		specialCharList.add("&#039;");
		
		changedCharList.add("\"");
		changedCharList.add("'");
		
		for(int i = 0;i<specialCharList.size();i++){
			String data = "";
			data = specialCharList.get(i);
			if(str.contains(data)){
				str = str.replaceAll(data, changedCharList.get(i));
			}
		}
		return str;
	}
	
}
