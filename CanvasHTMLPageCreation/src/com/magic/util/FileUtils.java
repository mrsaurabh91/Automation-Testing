package com.magic.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;

import com.magic.sussex.gui.GUI;

/**
 * FileUtils consists copy/move a file from source to destination location
 */
public class FileUtils {

	/**
	 * Copy a file from one location to another
	 * 
	 * @param f1
	 *            - Source file
	 * @param f2
	 *            - Destination File
	 * @throws IOException
	 */
	public static void copyFile(File f1, File f2) throws IOException {
		InputStream in = new FileInputStream(f1);

		// For Overwrite the file.
		OutputStream out = new FileOutputStream(f2);

		byte[] buf = new byte[1024];
		int len;
		while ((len = in.read(buf)) > 0) {
			out.write(buf, 0, len);
		}
		in.close();
		out.close();
	}

	/***
	 * Method move the file from source location, rename it and place it in the
	 * destination location and deletes the old file from the destination
	 * location if any
	 * 
	 * @param oldFile
	 * @param newFile
	 * @throws IOException
	 */
	public static void moveFile(String oldFile, String newFile) throws IOException {
		File oldfile = new File(oldFile);
		File newfile = new File(newFile);
		copyFile(oldfile, newfile);
		oldfile.delete();
	}
	
	/**Method reads content from file and returns the content available in the file
	 * @param FileName - Source file
	 * @return text - content of file
	 * @throws Exception 
	 */
	public static String readFile(String FileName) throws Exception{  
		 String text="";
		  try{
			 
		    FileInputStream textFile=new FileInputStream(System.getProperty("user.dir")+FileName);  
		    int i=0;  
		    while((i=textFile.read())!=-1){  
		     text=text+(char)i;
		    }  
		    textFile.close();  
		  }catch(Exception e){
			  
		  }
		return text;  
		 }  
	
	public static void deleteFile(String file){	
		
		String fileName = "";
		fileName = file;
		try{
            Files.deleteIfExists(Paths.get(fileName));
        }
        catch(NoSuchFileException e){
            GUI.logger.info("No such file/directory exists");
        }
        catch(DirectoryNotEmptyException e){
            GUI.logger.info("Directory is not empty");
        }
        catch(IOException e){
            GUI.logger.info("Invalid permissions");
        }
    }
	
	public static void createDirectory(String fileName){
		
		File file = new File(fileName);
        if (!file.exists()) {
            if(file.mkdir()) {
                GUI.logger.info(fileName+" folder is created!");
            }else{
                GUI.logger.info("Issue in creating folder "+fileName);
            }
        }else{
        	 GUI.logger.info("Folder already available so failed to create new folder!");
        }
    }
	
}
