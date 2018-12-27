package com.magic.hmh.operation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.magic.hmh.dao.InputDetails;
import com.magic.hmh.generic.BaseAction;
import com.magic.hmh.pageobject.Dashboard;
import com.magic.hmh.testscript.TestScript;

public class InputFolderOperation1 {
	private TestScript testScript;
	private WebDriver dvr;
	
	int mdsStartRow = 4;
	int guidCol = 38;
	int urlCol = 47;
	
	public InputFolderOperation1(TestScript testScript,WebDriver dvr) {
		// TODO Auto-generated constructor stub
		this.testScript = testScript;
		this.dvr = dvr;
	}
		
	
	public void selectPrograme() throws InterruptedException, NumberFormatException, IOException{
		
		
		InputDetails inputDetails = new InputDetails();
		String inputCourse = inputDetails.getInputCourse();
		String course  = inputDetails.getCourseName();
		String mdsFile = inputDetails.getMdsFileName();
		String sheetName = inputDetails.getMdsSheetName();
		
		boolean multipleCourse = false;
		String[] inputProgrameName = null;
		String[] brainHoneycourseName = null;
		String[] mdsFileName = null;
		String[] mdsSheetName = null;
		
//		String[] inputCourseSpilt;
		int countOfCourses = 0;
		int countOFCourseBrainHoney = 0;
		int mdsFileCount = 0;
		int mdsSheetCount = 0;
		
		if(inputCourse.contains(",") && course.contains(",") && mdsFile.contains(",") && sheetName.contains(",")){
			multipleCourse = true;
			inputProgrameName =  inputCourse.split(",");	
			brainHoneycourseName = course.split(",");
			mdsFileName = mdsFile.split(",");
			mdsSheetName = sheetName.split(",");
			countOfCourses = inputProgrameName.length;
			countOFCourseBrainHoney = brainHoneycourseName.length;
			mdsFileCount = mdsFileName.length;
			mdsSheetCount = mdsSheetName.length;
		}else{
			countOfCourses = 1;
			countOFCourseBrainHoney = 1;	
			mdsFileCount = 1;
			mdsSheetCount = 1;
		}
		
		if(countOfCourses!= 0 && countOFCourseBrainHoney !=0 && mdsFileCount!=0 && mdsSheetCount != 0){
		if(countOfCourses==countOFCourseBrainHoney && mdsFileCount==mdsSheetCount && countOfCourses == mdsFileCount){
			
				
		for(int courseCount = 0; courseCount < countOfCourses; courseCount++){
			System.out.println("Course Count Of Execution "+(courseCount+1));
			String hrwCourse = "";
			String bHCourse = "";
			String mds = "";
			String sheet = "";
			
			if(!multipleCourse){
				bHCourse = course;
				hrwCourse = inputCourse;
				mds = mdsFile;
				sheet = sheetName;
			}else{
				hrwCourse = inputProgrameName[courseCount];
				bHCourse = brainHoneycourseName[courseCount];
				mds = mdsFileName[courseCount];
				sheet = mdsSheetName[courseCount];
			}
			
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
		
		HashMap<String,String> mdsMapData = new HashMap<String,String>();
		
		BaseAction baseAction = new BaseAction();
		mdsMapData = baseAction.readExcelUserInput(mds, sheet, mdsStartRow, guidCol, urlCol);
		
		ArrayList innerLinkArrayList  = new ArrayList();
		
		HashMap<String,ArrayList> hashMap = new HashMap<String,ArrayList>();
		HashMap<String,ArrayList> level2FolderMap = new HashMap<String,ArrayList>();	
		HashMap<String,ArrayList> lessonLevel3FolderMap = new HashMap<String,ArrayList>();
		HashMap<String,ArrayList> lessonLevel4FolderMap = new HashMap<String,ArrayList>();
		
		HashMap<String,ArrayList> levelLevelResourcehashMap = new HashMap<String,ArrayList>();
		HashMap<String,ArrayList> level2FolderResourceHashMap = new HashMap<String,ArrayList>();
		HashMap<String,ArrayList> lessonLevel4FolderResourceHashMap = new HashMap<String,ArrayList>();
		
		HashMap<String,String> linkNameHashMap= new HashMap<String,String>();
		HashMap<String,String> lunchedUrlHashMap= new HashMap<String,String>();

			if(levelLevelResouresMenu()){
			testScript.threadSleep(2);
				if(levelLevelResoure()){
					
						List numberOfLinks = dvr.findElements(By.xpath("//div[@id='wb-results']/div[1]/div"));
						
						if(numberOfLinks.size()>0){
							
						for(int resourcesLevelElementNumber = 1; resourcesLevelElementNumber <= numberOfLinks.size(); resourcesLevelElementNumber++){
//							for(int resourcesLevelElementNumber = 2; resourcesLevelElementNumber <= 2; resourcesLevelElementNumber++){
							testScript.threadSleep(2);							
							
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
								//		System.out.println("innerLinkText "+innerLinkText);
										
										if(innerLinkText.equals("Las tradiciones")){
											
										
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
																			String hrefLink = "";
																			String linkedGUID = "";
																			String url = "";
																			WebElement linkElement = dvr.findElement(By.xpath("(((//div[@id='wb-results']/div[1]/div["+resourcesLevelElementNumber+"]//div[@class='panel-body']/div)["+innerLinkLoop+"]//div[@class='panel-body leaf-level'])["+level2FolderElement+"]//div[@class='resource'])["+link+"]/a"));
																			linkName = linkElement.getText();																	
																			linkedGUID = baseAction.getLinkedGUID(linkElement.getAttribute("href"));
																			System.out.println("linkedGUID "+linkedGUID);
																			url = getUrl(linkElement);
																			
																			linkNameHashMap.put(linkedGUID, linkName);
																			lunchedUrlHashMap.put(linkedGUID, url);
																			
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
																		String hrefLink = "";
																		String linkedGUID = "";
																		String url = "";
																		WebElement linkElement =dvr.findElement(By.xpath("((//div[@id='wb-results']/div[1]/div["+resourcesLevelElementNumber+"]//div[@class='panel-body leaf-level'])["+level2FolderElement+"]//div[@class='resource'])["+link+"]/a"));
																		linkName = linkElement.getText();
																		linkedGUID = baseAction.getLinkedGUID(linkElement.getAttribute("href"));
																		System.out.println("linkedGUID "+linkedGUID);
																		url = getUrl(linkElement);
																		linkNameHashMap.put(linkedGUID, linkName);
																		lunchedUrlHashMap.put(linkedGUID, url);																		
																		listOfLinkResource.add(linkName);
																	}
																	levelLevelResourcehashMap.put(index+"."+(level2FolderElement-1), listOfLinkResource);
																	}
															}}
														}catch(Exception f){
													
														}
							
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
																	String hrefLink = "";
																	String linkedGUID = "";
																	String url = "";
																	WebElement linkElement = dvr.findElement(By.xpath("(((//div[@id='wb-results']/div[1]/div["+resourcesLevelElementNumber+"]//div[@class='panel-body']/div)["+innerLinkLoop+"]//div[@class='panel-body leaf-level'])["+level2FolderElement+"]//div[@class='resource'])["+link+"]/a"));
																	linkName = linkElement.getText();
																	linkedGUID = baseAction.getLinkedGUID(linkElement.getAttribute("href"));
																	System.out.println("linkedGUID "+linkedGUID);
																	url = getUrl(linkElement);
																	linkNameHashMap.put(linkedGUID, linkName);
																	lunchedUrlHashMap.put(linkedGUID, url);
																	
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
																		String hrefLink = "";
																		String linkedGUID = "";
																		String url = "";
																		WebElement linkElement = dvr.findElement(By.xpath("((//div[@id='wb-results']/div[1]/div["+resourcesLevelElementNumber+"]//div[@class='panel-body leaf-level'])["+level2FolderElement+"]//div[@class='resource'])["+link+"]/a"));
																		linkName = linkElement.getText();
																//		//System.out.println("linkName "+linkName);																		
																		linkedGUID = baseAction.getLinkedGUID(linkElement.getAttribute("href"));
																		System.out.println("linkedGUID "+linkedGUID);
																		url = getUrl(linkElement);
																		linkNameHashMap.put(linkedGUID, linkName);
																		lunchedUrlHashMap.put(linkedGUID, url);																		
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
										}
									}	}				}
					}
				}
		}
				if(panelHeadingExist()){				
					List panelHeadingList = dvr.findElements(By.xpath("//div[@id='browseview']//div[@id='wb-program-structure']/div"));

					if(panelHeadingList.size()>0){						
						for(int panelHeadingCount = 1; panelHeadingCount <= panelHeadingList.size(); panelHeadingCount++){
//							for(int panelHeadingCount = 1;panelHeadingCount <= 1; panelHeadingCount++){
							
							testScript.threadSleep(2);
						
							ArrayList level2FoldersList = new ArrayList();
						
							String panelHeadingText = "";
							
							try{
								panelHeadingText = dvr.findElement(By.xpath("(//div[@id='browseview']//div[@id='wb-program-structure']/div)["+panelHeadingCount+"]")).getText();
							}catch(Exception e){
								try{
									panelHeadingText = dvr.findElement(By.xpath("(//div[@id='browseview']//div[@id='wb-program-structure']/div)["+panelHeadingCount+"]")).getText();
								}catch(Exception f){
									System.out.println("PanelHeadingText not found");
								}
							}
							
							System.out.println("-- "+panelHeadingText);
							
							String level1Folderindex = "";
							
							if(!innerLinkArrayList.contains(panelHeadingText)){
								innerLinkArrayList.add(panelHeadingText);
								level1Folderindex = String.valueOf(innerLinkArrayList.indexOf(panelHeadingText));
							}else{
								level1Folderindex = String.valueOf(innerLinkArrayList.indexOf(panelHeadingText));
							}
							testScript.threadSleep(2);
							try{
								dvr.findElement(By.xpath("(//div[@id='browseview']//div[@id='wb-program-structure']/div)["+panelHeadingCount+"]")).click();
							}catch(Exception e){
								try{									
									dvr.findElement(By.xpath("(//div[@id='browseview']//div[@id='wb-program-structure']/div)["+panelHeadingCount+"]")).click();
								}catch(Exception f){
									System.out.println("Unit panal not clicked");
								}
							}
							testScript.threadSleep(2);
							
							String resultHeading = "";
							
							try{
								testScript.waitForVisibility(dvr.findElement(By.xpath(".//div[@id='wb-results']/div[1]/h3")));
								resultHeading = dvr.findElement(By.xpath(".//div[@id='wb-results']/div[1]/h3")).getText();
							}catch(Exception e){
								try{
									testScript.waitForVisibility(dvr.findElement(By.xpath(".//div[@id='wb-results']/div[1]/h3")));
									resultHeading = dvr.findElement(By.xpath(".//div[@id='wb-results']/div[1]/h3")).getText();									
								}catch(Exception f){
									System.out.println("Result heading not found");
								}
							}
							
							testScript.threadSleep(2);
							
								if(levelLevelResoure()){
									if(panelHeadingText.contains(resultHeading)){											
										getResources(level1Folderindex,level2FolderMap,hashMap,level2FoldersList,level2FolderResourceHashMap,linkNameHashMap,lunchedUrlHashMap);
										
									}else{
										System.out.println("Unit Name in Programe Structure and Heading is not matching");
									}
								}else{
										System.out.println("wb-results not found of Unit");
								}
							
			
							testScript.threadSleep(2);
							List listOfLesson = dvr.findElements(By.xpath("((//div[@id='browseview']//div[@id='wb-program-structure']/div)["+panelHeadingCount+"]//div[@class='panel-body'])"));
							testScript.threadSleep(1);
							if(listOfLesson.size()>0){
							ArrayList lessonArrayList = new ArrayList();
							for(int lessonCount = 1;  lessonCount <= listOfLesson.size(); lessonCount++ ){
								testScript.threadSleep(2);
								try{
									String lessonName = "";
									testScript.waitForVisibility(dvr.findElement(By.xpath("((//div[@id='browseview']//div[@id='wb-program-structure']/div)["+panelHeadingCount+"]//div[@class='panel-body'])["+lessonCount+"]")));
									
									lessonName = dvr.findElement(By.xpath("((//div[@id='browseview']//div[@id='wb-program-structure']/div)["+panelHeadingCount+"]//div[@class='panel-body'])["+lessonCount+"]")).getText();

									System.out.println("---- "+lessonName);
									String lessonResultHeading = "";
									try{
										dvr.findElement(By.xpath("((//div[@id='browseview']//div[@id='wb-program-structure']/div)["+panelHeadingCount+"]//div[@class='panel-body'])["+lessonCount+"]")).click();
									}catch(Exception e){
										try{
											dvr.findElement(By.xpath("((//div[@id='browseview']//div[@id='wb-program-structure']/div)["+panelHeadingCount+"]//div[@class='panel-body'])["+lessonCount+"]")).click();
										}catch(Exception f){
									
										}
									}
									testScript.threadSleep(2);
									
									try{
										testScript.waitForVisibility(dvr.findElement(By.xpath(".//div[@id='wb-results']/div[1]/h3")));											
										lessonResultHeading = dvr.findElement(By.xpath(".//div[@id='wb-results']/div[1]/h3")).getText();
									}catch(Exception r){
										try{
											testScript.waitForVisibility(dvr.findElement(By.xpath(".//div[@id='wb-results']/div[1]/h3")));											
											lessonResultHeading = dvr.findElement(By.xpath(".//div[@id='wb-results']/div[1]/h3")).getText();
										}catch(Exception rr){
											System.out.println("lessonResultHeading not found");
										}
									}
									
									for(HashMap.Entry<String, ArrayList> me : hashMap.entrySet()){
											String key = me.getKey();
											if(key.equals(level1Folderindex)){
													ArrayList al = new ArrayList();
													if(!al.contains(lessonName)){
														al.add(lessonName);
													}
											}
									}
									
									String index1 = "";
									
									if(!level2FoldersList.contains(lessonName)){
										level2FoldersList.add(lessonName);
										lessonArrayList.add(lessonName);
										index1 = String.valueOf(level2FoldersList.indexOf(lessonName));
									}else{
										index1 = String.valueOf(level2FoldersList.indexOf(lessonName));
										lessonArrayList.add(lessonName);
									}
									
									if(!lessonName.isEmpty() && !lessonResultHeading.isEmpty()){
										if(levelLevelResoure()){
											if(lessonName.contains(lessonResultHeading)){
												getResourcesLesson(level1Folderindex,index1,lessonLevel3FolderMap,lessonLevel4FolderMap,lessonLevel4FolderResourceHashMap,linkNameHashMap,lunchedUrlHashMap);
											}else{
												System.out.println("Lesson Name in Programe Structure and Heading is not matching");
											}
										}else{
											System.out.println("wb-results not found of Lesson");
										}
									}else{
											System.out.println("Lesson name is Empty");
									}	
								}catch(Exception e){
									System.out.println("Lesson not found");
								}
								}			
							}
								
							testScript.threadSleep(2);
							try{
								dvr.findElement(By.xpath("(//div[@id='browseview']//div[@id='wb-program-structure']/div)["+panelHeadingCount+"]//div[@class='panel-heading']")).click();
							}catch(Exception e){
								try{									
									dvr.findElement(By.xpath("(//div[@id='browseview']//div[@id='wb-program-structure']/div)["+panelHeadingCount+"]//div[@class='panel-heading']")).click();
								}catch(Exception f){
									System.out.println("Unit panal not clicked");
								}							
							}							
						}
					}
					
				}
				 
				level2FolderMap.putAll(lessonLevel3FolderMap);
				level2FolderMap.putAll(levelLevelResourcehashMap);
				lessonLevel4FolderMap.putAll(level2FolderResourceHashMap);
		
	//			System.out.println("Level 1 FolderList "+innerLinkArrayList);
			 
				
				
				
				
				
			
	/*		testScript.createExcelFile(hrwCourse);
			testScript.createSheet("HRW Links");
				int rowvalue = 1;
				int mainFolderCol = 0;
				int level2FolderCol =1;
				int level3FolderCol =2;
				int level4FolderCol = 3;
				int lessonlevel4Roursorce = 4;
				
				testScript.worksheet.createRow(0).createCell(0).setCellValue("TCK Links");
				try{
					for(int i = 0; i < innerLinkArrayList.size(); i++){			
							testScript.worksheet.createRow(rowvalue).createCell(mainFolderCol).setCellValue(String.valueOf(innerLinkArrayList.get(i)));
						    rowvalue++;
							for (HashMap.Entry<String, ArrayList> me : hashMap.entrySet()) {
								String key = me.getKey();
								if(String.valueOf(i).equals(key)){
									ArrayList<String> valueList = me.getValue();
									for(int j = 0 ;j<valueList.size();j++){
										String s ="";
									  	s = valueList.get(j);		
									  	testScript.worksheet.createRow(rowvalue).createCell(level2FolderCol).setCellValue(s);
										  rowvalue++;
									  	for(HashMap.Entry<String, ArrayList> me2 : level2FolderMap.entrySet()){
											  String key2 = me2.getKey();
											  String level2Key = "";
											  level2Key = String.valueOf(i)+"."+(String.valueOf(j));
											  if(key2.equals(level2Key)){																				  
												  ArrayList<String> valueList1 = me2.getValue();
												 for(int k = 0;k<valueList1.size();k++){
													 	String str = "";
													  	str = valueList1.get(k);
													  	testScript.worksheet.createRow(rowvalue).createCell(level3FolderCol).setCellValue(str);
												  		rowvalue++;
													  	for(HashMap.Entry<String, ArrayList> me3 : lessonLevel4FolderMap.entrySet()){
													  		String key3 = me3.getKey();
															  String level2Key1 = "";
															  level2Key1 = String.valueOf(i)+"."+(String.valueOf(j))+"."+(String.valueOf(k));
															  	if(key3.equals(level2Key1)){														  		
															  		ArrayList<String> valueList2 = me3.getValue();
															  		for(int l=0;l<valueList2.size();l++){
															  			String strValueL2 = "";
															  			strValueL2 = valueList2.get(l);
															  			testScript.worksheet.createRow(rowvalue).createCell(level4FolderCol).setCellValue(strValueL2);
															  			rowvalue++;
															  			for(HashMap.Entry<String, ArrayList> me4 : lessonLevel4FolderResourceHashMap.entrySet()){
															  					String lessonlevel4Key = me4.getKey();
															  					String level4Key = "";
															  					level4Key = String.valueOf(i)+"."+(String.valueOf(j))+"."+(String.valueOf(k))+"."+(String.valueOf(l));
															  					if(level4Key.equals(lessonlevel4Key)){
															  						ArrayList<String> valueList4 = me4.getValue();
															  						for(String strResource : valueList4){
																			  			testScript.worksheet.createRow(rowvalue).createCell(lessonlevel4Roursorce).setCellValue(strResource);
																			  			rowvalue++;
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
								
							}
						}
					}
					}catch(Exception e){						
						
					}
				
		
				
				
				
				
				
				
				
				
				
				
	*/		/*System.out.println("linkNameHashMap "+linkNameHashMap);
			System.out.println("lunchedUrlHashMap "+lunchedUrlHashMap);
				
				
			testScript.createExcelFile(hrwCourse);
			testScript.createSheet("Lunched Links");
				
			int i = 1;	
			for(HashMap.Entry<String, String> LinkNameMap1 : linkNameHashMap.entrySet()){
				String linkNameKey1 =	"";
			linkNameKey1 = LinkNameMap1.getKey();
			for(HashMap.Entry<String, String> urlMap2 : lunchedUrlHashMap.entrySet()){
					String urlMapKey1 = "";
					urlMapKey1 = urlMap2.getKey();
					if(linkNameKey1.equals(urlMapKey1)){
						System.out.println("Pass");
						testScript.worksheet.createRow(i).createCell(0).setCellValue(linkNameKey1);
						testScript.worksheet.getRow(i).createCell(1).setCellValue(LinkNameMap1.getValue());
						testScript.worksheet.getRow(i).createCell(2).setCellValue(urlMapKey1);
						testScript.worksheet.getRow(i).createCell(3).setCellValue(urlMap2.getValue());
						break;
					}
					
			}
			i++;	
				
		}
		testScript.writeExcel(hrwCourse);		
	*/	testScript.threadSleep(2);		
		dvr.findElement(By.xpath("//a[@id='platform-logout']")).click();
		testScript.threadSleep(2);
		
		
		testScript.createExcelFile(hrwCourse+"urlmapping");
		testScript.createSheet("HRW Redirect URL with GUID");		
	
		
		int n = 0;
		for(HashMap.Entry<String,String> lunchedUrlMap : lunchedUrlHashMap.entrySet()){
			testScript.worksheet.createRow(n).createCell(0).setCellValue(lunchedUrlMap.getKey());
			testScript.worksheet.getRow(n).createCell(1).setCellValue(lunchedUrlMap.getValue());		
			n++;
		}
		testScript.createSheet("HRW Links with GUID");		
		int nn = 0;
		for(HashMap.Entry<String,String> lunchedUrlMaplinkNameHashMap : linkNameHashMap.entrySet()){
			testScript.worksheet.createRow(nn).createCell(0).setCellValue(lunchedUrlMaplinkNameHashMap.getKey());
			testScript.worksheet.getRow(nn).createCell(1).setCellValue(lunchedUrlMaplinkNameHashMap.getValue());		
			nn++;
		}
		
		
		testScript.createSheet("MDS Links");
		
		int n1 = 0;
		for(HashMap.Entry<String,String> mdsMapDataMap : mdsMapData.entrySet()){
			testScript.worksheet.createRow(n1).createCell(0).setCellValue(mdsMapDataMap.getKey());
			testScript.worksheet.getRow(n1).createCell(1).setCellValue(mdsMapDataMap.getValue());		
			n1++;
		}		
		
		BrainHonneyTest1 brainHonneyTest1 = new BrainHonneyTest1(testScript, dvr);
		brainHonneyTest1.folderOperation(innerLinkArrayList,hashMap,level2FolderMap,lessonLevel4FolderMap,lessonLevel4FolderResourceHashMap,bHCourse,hrwCourse,linkNameHashMap,lunchedUrlHashMap,mdsMapData);
	}
		
		}else{
			System.out.println("HRW course course are not same as Brain honey Course Count");
		}
		}else{
			System.out.println("Course Name not given");
		}
	}
	
	
	
	
	public void getResourcesLesson(String level1Folderindex,String level2Index,HashMap<String,ArrayList> lessonLevel3FolderMap,HashMap<String,ArrayList> lessonLevel4FolderMap,HashMap<String,ArrayList> lessonLevel4FolderResourceHashMap,HashMap<String,String> linkNameHashMap,HashMap<String,String> lunchedUrlHashMap) throws InterruptedException{
	//	System.out.println("getResourcesLesson");
		
		
		if(levelLevelResoure()){
			testScript.threadSleep(2);
				if(levelLevelResoure()){	
		
		List numberOfLinks = dvr.findElements(By.xpath("//div[@id='wb-results']/div[1]/div"));
		
		if(numberOfLinks.size()>0){
			BaseAction baseAction = new BaseAction();	
		
		ArrayList lessonLevel3FolderList = new ArrayList();
		for(int resourcesLevelElementNumber = 1; resourcesLevelElementNumber <= numberOfLinks.size(); resourcesLevelElementNumber++){

			
			testScript.threadSleep(2);							
			
					testScript.threadSleep(2);									
					if(!levelResourcesInnerLinkVisibile(resourcesLevelElementNumber)){

						try{
							dvr.findElement(By.xpath("//div[@id='wb-results']/div[1]/div["+resourcesLevelElementNumber+"]/div[1]/a")).click();
						}catch(Exception e){
							try{
								dvr.findElement(By.xpath("//div[@id='wb-results']/div[1]/div["+resourcesLevelElementNumber+"]/div[1]/a")).click();
							}catch(Exception f){
								
							}
						}
						
						
					}
					testScript.threadSleep(2);
					List numberOfInnerLink = dvr.findElements(By.xpath("//div[@id='wb-results']/div[1]/div["+resourcesLevelElementNumber+"]//div[@class='panel-heading']"));
					if(numberOfInnerLink.size()>0){
						
					for(int innerLinkLoop  = 1; innerLinkLoop <= numberOfInnerLink.size(); innerLinkLoop++){
						ArrayList al = new ArrayList();
						String innerLinkText  = "";
						try{
							innerLinkText  = dvr.findElement(By.xpath("(//div[@id='wb-results']/div[1]/div["+resourcesLevelElementNumber+"]//div[@class='panel-heading'])["+innerLinkLoop+"]")).getText();
						
						String index = "";
	//					System.out.println(innerLinkText);
						if(!lessonLevel3FolderList.contains(innerLinkText)){
							lessonLevel3FolderList.add(innerLinkText);
							index = String.valueOf(lessonLevel3FolderList.indexOf(innerLinkText));
							if(!innerLinkText.isEmpty()){
								try{
									dvr.findElement(By.xpath("(//div[@id='wb-results']/div[1]/div["+resourcesLevelElementNumber+"]//div[@class='panel-heading'])["+innerLinkLoop+"]")).click();
								}catch(Exception e){
									try{
										dvr.findElement(By.xpath("(//div[@id='wb-results']/div[1]/div["+resourcesLevelElementNumber+"]//div[@class='panel-heading'])["+innerLinkLoop+"]")).click();
									}catch(Exception f){
										System.out.println("lesson resources not get clicked");
									}
								}
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
																String hrefLink = "";
																String linkedGUID = "";
																String url = "";
																WebElement linkElement = null;
																
																
																
																
																try{
																	linkElement = dvr.findElement(By.xpath("(((//div[@id='wb-results']/div[1]/div["+resourcesLevelElementNumber+"]//div[@class='panel-body']/div)["+innerLinkLoop+"]//div[@class='panel-body leaf-level'])["+level2FolderElement+"]//div[@class='resource'])["+link+"]/a"));
																	linkName = linkElement.getText();
																	
									//								linkName = dvr.findElement(By.xpath("(((//div[@id='wb-results']/div[1]/div["+resourcesLevelElementNumber+"]//div[@class='panel-body']/div)["+innerLinkLoop+"]//div[@class='panel-body leaf-level'])["+level2FolderElement+"]//div[@class='resource'])["+link+"]/a")).getText();
																}catch(Exception e){
																	try{
																		linkElement = dvr.findElement(By.xpath("(((//div[@id='wb-results']/div[1]/div["+resourcesLevelElementNumber+"]//div[@class='panel-body']/div)["+innerLinkLoop+"]//div[@class='panel-body leaf-level'])["+level2FolderElement+"]//div[@class='resource'])["+link+"]/a"));
																		linkName = linkElement.getText();
																//		linkName = dvr.findElement(By.xpath("(((//div[@id='wb-results']/div[1]/div["+resourcesLevelElementNumber+"]//div[@class='panel-body']/div)["+innerLinkLoop+"]//div[@class='panel-body leaf-level'])["+level2FolderElement+"]//div[@class='resource'])["+link+"]/a")).getText();
																	}catch(Exception f){
																	
																	}
																}
																linkedGUID = baseAction.getLinkedGUID(linkElement.getAttribute("href"));
																System.out.println("linkedGUID "+linkedGUID);
																url = getUrl(linkElement);
																
																linkNameHashMap.put(linkedGUID, linkName);
																lunchedUrlHashMap.put(linkedGUID, url);
																listOfLinkResource.add(linkName);
														}
								
														lessonLevel4FolderResourceHashMap.put(level1Folderindex+"."+level2Index+"."+index+"."+(level2FolderElement-1), listOfLinkResource);
													}
												}
											}
									}catch(Exception e){
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
														String hrefLink = "";
														String linkedGUID = "";
														String url = "";
														WebElement linkElement = null;
														
														
														
															try{
																linkElement = dvr.findElement(By.xpath("((//div[@id='wb-results']/div[1]/div["+resourcesLevelElementNumber+"]//div[@class='panel-body leaf-level'])["+level2FolderElement+"]//div[@class='resource'])["+link+"]/a"));
																linkName = linkElement.getText();
												//				linkName = dvr.findElement(By.xpath("((//div[@id='wb-results']/div[1]/div["+resourcesLevelElementNumber+"]//div[@class='panel-body leaf-level'])["+level2FolderElement+"]//div[@class='resource'])["+link+"]/a")).getText();
																
															}catch(Exception f){
																try{
																	linkElement = dvr.findElement(By.xpath("((//div[@id='wb-results']/div[1]/div["+resourcesLevelElementNumber+"]//div[@class='panel-body leaf-level'])["+level2FolderElement+"]//div[@class='resource'])["+link+"]/a"));
																	linkName = linkElement.getText();
														//			linkName = dvr.findElement(By.xpath("((//div[@id='wb-results']/div[1]/div["+resourcesLevelElementNumber+"]//div[@class='panel-body leaf-level'])["+level2FolderElement+"]//div[@class='resource'])["+link+"]/a")).getText();
																	
																}catch(Exception g){
																	
																}
															}
															linkedGUID = baseAction.getLinkedGUID(linkElement.getAttribute("href"));
															System.out.println("linkedGUID "+linkedGUID);
															url = getUrl(linkElement);															
															linkNameHashMap.put(linkedGUID, linkName);
															lunchedUrlHashMap.put(linkedGUID, url);
															listOfLinkResource.add(linkName);
													}					
													lessonLevel4FolderResourceHashMap.put(level1Folderindex+"."+level2Index+"."+index+"."+(level2FolderElement-1), listOfLinkResource);
													}
												}
											}
										}catch(Exception f){
											System.out.println("Xpath not found for level 4");
										}
									}
									lessonLevel4FolderMap.put(level1Folderindex+"."+level2Index+"."+index, al);
								}
						}else{
							index = String.valueOf(lessonLevel3FolderList.indexOf(innerLinkText));
							testScript.threadSleep(2);
							if(!innerLinkText.isEmpty()){
								
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
																String hrefLink = "";
																String linkedGUID = "";
																String url = "";
																WebElement linkElement = null;
																
																
																try{
																	linkElement = dvr.findElement(By.xpath("(((//div[@id='wb-results']/div[1]/div["+resourcesLevelElementNumber+"]//div[@class='panel-body']/div)["+innerLinkLoop+"]//div[@class='panel-body leaf-level'])["+level2FolderElement+"]//div[@class='resource'])["+link+"]/a"));
																	linkName = linkElement.getText();
														//			linkName = dvr.findElement(By.xpath("(((//div[@id='wb-results']/div[1]/div["+resourcesLevelElementNumber+"]//div[@class='panel-body']/div)["+innerLinkLoop+"]//div[@class='panel-body leaf-level'])["+level2FolderElement+"]//div[@class='resource'])["+link+"]/a")).getText();
																	
																}catch(Exception e){
																	try{
																		linkElement = dvr.findElement(By.xpath("(((//div[@id='wb-results']/div[1]/div["+resourcesLevelElementNumber+"]//div[@class='panel-body']/div)["+innerLinkLoop+"]//div[@class='panel-body leaf-level'])["+level2FolderElement+"]//div[@class='resource'])["+link+"]/a"));
																		linkName = linkElement.getText();
															//			linkName = dvr.findElement(By.xpath("(((//div[@id='wb-results']/div[1]/div["+resourcesLevelElementNumber+"]//div[@class='panel-body']/div)["+innerLinkLoop+"]//div[@class='panel-body leaf-level'])["+level2FolderElement+"]//div[@class='resource'])["+link+"]/a")).getText();
																		
																	}catch(Exception f){
																		
																	}
																}		
																linkedGUID = baseAction.getLinkedGUID(linkElement.getAttribute("href"));
																System.out.println("linkedGUID "+linkedGUID);
																url = getUrl(linkElement);																
																linkNameHashMap.put(linkedGUID, linkName);
																lunchedUrlHashMap.put(linkedGUID, url);
																
																listOfLinkResource.add(linkName);
														}
													
											boolean bodyFolder = false;
											for (HashMap.Entry<String, ArrayList> entry : lessonLevel4FolderMap.entrySet()) {												
												String key = entry.getKey();
												String temp = "";
												temp = level1Folderindex+"."+level2Index+"."+index;
												if(key.equals(temp)){													 
													ArrayList value = entry.getValue();
													if(!value.contains(level2FolderName)){
														value.add(level2FolderName);	
													}else{
														bodyFolder = true;
													}
												}
											}
											
											if(bodyFolder){
												int folderIndex = 0;
												for (HashMap.Entry<String, ArrayList> entry : lessonLevel4FolderMap.entrySet()) {												
													String key = entry.getKey();
													String temp = "";
													temp = level1Folderindex+"."+level2Index+"."+index;	
																											
													if(key.equals(temp)){
														
														ArrayList value = entry.getValue();
														for(int i = 0 ;i<value.size();i++){
																											
															if(value.get(i).equals(level2FolderName)){
														
																folderIndex = i;
															}
														}																			
													}		
												}
												for (HashMap.Entry<String, ArrayList> entry : lessonLevel4FolderResourceHashMap.entrySet()) {												
													String key = entry.getKey();
													String temp = "";
													temp = level1Folderindex+"."+level2Index+"."+index+"."+folderIndex;
													if(key.equals(temp)){	
														ArrayList value = entry.getValue();
														value.addAll(listOfLinkResource);	
													}		
												}
											}else{
												int folderIndex = 0;
												for (HashMap.Entry<String, ArrayList> entry : lessonLevel4FolderMap.entrySet()) {												
													String key = entry.getKey();
													String temp = "";
													temp = level1Folderindex+"."+level2Index+"."+index;	
													if(key.equals(temp)){
														ArrayList value = entry.getValue();
														for(int i = 0 ;i<value.size();i++){
															if(value.get(i).equals(level2FolderName)){
																folderIndex = i;
															}
														}																			
													}		
												}												
													lessonLevel4FolderResourceHashMap.put(level1Folderindex+"."+level2Index+"."+index+"."+(folderIndex), listOfLinkResource);
													}
												}
										}	}
									}catch(Exception e){
										try{
											testScript.waitForVisibility(dvr.findElement(By.xpath("//div[@id='wb-results']/div[1]/div["+resourcesLevelElementNumber+"]//div[@class='panel-body leaf-level']//h4")));
											listOfLevel2Folders = dvr.findElements(By.xpath("//div[@id='wb-results']/div[1]/div["+resourcesLevelElementNumber+"]//div[@class='panel-body leaf-level']//h4"));
											
											if(listOfLevel2Folders.size()>0){
											
											for(int level2FolderElement = 1; level2FolderElement<=listOfLevel2Folders.size();level2FolderElement++){
												String level2FolderName = "";
												level2FolderName = dvr.findElement(By.xpath("(//div[@id='wb-results']/div[1]/div["+resourcesLevelElementNumber+"]//div[@class='panel-body leaf-level']//h4)["+level2FolderElement+"]")).getText();
												List countOfResources  = getListOfReasources(resourcesLevelElementNumber, level2FolderElement);
												
												if(countOfResources.size()>0){
												ArrayList listOfLinkResource = new ArrayList();
							
												for(int link = 1; link <= countOfResources.size(); link++){
													
													String linkName = "";
													String hrefLink = "";
													String linkedGUID = "";
													String url = "";
													WebElement linkElement = null;													
													
													try{
														linkElement = dvr.findElement(By.xpath("((//div[@id='wb-results']/div[1]/div["+resourcesLevelElementNumber+"]//div[@class='panel-body leaf-level'])["+level2FolderElement+"]//div[@class='resource'])["+link+"]/a"));
														linkName = linkElement.getText();
									//					linkName = dvr.findElement(By.xpath("((//div[@id='wb-results']/div[1]/div["+resourcesLevelElementNumber+"]//div[@class='panel-body leaf-level'])["+level2FolderElement+"]//div[@class='resource'])["+link+"]/a")).getText();
														
													}catch(Exception f){
														try{
															
															linkElement = dvr.findElement(By.xpath("((//div[@id='wb-results']/div[1]/div["+resourcesLevelElementNumber+"]//div[@class='panel-body leaf-level'])["+level2FolderElement+"]//div[@class='resource'])["+link+"]/a"));
															linkName = linkElement.getText();
																	
															
												//			linkName = dvr.findElement(By.xpath("((//div[@id='wb-results']/div[1]/div["+resourcesLevelElementNumber+"]//div[@class='panel-body leaf-level'])["+level2FolderElement+"]//div[@class='resource'])["+link+"]/a")).getText();
															
														}catch(Exception g){
															
														}
													}
													
													linkedGUID = baseAction.getLinkedGUID(linkElement.getAttribute("href"));
													System.out.println("linkedGUID "+linkedGUID);
													url = getUrl(linkElement);
													
													linkNameHashMap.put(linkedGUID, linkName);
													lunchedUrlHashMap.put(linkedGUID, url);
													listOfLinkResource.add(linkName);
												}
												boolean bodyFolder = false;
												for (HashMap.Entry<String, ArrayList> entry : lessonLevel4FolderMap.entrySet()) {												
													String key = entry.getKey();
													String temp = "";											
													temp = level1Folderindex+"."+level2Index+"."+index;													
													if(key.equals(temp)){													
														ArrayList value = entry.getValue();
														if(!value.contains(level2FolderName)){
															value.add(level2FolderName);
														}else{
															bodyFolder = true;
														}
													}													
												}
												
												if(bodyFolder){
													int folderIndex = 0;
													for (HashMap.Entry<String, ArrayList> entry : lessonLevel4FolderMap.entrySet()) {												
														String key = entry.getKey();
														String temp = "";
														temp = level1Folderindex+"."+level2Index+"."+index;																													
														if(key.equals(temp)){														
															ArrayList value = entry.getValue();
															for(int i = 0 ;i<value.size();i++){																											
																if(value.get(i).equals(level2FolderName)){																	
																	folderIndex = i;
																}
															}																			
														}		
													}
													for (HashMap.Entry<String, ArrayList> entry : lessonLevel4FolderResourceHashMap.entrySet()) {												
														String key = entry.getKey();
														String temp = "";
														temp = level1Folderindex+"."+level2Index+"."+index+"."+folderIndex;
														if(key.equals(temp)){	
															ArrayList value = entry.getValue();
															value.addAll(listOfLinkResource);														
														}		
													}
												}else{
													
													int folderIndex = 0;
													for (HashMap.Entry<String, ArrayList> entry : lessonLevel4FolderMap.entrySet()) {												
														String key = entry.getKey();
														String temp = "";
														temp = level1Folderindex+"."+level2Index+"."+index;																														
														if(key.equals(temp)){													
															ArrayList value = entry.getValue();
															for(int i = 0 ;i<value.size();i++){																													
																if(value.get(i).equals(level2FolderName)){																
																	folderIndex = i;
																}
															}																			
														}		
													}
													
														lessonLevel4FolderResourceHashMap.put(level1Folderindex+"."+level2Index+"."+index+"."+(folderIndex), listOfLinkResource);
												}
												}
											}}
										}catch(Exception f){
										
										}
									}
									
								}							
						}	
						}catch(Exception e){
							
						}
					
					}
					lessonLevel3FolderMap.put(level1Folderindex+"."+level2Index, lessonLevel3FolderList);
				}	

					
		}
			}}
		}	
	}  
	
	public void getResources(String level1Folderindex,HashMap<String,ArrayList> level2FolderMap,HashMap<String,ArrayList> hashMap,ArrayList level2FoldersList,HashMap<String,ArrayList> level2FolderResourceHashMap,HashMap<String,String> linkNameHashMap,HashMap<String,String> lunchedUrlHashMap) throws InterruptedException{
//	System.out.println("getResources");
		
		
		if(levelLevelResoure()){
			testScript.threadSleep(2);
				if(levelLevelResoure()){	
		
		List numberOfLinks = dvr.findElements(By.xpath("//div[@id='wb-results']/div[1]/div"));
		
		
		if(numberOfLinks.size()>0){
		ArrayList level3FoldersList = new ArrayList();
		BaseAction baseAction = new BaseAction();
		for(int resourcesLevelElementNumber = 1; resourcesLevelElementNumber <= numberOfLinks.size(); resourcesLevelElementNumber++){
			
			
			testScript.threadSleep(2);							
			
					testScript.threadSleep(2);									
					if(!levelResourcesInnerLinkVisibile(resourcesLevelElementNumber)){

						try{
							dvr.findElement(By.xpath("//div[@id='wb-results']/div[1]/div["+resourcesLevelElementNumber+"]/div[1]/a")).click();
						}catch(Exception e){
							try{
								dvr.findElement(By.xpath("//div[@id='wb-results']/div[1]/div["+resourcesLevelElementNumber+"]/div[1]/a")).click();
							}catch(Exception f){
								System.out.println("Level2 element not Clicked ");
							}
						}
						
						
					}
					testScript.threadSleep(2);
					List numberOfInnerLink = dvr.findElements(By.xpath("//div[@id='wb-results']/div[1]/div["+resourcesLevelElementNumber+"]//div[@class='panel-heading']"));
					if(numberOfInnerLink.size()>0){
						
					
					for(int innerLinkLoop  = 1; innerLinkLoop <= numberOfInnerLink.size(); innerLinkLoop++){
						ArrayList al = new ArrayList();
						String innerLinkText  = "";
						
						try{
							innerLinkText  = dvr.findElement(By.xpath("(//div[@id='wb-results']/div[1]/div["+resourcesLevelElementNumber+"]//div[@class='panel-heading'])["+innerLinkLoop+"]")).getText();
						
						String index = "";
	//					System.out.println(innerLinkText);
						if(!level2FoldersList.contains(innerLinkText)){
							level2FoldersList.add(innerLinkText);
							index = String.valueOf(level2FoldersList.indexOf(innerLinkText));
							
							if(!innerLinkText.isEmpty()){
								//			String level1FolderName = "";
											
											try{
												dvr.findElement(By.xpath("(//div[@id='wb-results']/div[1]/div["+resourcesLevelElementNumber+"]//div[@class='panel-heading'])["+innerLinkLoop+"]")).click();
												
											}catch(Exception e){
												try{
													dvr.findElement(By.xpath("(//div[@id='wb-results']/div[1]/div["+resourcesLevelElementNumber+"]//div[@class='panel-heading'])["+innerLinkLoop+"]")).click();
													
												}catch(Exception f){
														System.out.println("Panel heading not clicked");
												}
											}
								
								
											Thread.sleep(1000);
											List listOfLevel2Folders = null;
												try{
														testScript.waitForVisibility(dvr.findElement(By.xpath("(//div[@id='wb-results']/div[1]/div["+resourcesLevelElementNumber+"]//div[@class='panel-body']/div)["+innerLinkLoop+"]//div[@class='panel-body leaf-level']//h4")));
														listOfLevel2Folders = dvr.findElements(By.xpath("(//div[@id='wb-results']/div[1]/div["+resourcesLevelElementNumber+"]//div[@class='panel-body']/div)["+innerLinkLoop+"]//div[@class='panel-body leaf-level']//h4"));
													
													for(int level2FolderElement = 1; level2FolderElement<=listOfLevel2Folders.size();level2FolderElement++){
														String level2FolderName = "";
														level2FolderName = dvr.findElement(By.xpath("((//div[@id='wb-results']/div[1]/div["+resourcesLevelElementNumber+"]//div[@class='panel-body']/div)["+innerLinkLoop+"]//div[@class='panel-body leaf-level']//h4)["+level2FolderElement+"]")).getText();
														 //System.out.println(level2FolderName);
														al.add(level2FolderName);														
														List countOfResources  = getListOfReasources(resourcesLevelElementNumber, innerLinkLoop, level2FolderElement);
														//			//System.out.println("countOfResources "+countOfResources.size());
																	if(countOfResources.size()>0){
																		ArrayList listOfLinkResource = new ArrayList();
																		for(int link = 1; link <= countOfResources.size(); link++){
																			
																				String linkName = "";
																				String hrefLink = "";
																				String linkedGUID = "";
																				String url = "";
																				WebElement linkElement = null;
																				try{
																					linkElement = dvr.findElement(By.xpath("(((//div[@id='wb-results']/div[1]/div["+resourcesLevelElementNumber+"]//div[@class='panel-body']/div)["+innerLinkLoop+"]//div[@class='panel-body leaf-level'])["+level2FolderElement+"]//div[@class='resource'])["+link+"]/a"));
																					linkName = linkElement.getText();
																	//				linkName = dvr.findElement(By.xpath("(((//div[@id='wb-results']/div[1]/div["+resourcesLevelElementNumber+"]//div[@class='panel-body']/div)["+innerLinkLoop+"]//div[@class='panel-body leaf-level'])["+level2FolderElement+"]//div[@class='resource'])["+link+"]/a")).getText();
																					}catch(Exception e){
																					try{
																						linkElement = dvr.findElement(By.xpath("(((//div[@id='wb-results']/div[1]/div["+resourcesLevelElementNumber+"]//div[@class='panel-body']/div)["+innerLinkLoop+"]//div[@class='panel-body leaf-level'])["+level2FolderElement+"]//div[@class='resource'])["+link+"]/a"));
																						linkName = linkElement.getText();
																	//					linkName = dvr.findElement(By.xpath("(((//div[@id='wb-results']/div[1]/div["+resourcesLevelElementNumber+"]//div[@class='panel-body']/div)["+innerLinkLoop+"]//div[@class='panel-body leaf-level'])["+level2FolderElement+"]//div[@class='resource'])["+link+"]/a")).getText();
																					}catch(Exception f){
																						
																					}
																				}
																				linkedGUID = baseAction.getLinkedGUID(linkElement.getAttribute("href"));
																				System.out.println("linkedGUID "+linkedGUID);
																				url = getUrl(linkElement);
																				linkNameHashMap.put(linkedGUID, linkName);
																				lunchedUrlHashMap.put(linkedGUID, url);
																				listOfLinkResource.add(linkName);
																		}
													//					//System.out.println("index "+levelLevelResourcehashMap);
																		level2FolderResourceHashMap.put(level1Folderindex+"."+index+"."+(level2FolderElement-1), listOfLinkResource);
															
																	}
													}
												}catch(Exception e){
													try{
														testScript.waitForVisibility(dvr.findElement(By.xpath("//div[@id='wb-results']/div[1]/div["+resourcesLevelElementNumber+"]//div[@class='panel-body leaf-level']//h4")));
														listOfLevel2Folders = dvr.findElements(By.xpath("//div[@id='wb-results']/div[1]/div["+resourcesLevelElementNumber+"]//div[@class='panel-body leaf-level']//h4"));
														for(int level2FolderElement = 1; level2FolderElement<=listOfLevel2Folders.size();level2FolderElement++){
															String level2FolderName = "";
															level2FolderName = dvr.findElement(By.xpath("(//div[@id='wb-results']/div[1]/div["+resourcesLevelElementNumber+"]//div[@class='panel-body leaf-level']//h4)["+level2FolderElement+"]")).getText();
															 //System.out.println(level2FolderName);															
																al.add(level2FolderName);
																
																List countOfResources  = getListOfReasources(resourcesLevelElementNumber, level2FolderElement);
																if(countOfResources.size()>0){
																ArrayList listOfLinkResource = new ArrayList();
												//				//System.out.println("countOfResources "+countOfResources.size());
																for(int link = 1; link <= countOfResources.size(); link++){
																	
																	String linkName = "";
																	String hrefLink = "";
																	String linkedGUID = "";
																	String url = "";
																	WebElement linkElement = null;
																	
																	try{
																		linkElement = dvr.findElement(By.xpath("((//div[@id='wb-results']/div[1]/div["+resourcesLevelElementNumber+"]//div[@class='panel-body leaf-level'])["+level2FolderElement+"]//div[@class='resource'])["+link+"]/a"));
																		linkName = linkElement.getText();
																		//			linkName = dvr.findElement(By.xpath("((//div[@id='wb-results']/div[1]/div["+resourcesLevelElementNumber+"]//div[@class='panel-body leaf-level'])["+level2FolderElement+"]//div[@class='resource'])["+link+"]/a")).getText();
																	}catch(Exception f){
																		try{
																			
																			linkElement = dvr.findElement(By.xpath("((//div[@id='wb-results']/div[1]/div["+resourcesLevelElementNumber+"]//div[@class='panel-body leaf-level'])["+level2FolderElement+"]//div[@class='resource'])["+link+"]/a"));
																			linkName = linkElement.getText();
																//			linkName = dvr.findElement(By.xpath("((//div[@id='wb-results']/div[1]/div["+resourcesLevelElementNumber+"]//div[@class='panel-body leaf-level'])["+level2FolderElement+"]//div[@class='resource'])["+link+"]/a")).getText();
																		}catch(Exception g){
																			
																		}
																	}
																	
																	linkedGUID = baseAction.getLinkedGUID(linkElement.getAttribute("href"));
																	System.out.println("linkedGUID "+linkedGUID);
																	url = getUrl(linkElement);
																	
																	linkNameHashMap.put(linkedGUID, linkName);
																	lunchedUrlHashMap.put(linkedGUID, url);
																	
											//						//System.out.println("linkName "+linkName);
																	listOfLinkResource.add(linkName);
																}
								//								//System.out.println("index "+levelLevelResourcehashMap);
																level2FolderResourceHashMap.put(level1Folderindex+"."+index+"."+(level2FolderElement-1), listOfLinkResource);
																}	
														}
													}catch(Exception f){
														// //System.out.println("Xpath not found for level 2");
													}
												}
//												// //System.out.println("Level 3 list "+al);
												level2FolderMap.put(level1Folderindex+"."+index, al);
											}
//							//System.out.println("level2FolderResourceHashMap "+level2FolderResourceHashMap);
						}else{
							 //System.out.println("match so else");
							index = String.valueOf(level2FoldersList.indexOf(innerLinkText));
							
							
								//			String level1FolderName = "";
											dvr.findElement(By.xpath("(//div[@id='wb-results']/div[1]/div["+resourcesLevelElementNumber+"]//div[@class='panel-heading'])["+innerLinkLoop+"]")).click();
											Thread.sleep(1000);
											List listOfLevel2Folders = null;
												try{
														testScript.waitForVisibility(dvr.findElement(By.xpath("(//div[@id='wb-results']/div[1]/div["+resourcesLevelElementNumber+"]//div[@class='panel-body']/div)["+innerLinkLoop+"]//div[@class='panel-body leaf-level']//h4")));
														listOfLevel2Folders = dvr.findElements(By.xpath("(//div[@id='wb-results']/div[1]/div["+resourcesLevelElementNumber+"]//div[@class='panel-body']/div)["+innerLinkLoop+"]//div[@class='panel-body leaf-level']//h4"));
													
													for(int level2FolderElement = 1; level2FolderElement<=listOfLevel2Folders.size();level2FolderElement++){
														String level2FolderName = "";
														level2FolderName = dvr.findElement(By.xpath("((//div[@id='wb-results']/div[1]/div["+resourcesLevelElementNumber+"]//div[@class='panel-body']/div)["+innerLinkLoop+"]//div[@class='panel-body leaf-level']//h4)["+level2FolderElement+"]")).getText();
														 //System.out.println(level2FolderName);
											
														List countOfResources  = getListOfReasources(resourcesLevelElementNumber, innerLinkLoop, level2FolderElement);
														//			//System.out.println("countOfResources "+countOfResources.size());
																
														if(countOfResources.size()>0){
																	ArrayList listOfLinkResource = new ArrayList();
																	for(int link = 1; link <= countOfResources.size(); link++){
																		
																			String linkName = "";
																			String hrefLink = "";
																			String linkedGUID = "";
																			String url = "";
																			WebElement linkElement = null;
																			
																			
																			try{
																				linkElement = dvr.findElement(By.xpath("(((//div[@id='wb-results']/div[1]/div["+resourcesLevelElementNumber+"]//div[@class='panel-body']/div)["+innerLinkLoop+"]//div[@class='panel-body leaf-level'])["+level2FolderElement+"]//div[@class='resource'])["+link+"]/a"));
																				linkName = linkElement.getText();
														//						linkName = dvr.findElement(By.xpath("(((//div[@id='wb-results']/div[1]/div["+resourcesLevelElementNumber+"]//div[@class='panel-body']/div)["+innerLinkLoop+"]//div[@class='panel-body leaf-level'])["+level2FolderElement+"]//div[@class='resource'])["+link+"]/a")).getText();
																			}catch(Exception e){
																				try{
																					linkElement = dvr.findElement(By.xpath("(((//div[@id='wb-results']/div[1]/div["+resourcesLevelElementNumber+"]//div[@class='panel-body']/div)["+innerLinkLoop+"]//div[@class='panel-body leaf-level'])["+level2FolderElement+"]//div[@class='resource'])["+link+"]/a"));
																					linkName = linkElement.getText();
																				//							linkName = dvr.findElement(By.xpath("(((//div[@id='wb-results']/div[1]/div["+resourcesLevelElementNumber+"]//div[@class='panel-body']/div)["+innerLinkLoop+"]//div[@class='panel-body leaf-level'])["+level2FolderElement+"]//div[@class='resource'])["+link+"]/a")).getText();
																				}catch(Exception f){
																					
																				}
																			}
																			
																			linkedGUID = baseAction.getLinkedGUID(linkElement.getAttribute("href"));
																			System.out.println("linkedGUID "+linkedGUID);
																			url = getUrl(linkElement);																			
																			linkNameHashMap.put(linkedGUID, linkName);
																			lunchedUrlHashMap.put(linkedGUID, url);
																			
													//						//System.out.println("linkName "+linkName);
																			listOfLinkResource.add(linkName);
																	}
												//					//System.out.println("index "+levelLevelResourcehashMap);
																	
														
														
														boolean bodyFolder = false;
														
														for (HashMap.Entry<String, ArrayList> entry : level2FolderMap.entrySet()) {												
															String key = entry.getKey();
															String temp = "";
															 //System.out.println("index "+index);
															int hashMapSize = hashMap.size();
															 //System.out.println("hashMapSize "+hashMapSize);
															 //System.out.println("level1Folderindex "+level1Folderindex);
															temp = String.valueOf(hashMapSize)+"."+index;
															 //System.out.println("key "+key+" index "+index);
															if(key.equals(temp)){
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
															for (HashMap.Entry<String, ArrayList> entry : level2FolderMap.entrySet()) {												
																String key = entry.getKey();
																//System.out.println("key "+key);
																//System.out.println("level1Folderindex "+level1Folderindex+" index "+index);																
																String temp = level1Folderindex+"."+index;
																//System.out.println("temp "+temp);																
																if(key.equals(temp)){
																	//System.out.println("key==index");
																	ArrayList value = entry.getValue();
																	for(int i = 0 ;i<value.size();i++){
																		//System.out.println("i "+value.get(i)+" level2FolderName "+level2FolderName);
																		
																		if(value.get(i).equals(level2FolderName)){
																			//System.out.println("i- "+i);
																			folderIndex = i;
																		}
																	}																			
																}		
															}																	
															for (HashMap.Entry<String, ArrayList> entry : level2FolderResourceHashMap.entrySet()) {												
																String key = entry.getKey();
																//System.out.println("level1Folderindex "+level1Folderindex+" index "+index+" folderIndex "+folderIndex);
																String temp = level1Folderindex+"."+index+"."+folderIndex;
																//System.out.println("key "+key+" temp "+temp);
																if(key.equals(temp)){	
																	//System.out.println("Key == index");
																	ArrayList value = entry.getValue();
																	//System.out.println("value "+value);
																	value.addAll(listOfLinkResource);	
																	//System.out.println("value "+ value);
																}		
															}
														}else{
															int folderIndex = 0;
															for (HashMap.Entry<String, ArrayList> entry : level2FolderMap.entrySet()) {												
																String key = entry.getKey();
																//System.out.println("key "+key);
																//System.out.println("level1Folderindex "+level1Folderindex+" index "+index);																
																String temp = level1Folderindex+"."+index;
																//System.out.println("temp "+temp);																
																if(key.equals(temp)){
																	//System.out.println("key==index");
																	ArrayList value = entry.getValue();
																	for(int i = 0 ;i<value.size();i++){
																		//System.out.println("i "+value.get(i)+" level2FolderName "+level2FolderName);
																		
																		if(value.get(i).equals(level2FolderName)){
																			//System.out.println("i- "+i);
																			folderIndex = i;
																		}
																	}																			
																}		
															}
															level2FolderResourceHashMap.put(level1Folderindex+"."+index+"."+folderIndex, listOfLinkResource);															
														}
														}
													}
												}catch(Exception e){
													// //System.out.println("catched");
													try{
														testScript.waitForVisibility(dvr.findElement(By.xpath("//div[@id='wb-results']/div[1]/div["+resourcesLevelElementNumber+"]//div[@class='panel-body leaf-level']//h4")));
														listOfLevel2Folders = dvr.findElements(By.xpath("//div[@id='wb-results']/div[1]/div["+resourcesLevelElementNumber+"]//div[@class='panel-body leaf-level']//h4"));
														for(int level2FolderElement = 1; level2FolderElement<=listOfLevel2Folders.size();level2FolderElement++){
															String level2FolderName = "";
															level2FolderName = dvr.findElement(By.xpath("(//div[@id='wb-results']/div[1]/div["+resourcesLevelElementNumber+"]//div[@class='panel-body leaf-level']//h4)["+level2FolderElement+"]")).getText();
															 //System.out.println(level2FolderName);															
									
															List countOfResources  = getListOfReasources(resourcesLevelElementNumber, level2FolderElement);
															
															if(countOfResources.size()>0){
																
															ArrayList listOfLinkResource = new ArrayList();
											//				//System.out.println("countOfResources "+countOfResources.size());
															for(int link = 1; link <= countOfResources.size(); link++){
																
																String linkName = "";
																String hrefLink = "";
																String linkedGUID = "";
																String url = "";
																WebElement linkElement = null;
																
																
																
																
																try{
																	
																	linkElement = dvr.findElement(By.xpath("((//div[@id='wb-results']/div[1]/div["+resourcesLevelElementNumber+"]//div[@class='panel-body leaf-level'])["+level2FolderElement+"]//div[@class='resource'])["+link+"]/a"));
																	linkName = linkElement.getText();
														//			linkName = dvr.findElement(By.xpath("((//div[@id='wb-results']/div[1]/div["+resourcesLevelElementNumber+"]//div[@class='panel-body leaf-level'])["+level2FolderElement+"]//div[@class='resource'])["+link+"]/a")).getText();
																}catch(Exception f){
																	try{
																		linkElement = dvr.findElement(By.xpath("((//div[@id='wb-results']/div[1]/div["+resourcesLevelElementNumber+"]//div[@class='panel-body leaf-level'])["+level2FolderElement+"]//div[@class='resource'])["+link+"]/a"));
																		linkName = linkElement.getText();
															//			linkName = dvr.findElement(By.xpath("((//div[@id='wb-results']/div[1]/div["+resourcesLevelElementNumber+"]//div[@class='panel-body leaf-level'])["+level2FolderElement+"]//div[@class='resource'])["+link+"]/a")).getText();
																	}catch(Exception g){
																		
																	}
																}
																linkedGUID = baseAction.getLinkedGUID(linkElement.getAttribute("href"));
																System.out.println("linkedGUID "+linkedGUID);
																url = getUrl(linkElement);
																
																linkNameHashMap.put(linkedGUID, linkName);
																lunchedUrlHashMap.put(linkedGUID, url);
																
																
																
																listOfLinkResource.add(linkName);
															}
							//								//System.out.println("index "+levelLevelResourcehashMap);
															
															boolean bodyFolder = false;
															
															for (HashMap.Entry<String, ArrayList> entry : level2FolderMap.entrySet()) {												
																String key = entry.getKey();
																String temp = "";
																 //System.out.println("index "+index);
																int hashMapSize = hashMap.size();
																 //System.out.println("hashMapSize "+hashMapSize);
																 //System.out.println("level1Folderindex "+level1Folderindex);
																temp = String.valueOf(hashMapSize)+"."+index;
																 //System.out.println("key "+key+" index "+index);
																if(key.equals(temp)){
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
																for (HashMap.Entry<String, ArrayList> entry : level2FolderMap.entrySet()) {												
																	String key = entry.getKey();
																	//System.out.println("key "+key);
																	//System.out.println("level1Folderindex "+level1Folderindex+" index "+index);																
																	String temp = level1Folderindex+"."+index;
																	//System.out.println("temp "+temp);																
																	if(key.equals(temp)){
																		//System.out.println("key==index");
																		ArrayList value = entry.getValue();
																		for(int i = 0 ;i<value.size();i++){
																			//System.out.println("i "+value.get(i)+" level2FolderName "+level2FolderName);
																			
																			if(value.get(i).equals(level2FolderName)){
																				//System.out.println("i- "+i);
																				folderIndex = i;
																			}
																		}																			
																	}		
																}																	
																for (HashMap.Entry<String, ArrayList> entry : level2FolderResourceHashMap.entrySet()) {												
																	String key = entry.getKey();
																	//System.out.println("level1Folderindex "+level1Folderindex+" index "+index+" folderIndex "+folderIndex);
																	String temp = level1Folderindex+"."+index+"."+folderIndex;
																	//System.out.println("key "+key+" temp "+temp);
																	if(key.equals(temp)){	
																		//System.out.println("Key == index");
																		ArrayList value = entry.getValue();
																		//System.out.println("value "+value);
																		value.addAll(listOfLinkResource);	
																		//System.out.println("value "+ value);
																	}		
																}
															}else{
																
																int folderIndex = 0;
																for (HashMap.Entry<String, ArrayList> entry : level2FolderMap.entrySet()) {												
																	String key = entry.getKey();
																	//System.out.println("key "+key);
																	//System.out.println("level1Folderindex "+level1Folderindex+" index "+index);																
																	String temp = level1Folderindex+"."+index;
																	//System.out.println("temp "+temp);																
																	if(key.equals(temp)){
																		//System.out.println("key==index");
																		ArrayList value = entry.getValue();
																		for(int i = 0 ;i<value.size();i++){
																			//System.out.println("i "+value.get(i)+" level2FolderName "+level2FolderName);
																			
																			if(value.get(i).equals(level2FolderName)){
																				//System.out.println("i- "+i);
																				folderIndex = i;
																			}
																		}																			
																	}		
																}
																level2FolderResourceHashMap.put(level1Folderindex+"."+index+"."+(folderIndex), listOfLinkResource);
																
															}
															
															
															}	
															
														}
													}catch(Exception f){
														// //System.out.println("Xpath not found for level 2");
													}
												}
//												// //System.out.println("Level 3 list "+al);
//												level2FolderMap.put(level1Folderindex+"."+index, al);
											
//						//System.out.println("level2FolderResourceHashMap "+level2FolderResourceHashMap);	
						}
						}catch(Exception e){
							System.out.println("Level 2 Resourse not found");
						}
						
					}	
					}	
					}
				}else{
					System.out.println("Resources not found");
				}	
	//			System.out.println(level1Folderindex+"level 2 list- "+level2FoldersList);
		
			hashMap.put(level1Folderindex, level2FoldersList);
//			System.out.println("hashMap "+hashMap);
			}
		}
			
		
		
	}
	
	
	public boolean panelHeadingExist(){
		
		List panelHeadingList = null;
		try{
				panelHeadingList = dvr.findElements(By.xpath("//div[@id='browseview']//div[@id='wb-program-structure']/div"));
		}catch(NoSuchElementException e){
			
		}catch (NullPointerException e) {
			// TODO: handle exception
		}catch(Exception e){
			
		}
		
		if(panelHeadingList == null){
			return false;
		}else{
			return true;
		}	
	}
	
	
	private String typeOfResourceLevelIsLesson(int elementNumber){
		boolean typeOfResourceLevelIsLessonIsLesson = false;
		boolean typeOfResourceLevelIsLessonisSegment = false;
		String typeOfResourceLevelIsLessonIsLessonStr = "";
		String typeOfResourceLevelIsLessonisSegmentIsStr = "";
		
		try{
			dvr.manage().timeouts().implicitlyWait(2,TimeUnit.SECONDS) ;
			dvr.findElement(By.xpath("//div[@id='wb-results']/div[1]/div["+elementNumber+"]/div[2][contains(@id,'lesson')]"));
			typeOfResourceLevelIsLessonIsLesson = true;
		}catch(Exception e){
			
		}
		if(!typeOfResourceLevelIsLessonIsLesson){
		
			try{
				dvr.manage().timeouts().implicitlyWait(2,TimeUnit.SECONDS) ;
				dvr.findElement(By.xpath("//div[@id='wb-results']/div[1]/div["+elementNumber+"]/div[2][contains(@id,'segment')]"));
				typeOfResourceLevelIsLessonisSegment = true;
			}catch(Exception e){
				
			}		
		}
		dvr.manage().timeouts().implicitlyWait(60,TimeUnit.SECONDS) ;
		if(typeOfResourceLevelIsLessonIsLesson){
			return "l";
		}		
		if(typeOfResourceLevelIsLessonisSegment){
			return "s";
		}else{
			return "n";
		}
	}
	
	
	
	
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
	
	
	
	private boolean levelLevelResouresMenu(){		
		boolean levelLeveResourcesMenu = false;		
		try{
			dvr.manage().timeouts().implicitlyWait(2,TimeUnit.SECONDS) ;
			dvr.findElement(By.xpath("//button[@id='browseGradeResources']")).click();
			levelLeveResourcesMenu = true;
		}catch(Exception e){
			
		}
		dvr.manage().timeouts().implicitlyWait(60,TimeUnit.SECONDS) ;
		return levelLeveResourcesMenu;		
	}
	
	
	private boolean levelLevelResoure(){		
		boolean levelResources = false;
		try{
			dvr.manage().timeouts().implicitlyWait(2,TimeUnit.SECONDS) ;
			testScript.waitForVisibility(dvr.findElement(By.xpath("//div[@id='wb-results']/div[1]")));
			levelResources = true;
		}catch(Exception e){
			try{
				dvr.manage().timeouts().implicitlyWait(2,TimeUnit.SECONDS) ;
				testScript.waitForVisibility(dvr.findElement(By.xpath("//div[@id='wb-results']/div[1]")));
				levelResources = true;
			}catch(Exception f){
				
			}
		}
		dvr.manage().timeouts().implicitlyWait(60,TimeUnit.SECONDS) ;
		return levelResources;		
	}
	
	private List getListOfReasources(int wbResult,int panelBody,int panelBodyLeafLevel){
		//System.out.println("getListOfReasources");
		List numberOfLinks = null;		
		numberOfLinks =dvr.findElements(By.xpath("((//div[@id='wb-results']/div[1]/div["+wbResult+"]//div[@class='panel-body']/div)["+panelBody+"]//div[@class='panel-body leaf-level'])["+panelBodyLeafLevel+"]//div[@class='resource']"));
		//System.out.println("numberOfLinks "+numberOfLinks.size());		
		return numberOfLinks;
	}
	
	private List getListOfReasources(int wbResult,int panelBodyLeafLevel){
		//System.out.println("getListOfReasources");
		List numberOfLinks = null;
		numberOfLinks = dvr.findElements(By.xpath("((//div[@id='wb-results']/div[1]/div["+wbResult+"]//div[@class='panel-body leaf-level'])["+panelBodyLeafLevel+"]//div[@class='resource'])"));
		//System.out.println("numberOfLinks "+numberOfLinks.size());		
		return numberOfLinks;
	}
	
	private String getUrl(WebElement element) throws InterruptedException{
		System.out.println("getUrl");
		String url = "";
		int numberOfWindowBeforClick;
		int numberOfWindowAfterClick;
		numberOfWindowBeforClick = testScript.countWindow();
		element.click();
		testScript.threadSleep(3);
		numberOfWindowAfterClick =  testScript.countWindow();
		System.out.println("numberOfWindowBeforClick "+numberOfWindowBeforClick+" numberOfWindowAfterClick "+numberOfWindowAfterClick);
		if(numberOfWindowAfterClick>numberOfWindowBeforClick){
			testScript.switchwindow(1);
			url = dvr.getCurrentUrl();
			testScript.threadSleep(2);
			dvr.close();
			testScript.switchwindow(0);
		}
		System.out.println("url "+url);
		return 	url.trim();
	}
	
	
}
