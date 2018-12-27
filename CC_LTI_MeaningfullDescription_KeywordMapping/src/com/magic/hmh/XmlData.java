package com.magic.hmh;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XmlData {

	public Map<String,LinkedHashMap<String,String>> readManifestXmlV13(String folderName,String file){
		
		Map<String,LinkedHashMap<String,String>> xmlLinkedHashMap = new LinkedHashMap<String,LinkedHashMap<String,String>>();
		
			try {				
		
				File fXmlFile = new File("./"+folderName+"/"+file);
				System.out.println("file "+file);
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
				Document doc = dBuilder.parse(fXmlFile);
				doc.getDocumentElement().normalize();
				XPathFactory xPathfactory = XPathFactory.newInstance();
				XPath xpath = xPathfactory.newXPath();
				boolean presenseOfItemCodeInXml= false;
				
					XPathExpression expr = xpath.compile("//item/item");
					NodeList nl = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
					System.out.println("nl "+nl.getLength());

					for (int i = 0; i < nl.getLength(); i++){
					    Node resourceNode = nl.item(i);			
					    String key = "";
					    key = resourceNode.getAttributes().getNamedItem("identifier").getNodeValue();
					    key = key.replaceFirst("HMH_", "");
					    	presenseOfItemCodeInXml = true;				    	
					    //	System.out.println("resources pass");
					    	Element standardNodElement = (Element)resourceNode;
					    	NodeList childNodeOfItem = standardNodElement.getChildNodes();
					  //  	System.out.println("childNodeOfItem "+childNodeOfItem);
					    	for(int j = 0;j<childNodeOfItem.getLength();j++){
					    		Node childNodesName = childNodeOfItem.item(j);
					    //		System.out.println("childNodesName "+childNodesName.getNodeName());
					    		if(childNodesName.getNodeName().equalsIgnoreCase("metadata")){
					    			NodeList metadataNodeList = childNodesName.getChildNodes();
					    			for(int k = 0; k<metadataNodeList.getLength(); k++){
					    				Node childMetaDataNodesName = metadataNodeList.item(k);
					    	//			System.out.println("childMetaDataNodesName "+childMetaDataNodesName.getNodeName());
					    				if(childMetaDataNodesName.getNodeName().equalsIgnoreCase("lomr:lom")){
					    					NodeList metadataChildNodeList = childMetaDataNodesName.getChildNodes();
					    					for(int l = 0;l<metadataChildNodeList.getLength();l++){
					    						Node lchild = metadataChildNodeList.item(l);
							    //				System.out.println("lchild "+lchild.getNodeName());
					    						if(lchild.getNodeName().equalsIgnoreCase("lomr:general")){
					    							NodeList generalNodeList = lchild.getChildNodes();
					    							
					    							LinkedHashMap<String,String> xmlDataLinkedHashMap = new LinkedHashMap<String,String>();
					    							
					    							for(int m = 0;m<generalNodeList.getLength();m++){
					    								Node finalNode = generalNodeList.item(m);
					    								if(finalNode.getNodeName().equalsIgnoreCase("lomr:description")){
					    									Element finalNodElement = (Element)finalNode;
					    									String description = "";
					    									description = finalNodElement.getElementsByTagName("lomr:string").item(0).getTextContent().trim();
					    							//		System.out.println("description "+description);
					    									description = removeSpecialChar(description);
					    									xmlDataLinkedHashMap.put("desc", description);
					    								}else if(finalNode.getNodeName().equalsIgnoreCase("lomr:keyword")){
					    									Element finalNodElement = (Element)finalNode;
					    									String keyword = "";
					    									keyword = finalNodElement.getElementsByTagName("lomr:string").item(0).getTextContent().trim();
					    									keyword = removeSpecialChar(keyword);
					    							//		System.out.println("keyword "+keyword);
					    									xmlDataLinkedHashMap.put("keyword", keyword);
					    								}
					    							}
					    							xmlLinkedHashMap.put(key, xmlDataLinkedHashMap);
					    						}
					    						
					    					}
					    					
					    				}
					    			}
					    		}
					    	}
					}
					return xmlLinkedHashMap;
			    } catch (Exception e) {
				e.printStackTrace();
			    }
			return xmlLinkedHashMap;
		}
	
	
	
	public Map<String,LinkedHashMap<String,String>> readManifestXmlV12(String folderName,String file){
		
		Map<String,LinkedHashMap<String,String>> xmlLinkedHashMap = new LinkedHashMap<String,LinkedHashMap<String,String>>();
		
			try {				
		
				File fXmlFile = new File("./"+folderName+"/"+file);
				System.out.println("file "+file);
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
				Document doc = dBuilder.parse(fXmlFile);
				doc.getDocumentElement().normalize();
				XPathFactory xPathfactory = XPathFactory.newInstance();
				XPath xpath = xPathfactory.newXPath();
				boolean presenseOfItemCodeInXml= false;
				
					XPathExpression expr = xpath.compile("//item/item");
					NodeList nl = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
					System.out.println("nl "+nl.getLength());

					for (int i = 0; i < nl.getLength(); i++){
					    Node resourceNode = nl.item(i);			
					    String key = "";
					    key = resourceNode.getAttributes().getNamedItem("identifier").getNodeValue();
					    key = key.replaceFirst("HMH_", "");
					    	presenseOfItemCodeInXml = true;				    	
					    //	System.out.println("resources pass");
					    	Element standardNodElement = (Element)resourceNode;
					    	NodeList childNodeOfItem = standardNodElement.getChildNodes();
					  //  	System.out.println("childNodeOfItem "+childNodeOfItem);
					    	for(int j = 0;j<childNodeOfItem.getLength();j++){
					    //	for(int j = 0;j<4;j++){
					    		Node childNodesName = childNodeOfItem.item(j);
					    //		System.out.println("childNodesName "+childNodesName.getNodeName());
					    		if(childNodesName.getNodeName().equalsIgnoreCase("metadata")){
					    			NodeList metadataNodeList = childNodesName.getChildNodes();
					    			for(int k = 0; k<metadataNodeList.getLength(); k++){
					    				Node childMetaDataNodesName = metadataNodeList.item(k);
					    	//			System.out.println("childMetaDataNodesName "+childMetaDataNodesName.getNodeName());
					    				if(childMetaDataNodesName.getNodeName().equalsIgnoreCase("lomimscc:lom")){
					    					NodeList metadataChildNodeList = childMetaDataNodesName.getChildNodes();
					    					for(int l = 0;l<metadataChildNodeList.getLength();l++){
					    						Node lchild = metadataChildNodeList.item(l);
							    //				System.out.println("lchild "+lchild.getNodeName());
					    						if(lchild.getNodeName().equalsIgnoreCase("lomimscc:general")){
					    							NodeList generalNodeList = lchild.getChildNodes();
					    							
					    							LinkedHashMap<String,String> xmlDataLinkedHashMap = new LinkedHashMap<String,String>();
					    							
					    							for(int m = 0;m<generalNodeList.getLength();m++){
					    								Node finalNode = generalNodeList.item(m);
					    								if(finalNode.getNodeName().equalsIgnoreCase("lomimscc:identifier")){
					    									NodeList descriptionChildNode = finalNode.getChildNodes();
					    									
					    									String meaningfullDescriptionText = "";
					    									
					    									boolean checkDescription = false;
					    									
					    									for(int descriptionNodeCount = 0; descriptionNodeCount < descriptionChildNode.getLength(); descriptionNodeCount++){
					    										Node descriptionNode = descriptionChildNode.item(descriptionNodeCount);
					    										if(descriptionNode.getTextContent().trim().equalsIgnoreCase("MEANINGFUL_DESCRIPTION")){
					    											checkDescription = true;
					    											continue;
					    										}
					    										if(checkDescription){
					    											if(descriptionNode.getNodeName().equalsIgnoreCase("lomimscc:entry")){
					    												meaningfullDescriptionText = descriptionNode.getTextContent().trim();
					    												meaningfullDescriptionText = removeSpecialChar(meaningfullDescriptionText);
					    												if(!meaningfullDescriptionText.isEmpty()){
					    													xmlDataLinkedHashMap.put("desc", meaningfullDescriptionText);
					    												}
					    												break;
					    											}
					    										}
					    									}
					    								}else if(finalNode.getNodeName().equalsIgnoreCase("lomimscc:keyword")){
					    									NodeList keywordNode = finalNode.getChildNodes();
					    									String keywordStringTagTest = "";				
					    									for(int keyCount = 1; keyCount<keywordNode.getLength()-1;keyCount++){	
					    										String nodeValue = "";
					    										Node keywordStringNode = keywordNode.item(keyCount);
					    										if(keywordStringNode !=null){
						    										nodeValue = keywordStringNode.getTextContent();
						    										if(!nodeValue.isEmpty()){
						    											if(keyCount == keywordNode.getLength()-2){
						    												keywordStringTagTest = keywordStringTagTest+nodeValue;
							    										}else{
							    											keywordStringTagTest = keywordStringTagTest+nodeValue+",";
							    										}
						    										}
					    										}
					    									}
					    									keywordStringTagTest = removeSpecialChar(keywordStringTagTest);
					    									xmlDataLinkedHashMap.put("keyword", keywordStringTagTest);
					    								}
					    							}
					    							xmlLinkedHashMap.put(key, xmlDataLinkedHashMap);
					    						}
					    						
					    					}
					    					
					    				}
					    			}
					    		}
					    	}
					}
					return xmlLinkedHashMap;
			    } catch (Exception e) {
				e.printStackTrace();
			    }
			return xmlLinkedHashMap;
		}
	
	public String removeSpecialChar(String str){
		System.out.println(str);
		ArrayList<String> specialCharList = new ArrayList<String>();
		ArrayList<String> changedCharList = new ArrayList<String>();
		
		specialCharList.add("&quot;");
		specialCharList.add("&#039;");
		
		changedCharList.add("\"");
		changedCharList.add("'");
		
		for(int i = 0;i<specialCharList.size();i++){
			String data = "";
			data = specialCharList.get(i);
			if(str.contains(data)){
				str = str.replaceAll(data, changedCharList.get(i));
			}
		}
		return str;
	}
	
}
