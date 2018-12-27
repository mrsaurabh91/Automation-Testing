package com.magic.hmh.operation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import javax.swing.plaf.synth.SynthSpinnerUI;

import org.apache.xalan.xsltc.compiler.sym;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.magic.hmh.dao.InputDetails;
import com.magic.hmh.testscript.TestScript;

public class BrainHonneyTest1 {
	private TestScript testScript;
	private WebDriver dvr;
	
	 static Wait<WebDriver> wait;
	
	public BrainHonneyTest1(TestScript testScript,WebDriver dvr) {
		// TODO Auto-generated constructor stub		
		this.testScript = testScript;
		this.dvr = dvr;
		wait = new WebDriverWait(dvr, 60);
	}
	
	public void folderOperation(ArrayList innerLinkArrayList,HashMap<String,ArrayList> hashMap,HashMap<String,ArrayList> level2FolderMap,HashMap<String,ArrayList> lessonLevel4FolderMap,HashMap<String,ArrayList> lessonLevel4FolderResourceHashMap,String brainHoneyCourseName,String hrwCourse,HashMap<String,String> linkNameHashMap,HashMap<String,String> lunchedUrlHashMap,HashMap<String,String> mdsMapData) throws InterruptedException, IOException{
		System.out.println("Brainhoney");
		testScript.threadSleep(2);
		InputDetails inputDetails = new InputDetails();
		
		String url = inputDetails.geturl();
		dvr.get(url);
		testScript.threadSleep(5);
		try{
			
		
			testScript.waitForVisibility(dvr.findElement(By.xpath("//input[@id='username']")));
		
			String userName = inputDetails.getBrainhoneyUserName();
			String password = inputDetails.getBrainhoneyPassword();

			dvr.findElement(By.xpath("//input[@id='username']")).sendKeys(userName);
			Thread.sleep(2000);
			dvr.findElement(By.xpath("//input[@name='password']")).sendKeys(password);
			Thread.sleep(2000);
			dvr.findElement(By.xpath("//input[@id='localsubmit']")).click();			
			testScript.threadSleep(5);
		
			HashMap<String,ArrayList> brainHonneylessonLevel1FolderResourceHashMap = new HashMap<String,ArrayList>();
			HashMap<String,ArrayList> brainHonneylessonLevel2FolderResourceHashMap = new HashMap<String,ArrayList>();
			HashMap<String,ArrayList> brainHonneylessonLevel3FolderResourceHashMap = new HashMap<String,ArrayList>();
			HashMap<String,ArrayList> brainHonneylessonLevel4FolderResourceHashMap = new HashMap<String,ArrayList>();
			
			HashMap<String,String> brainHonneylinkNameMap = new HashMap<String,String>();
			HashMap<String,String> brainHonneyUrlMap = new HashMap<String,String>();
			
			try{
			
		
				testScript.waitForVisibility(dvr.findElement(By.xpath("//div[@id='h-admin-btn']/div")));
				dvr.findElement(By.xpath("//div[@id='h-admin-btn']/div")).click();
		
				try{
			
		
		
					testScript.waitForVisibility(dvr.findElement(By.xpath(".//*[contains(@id,'sub-header')]//div[contains(@id,'admin-courses')]/a")));
					dvr.findElement(By.xpath(".//*[contains(@id,'sub-header')]//div[contains(@id,'admin-courses')]/a")).click();
					Thread.sleep(3000);
		
					

					ArrayList level1FolderList = new ArrayList();

		boolean inputTitle = false;
		try{
			
			if(dvr.findElement(By.xpath(".//input[@name='name']")).isDisplayed()){
				dvr.findElement(By.xpath(".//input[@name='name']")).sendKeys(brainHoneyCourseName);
				dvr.findElement(By.xpath(".//*[@id='ext-gen78']")).click();
				inputTitle = true;
			}
			
		}catch(Exception e){
			try{
				
				if(dvr.findElement(By.xpath(".//input[@name='name']")).isDisplayed()){
					dvr.findElement(By.xpath(".//input[@name='name']")).sendKeys(brainHoneyCourseName);
					dvr.findElement(By.xpath(".//*[@id='ext-gen78']")).click();
					inputTitle = true;
				}
			}catch(Exception f){
				System.out.println("Find Course popUp not found");
			}
		}
		Thread.sleep(3000);
		boolean viewbutton = false;
		
		if(inputTitle){
		
//			String titleOfCourses = inputDetails.getCourseName();
//			System.out.println("TitleOfCourses "+brainHoneyCourseName);
			testScript.waitForVisibility(dvr.findElement(By.xpath("//div[text()='"+brainHoneyCourseName+"']")));			
			doubleClick(dvr.findElement(By.xpath("//div[text()='"+brainHoneyCourseName+"']")));
			Thread.sleep(5000);
			
			try{
				testScript.waitForVisibility(dvr.findElement(By.xpath("//button[text()='View']")));
				dvr.findElement(By.xpath("//button[text()='View']")).click();
				viewbutton = true;
			}catch(Exception e){
				try{
					testScript.waitForVisibility(dvr.findElement(By.xpath("//button[text()='View']")));
					dvr.findElement(By.xpath("//button[text()='View']")).click();
					viewbutton = true;
				}catch(Exception f){
					System.out.println("View Button not found");
				}
			}
//			System.out.println("View Btn clicked");
			Thread.sleep(3000);
			if(viewbutton){
				boolean rootNodeOfFolder = false;
				try{
					rootNodeOfFolder = dvr.findElement(By.xpath("//div[contains(@class,'root-node')]")).isDisplayed();
				}catch(Exception e){
					try{
						rootNodeOfFolder = dvr.findElement(By.xpath("//div[contains(@class,'root-node')]")).isDisplayed();
					}catch(Exception f){
						System.out.println("Folder Structure not found");
					}
				}
				Thread.sleep(3000);
//				System.out.println("Root Folder Shown");
				if(rootNodeOfFolder){
					
						List numberOfMainFolders  = dvr.findElements(By.xpath("//div[contains(@class,'root-node')]/li"));
//						System.out.println("numberOfResources Size "+numberOfMainFolders.size()); 
						int minSize = wait.until(ExpectedConditions.visibilityOfAllElements(dvr.findElements(By.xpath("//img[@class='x-tree-ec-icon x-tree-elbow-minus' or @class='x-tree-ec-icon x-tree-elbow-end-minus']")))).size();
			            for (int m = minSize; m >= 1; m--) {
			                dvr.findElement(By.xpath("(//img[@class='x-tree-ec-icon x-tree-elbow-minus' or @class='x-tree-ec-icon x-tree-elbow-end-minus'])[" + m + "]")).click();
			            }
			            
			           
			            
						for(int level1folder = 1; level1folder <= numberOfMainFolders.size(); level1folder++){
	//		            for(int level1folder = 1; level1folder<=3; level1folder++){
	//						System.out.println("level1folder "+level1folder);
							String level1ResourceName = "";
							level1ResourceName = dvr.findElement(By.xpath("((//div[contains(@class,'root-node')]/li)/div)["+level1folder+"]//a/span")).getText();
							System.out.println(level1ResourceName);
							level1FolderList.add(level1ResourceName);
							
							dvr.findElement(By.xpath("(//div[@class='x-tree-root-node']/li)[" + level1folder + "]/div//img[@class='x-tree-ec-icon x-tree-elbow-plus' or @class='x-tree-ec-icon x-tree-elbow-end-plus']")).click();
							int level2Size = dvr.findElements(By.xpath("(//div[@class='x-tree-root-node']/li)[" +level1folder+ "]/ul/li/div//img[@class='x-tree-ec-icon x-tree-elbow-plus' or @class='x-tree-ec-icon x-tree-elbow-end-plus']")).size();
						
							ArrayList level2FolderList = new ArrayList();
							if (level2Size >= 1) {
			                    for (int l2 = 1; l2 <= level2Size; l2++) {
	//		                    	  for (int l2 = 1; l2 <= 5; l2++) {
			                    	String level2ResourceName = "";			                    	
			                    	level2ResourceName = dvr.findElement(By.xpath("(((//div[contains(@class,'root-node')]/li)/ul)["+level1folder+"]/li/div/div/a/span)["+l2+"]")).getText();
		//	                    	System.out.println("--"+level2ResourceName);
			                    	level2FolderList.add(level2ResourceName);
			                    	
			                    	dvr.findElement(By.xpath("((//div[@class='x-tree-root-node']/li)[" + level1folder + "]/ul/li/div//img[@class='x-tree-ec-icon x-tree-elbow-plus' or @class='x-tree-ec-icon x-tree-elbow-end-plus'])[" + l2 + "]")).click();
			                    	
			                    	 int level3Size;
				                       try {
				                            dvr.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
				                            level3Size = dvr.findElements(By.xpath("((//div[@class='x-tree-root-node']/li)[" + level1folder + "]/ul/li/div//img[@class='x-tree-ec-icon x-tree-elbow-minus' or @class='x-tree-ec-icon x-tree-elbow-end-minus'])[1]/parent::div/parent::div/following-sibling::ul/li/div//img[@class='x-tree-ec-icon x-tree-elbow-plus' or @class='x-tree-ec-icon x-tree-elbow-end-plus']")).size();
				                            dvr.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
				                        } catch (Exception e) {
				                            level3Size = 0;
				                            dvr.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
				                        }
			                    	
				                       ArrayList level3FolderList = new ArrayList();
				                        if (level3Size >= 1) {
				                        	for (int l3 = 1; l3 <= level3Size; l3++) {
				                        		String level3ResourceName = "";			                    	
						                    	level3ResourceName = dvr.findElement(By.xpath("((((//div[contains(@class,'root-node')]/li)/ul)["+level1folder+"]/li)["+l2+"]/ul/li/div//a[@class='x-tree-node-anchor']/span)["+l3+"]")).getText();
		//		                        		System.out.println("----"+level3ResourceName);
				                        		level3FolderList.add(level3ResourceName);
				                        		dvr.findElement(By.xpath("(((//div[@class=\"x-tree-root-node\"]/li)[" + level1folder + "]/ul/li/div//img[@class=\"x-tree-ec-icon x-tree-elbow-minus\" or @class=\"x-tree-ec-icon x-tree-elbow-end-minus\"])[1]/parent::div/parent::div/following-sibling::ul/li[" + l3 + "]//img[@class=\"x-tree-ec-icon x-tree-elbow-plus\" or @class=\"x-tree-ec-icon x-tree-elbow-end-plus\"])[1]")).click();

				                        		 int level4Size;
					                                try {
					                                    dvr.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
					                                    level4Size = dvr.findElements(By.xpath("(((//div[@class='x-tree-root-node']/li)[" + level1folder + "]/ul/li/div//img[@class='x-tree-ec-icon x-tree-elbow-minus' or @class='x-tree-ec-icon x-tree-elbow-end-minus'])[1]/parent::div/parent::div/following-sibling::ul/li[" + l3 + "]//img[@class='x-tree-ec-icon x-tree-elbow-plus' or @class='x-tree-ec-icon x-tree-elbow-end-plus'])")).size();
					                                     dvr.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
					                                } catch (Exception e) {
					                                    level4Size = 0;
					                                    dvr.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
					                                }
					                               					                                
					                                ArrayList level4FolderList = new ArrayList();
					                               
					                                if (level4Size >= 1) {
					                                    for (int l4 = 1; l4 <= level4Size; l4++) {
					                                    	String level4ResourceName = "";	
					                                    	level4ResourceName = dvr.findElement(By.xpath("((((((//div[contains(@class,'root-node')]/li)/ul)["+level1folder+"]/li)["+l2+"]/ul)/li)["+l3+"]/ul/li/div/div//a/span)["+l4+"]")).getText();
					  //                                  	System.out.println("------"+level4ResourceName);
					                                    	level4FolderList.add(level4ResourceName);
					                                    	dvr.findElement(By.xpath("(((//div[@class='x-tree-root-node']/li)[" + level1folder + "]/ul/li/div//img[@class='x-tree-ec-icon x-tree-elbow-minus' or @class='x-tree-ec-icon x-tree-elbow-end-minus'])[1]/parent::div/parent::div/following-sibling::ul/li[" + l3 + "]//img[@class='x-tree-ec-icon x-tree-elbow-plus' or @class='x-tree-ec-icon x-tree-elbow-end-plus'])[" + l4 + "]")).click();
					                                    	 
					                                    	 int level5LinkSize;
						                                        try {
						                                            dvr.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
						                                            level5LinkSize = dvr.findElements(By.xpath("(((//div[@class=\"x-tree-root-node\"]/li)[" + level1folder + "]/ul/li/div//img[@class=\"x-tree-ec-icon x-tree-elbow-minus\" or @class=\"x-tree-ec-icon x-tree-elbow-end-minus\"])[1]/parent::div/parent::div/following-sibling::ul//img[@class=\"x-tree-ec-icon x-tree-elbow-minus\" or @class=\"x-tree-ec-icon x-tree-elbow-end-minus\"])[2]/parent::div/parent::div/following-sibling::ul//a/span")).size();
						                                            dvr.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
						                                        } catch (Exception e) {
						                                            level5LinkSize = 0;
						                                            dvr.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
						                                        }
					                                    	
						                                        ArrayList level4Resources = new ArrayList();
						                                        
						                                        if (level5LinkSize >= 1) {
						                                            for (int l5 = 1; l5 <= level5LinkSize; l5++) {
						                                            	 String title = "";
						                                            	 String brainHoneyUrl = "";
											                             WebElement webelementLink = null;
											                             String GUID = "";
											                             boolean webLinkPresense = false;
											                             try{
											                            	 webelementLink = dvr.findElement(By.xpath("((((//div[@class='x-tree-root-node']/li)[" + level1folder + "]/ul/li/div//img[@class='x-tree-ec-icon x-tree-elbow-minus' or @class='x-tree-ec-icon x-tree-elbow-end-minus'])[1]/parent::div/parent::div/following-sibling::ul//img[@class='x-tree-ec-icon x-tree-elbow-minus' or @class='x-tree-ec-icon x-tree-elbow-end-minus'])[2]/parent::div/parent::div/following-sibling::ul//a/span)[" + l5 + "]"));
											    						     webLinkPresense = true;
											                             }catch(Exception e){
											                            	 try{
											                            		 webelementLink = dvr.findElement(By.xpath("((((//div[@class='x-tree-root-node']/li)[" + level1folder + "]/ul/li/div//img[@class='x-tree-ec-icon x-tree-elbow-minus' or @class='x-tree-ec-icon x-tree-elbow-end-minus'])[1]/parent::div/parent::div/following-sibling::ul//img[@class='x-tree-ec-icon x-tree-elbow-minus' or @class='x-tree-ec-icon x-tree-elbow-end-minus'])[2]/parent::div/parent::div/following-sibling::ul//a/span)[" + l5 + "]"));
											        						      webLinkPresense = true;
												                             }catch(Exception f){
												                            	 System.out.println("Web Link not found");
												                             }
											                             }
											                            //                                           title = dvr.findElement(By.xpath("((((//div[@class='x-tree-root-node']/li)[" + level1folder + "]/ul/li/div//img[@class='x-tree-ec-icon x-tree-elbow-minus' or @class='x-tree-ec-icon x-tree-elbow-end-minus'])[1]/parent::div/parent::div/following-sibling::ul//img[@class='x-tree-ec-icon x-tree-elbow-minus' or @class='x-tree-ec-icon x-tree-elbow-end-minus'])[2]/parent::div/parent::div/following-sibling::ul//a/span)[" + l5 + "]")).getText();
											                             title = webelementLink.getText();
											                             try{
											                            	 GUID = dvr.findElement(By.xpath("((((//div[@class='x-tree-root-node']/li)[" + level1folder + "]/ul/li/div//img[@class='x-tree-ec-icon x-tree-elbow-minus' or @class='x-tree-ec-icon x-tree-elbow-end-minus'])[1]/parent::div/parent::div/following-sibling::ul//img[@class='x-tree-ec-icon x-tree-elbow-minus' or @class='x-tree-ec-icon x-tree-elbow-end-minus'])[2]/parent::div/parent::div/following-sibling::ul/li/div)[" + l5 + "]")).getAttribute("ext:tree-node-id");
													                            
											                             }catch(Exception e){
											                            	 try{
											                            		 GUID = dvr.findElement(By.xpath("((((//div[@class='x-tree-root-node']/li)[" + level1folder + "]/ul/li/div//img[@class='x-tree-ec-icon x-tree-elbow-minus' or @class='x-tree-ec-icon x-tree-elbow-end-minus'])[1]/parent::div/parent::div/following-sibling::ul//img[@class='x-tree-ec-icon x-tree-elbow-minus' or @class='x-tree-ec-icon x-tree-elbow-end-minus'])[2]/parent::div/parent::div/following-sibling::ul/li/div)[" + l5 + "]")).getAttribute("ext:tree-node-id");
														                            
												                             }catch(Exception f){
												                            	 System.out.println("GUID not found");
												                             }
											                             }
											                             
											                             if(webLinkPresense){
											                            	 try{
											                                		webelementLink.click();
											                                		brainHoneyUrl = brainHoneyUrl1();	
											                                	}catch(Exception e){
											                                		try{
											                                			webelementLink.click();
											                                			brainHoneyUrl = brainHoneyUrl1();	
												                                	}catch(Exception f){
												                                		System.out.println("Web Link not found");
												                                	}
											                                	}		
											                             }										                            
											                             									                             
											                             level4Resources.add(title);
											                             brainHonneylinkNameMap.put(GUID.trim(), title);
											                             brainHonneyUrlMap.put(GUID.trim(), brainHoneyUrl);
						                                            }

						                                            brainHonneylessonLevel4FolderResourceHashMap.put(String.valueOf(level1folder-1) + "." + String.valueOf(l2-1) + "." + String.valueOf(l3-1) + "." + String.valueOf(l4-1), level4Resources);
						                                            dvr.findElement(By.xpath("(((//div[@class='x-tree-root-node']/li)[" + level1folder + "]/ul/li/div//img[@class='x-tree-ec-icon x-tree-elbow-minus' or @class='x-tree-ec-icon x-tree-elbow-end-minus'])[1]/parent::div/parent::div/following-sibling::ul//img[@class='x-tree-ec-icon x-tree-elbow-minus' or @class='x-tree-ec-icon x-tree-elbow-end-minus'])[2]")).click();
						                                        }else {
						                                            throw new RuntimeException("Not Implemented beyound level 5");
						                                        }
					                                    	}
					                                    brainHonneylessonLevel3FolderResourceHashMap.put(String.valueOf(level1folder-1) + "." + String.valueOf(l2-1) + "." + String.valueOf(l3-1), level4FolderList);
						                                   
					                                    dvr.findElement(By.xpath("((//div[@class='x-tree-root-node']/li)[" + level1folder + "]/ul/li/div//img[@class='x-tree-ec-icon x-tree-elbow-minus' or @class='x-tree-ec-icon x-tree-elbow-end-minus'])[1]/parent::div/parent::div/following-sibling::ul//img[@class='x-tree-ec-icon x-tree-elbow-minus' or @class='x-tree-ec-icon x-tree-elbow-end-minus']")).click();
					 	                               
					                               }else{
					                            	  
					                            	   int level4LinkSize;
					                                    try {
					                                        dvr.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
					                                        level4LinkSize = dvr.findElements(By.xpath("((//div[@class=\"x-tree-root-node\"]/li)[" + level1folder + "]/ul/li/div//img[@class=\"x-tree-ec-icon x-tree-elbow-minus\" or @class=\"x-tree-ec-icon x-tree-elbow-end-minus\"])[1]/parent::div/parent::div/following-sibling::ul//img[@class=\"x-tree-ec-icon x-tree-elbow-minus\" or @class=\"x-tree-ec-icon x-tree-elbow-end-minus\"]/parent::div/parent::div/following-sibling::ul//a/span")).size();
					                                        dvr.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
					                                    } catch (Exception e) {
					                                        level4LinkSize = 0;
					                                        dvr.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
					                                    }
					                                    
					                                    ArrayList level3Resources = new ArrayList();
					                                 
					                                    
					                                    for (int l4 = 1; l4 <= level4LinkSize; l4++) {
					                                    	 String title = "";
					                                    	 String brainHoneyUrl = "";
								                             WebElement webelementLink = null;
								                             String GUID = "";
								                             boolean webLinkPresense = false;
								                             try{
								                            	 webelementLink = dvr.findElement(By.xpath("(((//div[@class=\"x-tree-root-node\"]/li)[" + level1folder + "]/ul/li/div//img[@class=\"x-tree-ec-icon x-tree-elbow-minus\" or @class=\"x-tree-ec-icon x-tree-elbow-end-minus\"])[1]/parent::div/parent::div/following-sibling::ul//img[@class=\"x-tree-ec-icon x-tree-elbow-minus\" or @class=\"x-tree-ec-icon x-tree-elbow-end-minus\"]/parent::div/parent::div/following-sibling::ul//a/span)[" + l4 + "]"));
								                            	 webLinkPresense = true;
								                             }catch(Exception e){
								                            	 try{
								                            		 webelementLink = dvr.findElement(By.xpath("(((//div[@class=\"x-tree-root-node\"]/li)[" + level1folder + "]/ul/li/div//img[@class=\"x-tree-ec-icon x-tree-elbow-minus\" or @class=\"x-tree-ec-icon x-tree-elbow-end-minus\"])[1]/parent::div/parent::div/following-sibling::ul//img[@class=\"x-tree-ec-icon x-tree-elbow-minus\" or @class=\"x-tree-ec-icon x-tree-elbow-end-minus\"]/parent::div/parent::div/following-sibling::ul//a/span)[" + l4 + "]"));
								                            		 webLinkPresense = true;
									                             }catch(Exception f){
									                            	 System.out.println("Web Link not found");
									                             }
								                             }
								                             
								                             //                            	 title = dvr.findElement(By.xpath("(((//div[@class=\"x-tree-root-node\"]/li)[" + level1folder + "]/ul/li/div//img[@class=\"x-tree-ec-icon x-tree-elbow-minus\" or @class=\"x-tree-ec-icon x-tree-elbow-end-minus\"])[1]/parent::div/parent::div/following-sibling::ul//img[@class=\"x-tree-ec-icon x-tree-elbow-minus\" or @class=\"x-tree-ec-icon x-tree-elbow-end-minus\"]/parent::div/parent::div/following-sibling::ul//a/span)[" + l4 + "]")).getText();
								                             title = webelementLink.getText();
								                             try{
								                            	 GUID = dvr.findElement(By.xpath("(((//div[@class=\"x-tree-root-node\"]/li)[" + level1folder + "]/ul/li/div//img[@class=\"x-tree-ec-icon x-tree-elbow-minus\" or @class=\"x-tree-ec-icon x-tree-elbow-end-minus\"])[1]/parent::div/parent::div/following-sibling::ul//img[@class=\"x-tree-ec-icon x-tree-elbow-minus\" or @class=\"x-tree-ec-icon x-tree-elbow-end-minus\"]/parent::div/parent::div/following-sibling::ul/li/div)[" + l4 + "]")).getAttribute("ext:tree-node-id");
									                             
								                             }catch(Exception e){
								                            	 try{
								                            		 GUID = dvr.findElement(By.xpath("(((//div[@class=\"x-tree-root-node\"]/li)[" + level1folder + "]/ul/li/div//img[@class=\"x-tree-ec-icon x-tree-elbow-minus\" or @class=\"x-tree-ec-icon x-tree-elbow-end-minus\"])[1]/parent::div/parent::div/following-sibling::ul//img[@class=\"x-tree-ec-icon x-tree-elbow-minus\" or @class=\"x-tree-ec-icon x-tree-elbow-end-minus\"]/parent::div/parent::div/following-sibling::ul/li/div)[" + l4 + "]")).getAttribute("ext:tree-node-id");
										                             
									                             }catch(Exception f){
									                            	 System.out.println("GUID not found");
									                             }
								                             }
								                             
								                             if(webLinkPresense){
								                            	 try{
								                                		webelementLink.click();
								                                		brainHoneyUrl = brainHoneyUrl1();	
								                                	}catch(Exception e){
								                                		try{
								                                			webelementLink.click();
								                                			brainHoneyUrl = brainHoneyUrl1();	
									                                	}catch(Exception f){
									                                		System.out.println("Web Link not found");
									                                	}
								                                	}
								                                }
								                             								                             
								                             level3Resources.add(title);
								                             brainHonneylinkNameMap.put(GUID.trim(), title);
								                             brainHonneyUrlMap.put(GUID.trim(), brainHoneyUrl);
					                                    }
					                                    brainHonneylessonLevel3FolderResourceHashMap.put(String.valueOf(level1folder-1) + "." + String.valueOf(l2-1) + "." + String.valueOf(l3-1), level3Resources);
					                                    dvr.findElement(By.xpath("((//div[@class=\"x-tree-root-node\"]/li)[" + level1folder + "]/ul/li/div//img[@class=\"x-tree-ec-icon x-tree-elbow-minus\" or @class=\"x-tree-ec-icon x-tree-elbow-end-minus\"])[1]/parent::div/parent::div/following-sibling::ul//img[@class=\"x-tree-ec-icon x-tree-elbow-minus\" or @class=\"x-tree-ec-icon x-tree-elbow-end-minus\"]")).click();
					                               }
				                        	}
				                        	dvr.findElement(By.xpath("((//div[@class=\"x-tree-root-node\"]/li)[" + level1folder + "]/ul/li/div//img[@class=\"x-tree-ec-icon x-tree-elbow-minus\" or @class=\"x-tree-ec-icon x-tree-elbow-end-minus\"])[1]")).click();
				                        	 brainHonneylessonLevel2FolderResourceHashMap.put(String.valueOf(level1folder-1) + "." + String.valueOf(l2-1), level3FolderList); 
				                        }else{
				                        	 int level3LinkSize;
					                            try {
					                                dvr.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
					                                level3LinkSize = dvr.findElements(By.xpath("((//div[@class=\"x-tree-root-node\"]/li)[" + level1folder + "]/ul/li/div//img[@class=\"x-tree-ec-icon x-tree-elbow-minus\" or @class=\"x-tree-ec-icon x-tree-elbow-end-minus\"])[1]/parent::div/parent::div/following-sibling::ul//a/span")).size();
					                                dvr.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
					                            } catch (Exception e) {
					                                level3LinkSize = 0;
					                                dvr.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
					                            }
					                            
					                            ArrayList level4Resources = new ArrayList();
					                           
					                            System.out.println("level3LinkSize "+level3LinkSize);
					                            for (int l3 = 1; l3 <= level3LinkSize; l3++) {
					                            	String title = "";
					                            	String brainHoneyUrl = "";
					                            	WebElement webelementLink = null;
					                            	String GUID = "";
					                            	boolean webLinkPresense = false;
					                            	try{
					                            		webelementLink = dvr.findElement(By.xpath("(((//div[@class=\"x-tree-root-node\"]/li)[" + level1folder + "]/ul/li/div//img[@class=\"x-tree-ec-icon x-tree-elbow-minus\" or @class=\"x-tree-ec-icon x-tree-elbow-end-minus\"])[1]/parent::div/parent::div/following-sibling::ul//a/span)[" + l3 + "]"));
					                            		webLinkPresense = true;
					                            		title = webelementLink.getText();
					                            	}catch(Exception e){
					                            		try{
					                            			testScript.scrolltoviewelement("(((//div[@class=\"x-tree-root-node\"]/li)[" + level1folder + "]/ul/li/div//img[@class=\"x-tree-ec-icon x-tree-elbow-minus\" or @class=\"x-tree-ec-icon x-tree-elbow-end-minus\"])[1]/parent::div/parent::div/following-sibling::ul//a/span)[" + l3 + "]");
						                            		webelementLink = dvr.findElement(By.xpath("(((//div[@class=\"x-tree-root-node\"]/li)[" + level1folder + "]/ul/li/div//img[@class=\"x-tree-ec-icon x-tree-elbow-minus\" or @class=\"x-tree-ec-icon x-tree-elbow-end-minus\"])[1]/parent::div/parent::div/following-sibling::ul//a/span)[" + l3 + "]"));
						                            		webLinkPresense = true;
						                            		title = webelementLink.getText();
						                            	}catch(Exception f){
						                            		System.out.println("WebLink not found");
						                            	}
					                            	}
					                            	
					                            	
					                            	try{
					                            		GUID = dvr.findElement(By.xpath("(((//div[@class=\"x-tree-root-node\"]/li)[" + level1folder + "]/ul/li/div//img[@class=\"x-tree-ec-icon x-tree-elbow-minus\" or @class=\"x-tree-ec-icon x-tree-elbow-end-minus\"])[1]/parent::div/parent::div/following-sibling::ul/li/div)[" + l3 + "]")).getAttribute("ext:tree-node-id");
						                                	
					                            	}catch(Exception e){
					                            		try{
					                            			GUID = dvr.findElement(By.xpath("(((//div[@class=\"x-tree-root-node\"]/li)[" + level1folder + "]/ul/li/div//img[@class=\"x-tree-ec-icon x-tree-elbow-minus\" or @class=\"x-tree-ec-icon x-tree-elbow-end-minus\"])[1]/parent::div/parent::div/following-sibling::ul/li/div)[" + l3 + "]")).getAttribute("ext:tree-node-id");
							                                
						                            	}catch(Exception f){
						                            		System.out.println("GUID not found");
						                            	}
					                            	}
					                                System.out.println("GUID "+GUID);
					                                					                               
					                                testScript.threadSleep(5);
					                                if(webLinkPresense){
					                                	try{
					                                		webelementLink.click();
					                                		brainHoneyUrl = brainHoneyUrl1();	
					                                	}catch(Exception e){
					                                		try{
					                                			dvr.findElement(By.xpath("(((//div[@class=\"x-tree-root-node\"]/li)[" + level1folder + "]/ul/li/div//img[@class=\"x-tree-ec-icon x-tree-elbow-minus\" or @class=\"x-tree-ec-icon x-tree-elbow-end-minus\"])[1]/parent::div/parent::div/following-sibling::ul//a/span)[" + l3 + "]")).click();
			//		                                			webelementLink.click();
					                                			brainHoneyUrl = brainHoneyUrl1();	
						                                	}catch(Exception f){
						                                		System.out.println("Web Link not found");
						                                	}
					                                	}	                                	
					                                	
					                                }
					                                
					                                				                                
					                                level4Resources.add(title);
					                                brainHonneylinkNameMap.put(GUID.trim(), title);			                              
					                                brainHonneyUrlMap.put(GUID.trim(), brainHoneyUrl);
					                                dvr.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
					                            }
					                           
					                            brainHonneylessonLevel2FolderResourceHashMap.put(String.valueOf(level1folder-1) + "." + String.valueOf(l2-1), level4Resources);
					                            dvr.findElement(By.xpath("((//div[@class=\"x-tree-root-node\"]/li)[" + level1folder + "]/ul/li/div//img[@class=\"x-tree-ec-icon x-tree-elbow-minus\" or @class=\"x-tree-ec-icon x-tree-elbow-end-minus\"])[1]")).click();
					                        System.out.println("All links of this page done");
				                        }
			                    }
			                    
			                    dvr.findElement(By.xpath("(//div[@class=\"x-tree-root-node\"]/li)[" + level1folder + "]/div//img[@class=\"x-tree-ec-icon x-tree-elbow-minus\" or @class=\"x-tree-ec-icon x-tree-elbow-end-minus\"]")).click();
				               System.out.println("All links of this folder done"); 
			        	}
							brainHonneylessonLevel1FolderResourceHashMap.put(String.valueOf(level1folder-1), level2FolderList);
						}			
						
						
//				System.out.println("level1FolderList "+level1FolderList);
				}else{
					System.out.println("Folder Structure not found in Brainhoney");
				}
				
				
			}else{
				System.out.println("View Button not found");
				
			}
			
		}else{
			
		}
		
		System.out.println("brainHonneylinkNameMap "+brainHonneylinkNameMap);
		System.out.println("brainHonneyUrlMap "+brainHonneyUrlMap); 
		
		
/*		int rowvalue = 1;
		int mainFolderCol = 0;
		int level2FolderCol =1;
		int level3FolderCol =2;
		int level4FolderCol = 3;
		int lessonlevel4Roursorce = 4;

		testScript.createSheet("BrainHoney Links");
		try{
			
		
		for(int i = 0 ;i<level1FolderList.size();i++){				

			testScript.worksheet.createRow(rowvalue).createCell(mainFolderCol).setCellValue(String.valueOf(level1FolderList.get(i)));
		    rowvalue++;
		    for (HashMap.Entry<String, ArrayList> me : brainHonneylessonLevel1FolderResourceHashMap.entrySet()) {
		    	String key = me.getKey();
		    	if(String.valueOf(i).equals(key)){
		    		ArrayList<String> valueList = me.getValue();
		    		for(int j = 0 ;j<valueList.size();j++){
		    			String s ="";
					  	s = valueList.get(j);

					  	testScript.worksheet.createRow(rowvalue).createCell(level2FolderCol).setCellValue(s);
						  rowvalue++;
						  for(HashMap.Entry<String, ArrayList> me2 : brainHonneylessonLevel2FolderResourceHashMap.entrySet()){
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
								  		for(HashMap.Entry<String, ArrayList> me3 : brainHonneylessonLevel3FolderResourceHashMap.entrySet()){
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
											  			for(HashMap.Entry<String, ArrayList> me4 : brainHonneylessonLevel4FolderResourceHashMap.entrySet()){
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
	*/	
		
	/*	ArrayList missingLinkData = new ArrayList();
		ArrayList extraLinkData = new ArrayList();
		
		
		testScript.worksheet.createRow(0).createCell(0).setCellValue("BrainHoney Links");
		
		ArrayList  tempInnerLinkArrayList =  new ArrayList();			
		for(int folderNumber = 0;folderNumber < innerLinkArrayList.size();folderNumber++){
			String folderName = (String) innerLinkArrayList.get(folderNumber);
			if(folderName.contains(" ")){
				folderName = folderName.replace(" ", "");
			}
			tempInnerLinkArrayList.add(folderName);
		}
		
		ArrayList  tempLevel1FolderList =  new ArrayList();
		for(int folderNumber = 0;folderNumber < level1FolderList.size();folderNumber++){
			String folderName = (String) level1FolderList.get(folderNumber);
			if(folderName.contains(" ")){
				folderName = folderName.replace(" ", "");
			}
			tempLevel1FolderList.add(folderName);
		}
		
		
		
		for(int i = 0;i< innerLinkArrayList.size();i++){

			String text1  = String.valueOf(innerLinkArrayList.get(i));
			String tempInnerLinkText = String.valueOf(tempInnerLinkArrayList.get(i));
			
//			if(level1FolderList.contains(text1)){
			if(tempLevel1FolderList.contains(tempInnerLinkText)){
//				int index = level1FolderList.indexOf(text1);
				int index = tempLevel1FolderList.indexOf(tempInnerLinkText);
				
				for(HashMap.Entry<String,ArrayList> entry1 : hashMap.entrySet()){
					String key1 = entry1.getKey();
	
					if(key1.equals(String.valueOf(i))){
						ArrayList<String> value1 = entry1.getValue();
						ArrayList<String> tempvalue1 = getListWithoutSpace(value1);
					
						for(HashMap.Entry<String,ArrayList> bentry1 : brainHonneylessonLevel1FolderResourceHashMap.entrySet()){
							String bkey1 = bentry1.getKey();
							if(bkey1.equals(String.valueOf(index))){
								ArrayList<String> bvalue1 = bentry1.getValue();										
								ArrayList<String> tempbvalue1 = getListWithoutSpace(bvalue1);
								
								if(bvalue1.size()>0){									
									for (int j = 0; j < tempvalue1.size(); j++) {
										if(tempbvalue1.contains(tempvalue1.get(j))){
											int index2 = tempbvalue1.indexOf(tempvalue1.get(j));
//											int index2 = bvalue1.indexOf(value1.get(j));
										
											for(HashMap.Entry<String,ArrayList> entry2 : level2FolderMap.entrySet()){
												String key2 = entry2.getKey();
		
												if(key2.equals(String.valueOf(i)+"."+String.valueOf(j))){
													ArrayList<String> value2 = entry2.getValue();
													ArrayList<String> tempvalue2 = getListWithoutSpace(value2);
											
													for(HashMap.Entry<String,ArrayList> bentry2 : brainHonneylessonLevel2FolderResourceHashMap.entrySet()){
														String bkey2 = bentry2.getKey();
														if(bkey2.equals(String.valueOf(index)+"."+String.valueOf(index2))){
															ArrayList<String> bvalue2 = bentry2.getValue();
															ArrayList<String> tempbvalue2 = getListWithoutSpace(bvalue2);
															
															if(bvalue2.size()>0){																
																for (int k = 0; k < tempvalue2.size(); k++) {
																	if(tempbvalue2.contains(tempvalue2.get(k))){
																		int index3 = tempbvalue2.indexOf(tempvalue2.get(k));
															//				int index3 = bvalue2.indexOf(value2.get(k));
																			
																			for(HashMap.Entry<String,ArrayList> entry3 : lessonLevel4FolderMap.entrySet()){
																					String key3 = entry3.getKey();
																					if(key3.equals((String.valueOf(i)+"."+String.valueOf(j))+"."+String.valueOf(k))){
																						ArrayList<String> value3 = entry3.getValue();
																						ArrayList<String> tempvalue3 = getListWithoutSpace(value3);
																						
																						for(HashMap.Entry<String,ArrayList> bentry3 : brainHonneylessonLevel3FolderResourceHashMap.entrySet()){
																							String bkey3 = bentry3.getKey();
																							if(bkey3.equals(String.valueOf(index)+"."+String.valueOf(index2)+"."+String.valueOf(index3))){
																								ArrayList<String> bvalue3 = bentry3.getValue();
																								ArrayList<String> tempbvalue3 = getListWithoutSpace(bvalue3);
																								
																								if(bvalue3.size()>0){
																									for (int l = 0; l < tempvalue3.size(); l++) {
																											if(tempbvalue3.contains(tempvalue3.get(l))){
																												int index4 = tempbvalue3.indexOf(tempvalue3.get(l));
																										//			int index4 = bvalue3.indexOf(value3.get(l));
																													
																													for(HashMap.Entry<String,ArrayList> entry4 : lessonLevel4FolderResourceHashMap.entrySet()){
																															String key4 = entry4.getKey();
																															if(key4.equals((String.valueOf(i)+"."+String.valueOf(j))+"."+String.valueOf(k)+"."+String.valueOf(l))){
																																ArrayList<String> value4 = entry4.getValue();
																																ArrayList<String> tempvalue4 =  getListWithoutSpace(value4);
																																
																																for(HashMap.Entry<String,ArrayList> bentry4 : brainHonneylessonLevel4FolderResourceHashMap.entrySet()){
																																	String bkey4 = bentry4.getKey();
																																	if(bkey4.equals(String.valueOf(index)+"."+String.valueOf(index2)+"."+String.valueOf(index3)+"."+String.valueOf(index4))){
																																		ArrayList<String> bvalue4 = bentry4.getValue();
																																		ArrayList<String> tempbvalue4 = getListWithoutSpace(bvalue4);
																																		
																																		if(bvalue4.size()>0){
																																			for (int m = 0; m < tempvalue4.size(); m++) {
																																					if(tempbvalue4.contains(tempvalue4.get(m))){							
																																					}else{																																							
																																						missingLinkData.add(text1+"||"+value1.get(j)+"||"+value2.get(k)+"||"+value3.get(l)+"||"+value4.get(m));
																																					}
																																			}
																																			if(bvalue4.size()==tempbvalue4.size()){
																																				ArrayList<String> al = new ArrayList(tempvalue4);
																																				ArrayList<String> bal = new ArrayList(tempbvalue4);
																																				for(int dup = 0;dup<bal.size();dup++){
																																						if(!al.contains(bal.get(dup))){
																																							int indexOfdup = bal.indexOf(bal.get(dup));
																																							extraLinkData.add(text1+"||"+value1.get(j)+"||"+value2.get(k)+"||"+value3.get(l)+"||"+bvalue4.get(indexOfdup));																																				
																																						}
																																				}
																																			}
																																		}else{	
																																			ArrayList<String> al = new ArrayList();
																																			al = value4;
																																			for(int el = 0; el<al.size();el++){																																					
																																				missingLinkData.add(text1+"||"+value1.get(j)+"||"+value2.get(k)+"||"+value3.get(l)+"||"+al.get(el));
																																			}
																																		}
																																	}
																																}
																															}
																													}
																											}else{
																												
																												missingLinkData.add(text1+"||"+value1.get(j)+"||"+value2.get(k)+"||"+value3.get(l));
																											}
																									}if(bvalue3.size()==tempbvalue3.size()){
																									ArrayList<String> al = new ArrayList(tempvalue3);
																									ArrayList<String> bal = new ArrayList(tempbvalue3);
																									for(int dup = 0;dup<bal.size();dup++){
																											if(!al.contains(bal.get(dup))){
																												int indexOfdup = bal.indexOf(bal.get(dup));
																												extraLinkData.add(text1+"||"+value1.get(j)+"||"+value2.get(k)+"||"+bvalue3.get(indexOfdup));
																											}
																									}
																								}	
																								}else{
																									ArrayList<String> al = new ArrayList();
																									al = value3;
																									for(int oo = 0;oo<al.size();oo++){
																											missingLinkData.add(text1+"||"+value1.get(j)+"||"+value2.get(k)+"||"+al.get(oo));
																									}
																								}
																							}
																						}
																					}
																			}																			
																	}else{																																				
																		missingLinkData.add(text1+"||"+value1.get(j)+"||"+value2.get(k));																			
																	}
																}
																if(bvalue2.size()==tempbvalue2.size()){
																	ArrayList<String> al = new ArrayList(tempvalue2);
																	ArrayList<String> bal = new ArrayList(tempbvalue2);
																	for(int dup = 0;dup<bal.size();dup++){
																			if(!al.contains(bal.get(dup))){
																				int indexOfdup = bal.indexOf(bal.get(dup));																					
																				extraLinkData.add(text1+"||"+value1.get(j)+"||"+bvalue2.get(indexOfdup));
																			}
																	}
																}																																		
															}else{		
																ArrayList<String> al = new ArrayList();
																al = value2;
																for(int oo = 0;oo<al.size();oo++){																
																	missingLinkData.add(text1+"||"+value1.get(j)+"||"+al.get(oo));																		
																}
															}
														}
													}
												}
											}
										}
										else{												
											missingLinkData.add(text1+"||"+value1.get(j));										
										}
									}
									if(bvalue1.size()==tempbvalue1.size()){
									ArrayList<String> al = new ArrayList(tempvalue1);
									ArrayList<String> bal = new ArrayList(tempbvalue1);
									for(int dup = 0;dup<bal.size();dup++){
											if(!al.contains(bal.get(dup))){
												int indexOfdup = bal.indexOf(bal.get(dup));
												extraLinkData.add(text1+"||"+bvalue1.get(indexOfdup));
											}
									}
								}
									
								}else{										
									ArrayList<String> al = new ArrayList();
									al = value1;
									for(int q= 0;q<al.size();q++){							
										missingLinkData.add(text1+"||"+al.get(q));											
									}	
								}
							}
						}
					}
				}
			}else{				
			missingLinkData.add(text1);				
			}
			
		}
		
		if(level1FolderList.size()==tempLevel1FolderList.size()){
			ArrayList<String> al = new ArrayList(tempInnerLinkArrayList);
			ArrayList<String> bal = new ArrayList(tempLevel1FolderList);
			
			for(int dup = 0;dup<bal.size();dup++){
				if(!al.contains(bal.get(dup))){
						int indexOfDup = bal.indexOf(bal.get(dup));
						extraLinkData.add(level1FolderList.get(indexOfDup));
				}
			}
		}
		
	*/	
		
		/*if(tempLevel1FolderList.size()>tempInnerLinkArrayList.size()){
			ArrayList<String> al = new ArrayList();
			ArrayList<String> bal = new ArrayList();
			bal = tempLevel1FolderList;
			al = tempInnerLinkArrayList;
			
			for(int i = 0;i<bal.size();i++){
				boolean folderPresent = false;
					for(int j = 0;j<al.size();i++){
							if(bal.get(i).equals(al.get(j))){
								folderPresent = true;									
							}							
					}
					if(!folderPresent){
						extraLinkData.add(level1FolderList.get(i));							
					}					
			}
		}
		*/
		
		
	

	/*	testScript.createSheet("Missing Links");
		
		int level1 = 0;
		int level2 = 1;
		int level3 = 2;
		
		testScript.worksheet.createRow(0).createCell(0).setCellValue("Missing Links");
		try{			
		
			for(int i = 0 ;i<missingLinkData.size();i++){
				String linkName = "";
				linkName = (String) missingLinkData.get(i);				
				testScript.worksheet.createRow(i+1).createCell(level1).setCellValue(linkName);
		}
		}catch(Exception e){			
			
		}

		testScript.createSheet("Extra Links");
		
		testScript.worksheet.createRow(0).createCell(0).setCellValue("Extra Links");
		try{
		for(int i = 0 ;i<extraLinkData.size();i++){
			String linkName = "";
			linkName = (String) extraLinkData.get(i);
			testScript.worksheet.createRow(i+1).createCell(level1).setCellValue(linkName);
		}
		}catch(Exception e){
			
			
		}
		testScript.writeExcel(hrwCourse);			
*/		
		
		
		
		
		
		testScript.createSheet("Brain Honey url Links");
		
		int n2 = 0;
		for(HashMap.Entry<String,String> brainHonneyUrlMap1 : brainHonneyUrlMap.entrySet()){
			testScript.worksheet.createRow(n2).createCell(0).setCellValue(brainHonneyUrlMap1.getKey());
			testScript.worksheet.getRow(n2).createCell(1).setCellValue(brainHonneyUrlMap1.getValue());		
			n2++;
		}
		
		testScript.createSheet("Brain Honey Links Name");
		
		int n3 = 0;
		for(HashMap.Entry<String,String> brainHonneyUrlMap1Temp : brainHonneylinkNameMap.entrySet()){
			testScript.worksheet.createRow(n3).createCell(0).setCellValue(brainHonneyUrlMap1Temp.getKey());
			testScript.worksheet.getRow(n3).createCell(1).setCellValue(brainHonneyUrlMap1Temp.getValue());		
			n3++;
		}
		
		testScript.createSheet("Mapping HRW with Brain Honey Result");
		
		int rowNum = 1;
		for(HashMap.Entry<String,String> hrwLinkNameMap : linkNameHashMap.entrySet()){		
			 String hrwLinkKey = "";
			 String hrwLinkValue = "";
			 String hrwUrlValue = "";
			 String brLinkValue = "";
			 String brUrlValue = "";
			 String mdsUrl = "";
			 
			 hrwLinkKey =  hrwLinkNameMap.getKey().trim();
			 testScript.worksheet.createRow(rowNum).createCell(0).setCellValue(hrwLinkKey);
			 hrwLinkValue = hrwLinkNameMap.getValue().trim();
			 testScript.worksheet.getRow(rowNum).createCell(1).setCellValue(hrwLinkValue);
			 
			 boolean hrwUrlMapCheck = false;
			 for(HashMap.Entry<String,String> hrwUrlMap : lunchedUrlHashMap.entrySet()){
				 String hrwUrlKey =  hrwUrlMap.getKey().trim();
				 if(hrwLinkKey.equals(hrwUrlKey)){					 
					 hrwUrlValue = hrwUrlMap.getValue().trim();
					 testScript.worksheet.getRow(rowNum).createCell(2).setCellValue(hrwUrlValue);
					 hrwUrlMapCheck = true;
					 break;
				 }
			 }
			 
// 			 boolean brLinkNameEntryCheck = false; 
			 boolean brUrlEntryCheck = false;	
		
			 for(HashMap.Entry<String,String> brLinkNameEntry : brainHonneylinkNameMap.entrySet()){
				 if(brUrlEntryCheck){
					 break;
				 }
				 String brLinkKey = "";
				 brLinkKey = brLinkNameEntry.getKey().trim();
				 if(brLinkKey.contains("HMH_")){
					 brLinkKey = brLinkKey.replace("HMH_", "").trim();
				 }
				 if(hrwLinkKey.equals(brLinkKey)){
					 testScript.worksheet.getRow(rowNum).createCell(3).setCellValue(brLinkKey);					 
					 brLinkValue =  brLinkNameEntry.getValue();
					 testScript.worksheet.getRow(rowNum).createCell(4).setCellValue(brLinkValue);
//					 brLinkNameEntryCheck = true;
					 for(HashMap.Entry<String,String> brUrlEntry : brainHonneyUrlMap.entrySet()){
						 String brUrlKey = "";
						 brUrlKey = brUrlEntry.getKey().trim();
						 if(brUrlKey.contains("HMH_")){
							 brUrlKey = brUrlKey.replace("HMH_", "").trim();
						 }
						 if(hrwLinkKey.equals(brUrlKey)){
							 brUrlValue =  brUrlEntry.getValue();
							 testScript.worksheet.getRow(rowNum).createCell(5).setCellValue(brUrlValue);
							 brUrlEntryCheck = true;
							 break;
						 }	
					 
					 }					
				 }				 
			 } 
			 
			 boolean mdsGuidCheck = false;
			 for(HashMap.Entry<String,String> mdsData : mdsMapData.entrySet()){
				 String mdsKey =  mdsData.getKey().trim();
				 if(hrwLinkKey.equals(mdsKey)){
					 mdsUrl = mdsData.getValue().trim();
					 testScript.worksheet.getRow(rowNum).createCell(6).setCellValue(mdsKey);
					 mdsUrl = "https://my.hrw.com"+mdsUrl;
					 testScript.worksheet.getRow(rowNum).createCell(7).setCellValue(mdsUrl);
					 mdsGuidCheck = true;
					 break;
				 }
			 }	
			 
			 if(brUrlEntryCheck){
				 System.out.println("hrwUrlValue "+hrwUrlValue);
				 System.out.println("hrwUrlValue "+hrwUrlValue);
				 if(hrwUrlValue.equals(brUrlValue)){
					 testScript.worksheet.getRow(rowNum).createCell(8).setCellValue("Pass");
				 }else{
					 testScript.worksheet.getRow(rowNum).createCell(8).setCellValue("Fail"); 
				 }
			 }else{
				 testScript.worksheet.getRow(rowNum).createCell(8).setCellValue("Not Found");
			 }
			 
			 if(mdsGuidCheck){
				 System.out.println("hwUrlV "+hrwUrlValue);
				 System.out.println("mdsUrl "+mdsUrl);
				 if(hrwUrlValue.equals(mdsUrl)){
					 testScript.worksheet.getRow(rowNum).createCell(9).setCellValue("Pass");
				 }else{
					 testScript.worksheet.getRow(rowNum).createCell(9).setCellValue("Fail"); 
				 }				
			 }else{
				 testScript.worksheet.getRow(rowNum).createCell(9).setCellValue("Not Found"); 
			 }
			 
			 rowNum++;
		}
		
		testScript.writeExcel(hrwCourse+"urlmapping");	
		
		}
				
		catch(Exception e){
		System.out.println("Course link not found");
		testScript.writeExcel(hrwCourse+"urlmapping");	
		}
		}catch(Exception e){
			System.out.println("Administrator icon not found");
		}
		}catch(Exception e){
			System.out.println("User Name and Password Element not found"); 
			
		}
	System.out.println("brainhonny done");
	}
	
	
	public void level2Folder(List level1ResourceList){
		
		int sizeOflevel1Folder = level1ResourceList.size();
	}
	
	
	public void doubleClick(WebElement element) {
		try {
			Actions action = new Actions(dvr).doubleClick(element);
			action.build().perform();

//			System.out.println("Double clicked the element");
		} catch (StaleElementReferenceException e) {
			System.out.println("Element is not attached to the page document "
					+ e.getStackTrace());
		} catch (NoSuchElementException e) {
			System.out.println("Element " + element + " was not found in DOM "
					+ e.getStackTrace());
		} catch (Exception e) {
			System.out.println("Element " + element + " was not clickable "
					+ e.getStackTrace());
		}
	}
	
