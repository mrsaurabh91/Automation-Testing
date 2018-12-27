package com.magic.sussex.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import com.magic.sussex.canvasDataPour.MainSussexPageCreation;
import com.magic.sussex.canvasDataPour.ReadTemplate;
import com.magic.util.WebDriverFactory;

public class GUI extends WebDriverFactory implements ActionListener{

	public static Logger logger = Logger.getLogger(GUI.class.getName());
	static FileHandler fh;
	static ConsoleHandler consoleHandler;
	
	
	String toolName = "mAutoIngestion";
	
	public static JFrame frame;
	JPanel toolNamePanel, htmlCreationPanel, consolePanel,imageUploadPanel;
	JButton  loginButton, browseBtn2, browseBtn3, imageUploadBrowseButton,imageUploadSubmitButton;
	public static JButton imageUploadResetButton;
	public static JButton resetBtn;
	JRadioButton rb1, rb2;
	JTextField userTextBox, urlTextBox, folderNameTextBox, programTextBox, tempTextBox, docFileTextBox,imgeUploadUrlBox,imageUploadPasswordBox,imageUploadUserNameBox,imageUploadProgrameBox,imageUploadFolderNameBox,imageUploadAssetPathTextBox;
	JPasswordField passwordText;
	static JButton submitBtn;
	JTextField pathText2;
	JTextField browseText2;
	public static JTextArea consoleText = new JTextArea(20, 20);
	
	JFrame tableFrame;
	DefaultTableModel model;
	JButton tableSubmitBtn, downlodBtn;
	JTable table;
	JPanel bottomPanel;	
	
	static String url = "";
	static String userName = "";
	static String programeName = "";
	static String folderName = "";
	public static String assetsPathAtLoacalMachine = "";
	static String templateFolderPath = "";			
	static String imageUpload = "";
	static String password = "";
	static String docFilePath = "";
	
	JTabbedPane jTabbedPane;  

