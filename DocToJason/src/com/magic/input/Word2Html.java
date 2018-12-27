package com.magic.input;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.xwpf.converter.core.IURIResolver;
import org.apache.poi.xwpf.converter.xhtml.DefaultContentHandlerFactory;
import org.apache.poi.xwpf.converter.xhtml.IContentHandlerFactory;
import org.apache.poi.xwpf.converter.xhtml.XHTMLOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import com.magic.main.Main;

public class Word2Html  {


	public Word2Html() {
		super();
		// TODO Auto-generated constructor stub
	}

	public static String createHTMLPage(String path) {
		
		String fullPath = "";
		fullPath = path;
		
		String htmlFileName = "";	
		
		InputStream fis = null;
		try {

			fis = new FileInputStream(fullPath);
			XWPFDocument document = new XWPFDocument(fis);
			final String imgUrl = "ImageLoader?imageId=";
			XHTMLOptions options = XHTMLOptions.create().URIResolver(new IURIResolver() {

				@Override
				public String resolve(String uri) {
					int ls = uri.lastIndexOf('/');
					if (ls >= 0)
						uri = uri.substring(ls + 1);
					return imgUrl + uri;
				}
			});
					
			htmlFileName = getHTMlPageName(fullPath);
			
			//Main.consoleText.append("\n"+htmlFileName);
			System.out.println(htmlFileName);
			
			FileOutputStream out = new FileOutputStream(new File(htmlFileName));
			
			IContentHandlerFactory factory = options.getContentHandlerFactory();
			if (factory == null) {
				factory = DefaultContentHandlerFactory.INSTANCE;
			}
			options.setIgnoreStylesIfUnused(false);
			Mapper mapper = new Mapper(document, factory.create(out, null, options), options);
			mapper.start();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		return htmlFileName;
	}
	
	static String getHTMlPageName(String str){
		
		String newName = "";
		String exetention = ".html";
		String temp = "";
		boolean checkExe = false;
		
		int countOfSlash = 0;
		
		for(int i = str.length()-1; i>=0; i--){
			temp  =temp+str.charAt(i);
			if(str.charAt(i) == '.' && countOfSlash == 0){
				if(temp.contains("xcod.") || temp.contains("cod.")){
					checkExe = true;
					continue;
				}
			}
			if(checkExe){
				if(str.charAt(i) == '\\' && countOfSlash == 0){
					countOfSlash++;
				}
				newName = newName+str.charAt(i);
			}
		}
		
		if(newName.length() > 0){
			newName = new StringBuilder(newName).reverse().toString()+exetention;
			return newName;
		}else{
			return newName;
		}
	}
	

}
