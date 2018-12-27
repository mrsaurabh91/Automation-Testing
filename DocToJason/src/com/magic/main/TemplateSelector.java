package com.magic.main;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import org.json.simple.parser.ParseException;

import com.magic.input.HtmlInputData;
import com.magic.input.Word2Html;

public class TemplateSelector {
	
	static LinkedHashMap<String, LinkedHashMap<String, ArrayList<String>>> linkedHashmapLevel1 = new LinkedHashMap<>();
	static LinkedHashMap<String, ArrayList<String>> linkedHashmap = new LinkedHashMap<>();
	static String tableNumber;
	
	public static void main(String[] args) {
		String docFilePath = "D:\\Programe\\Json Input data\\Input\\Json input file\\Word_Grid_Data same.docx";
		String folderPath = "D:\\Programe\\Json Input data\\Input\\Output";
//	}
//	public static void selectTemplate(String docFilePath,String folderPath) throws InterruptedException {
		
				try {
					String htmlFilePath = Word2Html.createHTMLPage(docFilePath);
					linkedHashmapLevel1 = HtmlInputData.getInputDocFileContentNew(htmlFilePath);
					for (Map.Entry<String, LinkedHashMap<String, ArrayList<String>>> entry : linkedHashmapLevel1.entrySet()) {
						tableNumber = entry.getKey();
						System.out.println("Table Number : " + tableNumber);
					}
					String templateType="";
					linkedHashmap = linkedHashmapLevel1.get("1");
					if(linkedHashmap.get("TemplateType").size()>0) {
						
						templateType=linkedHashmap.get("TemplateType").get(0);
						System.out.println(templateType);
						
						if(templateType.equalsIgnoreCase("Word_Grid"))
						{
							Cross_Word_Json.convertToJson(docFilePath, folderPath,linkedHashmapLevel1 );
						}
						else if(templateType.equalsIgnoreCase("daa")) {
							//FirstJsonTool.convertToJson(docFilePath, folderPath);
						}
						
					}
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
					
				
		
		
	}

}
