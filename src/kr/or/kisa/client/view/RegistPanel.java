package kr.or.kisa.client.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import kr.or.kisa.client.model.ChatClient;

public class RegistPanel extends JPanel {
	ChatUI ui;

	ChatClient chatClient;

	GridBagLayout gridBag;
	GridBagConstraints constraints;

	JTextArea memberLawTA;
	JScrollPane scrollPane;
	JLabel yesnoL, nickNameL, nameL, idL, pwL, pwcL, genderL, birthDateL,
			yearL, monthL, dayL, eMailL;
	JTextField nickNameTF, nameTF, idTF, eMailTF;
	JPasswordField pwF, pwcF;
	JRadioButton yesRB, noRB, manRB, womenRB;
	JEditorPane htmlPan;
	JScrollPane jsp;
	public JButton registB, returnB;
	JComboBox<String> yearType, monthType, dayType;
	String[] yearTypeComboBox = new String[100];
	String[] monthTypeComboBox = new String[12];
	String[] dayTypeComboBox = new String[31];
	Image bgImg;
	public static final byte MAN = 0;
	public static final byte WOMAN = 1;
	byte gender;

	public RegistPanel(ChatUI ui) {
		// super(title);
		this.ui = ui;

		gridBag = new GridBagLayout();
		constraints = new GridBagConstraints();

		htmlPan = new JEditorPane();
		htmlPan.setContentType("text/html");
		htmlPan.setEditable(false);
		try {
			htmlPan.setPage("http://smin2.me/wp-content/uploads/2015/02/readme.html");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		jsp = new JScrollPane(htmlPan);
		jsp.setPreferredSize(new Dimension(150, 130));
		jsp.setOpaque(true);

		yesnoL = new JLabel("ÀÌ¿ë¾à°ü¿¡ µ¿ÀÇÇÏ½Ã°Ú½À´Ï±î?", JLabel.LEFT);
		nickNameL = new JLabel("´Ð³×ÀÓ", JLabel.RIGHT);
		nickNameL.setFont(new Font("³ª´®°íµñ", Font.PLAIN, 13));
		nameL = new JLabel("ÀÌ¸§", JLabel.RIGHT);
		nameL.setFont(new Font("³ª´®°íµñ", Font.PLAIN, 13));
		idL = new JLabel("¾ÆÀÌµð", JLabel.RIGHT);
		idL.setFont(new Font("³ª´®°íµñ", Font.PLAIN, 13));
		pwL = new JLabel("ºñ¹Ð¹øÈ£", JLabel.RIGHT);
		pwL.setFont(new Font("³ª´®°íµñ", Font.PLAIN, 13));
		pwcL = new JLabel("ºñ¹Ð¹øÈ£È®ÀÎ", JLabel.RIGHT);
		pwcL.setFont(new Font("³ª´®°íµñ", Font.PLAIN, 13));
		genderL = new JLabel("¼ºº°", JLabel.RIGHT);
		genderL.setFont(new Font("³ª´®°íµñ", Font.PLAIN, 13));
		birthDateL = new JLabel("»ý³â¿ùÀÏ", JLabel.RIGHT);
		birthDateL.setFont(new Font("³ª´®°íµñ", Font.PLAIN, 13));
		yearL = new JLabel("³â", JLabel.RIGHT);
		yearL.setFont(new Font("³ª´®°íµñ", Font.PLAIN, 13));
		monthL = new JLabel("¿ù", JLabel.RIGHT);
		monthL.setFont(new Font("³ª´®°íµñ", Font.PLAIN, 13));
		dayL = new JLabel("ÀÏ", JLabel.RIGHT);
		dayL.setFont(new Font("³ª´®°íµñ", Font.PLAIN, 13));
		eMailL = new JLabel("ÀÌ¸ÞÀÏ", JLabel.RIGHT);
		eMailL.setFont(new Font("³ª´®°íµñ", Font.PLAIN, 13));

		pwF = new JPasswordField();
		pwcF = new JPasswordField();

		nickNameTF = new JTextField();
		nickNameTF.setEnabled(false);
		nameTF = new JTextField();
		nameTF.setEnabled(false);
		idTF = new JTextField();
		idTF.setEnabled(false);
		eMailTF = new JTextField();
		eMailTF.setEnabled(false);

		ButtonGroup bgr1 = new ButtonGroup();
		manRB = new JRadioButton("³²ÀÚ");
		manRB.setEnabled(false);
		manRB.setBackground(Color.WHITE);
		
		womenRB = new JRadioButton("¿©ÀÚ");
		womenRB.setEnabled(false);
		womenRB.setBackground(Color.WHITE);
		bgr1.add(manRB);
		bgr1.add(womenRB);

		ButtonGroup bgr2 = new ButtonGroup();
		yesRB = new JRadioButton("µ¿ÀÇ", false);
		noRB = new JRadioButton("µ¿ÀÇ¾ÈÇÔ", true);
		bgr2.add(yesRB);
		bgr2.add(noRB);

		registB = new JButton("°¡ÀÔÇÏ±â");
		registB.setEnabled(false);
		registB.setForeground(Color.WHITE);
		registB.setBackground(Color.BLACK);
		registB.setOpaque(true);
		registB.setBorderPainted(false);
		registB.setBorder(BorderFactory.createLineBorder(Color.BLACK, 15));
		registB.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseFirst(java.awt.event.MouseEvent evt) {
				registB.setForeground(Color.WHITE);
				registB.setBackground(Color.BLACK);
			}

			public void mousePressed(java.awt.event.MouseEvent evt) {
				registB.setBackground(Color.lightGray);
				registB.setForeground(Color.white);
			}

			public void mouseClicked(java.awt.event.MouseEvent evt) {
				registB.setBackground(Color.BLACK);
				registB.setForeground(Color.WHITE);

			}

		});

