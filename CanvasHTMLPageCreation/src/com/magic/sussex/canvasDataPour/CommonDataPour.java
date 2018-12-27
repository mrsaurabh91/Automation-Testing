package com.magic.sussex.canvasDataPour;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import com.magic.sussex.gui.GUI;

public class CommonDataPour {
	
public String getHomePageTemplateHTMLCode(LinkedHashMap<String,ArrayList<String>> inputDataLinkedHashMap,String fullHTMLCode,ArrayList<String> pageURL) throws InterruptedException{
		
		LinkedHashMap<String,String> templateIDHTMLCode = new LinkedHashMap<String,String>();
		LinkedHashMap<String,String> templateComponentHTMLCode = new LinkedHashMap<String,String>();
		
		ReadTemplate readTemplate = new ReadTemplate();
		
		GUI.logger.info("Reading template....");
		GUI.consoleText.append("Reading template...."+"\n");	
		
		templateIDHTMLCode = readTemplate.getElementViseHTMLCode();
		templateComponentHTMLCode = readTemplate.getComponentHTMLCode();

//		Thread.sleep(10000);
		
		ArrayList<String> extraTagList = new ArrayList<String>();
		
			for(Entry<String,ArrayList<String>> htmlEntrySet:inputDataLinkedHashMap.entrySet()){
				String inputHTMLKey = "";
				inputHTMLKey = htmlEntrySet.getKey();
				if(inputHTMLKey.equalsIgnoreCase("TemplateName")){continue;}
				if(inputHTMLKey.toLowerCase().equalsIgnoreCase("PageTitle")){continue;}
				boolean htmlKey = false;				
				for(Entry<String,String> templateEntrySet:templateIDHTMLCode.entrySet()){
					String templateHTMLKey = "";
					templateHTMLKey = templateEntrySet.getKey();
					if(inputHTMLKey.contains(templateHTMLKey)){						
						htmlKey = true;
					}	
				}
				if(!htmlKey){
					extraTagList.add(inputHTMLKey);
//					System.out.println("inputHTMLKeyNotFound "+inputHTMLKey);
				}
			}
//		System.out.println("extraTagList "+extraTagList);
		if(extraTagList.size() == 0){
			
			GUI.logger.info("Preparing final html to be ingested");
			GUI.consoleText.append("Preparing final html to be ingested"+"\n");
			
			fullHTMLCode = readTemplate.getIDBlockAfterDelete(inputDataLinkedHashMap,templateIDHTMLCode,fullHTMLCode);
			
//			System.out.println("BeforfullHTMLCode "+fullHTMLCode);
			
			for(Entry<String,ArrayList<String>> htmlEntrySet:inputDataLinkedHashMap.entrySet()){				
				
				String inputKey  = htmlEntrySet.getKey().trim();
				
				if(inputKey.toLowerCase().equalsIgnoreCase("templatename")){continue;}
				if(inputKey.toLowerCase().equalsIgnoreCase("PageTitle")){continue;}
				
				GUI.logger.info("InputKey "+inputKey);
				
				String templateIdValue = "";
				String temptemplateIdValue = "";
				
				boolean checkInTemplate = false;
				
				for(Entry<String,String> templateComponentEntrySet:templateIDHTMLCode.entrySet()){
					String templateIDKey = templateComponentEntrySet.getKey().trim();
					if(inputKey.equalsIgnoreCase(templateIDKey)){
						templateIdValue = templateComponentEntrySet.getValue();
						temptemplateIdValue = templateIdValue;
						checkInTemplate = true;
						break;
					}					
				}		
				
				if(checkInTemplate){
					
						ArrayList<String> inputDataComponentList = new ArrayList<String>();
						inputDataComponentList = htmlEntrySet.getValue();
						
						
						ArrayList<String> IDMatchedInTemplateList = new ArrayList<String>();						
						ArrayList<String> notMatchedDataList = new ArrayList<String>();
						
						for(int inputDataComponentCount = 0; inputDataComponentCount<inputDataComponentList.size();inputDataComponentCount++){
							String inputDataComponent = inputDataComponentList.get(inputDataComponentCount).trim();
							
							ArrayList<String> spittedInputList = new ArrayList<String>();
							spittedInputList = readTemplate.getListSplitedByChar(inputDataComponent, '|');							
							
							if(spittedInputList.size()>1){
								
								String sliptedText = "";
								String extactInputComponent = "";
								
								ArrayList<String> splitedCharList = new ArrayList<String>();
								if(spittedInputList.size()>0){
									sliptedText = spittedInputList.get(0);									
									if(sliptedText.contains("_")){									
										splitedCharList = readTemplate.getListSplitedByChar(sliptedText, '_');
										if(splitedCharList.size()>0){
											extactInputComponent = splitedCharList.get(0);
										}
									}else{
										extactInputComponent = sliptedText;
									}
								}
								String changedTemplateComponentValue = "";
								String templateComponentValue = "";
								
								boolean checkPresentComponentvalue = false;
								
								for(Entry<String,String> templateComponentEntrySet:templateComponentHTMLCode.entrySet()){
									String templateComponentKey  = templateComponentEntrySet.getKey().trim();
									ArrayList<String> spittedtemplateComponentKeytList = new ArrayList<String>();
									spittedtemplateComponentKeytList = readTemplate.getListSplitedByChar(templateComponentKey, '|');
									String id = "";
									String component = "";
									
									if(spittedtemplateComponentKeytList.size()>0){
										for(int spliteCount = 0; spliteCount<spittedtemplateComponentKeytList.size();spliteCount++){
											if(spliteCount == 0){
												id = spittedtemplateComponentKeytList.get(spliteCount);
											}else if(spliteCount == 1){
												component = spittedtemplateComponentKeytList.get(spliteCount);
											}
										}
									}
									
									if((inputKey.equalsIgnoreCase(id) &&  (extactInputComponent.equalsIgnoreCase(component)))){
										checkPresentComponentvalue = true;
										
										String inputValueToBePoured ="";
										inputValueToBePoured = spittedInputList.get(1).trim();
										
										IDMatchedInTemplateList.add(templateComponentKey);
										changedTemplateComponentValue = templateComponentEntrySet.getValue();	
										templateComponentValue = changedTemplateComponentValue;
										
										
											if(sliptedText.contains("_")){
												
												String inputTagAttributeName = "";
												inputTagAttributeName = splitedCharList.get(1).trim();
												String inputcomponentTagName = "";
												inputcomponentTagName = splitedCharList.get(0).trim();
												
												if(inputValueToBePoured == "" || inputValueToBePoured.isEmpty() || inputValueToBePoured == null){
													GUI.logger.info(sliptedText+" of inputkey "+inputKey+" not have content");												
												}else{
													if(sliptedText.toLowerCase().contains("alt") ){
														templateIdValue = readTemplate.getHTMLCodeAfterImageAltAndURL(templateIdValue,inputTagAttributeName,inputValueToBePoured);
														if(!templateIdValue.isEmpty() || !templateIdValue.equalsIgnoreCase("")){
															GUI.logger.info("Input text "+inputValueToBePoured+" successfully entered in HTML code");
														}else{
															GUI.logger.info("Input text "+inputValueToBePoured+" not entered in HTML code");
														}
													}else if(sliptedText.toLowerCase().contains("href")){												
														if(inputValueToBePoured.contains("http")){
															templateIdValue = readTemplate.getHtmlCodeAfterAttributeChange(templateIdValue,inputcomponentTagName,inputTagAttributeName,inputValueToBePoured);
															if(!templateIdValue.isEmpty() || !templateIdValue.equalsIgnoreCase("")){
																GUI.logger.info("Input text "+inputValueToBePoured+" successfully entered in HTML code");
															}else{
																GUI.logger.info("Input text "+inputValueToBePoured+" not entered in HTML code");
															}
														}else{
															String pageURLTemp  = "";
															pageURLTemp = ReadTemplate.getPageURL(inputValueToBePoured);
															
															if(pageURLTemp.equalsIgnoreCase("") || pageURLTemp == null || pageURLTemp.isEmpty()){
																GUI.logger.info("Page "+inputValueToBePoured+" not found so URL not entered in HTML code");
																GUI.consoleText.append("Page "+inputValueToBePoured+" not found so URL not entered in HTML code\n");
															}else{														
																GUI.logger.info("URL "+pageURLTemp+ " of page "+inputValueToBePoured);
																templateIdValue = readTemplate.getHtmlCodeAfterAttributeChange(templateIdValue,inputcomponentTagName,inputTagAttributeName,pageURLTemp);
																if(!templateIdValue.isEmpty() || !templateIdValue.equalsIgnoreCase("") || templateIdValue != null){
																	GUI.logger.info("Page "+inputValueToBePoured+" URL "+pageURLTemp+" successfully entered in HTML code");
																}else{
																	GUI.logger.info("URL of "+inputValueToBePoured+" not entered in HTML code");
																}
															}
														}
													}else{
														templateIdValue = readTemplate.getHtmlCodeAfterAttributeChange(templateIdValue,inputcomponentTagName,inputTagAttributeName,inputValueToBePoured);
														if(!templateIdValue.isEmpty() || !templateIdValue.equalsIgnoreCase("") || templateIdValue != null){
															GUI.logger.info("Input text "+inputValueToBePoured+" successfully entered in HTML code");
														}else{
															GUI.logger.info("Input text "+inputValueToBePoured+" not entered in HTML code");
														}
													}
												}
											}else{
												String val = "";
												val = spittedInputList.get(0).trim();
												if(val == "" || val.isEmpty() || val == null){
													GUI.logger.info(sliptedText+" of inputkey "+inputKey+" not have content");												
												}else{
													if(inputKey.toLowerCase().equalsIgnoreCase("PreviousPage")){												
														templateIdValue = readTemplate.getHtmlCodeAfterPourContentInPreviousPage(templateIdValue,val,inputValueToBePoured,pageURL);
													}else{
														templateIdValue = readTemplate.getHtmlCodeAfterPourContent(templateIdValue,val,inputValueToBePoured);
													}
													if(!templateIdValue.isEmpty() || !templateIdValue.equalsIgnoreCase("")){
														GUI.logger.info("Input text "+inputValueToBePoured+" successfully entered in HTML code");
													}else{
														GUI.logger.info("Input text "+inputValueToBePoured+" not entered in HTML code");
													}
												}
												
											}
										break;
									}
								}		
								if(!checkPresentComponentvalue){
									notMatchedDataList.add(sliptedText);
									GUI.logger.info(sliptedText+" of inputkey"+inputKey+" not found in Template");
								}
							}
						}						
						if(IDMatchedInTemplateList.size()>0){							
							templateIdValue = getAfterDeleteComponent(inputKey,templateComponentHTMLCode,templateIdValue,IDMatchedInTemplateList);							
						}
				}
				if(checkInTemplate){
//					System.out.println("temptemplateIdValue "+temptemplateIdValue);
//					System.out.println("templateIdValue     "+templateIdValue);
					fullHTMLCode = fullHTMLCode.replace(temptemplateIdValue, templateIdValue.trim());
				}
			}
			return fullHTMLCode.trim();
		}else{
			GUI.logger.info("id "+extraTagList+" not found in Template so html code not poured please do neccessary changes in the input document");	
			GUI.consoleText.append("id "+extraTagList+" not found in Template so html code not poured please do neccessary changes in the input document\n");
			return "";			
		}
	}


