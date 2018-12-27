package com.magic.hmh;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;



public class XmlDataMapping {
	
	static int displayTitlecol = 1;
	static int descriptionCol = 2;
	static int keywordCol = 36;
	static int l1Col = 11;
	static int l1LabelCol = 12;
	static int l1TitleCol = 13;
	static int l2Col = 14;
	static int l2LabelCol = 15;
	static int l2TitleCol = 16;
	static int l3Col = 17;
	static int l3LabelCol = 18;
	static int l3TitleCol = 19;
	static int l4Col = 20;
	static int l4LabelCol = 21;
	static int l4TitleCol = 22;
	static int l5Col = 23;
	static int l5LabelCol = 24;
	static int l5TitleCol = 25;
	static int l6Col = 26;
	static int l6LabelCol = 27;	
	static int l6TitleCol = 28;
	static int guidCol = 29;
	static int componentCol = 30;
	static int mediaTypeCol = 32;
	
	
	private static String mdsFileName = "";
	private static String manifestXmlName = "";
	private static int startRow = 0;

	public static void main(String[] args) throws FileNotFoundException, IOException, InterruptedException {
		Properties properties = null;
		properties = new Properties();
		properties.load(new FileInputStream("./input/input.properties"));
		mdsFileName = properties.getProperty("mdsFileName").trim();
		manifestXmlName = properties.getProperty("manifestXmlName").trim();
		startRow = Integer.valueOf(properties.getProperty("startRow").trim());
		
		System.out.println(mdsFileName);
		System.out.println(manifestXmlName);
		System.out.println(startRow);
		
		String reportFilename = "Report " +mdsFileName.replace(".xlsx", "");
		String sheetName = "Sheet1";
		String resultFolderName = "final_result";
		try{
			
		
		int totalRow = 0;
		totalRow = countRowsFromFile(mdsFileName);
		System.out.println("totalRow "+totalRow);
		
		
		
		Map<String,LinkedHashMap<String,String>> xmlLinkedHashMap = new LinkedHashMap<String,LinkedHashMap<String,String>>();
		Map<String,LinkedHashMap<String,String>> inputLinkedHashMap = new LinkedHashMap<String,LinkedHashMap<String,String>>();
	
		for(int row = (startRow-1);row <= totalRow;row++){
			
			LinkedHashMap<String,String> inputGuidDescriptionKeyword = new LinkedHashMap<String,String>();
			LinkedHashMap<String,String> xmlGuidDescriptionKeyword = new LinkedHashMap<String,String>();
			
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
			
			guid = readExcelUserInput(mdsFileName, row, guidCol).trim();
			
			System.out.println("guid "+guid);
			if(!guid.equalsIgnoreCase("") || !guid.isEmpty()){
				tempDescription = readExcelUserInput(mdsFileName, row, descriptionCol).trim();
				keyword = readExcelUserInput(mdsFileName, row, keywordCol).trim();
				l1Value = readExcelUserInput(mdsFileName, row, l1Col).trim();
				l1Label	= readExcelUserInput(mdsFileName, row, l1LabelCol).trim();
				l1Title = readExcelUserInput(mdsFileName, row, l1TitleCol).trim();
				l2Value = readExcelUserInput(mdsFileName, row, l2Col).trim();
				l2Label = readExcelUserInput(mdsFileName, row, l2LabelCol).trim();
				l2Title = readExcelUserInput(mdsFileName, row, l2TitleCol).trim();
				l3Value = readExcelUserInput(mdsFileName, row, l3Col).trim();
				l3Label = readExcelUserInput(mdsFileName, row, l3LabelCol).trim();
				l3Title = readExcelUserInput(mdsFileName, row, l3TitleCol).trim();
				l4Value = readExcelUserInput(mdsFileName, row, l4Col).trim();
				l4Label = readExcelUserInput(mdsFileName, row, l4LabelCol).trim();
				l4Title = readExcelUserInput(mdsFileName, row, l4TitleCol).trim();
				l5Value = readExcelUserInput(mdsFileName, row, l5Col).trim();
				l5Label = readExcelUserInput(mdsFileName, row, l5LabelCol).trim();
				l5Title = readExcelUserInput(mdsFileName, row, l5TitleCol).trim();
				l6Value = readExcelUserInput(mdsFileName, row, l6Col).trim();
				l6Label = readExcelUserInput(mdsFileName, row, l6LabelCol).trim();
				l6Title = readExcelUserInput(mdsFileName, row, l6TitleCol).trim();
				
				displayTitle = readExcelUserInput(mdsFileName, row, displayTitlecol).trim();
				componentValue = readExcelUserInput(mdsFileName, row, componentCol).trim();
				mediaType = readExcelUserInput(mdsFileName, row, mediaTypeCol).trim();
				
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
				System.out.println("GUID NOT FOUND");
			}
		}
		
		xmlLinkedHashMap = readManifestXml(manifestXmlName);
		
		ExcelUtils.createExcelFile(resultFolderName,reportFilename, sheetName);
		ExcelUtils.getExcelFile(resultFolderName,reportFilename);
		ExcelUtils.getSheet(sheetName);
		
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
	}catch (Exception e) {
		// TODO: handle exception
		
	}finally {
		ExcelUtils.closeExcelFile(resultFolderName,reportFilename);
	}
		
	}
	