		returnB = new JButton("Ãë¼Ò");
		returnB.setEnabled(false);
		returnB.setForeground(Color.WHITE);
		returnB.setBackground(Color.LIGHT_GRAY);
		returnB.setOpaque(true);
		returnB.setBorderPainted(false);
		returnB.setBorder(BorderFactory.createLineBorder(Color.BLACK, 15));
		returnB.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseFirst(java.awt.event.MouseEvent evt) {
				returnB.setForeground(Color.WHITE);
				returnB.setBackground(Color.LIGHT_GRAY);
			}

			public void mousePressed(java.awt.event.MouseEvent evt) {
				returnB.setBackground(Color.LIGHT_GRAY);
				returnB.setForeground(Color.WHITE);
				ui.change("¼±¹Î");
				ui.setResizable(false);
				ui.setSize(400, 600);

			}

			public void mouseClicked(java.awt.event.MouseEvent evt) {
				returnB.setBackground(Color.BLACK);
				returnB.setForeground(Color.WHITE);

			}

		});

		// ³â
		for (int i = 0; i < 100; i++) {
			yearTypeComboBox[i] = String.valueOf(1916 + i);
		}
		for (int i = 0; i < yearTypeComboBox.length; i++) {
			// System.out.println(yearTypeComboBox[i]);
		}
		yearType = new JComboBox<String>(yearTypeComboBox);

		// ¿ù
		for (int i = 0; i < 12; i++) {
			monthTypeComboBox[i] = String.valueOf(i + 1);
		}
		for (int i = 0; i < monthTypeComboBox.length; i++) {
			// System.out.println(monthTypeComboBox[i]);
		}
		monthType = new JComboBox<String>(monthTypeComboBox);

		// ÀÏ
		for (int i = 0; i < 31; i++) {
			dayTypeComboBox[i] = String.valueOf(i + 1);
		}
		for (int i = 0; i < dayTypeComboBox.length; i++) {
			// System.out.println(dayTypeComboBox[i]);
		}
		dayType = new JComboBox<String>(dayTypeComboBox);

		bgImg = new ImageIcon(Toolkit.getDefaultToolkit().getImage(
				getClass().getResource("/images/registUI.png"))).getImage();

	}

	public void setContents() {
		setLayout(gridBag);
		constraints.fill = GridBagConstraints.BOTH;
		constraints.insets = new Insets(0, 0, 0, 0);

		constraints.weightx = 0.0;
		constraints.weighty = 0.0;
		constraints.insets = new Insets(0, 0, 0, 0);

		add(jsp, 1, 0, 10, 10);

		// constraints.insets = new Insets(0, 0, 30, 0);
		// constraints.weightx = 1.0;
		add(yesnoL, 1, 11, 3, 1);
		add(yesRB, 5, 11, 1, 1);
		add(noRB, 7, 11, 1, 1);

		add(nickNameL, 1, 12, 1, 1);
		add(nickNameTF, 3, 12, 5, 1);

		add(nameL, 1, 13, 1, 1);
		add(nameTF, 3, 13, 5, 1);

		add(idL, 1, 14, 1, 1);
		add(idTF, 3, 14, 5, 1);

		add(pwL, 1, 15, 1, 1);
		add(pwF, 3, 15, 5, 1);

		add(pwcL, 1, 16, 1, 1);
		add(pwcF, 3, 16, 5, 1);

		add(birthDateL, 1, 17, 1, 1);

		add(yearType, 3, 17, 1, 1);
		add(yearL, 4, 17, 1, 1);

		add(monthType, 5, 17, 1, 1);
		add(monthL, 6, 17, 1, 1);

		add(dayType, 7, 17, 1, 1);
		add(dayL, 8, 17, 1, 1);

		add(genderL, 1, 18, 1, 1);
		add(manRB, 3, 18, 1, 1);
		add(womenRB, 5, 18, 1, 1);

		add(eMailL, 1, 19, 1, 1);
		add(eMailTF, 3, 19, 5, 1);

		constraints.insets = new Insets(0, 0, 25, 0);
		add(registB, 1, 20, 3, 1);
		add(returnB, 5, 20, 3, 1);

	}

	public boolean isNull() {
		if (nickNameTF.getText() == null
				|| nickNameTF.getText().trim().length() == 0
				|| nameTF.getText() == null
				|| nameTF.getText().trim().length() == 0
				|| idTF.getText() == null
				|| idTF.getText().trim().length() == 0
				|| new String(pwF.getPassword()) == null
				|| new String(pwF.getPassword()).trim().length() == 0
				|| new String(pwcF.getPassword()) == null
				|| new String(pwcF.getPassword()).trim().length() == 0
				|| eMailTF.getText() == null
				|| eMailTF.getText().trim().length() == 0) {
			return false;
		}
		return true;
	}

	/*
	 * public String registPerson(){ if(isNull()){ return Message.REQUEST_JOIN +
	 * Message.DELIMETER + nickNameTF.getText() + Message.DELIMETER +
	 * nameTF.getText() + Message.DELIMETER + idTF.getText() + Message.DELIMETER
	 * + pwTF.getText() + Message.DELIMETER + gender + Message.DELIMETER + +
	 * Message.DELIMETER + eMailTF.getText();
	 * 
	 * }else{ return null; } }
	 */

	// ºñ¹Ð¹øÈ£ À¯È¿¼º °Ë»ç.
	public boolean isEqualPw() {
		if (new String(pwcF.getPassword())
				.equals(new String(pwF.getPassword()))) {
			return true;
		}
		return false;
	}

	// ÃÊ±âÈ­
	public void clear() {
		nickNameTF.setText("");
		nickNameTF.setEnabled(false);
		nameTF.setText("");
		nameTF.setEnabled(false);
		idTF.setText("");
		idTF.setEnabled(false);
		pwF.setText("");
		pwcF.setText("");
		eMailTF.setText("");
		yearType.setSelectedIndex(0);
		monthType.setSelectedIndex(0);
		dayType.setSelectedIndex(0);
		manRB.setSelected(true);
		womenRB.setSelected(false);
		yesRB.setSelected(false);
		noRB.setSelected(true);
		// memberLawTA.setEditable(false);

	}

	public void eventRegist() {
		registB.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ui.join();
				clear();

			}
		});

		manRB.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				gender = MAN;
			}
		});

		womenRB.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				gender = WOMAN;
			}
		});

		yesRB.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				nickNameTF.setEnabled(true);
				nameTF.setEnabled(true);
				idTF.setEnabled(true);
				pwF.setEnabled(true);
				pwcF.setEnabled(true);
				womenRB.setEnabled(true);
				manRB.setEnabled(true);
				yearType.setEnabled(true);
				monthType.setEnabled(true);
				dayType.setEnabled(true);
				eMailTF.setEnabled(true);
				registB.setEnabled(true);
				returnB.setEnabled(true);
			}
		});
		noRB.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				nickNameTF.setEnabled(false);
				nameTF.setEnabled(false);
				idTF.setEnabled(false);
				pwF.setEnabled(false);
				pwcF.setEnabled(false);
				womenRB.setEnabled(false);
				manRB.setEnabled(false);
				yearType.setEnabled(false);
				monthType.setEnabled(false);
				dayType.setEnabled(false);
				eMailTF.setEnabled(false);
				registB.setEnabled(false);
			}
		});
		returnB.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ui.change("¼±¹Î");
				clear();
			}
		});

	}

	private void add(Component com, int gridx, int gridy, int gridwidth,
			int gridheight) {
		constraints.gridx = gridx;
		constraints.gridy = gridy;
		constraints.gridwidth = gridwidth;
		constraints.gridheight = gridheight;
		gridBag.setConstraints(com, constraints);
		add(com);
	}

	public void exit() {
		setVisible(false);
		System.exit(0);
	}

	@Override
	public void paintComponent(Graphics g) {
		g.drawImage(bgImg, 0, 0, getWidth(), getHeight(), this);
		// g.drawLine(x1, y1, x2, y2);
	}
}