	@SuppressWarnings("unused")
	private PrintStream standardOut;

	
	public static void main(String[] args) {
		
		try{
			
			try {
				 
				 String dir = "";
				 dir = System.getProperty("user.dir");
				 
				 DateFormat df = new SimpleDateFormat("dd_MM_yy HH_mm_ss");
				 Date dateobj = new Date();
				 
				 dir = dir+"\\log\\"+df.format(dateobj).toString()+" LogFile.log";
				 fh = new FileHandler(dir);
			        logger.addHandler(fh);
			        //logger.setLevel(Level.ALL);
			        SimpleFormatter formatter = new SimpleFormatter();
			        fh.setFormatter(formatter);
			        
			      
			        
			    } catch (SecurityException e) {
			        e.printStackTrace();
			    } catch (IOException e) {
			        e.printStackTrace();
			    }

			try {
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			GUI gui = new GUI();
			gui.createGUIMain();
			
		}finally{
			if(driver != null){
				driver.quit();
				driver = null;
			}
		}
	}	
	
	public void createGUIMain() {
	
		frame = new JFrame();

		
		frame.setSize(1100, 630);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		ImageIcon icon1 = new ImageIcon("./logo/magicIcon.png");
		frame.setIconImage(icon1.getImage());
		frame.getContentPane().setBackground( Color.red );
		
		toolNamePanel = new JPanel();
		toolNamePanel.setBounds(0, 0, 1100, 100);
		toolNamePanel.setBackground(java.awt.Color.decode("#FFFFFF"));
		
		frame.add(toolNamePanel);
		
		jTabbedPane = new JTabbedPane(); 
		jTabbedPane.setBounds(0, 100, 650, 530);
		jTabbedPane.setBackground(Color.white);
		htmlCreationPanel = new JPanel();
		htmlCreationPanel.setBounds(0, 100, 650, 530);
		htmlCreationPanel.setBackground(java.awt.Color.decode("#BDBDBD"));
		
		imageUploadPanel = new JPanel();
		imageUploadPanel.setBounds(0, 100, 650, 530);
		imageUploadPanel.setBackground(java.awt.Color.decode("#BDBDBD"));
		
		
		jTabbedPane.add("Ingestion",htmlCreationPanel);  
		jTabbedPane.add("Asset Upload",imageUploadPanel);  
		
		frame.add(jTabbedPane);
		
		consolePanel = new JPanel();
		consolePanel.setBounds(650, 100, 550, 530);
		consolePanel.setBackground(java.awt.Color.decode("#FFFDDD"));
		
		frame.add(consolePanel);

		toolNamePanel.setLayout(null);
		htmlCreationPanel.setLayout(null);
		imageUploadPanel.setLayout(null);
		consolePanel.setLayout(null);

		/* ****************************** panel ***************************** */

		ImageIcon icon = new ImageIcon("./logo/magic.png");

		JLabel label = new JLabel();
		label.setIcon(icon);
		label.setBounds(10, 3, 400, 100);
		toolNamePanel.add(label);

		JLabel Title = new JLabel();
		Title.setFont(new Font("Serif", Font.ITALIC, 24));
		Title.setBounds(400, 30, 200, 40);
		toolNamePanel.add(Title);

		/* ****************************** panel1 ***************************** */

		int x = 30;
		int y = 30;
		int textboxSize=300;
		int textFieldStartPoint=150;

		JLabel sussexLabel = new JLabel("URL of Program");
		sussexLabel.setFont(new Font("verdana", Font.BOLD, 12));
		sussexLabel.setBounds(x, y, 150, 25);
		htmlCreationPanel.add(sussexLabel);


		urlTextBox = new JTextField(20);
		urlTextBox.setBounds(x + textFieldStartPoint, y, textboxSize, 25);
		htmlCreationPanel.add(urlTextBox);

		JLabel userLabel = new JLabel("User Name");
		userLabel.setFont(new Font("verdana", Font.BOLD, 12));
		userLabel.setBounds(x, y += 40, 80, 25);
		htmlCreationPanel.add(userLabel);

		userTextBox = new JTextField(20);
		userTextBox.setBounds(x + textFieldStartPoint, y, textboxSize, 25);
		htmlCreationPanel.add(userTextBox);

		JLabel passwordLabel = new JLabel("Password");
		passwordLabel.setFont(new Font("verdana", Font.BOLD, 12));
		passwordLabel.setBounds(x, y += 40, 80, 25);
		htmlCreationPanel.add(passwordLabel);

		passwordText = new JPasswordField(20);
		passwordText.setBounds(x + textFieldStartPoint, y, textboxSize, 25);
		htmlCreationPanel.add(passwordText);


		JLabel programLabel = new JLabel("Course Name");
		programLabel.setFont(new Font("verdana", Font.BOLD, 12));
		programLabel.setBounds(x, y += 40, 120, 25);
		htmlCreationPanel.add(programLabel);

		programTextBox = new JTextField(20);
		programTextBox.setBounds(x + textFieldStartPoint, y, textboxSize, 25);
		htmlCreationPanel.add(programTextBox);

		JLabel folderName = new JLabel("Assets Folder Name");
		folderName.setFont(new Font("verdana", Font.BOLD, 12));
		folderName.setBounds(x, y += 40, 150, 25);
		htmlCreationPanel.add(folderName);

		folderNameTextBox = new JTextField(20);
		folderNameTextBox.setBounds(x + textFieldStartPoint, y, textboxSize, 25);
		htmlCreationPanel.add(folderNameTextBox);


		JLabel docSelectLabel = new JLabel("Select Storyboard");
		docSelectLabel.setFont(new Font("verdana", Font.BOLD, 12));
		docSelectLabel.setBounds(x, y += 40, 180, 25);
		htmlCreationPanel.add(docSelectLabel);

		docFileTextBox = new JTextField(20);
		docFileTextBox.setBounds(x + textFieldStartPoint, y, textboxSize, 25);
		htmlCreationPanel.add(docFileTextBox);

		browseBtn2 = new JButton("Select file");
		browseBtn2.setBounds(x + 480, y, 80, 25);
		browseBtn2.addActionListener(this);
		htmlCreationPanel.add(browseBtn2);

		JLabel templetLebel = new JLabel("Select Templates");
		templetLebel.setFont(new Font("verdana", Font.BOLD, 12));
		templetLebel.setBounds(x, y += 40, 150, 25);
		htmlCreationPanel.add(templetLebel);

		tempTextBox = new JTextField(20);
		tempTextBox.setBounds(x + textFieldStartPoint, y, textboxSize, 25);
		htmlCreationPanel.add(tempTextBox);

		browseBtn3 = new JButton("Browse...");
		browseBtn3.setBounds(x + 480, y, 80, 25);
		browseBtn3.addActionListener(this);
		htmlCreationPanel.add(browseBtn3);

		
//		ImageIcon submitIicon = new ImageIcon("./logo/submit.png");
		
		submitBtn = new JButton("Submit");
		submitBtn.setBounds(x+ 230, y += 40, 80, 25);
		//submitBtn2.setForeground(java.awt.Color.decode("#E9B4BC"));
		submitBtn.addActionListener(this);
		htmlCreationPanel.add(submitBtn);
		
		
		resetBtn = new JButton("Reset");
		resetBtn.setBounds(x + 230, y += 40, 80, 25);
		resetBtn.addActionListener(this);
		htmlCreationPanel.add(resetBtn);
		
		int xAxis = 30, yAxis = 30;
		
		JLabel imageUploadUrlLabel = new JLabel("URL of Program");
		imageUploadUrlLabel.setFont(new Font("verdana", Font.BOLD, 12));
		imageUploadUrlLabel.setBounds(xAxis, yAxis, 150, 25);
		imageUploadPanel.add(imageUploadUrlLabel);
		
		imgeUploadUrlBox = new JTextField(20);
		imgeUploadUrlBox.setBounds(xAxis + textFieldStartPoint, yAxis, textboxSize, 25);
		imageUploadPanel.add(imgeUploadUrlBox);
		
		JLabel imageUploadUserLabel = new JLabel("User Name");
		imageUploadUserLabel.setFont(new Font("verdana", Font.BOLD, 12));
		imageUploadUserLabel.setBounds(xAxis, yAxis += 40, 80, 25);
		imageUploadPanel.add(imageUploadUserLabel);

		imageUploadUserNameBox = new JTextField(20);
		imageUploadUserNameBox.setBounds(xAxis + textFieldStartPoint, yAxis, textboxSize, 25);
		imageUploadPanel.add(imageUploadUserNameBox);

		JLabel imageUploadPasswordLabel = new JLabel("Password");
		imageUploadPasswordLabel.setFont(new Font("verdana", Font.BOLD, 12));
		imageUploadPasswordLabel.setBounds(xAxis, yAxis += 40, 80, 25);
		imageUploadPanel.add(imageUploadPasswordLabel);

		imageUploadPasswordBox = new JPasswordField(20);
		imageUploadPasswordBox.setBounds(xAxis + textFieldStartPoint,yAxis, textboxSize, 25);
		imageUploadPanel.add(imageUploadPasswordBox);


		JLabel imageUploadProgramLabel = new JLabel("Course Name");
		imageUploadProgramLabel.setFont(new Font("verdana", Font.BOLD, 12));
		imageUploadProgramLabel.setBounds(xAxis, yAxis += 40, 120, 25);
		imageUploadPanel.add(imageUploadProgramLabel);

		imageUploadProgrameBox = new JTextField(20);
		imageUploadProgrameBox.setBounds(xAxis + textFieldStartPoint, yAxis, textboxSize, 25);
		imageUploadPanel.add(imageUploadProgrameBox);

		JLabel imageUploadFolderName = new JLabel("Assets Folder Name");
		imageUploadFolderName.setFont(new Font("verdana", Font.BOLD, 12));
		imageUploadFolderName.setBounds(xAxis, yAxis += 40, 150, 25);
		imageUploadPanel.add(imageUploadFolderName);

		imageUploadFolderNameBox = new JTextField(20);
		imageUploadFolderNameBox.setBounds(xAxis + textFieldStartPoint, yAxis, textboxSize, 25);
		imageUploadPanel.add(imageUploadFolderNameBox);
		
		
		JLabel assetLabel = new JLabel("Path of Assets");	
		assetLabel.setFont(new Font("verdana", Font.BOLD, 12));
		assetLabel.setBounds(xAxis, yAxis+=40, 150, 25);
		imageUploadPanel.add(assetLabel);

		imageUploadAssetPathTextBox = new JTextField(20);
		imageUploadAssetPathTextBox.setBounds(xAxis+textFieldStartPoint, yAxis, textboxSize, 25);
		imageUploadPanel.add(imageUploadAssetPathTextBox);

		imageUploadBrowseButton = new JButton("Browse...");
		imageUploadBrowseButton.setBounds(xAxis+480, yAxis, 80, 25);
		imageUploadBrowseButton.addActionListener(this);
		imageUploadPanel.add(imageUploadBrowseButton);
		
		imageUploadSubmitButton = new JButton("Submit");
		imageUploadSubmitButton.setBounds(280, yAxis+=40, 80, 25);
		imageUploadSubmitButton.addActionListener(this);
		imageUploadPanel.add(imageUploadSubmitButton);
		
		
		imageUploadResetButton = new JButton("Reset");
		imageUploadResetButton.setBounds(280, yAxis+=40, 80, 25);
		imageUploadResetButton.addActionListener(this);
		imageUploadPanel.add(imageUploadResetButton);

		/* ****************************** panel3***************************** */

		consoleText.setBackground(java.awt.Color.decode("#f4f4f4"));
		JScrollPane scr = new JScrollPane(consoleText);
		scr.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scr.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		consoleText.scrollRectToVisible(consoleText.getVisibleRect());
		scr.setBounds(650, 100, 445, 502);
		consolePanel.add(scr);
//		consoleText();

		frame.setVisible(true);
		
		frame.addWindowListener(new WindowAdapter() {
		      public void windowClosing(WindowEvent e) {
		    	  if(driver != null){
						driver.quit();
						driver = null;						
					}		       
		        GUI.logger.info("Closing the application");
				GUI.consoleText.append("Closing the application\n");
		      }
		    });
		
		
	}

	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource() == imageUploadSubmitButton) {
			consoleText.setText("");
			imageUploadResetButton.setEnabled(false);
			String url = "";
			String userName = "";
			String password = "";
			String programe = "";
			String folderName = "";
			String localmachineAssetsPath = "";
			
			url = imgeUploadUrlBox.getText().trim();
			userName = imageUploadUserNameBox.getText().trim();
			password = imageUploadPasswordBox.getText().trim();
			programe = imageUploadProgrameBox.getText().trim();
			folderName = imageUploadFolderNameBox.getText().trim();			
			localmachineAssetsPath = imageUploadAssetPathTextBox.getText().trim();
			
			boolean checkAllInput = true;
			
			if(url == "" || url.isEmpty()){
				 logger.info("URL is blank");
				 GUI.consoleText.append("URL is blank\n");
				checkAllInput = false;
			}if(userName == "" || userName.isEmpty()){
				 logger.info("Username is blank");
				 GUI.consoleText.append("Username is blank\n");
				checkAllInput = false;
			}if(password == "" || password.isEmpty()){
				logger.info("Password is blank");
				 GUI.consoleText.append("Password is blank\n");
				checkAllInput = false;
			}if(programe == "" || programe.isEmpty()){
				logger.info("Programe Name is blank");
				 GUI.consoleText.append("Programe Name is blank\n");
				checkAllInput = false;
			}if(folderName == "" || folderName.isEmpty()){
				logger.info("Image upload Folder Name is blank");
				 GUI.consoleText.append("Image upload Folder Name is blank\n");
				checkAllInput = false;
			}if(localmachineAssetsPath == "" || localmachineAssetsPath.isEmpty()){
					GUI.consoleText.append("Assets Folder Path is blank\n");
					logger.info("Assets Folder Path is blank");
					checkAllInput = false;
			}
			
			imgeUploadUrlBox.setEnabled(false);
			imageUploadUserNameBox.setEnabled(false);
			imageUploadPasswordBox.setEnabled(false);
			imageUploadProgrameBox.setEnabled(false);
			imageUploadFolderNameBox.setEnabled(false);
			imageUploadBrowseButton.setEnabled(false);
			imageUploadSubmitButton.setEnabled(false);			
			imageUploadAssetPathTextBox.setEnabled(false);
			
			if(checkAllInput){
				
				ImageUploadExecution imageUploadExecution = new ImageUploadExecution(url,userName,password,programe,folderName,localmachineAssetsPath);
				imageUploadExecution.execute();
			}else{
				imageUploadResetButton.setEnabled(true);
				if(driver != null){
					driver.quit();
				}
				GUI.logger.info("Execution stops due to the lack of the required details, press the reset button and add required details and run again.");
				GUI.consoleText.append("Execution stops due to the lack of the required details, press the reset button and add required details and run again.\n");
			}
			
		}
		
