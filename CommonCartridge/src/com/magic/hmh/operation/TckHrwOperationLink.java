package com.magic.hmh.operation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.magic.hmh.pageobject.Dashboard;
import com.magic.hmh.testscript.TestScript;

public class TckHrwOperationLink {
	
	private TestScript testScript;
	private WebDriver dvr;
	
	public TckHrwOperationLink (TestScript testScript,WebDriver dvr) {
		// TODO Auto-generated constructor stub
		this.testScript = testScript;
		this.dvr = dvr;
	}
	
/*	public void selectPrograme() throws InterruptedException, NumberFormatException, IOException{
		
		String hrwCourse = "Go Math Grade 1";
		
		LoginOperation loginOperation = new LoginOperation(testScript, dvr);
		loginOperation.TCKLogin();
		
		Dashboard dashboard = new Dashboard(dvr);
		testScript.waitForClickable(dashboard.getbrowseBtn());
		dashboard.getbrowseBtn().click();
		
		testScript.threadSleep(2);
		testScript.waitForVisibility(dvr.findElement(By.xpath("//*[text()='"+hrwCourse+"']")));
		dvr.findElement(By.xpath("//*[text()='"+hrwCourse+"']")).click();
		testScript.threadSleep(2);
		testScript.waitForClickable(dashboard.getProgrameStructure());
		dashboard.getProgrameStructure().click();
		testScript.threadSleep(2);
		
		ArrayList innerLinkArrayList  = new ArrayList();
		
		List numberOfLinks = dvr.findElements(By.xpath("//div[@id='wb-results']/div[1]/div"));
		if(numberOfLinks.size()>0){
			for(int resourcesLevelElementNumber = 1; resourcesLevelElementNumber <= numberOfLinks.size(); resourcesLevelElementNumber++){
				testScript.threadSleep(2);							
				if(!levelResourcesInnerLinkVisibile(resourcesLevelElementNumber)){
					dvr.findElement(By.xpath("//div[@id='wb-results']/div[1]/div["+resourcesLevelElementNumber+"]/div[1]/a")).click();
				}
				testScript.threadSleep(2);
				List numberOfInnerLink = dvr.findElements(By.xpath("//div[@id='wb-results']/div[1]/div["+resourcesLevelElementNumber+"]//div[@class='panel-heading']"));
				if(numberOfInnerLink.size()>0){	
					
					for(int innerLinkLoop  = 1; innerLinkLoop <= numberOfInnerLink.size(); innerLinkLoop++){
						String innerLinkText  = "";
						ArrayList al = new ArrayList();
						innerLinkText  = dvr.findElement(By.xpath("(//div[@id='wb-results']/div[1]/div["+resourcesLevelElementNumber+"]//div[@class='panel-heading'])["+innerLinkLoop+"]")).getText();
						String index = "";
						
						if(!innerLinkArrayList.contains(innerLinkText)){
							innerLinkArrayList.add(innerLinkText);
							index = String.valueOf(innerLinkArrayList.indexOf(innerLinkText));
							
							if(!innerLinkText.isEmpty()){
								String level1FolderName = "";
								dvr.findElement(By.xpath("(//div[@id='wb-results']/div[1]/div["+resourcesLevelElementNumber+"]//div[@class='panel-heading'])["+innerLinkLoop+"]")).click();
								Thread.sleep(1000);
								List listOfLevel2Folders = null;
								
								try{
										testScript.waitForVisibility(dvr.findElement(By.xpath("(//div[@id='wb-results']/div[1]/div["+resourcesLevelElementNumber+"]//div[@class='panel-body']/div)["+innerLinkLoop+"]//div[@class='panel-body leaf-level']//h4")));
										listOfLevel2Folders = dvr.findElements(By.xpath("(//div[@id='wb-results']/div[1]/div["+resourcesLevelElementNumber+"]//div[@class='panel-body']/div)["+innerLinkLoop+"]//div[@class='panel-body leaf-level']//h4"));
										if(listOfLevel2Folders.size()>0){
											for(int level2FolderElement = 1; level2FolderElement<=listOfLevel2Folders.size();level2FolderElement++){
												String level2FolderName = "";
												level2FolderName = dvr.findElement(By.xpath("((//div[@id='wb-results']/div[1]/div["+resourcesLevelElementNumber+"]//div[@class='panel-body']/div)["+innerLinkLoop+"]//div[@class='panel-body leaf-level']//h4)["+level2FolderElement+"]")).getText();
												al.add(level2FolderName);
												List countOfResources  = getListOfReasources(resourcesLevelElementNumber, innerLinkLoop, level2FolderElement);
																	
												if(countOfResources.size()>0){																
													ArrayList listOfLinkResource = new ArrayList();
													for(int link = 1; link <= countOfResources.size(); link++){
														String linkName = "";																	
														linkName = dvr.findElement(By.xpath("(((//div[@id='wb-results']/div[1]/div["+resourcesLevelElementNumber+"]//div[@class='panel-body']/div)["+innerLinkLoop+"]//div[@class='panel-body leaf-level'])["+level2FolderElement+"]//div[@class='resource'])["+link+"]/a")).getText();
														listOfLinkResource.add(linkName);
													}
													levelLevelResourcehashMap.put(index+"."+(level2FolderElement-1), listOfLinkResource);
												}
											}
										}
									}catch(Exception e){
										testScript.threadSleep(2);
										try{
											testScript.waitForVisibility(dvr.findElement(By.xpath("//div[@id='wb-results']/div[1]/div["+resourcesLevelElementNumber+"]//div[@class='panel-body leaf-level']//h4")));
											listOfLevel2Folders = dvr.findElements(By.xpath("//div[@id='wb-results']/div[1]/div["+resourcesLevelElementNumber+"]//div[@class='panel-body leaf-level']//h4"));
											if(listOfLevel2Folders.size()>0){
												
												for(int level2FolderElement = 1; level2FolderElement<=listOfLevel2Folders.size();level2FolderElement++){
													String level2FolderName = "";
													level2FolderName = dvr.findElement(By.xpath("(//div[@id='wb-results']/div[1]/div["+resourcesLevelElementNumber+"]//div[@class='panel-body leaf-level']//h4)["+level2FolderElement+"]")).getText();
													al.add(level2FolderName);
													List countOfResources  = getListOfReasources(resourcesLevelElementNumber, level2FolderElement);
													if(countOfResources.size()>0){
														ArrayList listOfLinkResource = new ArrayList();
														for(int link = 1; link <= countOfResources.size(); link++){
															String linkName = "";
															linkName = dvr.findElement(By.xpath("((//div[@id='wb-results']/div[1]/div["+resourcesLevelElementNumber+"]//div[@class='panel-body leaf-level'])["+level2FolderElement+"]//div[@class='resource'])["+link+"]/a")).getText();
															listOfLinkResource.add(linkName);
														}
														levelLevelResourcehashMap.put(index+"."+(level2FolderElement-1), listOfLinkResource);
													}
												}
											}
										}catch(Exception f){}
									}
									hashMap.put(index, al);
								}
							}else{
									index = String.valueOf(innerLinkArrayList.indexOf(innerLinkText));
									if(!innerLinkText.isEmpty()){										
										String level1FolderName = "";
										dvr.findElement(By.xpath("(//div[@id='wb-results']/div[1]/div["+resourcesLevelElementNumber+"]//div[@class='panel-heading'])["+innerLinkLoop+"]")).click();
										Thread.sleep(1000);
										List listOfLevel2Folders = null;
										try{
												testScript.waitForVisibility(dvr.findElement(By.xpath("(//div[@id='wb-results']/div[1]/div["+resourcesLevelElementNumber+"]//div[@class='panel-body']/div)["+innerLinkLoop+"]//div[@class='panel-body leaf-level']//h4")));
												listOfLevel2Folders = dvr.findElements(By.xpath("(//div[@id='wb-results']/div[1]/div["+resourcesLevelElementNumber+"]//div[@class='panel-body']/div)["+innerLinkLoop+"]//div[@class='panel-body leaf-level']//h4"));
												if(listOfLevel2Folders.size()>0){
													for(int level2FolderElement = 1; level2FolderElement<=listOfLevel2Folders.size();level2FolderElement++){
														String level2FolderName = "";
														level2FolderName = dvr.findElement(By.xpath("((//div[@id='wb-results']/div[1]/div["+resourcesLevelElementNumber+"]//div[@class='panel-body']/div)["+innerLinkLoop+"]//div[@class='panel-body leaf-level']//h4)["+level2FolderElement+"]")).getText();
																
															
															List countOfResources  = getListOfReasources(resourcesLevelElementNumber, innerLinkLoop, level2FolderElement);	
															if(countOfResources.size()>0){
																ArrayList listOfLinkResource = new ArrayList();
																for(int link = 1; link <= countOfResources.size(); link++){
																	
																	String linkName = "";
																	linkName = dvr.findElement(By.xpath("(((//div[@id='wb-results']/div[1]/div["+resourcesLevelElementNumber+"]//div[@class='panel-body']/div)["+innerLinkLoop+"]//div[@class='panel-body leaf-level'])["+level2FolderElement+"]//div[@class='resource'])["+link+"]/a")).getText();
																	listOfLinkResource.add(linkName);
																}
															
																boolean bodyFolder = false;
																for (HashMap.Entry<String, ArrayList> entry : hashMap.entrySet()) {												
																	String key = entry.getKey();
																	if(key.equals(index)){
																		 //System.out.println("key "+key);
																		ArrayList value = entry.getValue();																		
																		if(!value.contains(level2FolderName)){
																			 //System.out.println("Value not found "+level2FolderName);
																			value.add(level2FolderName);
																			
																		}else{
																			bodyFolder = true;
																		}
																	}		
																}
																if(bodyFolder){
																	int folderIndex = 0;
																	for (HashMap.Entry<String, ArrayList> entry : hashMap.entrySet()) {												
																		String key = entry.getKey();
														//				//System.out.println(key);
														//				//System.out.println("index "+index);
																		if(key.equals(index)){
														//					//System.out.println("key==index");
																			ArrayList value = entry.getValue();
																			for(int i = 0 ;i<value.size();i++){
														//						//System.out.println("i "+value.get(i)+" level2FolderName "+level2FolderName);
																				
																				if(value.get(i).equals(level2FolderName)){
														//							//System.out.println("i- "+i);
																					folderIndex = i;
																				}
																			}																			
																		}		
																	}																	
																	for (HashMap.Entry<String, ArrayList> entry : levelLevelResourcehashMap.entrySet()) {												
																		String key = entry.getKey();
													//					//System.out.println("index "+index+" folderIndex "+folderIndex);
																		String temp = index+"."+folderIndex;
													//					//System.out.println("key "+key+" temp "+temp);
																		if(key.equals(temp)){	
													//						//System.out.println("Key == index");
																			ArrayList value = entry.getValue();
													//						//System.out.println("value "+value);
																			value.addAll(listOfLinkResource);	
													//						//System.out.println("value "+ value);
																		}		
																	}
																}else{
																
																	int folderIndex = 0;
																	for (HashMap.Entry<String, ArrayList> entry : hashMap.entrySet()) {												
																		String key = entry.getKey();
														//				//System.out.println(key);
														//				//System.out.println("index "+index);
																		if(key.equals(index)){
														//					//System.out.println("key==index");
																			ArrayList value = entry.getValue();
																			for(int i = 0 ;i<value.size();i++){
														//						//System.out.println("i "+value.get(i)+" level2FolderName "+level2FolderName);
																				
																				if(value.get(i).equals(level2FolderName)){
														//							//System.out.println("i- "+i);
																					folderIndex = i;
																				}
																			}																			
																		}		
																	}																	
																																	
																	levelLevelResourcehashMap.put(index+"."+(folderIndex), listOfLinkResource);
																}
																}
																}
															}
														}catch(Exception e){
												//			 //System.out.println("catched");
															try{
																testScript.waitForVisibility(dvr.findElement(By.xpath("//div[@id='wb-results']/div[1]/div["+resourcesLevelElementNumber+"]//div[@class='panel-body leaf-level']//h4")));
																listOfLevel2Folders = dvr.findElements(By.xpath("//div[@id='wb-results']/div[1]/div["+resourcesLevelElementNumber+"]//div[@class='panel-body leaf-level']//h4"));
																if(listOfLevel2Folders.size()>0){
																
																for(int level2FolderElement = 1; level2FolderElement<=listOfLevel2Folders.size();level2FolderElement++){
																	String level2FolderName = "";
																	level2FolderName = dvr.findElement(By.xpath("(//div[@id='wb-results']/div[1]/div["+resourcesLevelElementNumber+"]//div[@class='panel-body leaf-level']//h4)["+level2FolderElement+"]")).getText();
																	 //System.out.println(level2FolderName);															
																		
																	List countOfResources  = getListOfReasources(resourcesLevelElementNumber, level2FolderElement);
																	
																	
																	if(countOfResources.size()>0){
																	
																	ArrayList listOfLinkResource = new ArrayList();
//																	//System.out.println("countOfResources "+countOfResources.size());
																
																	for(int link = 1; link <= countOfResources.size(); link++){
																		
																		String linkName = "";
																		linkName = dvr.findElement(By.xpath("((//div[@id='wb-results']/div[1]/div["+resourcesLevelElementNumber+"]//div[@class='panel-body leaf-level'])["+level2FolderElement+"]//div[@class='resource'])["+link+"]/a")).getText();
																//		//System.out.println("linkName "+linkName);
																		listOfLinkResource.add(linkName);
																	}
																	int size = 0;
																	boolean bodyFolder = false;
																	for (HashMap.Entry<String, ArrayList> entry : hashMap.entrySet()) {												
																		String key = entry.getKey();
													//					 //System.out.println("key-- "+key);
																		if(key.equals(index)){
													//						 //System.out.println("key "+key);
																			ArrayList value = entry.getValue();
																			size = value.size();
																			if(!value.contains(level2FolderName)){
														//						 //System.out.println("Value not found "+level2FolderName);
																				value.add(level2FolderName);
																				
																			}else{
																				bodyFolder = true;
																			}
																		}		
																	}
																	
																	if(bodyFolder){
																		int folderIndex = 0;
																		for (HashMap.Entry<String, ArrayList> entry : hashMap.entrySet()) {												
																			String key = entry.getKey();
																			
																			if(key.equals(index)){
																				
																				ArrayList value = entry.getValue();
																				for(int i = 0 ;i<value.size();i++){
															//						//System.out.println("i "+value.get(i)+" level2FolderName "+level2FolderName);
																					
																					if(value.get(i).equals(level2FolderName)){
																//						//System.out.println("i- "+i);
																						folderIndex = i;
																					}
																				}																			
																			}		
																		}																	
																		for (HashMap.Entry<String, ArrayList> entry : levelLevelResourcehashMap.entrySet()) {												
																			String key = entry.getKey();
																			String temp = index+"."+folderIndex;
																			if(key.equals(temp)){	
															//					//System.out.println("Key == index");
																				ArrayList value = entry.getValue();
															//					//System.out.println("value "+value);
																				value.addAll(listOfLinkResource);	
															//					//System.out.println("value "+ value);
																			}		
																		}
																	}else{
																			
																		int folderIndex = 0;
																		for (HashMap.Entry<String, ArrayList> entry : hashMap.entrySet()) {												
																			String key = entry.getKey();
																//			//System.out.println(key);
																//			//System.out.println("index "+index);
																			if(key.equals(index)){
																//				//System.out.println("key==index");
																				ArrayList value = entry.getValue();
																				for(int i = 0 ;i<value.size();i++){
																//					//System.out.println("i "+value.get(i)+" level2FolderName "+level2FolderName);
																					if(value.get(i).equals(level2FolderName)){
																//						//System.out.println("i- "+i);
																						folderIndex = i;
																					}
																				}																			
																			}		
																		}
																		levelLevelResourcehashMap.put(index+"."+(folderIndex), listOfLinkResource);
																	}
																}
															}
														}
															}catch(Exception f){
																 System.out.println("Xpath not found for level 2");
															}
														}
													}
										}
									}	}				}
					}
				
	}
*/	
	private boolean levelResourcesInnerLinkVisibile(int elementNumber){
		boolean innerLinkVisibility = false;
		
		try{
			dvr.manage().timeouts().implicitlyWait(2,TimeUnit.SECONDS) ;
			innerLinkVisibility = dvr.findElement(By.xpath("//div[@id='wb-results']/div[1]/div["+elementNumber+"]//div[@class='panel-heading']")).isDisplayed();
		}catch(Exception e){
			
		}
		dvr.manage().timeouts().implicitlyWait(6,TimeUnit.SECONDS) ;
		return innerLinkVisibility;
	}
	
	private List getListOfReasources(int wbResult,int panelBody,int panelBodyLeafLevel){		
		List numberOfLinks = null;		
		numberOfLinks =dvr.findElements(By.xpath("((//div[@id='wb-results']/div[1]/div["+wbResult+"]//div[@class='panel-body']/div)["+panelBody+"]//div[@class='panel-body leaf-level'])["+panelBodyLeafLevel+"]//div[@class='resource']"));
		return numberOfLinks;
	}

}