	public static LinkedHashMap<String,String> readManifestXml(String guid,String file){
		
		LinkedHashMap<String,String> xmlLinkedHashMap = new LinkedHashMap<String,String>();
		try {				
	
			File fXmlFile = new File("./input/"+file);
			System.out.println("file "+file);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
			XPathFactory xPathfactory = XPathFactory.newInstance();
			XPath xpath = xPathfactory.newXPath();
			boolean presenseOfItemCodeInXml= false;
			
				XPathExpression expr = xpath.compile("//item/item");
				NodeList nl = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
				System.out.println("nl "+nl.getLength());
				for (int i = 0; i < nl.getLength(); i++){
				    Node resourceNode = nl.item(i);			
				    String key = "";
				    key = resourceNode.getAttributes().getNamedItem("identifier").getNodeValue();		
				    if(key.contains(guid)){
				    	presenseOfItemCodeInXml = true;				    	
				    //	System.out.println("resources pass");
				    	Element standardNodElement = (Element)resourceNode;
				    	NodeList childNodeOfItem = standardNodElement.getChildNodes();
				  //  	System.out.println("childNodeOfItem "+childNodeOfItem);
				    	for(int j = 0;j<childNodeOfItem.getLength();j++){
				    		Node childNodesName = childNodeOfItem.item(j);
				    //		System.out.println("childNodesName "+childNodesName.getNodeName());
				    		if(childNodesName.getNodeName().equalsIgnoreCase("metadata")){
				    			NodeList metadataNodeList = childNodesName.getChildNodes();
				    			for(int k = 0; k<metadataNodeList.getLength(); k++){
				    				Node childMetaDataNodesName = metadataNodeList.item(k);
				    	//			System.out.println("childMetaDataNodesName "+childMetaDataNodesName.getNodeName());
				    				if(childMetaDataNodesName.getNodeName().equalsIgnoreCase("lomr:lom")){
				    					NodeList metadataChildNodeList = childMetaDataNodesName.getChildNodes();
				    					for(int l = 0;l<metadataChildNodeList.getLength();l++){
				    						Node lchild = metadataChildNodeList.item(l);
						    //				System.out.println("lchild "+lchild.getNodeName());
				    						if(lchild.getNodeName().equalsIgnoreCase("lomr:general")){
				    							NodeList generalNodeList = lchild.getChildNodes();
				    							for(int m = 0;m<generalNodeList.getLength();m++){
				    								Node finalNode = generalNodeList.item(m);
				    								if(finalNode.getNodeName().equalsIgnoreCase("lomr:description")){
				    									Element finalNodElement = (Element)finalNode;
				    									String description = "";
				    									description = finalNodElement.getElementsByTagName("lomr:string").item(0).getTextContent().trim();
				    									System.out.println("description "+description);
				    									xmlLinkedHashMap.put("desc", description);
				    								}else if(finalNode.getNodeName().equalsIgnoreCase("lomr:keyword")){
				    									Element finalNodElement = (Element)finalNode;
				    									String keyword = "";
				    									keyword = finalNodElement.getElementsByTagName("lomr:string").item(0).getTextContent().trim();
				    									System.out.println("keyword "+keyword);
				    									xmlLinkedHashMap.put("keyword", keyword);
				    								}
				    							}
				    							
				    						}
				    						
				    					}
				    					
				    				}
				    			}
				    		}
				    		
				    	}
		         }
				}
				return xmlLinkedHashMap;
		    } catch (Exception e) {
			e.printStackTrace();
		    }
		return xmlLinkedHashMap;
	}
	
	
	public static Map<String,LinkedHashMap<String,String>> readManifestXml(String file){
		
	Map<String,LinkedHashMap<String,String>> xmlLinkedHashMap = new LinkedHashMap<String,LinkedHashMap<String,String>>();
	
		try {				
	
			File fXmlFile = new File("./input/"+file);
			System.out.println("file "+file);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
			XPathFactory xPathfactory = XPathFactory.newInstance();
			XPath xpath = xPathfactory.newXPath();
			boolean presenseOfItemCodeInXml= false;
			
				XPathExpression expr = xpath.compile("//item/item");
				NodeList nl = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
				System.out.println("nl "+nl.getLength());

				for (int i = 0; i < nl.getLength(); i++){
				    Node resourceNode = nl.item(i);			
				    String key = "";
				    key = resourceNode.getAttributes().getNamedItem("identifier").getNodeValue();
				    key = key.replaceFirst("HMH_", "");
				    	presenseOfItemCodeInXml = true;				    	
				    //	System.out.println("resources pass");
				    	Element standardNodElement = (Element)resourceNode;
				    	NodeList childNodeOfItem = standardNodElement.getChildNodes();
				  //  	System.out.println("childNodeOfItem "+childNodeOfItem);
				    	for(int j = 0;j<childNodeOfItem.getLength();j++){
				    		Node childNodesName = childNodeOfItem.item(j);
				    //		System.out.println("childNodesName "+childNodesName.getNodeName());
				    		if(childNodesName.getNodeName().equalsIgnoreCase("metadata")){
				    			NodeList metadataNodeList = childNodesName.getChildNodes();
				    			for(int k = 0; k<metadataNodeList.getLength(); k++){
				    				Node childMetaDataNodesName = metadataNodeList.item(k);
				    	//			System.out.println("childMetaDataNodesName "+childMetaDataNodesName.getNodeName());
				    				if(childMetaDataNodesName.getNodeName().equalsIgnoreCase("lomr:lom")){
				    					NodeList metadataChildNodeList = childMetaDataNodesName.getChildNodes();
				    					for(int l = 0;l<metadataChildNodeList.getLength();l++){
				    						Node lchild = metadataChildNodeList.item(l);
						    //				System.out.println("lchild "+lchild.getNodeName());
				    						if(lchild.getNodeName().equalsIgnoreCase("lomr:general")){
				    							NodeList generalNodeList = lchild.getChildNodes();
				    							
				    							LinkedHashMap<String,String> xmlDataLinkedHashMap = new LinkedHashMap<String,String>();
				    							
				    							for(int m = 0;m<generalNodeList.getLength();m++){
				    								Node finalNode = generalNodeList.item(m);
				    								if(finalNode.getNodeName().equalsIgnoreCase("lomr:description")){
				    									Element finalNodElement = (Element)finalNode;
				    									String description = "";
				    									description = finalNodElement.getElementsByTagName("lomr:string").item(0).getTextContent().trim();
				    							//		System.out.println("description "+description);
				    									description = removeSpecialChar(description);
				    									xmlDataLinkedHashMap.put("desc", description);
				    								}else if(finalNode.getNodeName().equalsIgnoreCase("lomr:keyword")){
				    									Element finalNodElement = (Element)finalNode;
				    									String keyword = "";
				    									keyword = finalNodElement.getElementsByTagName("lomr:string").item(0).getTextContent().trim();
				    									keyword = removeSpecialChar(keyword);
				    							//		System.out.println("keyword "+keyword);
				    									xmlDataLinkedHashMap.put("keyword", keyword);
				    								}
				    							}
				    							xmlLinkedHashMap.put(key, xmlDataLinkedHashMap);
				    						}
				    						
				    					}
				    					
				    				}
				    			}
				    		}
				    	}
				}
				return xmlLinkedHashMap;
		    } catch (Exception e) {
			e.printStackTrace();
		    }
		return xmlLinkedHashMap;
	}
	


