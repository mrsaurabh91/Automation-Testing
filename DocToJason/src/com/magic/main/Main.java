package com.magic.main;

import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintStream;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Main implements ActionListener {

	static JFrame frame;
	JPanel panel, panel1, panel2;
	JButton submitBtn, docBrowseBtn, folderBrowseBtn, resetBtn;
	static JTextField docText, folderText;
	private PrintStream standardOut;
	public static JTextArea consoleText;

	public static void main(String... args) throws Exception {

		new Main();

	}

	Main() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		frame = new JFrame("Docx to Json Converter");
		ImageIcon img = new ImageIcon("icon.png");
		frame.setIconImage(img.getImage());

		frame.setSize(605, 450);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);

		panel = new JPanel();
		panel.setBounds(0, 0, 800, 80);
		panel.setBackground(java.awt.Color.decode("#FFFFFF"));

		panel1 = new JPanel();
		panel1.setBounds(0, 60, 600, 230);
		panel1.setBackground(java.awt.Color.decode("#BDBDBD"));

		panel2 = new JPanel();
		panel2.setBounds(0, 310, 600, 140);
		panel2.setBackground(java.awt.Color.decode("#FFFFFF"));

		frame.add(panel);
		frame.add(panel1);
		frame.add(panel2);

		panel.setLayout(null);
		panel1.setLayout(null);
		panel2.setLayout(null);

		ImageIcon icon = new ImageIcon("magic.png");

		JLabel label = new JLabel();
		label.setIcon(icon);
		label.setBounds(2, 4, 250, 75);
		panel.add(label);

		JLabel Title = new JLabel("Docx to Json Converter");
		Title.setFont(new Font("Serif", Font.BOLD, 24));
		Title.setBounds(330, 15, 320, 40);
		panel.add(Title);

		int x = 50;
		int y = 20;

		JLabel docPathLabel = new JLabel("Doc File select");
		docPathLabel.setFont(new Font("default", Font.BOLD, 11));
		docPathLabel.setBounds(x, y += 40, 85, 25);
		panel1.add(docPathLabel);

		docText = new JTextField(20);
		docText.setBounds(x + 90, y, 280, 25);
		panel1.add(docText);

		docBrowseBtn = new JButton("Browse...");
		docBrowseBtn.setBounds(x + 390, y, 80, 25);
		docBrowseBtn.addActionListener(this);
		panel1.add(docBrowseBtn);

		JLabel folderPathName = new JLabel("Folder Path");
		folderPathName.setFont(new Font("default", Font.BOLD, 11));
		folderPathName.setBounds(x, y += 40, 85, 25);
		panel1.add(folderPathName);

		folderText = new JTextField(20);
		folderText.setBounds(x + 90, y, 280, 25);
		panel1.add(folderText);

		folderBrowseBtn = new JButton("Browse...");
		folderBrowseBtn.setBounds(x + 390, y, 80, 25);
		folderBrowseBtn.addActionListener(this);
		panel1.add(folderBrowseBtn);

		submitBtn = new JButton("Submit");
		submitBtn.setBounds(x + 190, y += 40, 80, 25);
		submitBtn.addActionListener(this);
		panel1.add(submitBtn);

		resetBtn = new JButton("Reset");
		resetBtn.setBounds(x + 190, y += 40, 80, 25);
		resetBtn.addActionListener(this);
		panel1.add(resetBtn);

		consoleText = new JTextArea(20, 20);
		// consoleText.setLineWrap(true);
		consoleText.setEditable(false);
		consoleText.setBackground(java.awt.Color.decode("#FFFFFF"));
		JScrollPane scr = new JScrollPane(consoleText);
		scr.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scr.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		// consoleText.scrollRectToVisible(consoleText.getVisibleRect());

		// DefaultCaret caret = (DefaultCaret)consoleText.getCaret();
		// caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

		consoleText.setMargin(new Insets(5, 5, 0, 0));
		consoleText.setFont(new Font("Monospaced", Font.PLAIN, 11));
		scr.setBounds(0, 290, 600, 132);
		panel2.add(scr);

		// consoleText();

		frame.setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == folderBrowseBtn) {
			JFileChooser fc = new JFileChooser();
			fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			fc.setDialogTitle("Select path");
			int returnVal = fc.showOpenDialog(folderBrowseBtn);

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				String s1 = fc.getSelectedFile().getAbsolutePath();
				folderText.setText(s1);
				System.out.println("\nCreated Json file path : " + s1);
				// consoleText.append("\nCreated Json file path : "+s1);
			}
		}
		if (e.getSource() == docBrowseBtn) {
			JFileChooser fc = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter("DOC FILES", "doc", "docx");
			fc.setFileFilter(filter);
			fc.setDialogTitle("Select path");
			int returnVal = fc.showOpenDialog(docBrowseBtn);

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				String s1 = fc.getSelectedFile().getAbsolutePath();
				docText.setText(s1);
				System.out.println("Selected doc file path : " + s1);
				consoleText.append("Selected doc file path : " + s1);
			}
		}

		if (e.getSource() == submitBtn) {

			consoleText.append("\nConverting docx file into json....");

			docText.setEnabled(false);
			folderText.setEnabled(false);
			submitBtn.setEnabled(false);
			folderBrowseBtn.setEnabled(false);
			docBrowseBtn.setEnabled(false);

			String docFilePath = "";
			String folderPath = "";

			if (docText.getText().trim().isEmpty() || folderText.getText().trim().isEmpty()) {
				System.out.println("Path left blank");
			} else {

				docFilePath = docText.getText().trim();
				folderPath = folderText.getText().trim();

				try {
//					TemplateSelector.selectTemplate(docFilePath, folderPath);
				} catch (Exception e1) {
					e1.printStackTrace();
				}

			}
		}
		if (e.getSource() == resetBtn) {

			docText.setEnabled(true);
			folderText.setEnabled(true);
			submitBtn.setEnabled(true);
			folderBrowseBtn.setEnabled(true);
			docBrowseBtn.setEnabled(true);

			docText.setText("");
			folderText.setText("");
			consoleText.setText("");
		}

	}
	
	public void consoleText() {

		PrintStream printStream = new PrintStream(new CustomOutputStream(Main.consoleText));

		// keeps reference of standard output stream
		standardOut = System.out;

		// re-assigns standard output stream and error output stream
		System.setOut(printStream);
		System.setErr(printStream);
	}

}