	private ArrayList<String> getListWithoutSpace(ArrayList<String> list){
		ArrayList<String>  tempList =  new ArrayList<String>();			
		for(int folderNumber = 0;folderNumber < list.size();folderNumber++){
			String folderName = (String) list.get(folderNumber);
			if(folderName.contains(" ")){
				folderName = folderName.replace(" ", "");
			}
			tempList.add(folderName);
		}			
		return tempList;
	}
	
	private String brainHoneyUrl() throws InterruptedException{		
//		System.out.println("brainHoneyUrl");
		String brainHoneyURl = "";
//		testScript.threadSleep(10);		 
		 dvr.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		 dvr.switchTo().frame(0);
		 dvr.switchTo().frame(0);
		try {
			brainHoneyURl = dvr.findElement(By.xpath("//source")).getAttribute("src");
		} catch (Exception e) {
			try {
				brainHoneyURl = dvr.findElement(By.xpath("//base")).getAttribute("href");
			} catch (Exception e2) {
				try {
					brainHoneyURl = dvr.findElement(By.xpath("//embed")).getAttribute("src");
				} catch (Exception e3) {
					try{
						brainHoneyURl = dvr.findElement(By.xpath("//input[@name='custom_resource_url']")).getAttribute("value");
					}catch(Exception e4){
//						System.out.println("URL not found");
					}
				}
			}
		}dvr.switchTo().defaultContent(); 
		 dvr.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
//         System.out.println(brainHoneyURl);
		return brainHoneyURl;	
	}
	
