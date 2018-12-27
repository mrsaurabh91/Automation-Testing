package com.magic.main;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.gson.stream.JsonWriter;


public class Cross_Word_Json {

	static LinkedHashMap<String, LinkedHashMap<String, ArrayList<String>>> linkedHashmapLevel1 = new LinkedHashMap<>();
	static LinkedHashMap<String, ArrayList<String>> linkedHashmap = new LinkedHashMap<>();
	static String tableNumber="";

	public static void convertToJson(String docFilePath, String folderPath,LinkedHashMap<String, LinkedHashMap<String, ArrayList<String>>> linkedHashmapLevel1)
			throws FileNotFoundException, IOException, InterruptedException, ParseException {

		for (Map.Entry<String, LinkedHashMap<String, ArrayList<String>>> entry : linkedHashmapLevel1.entrySet()) {
			tableNumber = entry.getKey();
			System.out.println("tableNumber : " + tableNumber);
		}
		
		String templateType="";
		linkedHashmap = linkedHashmapLevel1.get("1");
		// System.out.println(linkedHashmapLevel1);
		
		String jsonFileName = "Json_file_structure\\screen-data.json";

		
		JSONParser parser = new JSONParser();

		Object obj = parser.parse(new FileReader(jsonFileName));

		JSONObject jsonObject = new JSONObject();
		jsonObject = (JSONObject )obj;
		
		JSONObject questionObj = new JSONObject();		
		questionObj=(JSONObject) jsonObject.get("question");
		
		JSONObject questionObjTemp = new JSONObject();		
		questionObjTemp=questionObj;
		
		JSONObject tagsListObj = new JSONObject();		
		tagsListObj=(JSONObject) jsonObject.get("__tagsList");
		
		System.out.println(questionObj.toJSONString());
		
		jsonObject.remove("question");
		
		int count = 0;
		for (Map.Entry<String, ArrayList<String>> entry : linkedHashmap.entrySet()) {
			
			System.out.println("Count of Iteration "+count);

			ArrayList<String> list = entry.getValue();
			System.out.println(entry.getKey() + "----" + list);			
			
	/*		if("TemplateType".equals(entry.getKey().trim())){
				
				String type="";
				for (int j = 0; j < list.size(); j++) {

					type=list.get(j);

				}
				jsonObject.put("type", type);
				
			}
			if("templateName".equals(entry.getKey().trim())){
				
				JSONObject obj2 = (JSONObject) jsonObject.get("start");
				
				String template="";
				for (int j = 0; j < list.size(); j++) {

					template=list.get(j);
				}
				template="<span class=\"english startTitle\">"+template+"</span>";
				
				obj2.put("templateName", template);
				jsonObject.put("start", obj2);
				
			}
			
		*/	
			if (list.size()>0 && entry.getKey().trim().contains("question") ) {
				
				String ques = "question" + count;
				String quesTitle = "question" + count + "Title";
				String quesRow = "question" + count + "Row";
				System.out.println(ques);
				
				
				if (quesTitle.equals(entry.getKey().trim())) {

					JSONObject obj2 = new JSONObject();
					obj2 = questionObjTemp;
					
					System.out.println("Before "+questionObjTemp.toJSONString());
					
					String title="";
					for (int j = 0; j < list.size(); j++) {
						title=list.get(j);
					}
						obj2.put("title", title);
						jsonObject.put(ques, obj2);
						System.out.println("After "+questionObj.toJSONString());
						count++;
				}
				
				
		/*		if (ques.equals(entry.getKey().trim())) {

					JSONObject obj2 = new JSONObject();
					obj2 = (JSONObject) jsonObject.get(ques);
					JSONObject obj3 = new JSONObject();
					obj3 = (JSONObject) obj2.get("answerkey");
					JSONArray array = new JSONArray();

					for (int j = 0; j < list.size(); j++) {
						String str[]=list.get(j).split(",");
						for(int k=0; k<str.length; k++) {
							array.add(str[k].trim());
						}
					}

					obj3.put("answer", array);
					obj2.put("answerkey", obj3);
					jsonObject.put(ques, obj2);
					//System.out.println(JsonWriter.formatJson(jsonObject.toJSONString()));

				}
				
				if (quesRow.equals(entry.getKey().trim())) {

					JSONObject obj2 = (JSONObject) jsonObject.get(ques);
					JSONObject obj4 = (JSONObject) obj2.get("options");

					JSONArray array = new JSONArray();

					for (int j = 0; j < list.size(); j++) {
						String str[]=list.get(j).split(",");
						for(int k=0; k<str.length; k++) {
							array.add(str[k].trim());
						}
					}

					obj4.put("row", array);
					obj2.put("options", obj4);
					jsonObject.put(ques, obj2);
					
					if (count==0) {
						jsonObject.put("path", "question");
					}else {
						jsonObject.put("path"+count, "question");
					}
					jsonObject.putAll(tagsListObj);
					count++;
					System.out.println("--------------------------------------------------------------------------");
					System.out.println(JsonWriter.formatJson(jsonObject.toJSONString()));
				} */
			}
			
		}

		
		String jsonString = jsonObject.toJSONString();
		// System.out.println(JsonWriter.formatJson(jsonString));
		

		try (FileWriter file = new FileWriter(folderPath+"\\example.json")) { // creating file name #game_1.json

			//file.write("window.local_run = true;\r\n" + "ScreenData ="+JsonWriter.formatJson(jsonString));
			file.write(jsonString);
			System.out.println("\nSuccessfully Written JSON Object to File...");
			// System.out.println("\nJSON Object: " + jsonString);
		} catch (Exception e) {
			e.printStackTrace();

		}

	}

}