		if(e.getSource()==imageUploadResetButton){
			
			imgeUploadUrlBox.setEnabled(true);
			imageUploadUserNameBox.setEnabled(true);
			imageUploadPasswordBox.setEnabled(true);
			imageUploadProgrameBox.setEnabled(true);
			imageUploadFolderNameBox.setEnabled(true);			
			imageUploadBrowseButton.setEnabled(true);
			imageUploadSubmitButton.setEnabled(true);			
			imageUploadAssetPathTextBox.setEnabled(true);	
			
			imgeUploadUrlBox.setText("");
			imageUploadUserNameBox.setText("");
			imageUploadPasswordBox.setText("");
			imageUploadProgrameBox.setText("");
			imageUploadFolderNameBox.setText("");		
			imageUploadAssetPathTextBox.setText("");
			consoleText.setText("");			
		}
		
		if (e.getSource() == imageUploadBrowseButton) {
			JFileChooser fc = new JFileChooser();
			fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			fc.setDialogTitle("Select path");
			int returnVal = fc.showOpenDialog(imageUploadBrowseButton);

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				String s1 = fc.getSelectedFile().getAbsolutePath();
				imageUploadAssetPathTextBox.setText(s1);
				System.out.println(s1);
			}
		}
		
		if (e.getSource() == browseBtn2) {
			JFileChooser fc = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter("DOC FILES", "doc", "docx");
			fc.setFileFilter(filter);

			int returnVal = fc.showOpenDialog(browseBtn2);

			if (returnVal == JFileChooser.APPROVE_OPTION) {

				String s2 = fc.getSelectedFile().getAbsolutePath();
				docFileTextBox.setText(s2);
				System.out.println(s2);
			}
		}

		if (e.getSource() == browseBtn3) {
			JFileChooser fc = new JFileChooser();
			fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			fc.setDialogTitle("Select path");
			int returnVal = fc.showOpenDialog(browseBtn3);

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				String s1 = fc.getSelectedFile().getAbsolutePath();
				tempTextBox.setText(s1);
				System.out.println(s1);
			}
		}

		
		if (e.getSource() == submitBtn) {
			consoleText.setText("");
			url = "";
			userName = "";
			programeName = "";
			folderName = "";
//			assetsPathAtLoacalMachine = "";
			templateFolderPath = "";			
			imageUpload = "";
			password = "";
			docFilePath = "";
			
			MainSussexPageCreation.htmllinkedMap = new LinkedHashMap<String,LinkedHashMap<String,ArrayList<String>>>();
			MainSussexPageCreation.listOfPageCreationType = new ArrayList<String>();
			
			url = urlTextBox.getText().trim();
			userName = userTextBox.getText().trim();
			password = passwordText.getText().trim();
			programeName = programTextBox.getText().trim();
			folderName = folderNameTextBox.getText().trim();
			docFilePath = docFileTextBox.getText().trim();
			templateFolderPath = tempTextBox.getText().trim();
			
			boolean checkAllInput = true;
			
			if(url == "" || url.isEmpty()){
				 logger.info("URL is blank");
				 GUI.consoleText.append("URL is blank\n");
				checkAllInput = false;
			}if(userName == "" || userName.isEmpty()){
				 logger.info("Username is blank");
				 GUI.consoleText.append("Username is blank\n");
				checkAllInput = false;
			}if(password == "" || password.isEmpty()){
				logger.info("Password is blank");
				 GUI.consoleText.append("Password is blank\n");
				checkAllInput = false;
			}if(programeName == "" || programeName.isEmpty()){
				logger.info("Course Name is blank");
				 GUI.consoleText.append("Course Name is blank\n");
				checkAllInput = false;
			}if(folderName == "" || folderName.isEmpty()){
				logger.info("Image upload Folder Name is blank");
				 GUI.consoleText.append("Image upload Folder Name is blank\n");
				checkAllInput = false;
			}if(docFilePath == "" || docFilePath.isEmpty()){
				logger.info("Doc File is blank");
				GUI.consoleText.append("Doc File is blank\n");
				checkAllInput = false;
			}else if(!docFilePath.contains(".docx")){
				logger.info("Please select .docx File");
				GUI.consoleText.append("Please select .docx File\n");
				
				checkAllInput = false;
			}if(templateFolderPath == "" || templateFolderPath.isEmpty()){
				GUI.consoleText.append("Template Folder is blank\n");
				logger.info("Template Folder is blank");
				checkAllInput = false;
			}
			
			urlTextBox.setEnabled(false);
			userTextBox.setEnabled(false);
			passwordText.setEnabled(false);
			programTextBox.setEnabled(false);
			folderNameTextBox.setEnabled(false);
			docFileTextBox.setEnabled(false);
			tempTextBox.setEnabled(false);
			submitBtn.setEnabled(false);			
			browseBtn2.setEnabled(false);
			browseBtn3.setEnabled(false);
			
			if(checkAllInput){
				
				StartBrowserAndReadInput startBrowserAndReadInput = new StartBrowserAndReadInput(docFilePath);
				startBrowserAndReadInput.execute();
							
			}else{
				
				if(!docFilePath.contains(".docx")){
					GUI.logger.info("Execution stops due to unsupported file type, press reset button and upload again.");
					GUI.consoleText.append("Execution stops due to unsupported file type, press reset button and upload again.\n");					
					
					}else{
						GUI.logger.info("Execution stops due to the lack of the required details, press the reset button and add required details.");
						GUI.consoleText.append("Execution stops due to the lack of the required details, press the reset button and add required details.\n");
					}
				if(driver != null){
					driver.quit();
					driver = null;
				}
			}			
		}		
		
		if(e.getSource()==resetBtn){
			
			MainSussexPageCreation.htmllinkedMap = new LinkedHashMap<String,LinkedHashMap<String,ArrayList<String>>>();
			MainSussexPageCreation.listOfPageCreationType = new ArrayList<String>();
			
			urlTextBox.setEnabled(true);
			userTextBox.setEnabled(true);
			passwordText.setEnabled(true);
			programTextBox.setEnabled(true);
			folderNameTextBox.setEnabled(true);			
			docFileTextBox.setEnabled(true);
			tempTextBox.setEnabled(true);
			submitBtn.setEnabled(true);	
			browseBtn2.setEnabled(true);
			browseBtn3.setEnabled(true);
			
			urlTextBox.setText("");
			userTextBox.setText("");
			passwordText.setText("");
			programTextBox.setText("");
			folderNameTextBox.setText("");
			docFileTextBox.setText("");
			tempTextBox.setText("");
			consoleText.setText("");
			
			url = "";
			userName = "";
			programeName = "";
			folderName = "";
			assetsPathAtLoacalMachine = "";
			templateFolderPath = "";			
			imageUpload = "";
			password = "";
			docFilePath = "";
			
			if(driver != null){
				driver.quit();
				driver = null;
			}
			
		}

		if (e.getSource() == tableSubmitBtn) {
			GUI.resetBtn.setEnabled(false);
			GUI.logger.info("Submit button of page creation type Clicked");
			for (int row = 0; row < table.getRowCount(); row++) {

				String pageName = "";
				String typeOfAction = "";
				boolean verifychecked = false;
				
				for (int col = 0; col < table.getColumnCount(); col++) {
					if(col == 0){
						pageName = (String) table.getValueAt(row, col);
					}else{
						Boolean b = ((Boolean) table.getValueAt(row, col));
						if (b.booleanValue()) {
							verifychecked = true;
							if(col == 1){
								typeOfAction = (String) "new";
							}else if(col == 2){
								typeOfAction = (String) "update";
							}/*else if(col == 3){
								typeOfAction = (String) "nochange";
							}*/
							break;
						}
					}
				}
				if(!verifychecked){
					typeOfAction = (String) "nochange";
				}
				if((!pageName.equalsIgnoreCase("") || (!pageName.isEmpty()))){
					MainSussexPageCreation.listOfPageCreationType.add(pageName+"|"+typeOfAction);
				}
			}			
			tableFrame.dispose();
			
			System.out.println("MainSussexPageCreation.listOfPageCreationType "+MainSussexPageCreation.listOfPageCreationType);
			
			boolean checkPageNeedToCreate = false;
			
			for(int pageCreationTypeCount = 0;pageCreationTypeCount<MainSussexPageCreation.listOfPageCreationType.size();pageCreationTypeCount++){
				String pageCreationType = "";
				pageCreationType = MainSussexPageCreation.listOfPageCreationType.get(pageCreationTypeCount).toLowerCase();
				
				ArrayList<String> spittedInputList = new ArrayList<String>();
				spittedInputList = ReadTemplate.getListSplitedByChar(pageCreationType, '|');
				
				for(int i = 0;i<spittedInputList.size();i++){
					if(i == 1){
						String str = "";
						str = spittedInputList.get(i).trim();
						if(str.contains("new") || str.contains("update")){
							checkPageNeedToCreate = true;
							break;
						}
					}
				}
			}
			
			if(checkPageNeedToCreate){
				ExecutionAferPageActionTypeSelected executionAferPageActionTypeSelected = new ExecutionAferPageActionTypeSelected(url, userName, password, programeName, folderName, docFilePath, templateFolderPath);
				executionAferPageActionTypeSelected.execute();				
			}else{
				
				GUI.logger.info("No new page creation or updation found, press reset button to run again.");
				GUI.consoleText.append("No new page creation or updation found, press reset button to run again.\n");
				ReadTemplate.deleteFile(GUI.assetsPathAtLoacalMachine);
				if(driver != null){
					driver.quit();
					driver = null;
					resetBtn.setEnabled(true);
				}
				
			}
		}
	}

	public void consoleText() {
		PrintStream printStream = new PrintStream(new CustomOutputStream(consoleText));

		// keeps reference of standard output stream
		standardOut = System.out;

		// re-assigns standard output stream and error output stream
		System.setOut(printStream);
		System.setErr(printStream);
	}

	public static void setSubmitButton(boolean b) {
		submitBtn.setEnabled(b);
	}
	
	
	public void createTable(int xx,int yy){
		
		tableFrame = new JFrame();
		tableFrame.setLocation(xx + 140, yy + 50);
		ImageIcon img = new ImageIcon();
		tableFrame.setIconImage(img.getImage());
		tableFrame.setResizable(false);
		tableFrame.setSize(500, 500);
		tableFrame.setVisible(true);
		tableFrame.setBackground(Color.GRAY);

		bottomPanel = new JPanel();

		tableSubmitBtn = new JButton("Submit");

		bottomPanel.add(tableSubmitBtn);
		tableSubmitBtn.addActionListener(this);

		tableFrame.add(bottomPanel, BorderLayout.SOUTH);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(33, 41, 494, 90);
		tableFrame.getContentPane().add(scrollPane);

		table = new JTable();
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(table);

		// Model for Table
		model = new DefaultTableModel() {
			public Class<?> getColumnClass(int column) {
				switch (column) {
				case 0:
					return String.class;
				case 1:
					return Boolean.class;
				case 2:
					return Boolean.class;				

				default:
					return String.class;
				}
			}
		};

		table.setModel(model);
		table.getModel().addTableModelListener(new CheckBoxModelListener());
		Color ivory = new Color(192,192,192);
		table.setOpaque(true);
		table.setFillsViewportHeight(true);
		table.setBackground(ivory);
		
		model.addColumn("Page Title");
		model.addColumn("Create New");
		model.addColumn("Update");

		int recordCount = 0;
		
		for(Entry<String,LinkedHashMap<String,ArrayList<String>>> tableHtmlEntrySet:MainSussexPageCreation.htmllinkedMap.entrySet()){
			
			String tableHtmlKey  = tableHtmlEntrySet.getKey();
			
			LinkedHashMap<String,ArrayList<String>> htmlLinkedHashMap = tableHtmlEntrySet.getValue();
			
			for(Entry<String,ArrayList<String>> htmlEntrySet:htmlLinkedHashMap.entrySet()){
				String htmlKey = "";
				htmlKey = htmlEntrySet.getKey();
				String PageName = "";
				if(htmlKey.equalsIgnoreCase("PageTitle")){
					ArrayList<String> tempList = new ArrayList<String>();
					tempList = htmlEntrySet.getValue();
					for(int i = 0;	i < tempList.size();	i++){
						if(i == 0){
							PageName = tempList.get(i).toString().trim();
							break;
						}						
					}
					if((!PageName.equalsIgnoreCase("")) || (!PageName.isEmpty())){
						model.addRow(new Object[0]);
						model.setValueAt(PageName, recordCount, 0);
						model.setValueAt(false, recordCount, 1);
						model.setValueAt(false, recordCount, 2);
						recordCount++;
						break;
					}					
				}
			}
		}
		
		tableFrame.addWindowListener(new WindowAdapter() {
		      public void windowClosing(WindowEvent e) {
		    	  if(driver != null){
						driver.quit();
						driver = null;						
					}
		        resetBtn.setEnabled(true);
		        GUI.logger.info("You have closed page creation type table, so the execution stopped.\nYou need to select the option before proceeding further. Press the reset button and try again.");
				GUI.consoleText.append("You have closed page creation type table, so the execution stopped.\nYou need to select the option before proceeding further. Press the reset button and try again.\n");
		      }
		    });
		
	}

	public class CheckBoxModelListener implements TableModelListener {

		public void tableChanged(TableModelEvent e) {
			int row = e.getFirstRow();
			int column = e.getColumn();

			if (column > 0) { 
				TableModel model = (TableModel) e.getSource();
				String columnName = model.getColumnName(column);
				Boolean checked = (Boolean) model.getValueAt(row, column);
				if (checked) {

					for (int i = 1; i < 3; i++) {
						if (!(column == i)) 
							model.setValueAt(false, row, i);
					} 
				}
			}
		}
	}
	
}
