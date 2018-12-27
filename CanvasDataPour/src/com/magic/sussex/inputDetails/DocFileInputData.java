package com.magic.sussex.inputDetails;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

public class DocFileInputData {

	
/*	
	public static LinkedHashMap<String,LinkedHashMap<String,ArrayList>> getInputDataNew(String fileName) throws IOException{
//		String fileName = "HomePage.docx";
		FileInputStream fis = new FileInputStream("./input/" + fileName);
        XWPFDocument doc = new XWPFDocument(fis);
        
		LinkedHashMap<String,LinkedHashMap<String,ArrayList>> linkedMap = new LinkedHashMap<String,LinkedHashMap<String,ArrayList>>(); 
		
		List<XWPFParagraph> paragraphs = doc.getParagraphs();

		 for (int i = 0; i<paragraphs.size(); i++) {     
			 	System.out.println(paragraphs.get(i).getText());			 
		 }
		
        List<XWPFTable> tables = doc.getTables();
        System.out.println("tables "+tables.size());
        for (int i = 0; i<tables.size(); i++) {        	
        	XWPFTable table = tables.get(i);
        	String screen = "Page"+String.valueOf(i);
        	System.out.println("Table "+(i+1));
        	System.out.println("table.getNumberOfRows() "+table.getNumberOfRows());
        	
        	LinkedHashMap<String,ArrayList> tempHashMap = new LinkedHashMap<String,ArrayList>();
        	
        	for(int j = 0;j<table.getNumberOfRows();j++){
        		XWPFTableRow row = table.getRow(j);
        		System.out.println("Row "+(j+1));
        		System.out.println("row.getHeight() "+row.getTableCells().size());
  //      		ArrayList keyList = new ArrayList();
        		String key = "";
        		ArrayList valueList = new ArrayList();
        		
        		for(int k = row.getTableCells().size()-1;k>=0;k--){
        			XWPFTableCell cell = row.getCell(k);
        			for (XWPFParagraph para : cell.getParagraphs()) {     
        				String text = para.getText();
        			//	if(j!=0){
        					if(k == 1){
        						if(!text.isEmpty() && (!text.equals(" "))){
        							valueList.add(text);
                					System.out.println("Type "+text);
        						}           				
        					}else  if(k == 0){
        						if(!text.isEmpty() && (!text.equals(" "))){
        							key = text;
        						
                					System.out.println("Content "+text);
        						}        						
        					}        					
        			//	}       				
                    }        			
        		}
        		if(valueList.size() !=0){  
        			if(tempHashMap.size()>0){
        				if(tempHashMap.containsKey(key)){
        					tempHashMap.put(key+(tempHashMap.size()+1), valueList);
        				}else{
        					tempHashMap.put(key, valueList);
        				}
        			}else{
        				tempHashMap.put(key, valueList);
        			}        			
        			
        		}        		
        	}
        	linkedMap.put(screen, tempHashMap);
        }
        System.out.println("-----------------------");
        System.out.println("linkedMapSize "+linkedMap.size()+" linkedMap "+linkedMap);    
        doc.close();
        return linkedMap;
	}
	
*/
}