	private String brainHoneyUrl1() throws InterruptedException, IOException{
//		System.out.println("brainHoneyUrl1");
		String brainHoneyURl = "";
		testScript.threadSleep(15);		 
		 dvr.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		 dvr.switchTo().frame(0);
		 String link = "";
		 boolean frameLoaded = false;
		 
		 WebDriverWait wait1 = new WebDriverWait(dvr, 180);
		 try{
				wait1.until(ExpectedConditions.visibilityOf(dvr.findElement(By.xpath("//iframe"))));
				 link = dvr.findElement(By.xpath("//iframe")).getAttribute("src");
				 frameLoaded = true;
		 }catch(Exception e){
			 try{
					wait1.until(ExpectedConditions.visibilityOf(dvr.findElement(By.xpath("//iframe"))));
					 link = dvr.findElement(By.xpath("//iframe")).getAttribute("src");
					 frameLoaded = true;
			 }catch(Exception e1){
				 System.out.println("iFrame not loaded");
			 }
		 }
//		 System.out.println("src "+link);
		 dvr.switchTo().defaultContent(); 
		 if(frameLoaded){			
			 int oldWindow ;
			 int newWindow ;
			 oldWindow = testScript.countWindow();
			 
			 JavascriptExecutor js = (JavascriptExecutor) dvr; 
			 (js).executeScript("window.open()");
			 
//			 System.out.println("clicked");
			 testScript.threadSleep(2);			
			 newWindow = testScript.countWindow();
//			 System.out.println("oldWindow "+oldWindow);
//			 System.out.println("newWindow "+newWindow);
			 
			 if(newWindow>oldWindow){
				 testScript.switchwindow(1);
				 dvr.get(link);
				 testScript.threadSleep(3);
				 int responseCode;
				 responseCode =  testScript.responsecode(link);
				 if(responseCode == 200 || responseCode == 302 || responseCode == 307 || responseCode == 403){
//					 System.out.println("Page Load");
					 brainHoneyURl = dvr.getCurrentUrl();	
				 }else{
//					 System.out.println("Page Load issue");
				 }				 			
				 dvr.close();
				 testScript.switchwindow(0);				 
			 }
		 }
		 
		 if(link.equals(brainHoneyURl)){
//			 System.out.println("link and brainHoneyURl new tab url same");
			 brainHoneyURl  = brainHoneyUrl();
		 }
		 
		 
		 dvr.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
         System.out.println(brainHoneyURl);
		return brainHoneyURl;	
	}
	
	
	
}

