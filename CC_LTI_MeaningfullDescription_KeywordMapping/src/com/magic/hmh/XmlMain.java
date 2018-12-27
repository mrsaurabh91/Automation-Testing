package com.magic.hmh;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;

public class XmlMain {

	private static String mdsFileName = "";
	private static String manifestXmlName = "";
	private static int startRow = 0;
	static String resultFolderName = "final_result";
	static String inputFolderName = "input";
	
	public static void main(String[] args) throws FileNotFoundException, IOException, InterruptedException {
		// TODO Auto-generated method stub

		
		Properties properties = null;
		properties = new Properties();
		properties.load(new FileInputStream("./input/input.properties"));
		mdsFileName = properties.getProperty("mdsFileName").trim();
		manifestXmlName = properties.getProperty("manifestXmlName").trim();
		startRow = Integer.valueOf(properties.getProperty("startRow").trim());
		
		String version = properties.getProperty("version").trim();
		System.out.println("version "+version);
		System.out.println(mdsFileName);
		System.out.println(manifestXmlName);
		System.out.println(startRow);
	
		Map<String,LinkedHashMap<String,String>> inputLinkedHashMap = new LinkedHashMap<String,LinkedHashMap<String,String>>();
		Map<String,LinkedHashMap<String,String>> xmlLinkedHashMap = new LinkedHashMap<String,LinkedHashMap<String,String>>();
		
		DataUtils dataUtils = new DataUtils();
		inputLinkedHashMap = dataUtils.getExceldData(inputFolderName, mdsFileName, startRow);
		
		XmlData xmlFileData = new XmlData();
		
		if(version.equalsIgnoreCase("1.3")){
			xmlLinkedHashMap = xmlFileData.readManifestXmlV13(inputFolderName, manifestXmlName);
		}else if(version.equalsIgnoreCase("1.2")){
			xmlLinkedHashMap = xmlFileData.readManifestXmlV12(inputFolderName, manifestXmlName);
		}
		
		String reportFilename = "Report " +mdsFileName.replace(".xlsx", "");
		String sheetName = "Sheet1";
		
		try{
		
		ExcelUtils.createExcelFile(resultFolderName,reportFilename, sheetName);
		ExcelUtils.getExcelFile(resultFolderName,reportFilename);
		ExcelUtils.getSheet(sheetName);
		
		if(inputLinkedHashMap.size() > 0 && xmlLinkedHashMap.size() > 0){
			
			int excelRowCounter = 1;
			int excelGUIDCol = 0;
			int excelMeaningfullDescCol = 1;
			int excelKeywordCol = 2;
			int xmlMeaningfullDiscriptionCol = 3;
			int xmlKeywordCol = 4;
			int mappingOfMeaningfullDescCol = 5;
			int mappingOfKeywordCol = 6;
			
			ExcelUtils.writeExcel(0, excelGUIDCol, "GUID in MDS Sheet", "yes","");
			ExcelUtils.writeExcel(0, excelMeaningfullDescCol, "Input MDS Description", "","");
			ExcelUtils.writeExcel(0, excelKeywordCol, "Input MDS Keyword", "","");
			ExcelUtils.writeExcel(0, xmlMeaningfullDiscriptionCol, "Manifest XML Description", "","");
			ExcelUtils.writeExcel(0, xmlKeywordCol, "Manifest XML Keyword", "","");
			ExcelUtils.writeExcel(0, mappingOfMeaningfullDescCol, "Verify Description", "","");
			ExcelUtils.writeExcel(0, mappingOfKeywordCol, "Verify keyword", "","");
			
			for(Entry<String, LinkedHashMap<String, String>> inputMainEntrySet	:	inputLinkedHashMap.entrySet()){
				String inputGuid = "";
				inputGuid = inputMainEntrySet.getKey().trim();
				ExcelUtils.writeExcel(excelRowCounter, excelGUIDCol, inputGuid, "yes","");
				
				if(xmlLinkedHashMap.containsKey(inputGuid)) {
					LinkedHashMap<String,String> inputGuidDescriptionKeyword = new LinkedHashMap<String,String>();
					inputGuidDescriptionKeyword = inputMainEntrySet.getValue();
					
					String inputDesc = "";
					String inputKeyword = "";
					String xmlDesc = "";
					String xmlKeyword = "";
					
					for(Entry<String, String> inputData	:	inputGuidDescriptionKeyword.entrySet()){
						if(inputData.getKey().equalsIgnoreCase("desc")) {
							inputDesc = inputData.getValue();
							ExcelUtils.writeExcel(excelRowCounter, excelMeaningfullDescCol , inputDesc, "","");
						}else if(inputData.getKey().equalsIgnoreCase("keyword")) {
							inputKeyword = inputData.getValue();
							ExcelUtils.writeExcel(excelRowCounter, excelKeywordCol, inputKeyword, "","");
						}
					}
					
					boolean checkLoopCompleted = false;
					
					for(Entry<String, LinkedHashMap<String, String>> xmlMainEntrySet	:	xmlLinkedHashMap.entrySet()){
						String xmlGuid = "";
						xmlGuid = xmlMainEntrySet.getKey().trim();
						
						if(inputGuid.equalsIgnoreCase(xmlGuid)) {
							
							LinkedHashMap<String,String> xmlGuidDescriptionKeyword = new LinkedHashMap<String,String>();
							xmlGuidDescriptionKeyword = xmlMainEntrySet.getValue();
							
							for(Entry<String, String> xmlData	:	xmlGuidDescriptionKeyword.entrySet()){
								checkLoopCompleted = true;
								if(xmlData.getKey().equalsIgnoreCase("desc")) {
									xmlDesc = xmlData.getValue();
									ExcelUtils.writeExcel(excelRowCounter, xmlMeaningfullDiscriptionCol  , xmlDesc, "","");
								}else if(xmlData.getKey().equalsIgnoreCase("keyword")) {
									xmlKeyword = xmlData.getValue();
									ExcelUtils.writeExcel(excelRowCounter, xmlKeywordCol , xmlKeyword, "","");
								}
							}
						}
						if(checkLoopCompleted) {
							break;
						}
					}
					
					if(inputDesc.equalsIgnoreCase(xmlDesc )) {
						ExcelUtils.writeExcel(excelRowCounter, mappingOfMeaningfullDescCol, "Pass", "","");
					}else{
						ExcelUtils.writeExcel(excelRowCounter, mappingOfMeaningfullDescCol , "Fail", "","");
					}
					
					if(inputKeyword .equalsIgnoreCase(xmlKeyword )) {
						ExcelUtils.writeExcel(excelRowCounter, mappingOfKeywordCol  , "Pass", "","");
					}else{
						ExcelUtils.writeExcel(excelRowCounter, mappingOfKeywordCol  , "Fail", "","");
					}
					
				}else {
					ExcelUtils.writeExcel(excelRowCounter, mappingOfMeaningfullDescCol , "GUID not found in xml", "","");
					ExcelUtils.writeExcel(excelRowCounter, mappingOfKeywordCol  , "GUID not found in xml", "","");
				}
				excelRowCounter++;
			}
			
		}else {
			System.out.println("MDS Data or XML Data not found");
		}
		
		
	}catch (Exception e) {
		// TODO: handle exception
		
	}finally {
		ExcelUtils.closeExcelFile(resultFolderName,reportFilename);
		System.out.println("Execution Completed");
	}		
	}

}
