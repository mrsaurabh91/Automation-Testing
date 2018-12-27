package com.magic.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/* ExcelUtils Class is used to Create Excel File, get excel file, create sheet and write excel file.
 */

public class ExcelUtils {
	
	static public File file;
	static public FileInputStream fileStream ;
	static public XSSFWorkbook workbook;
	static public FileOutputStream fileOutputStream;
	static public XSSFSheet worksheet;
	static public XSSFRow excelRow;
	static public XSSFCell excelCell;	
	
	public static void getExcelFile(String folderName,String fileName) throws IOException, InterruptedException{
		Thread.sleep(500);		
		try{
			workbook = new XSSFWorkbook(new FileInputStream("./"+folderName+"/"+fileName+".xlsx"));    //for excel file in any folder
			//workbook = new XSSFWorkbook(new FileInputStream("./"+fileName+".xlsx"));	//for file in current directory
		}catch(FileNotFoundException e){
//			e.printStackTrace();
			System.out.println(fileName+" file not found in "+folderName +" folder");
		}catch (Exception e) {
			// TODO: handle exception
//			e.printStackTrace();
			System.out.println(fileName+" file not found in "+folderName +" folder");
		}
			
	}
	
	public static void createExcelFile(String folderName,String fileName,String sheetName) throws IOException, InterruptedException{
		Thread.sleep(500);
		fileOutputStream = new FileOutputStream(new File("./"+folderName+"/"+fileName+".xlsx"));
		workbook = new XSSFWorkbook();
		createSheet(sheetName);
		closeExcelFile(folderName,fileName);
	}
	
	public static void closeExcelFile(String folderName,String fileName) throws IOException{
		FileOutputStream outfile = new FileOutputStream(new File("./"+folderName+"/"+fileName+".xlsx"));
		workbook.write(outfile);
		outfile.flush();
		outfile.close();
		workbook = null;
		worksheet = null;
		file = null;
		fileStream = null;
		excelRow = null;
		excelCell = null;
	}
	
	public static void createSheet(String sheetName){
		worksheet = workbook.createSheet(sheetName);		
	}
	
	public static void getSheet(String sheetName){
		try{
			worksheet = workbook.getSheet(sheetName);
		}catch(Exception e){
//			e.printStackTrace();
			System.out.println(sheetName+" sheet not found in file");
		}
	}
	
	public static void writeExcel(int row,int col,String value,String newRow,String color){	
		if(newRow.equalsIgnoreCase("yes")){
			excelRow = worksheet.createRow(row);			
		}else{
			excelRow = worksheet.getRow(row);			
		}		
		excelCell = excelRow.createCell(col);
		excelCell.setCellValue(value);		
		if(color.equalsIgnoreCase("Green")){
			XSSFCellStyle style = workbook.createCellStyle();
			style.setFillForegroundColor(IndexedColors.GREEN.getIndex());
		    style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		    excelCell.setCellStyle(style);
		}else if(color.equalsIgnoreCase("red")){
			XSSFCellStyle style = workbook.createCellStyle();
			style.setFillForegroundColor(IndexedColors.RED.getIndex());
		    style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		    excelCell.setCellStyle(style);
		}
	}
	
	public static Object readExcelUserInput(String folder,String fileName,String sheet,int rowNum, int cellNum){
		Object data = "";
		XSSFWorkbook wb = null;
		try {
			String fileLocation = "./"+folder+"/"+fileName+".xlsx"; 
			File file = new File(fileLocation);
			FileInputStream fileInputStream = new FileInputStream(file);
			wb = new XSSFWorkbook(fileInputStream);
			data = wb.getSheet(sheet).getRow(rowNum).getCell(cellNum).getStringCellValue();
				} catch (NullPointerException e) {			
				} catch (IllegalStateException e) {			
					data = wb.getSheet(sheet).getRow(rowNum).getCell(cellNum).getNumericCellValue();
					double num = (Double) data;
					int n = (int) num;
					data = n;
				} catch (Exception e) {			
					data = wb.getSheet(sheet).getRow(rowNum).getCell(cellNum).getNumericCellValue();
				double num = (Double) data;
				int n = (int) num;
				data = n;
			}  
			return data;		
	}
	
	public static int getRowCount(String folder,String fileName,String sheet) throws IOException{	
		int numOFRows = 0;
		try {	
			File file = new File("./"+folder+"/"+fileName+".xlsx");
			FileInputStream fileInputStream = new FileInputStream(file);
			Workbook wb = new XSSFWorkbook(fileInputStream);
			numOFRows= wb.getSheet(sheet).getLastRowNum();					
				}catch (NullPointerException e) {
					e.printStackTrace();
				} catch (EncryptedDocumentException e) {
					e.printStackTrace();
				}catch (Exception e) {
					e.printStackTrace();
				}
				return (numOFRows);
			}
}
