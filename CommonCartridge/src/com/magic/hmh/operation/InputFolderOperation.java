package com.magic.hmh.operation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.xmlbeans.soap.SOAPArrayType;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.magic.hmh.dao.InputDetails;
import com.magic.hmh.pageobject.Dashboard;
import com.magic.hmh.testscript.TestScript;

public class InputFolderOperation {
	
	private TestScript testScript;
	private WebDriver dvr;
	
	public InputFolderOperation(TestScript testScript,WebDriver dvr) {
		// TODO Auto-generated constructor stub
		this.testScript = testScript;
		this.dvr = dvr;
	}
		
	
	public void selectPrograme() throws InterruptedException, NumberFormatException, IOException{
		
		
		InputDetails inputDetails = new InputDetails();
		String inputCourse = inputDetails.getInputCourse();
		String course  = inputDetails.getCourseName();		
		boolean multipleCourse = false;
		String[] inputProgrameName = null;
		String[] brainHoneycourseName = null;
//		String[] inputCourseSpilt;
		int countOfCourses = 0;
		int countOFCourseBrainHoney = 0;
		if(inputCourse.contains(",") && course.contains(",")){
			multipleCourse = true;
			inputProgrameName =  inputCourse.split(",");	
			brainHoneycourseName = course.split(",");
			countOfCourses = inputProgrameName.length;
			countOFCourseBrainHoney = brainHoneycourseName.length;
		}else{
			countOfCourses = 1;
			countOFCourseBrainHoney = 1;	
		}
		
		if(countOfCourses!= 0 && countOFCourseBrainHoney !=0){
		if(countOfCourses==countOFCourseBrainHoney){
			
				
		for(int courseCount = 0; courseCount < countOfCourses; courseCount++){
			System.out.println("Course Count Of Execution "+(courseCount+1));
			String hrwCourse = "";
			String bHCourse = "";
			
			if(!multipleCourse){
				bHCourse = course;
				hrwCourse = inputCourse;
			}else{
				hrwCourse = inputProgrameName[courseCount];
				bHCourse = brainHoneycourseName[courseCount];
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
		
		ArrayList innerLinkArrayList  = new ArrayList();
		
		HashMap<String,ArrayList> hashMap = new HashMap<String,ArrayList>();
		HashMap<String,ArrayList> level2FolderMap = new HashMap<String,ArrayList>();	
		HashMap<String,ArrayList> lessonLevel3FolderMap = new HashMap<String,ArrayList>();
		HashMap<String,ArrayList> lessonLevel4FolderMap = new HashMap<String,ArrayList>();
		
		HashMap<String,ArrayList> levelLevelResourcehashMap = new HashMap<String,ArrayList>();
		HashMap<String,ArrayList> level2FolderResourceHashMap = new HashMap<String,ArrayList>();
		HashMap<String,ArrayList> lessonLevel4FolderResourceHashMap = new HashMap<String,ArrayList>();
		
		HashMap<String,ArrayList> chapterLinkMap = new HashMap<String,ArrayList>();
		
			if(levelLevelResouresMenu()){
			testScript.threadSleep(2);
				if(levelLevelResoure()){
					
						List numberOfLinks = dvr.findElements(By.xpath("//div[@id='wb-results']/div[1]/div"));
						
						if(numberOfLinks.size()>0){
							
						for(int resourcesLevelElementNumber = 1; resourcesLevelElementNumber <= numberOfLinks.size(); resourcesLevelElementNumber++){
							
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
		}
				if(panelHeadingExist()){				
					List panelHeadingList = dvr.findElements(By.xpath("//div[@id='browseview']//div[@id='wb-program-structure']/div"));

					if(panelHeadingList.size()>0){						
	//					for(int panelHeadingCount = 1; panelHeadingCount <= panelHeadingList.size(); panelHeadingCount++){
							for(int panelHeadingCount = 1;panelHeadingCount <= 2; panelHeadingCount++){
							
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
										getResources(level1Folderindex,level2FolderMap,hashMap,level2FoldersList,level2FolderResourceHashMap);
										
									}else{
										System.out.println("Unit Name in Programe Structure and Heading is not matching");
									}
								}else{
										System.out.println("wb-results not found of Unit");
								}
							
			
							testScript.threadSleep(2);
					//		List listOfLesson = dvr.findElements(By.xpath("((//div[@id='browseview']//div[@id='wb-program-structure']/div)["+panelHeadingCount+"]//div[@class='panel-body'])"));
							List listOfLesson = dvr.findElements(By.xpath("((//div[@id='browseview']//div[@id='wb-program-structure']/div)["+panelHeadingCount+"]/div/div[2]/div[@class='panel-body'])"));
							testScript.threadSleep(1);
							if(listOfLesson.size()>0){
								
								
							ArrayList lessonArrayList = new ArrayList();
							for(int lessonCount = 1;  lessonCount <= listOfLesson.size(); lessonCount++ ){
								testScript.threadSleep(2);
								ArrayList lessonLevel3FolderList = new ArrayList();
								try{
									String lessonName = "";
				//					testScript.waitForVisibility(dvr.findElement(By.xpath("((//div[@id='browseview']//div[@id='wb-program-structure']/div)["+panelHeadingCount+"]//div[@class='panel-body'])["+lessonCount+"]")));
									testScript.waitForVisibility(dvr.findElement(By.xpath("((//div[@id='browseview']//div[@id='wb-program-structure']/div)["+panelHeadingCount+"]/div/div[2]/div[@class='panel-body'])["+lessonCount+"]")));
									
						//			lessonName = dvr.findElement(By.xpath("((//div[@id='browseview']//div[@id='wb-program-structure']/div)["+panelHeadingCount+"]//div[@class='panel-body'])["+lessonCount+"]")).getText();
									lessonName = dvr.findElement(By.xpath("((//div[@id='browseview']//div[@id='wb-program-structure']/div)["+panelHeadingCount+"]/div/div[2]/div[@class='panel-body'])["+lessonCount+"]")).getText();
									System.out.println("---- "+lessonName);
									String lessonResultHeading = "";
									try{
						//				dvr.findElement(By.xpath("((//div[@id='browseview']//div[@id='wb-program-structure']/div)["+panelHeadingCount+"]//div[@class='panel-body'])["+lessonCount+"]")).click();
										dvr.findElement(By.xpath("((//div[@id='browseview']//div[@id='wb-program-structure']/div)["+panelHeadingCount+"]/div/div[2]/div[@class='panel-body'])["+lessonCount+"]")).click();
									}catch(Exception e){
										try{
						//					dvr.findElement(By.xpath("((//div[@id='browseview']//div[@id='wb-program-structure']/div)["+panelHeadingCount+"]//div[@class='panel-body'])["+lessonCount+"]")).click();
											dvr.findElement(By.xpath("((//div[@id='browseview']//div[@id='wb-program-structure']/div)["+panelHeadingCount+"]/div/div[2]/div[@class='panel-body'])["+lessonCount+"]")).click();
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
												getResourcesLesson(level1Folderindex,index1,lessonLevel3FolderMap,lessonLevel4FolderMap,lessonLevel4FolderResourceHashMap,lessonLevel3FolderList);
											}else{
												System.out.println("Lesson Name in Programe Structure and Heading is not matching");
											}
										}else{
											System.out.println("wb-results not found of Lesson");
										}
									}else{
											System.out.println("Lesson name is Empty");
									}									
									List listOfChapterLevel = null;
									listOfChapterLevel = dvr.findElements(By.xpath("((//div[@id='browseview']//div[@id='wb-program-structure']/div)["+panelHeadingCount+"]/div/div[2]/div[@class='panel-body'])["+lessonCount+"]/div/div[2]/div/a"));
									if(listOfChapterLevel.size()>0){										
										ArrayList arrayListChap = new ArrayList();										
										for(int chapNo = 1;chapNo <=listOfChapterLevel.size();chapNo++){
											String chapName = "";											
											String chapResultHeading = "";
											try{
												testScript.waitForVisibility(dvr.findElement(By.xpath("(((//div[@id='browseview']//div[@id='wb-program-structure']/div)["+panelHeadingCount+"]/div/div[2]/div[@class='panel-body'])["+lessonCount+"]/div/div[2]/div/a)["+chapNo+"]")));
												chapName = dvr.findElement(By.xpath("(((//div[@id='browseview']//div[@id='wb-program-structure']/div)["+panelHeadingCount+"]/div/div[2]/div[@class='panel-body'])["+lessonCount+"]/div/div[2]/div/a)["+chapNo+"]")).getText();												
												dvr.findElement(By.xpath("(((//div[@id='browseview']//div[@id='wb-program-structure']/div)["+panelHeadingCount+"]/div/div[2]/div[@class='panel-body'])["+lessonCount+"]/div/div[2]/div/a)["+chapNo+"]")).click();
											
											}catch(Exception e){
												try{
													dvr.findElement(By.xpath("(((//div[@id='browseview']//div[@id='wb-program-structure']/div)["+panelHeadingCount+"]/div/div[2]/div[@class='panel-body'])["+lessonCount+"]/div/div[2]/div/a)["+chapNo+"]")).click();
												}catch(Exception e1){													
												}
											}											
											testScript.threadSleep(2);											
											try{
												testScript.waitForVisibility(dvr.findElement(By.xpath(".//div[@id='wb-results']/div[1]/h3")));											
												chapResultHeading = dvr.findElement(By.xpath(".//div[@id='wb-results']/div[1]/h3")).getText();
											}catch(Exception r){
												try{
													testScript.waitForVisibility(dvr.findElement(By.xpath(".//div[@id='wb-results']/div[1]/h3")));											
													chapResultHeading = dvr.findElement(By.xpath(".//div[@id='wb-results']/div[1]/h3")).getText();
												}catch(Exception rr){
													System.out.println("lessonResultHeading not found");
												}
											}
											System.out.println("------"+chapName);
											String indexChap = "";
											if(!lessonLevel3FolderList.contains(chapName)){
												lessonLevel3FolderList.add(chapName);
												arrayListChap.add(chapName);
												indexChap = String.valueOf(lessonLevel3FolderList.indexOf(chapName));
											}else{
												arrayListChap.add(chapName);
												indexChap = String.valueOf(lessonLevel3FolderList.indexOf(chapName));
											}
											
											
											if(!(chapName.isEmpty() && chapResultHeading.isEmpty())){
												if(levelLevelResoure()){
													if(chapName.contains(chapResultHeading)){
														getResourcesChap(level1Folderindex,index1,indexChap,lessonLevel3FolderMap,lessonLevel4FolderMap,lessonLevel4FolderResourceHashMap,chapterLinkMap);
													}
												}
											}
										}
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
				
				
			
			testScript.createExcelFile(hrwCourse);
			testScript.createSheet("HRW Links");
				int rowvalue = 1;
				int mainFolderCol = 0;
				int level2FolderCol =1;
				int level3FolderCol =2;
				int level4FolderCol = 3;
				int lessonlevel4Roursorce = 4;
				int chapLevelCol = 5;
				
				testScript.worksheet.createRow(0).createCell(0).setCellValue("HRW Links");
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
															  						for(int m = 0; m<valueList4.size(); m++){
															  							String strValueLm = "";
															  							strValueLm = valueList4.get(m);
															  							testScript.worksheet.createRow(rowvalue).createCell(lessonlevel4Roursorce).setCellValue(strValueLm);
															  							rowvalue++;															  							
															  							for(HashMap.Entry<String, ArrayList> me5 : chapterLinkMap.entrySet()){
															  								String lessonlevel5Key = me5.getKey();
																		  					String level5Key = "";
																		  					level5Key = String.valueOf(i)+"."+(String.valueOf(j))+"."+(String.valueOf(k))+"."+(String.valueOf(l))+"."+(String.valueOf(m));
																		  					if(level5Key.equals(lessonlevel5Key)){
																		  						ArrayList<String> valueList5 = me5.getValue();																		  						
																		  						for(int n = 0; n<valueList5.size(); n++){
																		  							String strValueL5 = "";
																		  							strValueL5 = valueList5.get(n);
																		  							System.out.println(strValueL5);
																		  							testScript.worksheet.createRow(rowvalue).createCell(chapLevelCol).setCellValue(strValueL5);
																		  							rowvalue++;
																		  						}
																		  					}
															  								
															  								
															  							}
															  						}
															  						
															  						/*for(String strResource : valueList4){
																			  			testScript.worksheet.createRow(rowvalue).createCell(lessonlevel4Roursorce).setCellValue(strResource);
																			  			rowvalue++;
															  						}*/
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
				
				
				testScript.writeExcel(hrwCourse);		
				
		testScript.threadSleep(2);		
		dvr.findElement(By.xpath("//a[@id='platform-logout']")).click();
		testScript.threadSleep(2);
		
		BrainHonneyTest brainHonneyTest = new BrainHonneyTest(testScript, dvr);
		brainHonneyTest.folderOperation(innerLinkArrayList,hashMap,level2FolderMap,lessonLevel4FolderMap,lessonLevel4FolderResourceHashMap,bHCourse,hrwCourse,chapterLinkMap);
	}
		
		}else{
			System.out.println("HRW course course are not same as Brain honey Course Count");
		}
		}else{
			System.out.println("Course Name not given");
		}
	}
	
	public void getResourcesLesson(String level1Folderindex,String level2Index,HashMap<String,ArrayList> lessonLevel3FolderMap,HashMap<String,ArrayList> lessonLevel4FolderMap,HashMap<String,ArrayList> lessonLevel4FolderResourceHashMap,ArrayList lessonLevel3FolderList) throws InterruptedException{
	//	System.out.println("getResourcesLesson");
		
		
		if(levelLevelResoure()){
			testScript.threadSleep(2);
				if(levelLevelResoure()){	
		
		List numberOfLinks = dvr.findElements(By.xpath("//div[@id='wb-results']/div[1]/div"));
		
		if(numberOfLinks.size()>0){
			
		
//		ArrayList lessonLevel3FolderList = new ArrayList();
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
																try{
																	linkName = dvr.findElement(By.xpath("(((//div[@id='wb-results']/div[1]/div["+resourcesLevelElementNumber+"]//div[@class='panel-body']/div)["+innerLinkLoop+"]//div[@class='panel-body leaf-level'])["+level2FolderElement+"]//div[@class='resource'])["+link+"]/a")).getText();
																}catch(Exception e){
																	try{
																		linkName = dvr.findElement(By.xpath("(((//div[@id='wb-results']/div[1]/div["+resourcesLevelElementNumber+"]//div[@class='panel-body']/div)["+innerLinkLoop+"]//div[@class='panel-body leaf-level'])["+level2FolderElement+"]//div[@class='resource'])["+link+"]/a")).getText();
																	}catch(Exception f){
																	
																	}
																}
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
															try{
																linkName = dvr.findElement(By.xpath("((//div[@id='wb-results']/div[1]/div["+resourcesLevelElementNumber+"]//div[@class='panel-body leaf-level'])["+level2FolderElement+"]//div[@class='resource'])["+link+"]/a")).getText();
																
															}catch(Exception f){
																try{
																	linkName = dvr.findElement(By.xpath("((//div[@id='wb-results']/div[1]/div["+resourcesLevelElementNumber+"]//div[@class='panel-body leaf-level'])["+level2FolderElement+"]//div[@class='resource'])["+link+"]/a")).getText();
																	
																}catch(Exception g){
																	
																}
															}
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
																try{
																	linkName = dvr.findElement(By.xpath("(((//div[@id='wb-results']/div[1]/div["+resourcesLevelElementNumber+"]//div[@class='panel-body']/div)["+innerLinkLoop+"]//div[@class='panel-body leaf-level'])["+level2FolderElement+"]//div[@class='resource'])["+link+"]/a")).getText();
																	
																}catch(Exception e){
																	try{
																		linkName = dvr.findElement(By.xpath("(((//div[@id='wb-results']/div[1]/div["+resourcesLevelElementNumber+"]//div[@class='panel-body']/div)["+innerLinkLoop+"]//div[@class='panel-body leaf-level'])["+level2FolderElement+"]//div[@class='resource'])["+link+"]/a")).getText();
																		
																	}catch(Exception f){
																		
																	}
																}																
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
													try{
														linkName = dvr.findElement(By.xpath("((//div[@id='wb-results']/div[1]/div["+resourcesLevelElementNumber+"]//div[@class='panel-body leaf-level'])["+level2FolderElement+"]//div[@class='resource'])["+link+"]/a")).getText();
														
													}catch(Exception f){
														try{
															linkName = dvr.findElement(By.xpath("((//div[@id='wb-results']/div[1]/div["+resourcesLevelElementNumber+"]//div[@class='panel-body leaf-level'])["+level2FolderElement+"]//div[@class='resource'])["+link+"]/a")).getText();
															
														}catch(Exception g){
															
														}
													}
													
													
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
	
	public void getResources(String level1Folderindex,HashMap<String,ArrayList> level2FolderMap,HashMap<String,ArrayList> hashMap,ArrayList level2FoldersList,HashMap<String,ArrayList> level2FolderResourceHashMap) throws InterruptedException{
//	System.out.println("getResources");
		
		
		if(levelLevelResoure()){
			testScript.threadSleep(2);
				if(levelLevelResoure()){	
		
		List numberOfLinks = dvr.findElements(By.xpath("//div[@id='wb-results']/div[1]/div"));
		
		
		if(numberOfLinks.size()>0){
//		ArrayList level3FoldersList = new ArrayList();
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
	//								System.out.println(innerLinkText);
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
																				
																				try{
																					linkName = dvr.findElement(By.xpath("(((//div[@id='wb-results']/div[1]/div["+resourcesLevelElementNumber+"]//div[@class='panel-body']/div)["+innerLinkLoop+"]//div[@class='panel-body leaf-level'])["+level2FolderElement+"]//div[@class='resource'])["+link+"]/a")).getText();
																					}catch(Exception e){
																					try{
																						linkName = dvr.findElement(By.xpath("(((//div[@id='wb-results']/div[1]/div["+resourcesLevelElementNumber+"]//div[@class='panel-body']/div)["+innerLinkLoop+"]//div[@class='panel-body leaf-level'])["+level2FolderElement+"]//div[@class='resource'])["+link+"]/a")).getText();
																					}catch(Exception f){
																						
																					}
																				}
																				
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
																	try{
																		linkName = dvr.findElement(By.xpath("((//div[@id='wb-results']/div[1]/div["+resourcesLevelElementNumber+"]//div[@class='panel-body leaf-level'])["+level2FolderElement+"]//div[@class='resource'])["+link+"]/a")).getText();
																	}catch(Exception f){
																		try{
																			linkName = dvr.findElement(By.xpath("((//div[@id='wb-results']/div[1]/div["+resourcesLevelElementNumber+"]//div[@class='panel-body leaf-level'])["+level2FolderElement+"]//div[@class='resource'])["+link+"]/a")).getText();
																		}catch(Exception g){
																			
																		}
																	}
																	
																	
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
																			try{
																				linkName = dvr.findElement(By.xpath("(((//div[@id='wb-results']/div[1]/div["+resourcesLevelElementNumber+"]//div[@class='panel-body']/div)["+innerLinkLoop+"]//div[@class='panel-body leaf-level'])["+level2FolderElement+"]//div[@class='resource'])["+link+"]/a")).getText();
																			}catch(Exception e){
																				try{
																					linkName = dvr.findElement(By.xpath("(((//div[@id='wb-results']/div[1]/div["+resourcesLevelElementNumber+"]//div[@class='panel-body']/div)["+innerLinkLoop+"]//div[@class='panel-body leaf-level'])["+level2FolderElement+"]//div[@class='resource'])["+link+"]/a")).getText();
																				}catch(Exception f){
																					
																				}
																			}
																			
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
																
																try{
																	linkName = dvr.findElement(By.xpath("((//div[@id='wb-results']/div[1]/div["+resourcesLevelElementNumber+"]//div[@class='panel-body leaf-level'])["+level2FolderElement+"]//div[@class='resource'])["+link+"]/a")).getText();
																}catch(Exception f){
																	try{
																		linkName = dvr.findElement(By.xpath("((//div[@id='wb-results']/div[1]/div["+resourcesLevelElementNumber+"]//div[@class='panel-body leaf-level'])["+level2FolderElement+"]//div[@class='resource'])["+link+"]/a")).getText();
																	}catch(Exception g){
																		
																	}
																}
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
	
	
	public void getResourcesChap(String level1Folderindex,String level2Index,String indexChap,HashMap<String,ArrayList> lessonLevel3FolderMap,HashMap<String,ArrayList> lessonLevel4FolderMap,HashMap<String,ArrayList> lessonLevel4FolderResourceHashMap,HashMap<String,ArrayList> chapterLinkMap) throws InterruptedException{
		//	System.out.println("getResourcesLesson");
			
			
			if(levelLevelResoure()){
				testScript.threadSleep(2);
					if(levelLevelResoure()){	
			
			List numberOfLinks = dvr.findElements(By.xpath("//div[@id='wb-results']/div[1]/div"));
			
			if(numberOfLinks.size()>0){
				
			
//			ArrayList lessonLevel3FolderList = new ArrayList();
			ArrayList sectionLevelHeadingList = new ArrayList();
				
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
							if(!sectionLevelHeadingList.contains(innerLinkText)){
								sectionLevelHeadingList.add(innerLinkText);
								index = String.valueOf(sectionLevelHeadingList.indexOf(innerLinkText));
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
																	try{
																		linkName = dvr.findElement(By.xpath("(((//div[@id='wb-results']/div[1]/div["+resourcesLevelElementNumber+"]//div[@class='panel-body']/div)["+innerLinkLoop+"]//div[@class='panel-body leaf-level'])["+level2FolderElement+"]//div[@class='resource'])["+link+"]/a")).getText();
																	}catch(Exception e){
																		try{
																			linkName = dvr.findElement(By.xpath("(((//div[@id='wb-results']/div[1]/div["+resourcesLevelElementNumber+"]//div[@class='panel-body']/div)["+innerLinkLoop+"]//div[@class='panel-body leaf-level'])["+level2FolderElement+"]//div[@class='resource'])["+link+"]/a")).getText();
																		}catch(Exception f){
																		
																		}
																	}
																	listOfLinkResource.add(linkName);
															}
									
							//								lessonLevel4FolderResourceHashMap.put(level1Folderindex+"."+level2Index+"."+index+"."+(level2FolderElement-1), listOfLinkResource);
															chapterLinkMap.put(level1Folderindex+"."+level2Index+"."+indexChap+"."+index+"."+(level2FolderElement-1), listOfLinkResource);
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
																try{
																	linkName = dvr.findElement(By.xpath("((//div[@id='wb-results']/div[1]/div["+resourcesLevelElementNumber+"]//div[@class='panel-body leaf-level'])["+level2FolderElement+"]//div[@class='resource'])["+link+"]/a")).getText();
																	
																}catch(Exception f){
																	try{
																		linkName = dvr.findElement(By.xpath("((//div[@id='wb-results']/div[1]/div["+resourcesLevelElementNumber+"]//div[@class='panel-body leaf-level'])["+level2FolderElement+"]//div[@class='resource'])["+link+"]/a")).getText();
																		
																	}catch(Exception g){
																		
																	}
																}
																listOfLinkResource.add(linkName);
														}					
										//				lessonLevel4FolderResourceHashMap.put(level1Folderindex+"."+level2Index+"."+index+"."+(level2FolderElement-1), listOfLinkResource);
														chapterLinkMap.put(level1Folderindex+"."+level2Index+"."+indexChap+"."+index+"."+(level2FolderElement-1), listOfLinkResource);
														}
													}
												}
											}catch(Exception f){
												System.out.println("Xpath not found for level 4");
											}
										}
		//								lessonLevel4FolderMap.put(level1Folderindex+"."+level2Index+"."+index, al);
										lessonLevel4FolderResourceHashMap.put(level1Folderindex+"."+level2Index+"."+indexChap+"."+index, al);
									}
							}else{
								index = String.valueOf(sectionLevelHeadingList.indexOf(innerLinkText));
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
																	try{
																		linkName = dvr.findElement(By.xpath("(((//div[@id='wb-results']/div[1]/div["+resourcesLevelElementNumber+"]//div[@class='panel-body']/div)["+innerLinkLoop+"]//div[@class='panel-body leaf-level'])["+level2FolderElement+"]//div[@class='resource'])["+link+"]/a")).getText();
																		
																	}catch(Exception e){
																		try{
																			linkName = dvr.findElement(By.xpath("(((//div[@id='wb-results']/div[1]/div["+resourcesLevelElementNumber+"]//div[@class='panel-body']/div)["+innerLinkLoop+"]//div[@class='panel-body leaf-level'])["+level2FolderElement+"]//div[@class='resource'])["+link+"]/a")).getText();
																			
																		}catch(Exception f){
																			
																		}
																	}																
																	listOfLinkResource.add(linkName);
															}
														
												boolean bodyFolder = false;
												for (HashMap.Entry<String, ArrayList> entry : lessonLevel4FolderResourceHashMap.entrySet()) {												
													String key = entry.getKey();
													String temp = "";
													temp = level1Folderindex+"."+level2Index+"."+indexChap+"."+index;
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
													for (HashMap.Entry<String, ArrayList> entry : lessonLevel4FolderResourceHashMap.entrySet()) {												
														String key = entry.getKey();
														String temp = "";
														temp = level1Folderindex+"."+level2Index+"."+indexChap+"."+index;
																												
														if(key.equals(temp)){
															
															ArrayList value = entry.getValue();
															for(int i = 0 ;i<value.size();i++){
																												
																if(value.get(i).equals(level2FolderName)){
															
																	folderIndex = i;
																}
															}																			
														}		
													}
													for (HashMap.Entry<String, ArrayList> entry : chapterLinkMap.entrySet()) {												
														String key = entry.getKey();
														String temp = "";
														temp = level1Folderindex+"."+level2Index+"."+indexChap+"."+index+"."+folderIndex;
														if(key.equals(temp)){	
															ArrayList value = entry.getValue();
															value.addAll(listOfLinkResource);	
														}		
													}
												}else{
													int folderIndex = 0;
													for (HashMap.Entry<String, ArrayList> entry : lessonLevel4FolderResourceHashMap.entrySet()) {												
														String key = entry.getKey();
														String temp = "";
														temp = level1Folderindex+"."+level2Index+"."+indexChap+"."+index;	
														if(key.equals(temp)){
															ArrayList value = entry.getValue();
															for(int i = 0 ;i<value.size();i++){
																if(value.get(i).equals(level2FolderName)){
																	folderIndex = i;
																}
															}																			
														}		
													}												
							//							lessonLevel4FolderResourceHashMap.put(level1Folderindex+"."+level2Index+"."+index+"."+(folderIndex), listOfLinkResource);
														chapterLinkMap.put(level1Folderindex+"."+level2Index+"."+indexChap+"."+index+"."+(folderIndex), listOfLinkResource);
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
														try{
															linkName = dvr.findElement(By.xpath("((//div[@id='wb-results']/div[1]/div["+resourcesLevelElementNumber+"]//div[@class='panel-body leaf-level'])["+level2FolderElement+"]//div[@class='resource'])["+link+"]/a")).getText();
															
														}catch(Exception f){
															try{
																linkName = dvr.findElement(By.xpath("((//div[@id='wb-results']/div[1]/div["+resourcesLevelElementNumber+"]//div[@class='panel-body leaf-level'])["+level2FolderElement+"]//div[@class='resource'])["+link+"]/a")).getText();
																
															}catch(Exception g){																
															}
														}
														listOfLinkResource.add(linkName);
													}
													boolean bodyFolder = false;
													for (HashMap.Entry<String, ArrayList> entry : lessonLevel4FolderResourceHashMap.entrySet()) {												
														String key = entry.getKey();
														String temp = "";											
														temp = level1Folderindex+"."+level2Index+"."+indexChap+"."+index;													
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
														for (HashMap.Entry<String, ArrayList> entry : lessonLevel4FolderResourceHashMap.entrySet()) {												
															String key = entry.getKey();
															String temp = "";
															temp = level1Folderindex+"."+level2Index+"."+indexChap+"."+index;																													
															if(key.equals(temp)){														
																ArrayList value = entry.getValue();
																for(int i = 0 ;i<value.size();i++){																											
																	if(value.get(i).equals(level2FolderName)){																	
																		folderIndex = i;
																	}
																}																			
															}		
														}
														for (HashMap.Entry<String, ArrayList> entry : chapterLinkMap.entrySet()) {												
															String key = entry.getKey();
															String temp = "";
															temp = level1Folderindex+"."+level2Index+"."+indexChap+"."+index+"."+folderIndex;
															if(key.equals(temp)){	
																ArrayList value = entry.getValue();
																value.addAll(listOfLinkResource);														
															}		
														}
													}else{														
															int folderIndex = 0;
															for (HashMap.Entry<String, ArrayList> entry : lessonLevel4FolderResourceHashMap.entrySet()) {												
																String key = entry.getKey();
																String temp = "";
																temp = level1Folderindex+"."+level2Index+"."+indexChap+"."+index;																														
																if(key.equals(temp)){													
																	ArrayList value = entry.getValue();
																	for(int i = 0 ;i<value.size();i++){																													
																		if(value.get(i).equals(level2FolderName)){																
																			folderIndex = i;
																		}
																	}																			
																}		
															}														
							//								lessonLevel4FolderResourceHashMap.put(level1Folderindex+"."+level2Index+"."+index+"."+(folderIndex), listOfLinkResource);
															chapterLinkMap.put(level1Folderindex+"."+level2Index+"."+indexChap+"."+index+"."+(folderIndex), listOfLinkResource);
														}
														}
													}
												}
											}catch(Exception f){
											
											}
										}
										
									}							
							}	
							}catch(Exception e){
								
							}
						
						}
		//				lessonLevel3FolderMap.put(level1Folderindex+"."+level2Index, sectionLevelHeadingList);
						lessonLevel4FolderMap.put(level1Folderindex+"."+level2Index+"."+indexChap, sectionLevelHeadingList);
						
						}
					}
				}
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
	
	
	
}
