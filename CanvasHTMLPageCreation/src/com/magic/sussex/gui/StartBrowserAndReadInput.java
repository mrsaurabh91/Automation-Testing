package com.magic.sussex.gui;

import javax.swing.SwingWorker;

import com.magic.sussex.canvasDataPour.MainSussexPageCreation;
import com.magic.sussex.inputDetails.HtmlInputData;
import com.magic.sussex.inputDetails.Word2Html;
import com.magic.util.WebDriverFactory;

public class StartBrowserAndReadInput extends SwingWorker<Void, Void>{

	String docFilePath;
	
	public StartBrowserAndReadInput(String docFilePath) {
		// TODO Auto-generated constructor stub
		this.docFilePath  =  docFilePath;
	}
	
	@Override
	protected Void doInBackground() throws Exception {
		// TODO Auto-generated method stub
		
		GUI.logger.info("Fetching number of pages from the the docx file. Please wait");
		GUI.consoleText.append("Fetching number of pages from the the docx file. Please wait\n");
		
		String htmlPage = "";		
		htmlPage = Word2Html.createHTMLPage(docFilePath);
		
		if(htmlPage.length()>0 || !htmlPage.equals("")){
			GUI.assetsPathAtLoacalMachine = htmlPage;
			System.out.println("GUI.assetsPathAtLoacalMachine "+GUI.assetsPathAtLoacalMachine);
			GUI.resetBtn.setEnabled(false);
			WebDriverFactory.getWebDriver();
			
			try {
				
					MainSussexPageCreation.htmllinkedMap = HtmlInputData.getInputDocFileContentNew(htmlPage);
					
					if(MainSussexPageCreation.htmllinkedMap.size()>0){
						
						int xx = GUI.frame.getX();
						int yy = GUI.frame.getY();

						GUI gui = new GUI();				
						gui.createTable(xx,yy);	
						
					}else{
						GUI.logger.info("You need the select option before proceeding further, press reset button and try again.");
						GUI.consoleText.append("You need the select option before proceeding further, press reset button and try again.\n");	
						if(WebDriverFactory.driver != null){
							WebDriverFactory.driver.quit();
							WebDriverFactory.driver = null;
							GUI.resetBtn.setEnabled(true);
						}
					}
			} catch (InterruptedException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			
		}else{			
			GUI.logger.info("Coversion to html failed. Please try again.");
			GUI.consoleText.append("Coversion to html failed. Please try again.\n");
		}
		return null;
	}

	
	
}
