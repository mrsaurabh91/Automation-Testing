package com.magic.sussex.inputDetails;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class TemplateData {

	static String filePath = "./Templates/";
	
	public static LinkedHashMap<String,String> getTemplateCode() throws Exception{
		
		ArrayList htmlFileList = new ArrayList();
		htmlFileList = getFileNames(filePath,"html");
		System.out.println("htmlFileList "+htmlFileList);
		 
		LinkedHashMap<String,String> htmlMap = new LinkedHashMap<String,String>();  
			
		for(int htmlFileCount = 0; htmlFileCount<htmlFileList.size(); htmlFileCount++){
			 String text = "";
			 text = readFileAsString(filePath+htmlFileList.get(htmlFileCount));
			 System.out.println("htmlFileCount "+htmlFileCount);
			 System.out.println(text);
			 htmlMap.put((String) htmlFileList.get(htmlFileCount), text);
		 }
		 System.out.println("htmlMapSize "+htmlMap.size()+" htmlMap "+htmlMap);
		return htmlMap;
	}
	
	public static String readFileAsString(String fileName)throws Exception{	  
	    String data = "";
	    data = new String(Files.readAllBytes(Paths.get(fileName)));
	    return data;
	  }
	
	public static ArrayList getFileNames(String filelocation, String type) throws FileNotFoundException, IOException {

        ArrayList filenames = new ArrayList();
        System.out.println("fileLocation pre"+filelocation);
        filelocation.replace("\\", "\\\\");
        System.out.println("fileLocation post"+filelocation);
        File folder = new File(filelocation);
        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                String filename = listOfFiles[i].getName();
                if (filename.contains(type)) {
                    filenames.add(filename);
                }
            }
        }
        return filenames;
    }
	
}
