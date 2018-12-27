package com.magic.main;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.JOptionPane;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.magic.input.HtmlInputData;
import com.magic.input.Word2Html;
import com.magic.util.ExcelUtils;

public class FirstJsonTool {
	
	static String parentObject="game";
	static String gameType;
	static int answerCount=0;

	
	
	static String folderName="Keyword file";
	static String fileName="Keyword";
	static String sheetName="Sheet1";
	
	static LinkedHashMap<String, String> keywordMap = new LinkedHashMap<>();

	static LinkedHashMap<String, LinkedHashMap<String, ArrayList<String>>> linkedHashmapLevel1 = new LinkedHashMap<>();
	static LinkedHashMap<String, ArrayList<String>> linkedHashmap = new LinkedHashMap<>();
	
	static {
		readKeyWord();
	}

	public static void convertToJson(String docFilePath, String folderPath) throws Exception {

		// String docFilePath = "Dummy_Data.docx";
		String htmlFilePath = Word2Html.createHTMLPage(docFilePath);
		int answerCount = 0;
		// System.out.println(htmlFileName);

		linkedHashmapLevel1 = HtmlInputData.getInputDocFileContentNew(htmlFilePath);
		for (Map.Entry<String, LinkedHashMap<String, ArrayList<String>>> entry : linkedHashmapLevel1.entrySet()) {

			gameType = entry.getKey();
			System.out.println("Game Type : " + gameType);
		}

		linkedHashmap = linkedHashmapLevel1.get("1");

		// System.out.println(linkedHashmap);

		JSONObject jsonObject = new JSONObject();
		JSONObject innerObject = new JSONObject();

		innerObject.put("gameType", gameType);

		jsonObject.put(parentObject, innerObject);

		for (Map.Entry<String, ArrayList<String>> entry : linkedHashmap.entrySet()) {

			String col1Name = columnNameChange(entry.getKey());
			ArrayList<String> list = entry.getValue();
			System.out.println(col1Name + "----" + list);

			if (list.size() == 1) {

				JSONArray array = new JSONArray();
				String str[] = list.get(0).toString().split("\\|");
				int startPoint = 0;
				if (str[0].equals("NA"))
					startPoint = 1;
				else
					startPoint = 0;

				if (str.length < 3) {
					for (int i = startPoint; i < str.length; i++) {
						// str[i] = str[i].trim();

						if (str[i].equalsIgnoreCase("english"))
							str[i] = "en";
						else if (str[i].equalsIgnoreCase("Spanish"))
							str[i] = "es";

						try {
							int op1 = Integer.parseInt(str[i]);
							innerObject.put(col1Name, op1);

						} catch (Exception e) {
							innerObject.put(col1Name, str[i]);
						}

						// innerObject.put(col1Name, str[i]);
					}
					jsonObject.put(parentObject, innerObject);
				} else {
					for (int i = 1; i < str.length; i++) {
						// str[i]=str[i].substring(6, str[i].length()-1);
						array.add(str[i]);
					}
					innerObject.put(col1Name, array);
					jsonObject.put(parentObject, innerObject);
				}
			}

			if (col1Name.equalsIgnoreCase("questionDataV123")) {

				JSONArray array = new JSONArray();

				for (int i = 0; i < list.size(); i++) {

					String str[] = list.get(i).toString().split("\\|");

					for (int k = 1; k < str.length; k++) {
						JSONObject quesObject1 = new JSONObject();
						JSONObject quesObject2 = new JSONObject();

						if (!str[k].equalsIgnoreCase("<br>")) {
							str[k] = str[k].trim();
							// System.out.println("questionDataV123--"+str[k]);

							quesObject1.put("word", str[k]);
							quesObject1.put("title", str[k] + " /h/");
							quesObject1.put("audio", "assets/audios/gameSpecific/" + str[k] + ".mp3");
							quesObject1.put("image", "assets/images/gameSpecific/" + str[k] + ".png");
							quesObject1.put("zoneId", 1);

							quesObject2.put("word", "Not " + str[k]);
							quesObject2.put("title", "Not " + str[k] + " /h/");
							quesObject2.put("audio", "");
							quesObject2.put("image", "assets/images/gameSpecific/No" + str[k] + ".png");
							quesObject2.put("zoneId", 2);

							array.add(quesObject1);
							array.add(quesObject2);

							innerObject.put(col1Name, array);
							jsonObject.put(parentObject, innerObject);
						}
					}
				}
			}

			else if (col1Name.equalsIgnoreCase("answerDataV123")) {

				JSONArray array = new JSONArray();
				int uniqueId = 0;
				for (int i = 0; i < list.size(); i++) {

					String s[] = list.get(i).toString().split("\\|");
					String str[] = s[1].split(",");

					int relativeZone = 0;
					if (s[0].equalsIgnoreCase("correct")) {
						relativeZone = 1;
					} else {
						relativeZone = 2;
					}

					answerCount += str.length;
					for (int k = 0; k < str.length; k++) {

						str[k] = str[k].trim();

						JSONObject quesObject1 = new JSONObject();
						JSONObject quesObject2 = new JSONObject();

						quesObject1.put("word", str[k]);
						quesObject1.put("audio", "assets/audios/gameSpecific/" + str[k] + ".mp3");
						quesObject1.put("image", "assets/images/gameSpecific/" + str[k] + ".png");
						quesObject1.put("relativeZone", relativeZone);
						quesObject1.put("uniqueId", uniqueId++);

						array.add(quesObject1);
						// array.add(quesObject2);

						innerObject.put(col1Name, array);
						jsonObject.put(parentObject, innerObject);
					}
				}
			}

			if (list.size() > 1) {

				if (!col1Name.equalsIgnoreCase("answerDataV123") && !col1Name.equalsIgnoreCase("questionDataV123")) {
					JSONObject innerObject2 = new JSONObject();
					for (int i = 0; i < list.size(); i++) {
						String str[] = list.get(i).toString().split("\\|");

						if (str.length < 3) {
							if (str[0].equalsIgnoreCase("English"))
								str[0] = "en";
							else if (str[0].equalsIgnoreCase("Spanish"))
								str[0] = "es";

							innerObject2.put(str[0], str[1]);
							innerObject.put(col1Name, innerObject2);
							jsonObject.put(parentObject, innerObject);

						}
						// for infoAudio
						else {
							JSONObject innerObject3 = new JSONObject();
							for (int x = 1; x < str.length; x++) {
								if (str[0].equalsIgnoreCase("English"))
									str[0] = "en";
								else if (str[0].equalsIgnoreCase("Spanish"))
									str[0] = "es";

								innerObject3.put("audio" + x, str[x]);
							}
							innerObject2.put(str[0], innerObject3);
							innerObject.put(col1Name, innerObject2);
							jsonObject.put(parentObject, innerObject);

						}
					}
				}
			}
		}

		System.out.println("Answer Count : " + answerCount);
		innerObject.put("displayConfig", answerCount);
		innerObject.put("correctAnswers", answerCount);

		String jsonString = jsonObject.toJSONString();
		// System.out.println(JsonWriter.formatJson(jsonString));

		try (FileWriter file = new FileWriter(folderPath + "\\" + parentObject + "_" + gameType + ".json")) { // creating
																												// file
																												// name
																												// #game_1.json

			file.write(jsonString);

			System.out.println("\nSuccessfully Written JSON Object to File...");
			Main.consoleText.append("\nSuccessfully Written JSON Object to File...");
			deleteFile(htmlFilePath);
			Main.consoleText.append("\nCreated Json file path : " + Main.folderText.getText());
			JOptionPane.showMessageDialog(null, "JSON file created Successfully", "Message",
					JOptionPane.INFORMATION_MESSAGE);
			// System.out.println("\nJSON Object: " + jsonString);
		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	

	public static void deleteFile(String file) {

		String fileName = "";
		fileName = file;
		try {
			Files.deleteIfExists(Paths.get(fileName));
		} catch (NoSuchFileException e) {
			System.out.println("No such file/directory exists");
		} catch (DirectoryNotEmptyException e) {
			System.out.println("Directory is not empty");
		} catch (IOException e) {
			System.out.println("Invalid permissions");
			// FileDeleteStrategy.FORCE.doDelete(fileName);
		}

	}

	public static String columnNameChange(String columnName) {

		String col1Name = columnName;

		for (Map.Entry<String, String> entry : keywordMap.entrySet()) {

			if (columnName.trim().equalsIgnoreCase(entry.getKey())) {

				col1Name = entry.getValue();
				break;
			}
		}

		return col1Name;
	}
	
	public static void readKeyWord() {

		try {
			ExcelUtils.getExcelFile(folderName, fileName);
			ExcelUtils.getSheet(sheetName);
			int rowCount = ExcelUtils.getRowCount(folderName, fileName, sheetName);
			String key = "";
			String value = "";
			for (int i = 1; i <= rowCount; i++) {

				key = ExcelUtils.readExcelUserInput(folderName, fileName, sheetName, i, 0).toString();
				value = ExcelUtils.readExcelUserInput(folderName, fileName, sheetName, i, 1).toString();

				keywordMap.put(key, value);
			}

			ExcelUtils.closeExcelFile(folderName, fileName);

		} catch (IOException e) {
			System.out.println("something went wrong with file");
			// e.printStackTrace();
		} catch (InterruptedException e) {
			System.out.println(
					"Interruption in file reading....." + "\nMake sure file is not open or in use by other program");
			// e.printStackTrace();
		}

		// System.out.println(keywordMap);

	}

}