	public String getAfterDeleteComponent(String inputKey,LinkedHashMap<String,String> templateComponentHTMLCode,String templateIdHtmlCode,ArrayList<String> foundInInput){
		/*System.out.println("getAfterDeleteComponent");
		System.out.println("templateIdHtmlCode "+templateIdHtmlCode);
		System.out.println("foundInInput "+foundInInput);
		*/
		ReadTemplate readTemplate = new ReadTemplate();
		
		for(Entry<String,String> templateComponentEntrySet:templateComponentHTMLCode.entrySet()){
			String templateComponentKey  = templateComponentEntrySet.getKey().trim();
//			System.out.println("templateComponentKey "+templateComponentKey);
			ArrayList<String> spittedtemplateComponentKeytList = new ArrayList<String>();
			spittedtemplateComponentKeytList = readTemplate.getListSplitedByChar(templateComponentKey, '|');
			String id = "";
			String component = "";
			
			if(spittedtemplateComponentKeytList.size()>0){
				for(int spliteCount = 0; spliteCount<spittedtemplateComponentKeytList.size();spliteCount++){
					if(spliteCount == 0){
						id = spittedtemplateComponentKeytList.get(spliteCount);
					}else if(spliteCount == 1){
						component = spittedtemplateComponentKeytList.get(spliteCount);
					}
				}
			}
			
			if(id.trim().equalsIgnoreCase(inputKey.trim())){
				/*System.out.println("id "+id);
				System.out.println("inputKey "+inputKey);
				System.out.println("component "+component);*/
				boolean checkPresenceOfInput = false;
				for(int i = 0; i<foundInInput.size(); i++){	
					String foundnInput = "";
					foundnInput = foundInInput.get(i).trim();
					ArrayList<String> spittedFoundList = new ArrayList<String>();
					spittedFoundList = readTemplate.getListSplitedByChar(foundnInput, '|');
//					System.out.println("foundnInput "+foundnInput);
					String foundId = "";
					String foundComponent = "";
					
					if(spittedFoundList.size()>0){
						for(int spliteCount = 0; spliteCount<spittedFoundList.size();spliteCount++){
							if(spliteCount == 0){
								foundId = spittedFoundList.get(spliteCount);
							}else if(spliteCount == 1){
								foundComponent = spittedFoundList.get(spliteCount);
							}
						}
					}					
					if(foundId.equalsIgnoreCase(id)){
						if(component.trim().equalsIgnoreCase(foundComponent)){
							checkPresenceOfInput = true;
							break;
						}
					}
					
					
				}
//				System.out.println("checkPresenceOfInput "+checkPresenceOfInput);
				if(!checkPresenceOfInput){
//					System.out.println("templateComponentKey "+templateComponentKey);
					String componentHtmlCode = "";
					componentHtmlCode = templateComponentEntrySet.getValue();
//					System.out.println("componentHtmlCode "+componentHtmlCode);
					templateIdHtmlCode = templateIdHtmlCode.replace(componentHtmlCode, "");
				}
			}		
		}
//		System.out.println("ChangedtemplateIdHtmlCode "+templateIdHtmlCode);
		return templateIdHtmlCode;
	}	
	
	private String getPreviousPageChangeHTMLCode(String htmlCode){
		String newCode = "";
		
		
		return newCode;
	} 
	
}