	public static String removeSpecialChar(String str){
		
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



	/*public static void doSomething(Node node) {
	    // do something with the current node instead of System.out
	    System.out.println("Node Name "+node.getNodeName());

	    NodeList nodeList = node.getChildNodes();
	    for (int i = 0; i < nodeList.getLength(); i++) {
	        Node currentNode = nodeList.item(i);
	        doSomething(currentNode);
	        if (currentNode.getNodeType() == Node.ELEMENT_NODE) {
	            //calls this method for all the children which is Element
	        	 doSomething(currentNode);
	        }
	    }
	}*/
	
	
	public static String readStandardXml(String standard,String file){
//		System.out.println("readXml");
		
		String standardState_num = "";
		try {				
	
			File fXmlFile = new File("./input/"+file);
//			System.out.println("file "+file);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
			XPathFactory xPathfactory = XPathFactory.newInstance();
			XPath xpath = xPathfactory.newXPath();
			
			
				XPathExpression expr = xpath.compile("//STANDARD");
				NodeList TOPICNodelist = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
				
//				System.out.println("TOPICNodelist "+TOPICNodelist.getLength());
				
				
				outer:
				for (int i = 0; i < TOPICNodelist.getLength(); i++){
					
				    Node resourceNode = TOPICNodelist.item(i);		
				    Element TOPICElement = (Element)resourceNode;
			    	NodeList TOPICTOPICList = (NodeList)TOPICElement.getElementsByTagName("TOPIC");
				    
//			    	System.out.println("curriculumStandardsMetadataSetNodeList "+TOPICTOPICList.getLength());
			    	
			    	for(int TOPICTOPICListCount	= 0; TOPICTOPICListCount<TOPICTOPICList.getLength();TOPICTOPICListCount++){
			    		 Node TOPICTOPICNode = TOPICTOPICList.item(TOPICTOPICListCount);		
						 Element TOPICTOPICElement = (Element)TOPICTOPICNode;
						 String standardFileGuid = TOPICTOPICElement.getAttribute("id");
//			    		 System.out.println("standardFileGuid "+standardFileGuid);
			    		
			    		if(standardFileGuid.equals(standard)){
//			    			System.out.println("Pass");
			    			standardState_num = TOPICTOPICElement.getAttribute("state_num");
//			    			System.out.println("standardState_num "+standardState_num);			    			
			    			break outer;
			    		}
			    	}
				   }
				
				} catch (Exception e) {
					e.printStackTrace();
				}
		return standardState_num;
	}
	
	
	
	private static int countRowsFromFile(String fileName) throws IOException{
		System.out.println("countRowsFromFile");
		int numOFRows = 0;
		try {
	
			File file = new File("./input/"+fileName);
			FileInputStream fileInputStream = new FileInputStream(file);			
			Workbook wb = new XSSFWorkbook(fileInputStream);
			numOFRows= wb.getSheetAt(0).getLastRowNum();
			System.out.println(numOFRows);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (EncryptedDocumentException e) {
			e.printStackTrace();
		}
		return numOFRows;
	}
	
	
	private static String readExcelUserInput(String fileName,int rowNum, int cellNum){
		String data = "";
		XSSFWorkbook wb = null;
		try {
			String fileLocation = "./input/"+fileName; 
			File file = new File(fileLocation);
			FileInputStream fileInputStream = new FileInputStream(file);			
			wb = new XSSFWorkbook(fileInputStream);
			data = wb.getSheetAt(0).getRow(rowNum).getCell(cellNum).getStringCellValue();
			
		}  catch (NullPointerException e) {
			try{
				data = String.valueOf(wb.getSheetAt(0).getRow(rowNum).getCell(cellNum).getNumericCellValue());
	//			e.printStackTrace();
			}catch (Exception e1) {
	//			e.printStackTrace();
			}
		}catch (Exception e) {
			try{
				data = String.valueOf(wb.getSheetAt(0).getRow(rowNum).getCell(cellNum).getNumericCellValue());
		//		e.printStackTrace();
			}catch (Exception e1) {
		//		e.printStackTrace();
			}
		}
		return data;
		
	}
	
	private static int readExcelNumericInput(String fileName,int rowNum, int cellNum){
		int data = 0;
		XSSFWorkbook wb = null;
		try {
			String fileLocation = "./input/"+fileName; 
			File file = new File(fileLocation);
			FileInputStream fileInputStream = new FileInputStream(file);			
			wb = new XSSFWorkbook(fileInputStream);
			data = (int)wb.getSheetAt(0).getRow(rowNum).getCell(cellNum).getNumericCellValue();
		}catch (NullPointerException e) {
			try{
				data = Integer.valueOf(wb.getSheetAt(0).getRow(rowNum).getCell(cellNum).getStringCellValue());
			}catch (Exception e1) {
			}
		} catch (Exception e) {
			try{
				data = Integer.valueOf(wb.getSheetAt(0).getRow(rowNum).getCell(cellNum).getStringCellValue());
			}catch (Exception e1) {
			}
		}
		return data;
		
	}
	
	private static ArrayList getArraylist(String str){
		ArrayList arraylist = new ArrayList();
		String newStr = "";
		
		if(str.contains(",")){
			str = str+",";
			for(int i = 0;	i<str.length();		i++){
				if(str.charAt(i) != ','){
					newStr = newStr +	str.charAt(i);
				}else{
					arraylist.add(newStr.trim());
					newStr = "";
				}
			}
		}else{
			arraylist.add(str.trim());
		}
		return arraylist;
	}
	
	
}
